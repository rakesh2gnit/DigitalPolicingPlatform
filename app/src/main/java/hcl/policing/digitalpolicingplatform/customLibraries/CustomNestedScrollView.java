package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;

import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomNestedScrollView extends NestedScrollView {

    private CustomCommonProperties customCommonProperties;

    public CustomNestedScrollView(Context context) {
        super(context);
        //customCommonProperties = new CustomCommonProperties();
    }

    public CustomNestedScrollView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomNestedScrollView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        final int action = ev.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                //Log.i("CustomScrollView", "onInterceptTouchEvent: DOWN super false" );
                super.onTouchEvent(ev);
                break;

            case MotionEvent.ACTION_MOVE:
                return false; // redirect MotionEvents to ourself

            case MotionEvent.ACTION_CANCEL:
                // Log.i("CustomScrollView", "onInterceptTouchEvent: CANCEL super false" );
                super.onTouchEvent(ev);
                break;

            case MotionEvent.ACTION_UP:
                //Log.i("CustomScrollView", "onInterceptTouchEvent: UP super false" );
                return false;

            default:
                //Log.i("CustomScrollView", "onInterceptTouchEvent: " + action );
                break;
        }

        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        super.onTouchEvent(ev);
        //Log.i("CustomScrollView", "onTouchEvent. action: " + ev.getAction() );
        return true;
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
            if (scrollViewDTO.getOrientation() != null && !scrollViewDTO.getOrientation().equalsIgnoreCase("")) {
                setOrientation(context, this, scrollViewDTO.getOrientation());
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomNestedScrollView.class, "");
        }
    }

    public void setOrientation(Context context, CustomNestedScrollView view, String orientation) {
        try {
            switch (orientation.toLowerCase()) {
                case ViewPropertiesConstant.Horizontal:
                    view.setHorizontalScrollBarEnabled(false);
                    break;

                case ViewPropertiesConstant.Vertical:
                    view.setHorizontalScrollBarEnabled(false);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomNestedScrollView.class, "setOrientation");
        }
    }
}
