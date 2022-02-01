package Manager;

import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    HashMap<Integer, Task> taskMap;
    HashMap<Integer, Epic> epicMap;
    HashMap<Integer, SubTask> subTaskMap;
    ArrayList<SubTask> subTasks;
    int taskID;
    int epicID;
    int subTaskID;

    public Manager() {
        taskID = 1;
        epicID = 1;
        subTaskID = 1;
        taskMap = new HashMap<>();
        epicMap = new HashMap<>();
        subTaskMap = new HashMap<>();
    }

    //Методы для Заданий
    public void saveTask(Task task) { //Сохранение задачи по идентификатору
        task.setiD(taskID++);
        taskMap.put(task.getiD(), task);
    }

    public ArrayList giveAllTasks() { //Вывод всех задач
        ArrayList<Task> tasks = new ArrayList<>();
        for (Integer tas : taskMap.keySet()) {
            tasks.add(taskMap.get(tas));
        }
        return tasks;
    }

    public Task giveTaskById(int inputTaskID) { //Вывод задачи по идентификатору
        return taskMap.get(inputTaskID);
    }

    public void removeAllTasks() { //Удаление всех задач
        taskMap.clear();
    }

    public void removeTaskById(int inputTaskID) { //Удаление задачи по идентификатору
        taskMap.remove(inputTaskID);
    }

    public void refreshTask(Task task) { //Обновление задачи
        taskMap.put(task.getiD(), task);
    }

    //Методы для Эпиков
    public void saveEpic(Epic epic) { //Сохранение эпика по идентификатору
        epic.setiD(epicID++);
        epicMap.put(epic.getiD(), epic);
    }

    public ArrayList giveAllEpics() { //Получение списка всех эпиков
        ArrayList<Epic> epics = new ArrayList<>();
        for (Integer ep : epicMap.keySet()) {
            epics.add(epicMap.get(ep));
        }
        return epics;
    }

    public Epic giveEpicById(int inputTaskID) { //Получение эпика по идентификатору
        return epicMap.get(inputTaskID);
    }

    public void refreshEpic(Epic epic) { //Обновление эпика
        epicMap.put(epic.getiD(), epic);
    }

    public void removeAllEpics() { //Удаление всех эпиков
        removeAllSubTasks();
        epicMap.clear();
    }

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
    public void saveSubTask(SubTask subTask) { //Сохранение подзадачи по идентификатору
        subTask.setiD(subTaskID++);
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

    public ArrayList giveAllSubTasks() { //Получение списка всех подзадач
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for (Integer ep : subTaskMap.keySet()) {
           subTasks.add(subTaskMap.get(ep));
        }
        return subTasks;
    }

    public SubTask giveSubTaskById(int inputSubTaskID) { //Получение подзадачи по идентификатору
        return subTaskMap.get(inputSubTaskID);
    }

    public void refreshSubTask(SubTask subTask) { //Обновление подзадачи
        subTaskMap.put(subTask.getiD(), subTask);
        changeEpicStatus(subTask);
    }

    public void removeAllSubTasks() { //Удаление всех подзадач
        subTaskMap.clear();
    }

    public void removeSubTaskById(int inputSubTaskID) { //Удаление подзадачи по идентификатору
        epicMap.get(giveSubTaskById(inputSubTaskID).getEpicId()).getSubTaskList().remove(giveSubTaskById(inputSubTaskID));
        changeEpicStatus(giveSubTaskById(inputSubTaskID));
        subTaskMap.remove(inputSubTaskID);
    }

    public ArrayList findSubTaskByEpic(Epic epic) { //Нахождение списка подзадач по эпику
        return epic.getSubTaskList();
    }
}