package spring2020.mcon364.othello;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class OthelloModelTest {

    @Test
    void getInitialBoard() {
        OthelloModel model = new OthelloModel();
        CellStates[][] actual = model.getBoard();

        CellStates[][] board = new CellStates[8][8];
        board[3][3] = CellStates.WHITE;
        board[3][4] = CellStates.BLACK;
        board[4][3] = CellStates.BLACK;
        board[4][4] = CellStates.WHITE;

        assertArrayEquals(board, actual);
    }

    @Test
    void getIntermediateBoard() {
        OthelloModel model = new OthelloModel();
        model.makeMove(CellStates.BLACK, new Point(4, 5));
        model.makeMove(CellStates.WHITE, new Point(5, 3));
        CellStates[][] actual = model.getBoard();

        CellStates[][] board = new CellStates[8][8];
        board[3][3] = CellStates.WHITE;
        board[3][4] = CellStates.WHITE;
        board[3][5] = CellStates.WHITE;
        board[4][3] = CellStates.BLACK;
        board[4][4] = CellStates.BLACK;
        board[5][4] = CellStates.BLACK;

        assertArrayEquals(board, actual);
    }

    @Test
    void makeMoveValid() {
        OthelloModel model = new OthelloModel();
        CellStates[][] expected = model.getBoard();
        expected[2][3] = CellStates.BLACK;
        expected[3][3] = CellStates.BLACK;
        boolean resultA = model.makeMove(CellStates.BLACK, new Point(3, 2));
        CellStates[][] resultB = model.getBoard();

        assertTrue(resultA);
        assertArrayEquals(expected, resultB);
    }

    @Test
    void makeMoveInvalidColour() {
        OthelloModel model = new OthelloModel();
        CellStates[][] expected = model.getBoard();
        boolean resultA = model.makeMove(CellStates.WHITE, new Point(3, 2));
        CellStates[][] resultB = model.getBoard();

        assertFalse(resultA);
        assertArrayEquals(expected, resultB);
    }

    @Test
    void makeMoveInvalidSelection() {
        OthelloModel model = new OthelloModel();
        CellStates[][] expected = model.getBoard();
        boolean resultA = model.makeMove(CellStates.BLACK, new Point(4, 2));
        CellStates[][] resultB = model.getBoard();

        assertFalse(resultA);
        assertArrayEquals(expected, resultB);
    }

    @Test
    void hasAvailableMoves() {
        OthelloModel model = new OthelloModel();
        assertFalse(model.getAvailableMoves(CellStates.BLACK).isEmpty());
        assertFalse(model.getAvailableMoves(CellStates.WHITE).isEmpty());
    }


    @Test
    void getScore() {
        OthelloModel model = new OthelloModel();
        byte[] expected = new byte[] {2, 2};
        assertArrayEquals(expected, model.getScore());
        model.makeMove(CellStates.BLACK, new Point(3, 2));
        expected[0] = 4;
        expected[1] = 1;
        assertArrayEquals(expected, model.getScore());
    }
}