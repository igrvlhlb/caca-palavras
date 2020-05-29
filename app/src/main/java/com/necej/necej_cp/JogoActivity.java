package com.necej.necej_cp;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.necej.necej_cp.jogo_utils.Grade;
import com.necej.necej_cp.jogo_utils.Tabuleiro;
import com.necej.necej_cp.jogo_utils.TabuleiroView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Logger;

public class JogoActivity extends AppCompatActivity {

    int mImageSize;
    TabuleiroView mTabuleiroView;
    Tabuleiro mTabuleiro;
    Grade mGrade;
    private String mDificuldade;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jogo);
        mDificuldade = getIntent().getExtras().getString(TelaInicialActivity.EXTRA_DIFICULDADE);
        Logger.getAnonymousLogger().info(String.format("EXTRA_DIFICULDADE %s\n", mDificuldade));
        //this.hideBar();
        mTabuleiroView = findViewById(R.id.tabuleiro_image);
        mGrade = new Grade(12,12);
        gradeInit();
        this.criaTabuleiro();
    }

    private void hideBar(){
        try {
            ActionBar tmpAct = getSupportActionBar();
            if (tmpAct != null) tmpAct.hide();
        } catch (Exception e) {
            Logger.getAnonymousLogger().info("ERRO AO TENTAR OBTER REFERENCIA PARA ACTIONBAR");
        }
    }
    private void criaTabuleiro(){
        mTabuleiro = new Tabuleiro(mGrade);
        mTabuleiroView.init(mTabuleiro);
    }
    public int dpToPx(int dp) {
        float density = this.getResources()
                .getDisplayMetrics()
                .density;
        return Math.round((float) dp * density);
    }
    public int pxToDp(int px){
        float density = this.getResources()
                .getDisplayMetrics().density;
        return Math.round((float)px/density);
    }
    //teste
    private void gradeInit(){
        ArrayList<String> palavras;
        palavras = new ArrayList<>(Arrays.asList(new String[] {
                "palavra",
                "teste",
                "passou",
                "pequena",
                "algomaior",
                "fim",
                "pandemia",
                "bacteria",
                "coronavirus",
                "epidemia",
                "celula",
                "cromossomo",
                "fagocitose",
                "rna",
                "biologia",
                "microbio",
                "imunologia",
                "lagartixa"
        }));
        mGrade.inserePalavras(palavras);
    }
}
