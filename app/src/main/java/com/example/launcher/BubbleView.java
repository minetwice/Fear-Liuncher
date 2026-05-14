package com.example.launcher;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.util.Random;

public class BubbleView extends View {

    private final Paint paint = new Paint();
    private final Random random = new Random();

    public BubbleView(Context context, AttributeSet attrs) {
        super(context, attrs);

        paint.setColor(Color.WHITE);
        paint.setAlpha(80);
        paint.setAntiAlias(true);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        for (int i = 0; i < 25; i++) {

            float x = random.nextInt(getWidth());
            float y = random.nextInt(getHeight());
            float r = random.nextInt(20) + 10;

            canvas.drawCircle(x, y, r, paint);
        }

        invalidate();
    }
}
