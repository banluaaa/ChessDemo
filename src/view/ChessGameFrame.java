package view;

import controller.ClickController;
import controller.GameController;
import model.ChessComponent;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

import static model.ChessComponent.rounds;
/**
 * 这个类表示游戏过程中的整个游戏界面，是一切的载体
 */
public class ChessGameFrame extends JFrame implements ActionListener{
    //    public final Dimension FRAME_SIZE ;
    public static int WIDTH;
    public static int HEIGHT;
    public final int CHESSBOARD_SIZE;
    private GameController gameController;
    public String model;
    public static Chessboard chessboard;
    public static ChessGameFrame it;
    private static String player;
    private GamePanel gp;

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
        model = getModel();
        if (model == "AI") {
            getDifficulty();
            //调用AI
        }

        addChessboard();
        //gp = new GamePanel();
        //super.add(gp);
        //super.setVisible(true);
        //setLayout(null);

        JLabel hintLabel = new JLabel("White's Turn");
        hintLabel.setLocation(HEIGHT+10, HEIGHT / 10);
        hintLabel.setSize(200, 30);
        hintLabel.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(hintLabel);
        chessboard.setHintLabel(hintLabel);//完成

        JButton btnRetract = new JButton("Retract");
        btnRetract.setActionCommand("Retract");
        btnRetract.addActionListener(this);
        btnRetract.setLocation(HEIGHT, HEIGHT / 10 + 60);
        btnRetract.setSize(200, 60);
        btnRetract.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(btnRetract);//有问题

        JButton btnLoad = new JButton("Load");
        btnLoad.setActionCommand("Load");
        btnLoad.addActionListener(this);
        btnLoad.setLocation(HEIGHT, HEIGHT / 10 + 120);
        btnLoad.setSize(200, 60);
        btnLoad.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(btnLoad);

        JButton btnSave = new JButton("Save");
        btnSave.setActionCommand("Save");
        btnSave.addActionListener(this);
        btnSave.setLocation(HEIGHT, HEIGHT / 10 + 180);
        btnSave.setSize(200, 60);
        btnSave.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(btnSave);

        JButton btnReview = new JButton("Review");
        btnReview.setActionCommand("Review");
        btnReview.addActionListener(this);
        btnReview.setLocation(HEIGHT, HEIGHT / 10 + 240);
        btnReview.setSize(200, 60);
        btnReview.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(btnReview);

        JButton btnAppearance = new JButton("Appearance");
        btnAppearance.setActionCommand("Appearance");
        btnAppearance.addActionListener(this);
        btnAppearance.setLocation(HEIGHT, HEIGHT / 10 + 300);
        btnAppearance.setSize(200, 60);
        btnAppearance.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(btnAppearance);

        JButton btnRemake = new JButton("Remake");
        btnRemake.setActionCommand("Remake");
        btnRemake.addActionListener(this);
        btnRemake.setLocation(HEIGHT, HEIGHT / 10 + 360);
        btnRemake.setSize(200, 60);
        btnRemake.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(btnRemake);//完成

        setVisible(true);

        //addLabel();
        //addHelloButton();
        //addLoadButton();
        //addRounds();


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
        chessboard.setGameFrame(this);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
        it = this;
    }
    public void newChessboard() {
        remove(chessboard);
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setGameFrame(this);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
        chessboard.repaint();
        it = this;
    }


    /**
     * 在游戏面板中增加一个按钮，如果按下的话就会显示Hello, world!
     */


    public void addRounds() {
        JButton button = new JButton("Rounds");
        button.addActionListener((e) -> JOptionPane.showMessageDialog(this, (int)rounds));
        button.setLocation(HEIGHT, HEIGHT/ 10 + 360);
        button.setSize(200, 60);
        button.setFont(new Font("Rockwell", Font.BOLD, 20));
        add(button);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("clicked");
        String cmd = e.getActionCommand();
        switch (cmd) {
            case "Retract":
                System.out.println("Retract");
               chessboard.retract();
                break;
            case "Remake":
                System.out.println("Remake");
                it.newChessboard();
                break;
            case "Load":
                System.out.println("Load");
                load();
                break;
            case "Save":
                System.out.println("Save");
                save();
                break;
            case "renshu":
                System.out.println("renshu");

                break;
            default:
                break;
        }
    }

    private void load(){
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
        int result = chooser.showOpenDialog(null);
        File parent = chooser.getSelectedFile();
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try{
            fis = new FileInputStream(parent);
            ois = new ObjectInputStream(fis) ;
            ChessComponent[][]chessComponents = (ChessComponent[][]) ois.readObject();
            chessboard.setChessComponents(chessComponents);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(ois!=null){
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void save() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int result = chooser.showOpenDialog(null);
        File parent = chooser.getSelectedFile();
        System.out.println("parent--->"+parent);
        String path = parent.getAbsolutePath()+File.separator+ System.currentTimeMillis()+".txt";
        File file = new File(path);
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            FileOutputStream fos = null;
            ObjectOutputStream oos = null;
            try {
                fos = new FileOutputStream(file);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(chessboard.getChessComponents());
            }  catch (IOException e) {
                e.printStackTrace();
            } finally {
                if(oos!=null){
                    try {
                        oos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
