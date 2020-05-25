package com.necej.necej_cp.jogo_utils;

import android.graphics.Color;

import java.util.Random;

public class Cor {
    private int R,G,B,A;
    Random rnd = new Random();
    //StringBuilder sb;
    public int getRandomColor(int alpha){
        //sb = new StringBuilder(8);
        A = alpha;
        R = rnd.nextInt(0xff);
        G = rnd.nextInt(0xff);
        B = rnd.nextInt(0xff);
        /*sb.append(String.valueOf(A))
                .append(String.valueOf(R))
                .append(String.valueOf(G))
                .append(String.valueOf(B));*/
        return Color.argb(A,R,G,B);
    }
}
