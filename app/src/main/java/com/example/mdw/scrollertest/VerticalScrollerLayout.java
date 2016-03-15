package com.example.mdw.scrollertest;

import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by mdw on 2016/3/15.
 */
public class VerticalScrollerLayout extends ViewGroup {

    /**
     * 屏幕高度
     */
    private int mScreenHeight;

    private int mLastY;

    private int mStart;

    /**
     * 滚动控件
     */
    private Scroller mScroller;

    /**
     * 最小移动距离
     */
    private int mTouchSlop;


    private int mEnd;

    private int mDownStartX;

    private int mViewHeight;
    private int y;

    public VerticalScrollerLayout(Context context) {
        super(context, null);
    }

    public VerticalScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        DisplayMetrics dm = getResources().getDisplayMetrics();
        mScreenHeight = dm.heightPixels;

        mScroller = new Scroller(context);

        /**
         * 获取最小移动距离
         */
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int count = getChildCount();

        for (int i = 0; i < count; i++) {
            View childView = getChildAt(i);
            // 大坑
            int childHeight = MeasureSpec.makeMeasureSpec(mScreenHeight,MeasureSpec.EXACTLY);
            measureChild(childView, widthMeasureSpec, childHeight);
        }

        //mViewHeight = getChildAt(0).getMeasuredHeight();
        //设置ViewGroup的高度

       // Log.i("info", "onMeasure:"+getMeasuredHeight());

       // setMeasuredDimension(widthMeasureSpec,heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {



        int childCount = getChildCount();

        MarginLayoutParams mlp = (MarginLayoutParams) getLayoutParams();
        mlp.height = childCount*mScreenHeight;
        setLayoutParams(mlp);


        for (int i = 0; i < childCount; i++) {
            View child = getChildAt(i);
            if (child.getVisibility() != View.GONE) {
                child.layout(l, i * mScreenHeight, r, (i + 1) * mScreenHeight);
            }
           // Log.i("info", "childViewHeight:" + i + "````" + getChildAt(i).getMeasuredHeight());
        }
       // Log.i("info", "onLayout" + "``" + changed);

    }

   @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
       y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownStartX = (int) ev.getY();
                mLastY = y;
                mStart = getScrollY();
                break;
            case MotionEvent.ACTION_MOVE:
                if(Math.abs(y-mDownStartX)>mTouchSlop){
                    return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        y = (int) event.getY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mLastY = y;
                mStart = getScrollY();
                break;

            case MotionEvent.ACTION_MOVE:
                if (!mScroller.isFinished()) {
                    mScroller.abortAnimation();
                }

                Log.i("info","mLastY"+mLastY+"y"+y);
                int dy = mLastY - y;

                if (getScrollY() < 0) {
                    dy = 0;
                }

                Log.i("info", "scroll:" + getScrollY() + "height:" + getHeight() + "mScreenHeight:" + mScreenHeight + "dy" + dy);
                if (getScrollY() > getHeight() - mScreenHeight) {
                    dy = 0;
                }

                scrollBy(0, dy);
                mLastY = y;
                break;
            case MotionEvent.ACTION_UP:
                mEnd = getScrollY();
                int dScrollY = mEnd - mStart;
                if (dScrollY > 0) {
                    if (dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, mScreenHeight - dScrollY);
                    }
                } else {
                    if (-dScrollY < mScreenHeight / 3) {
                        mScroller.startScroll(0, getScrollY(), 0, -dScrollY);
                    } else {
                        mScroller.startScroll(0, getScrollY(), 0, -mScreenHeight - dScrollY);
                    }
                }
                break;
        }
        postInvalidate();
        return true;
    }


    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(0, mScroller.getCurrY());
            postInvalidate();
        }
    }
}
