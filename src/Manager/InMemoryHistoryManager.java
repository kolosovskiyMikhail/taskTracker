package Manager;

import Tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class InMemoryHistoryManager extends Managers implements HistoryManager{
    List<Task> historyList;
    HashMap<Integer, Node> lMap;
    public Node<Task> head;
    public Node<Task> tail;
    private int size = 0;
    List<Task> tList;

    public InMemoryHistoryManager() {
        lMap = new HashMap<>();
    }

        public void linkLast(Task data) {
            final Node<Task> oldTail = tail;
            final Node<Task> newNode = new Node<>(oldTail, data, null);
            tail = newNode;
            if (oldTail == null) {
                head = newNode;
            } else oldTail.next = newNode;
            size++;
            lMap.put(data.getiD(), newNode);
        }

        public List<Task> getTasks() {
            tList = new ArrayList<>();
            for (Node<Task> x = head; x != null; x = x.next) {
                tList.add(x.data);
            }
            return tList;
        }

        public void removeNode(Task task) {
            final Node<Task> node = lMap.get(task.getiD());
            final Node<Task> next = node.next;
            final Node<Task> prev = node.prev;

            if (prev == null) {
                head = next;
            } else {
                prev.next = next;
                node.prev = null;
            }
            if (next == null) {
                tail = prev;
            } else {
                next.prev = prev;
                node.next = null;
            }
            node.data = null;
            size--;
        }

    @Override
    public void add(Task task) {
        historyList = new ArrayList<>();

        if (lMap.containsKey(task.getiD())) {
            removeNode(task);
        }
        linkLast(task);
        historyList.addAll(getTasks());
    }

    @Override
    public List<Task> getHistory() {
        return historyList;
    }

    @Override
    public void remove(int id) {
        historyList.remove(id);
    }

}
