import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> implements PriorityQueue<T> {
    private Comparator<T> comparator;
    private ArrayList<T> heap;

    public Heap(Comparator<T> comparator, boolean maxHeap) {
        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }
        if (maxHeap) {
            // reverse the comparator for max-heap behavior
            this.comparator = (a, b) -> comparator.compare(b, a);
        } else {
            this.comparator = comparator;
        }
        this.heap = new ArrayList<>();
    }

    public Heap(Comparator<T> comparator) {
        this(comparator, false);
    }

    private void swap(int idx1, int idx2) {
        T tmp = this.heap.get(idx1);
        this.heap.set(idx1, this.heap.get(idx2));
        this.heap.set(idx2, tmp);
    }

    private int getParentIdx(int idx) {
        if (idx == 0)
            return -1;
        return (idx - 1) / 2;
    }

    private int getLeftChildIdx(int idx) {
        return 2 * idx + 1;
    }

    private int getRightChildIdx(int idx) {
        return 2 * idx + 2;
    }

    private void bubbleUp(int idx) {
        while (idx > 0) {
            int parent = getParentIdx(idx);
            if (parent < 0)
                break;
            T curr = heap.get(idx);
            T par = heap.get(parent);
            // if current has higher priority (smaller according to comparator), swap
            if (this.comparator.compare(curr, par) < 0) {
                swap(idx, parent);
                idx = parent;
            } else {
                break;
            }
        }
    }

    private void bubbleDown(int idx) {
        int size = this.size();
        while (true) {
            int left = getLeftChildIdx(idx);
            int right = getRightChildIdx(idx);
            int smallest = idx;

            if (left < size && this.comparator.compare(heap.get(left), heap.get(smallest)) < 0) {
                smallest = left;
            }
            if (right < size && this.comparator.compare(heap.get(right), heap.get(smallest)) < 0) {
                smallest = right;
            }

            if (smallest != idx) {
                swap(idx, smallest);
                idx = smallest;
            } else {
                break;
            }
        }
    }

    @Override
    public int size() {
        return this.heap.size();
    }

    @Override
    public T peek() {
        if (this.size() == 0)
            return null;
        return this.heap.get(0);
    }

    @Override
    public void offer(T item) {
        this.heap.add(item);
        bubbleUp(this.size() - 1);
    }

    @Override
    public T poll() {
        int sz = this.size();
        if (sz == 0)
            return null;
        if (sz == 1)
            return this.heap.remove(0);

        T root = this.heap.get(0);
        T last = this.heap.remove(sz - 1);
        this.heap.set(0, last);
        bubbleDown(0);
        return root;
    }

    @Override
    public void updatePriority(T item) {
        // linear search for the item, then bubbleUp and bubbleDown to restore heap
        int idx = -1;
        for (int i = 0; i < this.size(); i++) {
            if (this.heap.get(i) == null) {
                if (item == null) {
                    idx = i;
                    break;
                }
            } else if (this.heap.get(i).equals(item)) {
                idx = i;
                break;
            }
        }
        if (idx == -1)
            return; // item not found

        // try both directions; one of them (or none) will do nothing
        bubbleUp(idx);
        bubbleDown(idx);
    }

    public String toString() {
        int depth = 0;
        return toString(0, depth);
    }

    private String toString(int idx, int depth) {
        if (idx >= this.size()) {
            return "";
        }
        String left = toString(getLeftChildIdx(idx), depth + 1);
        String right = toString(getRightChildIdx(idx), depth + 1);

        String myself = "\t".repeat(depth) + this.heap.get(idx) + "\n";
        return right + myself + left;
    }

}
