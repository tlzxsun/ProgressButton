package org.matteo.progressbutton;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by matteo on 2015/12/7.
 */
public class ProgressButton extends View {

    int strokeWidth = 10;
    int gapWidth = 10;
    int strokeColor = Color.WHITE;
    int progressColor = Color.parseColor("#4caf50");
    float progress = 1.0f;
    int textColor = Color.WHITE;
    int textSize = 48;
    String text = "kwkw";


    public ProgressButton(Context context) {
        super(context);
        disableHardwareAccelerate();
    }

    public ProgressButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        disableHardwareAccelerate();
        parseAttributes(context.obtainStyledAttributes(attrs, R.styleable.ProgressButton));
    }

    private void parseAttributes(TypedArray a) {
        strokeWidth = (int) a.getDimension(R.styleable.ProgressButton_stroke_width, strokeWidth);
        gapWidth = (int) a.getDimension(R.styleable.ProgressButton_gap_width, gapWidth);
        strokeColor = a.getColor(R.styleable.ProgressButton_stroke_color, strokeColor);
        progressColor = a.getColor(R.styleable.ProgressButton_progress_color, progressColor);
        progress = a.getFloat(R.styleable.ProgressButton_progress, progress);
        textColor = a.getColor(R.styleable.ProgressButton_textColor, textColor);
        textSize = (int) a.getDimension(R.styleable.ProgressButton_textSize, textSize);
        text = a.getString(R.styleable.ProgressButton_text);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int radius = getHeight()/2;
        Paint paint = new Paint();
        paint.setAntiAlias(true);



        Path path = new Path();
        path.moveTo(getHeight()/2, getHeight() - strokeWidth/2);
        path.arcTo(new RectF(0 + strokeWidth/2, 0 + strokeWidth/2, getHeight() - strokeWidth/2, getHeight() - strokeWidth/2), 90, 180);
        path.lineTo(getWidth() - getHeight(), 0 + strokeWidth/2);
        path.arcTo(new RectF(getWidth() - getHeight(), 0 + strokeWidth/2, getWidth() - strokeWidth/2, getHeight() - strokeWidth/2), 270, 180);
        path.lineTo(getHeight()/2, getHeight() - strokeWidth/2);
        paint.setColor(strokeColor);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(strokeWidth);
        canvas.drawPath(path, paint);

        RectF rect3 = new RectF(strokeWidth + gapWidth, strokeWidth + gapWidth, getWidth() - strokeWidth - gapWidth,
                getHeight() - strokeWidth - gapWidth);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(progressColor);
        canvas.save();
//        canvas.drawRoundRect(rect3, radius -strokeWidth - spaceWidth, radius - strokeWidth - spaceWidth, paint);


        RectF rect4 = new RectF(strokeWidth + gapWidth, strokeWidth + gapWidth, (getWidth() - strokeWidth - gapWidth) * progress,
                getHeight() - strokeWidth - gapWidth);
//        paint.setColor(Color.YELLOW);
//        paint.setStyle(Paint.Style.FILL_AND_STROKE);
//        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
//        canvas.drawRect(rect4, paint);
        canvas.clipRect(rect4);
        canvas.drawRoundRect(rect3, radius -strokeWidth - gapWidth, radius - strokeWidth - gapWidth, paint);

        if(!TextUtils.isEmpty(text)) {
            canvas.restore();
            paint.setColor(textColor);
            paint.setTextSize(textSize);
            paint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics fontMetrics = paint.getFontMetrics();
            float fontHeight = fontMetrics.bottom - fontMetrics.top;
            float textBaseY = getHeight() - (getHeight() - fontHeight) / 2 - fontMetrics.bottom;
            canvas.drawText(text, getWidth() / 2, textBaseY, paint);
        }
    }

    private void disableHardwareAccelerate() {
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        }
    }

    public void setProgress(float progress) {
        this.progress = progress;
        postInvalidate();
    }

    public void setProgress(int progress) {
        this.progress = progress * 1.0f/100;
        postInvalidate();
    }
}
