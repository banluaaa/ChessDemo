package model;

import view.ChessboardPoint;

import java.awt.*;

public class Record {
    private static ChessComponent chess;
    //起点坐标
    private ChessboardPoint start;
    //结束坐标
    private ChessboardPoint end;
    //被吃掉的棋子
    private static ChessComponent eatedChess;

    public Record() {

    }

    public ChessComponent getChess() {
        return chess;
    }

    public Record setChess(ChessComponent chess) {
        this.chess = chess;
        return this;
    }

    public ChessboardPoint getStart() {
        return start;
    }

    public Record setStart(ChessboardPoint start) {
        this.start = start;
        return this;
    }

    public ChessboardPoint getEnd() {
        return end;
    }

    public Record setEnd(ChessboardPoint end) {
        this.end = end;
        return this;
    }

    public ChessComponent getEatedChess() {
        return eatedChess;
    }

    public Record setEatedChess(ChessComponent eatedChess) {
        this.eatedChess = eatedChess;
        return this;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Record{");
        sb.append("chess=").append(chess);
        sb.append(", start=").append(start);
        sb.append(", end=").append(end);
        sb.append(", eatedChess=").append(eatedChess);
        sb.append('}');
        return sb.toString();
    }
}
