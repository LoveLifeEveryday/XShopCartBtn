package com.xshopcartbtn.xcynice.listener;


/**
 * @Author 许朋友爱玩
 * @Date 2020/10/29 22:03
 * @Github https://github.com/LoveLifeEveryday
 * @JueJin https://juejin.im/user/5e429bbc5188254967066d1b/posts
 * @Description 监听值的改变
 */

public interface OnChangeValueListener {
    /**
     * 值改变
     *
     * @param value    值
     * @param position 位置
     */
    void onChangeValue(int value, int position);
}
