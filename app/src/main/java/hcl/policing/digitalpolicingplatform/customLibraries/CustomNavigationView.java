package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.google.android.material.navigation.NavigationView;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomNavigationView extends NavigationView {

    private CustomCommonProperties customCommonProperties;

    public CustomNavigationView(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomNavigationView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomNavigationView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void SetNavigationView(Context context, PropertiesBean navigationViewDTO) {
        try {
            DrawerLayout.LayoutParams layoutparams;
            layoutparams = new DrawerLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (navigationViewDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, navigationViewDTO.getWidth());
            }

            //Set Height
            if (navigationViewDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, navigationViewDTO.getHeight());
            }

            //To set ID
            if (navigationViewDTO.getId() != 0) {
                customCommonProperties.setID(context, this, navigationViewDTO.getId());
            }

            //To set Layout Gravity
            if (navigationViewDTO.getLayoutGravity() != null && !navigationViewDTO.getLayoutGravity().equalsIgnoreCase("")) {
                setLayoutGravity(context, this, navigationViewDTO.getLayoutGravity());
            }

            //Set fit system window
            if (navigationViewDTO.getIsFitSystemWindow()) {
                customCommonProperties.setFitsSystemWindow(context, this, true);
            }

            //To set Visibility
            if (navigationViewDTO.getVisibility() != null && !navigationViewDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, navigationViewDTO.getVisibility());
            }

            //set background color
            if (navigationViewDTO.getBackGroundColor() != null && !navigationViewDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, navigationViewDTO.getBackGroundColor());
            }

            //Set drawable background
            if (navigationViewDTO.getBackGround() != null && !navigationViewDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, navigationViewDTO.getBackGround());
            }

            //Set text color
            if (navigationViewDTO.getTextColorSelected() != null && !navigationViewDTO.getTextColorSelected().equalsIgnoreCase("")) {
                setTextColor(context, this, navigationViewDTO.getTextColorSelected(), navigationViewDTO.getTextColorUnselected());
            }

            //Set Icon color
            if (navigationViewDTO.getIconColorSelected() != null && !navigationViewDTO.getIconColorSelected().equalsIgnoreCase("")) {
                setIconColor(context, this, navigationViewDTO.getIconColorSelected(), navigationViewDTO.getIconColorUnselected());
            }

            //set selected item background
            //setItemSelectedBackground(context, this, "");

            //Set card elevation
            if (navigationViewDTO.getElevation() > 0) {
                setElevation(context, this, navigationViewDTO.getElevation());
            }

            /*if (navigationViewDTO.getMenuList() != null && navigationViewDTO.getMenuList().size() > 0) {
                setMenu(context, this, navigationViewDTO.getMenuList());
            }*/
            //inflateMenu();
            //header layout
            //item text appearance
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomNavigationView.class, "");
        }
    }

    public void setLayoutGravity(Context context, View view, String gravity) {
        try {
            DrawerLayout.LayoutParams layoutparams = new DrawerLayout.LayoutParams(view.getLayoutParams().width, view.getLayoutParams().height);
            switch (gravity.toLowerCase()) {
                case ViewPropertiesConstant.Center:
                    layoutparams.gravity = Gravity.CENTER;
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.CenterVertical:
                    layoutparams.gravity = Gravity.CENTER_VERTICAL;
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.CenterHorizontal:
                    layoutparams.gravity = Gravity.CENTER_HORIZONTAL;
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.Start:
                    layoutparams.gravity = Gravity.START;
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.End:
                    layoutparams.gravity = Gravity.END;
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.Top:
                    layoutparams.gravity = Gravity.TOP;
                    view.setLayoutParams(layoutparams);
                    break;

                case ViewPropertiesConstant.Bottom:
                    layoutparams.gravity = Gravity.BOTTOM;
                    view.setLayoutParams(layoutparams);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCommonProperties.class, "setLayoutGravity");
        }
    }

    public void setTextColor(Context context, CustomNavigationView view, String selectedColor, String unSelectedColor) {
        try {
            int navSelectedColor = Color.parseColor(selectedColor);
            int navUnSelectedColor = Color.parseColor(unSelectedColor);

            int[][] state = new int[][]{
                    new int[]{-android.R.attr.state_enabled}, // disabled
                    new int[]{android.R.attr.state_enabled}, // enabled
                    new int[]{-android.R.attr.state_checked}, // unchecked
                    new int[]{android.R.attr.state_checked}, // checked
                    new int[]{android.R.attr.state_pressed}  // pressed

            };

            int[] color = new int[]{
                    navUnSelectedColor,
                    navUnSelectedColor,
                    navUnSelectedColor,
                    navSelectedColor,
                    navSelectedColor
            };

            ColorStateList colorStateList = new ColorStateList(state, color);
            view.setItemTextColor(colorStateList);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomNavigationView.class, "setTextColor");
        }
    }

    public void setIconColor(Context context, CustomNavigationView view, String selectedColor, String unSelectedColor) {
        try {
            int navSelectedColor = Color.parseColor(selectedColor);
            int navUnSelectedColor = Color.parseColor(unSelectedColor);

            int[][] state = new int[][]{
                    new int[]{-android.R.attr.state_enabled}, // disabled
                    new int[]{android.R.attr.state_enabled}, // enabled
                    new int[]{-android.R.attr.state_checked}, // unchecked
                    new int[]{android.R.attr.state_pressed}  // pressed

            };

            int[] color = new int[]{
                    navUnSelectedColor,
                    navSelectedColor,
                    navUnSelectedColor,
                    navUnSelectedColor
            };

            ColorStateList colorStateList = new ColorStateList(state, color);
            view.setItemIconTintList(colorStateList);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomNavigationView.class, "setIconColor");
        }
    }

    public void setItemSelectedBackground(Context context, CustomNavigationView view, String drawable) {
        try {
            view.setItemBackground(ContextCompat.getDrawable(context, R.drawable.cursor_drawable));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomNavigationView.class, "setItemSelectedBackground");
        }
    }

    public void setElevation(Context context, CustomNavigationView view, int elevation) {
        try {
            view.setElevation((float) elevation);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCardView.class, "setElevation");
        }
    }

    /*public void setMenu(Context context, CustomNavigationView view, List<MenuDTO> menuList) {
        try {
            final Menu menu = view.getMenu();
            menu.clear();
            for (int i = 0; i < menuList.size(); i++) {
                menu.add(0, menuList.get(i).getId(), 0, menuList.get(i).getItem());
            }
            view.invalidate();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCardView.class, "setMenu");
        }
    }*/
}