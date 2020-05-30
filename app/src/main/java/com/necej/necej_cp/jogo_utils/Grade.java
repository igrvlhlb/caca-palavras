package com.necej.necej_cp.jogo_utils;

import android.util.Log;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Logger;

/**
 * Classe para a armazenar o objeto caca palavras.
 * Metodos para geracao e verificacao
 */
public class Grade {

    private final int MAX_TENTATIVAS = 20;
    private final float razaoMinima = 0.75f; //minimo aceito de palavras inseridas/totais
    private int mLin, mCol;
    char[][] mat; //matriz de caracteres
    private ArrayList<Palavra> inseridas; //palavras que ja conseguimos inserir

    public Grade(int lin, int col){
        int i, j;
        this.mLin = lin;
        this.mCol = col;
        this.mat = new char[lin][col];
        inicializaMat();
    }

    public boolean inserePalavras(ArrayList<String> entrada){
        Collections.shuffle(entrada);   //embaralha as palavras
        for(int tentativa = 0; tentativa < MAX_TENTATIVAS; tentativa++) { //repete o processo de tentativa de insercao ate 'MAX_TENTATIVA' vezes
            inseridas = new ArrayList<>();
            for (String str : entrada) {
                str=str.toUpperCase();  //todas os caracteres da matriz sao maiusculos
                Palavra palavraAtual = new Palavra(str);
                if (tryInsert(palavraAtual)) {
                    Log.i(this.getClass().getSimpleName(),palavraAtual.strPalavra + " INSERIDA");
                    this.inseridas.add(palavraAtual);
                    for(int t = 0; t < palavraAtual.length; t++){
                        this.mat[palavraAtual.inicio.getY()+(t*palavraAtual.direcao.getDy())]
                                [palavraAtual.inicio.getX()+(t*palavraAtual.direcao.getDx())] = palavraAtual.strPalavra.charAt(t);
                    }
                }
            }
            Log.i(this.getClass().getSimpleName(), "FIM DE RONDA " + tentativa +
                    "("+((float)inseridas.size()/(float)entrada.size())+")");
            //se nao conseguir inserir o minimo necessario, repete
            if( ((float)inseridas.size()/(float)entrada.size()) >= razaoMinima ) return true;
        }
        return false;
    }

    /**
     * Verifica se ha possibilidade de inserir a palavra em uma posicao da matriz
     * @param nova a palavra que queremos inserir na matriz
     * @return true se conseguiu inserir e false caso contrario
     */
    private boolean tryInsert(Palavra nova){
        for(int tentativa = 0; tentativa < this.MAX_TENTATIVAS; tentativa++){ //tenta inserir uma palavra ate 'MAX_TENTATIVA' vezes

            do {    //garante que e possivel posicionar a palavra na matriz, sem considerar outras palavras
                nova.mudaLoc(this.mLin,this.mCol);    //atribui valores randomicos a posicao inicial
                nova.mudaDir();                     //atribui valores randomicos
            }while(!validoPos(nova));               //verifica se a posicao do atributo 'final' e valida e repete  caso nao seja

            if(!this.inseridas.isEmpty()) {
                boolean falhou = false; //variavel auxiliar que informa se o loop terminou normalmente ou foi interrompido peli 'break'
                for (Palavra inserida : inseridas) { //verifica validade da tentativa, considerando todas as outras palavras ja inseridas

                    //if (boundingBoxesIntersect(nova, inserida)) { //verifica se ha possibilidade de intersecao entre as palavras
                    //    if(!podeBruto(nova, inserida)) falhou = true; //verifica, posicao por posicao, se ha intersecao destrutiva ou nao
                    //}
                    /*COMENTEI O BLOCO ACIMA POIS NAO E NECESSARIO*/
                    if(!podeBruto(nova, inserida)) falhou = true;
                }
                if(!falhou) return true;
            } else return true; //se a posicao e valida e nao ha outras palavras na matriz podemos inserir a atual
        }
        Log.e(this.getClass().getSimpleName(), nova.strPalavra + "NAO FOI INSERIDA");
        return false; //nao foi possivel inserir
    }



    /**
     * determina se os segmentos de reta (trataremos as palavras como retas aqui) *podem* se intersectar.
     * Consideramos que cada segmento de reta e a diagonal de um retangulo e caso os retangulos que os contem
     * se intersectarem, *pode* hever intersecao dos segmentos
     * @return retorna true se ha possibilidade de intersecao e false caso contrario
     */
    private boolean boundingBoxesIntersect(Palavra a, Palavra b){
	Palavra tmpa = new Palavra(a), tmpb = new Palavra(b); //fazemos copias das palavras originais, ja que o estado original delas e importante!
	tmpa.ajusta(); //no plano cartesiano, x0 <= x1. Para que a verificacao funcione,
	tmpb.ajusta(); //devemos ajustar o 'inicio' e 'fim' das palavras, ja que na pratica pode ocorrer inicio.x > fim.x
        return (tmpa.inicio.getX() <= tmpb.fim.getX()) && (tmpa.fim.getX() >= tmpb.inicio.getX())
		&& (tmpa.inicio.getY() <= tmpb.fim.getY()) && (tmpa.fim.getY() >= tmpb.inicio.getY());
    }



    /**
     * itera sobre as letras de cada palavra e verifica se ha intersecao e se e valida
     * @param nova palavra que queremos inserir
     * @param exist existente, palavra que ja foi inserida
     * @return true se a tentativa for valida; false caso contrario
     */
    private boolean podeBruto(Palavra nova, Palavra exist){
        int xN=nova.inicio.getX(),yN=nova.inicio.getY();      //componentes x e y de 'nova'
        int xE,yE;                                  //componentes x e y de 'exist'
        for(int t = 0; t < nova.length; t++){
            xE=exist.inicio.getX();
            yE=exist.inicio.getY();
            for(int r = 0; r<exist.length; r++){
                if( (xE==xN) && (yE==yN) ){
                    //ha intersecao!!
                    if(nova.charAt(t)!=exist.charAt(r)) return false; //intersecao invalida
                }
                xE+=exist.direcao.getDx();
                yE+=exist.direcao.getDy();
            }
            xN+=nova.direcao.getDx();
            yN+=nova.direcao.getDy();
        }
        //se a execucao chegou aqui, nao foram encontradas intersecoes ou as intersecoes sao validas
        return true;
    }

    //verifica se a extremidade 'fim' esta dentro da matriz e, portanto, e valida
    private boolean validoPos(Palavra palavra){
        int x = palavra.fim.getX();
        int y = palavra.fim.getY();
        return (x>=0) && (x<this.mCol) && (y>=0) && (y<this.mLin)
                && (x!=palavra.inicio.getX() || y!=palavra.inicio.getY());
    }

    public void mPrint(){
        StringBuilder sb = new StringBuilder((this.mCol *2));
        for(char[] s : this.mat) {
            for(char c : s){
                sb.append(c);
                sb.append(' ');
            }
            System.out.println(sb);
            sb.delete(0,sb.length());
        }

    }
    private void inicializaMat(){
        int i;
        Random rnd = new Random();
        for(i = 0; i < this.mLin *this.mCol; i++){
            this.mat[i/this.mCol][i%this.mCol] = (char) (rnd.nextInt(26) + 0x41);
        }
    }

    int getmLin() {
        return mLin;
    }

    int getmCol() {
        return mCol;
    }

    //debug
    public String getInseridas(){
        StringBuilder sb = new StringBuilder(100);
        for(Palavra p : inseridas){
            sb.append(p.strPalavra).append('\n');
        }
        sb.append(inseridas.size()+" PALAVRAS FORAM INSERIDAS");
        return new String(sb);
    }
}
