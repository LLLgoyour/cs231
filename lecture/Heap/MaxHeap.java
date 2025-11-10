public class MaxHeap{
    private int[] heap;
    private int size;
    private int maxsize;

    public MaxHeap(int maxsiz) {
        this.maxsize = maxsize;
        this.size = 0;
        heap = new int[this.maxsize+1];
        heap[0] = integer.MAX_VALUE;
    }
}