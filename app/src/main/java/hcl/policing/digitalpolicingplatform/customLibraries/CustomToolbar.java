package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.appbar.AppBarLayout;

import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomToolbar extends Toolbar {

    private CustomCommonProperties customCommonProperties;

    public CustomToolbar(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void SetToolbar(@NonNull Context context, PropertiesBean toolbarDTO) {
        try {
            int height = actionbarHeight(context);
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height);
            setLayoutParams(layoutparams);

            //To Set Width
            if (toolbarDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, toolbarDTO.getWidth());
            }

            //Set Height
            if (toolbarDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, toolbarDTO.getHeight());
            }

            //To set ID
            if (toolbarDTO.getId() != 0) {
                customCommonProperties.setID(context, this, toolbarDTO.getId());
            }

            //To set Layout Gravity
            if (toolbarDTO.getLayoutGravity() != null && !toolbarDTO.getLayoutGravity().equalsIgnoreCase("")) {
                customCommonProperties.setLayoutGravity(context, this, toolbarDTO.getLayoutGravity());
            }

            // To set PADDING
            if (toolbarDTO.getPaddingLeft() != 0 || toolbarDTO.getPaddingRight() != 0 || toolbarDTO.getPaddingTop() != 0 || toolbarDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, toolbarDTO.getPaddingLeft(), toolbarDTO.getPaddingTop(), toolbarDTO.getPaddingRight(), toolbarDTO.getPaddingBottom());
            }

            //To set Visibility
            if (toolbarDTO.getVisibility() != null && !toolbarDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, toolbarDTO.getVisibility());
            }

            //set background color
            if (toolbarDTO.getBackGroundColor() != null && !toolbarDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, toolbarDTO.getBackGroundColor());
            }

            if (toolbarDTO.getBackGround() != null && !toolbarDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, toolbarDTO.getBackGround());
            }

            //to set tittle
            if (toolbarDTO.getTitle() != null && !toolbarDTO.getTitle().equalsIgnoreCase("")) {
                setTittle(context, this, toolbarDTO.getTitle());
            }

            //set scroll flags
            if (toolbarDTO.getScrollFlag() != null && !toolbarDTO.getScrollFlag().equalsIgnoreCase("")) {
                setScrollFlags(context, this, toolbarDTO.getScrollFlag());
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomToolbar.class, "");
        }
    }

    public void setTittle(Context context, CustomToolbar view, String title) {
        try {
            view.setTitle(title);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomToolbar.class, "setTittle");
        }
    }

    public void setTitleColor(Context context, CustomToolbar view, String color) {
        try {
            view.setTitleTextColor(Color.parseColor(color));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomToolbar.class, "setTitleColor");
        }
    }

    public void setTitleSize(Context context, CustomToolbar view, int size) {
        try {
            //view.settitlete(TypedValue.COMPLEX_UNIT_SP, size);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomToolbar.class, "setTitleSize");
        }
    }

    public void setScrollFlags(Context context, CustomToolbar view, String gravity) {
        try {
            AppBarLayout.LayoutParams layoutparams = (AppBarLayout.LayoutParams) view.getLayoutParams();
            ;
            switch (gravity.toLowerCase()) {
                case ViewPropertiesConstant.Toolbar_Enteralways:
                    layoutparams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL
                            | AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS);
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.Toolbar_Enteralwayscollapsed:
                    layoutparams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_ENTER_ALWAYS_COLLAPSED);
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.Toolbar_Exituntilcollapsed:
                    layoutparams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED);
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.Toolbar_Scroll:
                    layoutparams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL);
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.Toolbar_Snap:
                    layoutparams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP);
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.Toolbar_Snapmargins:
                    layoutparams.setScrollFlags(AppBarLayout.LayoutParams.SCROLL_FLAG_SNAP_MARGINS);
                    view.setLayoutParams(layoutparams);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomToolbar.class, "setScrollFlags");
        }
    }

    private int actionbarHeight(Context context) {
        int actionBarHeight = 0;
        try {
            TypedValue tv = new TypedValue();
            if (context.getTheme().resolveAttribute(android.R.attr.actionBarSize, tv, true)) {
                actionBarHeight = TypedValue.complexToDimensionPixelSize(tv.data, getResources().getDisplayMetrics());
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomToolbar.class, "actionbarHeight");
        }
        return actionBarHeight;
    }
}
