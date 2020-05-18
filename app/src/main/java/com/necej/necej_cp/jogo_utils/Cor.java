package com.necej.necej_cp.jogo_utils;

import java.util.Random;

public class Cor {
    int R,G,B,A;
    Random rnd = new Random();
    StringBuilder sb = new StringBuilder(8);
    public int getRandomColor(int alpha){
        A = alpha;
        R = rnd.nextInt(0xff);
        G = rnd.nextInt(0xff);
        B = rnd.nextInt(0xff);
        sb.append(A)
                .append(R)
                .append(G)
                .append(B);
        return Integer.parseInt(sb.toString());
    }
}
