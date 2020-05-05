package xyz.chengzi.halma.listener;

import xyz.chengzi.halma.model.ChessBoardLocation;
import xyz.chengzi.halma.view.ChessComponent;
import xyz.chengzi.halma.view.SquareComponent;

/**
 * 输入监听
 */
public interface InputListener extends Listener {

    /**
     * 点击格子
     * @param location
     * @param component
     */
    void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component);


    /**
     * 点击棋子
     * @param location
     * @param component
     */
    void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component);
}
