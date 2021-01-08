package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomView extends View {

    private CustomCommonProperties customCommonProperties;

    public CustomView(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void SetView(@NonNull Context context, PropertiesBean viewDTO) {
        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (viewDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, viewDTO.getWidth());
            }

            //Set Height
            if (viewDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, viewDTO.getHeight());
            }

            //SetWeight
            if (viewDTO.getWeight() != 0) {
                customCommonProperties.setWeight(context, this, viewDTO.getWeight());
            }

            //Set Margin
            if (viewDTO.getMarginLeft() != 0 || viewDTO.getMarginRight() != 0 || viewDTO.getMarginTop() != 0 || viewDTO.getMarginBottom() != 0) {
                customCommonProperties.setMargins(context, this, viewDTO.getMarginLeft(), viewDTO.getMarginTop(), viewDTO.getMarginRight(), viewDTO.getMarginBottom());
            }

            //To set ID
            if (viewDTO.getId() != 0) {
                customCommonProperties.setID(context, this, viewDTO.getId());
            }

            //Set background color
            if (viewDTO.getBackGroundColor() != null && !viewDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, viewDTO.getBackGroundColor());
            }

            if (viewDTO.getBackGround() != null && !viewDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, viewDTO.getBackGround());
            }

            //To set Visibility
            if (viewDTO.getVisibility() != null && !viewDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, viewDTO.getVisibility());
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomView.class, "");
        }
    }
}
