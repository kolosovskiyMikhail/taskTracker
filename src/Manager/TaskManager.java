package Manager;

import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;

public interface TaskManager {

    void saveTask(Task task) throws IOException, InterruptedException;
    ArrayList<Task> giveAllTasks();
    Task giveTaskById(int inputTaskID) throws IOException, InterruptedException;
    void removeAllTasks() throws IOException, InterruptedException;
    void removeTaskById(int inputTaskID) throws IOException, InterruptedException;
    void refreshTask(Task task);
    TreeSet<Task> getPrioritizedTasks();

    void saveEpic(Epic epic) throws IOException, InterruptedException;
    ArrayList<Epic> giveAllEpics();
    Epic giveEpicById(int inputTaskID) throws IOException, InterruptedException;
    void refreshEpic(Epic epic) throws IOException, InterruptedException;
    void removeAllEpics() throws IOException, InterruptedException;
    void removeEpicById(int inputEpicID) throws IOException, InterruptedException;

    void saveSubTask(SubTask subTask) throws IOException, InterruptedException;
    ArrayList<SubTask> giveAllSubTasks();
    SubTask giveSubTaskById(int inputSubTaskID) throws IOException, InterruptedException;
    void refreshSubTask(SubTask subTask);
    void removeAllSubTasks() throws IOException, InterruptedException;
    void removeSubTaskById(int inputSubTaskID) throws IOException, InterruptedException;
    ArrayList<SubTask> findSubTaskByEpic(Epic epic);
    TreeSet<SubTask> getPrioritizedSubTasks();

    List<Task> history();

}
