package Manager;

public class Managers<T> {

    public TaskManager getDefaults() {
        return (TaskManager) this;
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
