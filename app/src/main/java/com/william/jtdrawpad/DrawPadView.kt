

package com.william.jtdrawpad

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by zhangwei on 24/12/2017.
 */
class DrawPadView : View {

    val TAG = "DrawPadView"

    //画笔,只能对画笔做一些设置，实际的绘画由画布来完成
    private var paint = Paint()

    //绘图的路径
    private val path = Path()

    //画布，主要用这货执行绘画操作
    private lateinit var canvas : Canvas

    private var view_width = 0
    private var view_height = 0
    private lateinit var cacheBitmap: Bitmap

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        init()
    }

    private fun init() {

        view_width = context.getResources().getDisplayMetrics().widthPixels;
        view_height = context.getResources().getDisplayMetrics().heightPixels;
        cacheBitmap = Bitmap.createBitmap(view_width, view_height, Bitmap.Config.ARGB_8888);// 建立图像缓冲区用来保存图像
//        canvas.setBitmap(cacheBitmap);
//        canvas.drawColor(Color.WHITE);

        paint.color = Color.BLACK// 设置画笔的默认颜色
        paint.setStyle(Paint.Style.STROKE)// 设置画笔的填充方式为无填充、仅仅是画线
        paint.setStrokeWidth(1f)// 设置画笔的宽度为1
    }

    override fun onDraw(canvas: Canvas) {
//        super.onDraw(canvas)
        this.canvas = canvas
//        paint.setColor(Color.BLUE);// 设置画笔颜色为蓝色
//        canvas.drawRect(10f, 10f, width.toFloat()-10f, height.toFloat()-10f, paint); // 绘制左上角在(10,10)，大小为100x100的矩形
//        paint.setColor(Color.RED);// 设置画笔颜色为红色
//        paint.setTextSize(24f);// 设置文字大小为24
//        canvas.drawText("我是被画出来的", 10f, 50f, paint);// 在(10,120)绘制文字

        canvas.drawPath(path, paint)
    }

    var lastX = 0f
    var lastY = 0f
    override fun onTouchEvent(event: MotionEvent): Boolean {
        //用来记录上一次的事件触发坐标

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                path.moveTo(event.x, event.y)
                lastX = event.x
                lastY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                //绘制曲线，除此之外还有lineTo,recTo等等
                path.quadTo(lastX, lastY, event.x, event.y)
//                canvas.drawPath(path, paint)
                lastX = event.x
                lastY = event.y
            }
            MotionEvent.ACTION_UP -> {
//                path.reset()
            }
        }
        invalidate()
        return true
    }
}