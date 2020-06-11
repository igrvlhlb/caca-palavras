package com.necej.necej_cp.jogo_utils;

import android.graphics.Point;

import com.necej.necej_cp.exceptions.TamanhoInvalidoException;

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
    private final float razaoMinima = 0.3f; //minimo aceito de palavras inseridas/totais
    private final float razaoMaxima = 0.6f;
    private int mLin, mCol, matSize, mLetrasInseridas;
    char[][] mat; //matriz de caracteres
    private ArrayList<Palavra> inseridas; //palavras que ja conseguimos inserir
    private ArrayList<String> raw_inseridas;
    private Dificuldades mDificuldade;
    private final int minTamanho = 6;

    public Grade(int lin, int col, Dificuldades dificuldade) throws TamanhoInvalidoException {
        if(lin<minTamanho || col<minTamanho) throw new TamanhoInvalidoException("O tamanho minimo para um tabuleiro e" + minTamanho);
        this.mLin = lin;
        this.mCol = col;
        this.matSize = lin*col;
        this.mat = new char[lin][col];
        mDificuldade = dificuldade;
        inicializaMat();
    }

    public boolean inserePalavras(ArrayList<String> entrada){
        Collections.shuffle(entrada);   //embaralha as palavras
        String tmp;
        for(int tentativa = 0; tentativa < MAX_TENTATIVAS; tentativa++) { //repete o processo de tentativa de insercao ate 'MAX_TENTATIVA' vezes
            inseridas = new ArrayList<>();
            raw_inseridas = new ArrayList<>();
            int letrasInseridas = 0;
            for (String str : entrada) {
                if(str.length()>Math.max(mLin,mCol)) continue;
                tmp = new String(str);
                str=str.toUpperCase();  //todas os caracteres da matriz sao maiusculos
                Palavra palavraAtual = new Palavra(str, mDificuldade);
                if( !( ( ( (float)letrasInseridas+palavraAtual.length) / (float)matSize ) > razaoMaxima)) {
                    if (tryInsert(palavraAtual)) {
                        //Log.i(this.getClass().getSimpleName(), palavraAtual.strPalavra + " INSERIDA");
                        Logger.getAnonymousLogger().info(palavraAtual.strPalavra + " INSERIDA");
                        this.inseridas.add(palavraAtual);
                        this.raw_inseridas.add(tmp);
                        letrasInseridas += palavraAtual.length;
                    }
                }
            }
            /*Log.i(this.getClass().getSimpleName(), "FIM DE RONDA " + tentativa +
                    "("+((float)letrasInseridas/(float)matSize)+"["+
                    (float)letrasInseridas + "/" + (float)matSize + "])");
            */
            Logger.getAnonymousLogger().info("FIM DE RONDA " + tentativa +
                    "("+((float)letrasInseridas/(float)matSize)+"["+
                    (float)letrasInseridas + "/" + (float)matSize + "])");
            //se nao conseguir inserir o minimo necessario, repete
            mLetrasInseridas = letrasInseridas;
            if( ((float)letrasInseridas/(float)matSize) >= razaoMinima ) {
                escrevePalavras();
                //Log.i(getClass().getSimpleName(), String.valueOf(raw_inseridas));
                return true;
            };
        }
        //Log.i(getClass().getSimpleName(), String.valueOf(raw_inseridas));
        escrevePalavras();
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
                nova.mudaDir(mDificuldade);                     //atribui valores randomicos
                nova.atualiza();
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
        //Log.e(this.getClass().getSimpleName(), nova.strPalavra + "NAO FOI INSERIDA");
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
        return (x>=0) && (x<this.mCol) && (y>=0) && (y<this.mLin);
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

    public ArrayList<Palavra> getInseridas(){
        return new ArrayList<>(inseridas);
    }

    //debug
    public String getInseridasAsString(){
        StringBuilder sb = new StringBuilder(100);
        for(Palavra p : inseridas){
            sb.append(p.strPalavra).append(" ").append(p.inicio).append('\n');
        }
        sb.append(inseridas.size()+" PALAVRAS FORAM INSERIDAS ("+ mLetrasInseridas + " LETRAS)");
        return new String(sb);
    }

    /**
     * getter para obter as strings originais inseridas
     * @return lista de palavras inseridas sem modificacoes
     */
    public ArrayList<String> getInseridasAsList(){
        return raw_inseridas;
    }

    private void escrevePalavras(){
        for(Palavra palavraAtual : inseridas) {
            for (int t = 0; t < palavraAtual.length; t++) {
                this.mat[palavraAtual.inicio.getY() + (t * palavraAtual.direcao.getDy())]
                        [palavraAtual.inicio.getX() + (t * palavraAtual.direcao.getDx())] = palavraAtual.strPalavra.charAt(t);
            }
        }
    }
    /*
    public String contemPalavra(Point ini, Point fim){
        StringBuilder sb;
        String res;
        int dX, dY;
        dX = fim.x-ini.x;
        dY = fim.y-ini.y;
        //se a variacao de x e y sao ambas 0 ou se ambas nao sao 0, mas tem modulos diferentes. esses sao os unicos casos invalidos
        if( (dX==0 && dY==0) || ( (dX!=0 && dY!=0) && (Math.abs(dX) != Math.abs(dY)) ) ) return null;
        sb = leChars(ini,dX,dY); //a linha acima garante que 'sb' nao sera null
        Log.i(getClass().getSimpleName(), String.valueOf(sb));
        res = procuraPalavra(sb);
        Log.i(getClass().getSimpleName(), res == null ? "NAO ENCONTRADA" : res + "ENCONTRADA");
        return res;
    }

    private StringBuilder leChars(Point ini, int dX, int dY){
        StringBuilder sb = new StringBuilder();
        int max = Math.max(Math.abs(dX), Math.abs(dY));
        int duX = dX == 0 ? 0 : dX/Math.abs(dX),
                duY = dY == 0 ? 0 : dY/Math.abs(dY);
        int step = 0, atualX = ini.x, atualY = ini.y;
        while(step <= max){
            sb.append(mat[atualY][atualX]);
            atualX += duX;
            atualY += duY;
            step++;
        }
        return sb;
    }

    private String procuraPalavra(StringBuilder sb){
        if(sb!=null) {
            String str = sb.toString(), rev = sb.reverse().toString();
            for (Palavra p : inseridas) {
                if (str.equals(p.strPalavra) && !p.getMarcada()) return str;
                else if (rev.equals(p.strPalavra) && !p.getMarcada()) return rev;
            }
        }
        return null;
    }*/
    public Palavra contemPalavra(Point ini, Point fim){
        boolean res;
        int dX, dY;
        dX = (int)fim.x-ini.x;
        dY = (int)fim.y-ini.y;
        Palavra tmp;
        //se a variacao de x e y sao ambas 0 ou se ambas nao sao 0, mas tem modulos diferentes. esses sao os unicos casos invalidos
        if( (dX==0 && dY==0) || ( (dX!=0 && dY!=0) && (Math.abs(dX) != Math.abs(dY)) ) ) return null;
        tmp = new Palavra(this,ini,fim);
        //Log.i(getClass().getSimpleName(), tmp.toString());
        res = procuraPalavra(tmp);
        //Log.i(getClass().getSimpleName(), res == false ? "NAO ENCONTRADA" : tmp + "ENCONTRADA");
        if (res) {

            return tmp;
        } else return null;
    }
    private boolean procuraPalavra(Palavra palavra){
        if(palavra!=null) {
            for (Palavra p : inseridas) {
                if (palavra.equals(p) && !p.getMarcada()){
                    p.setMarcada(true);
                    return true;
                }
            }
        }
        return false;
    }

    public void removeString(String s){
        for(Palavra p : inseridas){
            if(s.equals(p.strPalavra)) p.setMarcada(true);
        };
    }
    public void removePalavra(Palavra teste){
        for(Palavra p : inseridas){
            if(teste.equals(p)) p.setMarcada(true);
        };
    }

}
