package Manager;

import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public interface TaskManager {

    void saveTask(Task task) throws IOException;
    ArrayList<Task> giveAllTasks();
    Task giveTaskById(int inputTaskID) throws IOException;
    void removeAllTasks();
    void removeTaskById(int inputTaskID);
    void refreshTask(Task task);
    TreeSet<Task> getPrioritizedTasks();

    void saveEpic(Epic epic) throws IOException;
    ArrayList<Epic> giveAllEpics();
    Epic giveEpicById(int inputTaskID) throws IOException;
    void refreshEpic(Epic epic) throws IOException;
    void removeAllEpics();
    void removeEpicById(int inputEpicID) throws IOException;

    void saveSubTask(SubTask subTask) throws IOException;
    ArrayList<SubTask> giveAllSubTasks();
    SubTask giveSubTaskById(int inputSubTaskID) throws IOException;
    void refreshSubTask(SubTask subTask);
    void removeAllSubTasks();
    void removeSubTaskById(int inputSubTaskID) throws IOException;
    ArrayList<SubTask> findSubTaskByEpic(Epic epic);
    TreeSet<SubTask> getPrioritizedSubTasks();

    List<Task> history();

}
