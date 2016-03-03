package pers.medusa.xiayong.library.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.widget.LinearLayout;


import pers.medusa.xiayong.library.IBubbleView;
import pers.medusa.xiayong.library.R;
import pers.medusa.xiayong.library.drawable.BubbleDrawable;


/**
 * Created by lgp on 2015/3/25.
 */
public class BubbleLinearLayout extends LinearLayout implements IBubbleView{
    private BubbleDrawable bubbleDrawable;
    private float mArrowWidth;
    private float mAngle;
    private float mArrowHeight;
    private float mArrowPositionPercent;
    private BubbleDrawable.ArrowLocation mArrowLocation;
    private int solidColor;
    private int strokeColor;
    private float strokeWidth;
    public BubbleLinearLayout(Context context) {
        super(context);
        initView(null);
    }

    public BubbleLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView(attrs);
    }


    private void initView(AttributeSet attrs){
        if (attrs != null){
            TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.BubbleView);
            mArrowWidth = array.getDimension(R.styleable.BubbleView_arrowWidth,
                    BubbleDrawable.Builder.DEFAULT_ARROW_WITH);
            mArrowHeight = array.getDimension(R.styleable.BubbleView_arrowHeight,
                    BubbleDrawable.Builder.DEFAULT_ARROW_HEIGHT);
            mAngle = array.getDimension(R.styleable.BubbleView_angle,
                    BubbleDrawable.Builder.DEFAULT_ANGLE);
            mArrowPositionPercent  = array.getFraction(R.styleable.BubbleView_arrowRelativePosition, 1, 1,
                    BubbleDrawable.Builder.DEFAULT_ARROW_RELATIVE_POSITION);
            solidColor = array.getColor(R.styleable.BubbleView_solidColor,
                    BubbleDrawable.Builder.DEFAULT_SOLID_COLOR);
            strokeColor = array.getColor(R.styleable.BubbleView_strokeColor,
                    BubbleDrawable.Builder.DEFAULT_STROKE_COLOR);
            strokeWidth = array.getDimension(R.styleable.BubbleView_strokeWidth,
                    BubbleDrawable.Builder.DEFAULT_STROKE_WIDTH);
            int location = array.getInt(R.styleable.BubbleView_arrowLocation, 0);
            mArrowLocation = BubbleDrawable.ArrowLocation.mapIntToValue(location);
            array.recycle();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0){
            setUp(w, h);
        }
    }

    private void setUp(int left, int right, int top, int bottom){
        if (right < left || bottom < top)
            return;
        RectF rectF = new RectF(left, top, right, bottom);
        bubbleDrawable = new BubbleDrawable.Builder()
                .rect(rectF)
                .arrowLocation(mArrowLocation)
                .bubbleType(BubbleDrawable.BubbleType.COLOR)
                .angle(mAngle)
                .arrowHeight(mArrowHeight)
                .arrowWidth(mArrowWidth)
                .arrowRelativePosition(mArrowPositionPercent)
                .solidColor(solidColor)
                .strokeColor(strokeColor)
                .strokeWidth(strokeWidth)
                .build();
    }

    private void setUp(int width, int height){
        setUp(getPaddingLeft(),  + width - getPaddingRight(),
                getPaddingTop(), height - getPaddingBottom());
        setBackgroundDrawable(bubbleDrawable);
    }

    @Override
    public BubbleDrawable.ArrowLocation getArrowDirection() {
        return mArrowLocation;
    }

    @Override
    public float getArrowRelativePosition() {
        return mArrowPositionPercent;
    }
}
