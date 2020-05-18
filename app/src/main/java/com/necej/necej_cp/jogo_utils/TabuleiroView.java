package com.necej.necej_cp.jogo_utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.graphics.fonts.FontStyle;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;

import com.necej.necej_cp.R;

public class TabuleiroView extends View {

    Point mScrSize;
    private int mWidth, mHeight;
    private Canvas mExtraCanvas;
    private Paint mLetras = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mLinhas = new Paint();
    private Paint mMarcadores = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Bitmap mExtraBitmap;
    private int mColorFonteGrade, mColorLinhasGrade, mColorBG;
    private final int mLinhaWidth = 3;
    private Tabuleiro mTabuleiro;
    private boolean mReady = false; //indica se todos os parametros ja foram inicializados

    public TabuleiroView(Context context) {
        super(context);
    }

    public TabuleiroView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabuleiroView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mReady) {
            canvas.drawBitmap(mExtraBitmap, 0, 0, null);
        }
    }
    //TODO: utilizar obj tabuleiro para inicializacao
    public void init(Tabuleiro tabuleiro){
        mReady = true;
        mTabuleiro = tabuleiro;

        mColorFonteGrade = getResources().getColor(R.color.colorFonteGrade);
        mColorLinhasGrade = getResources().getColor(R.color.colorLinhasGrade);
        mColorBG = getResources().getColor(R.color.colorTabBG);
        mLetras.setColor(mColorFonteGrade);
        mLetras.setTextSize( Math.min(tabuleiro.getmXGap(),tabuleiro.getmYGap())*0.75f );
        mLetras.setTextAlign(Paint.Align.CENTER);
        mLetras.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        mLinhas.setColor(mColorLinhasGrade);
        mLinhas.setStrokeWidth(mLinhaWidth);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if(mReady) {
            int uniSz = Math.min(w, h);
            mWidth = uniSz;
            mHeight = uniSz;
            mExtraBitmap = Bitmap.createBitmap(uniSz, uniSz,
                    Bitmap.Config.ARGB_8888);
            mExtraCanvas = new Canvas(mExtraBitmap);
            mExtraCanvas.drawColor(mColorBG);
            desenhaGrade();
            escreveLinhas();
        }
    }
    private void desenhaGrade(){
        int yG,xG;
        yG = mTabuleiro.getmYGap();
        xG = mTabuleiro.getmXGap();
        for(int i = 1; i<mTabuleiro.getmLin(); i++){
            mExtraCanvas.drawLine(0f,(float) (yG*i),(float) mWidth,(float) (yG*i), mLinhas);
        }
        for(int i = 1; i<mTabuleiro.getmCol(); i++){
            mExtraCanvas.drawLine((float) xG*i,0f, (float)xG*i, (float) mHeight,mLinhas);
        }
    }
    private void escreveLinhas(){
        int cY, cX;
        int lin, col;
        for(lin = 0; lin < mTabuleiro.getmLin(); lin++){
            //cY = mTabuleiro.getBaseY(lin) - mTabuleiro.getmYGap()/8;
            cY = (int) (mTabuleiro.getBaseY(lin) - mTabuleiro.getmYGap()*0.25f);
            for(col = 0; col < mTabuleiro.getmCol(); col++){
                cX = mTabuleiro.getCenterX(col);
                mExtraCanvas.drawText(mTabuleiro.getCharAsString(lin,col),cX,cY,mLetras);
            }
        }
    }

}
