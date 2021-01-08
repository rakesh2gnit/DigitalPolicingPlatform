package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import com.google.android.material.appbar.AppBarLayout;

import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomAppBarLayout extends AppBarLayout {

    private CustomCommonProperties customCommonProperties;

    public CustomAppBarLayout(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomAppBarLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Set App Bar layout
     * @param context
     * @param appBarLayoutDTO
     */
    public void SetAppBarLayout(Context context, PropertiesBean appBarLayoutDTO) {
        try {
            LayoutParams layoutparams;
            layoutparams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (appBarLayoutDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, appBarLayoutDTO.getWidth());
            }

            //Set Height
            if (appBarLayoutDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, appBarLayoutDTO.getHeight());
            }

            //To set ID
            if (appBarLayoutDTO.getId() != 0) {
                customCommonProperties.setID(context, this, appBarLayoutDTO.getId());
            }

            //Set fit system window
            if (appBarLayoutDTO.getIsFitSystemWindow()) {
                customCommonProperties.setFitsSystemWindow(context, this, true);
            }

            //To set Visibility
            if (appBarLayoutDTO.getVisibility() != null && !appBarLayoutDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, appBarLayoutDTO.getVisibility());
            }

            //set background color
            if (appBarLayoutDTO.getBackGroundColor() != null && !appBarLayoutDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, appBarLayoutDTO.getBackGroundColor());
            }

            //setBackground(null);
            //Set drawable background
            if (appBarLayoutDTO.getBackGround() != null && !appBarLayoutDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, appBarLayoutDTO.getBackGround());
            } else {
                setBackground(null);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomAppBarLayout.class, "");
        }
    }
}
