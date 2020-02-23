package spring2020.mcon364.othello;

import java.awt.*;
import java.util.ArrayList;

public interface OthelloModelInterface {
    CellStates[][] getBoard();
    boolean makeMove(CellStates state, Point coordinates);
    ArrayList<Point> getAvailableMoves(CellStates state);
    byte[] getScore();

    static CellStates reverseState (CellStates current) {
        return  current == CellStates.BLACK ? CellStates.WHITE : CellStates.BLACK;
    }
}
