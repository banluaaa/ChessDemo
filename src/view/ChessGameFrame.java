package view;

import controller.ClickController;
import controller.GameController;
import model.ChessComponent;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionListener;
import static model.ChessComponent.rounds;

/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame {
    //    public final Dimension FRAME_SIZE ;
    public static int WIDTH;
    public static int HEIGHT;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    public String model;
    public static Chessboard chessboard;
    public static ChessGameFrame it;
    private static String player;

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);

        getPlayer();
        addChessboard();
        addLabel();
        addHelloButton();
        addLoadButton();
        addRounds();
        model = getModel();
        if (model == "AI") {
            getDifficulty();
            //调用AI
        }
    }

    public void getPlayer() {
        String[] options = {"Player", "Tourist player"};
        int n = JOptionPane.showOptionDialog(null, "Input your player name/Tourist player", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        System.out.println(options[n]);
        if (n == 0) {
            String path = JOptionPane.showInputDialog(this,"Input Player-name here");
            if (path == "?") {
                //载入？
            }
        }
    }

    public static String getModel() {
        String[] options = {"People", "AI"};
        int n = JOptionPane.showOptionDialog(null, "Select the model", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        System.out.println(options[n]);
        if (n == 0) {
            return "People";
        } else if (n == 1) {
            return "AI";
        } else {
            return null;
        }
    }
    public static String getWinner() {
        JOptionPane.showMessageDialog(null, "GameOver\nWinner is " + Chessboard.winner, "提示", JOptionPane.INFORMATION_MESSAGE);
        String[] options = {"NewGame", "Exit"};
        int n = JOptionPane.showOptionDialog(null, "Welcome "+ player, "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        System.out.println(options[n]);
        if (n == 0) {
            return "NewGame";
        } else if (n == 1) {
            return "Exit";
        }
        return null;
    }

    public static String getDifficulty() {
        String[] options = {"Easy", "Generally", "Hard"};
        int n = JOptionPane.showOptionDialog(null, "Selection difficulty", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        System.out.println(options[n]);
        if (n == 0) {
            return "Easy";
        } else if (n == 1) {
            return "Generally";
        } else {
            return "Hard";
        }
    }

    public static String changePawn() {
        String [] options = {"Bishop","Knight","Rook","Queen"};
        int n = JOptionPane.showOptionDialog(null,"请选择你的选项：","提示",JOptionPane.YES_NO_OPTION,JOptionPane.QUESTION_MESSAGE,null,options,options[0]);
        System.out.println(options[n]);
        if (n == 0) {
            return "Bishop";
        } else if (n == 1) {
            return "Knight";
        } else if (n == 2) {
            return "Rook";
        } else if (n == 3) {
            return "Queen";
        }
        return null;
    }
    /**
     * 在游戏面板中添加棋盘
     */
    public void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
        it = this;
    }
    public void newChessboard() {
        remove(chessboard);
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
        chessboard.repaint();
        it = this;
    }

    /**
     * 在游戏面板中添加标签
     */
    private void addLabel() {
        JLabel statusLabel = new JLabel("Sample label");
        statusLabel.setLocation(HEIGHT, HEIGHT / 10);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(statusLabel);
    }

    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */
    private void addHelloButton() {
        JButton button = new JButton("Show Hello Here");
        button.addActionListener((e)-> JOptionPane.showMessageDialog(this, "Hello, world!"));
        button.setLocation(HEIGHT, HEIGHT/ 10 + 120);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }

    public void addRounds() {
        JButton button = new JButton("Rounds");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, (int)rounds));
        button.setLocation(HEIGHT, HEIGHT/ 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }


    private void addLoadButton() {
        JButton button = new JButton("Load");
        button.setLocation(HEIGHT, HEIGHT/ 10 + 240);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);

        button.addActionListener(e -> {
            System.out.println("Click load");
            String path = JOptionPane.showInputDialog(this,"Input Path here");
            gameController.loadGameFromFile(path);
        });
    }

}
