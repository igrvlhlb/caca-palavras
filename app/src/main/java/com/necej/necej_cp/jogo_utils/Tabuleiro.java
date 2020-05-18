package com.necej.necej_cp.jogo_utils;

import android.graphics.Point;
import android.graphics.Rect;

import org.jetbrains.annotations.NotNull;

public class Tabuleiro {
    private char[][] mMat;
    private Grade mGrade;
    private int mLin, mCol;
    private int mPX, mPY; //pixels em cada direcao
    private int mXGap, mYGap; //espaco entre duas linhas
    private int mXGC, mYGC; //centro do espaco entre duas linhas

    public Tabuleiro(@NotNull Grade grade, int pX, int pY){
        mGrade = grade;
        mMat = grade.mat;
        mLin = grade.getmLin();
        mCol = grade.getmCol();
        mPX = pX;
        mPY = pY;
        mXGap = pX/mCol;
        mYGap = pY/mLin;
        mXGC = mXGap/2;
        mYGC = mYGap/2;
    }

    public Point getCellIdx(int x, int y){
        int cX, cY;
        cX = x/mXGap;
        cY = y/mYGap;
        return new Point(cX,cY);
    }
    public int getCenterY(int lin){
        return (lin*mYGap) + mYGC;
    }
    public int getCenterX(int col){
        return (col*mXGap) + mXGC;
    }

    public int getmXGap() {
        return mXGap;
    }

    public int getmYGap() {
        return mYGap;
    }

    public int getmLin() {
        return mLin;
    }

    public int getmCol() {
        return mCol;
    }
    public String getCharAsString(int lin, int col){
        return String.valueOf(mMat[lin][col]);
    }
    public int getBaseY(int y){
        return mYGap*(y+1);
    }
}
