package spring2020.mcon364.othello;

public class OthelloMain {
    public static void main(String[] args) {
        OthelloModelInterface model = new OthelloModelOnePlayer();
        new OthelloWindow(model);
//        new OthelloConsole(model);
    }
}
