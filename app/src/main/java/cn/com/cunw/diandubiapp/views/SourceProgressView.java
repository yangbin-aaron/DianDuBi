package cn.com.cunw.diandubiapp.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import cn.com.cunw.diandubiapp.R;

/**
 * @author YangBin
 * @time 2019/8/5 16:14
 * @des
 * @copyright 湖南新云网科技有限公司
 */
public class SourceProgressView extends View {
    private Context mContext;

    public SourceProgressView(Context context) {
        super(context);
        initPaint(context);
    }

    public SourceProgressView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initPaint(context);
    }

    private Paint mPaint;
    private RectF rectF;//矩形
    private int _w, _h;
    private int _radius;
    private int _proColor;

    //确定View大小
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this._w = w;     //获取宽高
        this._h = h;
    }

    //初始化画笔
    private void initPaint(Context context) {
        mContext = context;
        _radius = (int) context.getResources().getDimension(R.dimen.pro_radius);
        _proColor = context.getResources().getColor(R.color.pro_bg);

        mPaint = new Paint();
        mPaint.setColor(_proColor);
        //设置画笔模式：填充
        mPaint.setStyle(Paint.Style.FILL);
        // 抗锯齿
        mPaint.setAntiAlias(true);
        //初始化区域
        rectF = new RectF();
        _sweepAngle = 0;
    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        canvas.translate(_w / 2, _h / 2);             //将画布坐标原点移到中心位置
        int r = _radius;
        rectF.set(-r, -r, r, r);
        mPaint.setColor(_proColor);
        canvas.drawArc(rectF, -90, _sweepAngle, true, mPaint);
    }

    private int _sweepAngle;

    public void update(float pro) {
        _sweepAngle = (int) (360 * pro);
        invalidate();
    }
}