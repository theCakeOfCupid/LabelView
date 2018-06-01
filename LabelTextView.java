package com.shuwei.intelligentscene.customviews;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.shuwei.intelligentscene.R;
import com.shuwei.intelligentscene.utils.ABTextUtil;


/**
 * Created by james on 2018/5/18.
 */

@SuppressLint("AppCompatCustomView")
public class LabelTextView extends LinearLayout {
    //线条宽度
    private int mStrokeWidth = 2;
    //斜线长度
    private int bias = 50;
    //文字宽度
    private float textWidth;
    //文字高度
    private float textHeight;

    private String text = "ceshi";
    //文字大小，单位为sp
    private float textSp = 7;
    //文字内间距
    private int padding = ABTextUtil.dip2px(getContext(), 3);
    private int textColor = getResources().getColor(R.color.label_color);

    public LabelTextView(Context context) {
        super(context);
        //init();
    }

    public LabelTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        //init();
    }

    public LabelTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //init();
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (!TextUtils.isEmpty(text)) {
            Paint paint = new Paint();
            Rect rect = new Rect();
            paint.setTextSize(ABTextUtil.sp2px(getContext(), textSp));
            //paint.setTextSize(40);
            paint.getTextBounds(text, 0, text.length(), rect);
            textWidth = rect.width();
            textHeight = rect.height();
            bias = (int) (textWidth / 3);
        }
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) getLayoutParams();
        layoutParams.width = (int) (textWidth + 2 * padding + bias);
        layoutParams.height = (int) (textHeight + 2 * padding);

        setLayoutParams(layoutParams);
    }


    @Override
    protected void dispatchDraw(Canvas canvas) {
        Paint paint = new Paint();
        paint.setColor(textColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(mStrokeWidth);
        paint.setAntiAlias(true);
        paint.setTextSize(ABTextUtil.sp2px(getContext(), textSp));
        paint.setTextAlign(Paint.Align.CENTER);
        Path path = new Path();
        int width = getMeasuredWidth();
        int height = getMeasuredHeight();
        path.moveTo(mStrokeWidth, mStrokeWidth);
        path.rLineTo(width - bias - mStrokeWidth, 0);
        path.rLineTo(bias, height / 2 - mStrokeWidth);
        path.rLineTo(-bias, height / 2 - mStrokeWidth);
        path.rLineTo(bias + mStrokeWidth - width, 0);
        path.close();
        Paint.FontMetrics fm = paint.getFontMetrics();
        //文本的宽度
        textWidth = paint.measureText(text);
        float textCenterVerticalBaselineY = height / 2 - fm.descent + (fm.descent - fm.ascent) / 2;
        canvas.drawPath(path, paint);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawText(text, (getMeasuredWidth() - bias) / 2, textCenterVerticalBaselineY, paint);
        super.dispatchDraw(canvas);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
    }

    /**
     * 设置文本内容
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        invalidate();
    }
    /**
     * 设置文本大小
     * @param textSizeSp 文本大小，单位是sp
     */
//    public void setTextSize(int textSizeSp) {
//        DisplayParams displayParams = DisplayParams.getInstance(context);
//        this.textSize = DisplayUtil.sp2px(textSizeSp, displayParams.fontScale);
//        invalidate();
//    }

    /**
     * 设置文本的颜色
     *
     * @param textColor
     */
    public void setTextColor(int textColor) {
        this.textColor = textColor;
        invalidate();
    }
}
