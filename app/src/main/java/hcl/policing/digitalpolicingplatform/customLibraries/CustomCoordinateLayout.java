package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomCoordinateLayout extends CoordinatorLayout {

    private CustomCommonProperties customCommonProperties;

    public CustomCoordinateLayout(@NonNull Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomCoordinateLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCoordinateLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void SetCoordinateLayout(Context context, PropertiesBean coordinateLayoutDTO) {
        try {
            LayoutParams layoutparams;
            layoutparams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (coordinateLayoutDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, coordinateLayoutDTO.getWidth());
            }

            //Set Height
            if (coordinateLayoutDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, coordinateLayoutDTO.getHeight());
            }

            //To set ID
            if (coordinateLayoutDTO.getId() != 0) {
                customCommonProperties.setID(context, this, coordinateLayoutDTO.getId());
            }

            //Set fit system window
            if (coordinateLayoutDTO.getIsFitSystemWindow()) {
                customCommonProperties.setFitsSystemWindow(context, this, true);
            }

            //To set Visibility
            if (coordinateLayoutDTO.getVisibility() != null && !coordinateLayoutDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, coordinateLayoutDTO.getVisibility());
            }

            //set background color
            if (coordinateLayoutDTO.getBackGroundColor() != null && !coordinateLayoutDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, coordinateLayoutDTO.getBackGroundColor());
            }

            if (coordinateLayoutDTO.getBackGround() != null && !coordinateLayoutDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, coordinateLayoutDTO.getBackGround());
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCoordinateLayout.class, "");
        }
    }
}
