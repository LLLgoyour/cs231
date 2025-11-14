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

        // Get the left and right child indices
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

        /*
        // Continue heapifying while the left child index is within the heap size
        while(left <= size){
            Integer maxIndex = left;

            // Determine the index of the larger child
            if (right < this.size && heap[right] > heap[left])
                maxIndex = right;

            // If the current node is less than the larger child, swap them
            if (heap[position] < heap[maxIndex]){
                swap(position, maxIndex);
                position = maxIndex;
                left = leftChild(position);
                right = rightChild(position); 
            }
            else{
                break;
            }
        }
        */
    }

    // remove an element from the max heap
    public int extractMax(int position) {
        // store the maximum element
        int popped = heap[position]; // if position == 1: store the root node
        heap[position] = heap[size--]; // if position == 1: replace the root node with the last node
        heap[size + 1] = popped; // if position == 1: replace the last node with the popped node (root node)

        maxHeapify(position);
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

    private boolean isLeaf(int position) {
        if (position >= (size / 2) && position <= size) {             
            return true;         
        }        
        return false;     
    }
}