package pers.medusa.xiayong.library.drawable;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;

/**
 * Created by lgp on 2015/3/24.
 */
public class BubbleDrawable extends Drawable {
    private RectF mRect;
    private Path mPath = new Path();
    private BitmapShader mBitmapShader;
    private Paint mSolidPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mArrowWidth;
    private float mAngle;
    private float mArrowHeight;
    private float mArrowRelativePosition;
    private int solidColor;
    private int strokeColor;
    private float strokeWidth;
    private Bitmap bubbleBitmap;
    private ArrowLocation mArrowLocation;
    private BubbleType bubbleType;
    private BubbleDrawable(Builder builder) {
        this.mRect = builder.mRect;
        this.mAngle = builder.mAngle;
        this.mArrowHeight = builder.mArrowHeight;
        this.mArrowWidth = builder.mArrowWidth;
        this.mArrowRelativePosition = builder.mArrowRelativePosition;
        this.solidColor = builder.solidColor;
        this.strokeColor = builder.strokeColor;
        this.strokeWidth = builder.strokeWidth;
        this.bubbleBitmap = builder.bubbleBitmap;
        this.mArrowLocation = builder.mArrowLocation;
        this.bubbleType = builder.bubbleType;

    }

    @Override
    protected void onBoundsChange(Rect bounds) {
        super.onBoundsChange(bounds);
    }

    @Override
    public void draw(Canvas canvas) {
        setUp(canvas);
    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }

    @Override
    public void setAlpha(int alpha) {
        mSolidPaint.setAlpha(alpha);
        mStrokePaint.setAlpha(alpha);

    }

    @Override
    public void setColorFilter(ColorFilter cf) {
        mSolidPaint.setColorFilter(cf);
        mStrokePaint.setColorFilter(cf);
    }

    private void setUpPath(ArrowLocation mArrowLocation, Path path){
        switch (mArrowLocation){
            case LEFT:
                setUpLeftPath(mRect, path);
                break;
            case RIGHT:
                setUpRightPath(mRect, path);
                break;
            case TOP:
                setUpTopPath(mRect, path);
                break;
            case BOTTOM:
                setUpBottomPath(mRect, path);
                break;
        }
    }

    private void setUp(Canvas canvas) {
        switch (bubbleType) {
            case COLOR:
                mSolidPaint.setColor(solidColor);
                break;
            case BITMAP:
                if (bubbleBitmap == null)
                    return;
                if (mBitmapShader == null) {
                    mBitmapShader = new BitmapShader(bubbleBitmap,
                            Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
                }
                mSolidPaint.setShader(mBitmapShader);
                setUpShaderMatrix();
                break;
        }
        setUpPath(mArrowLocation, mPath);
        canvas.drawPath(mPath, mSolidPaint);
        drawStroke(canvas);
    }
    private void drawStroke(Canvas canvas){

        mStrokePaint.setColor(strokeColor);
        mStrokePaint.setStyle(Paint.Style.STROKE);
        mStrokePaint.setStrokeWidth(strokeWidth*2);
        mStrokePaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_ATOP));
        canvas.drawPath(mPath, mStrokePaint);
    }
    private void setUpLeftPath(RectF rect, Path path){

        float mArrowPosition = mArrowRelativePosition*rect.height();
        path.moveTo(mArrowWidth + rect.left + mAngle, rect.top);
        path.lineTo(rect.width() - mAngle, rect.top);
        path.arcTo(new RectF(rect.right - mAngle , rect.top, rect.right,
                 mAngle + rect.top), 270, 90);
        path.lineTo(rect.right, rect.bottom - mAngle);
        path.arcTo(new RectF(rect.right - mAngle , rect.bottom - mAngle,
                rect.right, rect.bottom), 0, 90);
        path.lineTo(rect.left + mArrowWidth + mAngle, rect.bottom);
        path.arcTo(new RectF(rect.left + mArrowWidth, rect.bottom - mAngle ,
                mAngle + rect.left + mArrowWidth, rect.bottom), 90, 90);
        path.lineTo(rect.left + mArrowWidth,  mArrowHeight + mArrowPosition);
        path.lineTo(rect.left, mArrowPosition + mArrowHeight / 2);
        path.lineTo(rect.left + mArrowWidth, mArrowPosition);
        path.lineTo(rect.left + mArrowWidth, rect.top + mAngle);
        path.arcTo(new RectF(rect.left + mArrowWidth, rect.top, mAngle
                + rect.left + mArrowWidth, mAngle + rect.top), 180, 90);
        path.close();
    }

    private void setUpTopPath(RectF rect, Path path){

        float mArrowPosition = mArrowRelativePosition*rect.width();
        path.moveTo(rect.left + Math.min(mArrowPosition, mAngle), rect.top + mArrowHeight);
        path.lineTo(rect.left + mArrowPosition,  rect.top + mArrowHeight);
        path.lineTo(rect.left + mArrowWidth / 2 + mArrowPosition, rect.top);
        path.lineTo(rect.left + mArrowWidth + mArrowPosition, rect.top + mArrowHeight);
        path.lineTo(rect.right - mAngle, rect.top + mArrowHeight);

        path.arcTo(new RectF(rect.right - mAngle,
                rect.top + mArrowHeight , rect.right, mAngle + rect.top + mArrowHeight), 270, 90);
        path.lineTo(rect.right, rect.bottom - mAngle);

        path.arcTo(new RectF(rect.right - mAngle , rect.bottom - mAngle,
                rect.right , rect.bottom), 0, 90);
        path.lineTo(rect.left + mAngle, rect.bottom);

        path.arcTo(new RectF(rect.left, rect.bottom - mAngle,
                mAngle + rect.left , rect.bottom), 90, 90);
        path.lineTo(rect.left , rect.top + mArrowHeight + mAngle);
        path.arcTo(new RectF(rect.left, rect.top + mArrowHeight , mAngle
                + rect.left, mAngle + rect.top + mArrowHeight), 180, 90);
        path.close();
    }

    private void setUpRightPath(RectF rect, Path path){

        float mArrowPosition = mArrowRelativePosition*rect.height();
        path.moveTo(rect.left + mAngle, rect.top);
        path.lineTo(rect.width() - mAngle - mArrowWidth, rect.top);
        path.arcTo(new RectF(rect.right - mAngle - mArrowWidth,
                rect.top, rect.right - mArrowWidth, mAngle + rect.top), 270, 90);
        path.lineTo(rect.right - mArrowWidth,  mArrowPosition);
        path.lineTo(rect.right, mArrowPosition + mArrowHeight / 2);
        path.lineTo(rect.right - mArrowWidth, mArrowPosition + mArrowHeight);
        path.lineTo(rect.right - mArrowWidth, rect.bottom - mAngle);

        path.arcTo(new RectF(rect.right - mAngle - mArrowWidth, rect.bottom - mAngle,
                rect.right - mArrowWidth, rect.bottom), 0, 90);
        path.lineTo(rect.left + mArrowWidth, rect.bottom);

        path.arcTo(new RectF(rect.left, rect.bottom - mAngle,
                mAngle + rect.left , rect.bottom), 90, 90);

        path.arcTo(new RectF(rect.left, rect.top, mAngle
                + rect.left, mAngle + rect.top), 180, 90);
        path.close();
    }

    private void setUpBottomPath(RectF rect, Path path){

        float mArrowPosition = mArrowRelativePosition*rect.width();
        path.moveTo(rect.left + mAngle, rect.top);
        path.lineTo(rect.width() - mAngle, rect.top);
        path.arcTo(new RectF(rect.right - mAngle,
                rect.top, rect.right, mAngle + rect.top), 270, 90);

        path.lineTo(rect.right, rect.bottom - mArrowHeight - mAngle);
        path.arcTo(new RectF(rect.right - mAngle, rect.bottom - mAngle - mArrowHeight,
                rect.right, rect.bottom - mArrowHeight), 0, 90);

        path.lineTo(rect.left + mArrowWidth + mArrowPosition,  rect.bottom - mArrowHeight);
        path.lineTo(rect.left + mArrowPosition + mArrowWidth / 2, rect.bottom);
        path.lineTo(rect.left + mArrowPosition, rect.bottom - mArrowHeight);
        path.lineTo(rect.left + Math.min(mAngle, mArrowPosition), rect.bottom - mArrowHeight);

        path.arcTo(new RectF(rect.left, rect.bottom - mAngle - mArrowHeight,
                mAngle + rect.left , rect.bottom - mArrowHeight), 90, 90);
        path.lineTo(rect.left, rect.top + mAngle);
        path.arcTo(new RectF(rect.left, rect.top, mAngle
                + rect.left, mAngle + rect.top), 180, 90);
        path.close();
    }

    private void setUpShaderMatrix() {
        float scale;
        Matrix mShaderMatrix = new Matrix();
        mShaderMatrix.set(null);
        int mBitmapWidth = bubbleBitmap.getWidth();
        int mBitmapHeight = bubbleBitmap.getHeight();
        float scaleX = getIntrinsicWidth() / (float) mBitmapWidth;
        float scaleY = getIntrinsicHeight() / (float) mBitmapHeight;
        scale = Math.min(scaleX, scaleY);
        mShaderMatrix.postScale(scale, scale);
        mShaderMatrix.postTranslate(mRect.left, mRect.top);
        mBitmapShader.setLocalMatrix(mShaderMatrix);
    }

    @Override
    public int getIntrinsicWidth() {
        return (int)mRect.width();
    }

    @Override
    public int getIntrinsicHeight() {
        return (int)mRect.height();
    }

    public static class Builder{
        public static float DEFAULT_ARROW_WITH = 25;
        public static float DEFAULT_ARROW_HEIGHT = 25;
        public static float DEFAULT_ANGLE = 20;
        public static float DEFAULT_ARROW_RELATIVE_POSITION = 0.3f;
        public static int DEFAULT_SOLID_COLOR = Color.RED;
        public static int DEFAULT_STROKE_COLOR = Color.TRANSPARENT;
        public static float DEFAULT_STROKE_WIDTH = 1;
        private RectF mRect;
        private float mArrowWidth = DEFAULT_ARROW_WITH;
        private float mAngle = DEFAULT_ANGLE;
        private float mArrowHeight = DEFAULT_ARROW_HEIGHT;
        private float mArrowRelativePosition = DEFAULT_ARROW_RELATIVE_POSITION;
        private int solidColor = DEFAULT_SOLID_COLOR;
        private int strokeColor = DEFAULT_STROKE_COLOR;
        private float strokeWidth = DEFAULT_STROKE_WIDTH;
        private Bitmap bubbleBitmap;
        private BubbleType bubbleType = BubbleType.COLOR;
        private ArrowLocation mArrowLocation = ArrowLocation.LEFT;

        public Builder rect(RectF rect){
            this.mRect = rect;
            return this;
        }

        public Builder arrowWidth(float mArrowWidth){
            this.mArrowWidth = mArrowWidth;
            return this;
        }

        public Builder angle(float mAngle){
            this.mAngle = mAngle * 2;
            return this;
        }

        public Builder arrowHeight(float mArrowHeight){
            this.mArrowHeight = mArrowHeight;
            return this;
        }
        public Builder strokeWidth(float strokeWidth){
            this.strokeWidth = strokeWidth;
            return this;
        }

        public Builder strokeColor(int strokeColor){
            this.strokeColor = strokeColor;
            return this;
        }

        public Builder arrowRelativePosition(float mArrowRelativePosition){
            if(mArrowRelativePosition > 0.8f){
                mArrowRelativePosition = 0.8f;
            }else if(mArrowRelativePosition < 0.2f){
                mArrowRelativePosition = 0.2f;
            }
            this.mArrowRelativePosition = mArrowRelativePosition;
            return this;
        }

        public Builder solidColor(int bubbleColor){
            this.solidColor = bubbleColor;
            bubbleType(BubbleType.COLOR);
            return this;
        }

        public Builder bubbleBitmap(Bitmap bubbleBitmap){
            this.bubbleBitmap = bubbleBitmap;
            bubbleType(BubbleType.BITMAP);
            return this;
        }

        public Builder arrowLocation(ArrowLocation arrowLocation){
            this.mArrowLocation = arrowLocation;
            return this;
        }

        public Builder bubbleType(BubbleType bubbleType){
            this.bubbleType = bubbleType;
            return this;
        }

        public BubbleDrawable build(){
            if (mRect == null){
                throw new IllegalArgumentException("BubbleDrawable Rect can not be null");
            }
            return new BubbleDrawable(this);
        }
    }

    public enum ArrowLocation{
        LEFT(0x00),
        RIGHT(0x01),
        TOP(0x02),
        BOTTOM(0x03);

        private int mValue;

        ArrowLocation(int value){
            this.mValue = value;
        }

        public static ArrowLocation mapIntToValue(int stateInt) {
            for (ArrowLocation value : ArrowLocation.values()) {
                if (stateInt == value.getIntValue()) {
                    return value;
                }
            }
            return getDefault();
        }

        public static ArrowLocation getDefault(){
            return LEFT;
        }

        public int getIntValue() {
            return mValue;
        }
    }

    public enum BubbleType{
        COLOR,
        BITMAP
    }
}
