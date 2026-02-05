import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        board = new Board();
    }

    @Test
    void newBoardIsEmptyWithNoWinner() {
        for (int i = 0; i < 9; i++) {
            assertThat(board.get(i)).isEqualTo(Board.EMPTY);
        }
        assertThat(board.winningLine()).isNull();
        assertThat(board.winner()).isEqualTo(Board.EMPTY);
        assertThat(board.isDraw()).isFalse();
    }

    @Test
    void cannotPlaceOnOccupiedCell() {
        assertThat(board.place(4, 'X')).isTrue();
        assertThat(board.place(4, 'O')).isFalse();
        assertThat(board.get(4)).isEqualTo('X');
    }

    @Test
    void rejectsInvalidMarks() {
        assertThatThrownBy(() -> board.place(0, 'Z')).isInstanceOf(IllegalArgumentException.class);
    }

    static Stream<Arguments> everyLineForBothPlayers() {
        return Arrays.stream(Board.LINES)
                .flatMap(line -> Stream.of(Arguments.of('X', line), Arguments.of('O', line)));
    }

    @ParameterizedTest(name = "{0} wins on {1}")
    @MethodSource("everyLineForBothPlayers")
    void detectsAllEightWinningLines(char mark, int[] line) {
        for (int cell : line) {
            board.place(cell, mark);
        }

        assertThat(board.winningLine()).containsExactly(line);
        assertThat(board.winner()).isEqualTo(mark);
    }

    @Test
    void mixedLineIsNotAWin() {
        board.place(0, 'X');
        board.place(1, 'O');
        board.place(2, 'X');

        assertThat(board.winningLine()).isNull();
    }

    @Test
    void fullBoardWithoutLineIsADraw() {
        // X O X
        // X O O
        // O X X
        char[] marks = {'X', 'O', 'X', 'X', 'O', 'O', 'O', 'X', 'X'};
        for (int i = 0; i < 9; i++) {
            board.place(i, marks[i]);
        }

        assertThat(board.isFull()).isTrue();
        assertThat(board.isDraw()).isTrue();
        assertThat(board.winner()).isEqualTo(Board.EMPTY);
    }

    @Test
    void winOnLastMoveIsNotADraw() {
        // X O X
        // O X O
        // O X X  -> X wins on the diagonal with the final move
        char[] marks = {'X', 'O', 'X', 'O', 'X', 'O', 'O', 'X', 'X'};
        for (int i = 0; i < 9; i++) {
            board.place(i, marks[i]);
        }

        assertThat(board.isDraw()).isFalse();
        assertThat(board.winner()).isEqualTo('X');
    }

    @Test
    void resetClearsTheBoard() {
        board.place(0, 'X');
        board.place(1, 'X');
        board.place(2, 'X');

        board.reset();

        assertThat(board.winningLine()).isNull();
        assertThat(board.place(0, 'O')).isTrue();
    }
}
