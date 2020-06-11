package com.necej.necej_cp.jogo_utils;

import java.util.Objects;
import java.util.Random;

public class Direcao{
    private int dx, dy;
    private final int[] vals = {-1,0,1};
    Random rnd = new Random();

    public Direcao(){this.rndDir();}

    //copia 'd' para uma nova instancia de Direcao
    public Direcao(Direcao d) throws NullPointerException{
        if(d == null) throw new NullPointerException();
        this.dx = d.dx;
        this.dy = d.dy;
    }

    public void rndDir(){
        rndDir(true);
    }

    public void rndDir(boolean signed){
        do {
            this.dx = this.vals[1 + rnd.nextInt(2)];
            this.dy = this.vals[1 + rnd.nextInt(2)];
        } while ((dx==0 && dy==0)); //pelo menos dx ou pelo menos dy deve ser diferente de zero
    }

    public int getDx() {
        return dx;
    }

    public int getDy() {
        return dy;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Direcao direcao = (Direcao) o;
        return dx == direcao.dx &&
                dy == direcao.dy;
    }
    public void rndDir(String dif){
        switch (Dificuldades.fromString(dif)){
            case FACIL:
                rndDir(false);
                break;
            case INTERMEDIARIO:
                do {
                    rndDir(true);
                } while (!(dx >= 0));
                break;
            default:
                //caso DIFICIL ou MUITO_DIFICIL, nao ha restricoes
                this.rndDir();
        }
    }
}