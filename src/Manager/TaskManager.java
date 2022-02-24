package Manager;

import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface TaskManager {

    void saveTask(Task task);
    ArrayList<Task> giveAllTasks();
    Task giveTaskById(int inputTaskID);
    void removeAllTasks();
    void removeTaskById(int inputTaskID);
    void refreshTask(Task task);

    void saveEpic(Epic epic);
    ArrayList<Epic> giveAllEpics();
    Epic giveEpicById(int inputTaskID);
    void refreshEpic(Epic epic);
    void removeAllEpics();
    void removeEpicById(int inputEpicID);

    void saveSubTask(SubTask subTask);
    ArrayList<SubTask> giveAllSubTasks();
    SubTask giveSubTaskById(int inputSubTaskID);
    void refreshSubTask(SubTask subTask);
    void removeAllSubTasks();
    void removeSubTaskById(int inputSubTaskID);
    ArrayList<SubTask> findSubTaskByEpic(Epic epic);

    List<Task> history();



}
