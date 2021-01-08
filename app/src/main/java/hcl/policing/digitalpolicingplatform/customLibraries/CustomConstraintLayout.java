package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.constraintlayout.widget.ConstraintLayout;

import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomConstraintLayout extends ConstraintLayout {

    private CustomCommonProperties customCommonProperties;

    public CustomConstraintLayout(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomConstraintLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomConstraintLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void SetConstraintLayout(Context context, PropertiesBean constraintLayoutDTO) {
        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (constraintLayoutDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, constraintLayoutDTO.getWidth());
            }

            //Set Height
            if (constraintLayoutDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, constraintLayoutDTO.getHeight());
            }

            //Set Weight
            if (constraintLayoutDTO.getWeight() != 0) {
                customCommonProperties.setWeight(context, this, constraintLayoutDTO.getWeight());
            }

            //To set Layout Gravity
            if (constraintLayoutDTO.getLayoutGravity() != null && !constraintLayoutDTO.getLayoutGravity().equalsIgnoreCase("")) {
                customCommonProperties.setLayoutGravity(context, this, constraintLayoutDTO.getLayoutGravity());
            }

            //Set Margin
            if (constraintLayoutDTO.getMarginLeft() != 0 || constraintLayoutDTO.getMarginRight() != 0 || constraintLayoutDTO.getMarginTop() != 0 || constraintLayoutDTO.getMarginBottom() != 0) {
                customCommonProperties.setMargins(context, this, constraintLayoutDTO.getMarginLeft(), constraintLayoutDTO.getMarginTop(), constraintLayoutDTO.getMarginRight(), constraintLayoutDTO.getMarginBottom());
            }

            //To set ID
            if (constraintLayoutDTO.getId() != 0) {
                customCommonProperties.setID(context, this, constraintLayoutDTO.getId());
            }

            //To set PADDING
            if (constraintLayoutDTO.getPaddingLeft() != 0 || constraintLayoutDTO.getPaddingRight() != 0 || constraintLayoutDTO.getPaddingTop() != 0 || constraintLayoutDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, constraintLayoutDTO.getPaddingLeft(), constraintLayoutDTO.getPaddingTop(), constraintLayoutDTO.getPaddingRight(), constraintLayoutDTO.getPaddingBottom());
            }

            //To set Visibility
            if (constraintLayoutDTO.getVisibility() != null && !constraintLayoutDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, constraintLayoutDTO.getVisibility());
            }

            //set background color
            if (constraintLayoutDTO.getBackGroundColor() != null && !constraintLayoutDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, constraintLayoutDTO.getBackGroundColor());
            }

            if (constraintLayoutDTO.getBackGround() != null && !constraintLayoutDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, constraintLayoutDTO.getBackGround());
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomConstraintLayout.class, "");
        }
    }
}
