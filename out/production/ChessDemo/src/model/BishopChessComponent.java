package model;

import view.Chessboard;
import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import static view.Chessboard.chessComponents;


/**
 * 这个类表示国际象棋里面的车
 */
public class BishopChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image BISHOP_WHITE;
    private static Image BISHOP_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image bishopImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (BISHOP_WHITE == null) {
            BISHOP_WHITE= ImageIO.read(new File("./images/bishop-white.png"));
        }

        if (BISHOP_BLACK == null) {
            BISHOP_BLACK = ImageIO.read(new File("./images/bishop-black.png"));
        }
    }

    public int getP() {
        return 0;
    }
    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiateBishopImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                bishopImage = BISHOP_WHITE;
            } else if (color == ChessColor.BLACK) {
                bishopImage = BISHOP_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BishopChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiateBishopImage(color);
    }

    @Override
    public ArrayList<ChessComponent> getCanMoves() {
        ArrayList<ChessComponent> ss = new ArrayList<>();
        ChessboardPoint s;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                s = new ChessboardPoint(i,j);
                if (canMoveTo(chessComponents,s)) {
                    ss.add(chessComponents[i][j]);
                }
            }
        }
        return ss;
    }

    /**
     * 车棋子的移动规则
     *
     * @param chessComponents 棋盘
     * @param destination     目标位置，如(0, 0), (0, 7)等等
     * @return 车棋子移动的合法性
     */

    @Override
    public boolean canMoveTo(ChessComponent[][] chessComponents, ChessboardPoint destination) {
        ChessboardPoint source = getChessboardPoint();
        int x = Math.abs(source.getX() - destination.getX());
        int y = Math.abs(source.getY() - destination.getY());
        if (getChessColor() == chessComponents[destination.getX()][destination.getY()].getChessColor()) {
            return false;
        } else if (x == y) {
            if ((x == 1)&&(y == 1)) {
                return true;
            } else if ((Math.max(destination.getX(), source.getX()) == destination.getX()) && (Math.max(destination.getY(), source.getY()) == destination.getY())) {
                int row = source.getX() + 1;
                for (int col = Math.min(source.getY(), destination.getY()) + 1; col < Math.max(source.getY(), destination.getY()); col++) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                    row++;
                }
                return true;
            } else if ((Math.max(destination.getX(), source.getX()) == source.getX()) && (Math.max(destination.getY(), source.getY()) == destination.getY())) {
                int row = source.getX() - 1;
                for (int col = Math.min(source.getY(), destination.getY()) + 1; col < Math.max(source.getY(), destination.getY()); col++) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                    row--;
                }
                return true;
            } else if ((Math.max(destination.getX(), source.getX()) == destination.getX()) && (Math.max(destination.getY(), source.getY()) == source.getY())) {
                int row = destination.getX() - 1;
                for (int col = Math.min(source.getY(),destination.getY()) + 1; col < Math.max(source.getY(), destination.getY()); col++) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                    row--;
                }
                return true;
            } else if ((Math.max(destination.getX(), source.getX()) == source.getX()) && (Math.max(destination.getY(), source.getY()) == source.getY())) {
                int row = destination.getX() + 1;
                for (int col = Math.min(source.getY(),destination.getY()) + 1; col < Math.max(source.getY(),destination.getY()); col++) {
                    if (!(chessComponents[row][col] instanceof EmptySlotComponent)) {
                        return false;
                    }
                    row++;
                }
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(bishopImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.BLUE);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}

