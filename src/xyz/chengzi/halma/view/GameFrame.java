package xyz.chengzi.halma.view;

import xyz.chengzi.halma.consts.PlayerColor;
import xyz.chengzi.halma.consts.PlayerNum;
import xyz.chengzi.halma.listener.Listenable;
import xyz.chengzi.halma.listener.SettingListener;
import xyz.chengzi.halma.listener.StatusListener;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static xyz.chengzi.halma.consts.SettingConst.*;

/**
 * 游戏面板
 */
public class GameFrame extends JFrame implements Listenable<SettingListener>, StatusListener {

    private List<SettingListener> listeners = new ArrayList<>();

    private JLabel settingStatus;
    private JLabel currentPlayerLabel;

    public GameFrame() {

        setTitle("多人跳棋");
        setSize(FRAME_WIDTH, BOARD_WIDTH + 20);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLayout(null);
        setResizable(false);

        // 设置
        int y = 0;
        JLabel settingTitleLabel = new JLabel("选择多人运动");
        settingTitleLabel.setLocation(BOARD_WIDTH, y += 10);
        settingTitleLabel.setSize(200, BTN_HEIGHT);
        add(settingTitleLabel);

        PlayerNum[] values = PlayerNum.values();
        for (PlayerNum value : values) {
            JButton button = new JButton(value.getTitle());
            button.addActionListener((e) -> {
                this.setPlayModeStatus(value);
                System.out.println(value);
                listeners.forEach(listener -> listener.onSettingPlayerNums(value));
            });

            button.setLocation(BOARD_WIDTH, y += BTN_HEIGHT + MARGIN);
            button.setSize(BTN_WIDTH, BTN_HEIGHT);
            add(button);
        }

        // 游戏人数状态
        y = playNumStatus(y);

        // 当前轮到谁
        whoIsTurnStatus(y);
    }

    private int playNumStatus(int y) {
        JLabel settingTitleLabel2 = new JLabel("当前运动：");
        settingTitleLabel2.setLocation(BOARD_WIDTH, y += BTN_HEIGHT + MARGIN);
        settingTitleLabel2.setSize(210, BTN_HEIGHT);
        add(settingTitleLabel2);

        this.settingStatus = new JLabel("");
        this.settingStatus.setLocation(BOARD_WIDTH, y += BTN_HEIGHT + MARGIN);
        this.settingStatus.setSize(210, BTN_HEIGHT);
        add(this.settingStatus);
        return y;
    }

    private void whoIsTurnStatus(int y) {
        JLabel settingTitleLabel2 = new JLabel("当前轮到：");
        settingTitleLabel2.setLocation(BOARD_WIDTH, y += BTN_HEIGHT + MARGIN);
        settingTitleLabel2.setSize(210, BTN_HEIGHT);
        add(settingTitleLabel2);

        this.currentPlayerLabel = new JLabel("");
        this.currentPlayerLabel.setLocation(BOARD_WIDTH, y += BTN_HEIGHT + MARGIN);
        this.currentPlayerLabel.setSize(210, BTN_HEIGHT);
        add(this.currentPlayerLabel);
    }

    @Override
    public void registerListener(SettingListener listener) {
        listeners.add(listener);
    }

    @Override
    public void unregisterListener(SettingListener listener) {
        listeners.remove(listener);
    }

    public void setPlayModeStatus(PlayerNum playerNum) {
        settingStatus.setText(playerNum.getTitle());
        settingStatus.repaint();
    }

    @Override
    public void setCurrentPlayerStatus(Color color) {
        this.currentPlayerLabel.setText(PlayerColor.getName(color));
    }
}
