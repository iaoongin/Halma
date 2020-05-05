package xyz.chengzi.halma.consts;

/**
 * 游戏人数
 */
public enum PlayerNum {
    TOW("二人运动"), FOUR("四人运动");

    String title;

    PlayerNum(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}