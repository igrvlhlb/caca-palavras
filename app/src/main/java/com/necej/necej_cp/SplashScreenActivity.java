package com.necej.necej_cp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

import java.util.logging.Logger;

public class SplashScreenActivity extends AppCompatActivity {
    private final long delay = 100;
    private ImageView logo;
    private Handler handler, myHandler;
    private Point scrSize;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //inicializa objetos
        myHandler = new Handler();
        handler = new Handler();

        //exibe tela de carregamento
        setContentView(R.layout.activity_splash_screen);
        hideBar();
        //ajusta tamanho da imagem
        scrSize=new Point();
        getWindowManager().getDefaultDisplay().getSize(scrSize);
        logo = ((ImageView)findViewById(R.id.fg_logo));
        logo.setMaxWidth(Math.round(scrSize.x*0.4f));
        logo.setMaxHeight(Math.round(scrSize.y*0.3f));
        //usa handlers para criar animacao
        myHandler.post(new Runnable() {
            @Override
            public void run() {
                myHandler.postDelayed(this,delay);
            }
        });
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostraTelaPrincipal();
            }
        }, delay*5);
    }

    private void mostraTelaPrincipal(){
        Intent intent = new Intent(this, TelaInicialActivity.class);
        this.startActivity(intent);
        this.finish();
    }
    private void hideBar(){
        try {
            ActionBar tmpAct = getSupportActionBar();
            if (tmpAct != null) tmpAct.hide();
        } catch (Exception e) {
            Logger.getAnonymousLogger().info("ERRO AO TENTAR OBTER REFERENCIA PARA ACTIONBAR");
        }
    }
}
