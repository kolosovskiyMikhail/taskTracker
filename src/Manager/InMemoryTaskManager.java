package Manager;

import Enum.Status;
import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;

import java.io.IOException;
import java.time.Duration;
import java.util.*;

public class InMemoryTaskManager extends Managers implements TaskManager{
    HashMap<Integer, Task> taskMap;
    HashMap<Integer, Epic> epicMap;
    HashMap<Integer, SubTask> subTaskMap;
    ArrayList<SubTask> subTasks;
    int taskID;
    List<Task> historyList;
    InMemoryHistoryManager history;
    TreeSet<Task> sortedTasks;
    TreeSet<SubTask> sortedSubTasks;

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
    public void saveTask(Task task) throws IOException { //Сохранение задачи по идентификатору
        if (taskValidation(task)) {
            task.setiD(taskID++);
            taskMap.put(task.getiD(), task);
        }
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
    public Task giveTaskById(int inputTaskID) throws IOException { //Вывод задачи по идентификатору
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
        if (taskValidation(task)) {
            taskMap.put(task.getiD(), task);
        }
    }

    //Методы для Эпиков
    @Override
    public void saveEpic(Epic epic) throws IOException { //Сохранение эпика по идентификатору
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
    public Epic giveEpicById(int inputTaskID) throws IOException { //Получение эпика по идентификатору
        history.add(epicMap.get(inputTaskID));
        return epicMap.get(inputTaskID);
    }

    @Override
    public void refreshEpic(Epic epic) throws IOException { //Обновление эпика
        epicMap.put(epic.getiD(), epic);
    }

    @Override
    public void removeAllEpics() { //Удаление всех эпиков
        removeAllSubTasks();
        epicMap.clear();
    }

    @Override
    public void removeEpicById(int inputEpicID) throws IOException { //Удаление эпика по идентификатору
        if (!(epicMap.get(inputEpicID).getSubTaskList() == null)) {
            for (SubTask sub : epicMap.get(inputEpicID).getSubTaskList()) {
                removeSubTaskById(sub.getiD());
            }
        } else epicMap.remove(inputEpicID);
    }

    private void changeEpicStatus(SubTask subTask) { //Изенение статуса эпика
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

    private void changeEpicStartTimeAndDuration (SubTask subTask) {
        Duration durat = epicMap.get(subTask.getEpicId()).getDuration();
        for (SubTask sub : epicMap.get(subTask.getEpicId()).getSubTaskList()) {
            if (!(epicMap.get(subTask.getEpicId()).getStartTime() == null)) {
                if (sub.getStartTime().isBefore(epicMap.get(subTask.getEpicId()).getStartTime())) {
                    epicMap.get(subTask.getEpicId()).setStartTime(sub.getStartTime());
                }
            } else epicMap.get(subTask.getEpicId()).setStartTime(sub.getStartTime());

            if (!(durat == null)) {
                epicMap.get(subTask.getEpicId()).setDuration(durat.plus(sub.getDuration()));
            } else epicMap.get(subTask.getEpicId()).setDuration(sub.getDuration());
        }
    }

    //Методы для Подзадач
    @Override
    public void saveSubTask(SubTask subTask) { //Сохранение подзадачи по идентификатору
        if (subTaskValidation(subTask)) {
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
            changeEpicStartTimeAndDuration(subTask);
            (epicMap.get(subTask.getEpicId())).setEndTime(epicMap.get(subTask.getEpicId()).getEndTime());

        }
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
    public SubTask giveSubTaskById(int inputSubTaskID) throws IOException { //Получение подзадачи по идентификатору
        history.add(subTaskMap.get(inputSubTaskID));
        return subTaskMap.get(inputSubTaskID);
    }

    @Override
    public void refreshSubTask(SubTask subTask) { //Обновление подзадачи
        if (subTaskValidation(subTask)) {
            subTaskMap.put(subTask.getiD(), subTask);
        }
        changeEpicStatus(subTask);
    }

    @Override
    public void removeAllSubTasks() { //Удаление всех подзадач
        subTaskMap.clear();
    }

    @Override
    public void removeSubTaskById(int inputSubTaskID) throws IOException { //Удаление подзадачи по идентификатору
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

    @Override
    public TreeSet<Task> getPrioritizedTasks() {
        sortedTasks = new TreeSet<>(Comparator.comparing(Task::getStartTime));
        sortedTasks.addAll(taskMap.values());
        return sortedTasks;
    }

    @Override
    public TreeSet<SubTask> getPrioritizedSubTasks() {
        sortedSubTasks = new TreeSet<>(Comparator.comparing(SubTask::getStartTime));
        sortedSubTasks.addAll(subTaskMap.values());
        return sortedSubTasks;
    }

    public boolean taskValidation(Task task) {
        boolean b = false;
        if (!taskMap.isEmpty()) {
            for (Task t : getPrioritizedTasks()) {
               if (task.getStartTime().isAfter(t.getEndTime()) || task.getStartTime().equals(t.getEndTime())) {
                   b = true;
                   break;
                }
            }
        } else b = true;
        return b;
    }

    public boolean subTaskValidation(SubTask subTask) {
        boolean b = false;
        if (!subTaskMap.isEmpty()) {
            for (Task t : getPrioritizedSubTasks()) {
                if (subTask.getStartTime().isAfter(t.getEndTime())) {
                    b = true;
                    break;
                }
            }
        } else b = true;
        return b;
    }
}