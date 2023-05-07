package com.example.bloxtrixgame.vistas;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.bloxtrixgame.presentacion.Puntos;

public class marcojuego extends View {
    public marcojuego(Context context) {
        super(context);
    }

    public marcojuego(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public marcojuego(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private Puntos[][] mPoints;
    private int mBoxSize;
    private int mBoxPadding;
    private int mGameSize;

    private final Paint mPaint = new Paint();

    public void init(int gameSize) {
        mGameSize = gameSize;
        getViewTreeObserver().addOnGlobalLayoutListener(() -> {
            mBoxSize = Math.min(getWidth(), getHeight()) / mGameSize;
            mBoxPadding = mBoxSize / 10;
        });
    }

    void setPoints(Puntos[][] points) {
        mPoints = points;
    }

    private Puntos getPoint(int x, int y) {
        return mPoints[y][x];
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        canvas.drawRect(0, 0, mGameSize, mGameSize, mPaint);
        if (mPoints == null) {
            return;
        }
        for (int i = 0; i < mGameSize; i++) {
            for (int j = 0; j < mGameSize; j++) {
                Puntos point = getPoint(i, j);
                int left, right, top, bottom;
                mPaint.setColor(Color.WHITE);
                switch (point.type) {
                    case BOX:
                        left = mBoxSize * point.x + mBoxPadding;
                        right = left + mBoxSize - mBoxPadding;
                        top = mBoxSize * point.y + mBoxPadding;
                        bottom = top + mBoxSize - mBoxPadding;
                        break;
                    case VERTICAL_LINE:
                        left = mBoxSize * point.x;
                        right = left + mBoxPadding;
                        top = mBoxSize * point.y;
                        bottom = top + mBoxSize;
                        break;
                    case HORIZONTAL_LINE:
                        left = mBoxSize * point.y;
                        right = left + mBoxSize;
                        top = mBoxSize * point.y;
                        bottom = top + mBoxPadding;
                        break;
                    case EMPTY:
                    default:
                        left = mBoxSize * point.x;
                        right = left + mBoxSize;
                        top = mBoxSize * point.y;
                        bottom = top + mBoxSize;
                        mPaint.setColor(Color.BLACK);
                        break;
                }
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }
}
