package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.drawerlayout.widget.DrawerLayout;

import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomDrawerLayout extends DrawerLayout {

    private CustomCommonProperties customCommonProperties;

    public CustomDrawerLayout(@NonNull Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomDrawerLayout(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void SetDrawerLayout(Context context, PropertiesBean drawerLayoutDTO) {
        try {
            DrawerLayout.LayoutParams layoutparams;
            layoutparams = new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (drawerLayoutDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, drawerLayoutDTO.getWidth());
            }

            //Set Height
            if (drawerLayoutDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, drawerLayoutDTO.getHeight());
            }

            //To set ID
            if (drawerLayoutDTO.getId() != 0) {
                customCommonProperties.setID(context, this, drawerLayoutDTO.getId());
            }

            //openDrawer(GravityCompat.START);

            //To set Visibility
            if (drawerLayoutDTO.getVisibility() != null && !drawerLayoutDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, drawerLayoutDTO.getVisibility());
            }

            //set background color
            if (drawerLayoutDTO.getBackGroundColor() != null && !drawerLayoutDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, drawerLayoutDTO.getBackGroundColor());
            }

            if (drawerLayoutDTO.getBackGround() != null && !drawerLayoutDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, drawerLayoutDTO.getBackGround());
            }

            //Set fit system window
            if (drawerLayoutDTO.getIsFitSystemWindow()) {
                customCommonProperties.setFitsSystemWindow(context, this, false);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomDrawerLayout.class, "");
        }
    }
}
