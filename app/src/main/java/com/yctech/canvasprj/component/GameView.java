package com.yctech.canvasprj.component;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import com.yctech.canvasprj.R;

public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    private SurfaceHolder mSurfaceHolder;
    private Paint mPaint;
    private GameThread mGameThread;
    private int mLeft;
    private int mTop;
    private Hero mHero;
    private Enemy mEnemy;
    private Context mContext;
    public GameView(Context context) {
        super(context,null);
    }

    public GameView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mSurfaceHolder = getHolder();
        mPaint = new Paint();
        mPaint.setColor(Color.RED);
        mGameThread = new GameThread();
        mSurfaceHolder.addCallback(this);
        setFocusable(true);
        setFocusableInTouchMode(true);
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        mHero = new Hero();
        mEnemy = new Enemy();
        mGameThread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }

    class GameThread extends Thread
    {
        @Override
        public void run() {
            super.run();
            while (true)
            {
                Canvas canvas = mSurfaceHolder.lockCanvas();
                canvas.drawColor(Color.WHITE);
                mSurfaceHolder.unlockCanvasAndPost(canvas);
                mHero.lifeStart();
                mEnemy.lifeStart();
            }
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        switch (event.getKeyCode())
        {
            case KeyEvent.KEYCODE_DPAD_LEFT:
                mHero.hrx-=10;
                break;

            case KeyEvent.KEYCODE_DPAD_RIGHT:
                mHero.hrx+=10;
                break;
            case KeyEvent.KEYCODE_DPAD_DOWN:
                mHero.hry+=10;
                break;
            case KeyEvent.KEYCODE_DPAD_UP:
                mHero.hry-=10;
                break;
        }
        return super.dispatchKeyEvent(event);
    }
    class Hero
    {
        public int hrx;
        public int hry;
        private Bitmap hrBm;
        public Hero(){
            hrBm = BitmapFactory.decodeResource(getResources(), R.drawable.hero);
            hrBm = Bitmap.createScaledBitmap(hrBm, 100, 100, false);
        }
        public void lifeStart()
        {
            long t = System.currentTimeMillis();
            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            canvas.drawBitmap(hrBm, hrx, hry, mPaint);
            mSurfaceHolder.unlockCanvasAndPost(canvas);
            long gap = System.currentTimeMillis()-t;
            Log.i("bitch",gap+"#");
            if(gap<50)
            {
                try {
                    Thread.sleep(50-gap);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class Enemy
    {
        public int hrx;
        public int hry;
        private Bitmap hrBm;
        public Enemy(){
            hrBm = BitmapFactory.decodeResource(getResources(), R.drawable.ic_launcher);
        }
        public void lifeStart()
        {
            long t = System.currentTimeMillis();
            Canvas canvas = mSurfaceHolder.lockCanvas();
            canvas.drawColor(Color.WHITE);
            if((hrx+=10)>360)
            {
                hrx = 0;
            }
            canvas.drawBitmap(hrBm, hrx, hry, mPaint);
            mSurfaceHolder.unlockCanvasAndPost(canvas);
            long gap = System.currentTimeMillis()-t;
            if(gap<50)
            {
                try {
                    Thread.sleep(50-gap);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
