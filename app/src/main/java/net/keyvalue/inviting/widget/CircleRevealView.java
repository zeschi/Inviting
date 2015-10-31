package net.keyvalue.inviting.widget;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;

/**
 * Created by Miroslaw Stanek on 18.01.15.
 */
public class CircleRevealView extends View {

    private Paint fillPaint;
    private int startLocationX;
    private int startLocationY;
    private int currentRadius;

    private OnRevealListener onRevealListener;

    public CircleRevealView(Context context) {
        super(context);
        init();
    }

    public CircleRevealView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleRevealView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public CircleRevealView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(Color.WHITE);
    }

    public void setFillPaintColor(int color) {
        fillPaint.setColor(color);
    }

    public void startAnimationFromLocation(int[] locationOnScreen) {
        saveLocation(locationOnScreen);
        startRevealAnimation();
    }

    private void saveLocation(int[] locationOnScreen) {
        startLocationX = locationOnScreen[0];
        startLocationY = locationOnScreen[1];
    }

    private void startRevealAnimation() {
        int MAX_RADIUS = getViewDiagonalLength();
        int FILL_ANIMATION_DURATION = 400;
        ValueAnimator revealAnimator = ValueAnimator.ofInt(0, MAX_RADIUS);
        revealAnimator.setDuration(FILL_ANIMATION_DURATION);
        revealAnimator.setInterpolator(new AccelerateInterpolator());
        revealAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                currentRadius = (int) animation.getAnimatedValue();
                invalidate();
            }
        });
        revealAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                if (null != onRevealListener) {
                    onRevealListener.onRevealEnd();
                }
            }
        });
        revealAnimator.start();
    }

    private int getViewDiagonalLength() {
        int width = getWidth();
        int height = getHeight();
        return (int) Math.sqrt(width * width + height * height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawCircle(startLocationX, startLocationY, currentRadius, fillPaint);
    }

    public interface OnRevealListener {
        void onRevealEnd();
    }

    public void setOnRevealListener(OnRevealListener onRevealListener) {
        this.onRevealListener = onRevealListener;
    }
}
