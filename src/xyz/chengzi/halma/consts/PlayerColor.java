package xyz.chengzi.halma.consts;

import java.awt.*;
import java.util.Random;

/**
 * TODO
 *
 * @author xiaohongxin
 * @version 1.0.0
 * @date 2020/5/4 14:42
 */
public enum PlayerColor {
    P1(Color.RED),
    P2(Color.GREEN),
    P3(Color.YELLOW),
    P4(Color.BLUE),
    ;
    private Color color;

    PlayerColor(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    public static String getName(Color color) {
        if (Color.RED.equals(color)) {
            return "红色";
        } else if (Color.GREEN.equals(color)) {
            return "绿色";
        } else if (Color.YELLOW.equals(color)) {
            return "黄色";
        } else if (Color.BLUE.equals(color)) {
            return "蓝色";
        }
        return "未知.";
    }
}
