package com.necej.necej_cp.jogo_utils;

public enum Dificuldades {
    FACIL("Fácil"),INTERMEDIARIO("Intermediário"),DIFICIL("Difícil"),MUITO_DIFICIL("Muito Difícil");
    final String val;
    Dificuldades(String s) {
        val = s;
    }

    public String getVal() {
        return val;
    }
    public static Dificuldades fromString(String str){
        for(Dificuldades d : Dificuldades.values()){
            if(d.val.equals(str)) return d;
        }
        return null;
    }
}
