package com.necej.necej_cp.listeners;

import android.content.Context;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.necej.necej_cp.R;

import java.util.Arrays;
import java.util.List;

public class BotaoDiffListener implements View.OnClickListener {

    private final String texDif;
    private List<String> dificuldades;
    private int idxDif = 0;
    private String difAtual;
    private StringBuilder difSB;

    public BotaoDiffListener(Context context, Button butDif){
        texDif = context.getResources().getString(R.string.dificuldade);
        dificuldades = Arrays.asList(context.getResources().getStringArray(R.array.texto_dificuldade));
        difAtual = dificuldades.get(idxDif);
        difSB = new StringBuilder(texDif);
        difSB.append(difAtual);
        butDif.setText(difSB);
        ((AppCompatActivity)context).findViewById(R.id.botao_dificuldade);
        difSB.delete(texDif.length(),difSB.length());
    }

    @Override
    public void onClick(View v) {
        idxDif = (idxDif+1)%dificuldades.size();
        difAtual = dificuldades.get(idxDif);
        difSB.append(difAtual);
        ((Button)v).setText(difSB);
        difSB.delete(texDif.length(),difSB.length());
    }

    public String getDifAtual() {
        return difAtual;
    }
}
