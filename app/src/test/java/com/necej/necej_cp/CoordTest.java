package com.necej.necej_cp;
import com.necej.necej_cp.exceptions.CoordInvalidaException;
import com.necej.necej_cp.jogo_utils.Coord;
import com.necej.necej_cp.jogo_utils.Direcao;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class CoordTest {
    Coord coordTest;
    @Before
    public void setUp(){
        coordTest = new Coord();
    }

    @Test
    public void copiaTeste(){
        Coord backUp = new Coord(coordTest);
        Coord secondCoord = new Coord(coordTest.getX(), coordTest.getY());
        assertEquals(backUp, coordTest);
        assertEquals(secondCoord,coordTest);
    }

    @Test
    public void TesteCriacaoInvalida() throws CoordInvalidaException {
        coordTest = new Coord(-1,-1);
    }

    @Test
    public void tracaRetaNullTest(){
        Direcao dir = null;
        assertEquals(coordTest.tracaReta(0, dir),null);
    }

    @Test
    public void tracaRetaTest() {
        for(int len = 0; len < 100; len++) {
            Coord nova;
            Direcao dir = new Direcao();
            nova = coordTest.tracaReta(len, dir);
            assertEquals(new Coord(
                    coordTest.getX() + len*dir.getDx(),
                    coordTest.getY() + len*dir.getDy()), nova);
        }
    }
}
