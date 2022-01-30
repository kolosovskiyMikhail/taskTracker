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
    HashMap<Epic, ArrayList<SubTask>> epicSubTask;
    ArrayList<SubTask> subTasks;
    int taskID;
    int epicID;
    int subTaskID;
    Task task;
    Epic epic;
    SubTask subTask;

    public Manager() {
        taskID = 1;
        epicID = 1;
        subTaskID = 1;
        taskMap = new HashMap<>();
        epicMap = new HashMap<>();
        subTaskMap = new HashMap<>();
        epicSubTask = new HashMap<>();
    }


    //Методы для Заданий
    public void saveTask(Task task) { //Сохранение задачи по идентификатору
        this.task = task;
        task.setiD(taskID++);
        taskMap.put(task.getiD(), task);
    }

    public void giveAllTasks() { //Вывод всех задач
        for (Integer tas : taskMap.keySet()) {
            System.out.println(taskMap.get(tas).toString());
        }
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
        this.epic = epic;
        epic.setiD(epicID++);
        epicMap.put(epic.getiD(), epic);
    }

    public void giveAllEpics() { //Получение списка всех эпиков
        for (Integer ep : epicMap.keySet()) {
            System.out.println(epicMap.get(ep).toString());
        }
    }

    public Epic giveEpicById(int inputTaskID) { //Получение эпика по идентификатору
        return epicMap.get(inputTaskID);
    }

    public void refreshEpic(Epic epic) { //Обновление эпика
        epicMap.put(epic.getiD(), epic);
    }

    public void removeAllEpics() { //Удаление всех эпиков
        epicMap.clear();
        epic.setSubTaskList(null);
    }

    public void removeEpicById(int inputEpicID) { //Удаление эпика по идентификатору
        epicMap.remove(inputEpicID);
    }

    public void changeEpicStatus() { //Изенение статуса эпика
        for (SubTask sub : epicMap.get(subTask.getEpicId()).getSubTaskList()) {
            if (sub.getTaskStatus().equals(String.valueOf(Status.NEW)) || (epicMap.get(sub.getEpicId()).getSubTaskList() == null)) {
                epicMap.get(sub.getEpicId()).setTaskStatus(String.valueOf(Status.NEW));
                epicSubTask.put(epicMap.get(sub.getEpicId()), epicMap.get(sub.getEpicId()).getSubTaskList());
            } else if (sub.getTaskStatus().equals(String.valueOf(Status.DONE))) {
                epicMap.get(sub.getEpicId()).setTaskStatus(String.valueOf(Status.DONE));
                epicSubTask.put(epicMap.get(sub.getEpicId()), epicMap.get(sub.getEpicId()).getSubTaskList());
            } else {
                epicMap.get(sub.getEpicId()).setTaskStatus(String.valueOf(Status.IN_PROGRESS));
                epicSubTask.put(epicMap.get(sub.getEpicId()), epicMap.get(sub.getEpicId()).getSubTaskList());
            }
        }
    }

    //Методы для Подзадач
    public void saveSubTask(SubTask subTask) { //Сохранение подзадачи по идентификатору
       this.subTask = subTask;
        subTask.setiD(subTaskID++);
        subTaskMap.put(subTask.getiD(), subTask);
        if (epicSubTask.containsKey(epicMap.get(subTask.getEpicId()))) {
            subTasks = epicSubTask.get(epicMap.get(subTask.getEpicId()));
            subTasks.add(subTask);
        } else {
            subTasks = new ArrayList<>();
            subTasks.add(subTask);
            epicSubTask.put(epicMap.get(subTask.getEpicId()), subTasks);
        }
        epicMap.get(subTask.getEpicId()).setSubTaskList(subTasks);
        changeEpicStatus();
    }

    public void giveAllSubTasks() { //Получение списка всех подзадач
        for (Integer ep : subTaskMap.keySet()) {
            System.out.println(subTaskMap.get(ep).toString());
        }
    }

    public SubTask giveSubTaskById(int inputSubTaskID) { //Получение подзадачи по идентификатору
        return subTaskMap.get(inputSubTaskID);
    }

    public void refreshSubTask(SubTask subTask) { //Обновление подзадачи
        subTaskMap.put(subTask.getiD(), subTask);
        changeEpicStatus();
    }

    public void removeAllSubTasks() { //Удаление всех подзадач
        subTaskMap.clear();
    }

    public void removeSubTaskById(int inputSubTaskID) { //Удаление подзадачи по идентификатору
        subTaskMap.remove(inputSubTaskID);
        changeEpicStatus();
    }

    public void findSubTaskByEpic(Epic epic) { //Нахождение списка подзадач по эпику
        System.out.println(epic.getTaskName());
        subTasks = epicSubTask.get(epic);
        for (SubTask sub : subTasks) {
            System.out.println(sub.toString());
        }
    }
}