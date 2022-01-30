import Manager.Manager;
import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;

public class Main {
    public static void main(String[] args) {
        Manager manager = new Manager();
        Task taskOne = new Task("Учиться", "Учеба", "NEW");
        Task taskTwo = new Task("Работать", "Работа", "NEW");
        Task taskTwoRef = new Task("Работать", "Работа", "DONE");

        Epic epicOne = new Epic("Эпик1", "Описание Эпика1", "NEW");
        Epic epicTwo = new Epic("Эпик2", "Описание Эпика2", "NEW");
        Epic epicOneRef = new Epic("Эпик11", "Описание Эпика11", "DONE");

        SubTask subTaskOne = new SubTask(1,"Подзадача1", "Описание Подзадачи1", "DONE");
        SubTask subTaskTwo = new SubTask(1,"Подзадача2", "Описание Подзадачи2", "DONE");
        SubTask subTaskThree = new SubTask(2,"Подзадача3", "Описание Подзадачи3", "IN_PROGRESS");
        SubTask subTaskFour = new SubTask(2,"Подзадача4", "Описание Подзадачи4", "NEW");

        manager.saveEpic(epicOne);
        manager.saveEpic(epicTwo);
        manager.giveAllEpics();
        manager.saveSubTask(subTaskOne);
        manager.saveSubTask(subTaskTwo);
        manager.saveSubTask(subTaskThree);
        manager.saveSubTask(subTaskFour);
        manager.findSubTaskByEpic(epicOne);
        manager.findSubTaskByEpic(epicTwo);
        manager.giveAllEpics();
       
    }
}
