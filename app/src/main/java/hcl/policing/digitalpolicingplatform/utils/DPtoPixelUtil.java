package hcl.policing.digitalpolicingplatform.utils;

import android.content.Context;
import android.util.DisplayMetrics;

public class DPtoPixelUtil {

    public static int convertDpToPixel(int dp, Context context){
        return dp * ((int) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }
}
