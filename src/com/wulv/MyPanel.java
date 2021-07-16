package com.wulv;

import javax.swing.*;
import java.awt.*;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MyPanel extends JPanel implements Runnable {

    static int[][] orginMapList = new int[Constants.WIDTH_SIZE][Constants.HEIGHT_SIZE];
    static int[][] newMapList = new int[Constants.WIDTH_SIZE][Constants.HEIGHT_SIZE];
    public MyPanel() {
        super();

        this.setBackground(Color.DARK_GRAY);
    }


    public static int worldTime = 0;

    @Override
    public void paint(Graphics g) {
        newMapList = new int[Constants.WIDTH_SIZE][Constants.HEIGHT_SIZE];
        for(int x = 1; x < orginMapList.length - 1; x++) {
            for(int y = 1; y < orginMapList[x].length - 1; y++) {
                int sum = orginMapList[x-1][y-1] + orginMapList[x][y-1]+orginMapList[x+1][y-1]
                        + orginMapList[x-1][y] + orginMapList[x+1][y]
                        + orginMapList[x-1][y+1] + orginMapList[x][y+1]+orginMapList[x+1][y+1];
                //当前死亡
                if(orginMapList[x][y] == 0 && sum == 3){
                    newMapList[x][y] = 1;
                }
                //当前存活
                if(orginMapList[x][y] == 1){
                    if( sum < 2 || sum > 3 ){
                        newMapList[x][y] = 0;
                    } else {
                        newMapList[x][y] = 1;
                    }
                }
            }
        }

        orginMapList = newMapList;

        for(int x = 0; x < newMapList.length; x++) {
            for(int y = 0; y < newMapList[x].length; y++) {
                if(newMapList[x][y] == 1){
                    g.setColor(Color.WHITE);
                }
                if(newMapList[x][y] == 0){
                    g.setColor(Color.BLACK);
                }
                g.fillRect(x * Constants.SQUARE_SIZE,y * Constants.SQUARE_SIZE,Constants.SQUARE_SIZE,Constants.SQUARE_SIZE);
            }
        }
    }


    /**
     * 初始化二维数组
     */
    private void initOrginMapList(){
//        //滑翔者
        orginMapList[10][10] = 1;
        orginMapList[12][9] = 1;
        orginMapList[12][10] = 1;
        orginMapList[11][11] = 1;
        orginMapList[12][11] = 1;

//        for (int i = 0 ; i < Constants.LIFE_NUM; i ++){
//            int widthMax=Constants.WIDTH_SIZE,heightMax=Constants.HEIGHT_SIZE,min=1;
//            int x = (int) (Math.random()*(widthMax-min)+min);
//            int y = (int) (Math.random()*(heightMax-min)+min);
//            if(x + 1 > Constants.WIDTH_SIZE || y + 1 >Constants.HEIGHT_SIZE){continue;}
//            orginMapList[x][y] = 1;
//        }
    }

    /*===============自动进程===============*/


    final TimerTask mainTask = new TimerTask() {
        @Override
        public void run() {

            MyPanel.this.repaint();
            worldTime++;
        }
    };

    @Override
    public void run() {
        initOrginMapList();
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);

        pool.scheduleAtFixedRate(mainTask, 0 , 100, TimeUnit.MILLISECONDS);
        //timer.schedule(new MyTimerTask(), 0, 10);
    }
}
