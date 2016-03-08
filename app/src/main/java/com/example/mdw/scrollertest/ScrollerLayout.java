package com.example.mdw.scrollertest;

import android.app.AlertDialog;
import android.content.Context;
import android.support.v4.view.ViewConfigurationCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by mdw on 2016/3/8.
 */
public class ScrollerLayout extends ViewGroup {

    //用于完成滚动操作的实例
    private Scroller mScroller;
    /**
     * 判定为拖动的最小像素数
     */
    private int mTouchSlop;
    private int leftBorder;
    private int rightBorder;

    public ScrollerLayout(Context context, AttributeSet attrs) {
        super(context, attrs);

        //第一步，创建Scroller的实例
        mScroller = new Scroller(context);

        ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = ViewConfigurationCompat.getScaledPagingTouchSlop(configuration);

        new AlertDialog.Builder(getContext()).create();
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
                childView.layout(i* childView.getMeasuredWidth(),0,(i+1)*childView.getMeasuredWidth(),childView.getHeight());
            }
            leftBorder = getChildAt(0).getLeft();
            rightBorder = getChildAt(getChildCount()-1).getRight();
        }

    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

        switch (ev.getAction()){
            case MotionEvent.ACTION_DOWN:
                ev.getRawX();
        }

        return super.onInterceptTouchEvent(ev);
    }
}
