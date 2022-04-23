package Http;


import Manager.HTTPTaskManager;
import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class HttpTaskServer {
    HTTPTaskManager manager;
    HttpServer httpServer;
    private final int PORT = 8080;
    private final String PATH = "/tasks";
    private static final Charset DEFAULT_CHARSET = StandardCharsets.UTF_8;

    public HttpTaskServer(HTTPTaskManager manager) throws IOException {
        this.manager = manager;
        httpServer = HttpServer.create(new InetSocketAddress("localhost", PORT), 0);
        httpServer.createContext(PATH, exchange -> {
            String method = exchange.getRequestMethod();
            Gson gson = new Gson();
            int code = 0;
            String response = "";
            String path = exchange.getRequestURI().getPath();
            String[] pathArray = path.split("/");
            String query = exchange.getRequestURI().getRawQuery();
            if ((pathArray[1] == "tasks") && (pathArray.length == 2) && (method == "GET")) {
                response = gson.toJson(manager.getPrioritizedTasks());
            }
            switch (pathArray[2]) {
                case "task" :
                    switch (method) {
                        case "POST":
                            InputStream inputStream = exchange.getRequestBody();
                            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            if (!(query == null) && (query.contains("id"))) {
                                manager.refreshTask(gson.fromJson(body, Task.class));
                                response = "Задача успешно обновлена";
                                code = 201;
                            } else if (query == null) {
                                try {
                                    manager.saveTask(gson.fromJson(body, Task.class));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                response = body;
                                code = 201;
                            } else code = 400;
                            break;
                        case "GET":
                            if (!(query == null) && (query.contains("id"))) {
                                int id = Integer.parseInt(query.substring(3));
                                try {
                                    response = gson.toJson(manager.giveTaskById(id));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                code = 200;
                            } else if (query == null) {
                                response = gson.toJson(manager.giveAllTasks());
                                code = 200;
                            } else code = 400;
                            break;
                        case "DELETE":
                            if (!(query == null) && (query.contains("id"))) {
                                int id = Integer.parseInt(query.substring(3));
                                try {
                                    manager.removeTaskById(id);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                response = "Задача " + id + " успешно удалена";
                                code = 200;
                            } else if (query == null) {
                                try {
                                    manager.removeAllTasks();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                response = "Все задачи успешно удалены";
                                code = 200;
                            } else code = 400;
                            break;
                    }
                    break;
                case "epic" :
                    switch (method) {
                        case "POST":
                            InputStream inputStream = exchange.getRequestBody();
                            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            if (!(query == null) && (query.contains("id"))) {
                                try {
                                    manager.refreshEpic(gson.fromJson(body, Epic.class));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                response = "Эпик успешно обновлен";
                                code = 201;
                            } else if (query == null) {
                                try {
                                    manager.saveEpic(gson.fromJson(body, Epic.class));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                response = body;
                                code = 201;
                            } else code = 400;
                            break;
                        case "GET":
                            if (!(query == null) && (query.contains("id"))) {
                                int id = Integer.parseInt(query.substring(3));
                                try {
                                    response = gson.toJson(manager.giveEpicById(id));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                code = 200;
                            } else if (query == null) {
                                response = gson.toJson(manager.giveAllEpics());
                                code = 200;
                            } else code = 400;
                            break;
                        case "DELETE":
                            if (!(query == null) && (query.contains("id"))) {
                                int id = Integer.parseInt(query.substring(3));
                                try {
                                    manager.removeEpicById(id);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                response = "Эпик " + id + " успешно удален";
                                code = 200;
                            } else if (query == null) {
                                try {
                                    manager.removeAllEpics();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                response = "Все эпики успешно удалены";
                                code = 200;
                            } else code = 400;
                            break;
                    }
                    break;
                case "subtask" :
                    switch (method) {
                        case "POST":
                            InputStream inputStream = exchange.getRequestBody();
                            String body = new String(inputStream.readAllBytes(), DEFAULT_CHARSET);
                            if (!(query == null) && (query.contains("id"))) {
                                manager.refreshSubTask(gson.fromJson(body, SubTask.class));
                                response = "Подзадача успешно обновлена";
                                code = 201;
                            } else if (query == null) {
                                try {
                                    manager.saveSubTask(gson.fromJson(body, SubTask.class));
                                    code = 200;
                                    response = body;
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            } else code = 400;
                            exchange.sendResponseHeaders(code, 0);
                            try (OutputStream os = exchange.getResponseBody()) {
                                os.write(response.getBytes());
                            }
                            break;
                        case "GET":
                            if (!(query == null) && (query.contains("id"))) {
                                int id = Integer.parseInt(query.substring(3));
                                try {
                                    response = gson.toJson(manager.giveSubTaskById(id));
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                code = 200;
                            } else if (query == null) {
                                response = gson.toJson(manager.giveAllSubTasks());
                                code = 200;
                            } else code = 400;
                            exchange.sendResponseHeaders(code, 0);
                            try (OutputStream os = exchange.getResponseBody()) {
                                os.write(response.getBytes());
                            }
                            break;
                        case "DELETE":
                            if (!(query == null) && (query.contains("id"))) {
                                int id = Integer.parseInt(query.substring(3));
                                try {
                                    manager.removeSubTaskById(id);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                response = "Подзадача " + id + " успешно удалена";
                                code = 200;
                            } else if (query == null) {
                                try {
                                    manager.removeAllSubTasks();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                                response = "Все подзадачи успешно удалены";
                                code = 200;
                            } else code = 400;
                            exchange.sendResponseHeaders(code, 0);
                            try (OutputStream os = exchange.getResponseBody()) {
                                os.write(response.getBytes());
                            }
                            break;
                    }
                    if ((pathArray[3] == "epic") && (method == "GET")) {
                        if (!(query == null) && (query.contains("id"))) {
                            int id = Integer.parseInt(query.substring(3));
                            try {
                                response = gson.toJson(manager.findSubTaskByEpic(manager.giveEpicById(id)));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            code = 200;
                        } else code = 400;
                    }
                    break;
                case "history" :
                    switch (method) {
                        case "GET" :
                            response = gson.toJson(manager.history());
                            code = 200;
                            break;
                    }
                    break;
            }
            exchange.sendResponseHeaders(code, 0);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(response.getBytes());
            }
        });
    }

    public void startServer() {
        httpServer.start();
        System.out.println("Сервер запущен на порту " + PORT);
    }

    public void stopServer() {
        httpServer.stop(0);
    }
}
