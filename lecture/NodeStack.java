public class NodeStack<T> {

    // Node class to represent each element in the stack
    private static class Node<T> {
        private T data;
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }
    }

    private Node<T> top; // Top of the stack
    private int size;

    // Push an element onto the stack
    public void push(T data) {

        Node newNode = new Node(data);
        newNode.next = this.top;
        this.top = newNode;
        this.size++;
    }

    // Pop the top element off the stack
    public T pop() {

        if (!isEmpty()) {
            T result = this.top.data;
            this.top = this.top.next;
            this.size--;
            return result;
        }
        return null;
    }

    // Peek at the top element of the stack without removing it
    public T peek() {
        if (isEmpty()) {
            return null;
        } else {
            return this.top.data;
        }

    }

    // Check if the stack is empty
    public boolean isEmpty() {
        return this.size == 0;
    }

    // Size of the stack
    public int size() {
        int count = 0;
        Node<T> current = top;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }

    // Clear the stack
    public void clear() {
        top = null;
    }

    public void swapStacks(LinkedList<T> s1, LinkedList<T> s2) {

    }

    public static void main(String[] args) {
        NodeStack<Integer> stack = new NodeStack<>();
        stack.push(1);
        stack.push(2);
        stack.push(3);

        System.out.println("Peek: " + stack.peek()); // 3
        System.out.println("Size: " + stack.size()); // 3

        System.out.println("Pop: " + stack.pop()); // 3
        System.out.println("Peek after pop: " + stack.peek()); // 2
        System.out.println("Size after pop: " + stack.size()); // 2

        stack.clear();
        System.out.println("Is empty after clear: " + stack.isEmpty()); // true
    }
}