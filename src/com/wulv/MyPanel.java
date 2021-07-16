package com.wulv;

import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyPanel extends JPanel implements Runnable {
    //初始细胞位置
    private static int[][] orginMapList = new int[Constants.WIDTH_SIZE][Constants.HEIGHT_SIZE];
    //用于绘制的缓存数组
    private static int[][] newMapList = new int[Constants.WIDTH_SIZE][Constants.HEIGHT_SIZE];

    @Override
    public void paint(Graphics g) {
        //每次循环清空用于绘制的缓存二维数组
        newMapList = new int[Constants.WIDTH_SIZE][Constants.HEIGHT_SIZE];
        //循环原始数组
        for(int x = 1; x < orginMapList.length - 1; x++) {
            for(int y = 1; y < orginMapList[x].length - 1; y++) {
                //计算周围活体细胞数量
                int sum = orginMapList[x-1][y-1] + orginMapList[x][y-1] + orginMapList[x+1][y-1]
                        + orginMapList[x-1][y]                          + orginMapList[x+1][y]
                        + orginMapList[x-1][y+1] + orginMapList[x][y+1] + orginMapList[x+1][y+1];
                //当前死亡
                //死亡情况下，周围活体细胞数量为3，则在本位置繁殖一个新的细胞
                if(orginMapList[x][y] == 0 && sum == 3){
                    //注意，这里是更新往新的数组中，如果在原始数组中更新会有连锁反应，导致绘图结果和预期不一致
                    newMapList[x][y] = 1;
                }
                //当前存活
                if(orginMapList[x][y] == 1){

                    //当前位置周围活体细胞数量少于2时，孤独而死。
                    //当前位置周围活体细胞数量大于3时，拥挤而死。
                    if( sum < 2 || sum > 3 ){

                        //注意，这里是更新往新的数组中，如果在原始数组中更新会有连锁反应，导致绘图结果和预期不一致
                        newMapList[x][y] = 0;

                    //因为是新的用于绘制的数组，所以正常活体细胞也要同步过去
                    } else {

                        //注意，这里是更新往新的数组中，如果在原始数组中更新会有连锁反应，导致绘图结果和预期不一致
                        newMapList[x][y] = 1;
                    }
                }
            }
        }

        //将用于绘制的二维数组赋值给原始数组，方便下次计算，这语句也可以放在绘制方法之后，无影响。
        orginMapList = newMapList;

        //绘制方法
        for(int x = 0; x < newMapList.length; x++) {
            for(int y = 0; y < newMapList[x].length; y++) {

                //根据细胞状态设置颜色
                if(newMapList[x][y] == 1){
                    g.setColor(Constants.LIVING_CELL);
                }
                if(newMapList[x][y] == 0){
                    g.setColor(Constants.DEAD_CELL);
                }

                //绘制细胞：矩形，位置根据二维数组所在位置计算得出
                g.fillRect(x * Constants.SQUARE_SIZE,y * Constants.SQUARE_SIZE,Constants.SQUARE_SIZE,Constants.SQUARE_SIZE);
            }
        }
    }


    /**
     * 初始化二维数组
     */
    private void initOrginMapList(){
        //滑翔者
        orginMapList[10][10] = 1;
        orginMapList[12][9] = 1;
        orginMapList[12][10] = 1;
        orginMapList[11][11] = 1;
        orginMapList[12][11] = 1;

        //随机生成活体细胞 要用时放开即可
//        for (int i = 0 ; i < Constants.LIFE_NUM; i ++){
//            int widthMax=Constants.WIDTH_SIZE,heightMax=Constants.HEIGHT_SIZE,min=1;
//            int x = (int) (Math.random()*(widthMax-min)+min);
//            int y = (int) (Math.random()*(heightMax-min)+min);
//            //确保下标不越界
//            if(x + 1 > Constants.WIDTH_SIZE || y + 1 >Constants.HEIGHT_SIZE){continue;}
//            orginMapList[x][y] = 1;
//        }
    }

    /*===============自动进程===============*/


    private final TimerTask mainTask = new TimerTask() {
        @Override
        public void run() {
            MyPanel.this.repaint();
        }
    };

    @Override
    public void run() {
        initOrginMapList();
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleAtFixedRate(mainTask, 0 , 100, TimeUnit.MILLISECONDS);
    }
}
