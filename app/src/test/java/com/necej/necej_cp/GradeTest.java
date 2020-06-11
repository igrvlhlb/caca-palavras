package com.necej.necej_cp;

import com.necej.necej_cp.exceptions.TamanhoInvalidoException;
import com.necej.necej_cp.jogo_utils.Dificuldades;
import com.necej.necej_cp.jogo_utils.Grade;
import com.necej.necej_cp.jogo_utils.Palavra;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Logger;

import static org.junit.Assert.assertTrue;

public class GradeTest {
    private Grade gradeTeste;
    private final String[] palavras = {"ola", "mundo", "teste", "java", "android"};

    @Test (expected = TamanhoInvalidoException.class)
    public void tamanhoInvalidoTest() throws TamanhoInvalidoException {
        gradeTeste = new Grade(0,0, Dificuldades.FACIL);
    }
    @Test
    public void palavrasInseridasTest() throws TamanhoInvalidoException {
        for(int rep = 0; rep < 30; rep++) {
            for (Dificuldades dif : Dificuldades.values()) {
                gradeTeste = new Grade(6, 6, dif);
                Logger.getAnonymousLogger().info("Inicializou grade");
                gradeTeste.inserePalavras(new ArrayList<String>(Arrays.asList(palavras)));
                Logger.getAnonymousLogger().info("Inseriu palavras");
                for (Palavra palavra : gradeTeste.getInseridas()) {
                    Palavra tmp = gradeTeste.contemPalavra(palavra.getInicio(), palavra.getFim());
                    assertTrue(palavra.equals(tmp));
                }
            }
        }
    }
}
