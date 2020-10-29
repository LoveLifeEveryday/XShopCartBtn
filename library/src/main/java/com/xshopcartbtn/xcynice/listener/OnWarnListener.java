package com.xshopcartbtn.xcynice.listener;


/**
 * @Author 许朋友爱玩
 * @Date 2020/10/29 22:02
 * @Github https://github.com/LoveLifeEveryday
 * @JueJin https://juejin.im/user/5e429bbc5188254967066d1b/posts
 * @Description 警告的监听器
 */

public interface OnWarnListener {
    /**
     * 超过的库存限制
     *
     * @param inventory 库存限制
     */
    void onWarningForInventory(int inventory);

    /**
     * 超过的最大购买数限制
     *
     * @param max 最大购买数
     */
    void onWarningForBuyMax(int max);

    /**
     * 低于最小购买数
     *
     * @param min 最小购买数
     */
    void onWarningForBuyMin(int min);
}
