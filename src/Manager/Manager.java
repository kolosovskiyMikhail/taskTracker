package Manager;

import Tasks.Epic;
import Tasks.SubEpic;
import Tasks.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    HashMap<Integer, Task> taskMap;
    HashMap<Integer, Epic> epicMap;
    HashMap<Integer, SubEpic> subEpicMap;
    HashMap<Epic, ArrayList<SubEpic>> epicSubEpic;
    ArrayList<SubEpic> subEpics;
    int taskID;
    int epicID;
    int subEpicID;
    Task task;
    Epic epic;
    SubEpic subEpic;
    String statusOne;
    String statusTwo;
    String statusThree;

    public Manager() {
        taskID = 1;
        epicID = 1;
        subEpicID = 1;
        taskMap = new HashMap<>();
        epicMap = new HashMap<>();
        subEpicMap = new HashMap<>();
        epicSubEpic = new HashMap<>();
        statusOne = "NEW";
        statusTwo = "DONE";
        statusThree = "IN_PROGRESS";
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
        epic.setSubEpicList(null);
    }

    public void removeEpicById(int inputEpicID) { //Удаление эпика по идентификатору
        epicMap.remove(inputEpicID);
    }

    //Методы для Подзадач
    public void saveSubEpic(Epic epic, SubEpic subEpic) { //Сохранение подзадачи по идентификатору
       this.subEpic = subEpic;
        subEpic.setiD(subEpicID++);
        subEpicMap.put(subEpic.getiD(), subEpic);
        if (epicSubEpic.containsKey(epic)) {
            subEpics = epicSubEpic.get(epic);
            subEpics.add(subEpic);
        } else {
            subEpics = new ArrayList<>();
            subEpics.add(subEpic);
            epicSubEpic.put(epic, subEpics);
        }
        epic.setSubEpicList(subEpics);
        subEpic.setEpicId(epic.getiD());
            if (subEpic.getTaskStatus().equals(statusOne) || (subEpics == null)) {
                epic.setTaskStatus(statusOne);
                epicSubEpic.put(epic, subEpics);
            } else if (subEpic.getTaskStatus().equals(statusTwo)) {
                epic.setTaskStatus(statusTwo);
                epicSubEpic.put(epic, subEpics);
            } else {
                epic.setTaskStatus(statusThree);
                epicSubEpic.put(epic, subEpics);
            }
    }

    public void giveAllSubEpics() { //Получение списка всех подзадач
        for (Integer ep : subEpicMap.keySet()) {
            System.out.println(subEpicMap.get(ep).toString());
        }
    }

    public SubEpic giveSubEpicById(int inputSubEpicID) { //Получение подзадачи по идентификатору
        return subEpicMap.get(inputSubEpicID);
    }

    public void refreshSubEpic(SubEpic subEpic) { //Обновление задачи
        subEpicMap.put(subEpic.getiD(), subEpic);
        if (subEpic.getTaskStatus().equals(statusOne) || (subEpics == null)) {
            epic.setTaskStatus(statusOne);
            epicSubEpic.put(epic, subEpics);
        } else if (subEpic.getTaskStatus().equals(statusTwo)) {
            epic.setTaskStatus(statusTwo);
            epicSubEpic.put(epic, subEpics);
        } else {
            epic.setTaskStatus(statusThree);
            epicSubEpic.put(epic, subEpics);
        }
    }

    public void removeAllSubEpics() { //Удаление всех подзадач
        subEpicMap.clear();
        if (subEpic.getTaskStatus().equals(statusOne) || (subEpics == null)) {
            epic.setTaskStatus(statusOne);
            epicSubEpic.put(epic, subEpics);
        } else if (subEpic.getTaskStatus().equals(statusTwo)) {
            epic.setTaskStatus(statusTwo);
            epicSubEpic.put(epic, subEpics);
        } else {
            epic.setTaskStatus(statusThree);
            epicSubEpic.put(epic, subEpics);
        }
    }

    public void removeSubEpicById(int inputSubEpicID) { //Удаление подзадачи по идентификатору
        subEpicMap.remove(inputSubEpicID);
    }

    public void findSubEpicByEpic(Epic epic) { //Нахождение списка подзадач по эпику
        System.out.println(epic.getTaskName());
        subEpics = epicSubEpic.get(epic);
        for (SubEpic sub : subEpics) {
            System.out.println(sub.toString());
        }
    }
}