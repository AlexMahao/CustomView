package com.example.mdw.scrollertest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;

/**
 * 圆形进度
 * Created by Alex_MaHao on 2016/3/14.
 */
public class CircleProgressView extends View {


    /**
     * 圆心坐标
     */
    private int mCircleXY;

    /**
     * 内部圆半径
     */
    private int mRadius;

    /**
     * 控件的宽度
     */
    private int width;
    /**
     *  椭圆的文字
     */
    private String mText;

    /**
     * 弧形的画笔
     */
    private Paint mArcPaint;

    /**
     * 文字的画笔
     */
    private Paint mTextPaint;

    /**
     * 内部圆的画笔
     */
    private Paint mCirclePaint;


    /**
     * 弧形的内切矩形
     */
    private Rect mArcRect;


    /**
     * 圆心文字
     */
    private String mCenterText = "Alex_Mahao";

    /**
     * 文字所占大小
     */
    private Rect mTextBound = new Rect();

    /**
     * 外部弧形的度数
     */
    private int mSweepAngle;

    /**
     * 外部弧形的最终度数
     */
    private int mEndAngle;

    public CircleProgressView(Context context) {
        super(context,null);
    }

    public CircleProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    private void initPaint() {
        mArcPaint = new Paint();
        mArcPaint.setColor(Color.BLUE);
        mArcPaint.setAntiAlias(true);
        mArcPaint.setStyle(Paint.Style.STROKE);
        mArcPaint.setStrokeWidth(width/2/2/2);


        mCirclePaint = new Paint();
        mCirclePaint.setColor(Color.RED);
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setAntiAlias(true);

        mTextPaint = new Paint();
        mTextPaint.setColor(Color.WHITE);
        mTextPaint.setTextSize(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,16,getResources().getDisplayMetrics()));
        mTextPaint.setAntiAlias(true);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        width = Math.min(getMeasuredWidth(),getMeasuredHeight());

        setMeasuredDimension(width,width);

        mCircleXY = width/2;

        mRadius = width/2/2;

        initPaint();

        //弧形矩形的范围
        mArcRect = new Rect(width/2/2/2/2,width/2/2/2/2,width-width/2/2/2/2,width-width/2/2/2/2);
        //测量文字的大小
        mTextPaint.getTextBounds(mCenterText, 0, mCenterText.length(), mTextBound);

        setProgress(270);
    }


    @Override
    protected void onDraw(Canvas canvas) {


        //在画笔有宽度的情况下，画笔的宽度的一半正好内切范围矩形
        canvas.drawArc(new RectF(mArcRect),-90,mSweepAngle,false,mArcPaint);

        canvas.drawCircle(mCircleXY,mCircleXY,mRadius,mCirclePaint);

        canvas.drawText(mCenterText,width/2-mTextBound.width()/2,getHeight()/2+mTextBound.height()/2,mTextPaint);

        if(mEndAngle>mSweepAngle){
            mSweepAngle++;
        }

        postInvalidateDelayed(5);
    }

    /**
     * 设置进度
     * @param endAngle
     */
    public void setProgress(int endAngle){
        mEndAngle = endAngle;
        mSweepAngle = 0;
        postInvalidate();
    }
}
