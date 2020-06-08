package com.necej.necej_cp.listeners;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.necej.necej_cp.jogo_utils.Cor;
import com.necej.necej_cp.jogo_utils.Tabuleiro;
import com.necej.necej_cp.jogo_utils.TabuleiroView;

public class TabuleiroListener {
    private TabuleiroView mTabView;
    private Tabuleiro mTabuleiro;
    private Canvas mCanvas;
    private Bitmap mBitmap, mBackUp;
    private Paint mMarcadores;
    private Point mToqueIni, mMovCoord; //coordenadas do toque e da posicao apos o movimento, respectivamente
    private Cor mCor;
    private final int mAlpha = 0x70;

    public TabuleiroListener(TabuleiroView tabuleiroView){
        mTabView = tabuleiroView;
        mTabuleiro = mTabView.getTabuleiro();
        mCanvas = mTabView.getCanvas();
        mBitmap = mTabView.getBitmap();
        mMarcadores = new Paint(Paint.ANTI_ALIAS_FLAG);
        mMarcadores.setStrokeCap(Paint.Cap.ROUND);
        mMarcadores.setStrokeWidth(Math.min(mTabuleiro.getmXGap(),mTabuleiro.getmYGap()));
        mCor = new Cor();
        mToqueIni = new Point();
        mMovCoord = new Point();
    }

    public boolean touch(View v, MotionEvent event) {
        int act = event.getAction();
        float x = event.getX(), y = event.getY();
        Point tmp = mTabuleiro.getCellIdx((int)x, (int)y);

        switch(act){
            case MotionEvent.ACTION_DOWN:
                int color = mCor.getRandomColor(mAlpha);
                mToqueIni.set(tmp.x,tmp.y);
                mMovCoord.set(tmp.x,tmp.y);
                mMarcadores.setColor(color);
                //salva o estado do bitmap associado ao canvas no momento do toque
                mBackUp = Bitmap.createBitmap(mBitmap);
                break;
            case MotionEvent.ACTION_MOVE:
                if(!tmp.equals(mToqueIni)){
                    if(!tmp.equals(mMovCoord)){
                        //restaura estado original (antes do toque) do bitmap
                        restauraBitmap();
                        tracaReta(tmp);
                        mMovCoord.set(tmp.x,tmp.y);
                        mTabView.invalidate();
                    }
                }
                break;
            case MotionEvent.ACTION_UP:
                String selecionada = mTabuleiro.getGrade().contemPalavra(mToqueIni, mMovCoord);
                Log.d(getClass().getSimpleName(), (selecionada==null ? "NENHUMA" : selecionada) + "SELECIONADA");
                if(tmp.equals(mToqueIni) || selecionada == null){
                    restauraBitmap();
                    mTabView.invalidate();
                } else{
                    mTabuleiro.getGrade().removeString(selecionada);
                }
                break;
            default:
                //nada
        }
        return false;
    }

    private void restauraBitmap(){
        mBitmap = Bitmap.createBitmap(mBackUp);
        mTabView.setBitmap(mBitmap);
        mCanvas.setBitmap(mBitmap);
    }
    private void tracaReta(Point dst){
        mCanvas.drawLine(mTabuleiro.getCenterX(mToqueIni.x),
                mTabuleiro.getCenterY(mToqueIni.y),
                mTabuleiro.getCenterX(dst.x),
                mTabuleiro.getCenterY(dst.y), mMarcadores);
    }
}
