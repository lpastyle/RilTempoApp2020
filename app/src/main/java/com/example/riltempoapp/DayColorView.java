package com.example.riltempoapp;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

/**
 * Custom view to display tempo color
 */
public class DayColorView extends View {

    static final float CIRCLE_SCALE = 0.9f; // half circle will occupy 90% of view

    // custom attributes data
    private String captionText;
    private int captionTextColor = Color.BLACK; // default value
    private float captionTextSize = 14;
    private int dayCircleColor;

    private TextPaint textPaint;
    private Paint circlePaint;
    private float textWidth;
    private float textHeight;

    public DayColorView(Context context) {
        super(context);
        init(null, 0);
    }

    public DayColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DayColorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load custom attributes
        final TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.DayColorView, defStyle, 0);
        captionText = a.getString(R.styleable.DayColorView_captionText);
        captionTextColor = a.getColor(R.styleable.DayColorView_captionTextColor, getContext().getColor(R.color.black));
        captionTextSize = a.getDimension(R.styleable.DayColorView_captionTextSize, getResources().getDimension(R.dimen.tempo_color_text_size));
        dayCircleColor = a.getColor(R.styleable.DayColorView_dayCircleColor, getContext().getColor(R.color.tempo_undecided_day_bg));

        // Recycles the TypedArray, to be re-used by a later caller
        a.recycle();

        // set a text paint and corresponding text measurements from attributes
        textPaint = new TextPaint();
        setTextPaintAndMeasurements();

        // set a circle paint from attributes
        circlePaint = new Paint();
        setCirclePaint();
    }

    private void setTextPaintAndMeasurements() {

        // Set up a default TextPaint object
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(captionTextSize);
        textPaint.setColor(captionTextColor);

        // compute dimensions to be used for drawing text
        textWidth = textPaint.measureText(captionText);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textHeight = fontMetrics.bottom;
    }

    private void setCirclePaint() {
        // set up a default Pain object to draw circle
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(dayCircleColor);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // compute view dimensions
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        // Draw the background circle with the expected color
        float radius = Math.min(getWidth(), getHeight()) * 0.5f * CIRCLE_SCALE;
        canvas.drawCircle(getWidth() * 0.5f, getHeight() * 0.5f, radius, circlePaint );

        // Draw the caption text.
        canvas.drawText(captionText,
                paddingLeft + (contentWidth - textWidth) / 2,
                paddingTop + (contentHeight + textHeight) / 2,
                textPaint);
    }

    public void setDayColor(TempoColor color) {
        dayCircleColor = getContext().getColor(color.getResId());
        setCirclePaint();
        invalidate();
    }


}