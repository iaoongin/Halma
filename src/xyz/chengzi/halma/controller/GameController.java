package xyz.chengzi.halma.controller;

import xyz.chengzi.halma.consts.PlayerNum;
import xyz.chengzi.halma.listener.InputListener;
import xyz.chengzi.halma.listener.SettingListener;
import xyz.chengzi.halma.listener.StatusListener;
import xyz.chengzi.halma.model.ChessBoard;
import xyz.chengzi.halma.model.ChessBoardLocation;
import xyz.chengzi.halma.model.ChessPiece;
import xyz.chengzi.halma.view.ChessBoardComponent;
import xyz.chengzi.halma.view.ChessComponent;
import xyz.chengzi.halma.view.GameFrame;
import xyz.chengzi.halma.view.SquareComponent;

import java.awt.*;
import java.util.Random;

public class GameController implements InputListener, SettingListener {
    private ChessBoardComponent view;
    private ChessBoard model;
    private GameFrame gameFrameView;

    private ChessBoardLocation selectedLocation;
    private Color currentPlayer;

    private Color[] colors = {};

    private StatusListener statusListener;

    public GameController(GameFrame gameFrameView, ChessBoardComponent chessBoardComponent, ChessBoard chessBoard) {
        this.gameFrameView = gameFrameView;
        this.view = chessBoardComponent;
        this.model = chessBoard;
        view.registerListener(this);
        model.registerListener(view);

        this.gameFrameView.registerListener(this);

        statusListener = gameFrameView;
    }

    public ChessBoardLocation getSelectedLocation() {
        return selectedLocation;
    }

    public void setSelectedLocation(ChessBoardLocation location) {
        this.selectedLocation = location;
    }

    public void resetSelectedLocation() {
        setSelectedLocation(null);
    }

    public boolean hasSelectedLocation() {
        return selectedLocation != null;
    }

    public Color nextPlayer() {

        int length = colors.length;
        for (int i = 0; i < length; i++) {
            if (colors[i].equals(currentPlayer)) {
                Color color = currentPlayer = colors[(i + 1) % length];
                this.statusListener.setCurrentPlayerStatus(this.currentPlayer);
                return color;
            }
        }

        throw new RuntimeException("异常.");

    }

    @Override
    public void onPlayerClickSquare(ChessBoardLocation location, SquareComponent component) {
        if (hasSelectedLocation() && model.isValidMove(getSelectedLocation(), location)) {
            model.moveChessPiece(selectedLocation, location);
            resetSelectedLocation();
            nextPlayer();
        }
    }

    @Override
    public void onPlayerClickChessPiece(ChessBoardLocation location, ChessComponent component) {
        ChessPiece piece = model.getChessPieceAt(location);
        if (piece.getColor() == currentPlayer && (!hasSelectedLocation() || location.equals(getSelectedLocation()))) {
            if (!hasSelectedLocation()) {
                setSelectedLocation(location);
            } else {
                resetSelectedLocation();
            }
            component.setSelected(!component.isSelected());
            component.repaint();
        }
    }

    @Override
    public void onSettingPlayerNums(PlayerNum playerNum) {
        this.colors = getColors(playerNum);
        this.currentPlayer = getRandom(this.colors);
        this.model.placeInitPieces(playerNum, this.colors);
        this.resetSelectedLocation();
        this.statusListener.setCurrentPlayerStatus(this.currentPlayer);
    }

    private Color[] getColors(PlayerNum playerNum) {
        Color[] colors;
        switch (playerNum) {
            case TOW:
            default:
                colors = new Color[]{Color.RED, Color.GREEN};
                break;
            case FOUR:
                colors = new Color[]{Color.RED, Color.YELLOW, Color.GREEN, Color.BLUE};
                break;
        }

        return colors;
    }

    public Color getRandom(Color[] colors) {
        Random random = new Random();
        int i = random.nextInt(colors.length);
        return colors[i];
    }

}
