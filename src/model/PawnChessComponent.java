package model;

import view.ChessboardPoint;
import controller.ClickController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.IOException;

/**
 * 这个类表示国际象棋里面的车
 */
public class PawnChessComponent extends ChessComponent {
    /**
     * 黑车和白车的图片，static使得其可以被所有车对象共享
     * <br>
     * FIXME: 需要特别注意此处加载的图片是没有背景底色的！！！
     */
    private static Image PAWN_WHITE;
    private static Image PAWN_BLACK;

    /**
     * 车棋子对象自身的图片，是上面两种中的一种
     */
    private Image pawnImage;

    /**
     * 读取加载车棋子的图片
     *
     * @throws IOException
     */
    public void loadResource() throws IOException {
        if (PAWN_WHITE == null) {
            PAWN_WHITE = ImageIO.read(new File("./images/pawn-white.png"));
        }

        if (PAWN_BLACK == null) {
            PAWN_BLACK = ImageIO.read(new File("./images/pawn-black.png"));
        }
    }


    /**
     * 在构造棋子对象的时候，调用此方法以根据颜色确定rookImage的图片是哪一种
     *
     * @param color 棋子颜色
     */

    private void initiatePawnImage(ChessColor color) {
        try {
            loadResource();
            if (color == ChessColor.WHITE) {
                pawnImage = PAWN_WHITE;
            } else if (color == ChessColor.BLACK) {
                pawnImage = PAWN_BLACK;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public PawnChessComponent(ChessboardPoint chessboardPoint, Point location, ChessColor color, ClickController listener, int size) {
        super(chessboardPoint, location, color, listener, size);
        initiatePawnImage(color);
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
        int x = destination.getX()-source.getX();
        int y = Math.abs(destination.getY()-source.getY());
        if ((x >0)&&(getChessColor() == ChessColor.WHITE)) {
            return false;
        }
        if ((x <0)&&(getChessColor() == ChessColor.BLACK)) {
            return false;
        }
        if (y == 0) {
            if (getChessColor() == ChessColor.BLACK) {
                for (int i = source.getX()+1;i <= destination.getX();i++) {
                    if (!(chessComponents[i][source.getY()] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
                if ((source.getX() == 1) && (x == 2)) {
                    return true;
                } else if (x == 1) {
                    return true;
                } else {
                    return false;
                }
            } else {
                for (int i = source.getX()-1;i >= destination.getX();i--) {
                    if (!(chessComponents[i][source.getY()] instanceof EmptySlotComponent)) {
                        return false;
                    }
                }
                if ((source.getX() == 6) && (x == -2)) {
                    return true;
                } else if (x == -1) {
                    return true;
                } else {
                    return false;
                }
            }
        } else if ((y == 1)&&(Math.abs(x) == 1)) {
            if (!(chessComponents[destination.getX()][destination.getY()] instanceof EmptySlotComponent)) {
                return true;
            } else {
                if ((getChessColor() == ChessColor.BLACK)&&(getChessboardPoint().getX() == 4)) {
                    if ((chessComponents[source.getX()][destination.getY()] instanceof PawnChessComponent)&&(chessComponents[source.getX()][destination.getY()].getChessColor() == ChessColor.WHITE)) {
                        return true;
                    }
                } else if ((getChessColor() == ChessColor.WHITE)&&(getChessboardPoint().getX() == 3)) {
                    if ((chessComponents[source.getX()][destination.getY()] instanceof PawnChessComponent) && (chessComponents[source.getX()][destination.getY()].getChessColor() == ChessColor.BLACK)) {
                        return true;
                    }
                }
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 注意这个方法，每当窗体受到了形状的变化，或者是通知要进行绘图的时候，就会调用这个方法进行画图。
     *
     * @param g 可以类比于画笔
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        g.drawImage(rookImage, 0, 0, getWidth() - 13, getHeight() - 20, this);
        g.drawImage(pawnImage, 0, 0, getWidth() , getHeight(), this);
        g.setColor(Color.BLACK);
        if (isSelected()) { // Highlights the model if selected.
            g.setColor(Color.RED);
            g.drawOval(0, 0, getWidth() , getHeight());
        }
    }
}
