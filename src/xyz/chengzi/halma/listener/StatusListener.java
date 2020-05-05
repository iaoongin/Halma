package xyz.chengzi.halma.listener;

import java.awt.*;

/**
 * 状态监听器
 *
 * @author xiaohongxin
 * @version 1.0.0
 * @date 2020/5/4 15:42
 */
public interface StatusListener extends Listener {

    void setCurrentPlayerStatus(Color color);

}
