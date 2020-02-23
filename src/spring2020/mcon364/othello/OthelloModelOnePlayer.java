package spring2020.mcon364.othello;

import java.awt.Point;
import java.util.ArrayList;

public class OthelloModelOnePlayer extends OthelloModel implements OthelloModelInterface {
    private CellStates[][] board;

    public Point computerChoice(CellStates computerColour) {
        board = getBoard();
        CellStates opposite = OthelloModelInterface.reverseState(computerColour);
//        1. Check all possible moves:
        ArrayList<Point> possibleMoves = getAvailableMoves(computerColour);
        ArrayList<ArrayList<Point>> possibleNeighbours = new ArrayList<>();
        for (Point possibleMove : possibleMoves) {
            possibleNeighbours.add(findMoves(computerColour, opposite, possibleMove));
        }
//        2. Check how many spaces will be flipped for each move:
        int mostIndex = 0, mostCount = 0;
        for (int i = 0; i < possibleNeighbours.size(); i++) {
            ArrayList<Point> points = possibleNeighbours.get(i);
            for (Point p : points) {
                Point c = possibleMoves.get(i);
                int numberFlipped = traverse(computerColour, opposite, c, p.y - c.y, p.x - c.x, false);
                if (numberFlipped > mostCount) {
                    mostIndex = i;
                    mostCount = numberFlipped;
                }
            }
        }
//        3. Flip the best one:
        return possibleMoves.get(mostIndex);
    }
}
