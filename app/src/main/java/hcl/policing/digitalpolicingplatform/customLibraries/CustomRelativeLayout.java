package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomRelativeLayout extends RelativeLayout {

    private CustomCommonProperties customCommonProperties;

    public CustomRelativeLayout(Context context) {
        super(context);
        /*RelativeLayout.LayoutParams layoutparams;
        layoutparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(layoutparams);*/
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomRelativeLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRelativeLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomRelativeLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void SetRelativeLayout(@NonNull Context context, PropertiesBean relativeLayoutDTO) {
        try {
            RelativeLayout.LayoutParams layoutparams;
            layoutparams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (relativeLayoutDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, relativeLayoutDTO.getWidth());
            }

            //Set Height
            if (relativeLayoutDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, relativeLayoutDTO.getHeight());
            }

            //Set Weight
            if (relativeLayoutDTO.getWeight() != 0) {
                customCommonProperties.setWeight(context, this, relativeLayoutDTO.getWeight());
            }

            //To set Layout Gravity
            if (relativeLayoutDTO.getLayoutGravity() != null && !relativeLayoutDTO.getLayoutGravity().equalsIgnoreCase("")) {
                customCommonProperties.setLayoutGravity(context, this, relativeLayoutDTO.getLayoutGravity());
            }

            //Set Margin
            if (relativeLayoutDTO.getMarginLeft() != 0 || relativeLayoutDTO.getMarginRight() != 0 || relativeLayoutDTO.getMarginTop() != 0 || relativeLayoutDTO.getMarginBottom() != 0) {
                customCommonProperties.setMargins(context, this, relativeLayoutDTO.getMarginLeft(), relativeLayoutDTO.getMarginTop(), relativeLayoutDTO.getMarginRight(), relativeLayoutDTO.getMarginBottom());
            }

            //To set ID
            if (relativeLayoutDTO.getId() != 0) {
                customCommonProperties.setID(context, this, relativeLayoutDTO.getId());
            }

            // To set PADDING
            if (relativeLayoutDTO.getPaddingLeft() != 0 || relativeLayoutDTO.getPaddingRight() != 0 || relativeLayoutDTO.getPaddingTop() != 0 || relativeLayoutDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, relativeLayoutDTO.getPaddingLeft(), relativeLayoutDTO.getPaddingTop(), relativeLayoutDTO.getPaddingRight(), relativeLayoutDTO.getPaddingBottom());
            }

            //To set Visibility
            if (relativeLayoutDTO.getVisibility() != null && !relativeLayoutDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, relativeLayoutDTO.getVisibility());
            }

            //set background color
            if (relativeLayoutDTO.getBackGroundColor() != null && !relativeLayoutDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, relativeLayoutDTO.getBackGroundColor());
            }

            if (relativeLayoutDTO.getBackGround() != null && !relativeLayoutDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, relativeLayoutDTO.getBackGround());
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRelativeLayout.class, "");
        }
    }
}
