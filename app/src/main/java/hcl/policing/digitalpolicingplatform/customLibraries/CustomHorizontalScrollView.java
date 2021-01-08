package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomHorizontalScrollView extends HorizontalScrollView {

    private CustomCommonProperties customCommonProperties;

    public CustomHorizontalScrollView(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomHorizontalScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomHorizontalScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void SetScrollView(@NonNull Context context, PropertiesBean scrollViewDTO) {
        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (scrollViewDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, scrollViewDTO.getWidth());
            }

            //Set Height
            if (scrollViewDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, scrollViewDTO.getHeight());
            }

            //Set Weight
            if (scrollViewDTO.getWeight() != 0) {
                customCommonProperties.setWeight(context, this, scrollViewDTO.getWeight());
            }

            //To set Layout Gravity
            if (scrollViewDTO.getLayoutGravity() != null && !scrollViewDTO.getLayoutGravity().equalsIgnoreCase("")) {
                customCommonProperties.setLayoutGravity(context, this, scrollViewDTO.getLayoutGravity());
            }

            //Set Margin
            if (scrollViewDTO.getMarginLeft() != 0 || scrollViewDTO.getMarginRight() != 0 || scrollViewDTO.getMarginTop() != 0 || scrollViewDTO.getMarginBottom() != 0) {

                customCommonProperties.setMargins(context, this, scrollViewDTO.getMarginLeft(), scrollViewDTO.getMarginTop(), scrollViewDTO.getMarginRight(), scrollViewDTO.getMarginBottom());
            }

            //To set ID
            if (scrollViewDTO.getId() != 0) {
                customCommonProperties.setID(context, this, scrollViewDTO.getId());
            }

            // To set PADDING
            if (scrollViewDTO.getPaddingLeft() != 0 || scrollViewDTO.getPaddingRight() != 0 || scrollViewDTO.getPaddingTop() != 0 || scrollViewDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, scrollViewDTO.getPaddingLeft(), scrollViewDTO.getPaddingTop(), scrollViewDTO.getPaddingRight(), scrollViewDTO.getPaddingBottom());
            }

            //To set Visibility
            if (scrollViewDTO.getVisibility() != null && !scrollViewDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, scrollViewDTO.getVisibility());
            }

            //set background color
            if (scrollViewDTO.getBackGroundColor() != null && !scrollViewDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, scrollViewDTO.getBackGroundColor());
            }

            if (scrollViewDTO.getBackGround() != null && !scrollViewDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, scrollViewDTO.getBackGround());
            }

            // set layout orientation
            setHorizontalScrollBarEnabled(false);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomHorizontalScrollView.class, "");
        }
    }
}
