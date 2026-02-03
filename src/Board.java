import java.util.Arrays;

/* Game state for a 3x3 board, independent of the Swing UI. Cells are numbered 0-8, left to right, top to bottom. */
public class Board {

    public static final char EMPTY = ' ';

    static final int[][] LINES = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // columns
            {0, 4, 8}, {2, 4, 6}             // diagonals
    };

    private final char[] cells = new char[9];

    public Board() {
        reset();
    }

    public void reset() {
        Arrays.fill(cells, EMPTY);
    }

    /** Places a mark and returns true, or returns false if the cell is already taken. */
    public boolean place(int cell, char mark) {
        if (mark != 'X' && mark != 'O') {
            throw new IllegalArgumentException("Mark must be X or O");
        }
        if (cells[cell] != EMPTY) {
            return false;
        }
        cells[cell] = mark;
        return true;
    }

    public char get(int cell) {
        return cells[cell];
    }

    /** The three cells of a completed line, or null if nobody has won yet. */
    public int[] winningLine() {
        for (int[] line : LINES) {
            char first = cells[line[0]];
            if (first != EMPTY && first == cells[line[1]] && first == cells[line[2]]) {
                return line;
            }
        }
        return null;
    }

    public char winner() {
        int[] line = winningLine();
        return line == null ? EMPTY : cells[line[0]];
    }

    public boolean isFull() {
        for (char cell : cells) {
            if (cell == EMPTY) {
                return false;
            }
        }
        return true;
    }

    public boolean isDraw() {
        return isFull() && winningLine() == null;
    }
}
