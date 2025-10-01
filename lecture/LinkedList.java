public class LinkedList {

    public class Node {
        public int data;
        public Node next;

        public Node() {
            this.data = 0;
            this.next = null;
        }

        public Node(int newData) {
            this.data = newData;
            this.next = null;
        }

        public Node(int newData, Node newNext) {
            this.data = newData;
            this.next = newNext;
        }
    }

    private Node head;
    private Node tail;

    public LinkedList() {
        this.tail = null;
        this.head = null;
    }

    public void addLast(int item) {
        if (this.head == null && this.tail == null) {
            Node newNode = new Node(item);
            this.head = newNode;
            this.tail = newNode;
        } else {
            Node newNode = new Node(item);
            this.tail.next = newNode;
            this.tail = newNode;
        }
    }

    public void addFirst(int item) {
        Node newNode = new Node(item);
        if (this.head == null) {
            this.head = newNode;
            this.tail = newNode;
        } else {
            newNode.next = this.head;
            this.head = newNode;
        }
    }

    public void addMiddle(int item, int position) {
        Node newNode = new Node(item);
        if (position <= 0 || head == null) {
            addFirst(item);
        } else {
            Node current = head;
            int index = 0;
            while (current != null && index < position - 1) {
                current = current.next;
                index++;
            }
            if (current == null) {
                addLast(item); // If position is out of bounds, add to the end
            } else {
                newNode.next = current.next;
                current.next = newNode;
                if (newNode.next == null) { // If we added to the end, update tail
                    tail = newNode;
                }
            }
        }
    }

    remove()

    public String toString() {
        StringBuilder result = new StringBuilder();
        Node temp = this.head;

        while (temp != null) {
            result.append(temp.data).append(", ");
            temp = temp.next;
        }

        // Remove the last comma and space if there's any data
        if (result.length() > 0) {
            result.setLength(result.length() - 2);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        LinkedList myList = new LinkedList();

        myList.addLast(25);
        myList.addLast(3);
        myList.addFirst(10);
        myList.addMiddle(15, 1); // Adding 15 at position 1

        System.out.println(myList);
    }
}
