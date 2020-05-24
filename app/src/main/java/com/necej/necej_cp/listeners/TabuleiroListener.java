package com.necej.necej_cp.listeners;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import com.necej.necej_cp.jogo_utils.Cor;
import com.necej.necej_cp.jogo_utils.Tabuleiro;
import com.necej.necej_cp.jogo_utils.TabuleiroView;

public class TabuleiroListener implements View.OnTouchListener {
    private TabuleiroView mTabView;
    private Tabuleiro mTabuleiro;
    private Canvas mCanvas;
    private Bitmap mBitmap, mBackUp;
    private Paint mMarcadores;
    Cor mCor;
    public TabuleiroListener(TabuleiroView tabuleiroView){
        mTabView = tabuleiroView;
        mTabuleiro = mTabView.getTabuleiro();
        mCanvas = mTabView.getCanvas();
        mBitmap = mTabView.getBitmap();
        mMarcadores = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMarcadores.setStrokeCap(Paint.Cap.ROUND);
        mMarcadores.setStrokeWidth(Math.min(mTabuleiro.getmXGap(),mTabuleiro.getmYGap()));

    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        int act = event.getAction();
        switch(act){
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                //nada
        }
        return false;
    }
}
