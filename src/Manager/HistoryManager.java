package Manager;

import Tasks.Task;

import java.util.List;

public interface HistoryManager {

    int viewCount = 10;
    void add(Task task);
    List<Task> getHistory();
}
