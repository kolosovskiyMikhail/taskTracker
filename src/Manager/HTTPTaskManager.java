package Manager;

import Http.KVTaskClient;
import Enum.TaskType;
import Tasks.SubTask;
import com.google.gson.Gson;

import java.io.IOException;

public class HTTPTaskManager extends FileBackedTasksManager{
    String url;
    public KVTaskClient kvTaskClient;

    public HTTPTaskManager(String url) throws IOException, InterruptedException {
        this.url = url;
        kvTaskClient = new KVTaskClient(url);
    }

    @Override
    public void save() throws IOException, InterruptedException {
        Gson gson = new Gson();
        if (!taskMap.isEmpty()) {
            kvTaskClient.put(String.valueOf(TaskType.TASK), gson.toJson(giveAllTasks()));
        }
        if (!epicMap.isEmpty()) {
            kvTaskClient.put(String.valueOf(TaskType.EPIC), gson.toJson(giveAllEpics()));
        }
        if (!subTaskMap.isEmpty()) {
            kvTaskClient.put(String.valueOf(TaskType.SUBTASK), gson.toJson(giveAllSubTasks()));
        }
    }

    @Override
    public void removeAllTasks() throws IOException, InterruptedException {
        Gson gson = new Gson();
        super.removeAllTasks();
        kvTaskClient.put(String.valueOf(TaskType.TASK), gson.toJson(giveAllTasks()));
    }

    @Override
    public void removeTaskById(int inputTaskID) throws IOException, InterruptedException {
        super.removeTaskById(inputTaskID);
        save();
    }

    @Override
    public void removeAllEpics() throws IOException, InterruptedException {
        Gson gson = new Gson();
        super.removeAllEpics();
        kvTaskClient.put(String.valueOf(TaskType.EPIC), gson.toJson(giveAllEpics()));
    }

    @Override
    public void removeEpicById(int inputEpicID) throws IOException, InterruptedException {
        super.removeEpicById(inputEpicID);
        save();
    }

    @Override
    public void removeAllSubTasks() throws IOException, InterruptedException {
        Gson gson = new Gson();
        super.removeAllSubTasks();
        kvTaskClient.put(String.valueOf(TaskType.SUBTASK), gson.toJson(giveAllSubTasks()));
    }

    @Override
    public void removeSubTaskById(int inputSubTaskID) throws IOException, InterruptedException {
        super.removeSubTaskById(inputSubTaskID);
        save();
    }

    @Override
    public void saveSubTask(SubTask subTask) throws IOException, InterruptedException {
        super.saveSubTask(subTask);
        save();
    }

 }
