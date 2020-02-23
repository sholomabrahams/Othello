package spring2020.mcon364.othello;

import java.awt.*;
import java.util.Scanner;

public class OthelloConsole {
    private static final CellStates COMPUTER_COLOUR = CellStates.WHITE;
    OthelloModelInterface model;
    private static Scanner scanner = new Scanner(System.in);

    public OthelloConsole(OthelloModelInterface model) {
        this.model = model;

        //Welcome message and print starting board
        System.out.println("Welcome!");
        System.out.println(printBoard());
        System.out.println("● goes first.");

        CellStates current = CellStates.BLACK;
        boolean gameOver = false;
        while (!gameOver) {
            Point input = getInput(current);
            if (model.makeMove(current, input)) {
                System.out.println(printBoard());
                //Switch to the other player if the other player has possible moves
                CellStates otherPlayer = current == CellStates.BLACK ? CellStates.WHITE : CellStates.BLACK;
                if (!model.getAvailableMoves(otherPlayer).isEmpty()) current = otherPlayer;
                else if (model.getAvailableMoves(current).isEmpty()) gameOver = true;
            } else {
                System.out.println("Your entry was invalid. Please try again.");
            }
        }
        System.out.println("Game over.");
        byte[] score = model.getScore();
        System.out.println("The score is:\n●: " + score[0] + " and ○: " + score[1] + ".");
        if (score[0] == score[1]) System.out.println("The game was a draw.");
        else {
            if (score[0] > score[1]) System.out.print("●");
            else System.out.print("○");
            System.out.println(" won.");
        }
    }

    private Point getInput(CellStates state) {
        if (model instanceof OthelloModelOnePlayer && state == COMPUTER_COLOUR) return ((OthelloModelOnePlayer) model).computerChoice(state);
        Point coordinates = new Point();
        System.out.println((state == CellStates.BLACK ? "●" : "○") + " player, please select a cell in the format D5: ");
        boolean validInput = true;
        String input;
        char col;
        int row;
        do {
            input = scanner.nextLine();
            col = input.charAt(0);
            row = input.charAt(1) - 49;
            if (input.length() != 2 || col < 65 || col > 104 || (col > 72 && col < 97)) {
                validInput = false;
                System.out.println("Your input is invalid.\nPlease enter cell coordinates in the format D5:");
            }
        } while (!validInput);
        coordinates.y = row;
        coordinates.x = col > 72 ? col - 97 : col - 65;

        return coordinates;
    }

    private String printBoard() {
        CellStates[][] board = model.getBoard();
        int last = board.length - 1;
        String result = "    A   B   C   D   E   F   G   H  \n  ┌───┬───┬───┬───┬───┬───┬───┬───┐\n";
        for (byte r = 0; r < board.length; r++) {
            result = result.concat(r + 1 + " │");
            for (byte c = 0; c < board[0].length; c++) {
                result = result.concat(" ");
                if (board[r][c] == CellStates.BLACK) result = result.concat("●");
                else if (board[r][c] == CellStates.WHITE) result = result.concat("○");
                else result = result.concat(" ");
                result = result.concat(" │");
            }
            result = result.concat(r != last ? "\n  ├───┼───┼───┼───┼───┼───┼───┼───┤\n" : "\n");
        }
        result = result.concat("  └───┴───┴───┴───┴───┴───┴───┴───┘");
        return result;
    }
}