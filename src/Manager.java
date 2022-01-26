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

    public Manager() {
        taskID = 1;
        epicID = 1;
        subEpicID = 1;
        taskMap = new HashMap<>();
        epicMap = new HashMap<>();
        subEpicMap = new HashMap<>();
        epicSubEpic = new HashMap<>();
    }

    //Методы для Заданий
    public void saveTask(Task task) { //Сохранение задачи по идентификатору
        this.task = task;
        taskMap.put(taskID++, task);
    }

    public void giveAllTasks() { //Вывод всех задач
        for (Integer tas : taskMap.keySet()) {
            System.out.println(taskMap.get(tas).toString());
        }
    }

    public void giveTaskById(int inputTaskID) { //Вывод задачи по идентификатору
        System.out.println(taskMap.get(inputTaskID).toString());
    }

    public void removeAllTasks() { //Удаление всех задач
        taskMap.clear();
    }

    public void removeTaskById(int inputTaskID) { //Удаление задачи по идентификатору
        taskMap.remove(inputTaskID);
    }

    public void refreshTask(Task task, int iD) { //Обновление задачи
        taskMap.remove(iD);
        taskMap.put(iD, task);
    }

    //Методы для Эпиков
    public void saveEpic(Epic epic) { //Сохранение эпика по идентификатору
        this.epic = epic;
        epicMap.put(epicID++, epic);
    }

    public void giveAllEpics() { //Получение списка всех эпиков
        for (Integer ep : epicMap.keySet()) {
            System.out.println(epicMap.get(ep).toString());
        }
    }

    public void giveEpicById(int inputTaskID) { //Получение эпика по идентификатору
        System.out.println(epicMap.get(inputTaskID).toString());
    }

    public void refreshEpic(Epic epic, int iD) { //Обновление эпика
        epicMap.remove(iD);
        epicMap.put(iD, new Epic(epic.taskName, epic.taskDescription, this.epic.taskStatus));
    }

    public void removeAllEpics() { //Удаление всех эпиков
        epicMap.clear();
    }

    public void removeEpicById(int inputEpicID) { //Удаление эпика по идентификатору
        epicMap.remove(inputEpicID);
    }

    public void epicStatus() { //Обновление статусов эпиков
        for (Epic eps : epicSubEpic.keySet()) {
            for (SubEpic sub : epicSubEpic.get(eps)) {
                if (sub.taskStatus.equals("NEW") || (!epicSubEpic.containsKey(eps))) {
                    eps.taskStatus = "NEW";
                    eps = new Epic(eps.taskName, eps.taskDescription, eps.taskStatus);
                } else if (sub.taskStatus.equals("DONE")) {
                    eps.taskStatus = "DONE";
                    eps = new Epic(eps.taskName, eps.taskDescription, eps.taskStatus);
                } else {
                    eps.taskStatus = "IN_PROGRESS";
                    eps = new Epic(eps.taskName, eps.taskDescription, eps.taskStatus);
                }
            }
        }
    }

    //Методы для Подзадач
    public void saveSubEpic(SubEpic subEpic) { //Сохранение подзадачи по идентификатору
        this.subEpic = subEpic;
        subEpicMap.put(subEpicID++, subEpic);
    }

    public void giveAllSubEpics() { //Получение списка всех подзадач
        for (Integer ep : subEpicMap.keySet()) {
            System.out.println(subEpicMap.get(ep).toString());
        }
    }

    public void giveSubEpicById(int inputSubEpicID) { //Получение подзадачи по идентификатору
        System.out.println(subEpicMap.get(inputSubEpicID).toString());
    }

    public void refreshSubEpic(SubEpic subEpic, int iD) { //Обновление задачи
        subEpicMap.remove(iD);
        subEpicMap.put(iD, subEpic);
    }

    public void removeAllSubEpics() { //Удаление всех подзадач
        subEpicMap.clear();
    }

    public void removeSubEpicById(int inputSubEpicID) { //Удаление подзадачи по идентификатору
        subEpicMap.remove(inputSubEpicID);
    }

    public void subEpicToEpic(Epic epic, SubEpic subEpic) { //Добавление подзадачи к эпику
        if (epicSubEpic.containsKey(epic)) {
            subEpics = epicSubEpic.get(epic);
            subEpics.add(subEpic);
        } else {
            subEpics = new ArrayList<>();
            subEpics.add(subEpic);
            epicSubEpic.put(epic, subEpics);
        }
    }

    public void findSubEpicByEpic(Epic epic) { //Нахождение списка подзадач по эпику
       // if (epicSubEpic.containsKey(epic)) {
            System.out.println(epic.taskName);
            for (SubEpic sub : epicSubEpic.get(epic)) {
                System.out.println(sub.toString());
            }
       // }
    }
}
