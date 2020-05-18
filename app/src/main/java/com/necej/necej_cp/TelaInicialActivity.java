package com.necej.necej_cp;

import com.necej.necej_cp.listeners.BotaoDiffListener;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.Arrays;
import java.util.List;

public class TelaInicialActivity extends AppCompatActivity {

    BotaoDiffListener botaoDiffListener;
    Button botaoDiff;
    public static final String EXTRA_DIFICULDADE = "com.necej.necej_cp.EXTRA_DIFICULDADE";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_inicial);
        botaoDiff = (Button)findViewById(R.id.botao_dificuldade);
        botaoDiffListener = new BotaoDiffListener(this, botaoDiff);
        botaoDiff.setOnClickListener(botaoDiffListener);
    }
    //callback para botao comeca
    public void comecaJogo(View v){
        Intent intent = new Intent(this, JogoActivity.class);
        intent.putExtra(this.EXTRA_DIFICULDADE, botaoDiffListener.getDifAtual());
        startActivity(intent);
    }

}
