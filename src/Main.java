import Manager.InMemoryHistoryManager;
import Manager.InMemoryTaskManager;
import Manager.Managers;
import Manager.TaskManager;
import Tasks.Epic;
import Tasks.SubTask;
import Tasks.Task;

public class Main {
    public static void main(String[] args) {
        Managers<TaskManager> manage = new InMemoryTaskManager();
        InMemoryHistoryManager hist = new InMemoryHistoryManager();

        Task taskOne = new Task("ЗД1", "О1", "NEW");
        Task taskTwo = new Task("ЗД2", "О2", "NEW");
        Task taskTwoRef = new Task("Работать", "Работа", "DONE");

        Epic epicOne = new Epic("ЭП1", "О1", "NEW");
        Epic epicTwo = new Epic("ЭП2", "О2", "NEW");
        Epic epicOneRef = new Epic("Эпик11", "Описание Эпика11", "DONE");

        /*SubTask subTaskOne = new SubTask(1,"Подзадача1", "Описание Подзадачи1", "DONE");
        SubTask subTaskTwo = new SubTask(1,"Подзадача2", "Описание Подзадачи2", "DONE");
        SubTask subTaskThree = new SubTask(2,"Подзадача3", "Описание Подзадачи3", "IN_PROGRESS");
        SubTask subTaskFour = new SubTask(2,"Подзадача4", "Описание Подзадачи4", "NEW");*/

        manage.getDefaults().saveTask(taskOne);
        manage.getDefaults().saveTask(taskTwo);
        manage.getDefaults().saveEpic(epicOne);
        manage.getDefaults().saveEpic(epicTwo);
        /*manage.getDefaults().saveSubTask(subTaskOne);
        manage.getDefaults().saveSubTask(subTaskTwo);
        manage.getDefaults().saveSubTask(subTaskThree);
        manage.getDefaults().saveSubTask(subTaskFour);*/
        manage.getDefaults().giveTaskById(1);
        manage.getDefaults().giveTaskById(2);
        manage.getDefaults().giveEpicById(3);
        manage.getDefaults().giveTaskById(1);
        /*System.out.println(manage.getDefaults().history());
        manage.getDefaults().giveTaskById(1);
        System.out.println(manage.getDefaults().history());*/
        System.out.println(manage.getDefaults().history());
       
    }
}
