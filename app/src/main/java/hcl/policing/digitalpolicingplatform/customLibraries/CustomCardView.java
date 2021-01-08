package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomCardView extends CardView {

    private CustomCommonProperties customCommonProperties;

    public CustomCardView(@NonNull Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomCardView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCardView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void SetCardView(@NonNull Context context, PropertiesBean cardViewDTO) {
        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (cardViewDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, cardViewDTO.getWidth());
            }

            //Set Height
            if (cardViewDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, cardViewDTO.getHeight());
            }

            //Set weight
            if (cardViewDTO.getWeight() != 0) {
                customCommonProperties.setWeight(context, this, cardViewDTO.getWeight());
            }

            //To set Layout Gravity
            if (cardViewDTO.getLayoutGravity() != null && !cardViewDTO.getLayoutGravity().equalsIgnoreCase("")) {
                customCommonProperties.setLayoutGravity(context, this, cardViewDTO.getLayoutGravity());
            }

            //Set Margin
            if (cardViewDTO.getMarginLeft() != 0 || cardViewDTO.getMarginRight() != 0 || cardViewDTO.getMarginTop() != 0 || cardViewDTO.getMarginBottom() != 0) {

                customCommonProperties.setMargins(context, this, cardViewDTO.getMarginLeft(), cardViewDTO.getMarginTop(), cardViewDTO.getMarginRight(), cardViewDTO.getMarginBottom());
            }

            //To set ID
            if (cardViewDTO.getId() != 0) {
                customCommonProperties.setID(context, this, cardViewDTO.getId());
            }

            // To set PADDING
            if (cardViewDTO.getPaddingLeft() != 0 || cardViewDTO.getPaddingRight() != 0 || cardViewDTO.getPaddingTop() != 0 || cardViewDTO.getPaddingBottom() != 0) {
                setPadding(context, this, cardViewDTO.getPaddingLeft(), cardViewDTO.getPaddingTop(), cardViewDTO.getPaddingRight(), cardViewDTO.getPaddingBottom());
            } else {
                setPadding(context, this, 10, 10, 10, 10);
            }

            //To set Visibility
            if (cardViewDTO.getVisibility() != null && !cardViewDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, cardViewDTO.getVisibility().toLowerCase());
            }

            //Set background color
            if (cardViewDTO.getBackGroundColor() != null && !cardViewDTO.getBackGroundColor().equalsIgnoreCase("")) {
                setBackgroundColor(context, this, cardViewDTO.getBackGroundColor());
            }

            //Set card elevation
            if (cardViewDTO.getElevation() > 0) {
                setElevation(context, this, cardViewDTO.getElevation());
            }

            //Set card radius
            if (cardViewDTO.getRadius() > 0) {
                setRadius(context, this, cardViewDTO.getRadius());
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCardView.class, "");
        }
    }

    public void setElevation(Context context, CustomCardView view, int elevation) {
        try {
            view.setCardElevation((float) elevation);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCardView.class, "setElevation");
        }
    }

    public void setPadding(Context context, CustomCardView view, int left, int top, int right, int bottom) {
        try {
            view.setContentPadding(left, top, right, bottom);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCommonProperties.class, "setBackgroundColor");
        }
    }

    public void setRadius(Context context, CustomCardView view, int radius) {
        try {
            view.setRadius((float) radius);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCardView.class, "setRadius");
        }
    }

    public void setBackgroundColor(Context context, CustomCardView view, String color) {
        try {
            view.setCardBackgroundColor(Color.parseColor(color));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCommonProperties.class, "setBackgroundColor");
        }
    }
}
