package xyz.chengzi.halma.listener;

import xyz.chengzi.halma.consts.PlayerNum;

import java.awt.*;

/**
 * 设置监听器
 *
 * @author xiaohongxin
 * @version 1.0.0
 * @date 2020/5/4 13:57
 */
public interface SettingListener extends Listener {

    void onSettingPlayerNums(PlayerNum playerNum);

}
