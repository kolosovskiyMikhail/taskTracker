package Manager;

import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.SubmissionPublisher;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class InMemoryTaskManagerTest {
    public InMemoryTaskManager manager;

    @BeforeEach
    void beforeEach() {
        manager = new InMemoryTaskManager();
    }

    @Test
    void saveTask() throws IOException, InterruptedException {
        Task task = new Task("T1", "D1", "NEW", LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        manager.saveTask(task);
        int taskId = task.getiD();
        assertEquals(1, taskId);
        assertEquals(task, manager.taskMap.get(taskId));
    }

    @Test
    void giveAllTasks() throws IOException, InterruptedException {
        Task task = new Task("T1", "D1", "NEW", LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        manager.saveTask(task);
        ArrayList<Task> tasksList = manager.giveAllTasks();
        assertNotNull(tasksList);
        assertEquals(task, tasksList.get(0));
        assertEquals(1, tasksList.size());
    }

    @Test
    void giveTaskById() throws IOException, InterruptedException {
        Task task = new Task("T1", "D1", "NEW", LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        manager.saveTask(task);
        assertEquals(task, manager.giveTaskById(1));
        assertNotNull(manager.history);
    }

    @Test
    void removeAllTasks() throws IOException, InterruptedException {
        Task task = new Task("T1", "D1", "NEW", LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        manager.saveTask(task);
        manager.removeAllTasks();
        assertTrue(manager.taskMap.isEmpty());
    }

    @Test
    void removeTaskById() throws IOException, InterruptedException {
        Task task = new Task("T1", "D1", "NEW", LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        manager.saveTask(task);
        manager.removeTaskById(1);
        assertNull(manager.taskMap.get(0));
    }

    @Test
    void refreshTask() throws IOException, InterruptedException {
        Task task = new Task("T1", "D1", "NEW", LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        manager.saveTask(task);
        task.setTaskStatus("DONE");
        manager.refreshTask(task);
        assertEquals(task, manager.taskMap.get(1));
    }

    @Test
    void saveEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("E1", "D1", "NEW");
        manager.saveEpic(epic);
        int epicId = epic.getiD();
        assertEquals(1, epicId);
        assertEquals(epic, manager.epicMap.get(epicId));

    }

    @Test
    void giveAllEpics() throws IOException, InterruptedException {
        Epic epic = new Epic("E1", "D1", "NEW");
        manager.saveEpic(epic);
        ArrayList<Epic> epicList = manager.giveAllEpics();
        assertNotNull(epicList);
        assertEquals(epic, epicList.get(0));
        assertEquals(1, epicList.size());
    }

    @Test
    void giveEpicById() throws IOException, InterruptedException {
        Epic epic = new Epic("E1", "D1", "NEW");
        manager.saveEpic(epic);
        assertEquals(epic, manager.giveEpicById(1));
        assertNotNull(manager.history);
    }

    @Test
    void refreshEpic() throws IOException, InterruptedException {
        Epic epic = new Epic("E1", "D1", "NEW");
        manager.saveEpic(epic);
        epic.setTaskName("E11");
        manager.refreshTask(epic);
        assertEquals(epic, manager.epicMap.get(1));
    }

    @Test
    void removeAllEpics() throws IOException, InterruptedException {
        Epic epic = new Epic("E1", "D1", "NEW");
        manager.saveEpic(epic);
        manager.removeAllEpics();
        assertTrue(manager.epicMap.isEmpty());
    }

    @Test
    void removeEpicById() throws IOException, InterruptedException {
        Epic epic = new Epic("E1", "D1", "NEW");
        manager.saveEpic(epic);
        manager.removeEpicById(1);
        assertNull(manager.epicMap.get(0));
    }

    @Test
    void saveSubTask() throws IOException, InterruptedException {
        Epic epic1 = new Epic("E1", "D1", "NEW");
        manager.saveEpic(epic1);
        Epic epic2 = new Epic("E1", "D1", "NEW");
        manager.saveEpic(epic2);
        SubTask subTask1 = new SubTask(epic1.getiD(), "S1", "D1", "DONE",
                LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        manager.saveSubTask(subTask1);
        SubTask subTask2 = new SubTask(epic1.getiD(), "S2", "D2", "DONE",
                LocalDateTime.of(2022, 04, 01, 13, 1), Duration.ofMinutes(60));
        manager.saveSubTask(subTask2);
        SubTask subTask3 = new SubTask(epic2.getiD(), "S3", "D3", "IN_PROGRESS",
                LocalDateTime.of(2022, 04, 01, 15, 0), Duration.ofMinutes(60));
        manager.saveSubTask(subTask3);
        assertEquals(3, subTask1.getiD());
        assertEquals(4, subTask2.getiD());
        assertEquals(5, subTask3.getiD());
        assertEquals(subTask1, manager.subTaskMap.get(subTask1.getiD()));
        assertEquals(subTask2, manager.subTaskMap.get(subTask2.getiD()));
        assertEquals(subTask3, manager.subTaskMap.get(subTask3.getiD()));
        assertEquals(subTask1, manager.epicMap.get(subTask1.getEpicId()).getSubTaskList().get(0));
        assertEquals(subTask2, manager.epicMap.get(subTask2.getEpicId()).getSubTaskList().get(1));
        assertEquals(subTask3, manager.epicMap.get(subTask3.getEpicId()).getSubTaskList().get(0));
        assertEquals("DONE", epic1.getTaskStatus());
        assertEquals("IN_PROGRESS", epic2.getTaskStatus());
        assertEquals(LocalDateTime.of(2022, 04, 01, 12, 0), epic1.getStartTime());
        assertEquals(LocalDateTime.of(2022, 04, 01, 14, 1), epic1.getEndTime());
        assertEquals(Duration.ofMinutes(120), epic1.getDuration());
        assertEquals(LocalDateTime.of(2022, 04, 01, 15, 0), epic2.getStartTime());
        assertEquals(LocalDateTime.of(2022, 04, 01, 16, 0), epic2.getEndTime());
    }

    @Test
    void giveAllSubTasks() throws IOException, InterruptedException {
        Epic epic1 = new Epic("E1", "D1", "NEW");
        manager.saveEpic(epic1);
        SubTask subTask1 = new SubTask(epic1.getiD(), "S1", "D1", "DONE",
                LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        manager.saveSubTask(subTask1);
        ArrayList<SubTask> subTaskList = manager.giveAllSubTasks();
        assertNotNull(subTaskList);
        assertEquals(subTask1, subTaskList.get(0));
        assertEquals(1, subTaskList.size());
    }

    @Test
    void giveSubTaskById() throws IOException, InterruptedException {
        Epic epic1 = new Epic("E1", "D1", "NEW");
        manager.saveEpic(epic1);
        SubTask subTask1 = new SubTask(epic1.getiD(), "S1", "D1", "DONE",
                LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        manager.saveSubTask(subTask1);
        assertEquals(subTask1, manager.giveSubTaskById(2));
        assertNotNull(manager.history);
    }

    @Test
    void refreshSubTask() throws IOException, InterruptedException {
        Epic epic1 = new Epic("E1", "D1", "NEW");
        manager.saveEpic(epic1);
        SubTask subTask1 = new SubTask(epic1.getiD(), "S1", "D1", "DONE",
                LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        manager.saveSubTask(subTask1);
        subTask1.setTaskStatus("IN_PROGRESS");
        manager.refreshSubTask(subTask1);
        assertEquals(subTask1, manager.subTaskMap.get(2));
        assertEquals("IN_PROGRESS", epic1.getTaskStatus());
    }

    @Test
    void removeAllSubTasks() throws IOException, InterruptedException {
        Epic epic1 = new Epic("E1", "D1", "NEW");
        manager.saveEpic(epic1);
        SubTask subTask1 = new SubTask(epic1.getiD(), "S1", "D1", "DONE",
                LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        manager.saveSubTask(subTask1);
        manager.removeAllSubTasks();
        assertTrue(manager.subTaskMap.isEmpty());
    }

    @Test
    void removeSubTaskById() throws IOException, InterruptedException {
        Epic epic1 = new Epic("E1", "D1", "NEW");
        manager.saveEpic(epic1);
        SubTask subTask1 = new SubTask(epic1.getiD(), "S1", "D1", "DONE",
                LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        manager.saveSubTask(subTask1);
        manager.removeSubTaskById(2);
        assertFalse(epic1.getSubTaskList().contains(subTask1));
        assertNull(manager.subTaskMap.get(0));
    }

    @Test
    void findSubTaskByEpic() throws IOException, InterruptedException {
        Epic epic1 = new Epic("E1", "D1", "NEW");
        manager.saveEpic(epic1);
        SubTask subTask1 = new SubTask(epic1.getiD(), "S1", "D1", "DONE",
                LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        manager.saveSubTask(subTask1);
        ArrayList<SubTask> subTaskList = manager.findSubTaskByEpic(epic1);
        assertTrue(subTaskList.contains(subTask1));
    }

    @Test
    void history() throws IOException, InterruptedException {
        Epic epic1 = new Epic("E1", "D1", "NEW");
        manager.saveEpic(epic1);
        SubTask subTask1 = new SubTask(epic1.getiD(), "S1", "D1", "DONE",
                LocalDateTime.of(2022, 04, 01, 12, 0), Duration.ofMinutes(60));
        manager.saveSubTask(subTask1);
        manager.giveEpicById(1);
        manager.giveSubTaskById(2);
        List<Task> h = manager.history();
        assertTrue(h.contains(epic1));
        assertTrue(h.contains(subTask1));
    }

    @Test
    void getPrioritizedTasks() {
    }

    @Test
    void getPrioritizedSubTasks() {
    }
}