public class MaxHeap {
    private int[] heap;
    private int size;
    private int maxsize;

    public MaxHeap(int maxsize) {
        this.maxsize = maxsize;
        this.size = 0;
        heap = new int[this.maxsize + 1];
        heap[0] = Integer.MAX_VALUE;
    }

    /**
     * return the position of parent
     * 
     * @return the position of parent
     */
    private int parent(int position) {
        return position / 2;
    }

    public void swap(int curr, int parent) {
        int tmp = heap[curr];
        heap[curr] = heap[parent];
        heap[parent] = tmp;
    }

    public int peek() {
        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }
        return heap[1];
    }

    public void maxHeapify() {
        maxHeapify(1);
    }

    /**
     * Heapify the subtree rooted at the given position.
     */
    public void maxHeapify(int position) {
        if (position <= 0 || position > size) {
            return;
        }

        int left = leftChild(position);
        int right = rightChild(position);
        int largest = position;

        if (left <= size && heap[left] > heap[largest]) {
            largest = left;
        }

        if (right <= size && heap[right] > heap[largest]) {
            largest = right;
        }

        if (largest != position) {
            swap(position, largest);
            maxHeapify(largest);
        }
    }

    // remove an element from the max heap
    public int extractMax() {
        // store the maximum element
        int popped = heap[1];
        heap[1] = heap[size--];
        heap[size + 1] = popped;

        maxHeapify(1);
        return popped;

    }

    /**
     * return left child
     * 
     * @return left child
     */
    private int leftChild(int position) {
        return (2 * position);
    }

    /**
     * return right child
     * 
     * @return right child
     */
    private int rightChild(int position) {
        return (2 * position + 1);
    }

    public void insert(int element) {
        heap[++size] = element;

        int current = size;
        while (heap[current] > heap[parent(current)]) {
            swap(current, parent(current));
            current = parent(current);
        }
    }
}