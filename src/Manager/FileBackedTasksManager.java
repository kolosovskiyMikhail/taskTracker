package Manager;

import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager implements TaskManager {
    String fileName;

    public FileBackedTasksManager(String fileName) {
        this.fileName = fileName;
    }

    /*public static void main(String[] args) throws IOException {
        loadFromFile("tasks.csv");
    }*/

    @Override
    public void saveTask(Task task) throws IOException {
        super.saveTask(task);
        save();
    }

    @Override
    public void saveEpic(Epic epic) throws IOException {
        super.saveEpic(epic);
        save();
    }

    @Override
    public Task giveTaskById(int inputTaskID) throws IOException {
        history.add(taskMap.get(inputTaskID));
        save();
        return super.giveTaskById(inputTaskID);
    }

    @Override
    public Epic giveEpicById(int inputTaskID) throws IOException {
        history.add(epicMap.get(inputTaskID));
        save();
        return super.giveEpicById(inputTaskID);
    }

    @Override
    public void refreshEpic(Epic epic) throws IOException {
        super.refreshEpic(epic);
        save();
    }

    @Override
    public SubTask giveSubTaskById(int inputSubTaskID) throws IOException {
        history.add(subTaskMap.get(inputSubTaskID));
        save();
        return super.giveSubTaskById(inputSubTaskID);
    }

    static String historyToString(HistoryManager manager) {
        String[] tasksArray = new String[manager.getHistory().size()];
        for (int i = 0; i < manager.getHistory().size(); i++) {
            tasksArray[i] = String.valueOf(manager.getHistory().get(i).getiD());
        }
        return String.join(",", tasksArray);
    }

    static List<Integer> historyFromString(String value) {
        List<Integer> historyList = new ArrayList<>();
        String[] split = value.split(",");
        for (String s : split) {
            historyList.add(Integer.valueOf(s));
        }
        return historyList;
    }

    public void save() throws IOException {
        try (Writer fileWriter = new FileWriter(fileName)) {
            fileWriter.write("id,type,name,status,description,startTime,endTime,duration,epic\n");
            if (!taskMap.isEmpty()) {
                for (Integer t : taskMap.keySet()) {
                    fileWriter.write(taskMap.get(t).toString());
                    fileWriter.write("\n");
                }
            }
            if (!epicMap.isEmpty()) {
                for (Integer t : epicMap.keySet()) {
                    fileWriter.write(epicMap.get(t).toString());
                    fileWriter.write("\n");
                }
            }
            if (!subTaskMap.isEmpty()) {
                for (Integer t : subTaskMap.keySet()) {
                    fileWriter.write(subTaskMap.get(t).toString());
                    fileWriter.write("\n");
                }

            }
            fileWriter.write("\n");
            fileWriter.write(historyToString(history));
        } catch (ManagerSaveException e) {
            e.getMessage();
        }
    }

    public FileBackedTasksManager loadFromFile(String fileName) throws IOException {
        FileBackedTasksManager fb = new FileBackedTasksManager(fileName);
        List<String> lines = new ArrayList<>();
        try (BufferedReader fileReader = new BufferedReader(new FileReader(fileName))) {
            while (fileReader.ready()) {
                lines.add(fileReader.readLine());
            }
            for (int i = 1; i < lines.size(); i++) {
                if (!lines.get(i).isBlank() /*|| lines.get(lines.size()-1).isBlank()*/) {
                    if (lines.get(i).split(",")[1].equals(String.valueOf(TaskType.TASK))) {
                        Task task = new Task(lines.get(i).split(",")[2], lines.get(i).split(",")[4],
                                lines.get(i).split(",")[3], LocalDateTime.parse(lines.get(i).split(",")[5]),
                                Duration.parse(lines.get(i).split(",")[7]));
                        fb.taskMap.put(Integer.valueOf(lines.get(i).split(",")[0]), task);
                        task.setiD(Integer.parseInt(lines.get(i).split(",")[0]));
                    } else if (lines.get(i).split(",")[1].equals(String.valueOf(TaskType.EPIC))) {
                        Epic epic = new Epic(lines.get(i).split(",")[2], lines.get(i).split(",")[4], lines.get(i).split(",")[3]);
                        fb.epicMap.put(Integer.valueOf(lines.get(i).split(",")[0]), epic);
                        epic.setiD(Integer.parseInt(lines.get(i).split(",")[0]));
                    } else if (lines.get(i).split(",")[1].equals(String.valueOf(TaskType.SUBTASK))) {
                        SubTask subTask = new SubTask(Integer.valueOf(lines.get(i).split(",")[8]), lines.get(i).split(",")[2],
                                lines.get(i).split(",")[4], lines.get(i).split(",")[3], LocalDateTime.parse(lines.get(i).split(",")[5]),
                                Duration.parse(lines.get(i).split(",")[7]));
                        fb.subTaskMap.put(Integer.valueOf(lines.get(i).split(",")[0]), subTask);
                        subTask.setiD(Integer.parseInt(lines.get(i).split(",")[0]));
                    }
                } else {
                    for (Integer h : historyFromString(lines.get(i + 1))) {
                        if (fb.taskMap.containsKey(h)) {
                            fb.history.add(fb.taskMap.get(h));
                        } else if (fb.epicMap.containsKey(h)) {
                            fb.history.add(fb.epicMap.get(h));
                        } else if (fb.subTaskMap.containsKey(h)) {
                            fb.history.add(fb.subTaskMap.get(h));
                        }
                    }
                }
            }
        } catch (ManagerSaveException e) {
            e.getMessage();
        }
        return fb;
    }
}
