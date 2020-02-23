package spring2020.mcon364.othello;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class OthelloWindow extends JFrame {
    private static final CellStates COMPUTER_COLOUR = CellStates.WHITE;
    OthelloModelInterface model;
    CellStates current = CellStates.BLACK;
    JButton[][] buttons;

    public OthelloWindow(OthelloModelInterface model) {
        this.model = model;
        setTitle("Othello");
        setSize(600, 600);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));

        updateBoard(true);

        this.setVisible(true);
    }

    private ActionListener al = actionEvent -> {
        Point input =  parseInput(actionEvent.getActionCommand());
        if (model.makeMove(current, input)) {
            turnEvaluation();
            if (model instanceof OthelloModelOnePlayer && current == COMPUTER_COLOUR) {
                model.makeMove(current, ((OthelloModelOnePlayer) model).computerChoice(current));
                turnEvaluation();
            }
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Your entry was invalid. Please try again.",
                    "Invalid Entry",
                    JOptionPane.WARNING_MESSAGE
            );
        }
    };

    private void turnEvaluation() {
        updateBoard(false);
        CellStates otherPlayer = OthelloModelInterface.reverseState(current);
        if (!model.getAvailableMoves(otherPlayer).isEmpty()) current = otherPlayer;
        else if (model.getAvailableMoves(current).isEmpty()) gameOver();
    }

    private Point parseInput(String str) {
        String[] split = str.split(",");
        return new Point(Integer.parseInt(split[1]), Integer.parseInt(split[0]));
    }

    private void updateBoard(boolean firstTime) {
        CellStates[][] startingBoard = model.getBoard();
        int rows = startingBoard.length;
        int cols = startingBoard[0].length;
        if (firstTime) buttons = new JButton[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                CellStates cell = startingBoard[i][j];
                JButton button = firstTime ? new JButton() : buttons[i][j];
                if (firstTime) {
                    if (cell == CellStates.BLACK) button.setText("●");
                    else if (cell == CellStates.WHITE) button.setText("○");
                    button.setActionCommand(i + "," + j);
                    button.addActionListener(al);
                    buttons[i][j] = button;
                    this.add(button);
                } else if (cell == CellStates.WHITE || cell == CellStates.BLACK) {
                    button.setText(cell == CellStates.BLACK ? "●" : "○");
                }
            }
        }
    }

     private void gameOver() {
        byte[] score = model.getScore();

        StringBuilder build = new StringBuilder();
        build.append("The score is:\n●: ").append(score[0]).append(" and ○: ").append(score[1]).append(".\n");
        if (score[0] == score[1]) build.append("The game was a draw.");
        else {
            if (score[0] > score[1]) build.append("●");
            else build.append("○");
            build.append(" won.");
        }

        JOptionPane.showMessageDialog(
                this,
                build,
                "Game Over",
                JOptionPane.PLAIN_MESSAGE
        );

        System.exit(0);
    }
}
