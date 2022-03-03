package log;

import java.util.LinkedList;

public class LimitedQueue<T> extends LinkedList<T> {
    private final int limit;

    public LimitedQueue(int limit) {
        this.limit = limit;
    }

    public boolean add(T item) {
        super.add(item);
        if (this.size() > this.limit) super.remove();
        return true;
    }
}
