package xyz.chengzi.halma.model;

import xyz.chengzi.halma.consts.PlayerNum;
import xyz.chengzi.halma.listener.GameListener;
import xyz.chengzi.halma.listener.Listenable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 棋盘
 */
public class ChessBoard implements Listenable<GameListener> {

    /**
     * 棋盘监听器
     */
    private List<GameListener> listenerList = new ArrayList<>();

    /**
     * 棋盘的 每个方快
     */
    private Square[][] grid;

    /**
     * 棋盘的维度
     */
    private int dimension;

    public ChessBoard(int dimension) {
        this.grid = new Square[dimension][dimension];
        this.dimension = dimension;

        initGrid();
    }

    private void initGrid() {
        for (int i = 0; i < dimension; i++) {
            for (int j = 0; j < dimension; j++) {
                grid[i][j] = new Square(new ChessBoardLocation(i, j));
            }
        }
    }

    public void placeInitPieces(PlayerNum playerNum, Color[] colors) {

        initGrid();

        switch (playerNum) {
            case TOW:
            default:
                initTowPlayer(colors);
                break;
            case FOUR:
                initFourPlayer(colors);
                break;
        }

        for (int row = 0; row < this.dimension; row++) {
            for (int col = 0; col < this.dimension; col++) {
                ChessBoardLocation location = new ChessBoardLocation(row, col);
                listenerList.forEach(listener -> listener.onChessPieceRemove(location));
            }
        }

        listenerList.forEach(listener -> listener.onChessBoardReload(this));
    }

    public void initTowPlayer(Color[] colors) {
        int[] jNmus = {5, 5, 4, 3, 2};
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < jNmus[i]; j++) {
                grid[i][j].setPiece(new ChessPiece(colors[0]));
            }
        }

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < jNmus[i]; j++) {
                grid[dimension - 1 - i][dimension - 1 - j].setPiece(new ChessPiece(colors[1]));
            }
        }

        listenerList.forEach(listener -> listener.onChessBoardReload(this));
    }

    public void initFourPlayer(Color[] colors) {

        int[] jNmus = {4, 4, 3, 2};
        for (int i = 0; i < jNmus.length; i++) {
            for (int j = 0; j < jNmus[i]; j++) {
                grid[i][j].setPiece(new ChessPiece(colors[0]));
            }
        }

        for (int i = 0; i < jNmus.length; i++) {
            for (int j = 0; j < jNmus[i]; j++) {
                grid[i][dimension - 1 - j].setPiece(new ChessPiece(colors[1]));
            }
        }

        for (int i = 0; i < jNmus.length; i++) {
            for (int j = 0; j < jNmus[i]; j++) {
                grid[dimension - 1 - i][dimension - 1 - j].setPiece(new ChessPiece(colors[2]));
            }
        }

        for (int i = 0; i < jNmus.length; i++) {
            for (int j = 0; j < jNmus[i]; j++) {
                grid[dimension - 1 - i][j].setPiece(new ChessPiece(colors[3]));
            }
        }

        listenerList.forEach(listener -> listener.onChessBoardReload(this));
    }

    public Square getGridAt(ChessBoardLocation location) {
        return grid[location.getRow()][location.getColumn()];
    }

    public ChessPiece getChessPieceAt(ChessBoardLocation location) {
        return getGridAt(location).getPiece();
    }

    public void setChessPieceAt(ChessBoardLocation location, ChessPiece piece) {
        getGridAt(location).setPiece(piece);
        listenerList.forEach(listener -> listener.onChessPiecePlace(location, piece));
    }

    public ChessPiece removeChessPieceAt(ChessBoardLocation location) {
        ChessPiece piece = getGridAt(location).getPiece();
        getGridAt(location).setPiece(null);
        listenerList.forEach(listener -> listener.onChessPieceRemove(location));
        return piece;
    }

    public void moveChessPiece(ChessBoardLocation src, ChessBoardLocation dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal halma move");
        }
        setChessPieceAt(dest, removeChessPieceAt(src));
    }

    public int getDimension() {
        return dimension;
    }

    public boolean isValidMove(ChessBoardLocation src, ChessBoardLocation dest, Object... jumpArr) {

        // 判断是否已经 跨过一次 棋子。
        boolean hasJump = jumpArr.length > 0;

        if (!hasJump && (getChessPieceAt(src) == null || getChessPieceAt(dest) != null)) {
            return false;
        }

        // 获取四面八方的棋子
        Square[][] adjacentSquares = getAdjacentSquares(src);

//        printIt(adjacentSquares);

        // 获取四面八方的棋子
        for (Square[] adjacentSquare : adjacentSquares) {
            for (Square square : adjacentSquare) {
                if (hasJump) {
                    if (square == null) {   // null // 跳过一次就false
                        return false;
                    } else {
                        if (square.getPiece() != null) { // chess
//                        if (dest.equals(square.getLocation()))
                            ChessBoardLocation jump = jump(src, square.getLocation());
                            if (jump == null) { // 跳过一次就false
                                return false;
                            }
                            // jump 是否已存在
                            ChessPiece chessPieceAt = getChessPieceAt(jump);

                            if (chessPieceAt != null) { // 跳过一次就false
                                return false;
                            }
                            if (jump.equals(dest)) { // 跳过一次就true
                                return true;
                            }

                            if (isValidMove(jump, dest, 1)) {
                                return true;
                            }
                        } else {     // empty // 跳过一次就false
                            if (dest.equals(square.getLocation())) {
                                return false;
                            }
                        }
                    }
                } else {
                    if (square == null) {   // null // 没跳过就continue
                        continue;
                    } else {
                        if (square.getPiece() != null) { // chess
//                        if (dest.equals(square.getLocation()))
                            ChessBoardLocation jump = jump(src, square.getLocation());
                            if (jump == null) { // 没跳过就continue
                                continue;
                            }
                            // jump 是否已存在
                            ChessPiece chessPieceAt = getChessPieceAt(jump);

                            if (chessPieceAt != null) { // 没跳过就continue
                                continue;
                            }
                            if (jump.equals(dest)) {
                                return true;
                            }

                            if (isValidMove(jump, dest, 1)) {
                                return true;
                            }
                        } else {     // empty
                            if (dest.equals(square.getLocation())) { // 没跳过就true
                                return true;
                            }
                        }
                    }
                }

            }
        }

        return false;
    }

    private ChessBoardLocation jump(ChessBoardLocation from, ChessBoardLocation adjacent) {

        int row = 2 * adjacent.getRow() - from.getRow();
        int column = 2 * adjacent.getColumn() - from.getColumn();

        if (isValidate(row, column)) {
            return new ChessBoardLocation(row, column);
        } else {
            return null;
        }

    }

    private void printIt(Square[][] adjacentSquares) {
        for (Square[] adjacentSquare : adjacentSquares) {
            for (Square square : adjacentSquare) {
                if (square == null) {
                    System.out.printf("%5s\t", null);
                } else {
                    if (square.getPiece() != null) {
                        System.out.printf("%5s\t", "chess");
                    } else {
                        System.out.printf("%5s\t", "empty");
                    }

                }
            }
            System.out.println();
        }
    }

    private Square[][] getAdjacentSquares(ChessBoardLocation src) {

        int row = src.getRow();
        int column = src.getColumn();

        Square[][] array = new Square[3][3];

        array[0][0] = getSquareAt(row - 1, column - 1);
        array[0][1] = getSquareAt(row - 1, column);
        array[0][2] = getSquareAt(row - 1, column + 1);

        array[1][0] = getSquareAt(row, column - 1);
        array[1][2] = getSquareAt(row, column + 1);

        array[2][0] = getSquareAt(row + 1, column - 1);
        array[2][1] = getSquareAt(row + 1, column);
        array[2][2] = getSquareAt(row + 1, column + 1);

        return array;
    }

    private ChessPiece getChessPieceAt(int row, int column) {
        if (!isValidate(row, column)) {
            return null;
        }
        ChessPiece chessPieceAt = this.getChessPieceAt(new ChessBoardLocation(row, column));
        return chessPieceAt;
    }

    private boolean isValidate(int row, int column) {
        return !(row < 0 || row >= this.dimension || column < 0 || column >= this.dimension);
    }

    private Square getSquareAt(int row, int column) {
        if (!isValidate(row, column)) {
            return null;
        }
        return getGridAt(new ChessBoardLocation(row, column));
    }

    @Override
    public void registerListener(GameListener listener) {
        listenerList.add(listener);
    }

    @Override
    public void unregisterListener(GameListener listener) {
        listenerList.remove(listener);
    }

}
