package Manager;

import Http.KVTaskClient;
import Enum.TaskType;
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

 }
