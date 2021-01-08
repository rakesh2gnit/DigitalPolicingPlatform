package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomRadioGroup extends RadioGroup {

    private CustomCommonProperties customCommonProperties;

    public CustomRadioGroup(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomRadioGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void SetRadioGroup(Context context, PropertiesBean radioGroupDTO) {
        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (radioGroupDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, radioGroupDTO.getWidth());
            }

            //Set Height
            if (radioGroupDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, radioGroupDTO.getHeight());
            }

            //To set Layout Gravity
            if (radioGroupDTO.getLayoutGravity() != null && !radioGroupDTO.getLayoutGravity().equalsIgnoreCase("")) {
                customCommonProperties.setLayoutGravity(context, this, radioGroupDTO.getLayoutGravity());
            }

            //Set Margin
            if (radioGroupDTO.getMarginLeft() != 0 || radioGroupDTO.getMarginRight() != 0 || radioGroupDTO.getMarginTop() != 0 || radioGroupDTO.getMarginBottom() != 0) {

                customCommonProperties.setMargins(context, this, radioGroupDTO.getMarginLeft(), radioGroupDTO.getMarginTop(), radioGroupDTO.getMarginRight(), radioGroupDTO.getMarginBottom());
            }

            //To set ID
            if (radioGroupDTO.getId() != 0) {
                customCommonProperties.setID(context, this, radioGroupDTO.getId());
            }

            // To set PADDING
            if (radioGroupDTO.getPaddingLeft() != 0 || radioGroupDTO.getPaddingRight() != 0 || radioGroupDTO.getPaddingTop() != 0 || radioGroupDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, radioGroupDTO.getPaddingLeft(), radioGroupDTO.getPaddingTop(), radioGroupDTO.getPaddingRight(), radioGroupDTO.getPaddingBottom());
            } else {
                customCommonProperties.setPadding(context, this, 5, 0, 0, 0);
            }

            //Set orientation
            if (radioGroupDTO.getOrientation() != null && !radioGroupDTO.getOrientation().equalsIgnoreCase("")) {
                setOrientation(context, this, radioGroupDTO.getOrientation());
            }

            //To set Visibility
            if (radioGroupDTO.getVisibility() != null && !radioGroupDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, radioGroupDTO.getVisibility().toLowerCase());
            }

            //To add radiobutton
            /*if (radioGroupDTO.getRadioButtonList() != null && radioGroupDTO.getRadioButtonList().size() > 0) {
                addRadioButton(context, this, radioGroupDTO.getRadioButtonList());
            }*/
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRadioGroup.class, "");
        }
    }

    public void setOrientation(Context context, CustomRadioGroup view, String orientation) {
        try {
            switch (orientation.toLowerCase()) {
                case ViewPropertiesConstant.Horizontal:
                    view.setOrientation(HORIZONTAL);
                    break;

                case ViewPropertiesConstant.Vertical:
                    view.setOrientation(VERTICAL);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRadioGroup.class, "setOrientation");
        }
    }

    public void addRadioButton(Context context, CustomRadioGroup view, ArrayList<RadioButton> radioButtonList) {
        try {
            for (int i = 0; i < radioButtonList.size(); i++) {
                view.addView(radioButtonList.get(i));
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRadioGroup.class, "addRadioButton");
        }
    }
}
