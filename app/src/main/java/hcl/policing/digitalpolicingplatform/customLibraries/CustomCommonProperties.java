package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.utils.DPtoPixelUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;


public class CustomCommonProperties {

    public void setID(Context context, View view, int id) {
        try {
            view.setId(id);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCommonProperties.class, "setID");
        }
    }

    public void setLayoutParams(Context context, View view) {
        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            view.setLayoutParams(layoutparams);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCommonProperties.class, "setWidth");
        }
    }

    public void setWidth(Context context, View view, int width) {
        try {
            //Log.e("Common ", " width>> ");
            if (view.getLayoutParams() != null) {
                //Log.e("Common inside ", " width>> ");
                if (width == -1) {
                    view.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
                } else {
                    view.getLayoutParams().width = DPtoPixelUtil.convertDpToPixel(width, context);
                }
                view.requestLayout();
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCommonProperties.class, "setWidth");
        }
    }

    public void setHeight(Context context, View view, int height) {
        try {
            if (view.getLayoutParams() != null) {
                if (height == -1) {
                    view.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
                } if (height == 0) {
                    view.getLayoutParams().height = ViewGroup.LayoutParams.WRAP_CONTENT;
                } else {
                    view.getLayoutParams().height = DPtoPixelUtil.convertDpToPixel(height, context);
                }
                view.requestLayout();
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCommonProperties.class, "setHeight");
        }
    }

    public void setWeight(Context context, View view, int weight) {
        try {
            LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(view.getLayoutParams().width, view.getLayoutParams().height);
            layoutparams.weight = (float) weight;
            view.setLayoutParams(layoutparams);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCommonProperties.class, "setWeight");
        }
    }

    public void setMargins(Context context, View view, int left, int top, int right, int bottom) {
        try {
            if (view.getLayoutParams() instanceof ViewGroup.MarginLayoutParams) {
                ViewGroup.MarginLayoutParams p = (ViewGroup.MarginLayoutParams) view.getLayoutParams();
                p.setMargins(DPtoPixelUtil.convertDpToPixel(left, context), DPtoPixelUtil.convertDpToPixel(top, context), DPtoPixelUtil.convertDpToPixel(right, context), DPtoPixelUtil.convertDpToPixel(bottom, context));
                view.requestLayout();
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCommonProperties.class, "setMargins");
        }
    }

    public void setLayoutGravity(Context context, View view, String gravity) {
        try {
            LinearLayout.LayoutParams layoutparams = new LinearLayout.LayoutParams(view.getLayoutParams().width, view.getLayoutParams().height);
            switch (gravity.toLowerCase()) {
                case ViewPropertiesConstant.Center:
                    layoutparams.gravity = Gravity.CENTER;
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.CenterVertical:
                    layoutparams.gravity = Gravity.CENTER_VERTICAL;
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.CenterHorizontal:
                    layoutparams.gravity = Gravity.CENTER_HORIZONTAL;
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.Start:
                    layoutparams.gravity = Gravity.START;
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.End:
                    layoutparams.gravity = Gravity.END;
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.Top:
                    layoutparams.gravity = Gravity.TOP;
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.Bottom:
                    layoutparams.gravity = Gravity.BOTTOM;
                    view.setLayoutParams(layoutparams);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCommonProperties.class, "setLayoutGravity");
        }
    }

    public void setPadding(Context context, View view, int left, int top, int right, int bottom) {
        try {
            view.setPadding(DPtoPixelUtil.convertDpToPixel(left, context), DPtoPixelUtil.convertDpToPixel(top, context), DPtoPixelUtil.convertDpToPixel(right, context), DPtoPixelUtil.convertDpToPixel(bottom, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCommonProperties.class, "setPadding");
        }
    }

    public static void setBackgroundColor(Context context, View view, String color) {
        try {
            view.setBackgroundColor(Color.parseColor(color));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCommonProperties.class, "setBackgroundColor");
        }
    }

    public void setBackgroundDrawable(Context context, View view, String drawable) {
        try {
            switch (drawable.toLowerCase()) {
                case ViewPropertiesConstant.BackgroundGradient:
                    view.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gradient));
                    break;

                case ViewPropertiesConstant.BackgroundRed:
                    view.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_red_circle));
                    break;

                case ViewPropertiesConstant.BackgroundGreen:
                    view.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_green));
                    break;

                case ViewPropertiesConstant.BackgroundDarkGreen:
                    view.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_green_dark));
                    break;

                case ViewPropertiesConstant.BackgroundBlue:
                    view.setBackground(ContextCompat.getDrawable(context, R.drawable.blue_background));
                    break;

                case ViewPropertiesConstant.BackgroundButton:
                    view.setBackground(ContextCompat.getDrawable(context, R.drawable.btn_background));
                    break;

                case ViewPropertiesConstant.BackgroundSpinner:
                    view.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_gray_corner));
                    break;

                case ViewPropertiesConstant.BackgroundGreenCircle:
                    view.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_green_circle));
                    break;

                case ViewPropertiesConstant.BackgroundAssistant:
                    view.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_assistant));
                    break;

                case ViewPropertiesConstant.BackgroundUser:
                    view.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_user));
                    break;

                case ViewPropertiesConstant.BGLeftBlue:
                    view.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_blue_left_round));
                    break;

                case ViewPropertiesConstant.BGRightBlue:
                    view.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_blue_right_round));
                    break;

                case ViewPropertiesConstant.BGLeftYellow:
                    view.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_yellow_left_round));
                    break;

                case ViewPropertiesConstant.BGRightYellow:
                    view.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_yellow_right_round));
                    break;

                case ViewPropertiesConstant.BGYellowCorner:
                    view.setBackground(ContextCompat.getDrawable(context, R.drawable.bg_yellow_corner));
                    break;

                default:
                    view.setBackground(null);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCommonProperties.class, "setBackgroundDrawable");
        }
    }

    public void setVisibility(Context context, View view, String type) {
        try {
            switch (type.toLowerCase()) {
                case ViewPropertiesConstant.Gone:
                    view.setVisibility(View.GONE);
                    break;

                case ViewPropertiesConstant.Visible:
                    view.setVisibility(View.VISIBLE);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCommonProperties.class, "setVisibility");
        }
    }

    public void setFitsSystemWindow(Context context, View view, boolean flag) {
        try {
            view.setFitsSystemWindows(flag);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomAppBarLayout.class, "setFitsSystemWindow");
        }
    }
}
