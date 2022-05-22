package view;
import model.*;
import controller.ClickController;
import model.Record;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static model.ChessComponent.rounds;
import static view.ChessGameFrame.*;

/**
 * 这个类表示面板上的棋盘组件对象
 */
public class Chessboard extends JComponent {
    /**
     * CHESSBOARD_SIZE： 棋盘是8 * 8的
     * <br>
     * BACKGROUND_COLORS: 棋盘的两种背景颜色
     * <br>
     * chessListener：棋盘监听棋子的行动
     * <br>
     * chessboard: 表示8 * 8的棋盘
     * <br>
     * currentColor: 当前行棋方
     */
    private static final int CHESSBOARD_SIZE = 8;
    public static String winner;
    public static  ChessComponent[][] chessComponents = new ChessComponent[CHESSBOARD_SIZE][CHESSBOARD_SIZE];//改变了final属性
    private ChessColor currentColor = ChessColor.WHITE;
    //all chessComponents in this chessboard are shared only one model controller
    private final ClickController clickController = new ClickController(this);
    private final int CHESS_SIZE;
    private ChessGameFrame gameFrame;
    private JLabel hintLabel;

    private List<Record> retractLiat = new ArrayList<>() ;
    public List<Record> getRetractLiat(){
        return retractLiat;
    }
    public void setRetractLiat(Record record){
        this.retractLiat.add(record);
    }
    public void setGameFrame(ChessGameFrame gameFrame) {
        this.gameFrame = gameFrame;
    }

    public void setHintLabel(JLabel hintLabel){
        this.hintLabel = hintLabel;
    }

    public Chessboard(int width, int height) {
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        initiateEmptyChessboard();
        // FIXME: Initialize chessboard for testing only.
        initRookOnBoard(0, 0, ChessColor.BLACK);
        initRookOnBoard(0, CHESSBOARD_SIZE - 1, ChessColor.BLACK);
        initRookOnBoard(CHESSBOARD_SIZE - 1, 0, ChessColor.WHITE);
        initRookOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 1, ChessColor.WHITE);
        initKnightOnBoard(0, 1, ChessColor.BLACK);
        initKnightOnBoard(0, CHESSBOARD_SIZE - 2, ChessColor.BLACK);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, 1, ChessColor.WHITE);
        initKnightOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 2, ChessColor.WHITE);
        initBishopOnBoard(0, 2, ChessColor.BLACK);
        initBishopOnBoard(0, CHESSBOARD_SIZE - 3, ChessColor.BLACK);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, 2, ChessColor.WHITE);
        initBishopOnBoard(CHESSBOARD_SIZE - 1, CHESSBOARD_SIZE - 3, ChessColor.WHITE);
        initQueenOnBoard(0, 4, ChessColor.BLACK);
        initQueenOnBoard(CHESSBOARD_SIZE - 1, 4, ChessColor.WHITE);
        initKingOnBoard(0, 3, ChessColor.BLACK);
        initKingOnBoard(CHESSBOARD_SIZE - 1, 3, ChessColor.WHITE);
        initPawnOnBoard(1, 0, ChessColor.BLACK);
        initPawnOnBoard(1, 1, ChessColor.BLACK);
        initPawnOnBoard(1, 2, ChessColor.BLACK);
        initPawnOnBoard(1, 3, ChessColor.BLACK);
        initPawnOnBoard(1, 4, ChessColor.BLACK);
        initPawnOnBoard(1, 5, ChessColor.BLACK);
        initPawnOnBoard(1, 6, ChessColor.BLACK);
        initPawnOnBoard(1, 7, ChessColor.BLACK);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 0, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 1, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 2, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 3, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 4, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 5, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 6, ChessColor.WHITE);
        initPawnOnBoard(CHESSBOARD_SIZE - 2, 7, ChessColor.WHITE);
    }

    public ChessComponent[][] getChessComponents() {
        return chessComponents;
    }
    public void setChessComponents(ChessComponent[][]chessComponents){
        this.chessComponents = chessComponents;
        repaint();
    }

    public ChessColor getCurrentColor() {
        return currentColor;
    }

    public String getChessboardGraph() {
        StringBuilder s = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                s.append(chessComponents[i][j].getSuoXie());
            }
            s.append("\n");
        }
        if(getCurrentColor()==ChessColor.BLACK){
            s.append('b');
        }else if(getCurrentColor()==ChessColor.WHITE){
            s.append('w');
        }
        return s.toString();
    }

    public void putChessOnBoard(ChessComponent chessComponent) {
        int row = chessComponent.getChessboardPoint().getX(), col = chessComponent.getChessboardPoint().getY();

        if (chessComponents[row][col] != null) {
            remove(chessComponents[row][col]);
        }
        add(chessComponents[row][col] = chessComponent);
    }

    public void swapChessComponents(ChessComponent chess1, ChessComponent chess2) {
        // Note that chess1 has higher priority, 'destroys' chess2 if exists.
        if(ChessGameFrame.w >= 5) {
            if (getDraw() == "NewGame") {
                it.newChessboard();
                it.getPlayer();
                if (getModel() == "AI") {
                    getDifficulty();
                    //调用AI
                }
            } else {
                System.exit (EXIT_ON_CLOSE);
            }
        }
        if (check(chess1,chess2) == false) {
            Waining();
            chess1.swapLocation(chess2);
            chess2.swapLocation(chess1);
            if (currentColor == ChessColor.BLACK) {
                currentColor = ChessColor.WHITE;
            } else {
                currentColor = ChessColor.BLACK;
            }
            return;
        } else {
            if ((chess1.getChessColor() == ChessColor.BLACK) && (chess1.getChessboardPoint().getX() == 4) && (Math.abs(chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()].getP() - rounds) <= 1)) {
                if ((chess1 instanceof PawnChessComponent) && (chess2 instanceof EmptySlotComponent) && (chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()] instanceof PawnChessComponent) && (chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()].getChessColor() == ChessColor.WHITE)) {
                    remove(chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()]);
                    add(new EmptySlotComponent(chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()].getChessboardPoint(), chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()].getLocation(), clickController, CHESS_SIZE));
                }
            } else if ((chess1.getChessColor() == ChessColor.WHITE) && (chess1.getChessboardPoint().getX() == 3) && (Math.abs(chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()].getP() - rounds) <= 1)) {
                if ((chess1 instanceof PawnChessComponent) && (chess2 instanceof EmptySlotComponent) && (chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()] instanceof PawnChessComponent) && (chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()].getChessColor() == ChessColor.BLACK)) {
                    remove(chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()]);
                    add(new EmptySlotComponent(chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()].getChessboardPoint(), chessComponents[chess1.getChessboardPoint().getX()][chess2.getChessboardPoint().getY()].getLocation(), clickController, CHESS_SIZE));
                }
            }
            if ((chess1.getChessColor() == ChessColor.BLACK) && (chess1.getChessboardPoint().getX() == 6) && (chess2.getChessColor() != ChessColor.BLACK) && (chess2.getChessboardPoint().getX() == 7) && (chess1 instanceof PawnChessComponent)) {
                if (!(chess2 instanceof EmptySlotComponent)) {
                    if (chess2 instanceof KingChessComponent) {
                        if (chess2.getChessColor() == ChessColor.BLACK) {
                            winner = "WHITE";
                        } else {
                            winner = "BLACK";
                        }
                        if (getWinner() == "NewGame") {
                            it.newChessboard();
                            it.getPlayer();
                            if (getModel() == "AI") {
                                getDifficulty();
                                //调用AI
                            }
                        } else {
                            System.exit (EXIT_ON_CLOSE);
                        }
                    }
                    remove(chess2);
                    add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
                }
                chess1.swapLocation(chess2);
                int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
                chessComponents[row1][col1] = chess1;
                int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
                chessComponents[row2][col2] = chess2;
                chess1.repaint();
                chess2.repaint();
                String a = changePawn();
                remove(chess1);
                if (a == "Bishop") {
                    add(chess1 = new BishopChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(), clickController, CHESS_SIZE));
                } else if (a == "Knight") {
                    add(chess1 = new KnightChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(), clickController, CHESS_SIZE));
                } else if (a == "Rook") {
                    add(chess1 = new RookChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(), clickController, CHESS_SIZE));
                } else if (a == "Queen") {
                    add(chess1 = new QueenChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(), clickController, CHESS_SIZE));
                }
                chess1.repaint();
            } else if ((chess1.getChessColor() == ChessColor.WHITE) && (chess1.getChessboardPoint().getX() == 1) && (chess2.getChessColor() != ChessColor.WHITE) && (chess2.getChessboardPoint().getX() == 0) && (chess1 instanceof PawnChessComponent)) {
                if (!(chess2 instanceof EmptySlotComponent)) {
                    if (chess2 instanceof KingChessComponent) {
                        if (chess2.getChessColor() == ChessColor.BLACK) {
                            winner = "WHITE";
                        } else {
                            winner = "BLACK";
                        }
                        if (getWinner() == "NewGame") {
                            it.newChessboard();
                            it.getPlayer();
                            if (getModel() == "AI") {
                                getDifficulty();
                                //调用AI
                            }
                        } else {
                            System.exit (EXIT_ON_CLOSE);
                        }
                    }
                    remove(chess2);
                    add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
                }
                chess1.swapLocation(chess2);
                int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
                chessComponents[row1][col1] = chess1;
                int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
                chessComponents[row2][col2] = chess2;
                chess1.repaint();
                chess2.repaint();
                String a = changePawn();
                remove(chess1);
                if (a == "Bishop") {
                    add(chess1 = new BishopChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(), clickController, CHESS_SIZE));
                } else if (a == "Knight") {
                    add(chess1 = new KnightChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(), clickController, CHESS_SIZE));
                } else if (a == "Rook") {
                    add(chess1 = new RookChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(), clickController, CHESS_SIZE));
                } else if (a == "Queen") {
                    add(chess1 = new QueenChessComponent(chess1.getChessboardPoint(), chess1.getLocation(), chess1.getChessColor(), clickController, CHESS_SIZE));
                }
                chess1.repaint();
            } else {
                if (!(chess2 instanceof EmptySlotComponent)) {
                    if (chess2 instanceof KingChessComponent) {
                        if (chess2.getChessColor() == ChessColor.BLACK) {
                            winner = "WHITE";
                        } else {
                            winner = "BLACK";
                        }
                        if (getWinner() == "NewGame") {
                            it.newChessboard();
                            it.getPlayer();
                            if (getModel() == "AI") {
                                getDifficulty();
                                //调用AI
                            }
                        } else {
                            System.exit (EXIT_ON_CLOSE);
                        }
                    }
                    remove(chess2);
                    add(chess2 = new EmptySlotComponent(chess2.getChessboardPoint(), chess2.getLocation(), clickController, CHESS_SIZE));
                }
                chess1.swapLocation(chess2);
                int row1 = chess1.getChessboardPoint().getX(), col1 = chess1.getChessboardPoint().getY();
                chessComponents[row1][col1] = chess1;
                int row2 = chess2.getChessboardPoint().getX(), col2 = chess2.getChessboardPoint().getY();
                chessComponents[row2][col2] = chess2;
                chess1.repaint();
                chess2.repaint();
            }
        }

    }

    public void initiateEmptyChessboard() {
        for (int i = 0; i < chessComponents.length; i++) {
            for (int j = 0; j < chessComponents[i].length; j++) {
                putChessOnBoard(new EmptySlotComponent(new ChessboardPoint(i, j), calculatePoint(i, j), clickController, CHESS_SIZE));
            }
        }
    }

    public void swapColor() {
        currentColor = currentColor == ChessColor.BLACK ? ChessColor.WHITE : ChessColor.BLACK;
        hintLabel.setText(currentColor==ChessColor.BLACK?"Black's Turn":"White's Turn");
    }

    private void initRookOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new RookChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        if(chessComponent.getChessColor()==ChessColor.BLACK){
            chessComponent.setName('R');
        }else{
            chessComponent.setName('r');
        }
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKnightOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KnightChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        if(chessComponent.getChessColor()==ChessColor.BLACK){
            chessComponent.setName('N');
        }else{
            chessComponent.setName('n');
        }
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initBishopOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new BishopChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        if(chessComponent.getChessColor()==ChessColor.BLACK){
            chessComponent.setName('B');
        }else{
            chessComponent.setName('b');
        }
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initQueenOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new QueenChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        if(chessComponent.getChessColor()==ChessColor.BLACK){
            chessComponent.setName('Q');
        }else{
            chessComponent.setName('q');
        }
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initKingOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new KingChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        if(chessComponent.getChessColor()==ChessColor.BLACK){
            chessComponent.setName('K');
        }else{
            chessComponent.setName('k');
        }
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }

    private void initPawnOnBoard(int row, int col, ChessColor color) {
        ChessComponent chessComponent = new PawnChessComponent(new ChessboardPoint(row, col), calculatePoint(row, col), color, clickController, CHESS_SIZE);
        if(chessComponent.getChessColor()==ChessColor.BLACK){
            chessComponent.setName('P');
        }else{
            chessComponent.setName('p');
        }
        chessComponent.setVisible(true);
        putChessOnBoard(chessComponent);
    }
    public Chessboard (List<String> chessboard,int width, int height,List<Record> retractLiat) {
        this.retractLiat = retractLiat;
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            String s = chessboard.get(i);
            for (int j = 0; j < 8; j++) {
                char a = s.charAt(j);
                switch (a) {
                    case ('P') -> initPawnOnBoard(i,j,ChessColor.BLACK);
                    case ('p') -> initPawnOnBoard(i,j,ChessColor.WHITE);
                    case ('B') -> initBishopOnBoard(i,j,ChessColor.BLACK);
                    case ('b') -> initBishopOnBoard(i,j,ChessColor.WHITE);
                    case ('N') -> initKnightOnBoard(i,j,ChessColor.BLACK);
                    case ('n') -> initKnightOnBoard(i,j,ChessColor.WHITE);
                    case ('R') -> initRookOnBoard(i,j,ChessColor.BLACK);
                    case ('r') -> initRookOnBoard(i,j,ChessColor.WHITE);
                    case ('K') -> initKingOnBoard(i,j,ChessColor.BLACK);
                    case ('k') -> initKingOnBoard(i,j,ChessColor.WHITE);
                    case ('Q') -> initQueenOnBoard(i,j,ChessColor.BLACK);
                    case ('q') -> initQueenOnBoard(i,j,ChessColor.WHITE);
                    default -> {
                    }
                }
            }
        }
        if (chessboard.get(8).charAt(0) == 'w') {
            currentColor = ChessColor.WHITE;
        } else if (chessboard.get(8).charAt(0) == 'b') {
            currentColor = ChessColor.BLACK;
        }
    }
    public Chessboard (List<String> chessboard,int width, int height) {
        //this.retractLiat = retractLiat;
        setLayout(null); // Use absolute layout.
        setSize(width, height);
        CHESS_SIZE = width / 8;
        System.out.printf("chessboard size = %d, chess size = %d\n", width, CHESS_SIZE);
        initiateEmptyChessboard();
        for (int i = 0; i < 8; i++) {
            String s = chessboard.get(i);
            for (int j = 0; j < 8; j++) {
                char a = s.charAt(j);
                switch (a) {
                    case ('P') -> initPawnOnBoard(i,j,ChessColor.BLACK);
                    case ('p') -> initPawnOnBoard(i,j,ChessColor.WHITE);
                    case ('B') -> initBishopOnBoard(i,j,ChessColor.BLACK);
                    case ('b') -> initBishopOnBoard(i,j,ChessColor.WHITE);
                    case ('N') -> initKnightOnBoard(i,j,ChessColor.BLACK);
                    case ('n') -> initKnightOnBoard(i,j,ChessColor.WHITE);
                    case ('R') -> initRookOnBoard(i,j,ChessColor.BLACK);
                    case ('r') -> initRookOnBoard(i,j,ChessColor.WHITE);
                    case ('K') -> initKingOnBoard(i,j,ChessColor.BLACK);
                    case ('k') -> initKingOnBoard(i,j,ChessColor.WHITE);
                    case ('Q') -> initQueenOnBoard(i,j,ChessColor.BLACK);
                    case ('q') -> initQueenOnBoard(i,j,ChessColor.WHITE);
                    default -> {
                    }
                }
            }
        }
        if (chessboard.get(8).charAt(0) == 'w') {
            currentColor = ChessColor.WHITE;
        } else if (chessboard.get(8).charAt(0) == 'b') {
            currentColor = ChessColor.BLACK;
        }
    }
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
    }

    private Point calculatePoint(int row, int col) {
        return new Point(col * CHESS_SIZE, row * CHESS_SIZE);
    }

    public void loadGame(List<String> chessboard) {
        chessboard.forEach(System.out::println);

    }

    public boolean check(ChessComponent a,ChessComponent b) {
        if (a.getChessColor() == ChessColor.BLACK) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < chessComponents[i][j].getCanMoves().size(); k++) {
                        if (chessComponents[i][j] == b) {
                            return true;
                        }
                        if ((chessComponents[i][j].getChessColor() == ChessColor.WHITE)&&(chessComponents[i][j].getCanMoves().get(k) instanceof KingChessComponent)&&(chessComponents[i][j].getCanMoves().get(k).getChessColor() == ChessColor.BLACK)) {
                            return false;
                        }
                    }
                }
            }
        } else if (a.getChessColor() == ChessColor.WHITE) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < chessComponents[i][j].getCanMoves().size(); k++) {
                        if (chessComponents[i][j] == b) {
                            return true;
                        }
                        if ((chessComponents[i][j].getChessColor() == ChessColor.BLACK)&&(chessComponents[i][j].getCanMoves().get(k) instanceof KingChessComponent)&&(chessComponents[i][j].getCanMoves().get(k).getChessColor() == ChessColor.WHITE)){
                            return false;
                        }
                    }
                }
            }
        } else {
            return true;
        }
        return true;
    }
}
