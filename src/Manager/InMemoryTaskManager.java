package Manager;

import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager extends Managers implements TaskManager{
    HashMap<Integer, Task> taskMap;
    HashMap<Integer, Epic> epicMap;
    HashMap<Integer, SubTask> subTaskMap;
    ArrayList<SubTask> subTasks;
    int taskID;
    List<Task> historyList;
    InMemoryHistoryManager history;

    public InMemoryTaskManager() {
        taskID = 1;
        taskMap = new HashMap<>();
        epicMap = new HashMap<>();
        subTaskMap = new HashMap<>();
        historyList = new ArrayList<>();
        history = new InMemoryHistoryManager();
    }

    //Методы для Заданий
    @Override
    public void saveTask(Task task) { //Сохранение задачи по идентификатору
        task.setiD(taskID++);
        taskMap.put(task.getiD(), task);
    }

    @Override
    public ArrayList<Task> giveAllTasks() { //Вывод всех задач
        ArrayList<Task> tasks = new ArrayList<>();
        for (Integer tas : taskMap.keySet()) {
            tasks.add(taskMap.get(tas));
        }
        return tasks;
    }

    @Override
    public Task giveTaskById(int inputTaskID) { //Вывод задачи по идентификатору
        history.add(taskMap.get(inputTaskID));
        return taskMap.get(inputTaskID);
    }

    @Override
    public void removeAllTasks() { //Удаление всех задач
        taskMap.clear();
    }

    @Override
    public void removeTaskById(int inputTaskID) { //Удаление задачи по идентификатору
        taskMap.remove(inputTaskID);
    }

    @Override
    public void refreshTask(Task task) { //Обновление задачи
        taskMap.put(task.getiD(), task);
    }

    //Методы для Эпиков
    @Override
    public void saveEpic(Epic epic) { //Сохранение эпика по идентификатору
        epic.setiD(taskID++);
        epicMap.put(epic.getiD(), epic);
    }

    @Override
    public ArrayList<Epic> giveAllEpics() { //Получение списка всех эпиков
        ArrayList<Epic> epics = new ArrayList<>();
        for (Integer ep : epicMap.keySet()) {
            epics.add(epicMap.get(ep));
        }
        return epics;
    }

    @Override
    public Epic giveEpicById(int inputTaskID) { //Получение эпика по идентификатору
        history.add(epicMap.get(inputTaskID));
        return epicMap.get(inputTaskID);
    }

    @Override
    public void refreshEpic(Epic epic) { //Обновление эпика
        epicMap.put(epic.getiD(), epic);
    }

    @Override
    public void removeAllEpics() { //Удаление всех эпиков
        removeAllSubTasks();
        epicMap.clear();
    }

    @Override
    public void removeEpicById(int inputEpicID) { //Удаление эпика по идентификатору
        for (SubTask sub : epicMap.get(inputEpicID).getSubTaskList()) {
            removeSubTaskById(sub.getiD());
        }
        epicMap.remove(inputEpicID);
    }

    public void changeEpicStatus(SubTask subTask) { //Изенение статуса эпика
        for (SubTask sub : epicMap.get(subTask.getEpicId()).getSubTaskList()) {
            if (sub.getTaskStatus().equals(String.valueOf(Status.NEW)) || (epicMap.get(sub.getEpicId()).getSubTaskList() == null)) {
                epicMap.get(sub.getEpicId()).setTaskStatus(String.valueOf(Status.NEW));
            } else if (sub.getTaskStatus().equals(String.valueOf(Status.DONE))) {
                epicMap.get(sub.getEpicId()).setTaskStatus(String.valueOf(Status.DONE));
            } else {
                epicMap.get(sub.getEpicId()).setTaskStatus(String.valueOf(Status.IN_PROGRESS));
            }
        }
    }

    //Методы для Подзадач
    @Override
    public void saveSubTask(SubTask subTask) { //Сохранение подзадачи по идентификатору
        subTask.setiD(taskID++);
        subTaskMap.put(subTask.getiD(), subTask);
        if (!(epicMap.get(subTask.getEpicId()).getSubTaskList() == null)) {
            subTasks = epicMap.get(subTask.getEpicId()).getSubTaskList();
            subTasks.add(subTask);
        } else {
            subTasks = new ArrayList<>();
            subTasks.add(subTask);
        }
        epicMap.get(subTask.getEpicId()).setSubTaskList(subTasks);
        changeEpicStatus(subTask);
    }

    @Override
    public ArrayList<SubTask> giveAllSubTasks() { //Получение списка всех подзадач
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for (Integer ep : subTaskMap.keySet()) {
           subTasks.add(subTaskMap.get(ep));
        }
        return subTasks;
    }

    @Override
    public SubTask giveSubTaskById(int inputSubTaskID) { //Получение подзадачи по идентификатору
        history.add(subTaskMap.get(inputSubTaskID));
        return subTaskMap.get(inputSubTaskID);
    }

    @Override
    public void refreshSubTask(SubTask subTask) { //Обновление подзадачи
        subTaskMap.put(subTask.getiD(), subTask);
        changeEpicStatus(subTask);
    }

    @Override
    public void removeAllSubTasks() { //Удаление всех подзадач
        subTaskMap.clear();
    }

    @Override
    public void removeSubTaskById(int inputSubTaskID) { //Удаление подзадачи по идентификатору
        epicMap.get(giveSubTaskById(inputSubTaskID).getEpicId()).getSubTaskList().remove(giveSubTaskById(inputSubTaskID));
        changeEpicStatus(giveSubTaskById(inputSubTaskID));
        subTaskMap.remove(inputSubTaskID);
    }

    @Override
    public ArrayList<SubTask> findSubTaskByEpic(Epic epic) { //Нахождение списка подзадач по эпику
        return epic.getSubTaskList();
    }

    @Override
    public List<Task> history() {
        return history.getHistory();
    }

}