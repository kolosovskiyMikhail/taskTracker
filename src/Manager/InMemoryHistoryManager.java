package Manager;

import Tasks.Epic;
import Tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager extends Managers implements HistoryManager{
    List<Task> historyList;

    public InMemoryHistoryManager() {
        historyList = new ArrayList<>();
    }

    @Override
    public void add(Task task) {
        if (historyList.size() < viewCount) {
            historyList.add(task);
        } else {
            historyList.remove(0);
            historyList.add(task);
        }
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }
}
