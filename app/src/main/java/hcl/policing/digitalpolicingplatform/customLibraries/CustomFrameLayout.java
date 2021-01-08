package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.google.android.material.appbar.AppBarLayout;

import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomFrameLayout extends FrameLayout {

    private CustomCommonProperties customCommonProperties;

    public CustomFrameLayout(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomFrameLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomFrameLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public CustomFrameLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    public void SetFrameLayout(@NonNull Context context, PropertiesBean frameLyoutDTO) {
        try {
            FrameLayout.LayoutParams layoutparams;
            layoutparams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (frameLyoutDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, frameLyoutDTO.getWidth());
            }

            //Set Height
            if (frameLyoutDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, frameLyoutDTO.getHeight());
            }

            //Set Weight
            if (frameLyoutDTO.getWeight() != 0) {
                customCommonProperties.setWeight(context, this, frameLyoutDTO.getWeight());
            }

            //To set Layout Gravity
            if (frameLyoutDTO.getLayoutGravity() != null && !frameLyoutDTO.getLayoutGravity().equalsIgnoreCase("")) {
                customCommonProperties.setLayoutGravity(context, this, frameLyoutDTO.getLayoutGravity());
            }

            //To set ID
            if (frameLyoutDTO.getId() != 0) {
                customCommonProperties.setID(context, this, frameLyoutDTO.getId());
            }

            //to set behaviour
            if (frameLyoutDTO.getBehaviour() != null && !frameLyoutDTO.getBehaviour().equalsIgnoreCase("")) {
                setBehaviour(context, this, frameLyoutDTO.getBehaviour());
            }

            //Set Margin
            if (frameLyoutDTO.getMarginLeft() != 0 || frameLyoutDTO.getMarginRight() != 0 || frameLyoutDTO.getMarginTop() != 0 || frameLyoutDTO.getMarginBottom() != 0) {
                customCommonProperties.setMargins(context, this, frameLyoutDTO.getMarginLeft(), frameLyoutDTO.getMarginTop(), frameLyoutDTO.getMarginRight(), frameLyoutDTO.getMarginBottom());
            }

            // To set PADDING
            if (frameLyoutDTO.getPaddingLeft() != 0 || frameLyoutDTO.getPaddingRight() != 0 || frameLyoutDTO.getPaddingTop() != 0 || frameLyoutDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, frameLyoutDTO.getPaddingLeft(), frameLyoutDTO.getPaddingTop(), frameLyoutDTO.getPaddingRight(), frameLyoutDTO.getPaddingBottom());
            }

            //To set Visibility
            if (frameLyoutDTO.getVisibility() != null && !frameLyoutDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, frameLyoutDTO.getVisibility());
            }

            //set background color
            if (frameLyoutDTO.getBackGroundColor() != null && !frameLyoutDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, frameLyoutDTO.getBackGroundColor());
            }

            if (frameLyoutDTO.getBackGround() != null && !frameLyoutDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, frameLyoutDTO.getBackGround());
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomFrameLayout.class, "");
        }
    }

    public void setBehaviour(Context context, CustomFrameLayout view, String behaviour) {
        try {
            if (view.getLayoutParams() != null) {
                CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)
                        view.getLayoutParams();

                params.setBehavior(new AppBarLayout.ScrollingViewBehavior());
                view.requestLayout();
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomFrameLayout.class, "setBehaviour");
        }
    }
}
