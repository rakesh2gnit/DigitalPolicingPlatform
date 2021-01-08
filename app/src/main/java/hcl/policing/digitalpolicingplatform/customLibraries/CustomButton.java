package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatButton;
import androidx.core.content.ContextCompat;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.DPtoPixelUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomButton extends AppCompatButton {

    private CustomCommonProperties customCommonProperties;

    public CustomButton(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void SetButton(Context context, PropertiesBean buttonDTO) {
        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (buttonDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, buttonDTO.getWidth());
            }

            //Set Height
            if (buttonDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, buttonDTO.getHeight());
            }

            //Set weight
            if (buttonDTO.getWeight() != 0) {
                customCommonProperties.setWeight(context, this, buttonDTO.getWeight());
            }

            //Set Margin
            if (buttonDTO.getMarginLeft() != 0 || buttonDTO.getMarginRight() != 0 || buttonDTO.getMarginTop() != 0 || buttonDTO.getMarginBottom() != 0) {

                customCommonProperties.setMargins(context, this, buttonDTO.getMarginLeft(), buttonDTO.getMarginTop(), buttonDTO.getMarginRight(), buttonDTO.getMarginBottom());
            }

            //To set Layout Gravity
            if (buttonDTO.getLayoutGravity() != null && !buttonDTO.getLayoutGravity().equalsIgnoreCase("")) {
                customCommonProperties.setLayoutGravity(context, this, buttonDTO.getLayoutGravity());
            }

            //To set ID
            if (buttonDTO.getId() != 0) {
                customCommonProperties.setID(context, this, buttonDTO.getId());
            }

            // set min height
            if (buttonDTO.getMinHeight() != 0) {
                setMinHeight(context, this, buttonDTO.getMinHeight());
            }

            // set min width
            if (buttonDTO.getMinWidth() != 0) {
                setMinWidth(context, this, buttonDTO.getMinWidth());
            }

            // set max height
            if (buttonDTO.getMaxHeight() != 0) {
                setMaxHeight(context, this, buttonDTO.getMaxHeight());
            }

            // set max width
            if (buttonDTO.getMaxWidth() != 0) {
                setMaxWidth(context, this, buttonDTO.getMaxWidth());
            }

            // To set PADDING
            if (buttonDTO.getPaddingLeft() != 0 || buttonDTO.getPaddingRight() != 0 || buttonDTO.getPaddingTop() != 0 || buttonDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, buttonDTO.getPaddingLeft(), buttonDTO.getPaddingTop(), buttonDTO.getPaddingRight(), buttonDTO.getPaddingBottom());
            } else {
                customCommonProperties.setPadding(context, this, 16, 8, 16, 8);
            }

            //Set background color
            if (buttonDTO.getBackGroundColor() != null && !buttonDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, buttonDTO.getBackGroundColor());
            }

            //Set drawable background
            if (buttonDTO.getBackGround() != null && !buttonDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, buttonDTO.getBackGround());
            }

            //To Set Text
            if (buttonDTO.getText() != null && !buttonDTO.getText().equalsIgnoreCase("")) {
                setText(context, this, buttonDTO.getText());
            }

            //TO set Text Size
            if (buttonDTO.getTextSize() != 0) {
                setTextSize(context, this, buttonDTO.getTextSize());
            }/* else {
            setTextSize(spToPx(12, context));
        }*/

            //To set text color
            if (buttonDTO.getTextColor() != null && !buttonDTO.getTextColor().equalsIgnoreCase("")) {
                setTextColor(context, this, buttonDTO.getTextColor());
            } else {
                setTextColor(ContextCompat.getColor(context, R.color.text_color_default));
            }

            // set characters AllCaps
            if (buttonDTO.getIsAllCaps()) {
                setAllCaps(context, this, true);
            } else {
                setAllCaps(context, this, false);
            }

            //To set text style
            if (buttonDTO.getTextStyle() != null && !buttonDTO.getTextStyle().equalsIgnoreCase("")) {
                setTextStyle(context, this, buttonDTO.getTextStyle().toLowerCase());
            }

            //To set Gravity
            if (buttonDTO.getGravity() != null && !buttonDTO.getGravity().equalsIgnoreCase("")) {
                setGravity(context, this, buttonDTO.getGravity().toLowerCase());
            } else {
                setGravity(context, this, "center");
            }

            //To set Visibility
            if (buttonDTO.getVisibility() != null && !buttonDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, buttonDTO.getVisibility().toLowerCase());
            }

            setClick(context, this, buttonDTO.getOnClickListener());

            //SET drawable left top right bottom
            //setDrawablesLRTB(context, this, 0, 0, 0, 0);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomButton.class, "");
        }
    }

    public void setMinWidth(Context context, CustomButton view, int width) {
        try {
            view.setMinWidth(DPtoPixelUtil.convertDpToPixel(width, context));
            view.setMinimumWidth(DPtoPixelUtil.convertDpToPixel(width, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomButton.class, "setMinWidth");
        }
    }

    public void setMinHeight(Context context, CustomButton view, int height) {
        try {
            view.setMinHeight(DPtoPixelUtil.convertDpToPixel(height, context));
            view.setMinimumHeight(DPtoPixelUtil.convertDpToPixel(height, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomButton.class, "setMinHeight");
        }
    }

    public void setMaxHeight(Context context, CustomButton view, int height) {
        try {
            view.setMaxHeight(DPtoPixelUtil.convertDpToPixel(height, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomButton.class, "setMaxHeight");
        }
    }

    public void setMaxWidth(Context context, CustomButton view, int width) {
        try {
            view.setMaxWidth(DPtoPixelUtil.convertDpToPixel(width, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomButton.class, "setMaxWidth");
        }
    }

    public void setText(Context context, CustomButton view, String text) {
        try {
            view.setText(text);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomButton.class, "setText");
        }
    }

    public void setTextColor(Context context, CustomButton view, String color) {
        try {
            view.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomButton.class, "setTextColor");
        }
    }

    public void setTextSize(Context context, CustomButton view, int size) {
        try {
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomButton.class, "setTextSize");
        }
    }

    public void setAllCaps(Context context, CustomButton view, boolean flag) {
        try {
            view.setAllCaps(flag);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomButton.class, "setAllCaps");
        }
    }

    public void setTextStyle(Context context, CustomButton view, String type) {
        try {
            switch (type.toLowerCase()) {
                case ViewPropertiesConstant.Bold:
                    view.setTypeface(null, Typeface.BOLD);
                    break;

                case ViewPropertiesConstant.Italic:
                    view.setTypeface(null, Typeface.ITALIC);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomButton.class, "setTextStyle");
        }
    }

    public void setGravity(Context context, CustomButton view, String gravity) {
        try {
            switch (gravity.toLowerCase()) {
                case ViewPropertiesConstant.Center:
                    view.setGravity(Gravity.CENTER);
                    break;

                case ViewPropertiesConstant.CenterVertical:
                    view.setGravity(Gravity.CENTER_VERTICAL);
                    break;

                case ViewPropertiesConstant.CenterHorizontal:
                    view.setGravity(Gravity.CENTER_HORIZONTAL);
                    break;

                case ViewPropertiesConstant.ClipVertical:
                    view.setGravity(Gravity.CLIP_VERTICAL);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomButton.class, "setGravity");
        }
    }

    public void setDrawablesLRTB(Context context, CustomButton view, int left, int top, int right, int bottom) {
        try {
            view.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomButton.class, "setDrawablesLRTB");
        }
    }

    public void setClick(Context context, CustomButton view, View.OnClickListener onClickL) {
        try {
            view.setOnClickListener(onClickL);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomButton.class, "setDrawablesLRTB");
        }
    }
}
