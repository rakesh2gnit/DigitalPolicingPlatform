package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.content.ContextCompat;
import androidx.core.widget.CompoundButtonCompat;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomRadioButton extends AppCompatRadioButton {

    private CustomCommonProperties customCommonProperties;

    public CustomRadioButton(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomRadioButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRadioButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void SetRadioButton(Context context, PropertiesBean radioButtonDTO) {
        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (radioButtonDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, radioButtonDTO.getWidth());
            }

            //Set Height
            if (radioButtonDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, radioButtonDTO.getHeight());
            }

            //Set weight
            if (radioButtonDTO.getWeight() != 0) {
                customCommonProperties.setWeight(context, this, radioButtonDTO.getWeight());
            }

            //Set Margin
            if (radioButtonDTO.getMarginLeft() != 0 || radioButtonDTO.getMarginRight() != 0 || radioButtonDTO.getMarginTop() != 0 || radioButtonDTO.getMarginBottom() != 0) {

                customCommonProperties.setMargins(context, this, radioButtonDTO.getMarginLeft(), radioButtonDTO.getMarginTop(), radioButtonDTO.getMarginRight(), radioButtonDTO.getMarginBottom());
            }

            //To set ID
            if (radioButtonDTO.getId() != 0) {
                customCommonProperties.setID(context, this, radioButtonDTO.getId());
            }

            // To set PADDING
            if (radioButtonDTO.getPaddingLeft() != 0 || radioButtonDTO.getPaddingRight() != 0 || radioButtonDTO.getPaddingTop() != 0 || radioButtonDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, radioButtonDTO.getPaddingLeft(), radioButtonDTO.getPaddingTop(), radioButtonDTO.getPaddingRight(), radioButtonDTO.getPaddingBottom());
            } else {
                customCommonProperties.setPadding(context, this, 5, 0, 0, 0);
            }

            //To Set Text
            if (radioButtonDTO.getText() != null && !radioButtonDTO.getText().equalsIgnoreCase("")) {
                setText(context, this, radioButtonDTO.getText());
            }

            //TO set Text Size
            if (radioButtonDTO.getTextSize() != 0) {
                setTextSize(context, this, radioButtonDTO.getTextSize());
            }

            //To set text color
            if (radioButtonDTO.getTextColor() != null && !radioButtonDTO.getTextColor().equalsIgnoreCase("")) {
                setTextColor(context, this, radioButtonDTO.getTextColor());
            } else {
                setTextColor(ContextCompat.getColor(context, R.color.text_color_default));
            }

            //To set Visibility
            if (radioButtonDTO.getVisibility() != null && !radioButtonDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, radioButtonDTO.getVisibility().toLowerCase());
            }

            //To set check uncheck color
            if (radioButtonDTO.getCheckColor() != null && !radioButtonDTO.getCheckColor().equalsIgnoreCase("") && radioButtonDTO.getUnCheckColor() != null && !radioButtonDTO.getUnCheckColor().equalsIgnoreCase("")) {
                setCheckAndUncheckColor(context, this, radioButtonDTO.getCheckColor(), radioButtonDTO.getUnCheckColor());
            }

            //Set drawable selector
            if (radioButtonDTO.getButtonSelector() != null && !radioButtonDTO.getButtonSelector().equalsIgnoreCase("")) {
                setDrawableSelector(context, this, R.drawable.radiobutton_selector);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRadioButton.class, "");
        }
    }

    public void setText(Context context, CustomRadioButton view, String text) {
        try {
            view.setText(text);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRadioButton.class, "setText");
        }
    }

    public void setTextColor(Context context, CustomRadioButton view, String color) {
        try {
            view.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRadioButton.class, "setTextColor");
        }
    }

    public void setTextSize(Context context, CustomRadioButton view, int size) {
        try {
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRadioButton.class, "setTextSize");
        }
    }

    public void setCheckAndUncheckColor(Context context, CustomRadioButton view, String checkColor, String unCheckColor) {
        try {
            int[][] states = {{android.R.attr.state_checked}, {}};
            int[] colors = {Color.parseColor(checkColor), Color.parseColor(unCheckColor)};
            CompoundButtonCompat.setButtonTintList(view, new ColorStateList(states, colors));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRadioButton.class, "setCheckAndUncheckColor");
        }
    }

    public void setDrawableSelector(Context context, CustomRadioButton view, int drawable) {
        try {
            setButtonDrawable(ContextCompat.getDrawable(context, drawable));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRadioButton.class, "setDrawableSelector");
        }
    }
}