package view;

import controller.ClickController;
import controller.GameController;
import jdk.jfr.consumer.RecordedFrame;
import model.ChessColor;
import model.ChessComponent;
import model.Record;

import javax.swing.*;
import javax.swing.text.View;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;
import java.util.Timer;

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
    public Chessboard chessboard;
    public static ChessGameFrame it;
    //private static String player;不要了？
    public static int w = 1;
    public static int time1 = 30;
    public static int time2 = 30;
    //int a = 0;
    JLabel bg = new aLabel(null);
    JLabel hintLabel = new JLabel("White's Turn");
    Image img1 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/bgImage.png"))).getImage();
    //Image img2 = new ImageIcon(Objects.requireNonNull(getClass().getResource("/images/bgImage2.png"))).getImage();

    public ChessGameFrame(int width, int height) {
        setTitle("2022 CS102A Project Demo"); //设置标题
        this.WIDTH = width;
        this.HEIGHT = height;
        this.CHESSBOARD_SIZE = HEIGHT * 4 / 5;

        setSize(WIDTH, HEIGHT);
        setLocationRelativeTo(null); // Center the window.
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE); //设置程序关闭按键，如果点击右上方的叉就游戏全部关闭了
        setLayout(null);


        test1();
        getPlayer();
        model = getModel();
        if (model == "AI") {
            getDifficulty();
            //调用AI
        }

        setSize(WIDTH, HEIGHT);
        setLocation(0,0);
        setLayout(null);
        addChessboard();
        hintLabel.setLocation(HEIGHT+10, HEIGHT / 10+420);
        hintLabel.setSize(200, 30);
        hintLabel.setFont(new Font("Rockwell", Font.BOLD, 30));
        //hintLabel.setForeground(Color.WHITE);
        add(hintLabel);


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
        //this.setContentPane(gamePanel);
        //gamePanel.setVisible(true);
        //gamePanel.setOpaque(false);
    }
    public void test1() {
        java.util.Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Time1();
                Time2();
            }
        }, 1000, 1000);
    }
    public void Time1() {
        JLabel statusLabel = new JLabel("BLACK Time :"+time1);
        statusLabel.setLocation(80,30);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("BLACK", Font.HANGING_BASELINE, 20));
        add(statusLabel);
        if (time1 == 0) {
            time1 = 30;
        } else {
            time1--;
        }
    }
    private void Time2() {
        JLabel statusLabel = new JLabel("WHITE Time :"+time2);
        statusLabel.setLocation(80, 670);
        statusLabel.setSize(200, 60);
        statusLabel.setFont(new Font("WHITE", Font.HANGING_BASELINE, 20));
        add(statusLabel);
        if (time2 == 0) {
            time2 = 30;
        } else {
            time2--;
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
    public static void Waining(){
        w++;
        JOptionPane.showMessageDialog(null, "Warning ,your CHECK will be attack!", "提示", JOptionPane.INFORMATION_MESSAGE);
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
    public static String getDraw() {
        JOptionPane.showMessageDialog(null, "A draw in chess", "提示", JOptionPane.INFORMATION_MESSAGE);
        String[] options = {"NewGame", "Exit"};
        int n = JOptionPane.showOptionDialog(null, "Welcome ChessGame", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
        System.out.println(options[n]);
        if (n == 0) {
            return "NewGame";
        } else if (n == 1) {
            return "Exit";
        }
        return null;
    }
    public static String getWinner() {
        JOptionPane.showMessageDialog(null, "GameOver\nWinner is " + Chessboard.winner, "提示", JOptionPane.INFORMATION_MESSAGE);
        String[] options = {"NewGame", "Exit"};
        int n = JOptionPane.showOptionDialog(null, "Welcome ChessGame", "提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
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

    public void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        chessboard.setGameFrame(this);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10-10);
        add(chessboard);
        chessboard.setHintLabel(hintLabel);
        it = this;
    }
    public void newChessboard() {
        remove(chessboard);
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setGameFrame(this);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10-10);
        add(chessboard);
        hintLabel.setText("White's Turn");
        chessboard.setHintLabel(hintLabel);
        chessboard.repaint();
        it = this;
    }
    public void loadRetractChessboard(List<String>chessboard1,List<Record> retractLiat){
       remove(chessboard);
       chessboard = new Chessboard(chessboard1,CHESSBOARD_SIZE, CHESSBOARD_SIZE,retractLiat);
       gameController = new GameController(chessboard);
       chessboard.setGameFrame(this);
       chessboard.setLocation(HEIGHT / 10, HEIGHT / 10-10);
       add(chessboard);
       hintLabel.setText(chessboard.getCurrentColor()== ChessColor.BLACK?"Black's Turn":"White's Turn");
       chessboard.setHintLabel(hintLabel);
       chessboard.repaint();
       it = this;
     }
    public void loadNewChessboard(List<String>chessboard1){
        remove(chessboard);
        chessboard = new Chessboard(chessboard1,CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setGameFrame(this);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10-10);
        add(chessboard);
        hintLabel.setText(chessboard.getCurrentColor()== ChessColor.BLACK?"Black's Turn":"White's Turn");
        chessboard.setHintLabel(hintLabel);
        chessboard.repaint();
        it = this;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("clicked");
        String cmd = e.getActionCommand();
        switch (cmd) {
            case "Retract":
                System.out.println("Retract");
                retract();
                break;
            case "Remake":
                System.out.println("Remake");
                it.newChessboard();
                break;
            case "Load":
                System.out.println("Load");
                it.loadNewChessboard(load());
                break;
            case "Save":
                System.out.println("Save");
                save();
                break;
            case "Appearance":
                System.out.println("Appearance");
                appearance();
                break;
            default:
                break;
        }
    }

    private List<String> load(){
        String parent = "C:\\Users\\admin\\Documents\\GitHub\\ChessDemo\\resource";
        String fileName = JOptionPane.showInputDialog(this, "input the path here");
        File file = new File(parent+File.separator+fileName+".txt");
        List<String>chessboard = new ArrayList<>();
        if(!file.exists()){
            System.out.println("文件"+fileName+" 不存在！");
            return null;
        }

        int length = (int)file.length();
        FileInputStream putout;
        try{
            putout = new FileInputStream(file);
            byte data[] = new byte[length];
            int i =putout.read(data);
            String need = new String(data).trim();
            System.out.println(need);
            String[]need2 = need.split("\n");
            for (int j = 0; j < 9; j++) {
                chessboard.add(need2[j]);
            }
            putout.close();
        } catch (IOException e){
            e.printStackTrace();
        }return chessboard;
    }

    private void save() {
        System.out.println(chessboard.getChessboardGraph());
        String parent = "C:\\Users\\admin\\Documents\\GitHub\\ChessDemo\\resource";
        String fileName = JOptionPane.showInputDialog(this, "input the path here");
       File file = new File(parent+File.separator+fileName+".txt");
       File pare = file.getParentFile();
       if(!pare.exists()){
           pare.mkdirs();
       }
       String putin = chessboard.getChessboardGraph();
       try{
           FileOutputStream fos = new FileOutputStream(file);
           fos.write(putin.getBytes());
           fos.close();
       } catch (IOException e){
           e.printStackTrace();
       }
    }

    private void appearance(){
        getContentPane().setLayout(null);
            bg =new aLabel(img1);
            this.getLayeredPane().setLayout(null);
            bg.setBounds(0,0,this.getWidth(),this.getHeight());
            this.getLayeredPane().add(bg,new Integer(Integer.MIN_VALUE));
            bg.repaint();
            ((JPanel)this.getContentPane()).setOpaque(false);

    }
    private class aLabel extends JLabel{
        private Image img;
        public aLabel(Image img){
            this.img = img;
        }
        @Override
        public  void paintComponent(Graphics g){
            super.paintComponent(g);
            int x = this.getWidth();
            int y = this.getHeight();
            g.drawImage(img,0,0,x,y,null);
        }
    }
    private void retract(){
        List<Record> recordLiat  = chessboard.getRetractLiat();
        System.out.println(recordLiat.size());
        if(recordLiat.size()<=1){
            it.newChessboard();
        }else{
            List<String> recordBoard=recordLiat.get(recordLiat.size()-2).getRecordString();
            recordLiat.remove(recordLiat.size()-1);
            it.loadRetractChessboard(recordBoard,recordLiat);
        }
    }
}
