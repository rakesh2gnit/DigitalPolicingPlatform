package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.adapters.layoutHelper.CustomSpinnerAdapter;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.DPtoPixelUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomAutoCompleteTextView extends AppCompatAutoCompleteTextView {

    private Context context;
    private int minLength = 0;
    private Drawable dRight;
    private Rect rBounds;
    private CustomCommonProperties customCommonProperties;

    public CustomAutoCompleteTextView(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomAutoCompleteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void SetAutoCompleteTextView(Context context, PropertiesBean textViewDTO) {
        this.context = context;

        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (textViewDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, textViewDTO.getWidth());
            }

            //Set Height
            if (textViewDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, textViewDTO.getHeight());
            }

            //Set Weight
            if (textViewDTO.getWeight() != 0) {
                customCommonProperties.setWeight(context, this, textViewDTO.getWeight());
            }

            //To set Layout Gravity
            if (textViewDTO.getLayoutGravity() != null && !textViewDTO.getLayoutGravity().equalsIgnoreCase("")) {
                customCommonProperties.setLayoutGravity(context, this, textViewDTO.getLayoutGravity());
            }

            //Set Margin
            if (textViewDTO.getMarginLeft() != 0 || textViewDTO.getMarginRight() != 0 || textViewDTO.getMarginTop() != 0 || textViewDTO.getMarginBottom() != 0) {

                customCommonProperties.setMargins(context, this, textViewDTO.getMarginLeft(), textViewDTO.getMarginTop(), textViewDTO.getMarginRight(), textViewDTO.getMarginBottom());
            }

            //To set ID
            if (textViewDTO.getId() != 0) {
                customCommonProperties.setID(context, this, textViewDTO.getId());
            }

            // set min height
            if (textViewDTO.getMinHeight() != 0) {
                setMinHeight(context, this, textViewDTO.getMinHeight());
            }

            // set min width
            if (textViewDTO.getMinWidth() != 0) {
                setMinWidth(context, this, textViewDTO.getMinWidth());
            }

            // set max height
            if (textViewDTO.getMaxHeight() != 0) {
                setMaxHeight(context, this, textViewDTO.getMaxHeight());
            }

            // set max width
            if (textViewDTO.getMaxWidth() != 0) {
                setMaxWidth(context, this, textViewDTO.getMaxWidth());
            }

            // To srt PADDING
            if (textViewDTO.getPaddingLeft() != 0 || textViewDTO.getPaddingRight() != 0 || textViewDTO.getPaddingTop() != 0 || textViewDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, textViewDTO.getPaddingLeft(), textViewDTO.getPaddingTop(), textViewDTO.getPaddingRight(), textViewDTO.getPaddingBottom());
            }

            //Set background color
            if (textViewDTO.getBackGroundColor() != null && !textViewDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, textViewDTO.getBackGroundColor());
            }

            //Set drawable background
            if (textViewDTO.getBackGround() != null && !textViewDTO.getBackGround().equalsIgnoreCase("")) {
                //customCommonProperties.setBackgroundDrawable(context, this, textViewDTO.getBackGround());
            }
            this.setBackground(null);
            //this.setCursonColor(context, this, "black");

            //To Set Text
            if (textViewDTO.getText() != null && !textViewDTO.getText().equalsIgnoreCase("")) {
                setText(context, this, textViewDTO.getText());
            }

            //TO set Text Size
            if (textViewDTO.getTextSize() != 0) {
                setTextSize(context, this, textViewDTO.getTextSize());
            }/* else {
            setTextSize(spToPx(12, context));
        }*/

            //To set text color
            if (textViewDTO.getTextColor() != null && !textViewDTO.getTextColor().equalsIgnoreCase("")) {
                setTextColor(context, this, textViewDTO.getTextColor());
            } else {
                setTextColor(ContextCompat.getColor(context, R.color.text_color_default));
            }

            // set characters AllCaps
            if (textViewDTO.getIsAllCaps()) {
                setAllCaps(context, this, true);
            } else {
                setAllCaps(context, this, false);
            }

            //To set text style
            if (textViewDTO.getTextStyle() != null && !textViewDTO.getTextStyle().equalsIgnoreCase("")) {
                setTextStyle(context, this, textViewDTO.getTextStyle());
            }

            //To set Gravity
            if (textViewDTO.getGravity() != null && !textViewDTO.getGravity().equalsIgnoreCase("")) {
                setGravity(context, this, textViewDTO.getGravity());
            } else {
                setGravity(context, this, ViewPropertiesConstant.CenterVertical);
            }

            //To set Visibility
            if (textViewDTO.getVisibility() != null && !textViewDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, textViewDTO.getVisibility());
            }

            //SET drawable left top right bottom
            setDrawablesLRTB(context, this, 0, 0, R.drawable.ic_arrow_down, 0);

            //To set imeOptions
            if (textViewDTO.getKeyboardAction() != null && !textViewDTO.getKeyboardAction().equalsIgnoreCase("")) {
                setImeOptions(context, this, textViewDTO.getKeyboardAction());
            }

            this.setSingleLine();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextView.class, "");
        }
    }

    @Override
    public void setCompoundDrawablesWithIntrinsicBounds(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        if (right != null) {
            dRight = right;
        }
        super.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if (event.getAction() == MotionEvent.ACTION_UP && dRight != null) {
            rBounds = dRight.getBounds();
            final int x = (int) event.getX();
            final int y = (int) event.getY();
            //System.out.println("x:/y: "+x+"/"+y);
            //System.out.println("bounds: "+bounds.left+"/"+bounds.right+"/"+bounds.top+"/"+bounds.bottom);
            //check to make sure the touch event was within the bounds of the drawable
            if (x >= (this.getRight() - rBounds.width()) && x <= (this.getRight() - this.getPaddingRight())
                    && y >= this.getPaddingTop() && y <= (this.getHeight() - this.getPaddingBottom())) {
                //System.out.println("touch");
                performClick();
                //event.setAction(MotionEvent.ACTION_CANCEL);//use this to prevent the keyboard from coming up
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        this.setText("");
        return super.performClick();
    }

    public void setMinWidth(Context context, CustomAutoCompleteTextView view, int width) {
        try {
            view.setMinWidth(DPtoPixelUtil.convertDpToPixel(width, context));
            view.setMinimumWidth(DPtoPixelUtil.convertDpToPixel(width, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextView.class, "setMinWidth");
        }
    }

    public void setMinHeight(Context context, CustomAutoCompleteTextView view, int height) {
        try {
            view.setMinHeight(DPtoPixelUtil.convertDpToPixel(height, context));
            view.setMinimumHeight(DPtoPixelUtil.convertDpToPixel(height, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextView.class, "setMinHeight");
        }
    }

    public void setMaxHeight(Context context, CustomAutoCompleteTextView view, int height) {
        try {
            view.setMaxHeight(DPtoPixelUtil.convertDpToPixel(height, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextView.class, "setMaxHeight");
        }
    }

    public void setMaxWidth(Context context, CustomAutoCompleteTextView view, int width) {
        try {
            view.setMaxWidth(DPtoPixelUtil.convertDpToPixel(width, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextView.class, "setMaxWidth");
        }
    }

    public void setText(Context context, CustomAutoCompleteTextView view, String text) {
        try {
            view.setText(text);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextView.class, "setText");
        }
    }

    public void setTextColor(Context context, CustomAutoCompleteTextView view, String color) {
        try {
            view.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextView.class, "setTextColor");
        }
    }

    public void setTextSize(Context context, CustomAutoCompleteTextView view, int size) {
        try {
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextView.class, "setTextSize");
        }
    }

    public void setAllCaps(Context context, CustomAutoCompleteTextView view, boolean flag) {
        try {
            view.setAllCaps(flag);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextView.class, "setAllCaps");
        }
    }

    public void setTextStyle(Context context, CustomAutoCompleteTextView view, String type) {
        try {
            switch (type.toLowerCase()) {
                case ViewPropertiesConstant.Bold:
                    view.setTypeface(null, Typeface.BOLD);
                    break;

                case ViewPropertiesConstant.Italic:
                    view.setTypeface(null, Typeface.ITALIC);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextView.class, "setTextStyle");
        }
    }

    public void setCursonColor(Context context, CustomAutoCompleteTextView view, String type) {
        try {
            switch (type.toLowerCase()) {
                case ViewPropertiesConstant.Black:
                    view.setTextCursorDrawable(null);
                    break;

                case ViewPropertiesConstant.White:
                    //view.setTypeface(null, Typeface.ITALIC);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextView.class, "setTextStyle");
        }
    }

    public void setGravity(Context context, CustomAutoCompleteTextView view, String gravity) {
        try {
            switch (gravity.toLowerCase()) {
                case ViewPropertiesConstant.Center:
                    view.setGravity(Gravity.CENTER);
                    break;

                case ViewPropertiesConstant.CenterVertical:
                    view.setGravity(Gravity.CENTER_VERTICAL);
                    break;

                case ViewPropertiesConstant.CenterHorizontal:
                    view.setGravity(Gravity.CENTER_HORIZONTAL);
                    break;

                case ViewPropertiesConstant.ClipVertical:
                    view.setGravity(Gravity.CLIP_VERTICAL);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextView.class, "setGravity");
        }
    }

    public void setDrawablesLRTB(Context context, CustomAutoCompleteTextView view, int left, int top, int right, int bottom) {
        try {
            view.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextView.class, "setDrawablesLRTB");
        }
    }

    public void setImeOptions(Context context, CustomAutoCompleteTextView view, String action) {
        try {
            switch (action.toLowerCase()) {
                case ViewPropertiesConstant.Next:
                    view.setImeOptions(EditorInfo.IME_ACTION_NEXT);
                    break;

                case ViewPropertiesConstant.Done:
                    view.setImeOptions(EditorInfo.IME_ACTION_DONE);
                    break;

                case ViewPropertiesConstant.Search:
                    view.setImeOptions(EditorInfo.IME_ACTION_SEARCH);
                    break;

                case ViewPropertiesConstant.Send:
                    view.setImeOptions(EditorInfo.IME_ACTION_SEND);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputEditText.class, "setImeOptions");
        }
    }

    public void setList(Context context, CustomAutoCompleteTextView customAutoCompleteTextView, ArrayList<String> list, String selectedValue) {
        CustomSpinnerAdapter adapter = new CustomSpinnerAdapter(context, R.layout.single_item_list, list);
        customAutoCompleteTextView.setThreshold(0);
        customAutoCompleteTextView.setAdapter(adapter);
        customAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                customAutoCompleteTextView.setText(context, customAutoCompleteTextView, "" + adapterView.getItemAtPosition(position));
            }
        });
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
        if (focused && getFilter() != null) {
            performFiltering(getText(), 0);
        }
    }
}