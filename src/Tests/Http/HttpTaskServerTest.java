package Http;

import Manager.HTTPTaskManager;
import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;
import Enum.TaskType;
import com.google.gson.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class HttpTaskServerTest {
    public HttpTaskServer server;
    public HTTPTaskManager manager;
    KVServer kv;
    HttpClient client;

    @BeforeEach
    public void create() throws IOException, InterruptedException {
        kv = new KVServer();
        kv.start();
        manager = new HTTPTaskManager("http://localhost:8078");
        server =  new HttpTaskServer(manager);
        server.startServer();
        client = HttpClient.newHttpClient();
    }

    @AfterEach
    public void stop() {
        kv.stop();
        server.stopServer();
    }

    @Test
    public void httpTestTasks() throws IOException, InterruptedException {
        Task task = new Task("T1", "D1", "NEW", LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));

        URI url = URI.create("http://localhost:8080/tasks/task/");
        Gson gson = new Gson();

        String taskJson = gson.toJson(task);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestPOST = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(taskJson)).build();
        HttpResponse<String> responsePOST = client.send(requestPOST, HttpResponse.BodyHandlers.ofString());
        assertEquals(taskJson, responsePOST.body());

        HttpRequest requestAll = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> responseAll = client.send(requestAll, HttpResponse.BodyHandlers.ofString());
        assertEquals(gson.toJson(server.manager.giveAllTasks()), responseAll.body());

        URI urlId = URI.create("http://localhost:8080/tasks/task/?id=1");
        HttpRequest requestId = HttpRequest.newBuilder().uri(urlId).GET().build();
        HttpResponse<String> responseId = client.send(requestId, HttpResponse.BodyHandlers.ofString());
        assertEquals(server.manager.giveTaskById(1), gson.fromJson(responseId.body(), Task.class));

        HttpRequest requestDelId = HttpRequest.newBuilder().uri(urlId).DELETE().build();
        HttpResponse<String> responseDelId = client.send(requestDelId, HttpResponse.BodyHandlers.ofString());
        assertEquals("Задача 1 успешно удалена", responseDelId.body());

        HttpRequest requestDel = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> responseDel = client.send(requestDel, HttpResponse.BodyHandlers.ofString());
        assertEquals("Все задачи успешно удалены", responseDel.body());
    }

    @Test
    public void httpTestEpics() throws IOException, InterruptedException {
        Epic epic = new Epic("E1", "D1", "NEW");

        URI url = URI.create("http://localhost:8080/tasks/epic/");
        Gson gson = new Gson();

        String epicJson = gson.toJson(epic);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest requestPOST = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(epicJson)).build();
        HttpResponse<String> responsePOST = client.send(requestPOST, HttpResponse.BodyHandlers.ofString());
        assertEquals(epicJson, responsePOST.body());


        HttpRequest requestAll = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> responseAll = client.send(requestAll, HttpResponse.BodyHandlers.ofString());
        assertEquals(gson.toJson(server.manager.giveAllEpics()), responseAll.body());

        URI urlId = URI.create("http://localhost:8080/tasks/epic/?id=1");
        HttpRequest requestId = HttpRequest.newBuilder().uri(urlId).GET().build();
        HttpResponse<String> responseId = client.send(requestId, HttpResponse.BodyHandlers.ofString());
        assertEquals(server.manager.giveEpicById(1), gson.fromJson(responseId.body(), Epic.class));

        HttpRequest requestDelId = HttpRequest.newBuilder().uri(urlId).DELETE().build();
        HttpResponse<String> responseDelId = client.send(requestDelId, HttpResponse.BodyHandlers.ofString());
        assertEquals("Эпик 1 успешно удален", responseDelId.body());

        HttpRequest requestDel = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> responseDel = client.send(requestDel, HttpResponse.BodyHandlers.ofString());
        assertEquals("Все эпики успешно удалены", responseDel.body());
    }

    @Test
    public void httpTestSubTasks() throws IOException, InterruptedException {

        Epic epic = new Epic("E1", "D1", "NEW");
        server.manager.saveEpic(epic);
        SubTask subTask = new SubTask(1, "S1", "D1", "NEW", LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));

        URI url = URI.create("http://localhost:8080/tasks/subtask");
        Gson gson = new Gson();

        String subTaskJson = gson.toJson(subTask);
        HttpRequest requestPOST = HttpRequest.newBuilder().uri(url).POST(HttpRequest.BodyPublishers.ofString(subTaskJson)).build();
        HttpResponse<String> responsePOST = client.send(requestPOST, HttpResponse.BodyHandlers.ofString());
        assertEquals(subTaskJson, responsePOST.body());

        HttpRequest requestAll = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> responseAll = client.send(requestAll, HttpResponse.BodyHandlers.ofString());
        assertEquals(gson.toJson(server.manager.giveAllSubTasks()), responseAll.body());

        URI urlId = URI.create("http://localhost:8080/tasks/subtask/?id=2");
        HttpRequest requestId = HttpRequest.newBuilder().uri(urlId).GET().build();
        HttpResponse<String> responseId = client.send(requestId, HttpResponse.BodyHandlers.ofString());
        assertEquals(server.manager.giveSubTaskById(2), gson.fromJson(responseId.body(), SubTask.class));

        HttpRequest requestDelId = HttpRequest.newBuilder().uri(urlId).DELETE().build();
        HttpResponse<String> responseDelId = client.send(requestDelId, HttpResponse.BodyHandlers.ofString());
        assertEquals("Подзадача 2 успешно удалена", responseDelId.body());

        HttpRequest requestDel = HttpRequest.newBuilder().uri(url).DELETE().build();
        HttpResponse<String> responseDel = client.send(requestDel, HttpResponse.BodyHandlers.ofString());
        assertEquals("Все подзадачи успешно удалены", responseDel.body());

    }

    @Test
    public void httpTestHistory() throws IOException, InterruptedException {
        Task task = new Task("T1", "D1", "NEW", LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        Epic epic = new Epic("E1", "D1", "NEW");
        SubTask subTask = new SubTask(2, "S1", "D1", "NEW", LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        server.manager.saveTask(task);
        server.manager.saveEpic(epic);
        server.manager.saveSubTask(subTask);
        server.manager.giveTaskById(1);
        server.manager.giveEpicById(2);
        server.manager.giveSubTaskById(3);
        Gson gson = new Gson();

        URI url = URI.create("http://localhost:8080/tasks/history/");
        HttpRequest request = HttpRequest.newBuilder().uri(url).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        assertEquals(gson.toJson(manager.history()), response.body());
    }
}