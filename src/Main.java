import Manager.Manager;
import Tasks.Epic;
import Tasks.SubEpic;
import Tasks.Task;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Task taskOne = new Task("Учиться", "Учеба", "NEW");
        Task taskTwo = new Task("Работать", "Работа", "NEW");
        Epic epicOne = new Epic("Эпик1", "Описание Эпика1", "NEW");
        Epic epicTwo = new Epic("Эпик2", "Описание Эпика2", "NEW");

        manager.saveEpic(epicOne);
        manager.saveEpic(epicTwo);
        manager.saveTask(taskOne);
        manager.saveTask(taskTwo);
        manager.giveAllTasks();
        manager.giveAllEpics();
    }
}
