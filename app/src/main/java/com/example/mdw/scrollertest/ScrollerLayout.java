package com.example.mdw.scrollertest;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 *
 *
 *
 * Created by mdw on 2016/3/8.
 */
public class ScrollerLayout extends ViewGroup {

    //用于完成滚动操作的实例
    private Scroller mScroller;
    /**
     * 判定为拖动的最小像素数
     */
    private int mTouchSlop;

    /**
     *左边界
     */
    private int leftBorder;
    /**
     * 右边界
     */
    private int rightBorder;

    /**
     * 手指按下时的屏幕坐标
     */
    private float mXDown;

    /**
     * 上一次触发ACTION_MOVE时的屏幕坐标
     */
    private float mXLastMove;

    /**
     * 手机当时所处的屏幕坐标
     */
    private float mXMove;

    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        //第一步，创建Scroller的实例
        mScroller = new Scroller(context);
        getParent().requestDisallowInterceptTouchEvent(true);

        //获取系统的最小移动距离
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);


        setClickable(true);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childView = getChildAt(i);
            measureChild(childView, widthMeasureSpec, heightMeasureSpec);
        }
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        //changed 表示view有新的尺寸或位置，左，上，右，底
        if (changed) {
            int childCount = getChildCount();
            for(int i=0;i<childCount;i++){
                View childView  = getChildAt(i);
                childView.layout(i* childView.getMeasuredWidth(),0,(i+1)*childView.getMeasuredWidth(),childView.getMeasuredHeight());
            }
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(getChildCount()-1).getRight();
        }

    }



    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        Log.i("info","scroller onInterceptTouchEvent");
        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                mXDown = ev.getRawX();
                mXLastMove = mXDown;
                break;
            case MotionEvent.ACTION_MOVE:
                mXMove = ev.getRawX();

                float diff = Math.abs(mXMove-mXDown);
                mXLastMove =mXDown;

                if(diff>mTouchSlop){
                    return true;
                }
                break;
        }

        return false;
    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_MOVE:
                mXMove = event.getRawX();
                Log.i("info",mXMove+"");
                int scrolledX = (int) (mXLastMove-mXMove);
                if(getScrollX()+scrolledX<leftBorder){
                    scrollTo(leftBorder,0);
                    return true;
                }else if(getScrollX()+getWidth()+scrolledX>rightBorder){
                    scrollTo(rightBorder-getWidth(),0);
                    return true;
                }
                scrollBy(scrolledX,0);
                mXLastMove = mXMove;
                break;
            case MotionEvent.ACTION_UP:
                int targetIndex = (getScrollX()+getWidth()/2)/getWidth();
                int dx = targetIndex*getWidth()-getScrollX();
                //滚动开始时X的坐标，滚动开始时Y轴的坐标，横向滚动的距离（正值表示向左滚动），纵向滚动的距离（正值表示向上滚动）
                mScroller.startScroll(getScrollX(),0,dx,0);
                invalidate();
                break;
        }

        return super.onTouchEvent(event);
    }


  /*  @Override
    public boolean onTouchEvent(MotionEvent event) {
        return super.onTouchEvent(event);
    }*/

    @Override
    public void computeScroll() {
        if(mScroller.computeScrollOffset()){
            scrollTo(mScroller.getCurrX(),mScroller.getCurrY());
            invalidate();
        }
    }
}
