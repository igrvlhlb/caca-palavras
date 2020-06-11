package com.necej.necej_cp.jogo_utils;

import android.graphics.Point;

public class Palavra {
    String strPalavra;
    Coord inicio, fim;
    Direcao direcao;
    private boolean mMarcada;
    int length; //atributo apenas para facilitar a checagem do tamanho da string

    public Palavra(String str){
        this.strPalavra = str;
        this.length=str.length();
        this.direcao = new Direcao();
        this.inicio = new Coord();
        atualiza();
        mMarcada = false;
    }

    public Palavra(String str, Dificuldades dif){
        this.strPalavra = str;
        this.length=str.length();
        this.direcao = new Direcao(dif);
        this.inicio = new Coord();
        atualiza();
        mMarcada = false;
    }

    public Palavra(char[] str){
        this(new String(str));
    }

    public Palavra(char[] str, Dificuldades dif){
        this(new String(str), dif);
    }

    //copia atributos de p para 'this'
    public Palavra(Palavra p){
        this.inicio = new Coord(p.inicio);
        this.fim = new Coord(p.fim);
        this.direcao = new Direcao(p.direcao);
        this.strPalavra = new String(p.strPalavra);
        this.length=this.strPalavra.length();
    }

    public Palavra(Grade g, Point ini, Point fim){
        this.inicio = new Coord(ini);
        this.fim = new Coord(fim);
        leChars(g);
        //atualiza();
    }

    public void setLoc(int lin, int col) {
        this.inicio=new Coord(lin,col);
        this.atualiza(); //atualiza 'fim' de acordo com 'inicio' e 'direcao'
    }


    @Override
    public String toString(){
        return strPalavra;
    }

    public void mudaDir(Dificuldades dif){
        try {
            this.direcao.rndDir(dif);
        } catch(NullPointerException e){
            e.printStackTrace();
            this.direcao=new Direcao(dif);
        }
        /*try {
            //this.atualiza();
        } catch (NullPointerException e) {
            //ja e garantido que 'direcao' nao e nulo, entao se encontramos uma excecao, 'inicio' e nulo
            //entao nao atualizamos o objeto
        }*/
    }

    public void mudaLoc(int linhas, int colunas){
        try {
            this.inicio.rndLoc(colunas, linhas);
        } catch(NullPointerException e){
            this.inicio = new Coord(); //ao gerar uma nova instancia de Coord, ja temos valores randomicos
        }
        /*try {
            //this.atualiza();
        } catch(NullPointerException e){
            this.direcao=new Direcao();
            this.atualiza(); //como sempre estaremos lidando com atributos randomicos, e seguro chamar atualiza()
        }*/
    }

    void atualiza() throws NullPointerException{
        if(this.inicio != null && this.direcao!=null) {
            this.fim = inicio.tracaReta(this.length, this.direcao);
        } else if(this.fim != null && this.inicio != null){
            this.direcao = new Direcao((fim.x-inicio.x) != 0 ? (fim.x - inicio.x) / Math.abs(fim.x - inicio.x) : 0,
                    (fim.y-inicio.y) != 0 ? (fim.y - inicio.y) / Math.abs(fim.y - inicio.y) : 0);
        }
    }

    //verifica se x0>x1 e ajusta as coordenadas caso seja verdade
    public void ajusta(){
        if(this.inicio.getX() > this.fim.getX()){
            this.swpCoords();
        }
    }

    private void swpCoords(){
        Coord tmp = this.inicio;
        this.inicio = this.fim;
        this.fim = tmp;
    }

    //apenas *conveniencia*
    public char charAt(int idx){
        return strPalavra.charAt(idx);
    }

    public void setMarcada(boolean bool) {
        mMarcada = bool;
    }

    public boolean getMarcada(){
        return mMarcada;
    }

    public Point getInicio(){
        return this.inicio;
    }

    public Point getFim(){
        return this.fim;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StringBuilder sb = new StringBuilder(strPalavra);
        String rev = sb.reverse().toString();
        Palavra palavra = (Palavra) o;
        return strPalavra.equals(palavra.strPalavra) || rev.equals(palavra.strPalavra);
    }

    public Direcao getDir(){
        return direcao;
    }

    private void leChars(Grade grade){
        StringBuilder sb = new StringBuilder();
        int dX, dY;
        char[][] mat = grade.mat;
        dX = this.fim.x - this.inicio.x;
        dY = this.fim.y - this.inicio.y;
        int max = Math.max(Math.abs(dX), Math.abs(dY));
        int duX = dX == 0 ? 0 : dX/Math.abs(dX),
                duY = dY == 0 ? 0 : dY/Math.abs(dY);
        int step = 0, atualX = inicio.x, atualY = inicio.y;
        while(step <= max){
            sb.append(mat[atualY][atualX]);
            atualX += duX;
            atualY += duY;
            step++;
        }
        strPalavra = sb.toString();
    }

    private void setStrPalavra(String str){
        strPalavra = str;
        length = str.length();
    }
}
