package com.necej.necej_cp.jogo_utils;

import java.util.Random;

public class Coord {
    private int x,y; //private garante que so serao alterados pelos metodos apropriados
	
    //metodo construtor auxiliar que copia outra instancia de Coord
    public Coord(Coord c){
    	this.x=c.x;
	this.y=c.y;
    }
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Coord(int x, int y){
        this.x=x;
        this.y=y;
    }

    public Coord(){
        this.x=0;
        this.y=0;
    }

    public Coord tracaReta(int len, Direcao dir){
        return new Coord(this.x+(dir.getDx()*(len-1)),this.y+(dir.getDy()*(len-1)));
    }

    /**
     * reseta valores de x e y. Essa funcao deve ser chamada pela mudaLoc()
     * @param xlim  limite em x
     * @param ylim  limite em y
     */
    public void rndLoc(int xlim, int ylim){
        Random rnd = new Random();
        this.x = rnd.nextInt(xlim);
        this.y = rnd.nextInt(ylim);
    }
    @Override
    public String toString(){
        //return new String(String.valueOf('('+this.x+','+this.y+')'));
        return String.format("(%d, %d)",this.y,this.x);
    }
}
