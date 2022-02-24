package Manager;

public class Managers<T> {

    public TaskManager getDefaults() {
        return (TaskManager) this;
    }

    public HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
