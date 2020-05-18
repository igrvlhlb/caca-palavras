package com.necej.necej_cp;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.ViewGroup;

import com.necej.necej_cp.jogo_utils.Grade;
import com.necej.necej_cp.jogo_utils.Tabuleiro;
import com.necej.necej_cp.jogo_utils.TabuleiroView;

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
        this.hideBar();
        mTabuleiroView = findViewById(R.id.tabuleiro_image);
        mGrade = new Grade(10,10);
        this.enquadraImageView();
    }

    private void hideBar(){
        try {
            ActionBar tmpAct = getSupportActionBar();
            if (tmpAct != null) tmpAct.hide();
        } catch (Exception e) {
            Logger.getAnonymousLogger().info("ERRO AO TENTAR OBTER REFERENCIA PARA ACTIONBAR");
        }
    }
    private void enquadraImageView(){
        Point p = new Point();
        getWindowManager().getDefaultDisplay().getSize(p);
        mImageSize = Math.min(p.x,p.y);
        mTabuleiro = new Tabuleiro(mGrade, mImageSize, mImageSize);
        mTabuleiroView.init(mTabuleiro);
        ViewGroup.LayoutParams layoutPar = this.mTabuleiroView.getLayoutParams();
        //layoutPar.width = this.pxToDp(this.imageSize);
        //layoutPar.height = this.pxToDp(this.imageSize);
        layoutPar.width = this.mImageSize;
        layoutPar.height = this.mImageSize;
        this.mTabuleiroView.setLayoutParams(layoutPar);
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
}
