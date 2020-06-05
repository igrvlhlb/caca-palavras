package com.necej.necej_cp.jogo_utils;

import java.util.Random;

class Direcao{
    private int dx, dy;
    private final int[] vals = {-1,0,1};
    Random rnd = new Random();

    public Direcao(){this.rndDir();}

    //copia 'd' para uma nova instancia de Direcao
    public Direcao(Direcao d){
    	this.dx = d.dx;
	    this.dy = d.dy;
    }

    public void rndDir(){
        do {
            this.dx = this.vals[rnd.nextInt(3)];
            this.dy = this.vals[rnd.nextInt(3)];
        } while ((dx==0 && dy==0)); //pelo menos dx ou pelo menos dy deve ser diferente de zero
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }
}
