package com.necej.necej_cp.jogo_utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.necej.necej_cp.R;
import com.necej.necej_cp.listeners.TabuleiroListener;

public class TabuleiroView extends View {

    private int mWidth, mHeight;
    private Canvas mExtraCanvas;
    private Paint mLetras = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mLinhas = new Paint();
    private Bitmap mExtraBitmap;
    private int mColorFonteGrade, mColorLinhasGrade, mColorBG;
    private final int mLinhaWidth = 3;
    private final float mLetSizePercent = 0.75f;
    private Tabuleiro mTabuleiro;
    private boolean mReady = false; //indica se todos os parametros ja foram inicializados
    private TabuleiroListener mTabListener;

    public TabuleiroView(Context context) {
        super(context);
        initBackground();
    }

    public TabuleiroView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBackground();
    }

    public TabuleiroView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBackground();
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if(mReady && (mHeight > 0 && mWidth > 0)) {
            canvas.drawBitmap(mExtraBitmap, 0, 0, null);
        }
    }

    public void init(Tabuleiro tabuleiro){
        mReady = true;
        mTabuleiro = tabuleiro;
        mColorFonteGrade = getResources().getColor(R.color.colorFonteGrade);
        mColorLinhasGrade = getResources().getColor(R.color.colorLinhasGrade);
        mColorBG = getResources().getColor(R.color.colorTabBG);
        mLetras.setColor(mColorFonteGrade);
        mLetras.setTextAlign(Paint.Align.CENTER);
        mLetras.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.NORMAL));
        mLinhas.setColor(mColorLinhasGrade);
        mLinhas.setStrokeWidth(mLinhaWidth);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        int uniSz = (w==0 || h==0) ? Math.max(w, h) : Math.min(w, h);
        mWidth = uniSz;
        mHeight = uniSz;
        if(mReady && uniSz > 0) {
            //Muda o tamanho da View
            //ViewGroup.LayoutParams layoutPar = this.getLayoutParams();
            //layoutPar.width = mWidth;
            //layoutPar.height = mHeight;
            //this.setLayoutParams(this.getLayoutParams());
            //
            mTabuleiro.setXY(mWidth,mHeight);
            mLetras.setTextSize( (convertDpToPx(Math.min(mTabuleiro.getmXGap(),mTabuleiro.getmYGap())*mLetSizePercent, getContext() )/2)*mLetSizePercent);
            mExtraBitmap = Bitmap.createBitmap(uniSz, uniSz,
                    Bitmap.Config.ARGB_8888);
            mExtraCanvas = new Canvas(mExtraBitmap);
            mExtraCanvas.drawColor(mColorBG);
            desenhaGrade();
            escreveLinhas();
            mTabListener = new TabuleiroListener(this);

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int w = ((ConstraintLayout) getParent()).getMeasuredWidth(),
                h = ((ConstraintLayout) getParent()).getMeasuredHeight();
        Log.i("Parent wodth", String.valueOf(w) + String.valueOf(h));
        int measure = MeasureSpec.makeMeasureSpec(Math.min(h,w),MeasureSpec.EXACTLY);
        Log.i("MEASURE", MeasureSpec.toString(measure));
        super.onMeasure(measure, measure+getPaddingBottom());
        Log.i(getClass().getSimpleName() ,"Measured Height aft super" + MeasureSpec.toString(getMeasuredHeight()));
        Log.i(getClass().getSimpleName() ,"Measured Width aft super" + MeasureSpec.toString(getMeasuredHeight()));
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
            cY = (int) (mTabuleiro.getBaseY(lin) - (mTabuleiro.getmYGap()*((1-mLetSizePercent)/2)));
            for(col = 0; col < mTabuleiro.getmCol(); col++){
                cX = (int) mTabuleiro.getCenterX(col);
                mExtraCanvas.drawText(mTabuleiro.getCharAsString(lin,col),cX,cY,mLetras);
            }
        }
    }
    public static float convertPixelsToDp(float px, Context context){
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
    public static float convertDpToPx(float dp, Context context){
        return dp*((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
    public Tabuleiro getTabuleiro(){
        return mTabuleiro;
    }
    public Canvas getCanvas(){
        return mExtraCanvas;
    }
    public Bitmap getBitmap(){
        return mExtraBitmap;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        super.onTouchEvent(event);
        mTabListener.touch(this,event);
        return true;
    }
    public void setBitmap(Bitmap bmp){
        mExtraBitmap = bmp;
    }
    private void initBackground() {
        setBackground(ViewUtils.generateBackgroundWithShadow(this,R.color.colorTabBG,
                R.dimen.radius_corner,R.color.colorLinhasGrade,R.dimen.elevation, Gravity.BOTTOM));
    }
}
