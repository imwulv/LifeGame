package com.wulv;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        initPanel();
    }

    private static void initPanel() {
        MyPanel p = new MyPanel();
        p.setBackground(Color.DARK_GRAY);
        Thread panelThread = new Thread(p);
        JFrame frame = new JFrame();
        frame.add(p);
        frame.setSize(Constants.WORLD_WIDTH, Constants.WORLD_HEIGHT);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setTitle("生命游戏");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panelThread.start();
    }
}
