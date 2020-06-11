package com.necej.necej_cp;

import com.necej.necej_cp.jogo_utils.Dificuldades;
import com.necej.necej_cp.jogo_utils.Palavra;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class PalavraTest {
    Palavra palavraTeste;
    String sTeste;
    char[] cTeste;
    @Before
    public void setUp(){
        sTeste = "Testando";
        cTeste = sTeste.toCharArray();
        palavraTeste = new Palavra(sTeste);
    }

    @Test
    public void equalsStringLit(){
        assertEquals(palavraTeste.toString(),sTeste);
    }

    @Test
    public void equalsTypeInit(){
        Palavra string = new Palavra(sTeste),
        character = new Palavra(cTeste);
        assertEquals(string, character);
    }

    @Test
    public void equalsRevTest(){
        Palavra rev = new Palavra(new StringBuilder(sTeste).reverse().toString());
        assertEquals(palavraTeste,rev);
    }

    @Test
    public void rndDirTest(){
        for(Dificuldades dif : Dificuldades.values()) {
            for (int i = 0; i < 100; i++) {
                palavraTeste.mudaDir(dif);
                assertFalse(palavraTeste.getDir().getDx() == 0
                        && palavraTeste.getDir().getDy() == 0);
                switch (dif) {
                    case FACIL:
                        assertFalse(palavraTeste.getDir().getDx() < 0 || palavraTeste.getDir().getDy() < 0);
                        break;
                    case INTERMEDIARIO:
                        assertFalse( palavraTeste.getDir().getDx() < 0 && palavraTeste.getDir().getDy() < 0);
                        break;
                    default:
                }
            }
        }
    }
}
