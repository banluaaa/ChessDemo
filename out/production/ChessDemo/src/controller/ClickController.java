package controller;


import model.ChessComponent;
import model.Record;
import view.ChessGameFrame;
import view.Chessboard;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static model.ChessComponent.rounds;

public class ClickController implements Serializable {
    private final Chessboard chessboard;
    private ChessComponent first;
    //public static boolean paw = false;
    public ChessComponent other;
    public ChessComponent another;
    public ChessComponent test ;

    public ClickController(Chessboard chessboard) {
        this.chessboard = chessboard;
    }
    public void onClick(ChessComponent chessComponent) {
        if (first == null) {
            if (handleFirst(chessComponent)) {
                chessComponent.setSelected(true);
                /**ArrayList<ChessComponent> list = chessComponent.getCanMoves();
                for (int i = 0; i < list.size(); i++) {
                    another = list.get(i);
                    another.setCanGoTo(true);
                    another.repaint();
                }*/
                first = chessComponent;
                first.repaint();
                test = chessboard.getChessComponents()[4][4];
                test.setCanGoTo(true);
                test.repaint();
            }
        } else {
            if (first == chessComponent) { // 再次点击取消选取
                chessComponent.setSelected(false);
                ArrayList<ChessComponent> list = chessComponent.getCanMoves();
                for (int i = 0; i < list.size(); i++) {
                    another = list.get(i);
                    another.setCanGoTo(false);
                    ChessComponent recordAnother = another;
                    another = null;
                    recordAnother.repaint();
                }
                ChessComponent recordFirst = first;
                first = null;
                recordFirst.repaint();
            } else if (handleSecond(chessComponent)) {
                //repaint in swap chess method.
                ArrayList<ChessComponent> list = chessComponent.getCanMoves();
                for (int i = 0; i < list.size(); i++) {
                    another = list.get(i);
                    another.setCanGoTo(false);
                    //ChessComponent recordAnother = another;
                    another = null;
                   // recordAnother.repaint();
                }
                chessboard.swapChessComponents(first, chessComponent);
                chessboard.swapColor();//
                Record record = new Record();
                String[]need = chessboard.getChessboardGraph().split("\n");
                List<String> gra = new ArrayList<>();
                for (int j = 0; j < 9; j++) {
                    gra.add(need[j]);
                }
                record.setRecordString(gra);
                chessboard.setRetractLiat(record);//
                first.setSelected(false);
                first = null;
            }
        }
    }

    public void Enter(ChessComponent chessComponent){
        chessComponent.setEntered(true);
        other = chessComponent;
        other.repaint();
    }


    public void Exited(ChessComponent chessComponent){
        chessComponent.setEntered(false);
        other = chessComponent;
        other.repaint();
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
            rounds ++;
        }
        return chessComponent.getChessColor() != chessboard.getCurrentColor() &&
                first.canMoveTo(chessboard.getChessComponents(), chessComponent.getChessboardPoint());
    }
}
