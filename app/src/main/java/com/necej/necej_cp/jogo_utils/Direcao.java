package com.necej.necej_cp.jogo_utils;

import java.util.Random;

class Direcao{
    private int dx, dy;
    private final int[] vals = {-1,0,1};

    public Direcao(){this.rndDir();}

    //copia 'd' para uma nova instancia de Direcao
    public Direcao(Direcao d){
    	this.dx = d.dx;
	    this.dy = d.dy;
    }

    public void rndDir(){
        Random rnd = new Random();
        this.dx = this.vals[rnd.nextInt(3)];
        this.dy = this.vals[rnd.nextInt(3)];
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
