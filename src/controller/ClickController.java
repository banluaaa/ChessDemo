package controller;


import model.ChessComponent;
import view.ChessGameFrame;
import view.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

import static model.ChessComponent.rounds;

public class ClickController {
    private final Chessboard chessboard;
    private ChessComponent first;
    public static boolean paw = false;
    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }
    public void onClick(ChessComponent chessComponent) {
        ChessComponent other = chessboard.getChessComponents()[3][4];
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                other.setCanMove(true);
                first = chessComponent;
                first.repaint();
                other.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                other.setCanMove(false);
                ChessComponent recordFirst = first;
                ChessComponent recordOther = other;
                first = null;
                other = null;
                recordFirst.repaint();
                recordOther.repaint();
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();

                first.setSelected(false);
                other.setCanMove(false);
                ChessComponent recordOther = other;
                first = null;
                other = null;
                recordOther.repaint();
            }
        }
    }

    /**
     * @param chessComponent 目标选取的棋子
     * @return 目标选取的棋子是否与棋盘记录的当前行棋方颜色相同
     */

    private boolean handleFirst(ChessComponent chessComponent) {
        if (chessComponent.getChessColor() == chessboard.getCurrentColor()) {
        }
        return chessComponent.getChessColor() == chessboard.getCurrentColor();
    }

    /**
     * @param chessComponent first棋子目标移动到的棋子second
     * @return first棋子是否能够移动到second棋子位置
     */

    private boolean handleSecond(ChessComponent chessComponent) {
        if (chessComponent.getChessColor() != chessboard.getCurrentColor() && first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint())) {
            rounds++;
        }
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }
}
