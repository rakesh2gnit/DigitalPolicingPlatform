package hcl.policing.digitalpolicingplatform.utils;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.Shape;
import android.util.DisplayMetrics;

import androidx.annotation.ColorInt;

public class DrawableUtil {

    public static Drawable getTextDrawable(@ColorInt int iColor) {
        Shape shape = new Shape(){

            @Override
            public void draw(Canvas canvas, Paint paint)
            {
                paint.setColor(iColor);

                paint.setTextSize(15);

                //int radii = convertDpToPixel(3, DrawableUtil.this);

                canvas.drawText("Hello Canvas", canvas.getWidth() - 150, canvas.getHeight() / 2, paint);

                canvas.drawRect(15, 15, 15, 15, paint);
            }
        };
        shape.getHeight();

        Drawable drawable = new ShapeDrawable(shape);

        return drawable;
    }

    public static int convertDpToPixel(int dp, Context context){
        return dp * ((int) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
