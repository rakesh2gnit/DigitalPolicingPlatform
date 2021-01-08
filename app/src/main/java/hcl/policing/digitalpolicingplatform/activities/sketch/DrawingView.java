package hcl.policing.digitalpolicingplatform.activities.sketch;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;

import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

public class DrawingView extends View {

    private static final float STROKE_WIDTH = 5f;
    private static final float STROKE_WIDTH_ERASE = 25f;
    private static final float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
    private Paint drawPaint, canvasPaint;
    private Path drawPath;
    private Canvas drawCanvas;
    private int paintColor = Color.BLACK;
    private boolean erase = false;
    private Bitmap canvasBitmap;
    private float brushSize, lastBrushSize;

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setupDrawing();
    }

    public void startNew() {
        drawCanvas.drawColor(0, PorterDuff.Mode.CLEAR);
        invalidate();
    }

    public void setErase(boolean isErase) {
        erase = isErase;
        if (erase) drawPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        else drawPaint.setXfermode(null);
    }

    public void setBrushSize(float newSize) {
        float pixelAmount = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, newSize, getResources().getDisplayMetrics());
        brushSize = pixelAmount;
        drawPaint.setStrokeWidth(brushSize);
    }

    public void getLastBrushSize(float lastSize) {
        lastBrushSize = lastSize;
    }

    public float getBrushSize() {
        return lastBrushSize;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        canvasBitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        drawCanvas = new Canvas(canvasBitmap);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(canvasBitmap, 0, 0, canvasPaint);
        canvas.drawPath(drawPath, drawPaint);
        //canvas.drawPath(pathErase, paintErase);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //if (isDraw) {
                drawPath.moveTo(eventX, eventY);
                break;

            case MotionEvent.ACTION_MOVE:
                drawPath.lineTo(eventX, eventY);
                break;

            case MotionEvent.ACTION_UP:
                drawCanvas.drawPath(drawPath, drawPaint);
                drawPath.reset();
                break;

            default:
                return false;
        }
        invalidate();
        return true;
    }

    public void setupDrawing() {
        drawPath = new Path();
        drawPaint = new Paint();
        drawPaint.setAntiAlias(true);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
        drawPaint.setColor(paintColor);
        drawPaint.setStrokeWidth(STROKE_WIDTH);

        canvasPaint = new Paint(Paint.DITHER_FLAG);
        brushSize = 10;
        lastBrushSize = brushSize;
        drawPaint.setStrokeWidth(brushSize);
    }
}
