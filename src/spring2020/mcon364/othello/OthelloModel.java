package spring2020.mcon364.othello;

import java.awt.*;
import java.util.ArrayList;

enum CellStates {BLACK, WHITE}

public class OthelloModel implements OthelloModelInterface {
    private CellStates[][] board;

    public OthelloModel() {
        board = new CellStates[8][8];
        board[3][3] = CellStates.WHITE;
        board[3][4] = CellStates.BLACK;
        board[4][3] = CellStates.BLACK;
        board[4][4] = CellStates.WHITE;
    }

    public CellStates[][] getBoard() {
        CellStates[][] boardCopy = new CellStates[board.length][board[0].length];
        for (int i = 0; i < boardCopy.length; i++) {
            boardCopy[i] = board[i].clone();
        }
        return boardCopy;
    }

    //Does the move; returns false if invalid selection
    public boolean makeMove(CellStates state, Point coordinates) {
        CellStates opposite = OthelloModelInterface.reverseState(state);
        ArrayList<Point> surroundingCells = findMoves(state, opposite, coordinates);
        if (surroundingCells == null) return false;

        //Flip the appropriate cells to the current colour
        for (Point cell:surroundingCells) {
            traverse(state, opposite, coordinates, cell.y - coordinates.y, cell.x - coordinates.x, true);
        }
        board[coordinates.y][coordinates.x] = state;
        return true;
    }

    ArrayList<Point> findMoves(CellStates state, CellStates opposite, Point point) {
        if (point.x < 0 || point.x > 7 || point.y < 0 || point.y > 7 || board[point.y][point.x] == CellStates.BLACK || board[point.y][point.x] == CellStates.WHITE) return null;
        ArrayList<Point> surroundingCells = new ArrayList<>();
        int i = point.y - 1;
        while (i < point.y + 2) {
            int j = point.x - 1;
            while (j < point.x + 2) {
                if (i >= 0 && i <= 7 && j >= 0 && j <= 7) surroundingCells.add(new Point(j, i));
                j++;
            }
            i++;
        }

        //Removes any cells which are not of the opposite colour from the surroundingCells ArrayList
        surroundingCells.removeIf(cell -> board[cell.y][cell.x] != opposite);
        if (surroundingCells.isEmpty()) return null;

        //Removes any cell which are not flippable from the surroundingCells ArrayList
        surroundingCells.removeIf(cell -> traverse(state, opposite, point, cell.y - point.y, cell.x - point.x, false) < 0);
        if (surroundingCells.isEmpty()) return null;

        return surroundingCells;
    }

    //Traverses in the direction of opposite colour cells then flips cells when appropriate
    private int traverse(CellStates current, CellStates opposite, Point coordinates, int y, int x, boolean flip, int counter) {
        Point newCoordinates = (Point) coordinates.clone();
        newCoordinates.translate(x, y);
        if (newCoordinates.y < 0 || newCoordinates.y > 7 || newCoordinates.x < 0 || newCoordinates.x > 7) return -1;
        if (board[newCoordinates.y][newCoordinates.x] == opposite) {
            if (flip) board[newCoordinates.y][newCoordinates.x] = current;
            counter++;
            return traverse(current, opposite, newCoordinates, y, x, flip, counter);
        }
        else return board[newCoordinates.y][newCoordinates.x] != current ? -1 : counter;
    }

    int traverse(CellStates current, CellStates opposite, Point coordinates, int y, int x, boolean flip) {
        return traverse(current, opposite, coordinates, y, x, flip, 0);
    }

    public ArrayList<Point> getAvailableMoves(CellStates state) {
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                Point point = new Point(j, i);
                CellStates currentCell = board[i][j];
                if (!(currentCell == CellStates.BLACK) && !(currentCell == CellStates.WHITE) && findMoves(state, state == CellStates.BLACK ? CellStates.WHITE : CellStates.BLACK, point) != null) points.add(point);
            }
        }
        return points;
    }

    public byte[] getScore() {
        byte[] score = {0, 0};
        for (CellStates[] row:board) {
            for (CellStates colour:row) {
                if (colour == CellStates.BLACK) score[0]++;
                else if (colour == CellStates.WHITE) score[1]++;
            }
        }
        return score;
    }
}