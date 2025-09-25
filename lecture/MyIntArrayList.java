package lecture;

public class MyIntArrayList {
    private int[] elements; // backing array
    private int size; // number of elements
    private static final int DEFAULT_CAPACITY = 10;

    // Constructor
    public MyIntArrayList() {
        elements = new int[DEFAULT_CAPACITY];
        size = 0;
    }

    // Add element at the end
    public void add(int value) {
        ensureCapacity();
        elements[size++] = value;
    }

    // Get element at index
    public int get(int index) {
        checkIndex(index);
        return elements[index];
    }

    // Remove element at index
    public void remove(int index) {
        checkIndex(index);
        for (int i = index; i < size - 1; i++) {
            elements[i] = elements[i + 1];
        }
        size--; // last element is ignored now
    }

    // Return current size
    public int size() {
        return size;
    }

    // Check if empty
    public boolean isEmpty() {
        return size == 0;
    }

    // Private helper: grow array if needed
    private void ensureCapacity() {
        if (size == elements.length) {
            int[] newElements = new int[elements.length * 2];
            for (int i = 0; i < size; i++) {
                newElements[i] = elements[i];
            }
            elements = newElements;
        }
    }

    // Private helper: check index validity
    private void checkIndex(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index " + index + " out of bounds for size " + size);
        }
    }

    // For demonstration: print elements
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            sb.append(elements[i]);
            if (i < size - 1)
                sb.append(", ");
        }
        sb.append("]");
        return sb.toString();
    }

    // Main to test
    public static void main(String[] args) {
        MyIntArrayList list = new MyIntArrayList();
        list.add(10);
        list.add(20);
        list.add(30);
        System.out.println("List: " + list);

        list.remove(1);
        System.out.println("After removing index 1: " + list);

        System.out.println("Element at index 1: " + list.get(1));
        System.out.println("Size: " + list.size());
    }
}