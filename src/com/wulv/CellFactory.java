package com.wulv;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class CellFactory {
    //位置偏移量，
    static final int shiftNum = 10;
    static final URL file = Thread.currentThread().getContextClassLoader().getResource("00celllist.txt");
    static final List<String> fileList = new ArrayList<String>();

    static {
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(new File(file.getPath()),"r");
            String s ;
            while((s = raf.readLine())!=null){
                fileList.add(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void setCellListByFileName(String fileName,int[][] orginMapList){
        RandomAccessFile raf = null;
        try {
            raf = new RandomAccessFile(new File(Thread.currentThread().getContextClassLoader().getResource(fileName).getPath()),"r");
            String s ;
            int x = 0;
            while((s = raf.readLine())!=null){

                if (s.startsWith("!")){ continue;}

                int y = 0;
                for (char c : s.toCharArray()) {
                    if(String.valueOf(c).equals("O")){

                        //这里将x，i 加上偏移量，以确保细胞生成在相对考中间的位置。
                        orginMapList[x+shiftNum][y+shiftNum] = 1;
                    }
                    y++;
                }
                x++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                raf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
