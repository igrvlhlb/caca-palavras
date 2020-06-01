package com.necej.necej_cp.listeners;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.necej.necej_cp.R;
import com.necej.necej_cp.jogo_utils.Dificuldades;

public class BotaoDiffListener implements View.OnClickListener {

    private final String texDif;
    //private List<String> dificuldades;
    private Dificuldades[] dificuldades;
    private int idxDif = 0;
    private Dificuldades difAtual;
    private StringBuilder difSB;

    public BotaoDiffListener(Context context, Button butDif){
        texDif = context.getResources().getString(R.string.dificuldade);
        //dificuldades = Arrays.asList(context.getResources().getStringArray(R.array.texto_dificuldade));
        dificuldades = Dificuldades.values();
        difAtual = dificuldades[idxDif];
        difSB = new StringBuilder(texDif);
        difSB.append(difAtual.getVal());
        butDif.setText(difSB);
        ((AppCompatActivity)context).findViewById(R.id.botao_dificuldade);
        difSB.delete(texDif.length(),difSB.length());
    }

    @Override
    public void onClick(View v) {
        idxDif = (idxDif+1)%dificuldades.length;
        difAtual = dificuldades[idxDif];
        difSB.append(difAtual.getVal());
        ((Button)v).setText(difSB);
        difSB.delete(texDif.length(),difSB.length());
        Log.i(getClass().getSimpleName(),getDifAtual().name());
    }

    public Dificuldades getDifAtual() {
        return difAtual;
    }
}
