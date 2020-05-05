package xyz.chengzi.halma;

import xyz.chengzi.halma.controller.GameController;
import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.view.ChessBoardComponent;
import xyz.chengzi.halma.view.GameFrame;

import javax.swing.*;

import static xyz.chengzi.halma.consts.SettingConst.BOARD_DIMENSION;
import static xyz.chengzi.halma.consts.SettingConst.BOARD_WIDTH;

public class Halma {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessBoardComponent chessBoardComponent = new ChessBoardComponent(BOARD_WIDTH, BOARD_DIMENSION);
            ChessBoard chessBoard = new ChessBoard(BOARD_DIMENSION);
            GameFrame mainFrame = new GameFrame();

            GameController controller = new GameController(mainFrame, chessBoardComponent, chessBoard);

            mainFrame.add(chessBoardComponent);
            mainFrame.setVisible(true);
        });
    }
}
