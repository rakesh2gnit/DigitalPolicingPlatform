package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomLinearLayout extends LinearLayout {

    private CustomCommonProperties customCommonProperties;

    public CustomLinearLayout(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomLinearLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void SetLinearLayout(@NonNull Context context, PropertiesBean linearLayoutDTO) {
        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (linearLayoutDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, linearLayoutDTO.getWidth());
            }

            //Set Height
            if (linearLayoutDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, linearLayoutDTO.getHeight());
            }

            //SetWeight
            if (linearLayoutDTO.getWeight() != 0) {
                customCommonProperties.setWeight(context, this, linearLayoutDTO.getWeight());
            }

            //To set Layout Gravity
            if (linearLayoutDTO.getLayoutGravity() != null && !linearLayoutDTO.getLayoutGravity().equalsIgnoreCase("")) {
                customCommonProperties.setLayoutGravity(context, this, linearLayoutDTO.getLayoutGravity());
            }

            //Set Margin
            if (linearLayoutDTO.getMarginLeft() != 0 || linearLayoutDTO.getMarginRight() != 0 || linearLayoutDTO.getMarginTop() != 0 || linearLayoutDTO.getMarginBottom() != 0) {
                customCommonProperties.setMargins(context, this, linearLayoutDTO.getMarginLeft(), linearLayoutDTO.getMarginTop(), linearLayoutDTO.getMarginRight(), linearLayoutDTO.getMarginBottom());
            }

            //To set ID
            if (linearLayoutDTO.getId() != 0) {
                customCommonProperties.setID(context, this, linearLayoutDTO.getId());
            }

            // To srt PADDING
            if (linearLayoutDTO.getPaddingLeft() != 0 || linearLayoutDTO.getPaddingRight() != 0 || linearLayoutDTO.getPaddingTop() != 0 || linearLayoutDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, linearLayoutDTO.getPaddingLeft(), linearLayoutDTO.getPaddingTop(), linearLayoutDTO.getPaddingRight(), linearLayoutDTO.getPaddingBottom());
            }

            //Set background color
            if (linearLayoutDTO.getBackGroundColor() != null && !linearLayoutDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, linearLayoutDTO.getBackGroundColor());
            }

            //Set drawable background
            if (linearLayoutDTO.getBackGround() != null && !linearLayoutDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, linearLayoutDTO.getBackGround());
            }

            //To set Visibility
            if (linearLayoutDTO.getVisibility() != null && !linearLayoutDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, linearLayoutDTO.getVisibility());
            }

            //To set Gravity
            if (linearLayoutDTO.getGravity() != null && !linearLayoutDTO.getGravity().equalsIgnoreCase("")) {
                setGravity(context, this, linearLayoutDTO.getGravity());
            }

            // set layout orientation
            if (linearLayoutDTO.getOrientation() != null && !linearLayoutDTO.getOrientation().equalsIgnoreCase("")) {
                setOrientation(context, this, linearLayoutDTO.getOrientation());
            } else {
                setOrientation(context, this, ViewPropertiesConstant.Vertical);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomLinearLayout.class, "");
        }
    }

    public void setGravity(Context context, CustomLinearLayout view, String gravity) {
        try {
            switch (gravity) {
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
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomLinearLayout.class, "setGravity");
        }
    }

    public void setOrientation(Context context, CustomLinearLayout view, String orientation) {
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
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomLinearLayout.class, "setOrientation");
        }
    }
}
