package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.content.res.Resources;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatSpinner;

import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomSpinner extends AppCompatSpinner {

    private CustomCommonProperties customCommonProperties;

    public CustomSpinner(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomSpinner(Context context, int mode) {
        super(context, mode);
    }

    public CustomSpinner(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode) {
        super(context, attrs, defStyleAttr, mode);
    }

    public CustomSpinner(Context context, AttributeSet attrs, int defStyleAttr, int mode, Resources.Theme popupTheme) {
        super(context, attrs, defStyleAttr, mode, popupTheme);
    }

    public void SetSpinner(Context context, PropertiesBean spinnerDTO) {
        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (spinnerDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, spinnerDTO.getWidth());
            }

            //Set Height
            if (spinnerDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, spinnerDTO.getHeight());
            }

            //Set weight
            if (spinnerDTO.getWeight() != 0) {
                customCommonProperties.setWeight(context, this, spinnerDTO.getWeight());
            }

            //Set Margin
            if (spinnerDTO.getMarginLeft() != 0 || spinnerDTO.getMarginRight() != 0 || spinnerDTO.getMarginTop() != 0 || spinnerDTO.getMarginBottom() != 0) {

                customCommonProperties.setMargins(context, this, spinnerDTO.getMarginLeft(), spinnerDTO.getMarginTop(), spinnerDTO.getMarginRight(), spinnerDTO.getMarginBottom());
            }

            //To set ID
            if (spinnerDTO.getId() != 0) {
                customCommonProperties.setID(context, this, spinnerDTO.getId());
            }

            // To set PADDING
            if (spinnerDTO.getPaddingLeft() != 0 || spinnerDTO.getPaddingRight() != 0 || spinnerDTO.getPaddingTop() != 0 || spinnerDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, spinnerDTO.getPaddingLeft(), spinnerDTO.getPaddingTop(), spinnerDTO.getPaddingRight(), spinnerDTO.getPaddingBottom());
            } else {
                customCommonProperties.setPadding(context, this, 5, 0, 0, 0);
            }

            // To srt PADDING
            if (spinnerDTO.getPaddingLeft() != 0 || spinnerDTO.getPaddingRight() != 0 || spinnerDTO.getPaddingTop() != 0 || spinnerDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, spinnerDTO.getPaddingLeft(), spinnerDTO.getPaddingTop(), spinnerDTO.getPaddingRight(), spinnerDTO.getPaddingBottom());
            }

            //Set background color
            if (spinnerDTO.getBackGroundColor() != null && !spinnerDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, spinnerDTO.getBackGroundColor());
            }

            //Set drawable background
            if (spinnerDTO.getBackGround() != null && !spinnerDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, spinnerDTO.getBackGround());
            }

            //To set Gravity
            if (spinnerDTO.getGravity() != null && !spinnerDTO.getGravity().equalsIgnoreCase("")) {
                setGravity(context, this, spinnerDTO.getGravity());
            } else {
                setGravity(context, this, ViewPropertiesConstant.CenterVertical);
            }

            //To set Visibility
            if (spinnerDTO.getVisibility() != null && !spinnerDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, spinnerDTO.getVisibility());
            }

            /*if(spinnerDTO.getList() != null && spinnerDTO.getList().length > 0) {
                setList(context, this, spinnerDTO.getList());
            }*/
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRadioButton.class, "");
        }
    }

    public void setGravity(Context context, CustomSpinner view, String gravity) {
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
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomSpinner.class, "setGravity");
        }
    }

    public void setList(Context context, CustomSpinner customSpinner, String[] list, String selectedValue) {
        //Creating the ArrayAdapter instance having the country list
        ArrayAdapter<String> aa = new ArrayAdapter<>(context, android.R.layout.simple_spinner_item, list);
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //Setting the ArrayAdapter data on the Spinner
        customSpinner.setAdapter(aa);
        if (selectedValue != null && !selectedValue.equalsIgnoreCase("")) {
            int spinnerPosition = aa.getPosition(selectedValue);
            customSpinner.setSelection(spinnerPosition);
        }
    }
}
