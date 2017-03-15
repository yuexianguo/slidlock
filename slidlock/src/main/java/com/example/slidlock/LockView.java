package com.example.slidlock;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Scroller;


/**
 * Created by Administrator on 2017/3/14.
 */

public class LockView extends View {

    private Scroller mScroller;
    private Bitmap mSlidingButton;
    private Paint mPaint;
    private OnUnlockListener mUnLockListener;


    public LockView(Context context) {
       this(context,null);
    }

    public LockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller =  new Scroller(context);
        init();
    }
    private void init() {
        mSlidingButton = BitmapFactory.decodeResource(getResources(), R.mipmap.switch_button);
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = mSlidingButton.getHeight();
        setMeasuredDimension(width,height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mSlidingButton,0,0,mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch(event.getAction()){
            case MotionEvent.ACTION_DOWN:
                if(event.getX()>mSlidingButton.getWidth()){
                    return false;
                }
                float x1= event.getX()-mSlidingButton.getWidth()/2;
                float scrollerX1 =  -x1;
                if(scrollerX1 >0){
                    scrollerX1 =0;
                }
                scrollTo((int)scrollerX1 ,0);
                break;
            case MotionEvent.ACTION_MOVE:
                float x2= event.getX()-mSlidingButton.getWidth()/2;
                if(x2<0){
                    x2=0;
                }else if(x2>getWidth()-mSlidingButton.getWidth()){
                    x2=getWidth()-mSlidingButton.getWidth();
                }
                scrollTo((int)(-x2),0);
                break;
            case MotionEvent.ACTION_UP:
                float x3 = event.getX()-mSlidingButton.getWidth()/2;
                if(x3<getWidth()-mSlidingButton.getWidth()){
                    int startX = getScrollX();
                    int startY=0;
                    int endX =0;
                    int dx = endX - startX;
                    int dy = 0;
                    mScroller.startScroll(startX, startY, dx, dy, 1000);
                    invalidate();

                }else {
                    //通知外界，可以解锁
                    if(mUnLockListener!=null){
                        mUnLockListener.unLock();
                    }
                }
                break;


        }

        return true;
    }

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),0);
            invalidate();
        }

    }
    public interface OnUnlockListener{
        void unLock();
    }

   public void setOnUnlockListener(OnUnlockListener listener){
       mUnLockListener = listener;
   }
}
