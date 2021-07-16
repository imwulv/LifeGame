package com.wulv;

import java.awt.*;

public class Constants {

    //二维数组长度宽
    public static final int WIDTH_SIZE = 1024;
    public static final int HEIGHT_SIZE = 1024;
    //控制绘制的细胞大小
    public static final int SQUARE_SIZE = 5;
    //根据二维数组自动调整窗口大小
    public static final int WORLD_WIDTH = WIDTH_SIZE * SQUARE_SIZE;
    public static final int WORLD_HEIGHT = HEIGHT_SIZE * SQUARE_SIZE;
    //随机生成存活细胞的个数
    public static final int LIFE_NUM = 1000000;

    //细胞颜色
    public static final Color DEAD_CELL = Color.BLACK;
    public static final Color LIVING_CELL = Color.WHITE;

}
