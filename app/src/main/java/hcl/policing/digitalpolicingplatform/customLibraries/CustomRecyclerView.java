package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.util.AttributeSet;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import hcl.policing.digitalpolicingplatform.adapters.layoutHelper.CustomTabSubSectionAdapter;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomRecyclerView extends RecyclerView {

    private CustomCommonProperties customCommonProperties;

    public CustomRecyclerView(@NonNull Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    public void SetRecyclerView(@NonNull Context context, PropertiesBean recyclerViewDTO) {
        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (recyclerViewDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, recyclerViewDTO.getWidth());
            }

            //Set Height
            if (recyclerViewDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, recyclerViewDTO.getHeight());
            }

            //Set Weight
            if (recyclerViewDTO.getWeight() != 0) {
                customCommonProperties.setWeight(context, this, recyclerViewDTO.getWeight());
            }

            //To set Layout Gravity
            if (recyclerViewDTO.getLayoutGravity() != null && !recyclerViewDTO.getLayoutGravity().equalsIgnoreCase("")) {
                customCommonProperties.setLayoutGravity(context, this, recyclerViewDTO.getLayoutGravity());
            }

            //Set Margin
            if (recyclerViewDTO.getMarginLeft() != 0 || recyclerViewDTO.getMarginRight() != 0 || recyclerViewDTO.getMarginTop() != 0 || recyclerViewDTO.getMarginBottom() != 0) {
                customCommonProperties.setMargins(context, this, recyclerViewDTO.getMarginLeft(), recyclerViewDTO.getMarginTop(), recyclerViewDTO.getMarginRight(), recyclerViewDTO.getMarginBottom());
            }

            //To set ID
            if (recyclerViewDTO.getId() != 0) {
                customCommonProperties.setID(context, this, recyclerViewDTO.getId());
            }

            // To srt PADDING
            if (recyclerViewDTO.getPaddingLeft() != 0 || recyclerViewDTO.getPaddingRight() != 0 || recyclerViewDTO.getPaddingTop() != 0 || recyclerViewDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, recyclerViewDTO.getPaddingLeft(), recyclerViewDTO.getPaddingTop(), recyclerViewDTO.getPaddingRight(), recyclerViewDTO.getPaddingBottom());
            }

            //Set background color
            if (recyclerViewDTO.getBackGroundColor() != null && !recyclerViewDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, recyclerViewDTO.getBackGroundColor());
            }

            //Set drawable background
            if (recyclerViewDTO.getBackGround() != null && !recyclerViewDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, recyclerViewDTO.getBackGround());
            }

            //To set Visibility
            if (recyclerViewDTO.getVisibility() != null && !recyclerViewDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, recyclerViewDTO.getVisibility());
            }

            setHasFixedSize(true);

            if (recyclerViewDTO.getOrientation() != null && !recyclerViewDTO.getOrientation().equalsIgnoreCase("")) {
                setOrientation(context, this, recyclerViewDTO.getOrientation());
            } else {
                setOrientation(context, this, "vertical");
            }

            //to scroll to position
            if (recyclerViewDTO.getScrollToPosition() != 0) {
                scrollToPosition(context, this, recyclerViewDTO.getScrollToPosition());
            }

            //set nested scrolling
            if (recyclerViewDTO.getIsNestedScroll()) {
                setNestedScrollingEnabled(context, this, true);
            } else {
                setNestedScrollingEnabled(context, this, false);
            }

            //set adapter
            /*if (recyclerViewDTO.getCustomListAdapter() != null) {
                setAdapter(context, this, recyclerViewDTO.getCustomListAdapter());
            }*/
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRecyclerView.class, "");
        }
    }

    public void setOrientation(Context context, CustomRecyclerView view, String orientation) {
        try {
            switch (orientation.toLowerCase()) {
                case ViewPropertiesConstant.Horizontal:
                    LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, HORIZONTAL, false);
                    view.setLayoutManager(mLayoutManager);
                    break;

                case ViewPropertiesConstant.Vertical:
                    LinearLayoutManager mLayoutManager1 = new LinearLayoutManager(context);
                    view.setLayoutManager(mLayoutManager1);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRecyclerView.class, "setOrientation");
        }
    }

    public void scrollToPosition(Context context, CustomRecyclerView view, int position) {
        try {
            view.scrollToPosition(position);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRecyclerView.class, "scrollToPosition");
        }
    }

    public void setNestedScrollingEnabled(Context context, CustomRecyclerView view, boolean flag) {
        try {
            view.setNestedScrollingEnabled(flag);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRecyclerView.class, "setNestedScrollingEnabled");
        }
    }

    public void setAdapter(Context context, CustomRecyclerView view, CustomTabSubSectionAdapter adapter) {
        try {
            view.setAdapter(adapter);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomRecyclerView.class, "setAdapter");
        }
    }
}
