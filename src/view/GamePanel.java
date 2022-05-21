package view;

import controller.GameController;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


public class GamePanel extends JPanel {
    private GameController gameController;
    public String model;
    public static Chessboard chessboard;
    public static GamePanel it;
    private static String player;

    private final int CHESSBOARD_SIZE = 8;
    BufferedImage bgImage = null;
    int x = 0;
    int y = 0;//画图的起始位置
    int bgWidth;
    int bgHeight;


    public GamePanel(){
        try {
            bgImage = ImageIO.read(new File("C:\\Users\\admin\\Documents\\GitHub\\ChessDemo\\images\\bgImage.png"));
            bgWidth = 1000;
            bgHeight = 760;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void addChessboard() {
        chessboard = new Chessboard(CHESSBOARD_SIZE, CHESSBOARD_SIZE);
        gameController = new GameController(chessboard);
        chessboard.setLocation(HEIGHT / 10, HEIGHT / 10);
        add(chessboard);
        it = this;
    }
    @Override
    public void paint(Graphics g){
        g.drawImage(bgImage, x, y, bgWidth, bgHeight, null);
    }

}
