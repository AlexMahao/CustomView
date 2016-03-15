package com.example.mdw.scrollertest;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by mdw on 2016/3/14.
 */
public class BorderTextView  extends TextView{


    private Paint mPaint1,mPaint2;

    public BorderTextView(Context context) {
        super(context,null);
    }

    public BorderTextView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //外边框
        mPaint1 = new Paint();
        mPaint1.setColor(getResources().getColor(android.R.color.holo_blue_bright));
        //画笔的样式，充满
        mPaint1.setStyle(Paint.Style.FILL);


        //内边框
        mPaint2 = new Paint();
        mPaint2.setColor(Color.YELLOW);
        mPaint2.setStyle(Paint.Style.FILL);


    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        //因为我们重绘了TextView的上下左右的边框，所以其宽高应该各增大10*2
        setMeasuredDimension(getMeasuredWidth()+20,getMeasuredHeight()+20);
    }

    @Override
    protected void onDraw(Canvas canvas) {

        //绘制外层矩形
        canvas.drawRect(0,0,getMeasuredWidth(),getMeasuredHeight(),mPaint1);

        //绘制内层矩形
        canvas.drawRect(10,10,getMeasuredWidth()-10,getMeasuredHeight()-10,mPaint2);

        canvas.save();//保存之前的状态

        //水平评议10px
        canvas.translate(10,10);

        //回调父类方法之前，实现自己的逻辑，则会被文本遮盖
        super.onDraw(canvas);

        //方法之后实现，则内容会覆盖文本

        canvas.restore();//恢复之前的状态

    }
}
