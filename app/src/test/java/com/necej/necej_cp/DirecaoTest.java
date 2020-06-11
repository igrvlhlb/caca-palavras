package com.necej.necej_cp;

import com.necej.necej_cp.jogo_utils.Dificuldades;
import com.necej.necej_cp.jogo_utils.Direcao;

import org.junit.Test;
import org.junit.Before;
import static org.junit.Assert.*;

public class DirecaoTest {
    Direcao dir;
    @Before
    public void setUp(){
        dir = new Direcao();
    }

    @Test
    public void copiaDirecaoTest(){
        Direcao nova = new Direcao(dir);
        assertEquals(dir, nova);
    }

    @Test (expected = NullPointerException.class)
    public void copiaDirecaoNullTest(){
        Direcao nul = null;
        Direcao nova = new Direcao(nul);
    }

    @Test
    public void rndDirDificuldades(){
        for(Dificuldades dif : Dificuldades.values()) {
            for (int i = 0; i < 100; i++) {
                dir.rndDir(dif);
                assertFalse(dir.getDx() == 0 && dir.getDy() == 0);
                switch (dif) {
                    case FACIL:
                        assertFalse(dir.getDx() < 0 || dir.getDy() < 0);
                        break;
                    case INTERMEDIARIO:
                        assertFalse( dir.getDx() < 0 && dir.getDy() < 0);
                        break;
                    default:
                }
            }
        }
    }
}
