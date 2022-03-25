package Manager;

import Tasks.Task;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;

public class InMemoryHistoryManager extends Managers implements HistoryManager {
    HashMap<Integer, Node> lMap;
    public Node<Task> head;
    public Node<Task> tail;
    private int size = 0;
    List<Task> tList;

    public InMemoryHistoryManager() {
        lMap = new HashMap<>();
    }

    private void linkLast(Task data) {
        final Node<Task> oldTail = tail;
        final Node<Task> newNode = new Node<>(oldTail, data, null);
        tail = newNode;
        if (oldTail == null) {
            head = newNode;
        } else oldTail.next = newNode;
        size++;
        lMap.put(data.getiD(), newNode);
    }

    private List<Task> getTasks() {
        tList = new ArrayList<>();
        for (Node<Task> x = head; x != null; x = x.next) {
            tList.add(x.data);
        }
        return tList;
    }

    private void removeNode(Task task) {
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
        if (lMap.containsKey(task.getiD())) {
            removeNode(task);
        }
        linkLast(task);
    }

    @Override
    public List<Task> getHistory() {
        return getTasks();
    }

    @Override
    public void remove(int id) {
        removeNode((Task) lMap.get(id).data);
    }


}
