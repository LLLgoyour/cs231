package lecture;

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
            for (int col = 0; col < 3; col++) {
                this.board[row][col] = '-';
            }
        }
    }

    public void printBoard() {
        System.out.println("----------");
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                System.out.print(this.board[i][j] + " ");
            }
            System.out.println();
            System.out.println("------------");
        }
    }

    public boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (this.board[i][j] == '-') {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean checkForWin() {
        return (checkRowsForWin() || checkColumnsForWin() || checkDiagonalsForWin());
    }

    private boolean checkRowsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkThreeCells(this.board[i][0], this.board[i][1], this.board[i][2])) {
                return true;
            }
        }
        return false;
    }

    private boolean checkThreeCells(char c1, char c2, char c3) {
        return ((c1 != '-') && (c1 == c2) && (c2 == c3));
    }

    private boolean checkColumnsForWin() {
        for (int i = 0; i < 3; i++) {
            if (checkThreeCells(this.board[0][i], this.board[1][i], this.board[2][i])) {
                return true;
            }
        }
        return false;
    }

    private boolean checkDiagonalsForWin() {
        if (checkThreeCells(this.board[0][0], this.board[1][1], this.board[2][2])) {
            return true;
        }
        return false;
    }

    public void changePlayer() {
        if(this.)
    }

    public static void main(String[] args) {
        TicTacToe game = new TicTacToe();
        while(int )
    }
}
