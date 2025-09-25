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
