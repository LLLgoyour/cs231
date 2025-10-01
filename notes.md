## week 3

```java
String name = "Gru";
String name2 = new String("Naser"); // New reference in the heap, allocate memory
String myName = "Gru"; // Same reference as name in the string pool
if (name == name2) {
    // false because they are different references
    // name points to the string pool, name2 points to the heap
}
if (name == myName) {
    // true because they point to the same reference in the string pool
}
if (name.equals(name2)) {
    // false because they have different values
}
```

### grids

```java
int[][] grid = new int[3][4]; // 3 rows, 4 columns
int[][] grid2 = {
    {1,2,3},
    {4,5,6},
    {7,8,9}
} // 3 rows, 3 columns grid

int[][] grid3 = {
    {1,2,3},
    {4,5},
    {6,7,8,9} // jagged array, not grid
}
```

##### example

```java
public class TicTacToe {
    private char[][] board;
    private char currentPlayer;

    public TicTacToe() {
        this.board = new char[3][3];
        this.currentPlayer = 'X';
        this.initializeBoard();
    }

    private void initializeBoard() {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col< 3; col++){
                this.board[row][col] = '-';
            }
        }
    }
}
```

##### LinkedList Concept

1. create a new node with the passed value
2. make node at tail point to new node
3. make tail reference to new node
4. so the time complexity is O(1), much better than arraylist O(n)

##### Node

A node consists of data and next (reference to the next node)

```java
// create a node class inside a LinkedList class
public class LinkedList {
    public LinkedList() {
        this.head = null;
        this.tail = null;
    }

    public class Node {

        public int data;
        public Node next; // reference to the next node

        public Node() {
            this.data = 0;
            this.next = null;
        }

        public Node(int newData){
            this.data = newData;
            this.next = null;
        }

        public Node(int newData, Node newNext){
            this.data = newData;
            this.next = newNext;
        }
    }
    private Node head;
    private Node tail;
}
```

## week 4

### 9/29

#### LinkedList vs ArrayList

##### time complexity for ArrayList vs LinkedList

|            | Add first | remove first | search | access |
| ---------- | --------- | ------------ | ------ | ------ |
| Array      | n         | n            | n      | 1      |
| ArrayList  | n         | n            | n      | 1      |
| LinkedList | 1         | 1            | n      | n      |

##### Add first to a LinkedList

```java
public void addFirst(int newData) {
    Node newNode = new Node(newData);
    if (this.head = null) {
        // empty list
        this.head = newNode;
        this.tail = newNode;
    } else {
        newNode.next = this.head;
        this.head = newNode;
    }
}
```
