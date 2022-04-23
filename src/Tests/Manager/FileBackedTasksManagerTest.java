package Manager;

import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FileBackedTasksManagerTest {
    public FileBackedTasksManager fileManager;
    public FileBackedTasksManager loadTaskManager;

    @Test
    void saveTest() throws IOException, InterruptedException {
        fileManager = new FileBackedTasksManager("tasks.csv");
        Task task = new Task("T1", "D1", "NEW", LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        fileManager.saveTask(task);
        Epic epic1 = new Epic("E1", "D1", "NEW");
        fileManager.saveEpic(epic1);
        SubTask subTask1 = new SubTask(epic1.getiD(), "S1", "D1", "DONE",
                LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        fileManager.saveSubTask(subTask1);
        SubTask subTask2 = new SubTask(epic1.getiD(), "S2", "D2", "DONE",
                LocalDateTime.of(2022, 04, 01, 13, 1), Duration.ofMinutes(60));
        fileManager.saveSubTask(subTask2);
        fileManager.refreshEpic(epic1);
        fileManager.giveTaskById(1);
        fileManager.giveEpicById(2);
        fileManager.giveSubTaskById(3);
        fileManager.giveSubTaskById(4);
        loadTaskManager = fileManager.loadFromFile("tasks.csv");
        assertEquals(task, loadTaskManager.taskMap.get(1));
        assertEquals(epic1, loadTaskManager.epicMap.get(2));
        assertEquals(subTask1, loadTaskManager.subTaskMap.get(3));
        assertEquals(subTask2, loadTaskManager.subTaskMap.get(4));
        assertNotNull(fileManager.history().size());
        assertNotNull(loadTaskManager.history().size());
        assertEquals(fileManager.history().get(0), loadTaskManager.history().get(0));
    }

}