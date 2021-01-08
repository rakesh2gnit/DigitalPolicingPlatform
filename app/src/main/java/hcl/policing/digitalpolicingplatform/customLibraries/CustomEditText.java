package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.ContextCompat;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.DPtoPixelUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class CustomEditText extends AppCompatEditText {

    private Context context;
    private int minLength = 0;
    private Drawable dRight;
    private Rect rBounds;
    private CustomCommonProperties customCommonProperties;

    public CustomEditText(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void SetEditText(Context context, PropertiesBean editTextDTO) {
        this.context = context;

        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (editTextDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, editTextDTO.getWidth());
            }

            //Set Height
            if (editTextDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, editTextDTO.getHeight());
            }

            //To set Layout Gravity
            if (editTextDTO.getLayoutGravity() != null && !editTextDTO.getLayoutGravity().equalsIgnoreCase("")) {
                customCommonProperties.setLayoutGravity(context, this, editTextDTO.getLayoutGravity());
            }

            //To set ID
            if (editTextDTO.getId() != 0) {
                customCommonProperties.setID(context, this, editTextDTO.getId());
            }

            // set min height
            if (editTextDTO.getMinHeight() != 0) {
                setMinHeight(context, this, editTextDTO.getMinHeight());
            }

            // set min width
            if (editTextDTO.getMinWidth() != 0) {
                setMinWidth(context, this, editTextDTO.getMinWidth());
            }

            // set max height
            if (editTextDTO.getMaxHeight() != 0) {
                setMaxHeight(context, this, editTextDTO.getMaxHeight());
            }

            // set max width
            if (editTextDTO.getMaxWidth() != 0) {
                setMaxWidth(context, this, editTextDTO.getMaxWidth());
            }

            // To set PADDING
            if (editTextDTO.getPaddingLeft() != 0 || editTextDTO.getPaddingRight() != 0 || editTextDTO.getPaddingTop() != 0 || editTextDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, editTextDTO.getPaddingLeft(), editTextDTO.getPaddingTop(), editTextDTO.getPaddingRight(), editTextDTO.getPaddingBottom());
            }

            //Set background color
            if (editTextDTO.getBackGroundColor() != null && !editTextDTO.getBackGroundColor().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundColor(context, this, editTextDTO.getBackGroundColor());
            }

            //Set drawable background
            if (editTextDTO.getBackGround() != null && !editTextDTO.getBackGround().equalsIgnoreCase("")) {
                customCommonProperties.setBackgroundDrawable(context, this, editTextDTO.getBackGround());
            }

            //To Set Text
            if (editTextDTO.getText() != null && !editTextDTO.getText().equalsIgnoreCase("")) {
                setText(context, this, editTextDTO.getText());
                //setSelection(editTextDTO.getText().length(), editTextDTO.getText().length());
            }

            //TO set Text Size
            if (editTextDTO.getTextSize() != 0) {
                setTextSize(context, this, editTextDTO.getTextSize());
            }

            // set characters AllCaps
            if (editTextDTO.getIsAllCaps()) {
                setAllCaps(context, this, true);
            } else {
                setAllCaps(context, this, false);
            }

            //To set text color
            if (editTextDTO.getTextColor() != null && !editTextDTO.getTextColor().equalsIgnoreCase("")) {
                setTextColor(context, this, editTextDTO.getTextColor());
            } else {
                setTextColor(ContextCompat.getColor(context, R.color.text_color_default));
            }

            //To set text style
            if (editTextDTO.getTextStyle() != null && !editTextDTO.getTextStyle().equalsIgnoreCase("")) {
                setTextStyle(context, this, editTextDTO.getTextStyle().toLowerCase());
            }

            //To Set Hint
            if (editTextDTO.getHint() != null && !editTextDTO.getHint().equalsIgnoreCase("")) {
                setHint(context, this, editTextDTO.getHint());
            }

            //To set hint color
            if (editTextDTO.getHintColor() != null && !editTextDTO.getHintColor().equalsIgnoreCase("")) {
                setHintColor(context, this, editTextDTO.getHintColor());
            }

            //setBackgroundTintMode(R.c);

            //To set Gravity
            if (editTextDTO.getGravity() != null && !editTextDTO.getGravity().equalsIgnoreCase("")) {
                setGravity(context, this, editTextDTO.getGravity());
            } else {
                setGravity(context, this, ViewPropertiesConstant.CenterVertical);
            }

            //To set Visibility of edittext
            if (editTextDTO.getVisibility() != null && !editTextDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, editTextDTO.getVisibility());
            }

            //SET max length
            if (editTextDTO.getMaxLength() != 0) {
                setMaxLength(context, this, editTextDTO.getMaxLength());
            }

            //SET drawable left top right bottom
            setDrawablesLRTB(context, this, 0, 0, R.drawable.ic_close, 0);

            if (editTextDTO.getInputType() != null && !editTextDTO.getInputType().equalsIgnoreCase("")) {
                setInputType(context, this, editTextDTO.getInputType());
            } else {
                setInputType(InputType.TYPE_CLASS_TEXT);
            }

            // to set is this field mendatary
            if (editTextDTO.getMendatry()) {
                //setError(editTextDTO.getErrorMsg());
                if (editTextDTO.getMinLength() > 0) {
                    minLength = editTextDTO.getMinLength();
                }
                if (editTextDTO.getInputType() != null && !editTextDTO.getInputType().equalsIgnoreCase("")) {
                    initMendatryField(editTextDTO.getInputType().toLowerCase());
                } else {
                    initMendatryField("");
                }
            } else {
                if (editTextDTO.getInputType() != null && !editTextDTO.getInputType().equalsIgnoreCase("")) {
                    if (editTextDTO.getMinLength() > 0) {
                        minLength = editTextDTO.getMinLength();
                    }
                    initInputTypeCheck(editTextDTO.getInputType().toLowerCase());
                } else {
                    if (editTextDTO.getMinLength() > 0) {
                        minLength = editTextDTO.getMinLength();
                    }
                    initInputTypeCheck("");
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "");
        }
    }

    private void initMendatryField(final String type) {
        //if text changes, take care of the button
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().length() == 0) {
                    CustomEditText.this.setError(getResources().getString(R.string.field_cannot_blank));
                } else if (minLength > 0 && s.toString().length() > 0 && s.toString().length() < minLength) {
                    CustomEditText.this.setError("Minimum " + minLength + " characters required.");
                } else if (type.equalsIgnoreCase(ViewPropertiesConstant.Pattern_Email)) {
                    if (!Utilities.getInstance(context).checkEmail(s.toString())) {
                        CustomEditText.this.setError(getResources().getString(R.string.enter_valid_email));
                    }
                } else if (type.equalsIgnoreCase(ViewPropertiesConstant.Pattern_Postcode)) {
                    if (!Utilities.getInstance(context).checkPostcode(s.toString())) {
                        CustomEditText.this.setError(getResources().getString(R.string.enter_valid_postcode));
                    }
                } else if (type.equalsIgnoreCase(ViewPropertiesConstant.Pattern_Mobile)) {
                    if (!Utilities.getInstance(context).checkMobile(s.toString())) {
                        CustomEditText.this.setError(getResources().getString(R.string.enter_valid_mobile));
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
    }

    private void initInputTypeCheck(final String type) {
        //if text changes, take care of the button
        this.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    if (minLength > 0 && s.toString().length() < minLength) {
                        CustomEditText.this.setError("Minimum " + minLength + " characters required.");
                    } else if (type.equalsIgnoreCase(ViewPropertiesConstant.Pattern_Email)) {
                        if (!Utilities.getInstance(context).checkEmail(s.toString())) {
                            CustomEditText.this.setError(getResources().getString(R.string.enter_valid_email));
                        }
                    } else if (type.equalsIgnoreCase(ViewPropertiesConstant.Pattern_Postcode)) {
                        if (!Utilities.getInstance(context).checkPostcode(s.toString())) {
                            CustomEditText.this.setError(getResources().getString(R.string.enter_valid_postcode));
                        }
                    } else if (type.equalsIgnoreCase(ViewPropertiesConstant.Pattern_Mobile)) {
                        if (!Utilities.getInstance(context).checkMobile(s.toString())) {
                            CustomEditText.this.setError(getResources().getString(R.string.enter_valid_mobile));
                        }
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
        });
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

    public void setMinWidth(Context context, CustomEditText view, int width) {
        try {
            view.setMinWidth(DPtoPixelUtil.convertDpToPixel(width, context));
            view.setMinimumWidth(DPtoPixelUtil.convertDpToPixel(width, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "setMinWidth");
        }
    }

    public void setMinHeight(Context context, CustomEditText view, int height) {
        try {
            view.setMinHeight(DPtoPixelUtil.convertDpToPixel(height, context));
            view.setMinimumHeight(DPtoPixelUtil.convertDpToPixel(height, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "setMinHeight");
        }
    }

    public void setMaxHeight(Context context, CustomEditText view, int height) {
        try {
            view.setMaxHeight(DPtoPixelUtil.convertDpToPixel(height, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "setMaxHeight");
        }
    }

    public void setMaxWidth(Context context, CustomEditText view, int width) {
        try {
            view.setMaxWidth(DPtoPixelUtil.convertDpToPixel(width, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "setMaxWidth");
        }
    }

    public void setText(Context context, CustomEditText view, String text) {
        try {
            view.setText(text);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "setText");
        }
    }

    public void setTextColor(Context context, CustomEditText view, String color) {
        try {
            view.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "setTextColor");
        }
    }

    public void setTextSize(Context context, CustomEditText view, int size) {
        try {
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "setTextSize");
        }
    }

    public void setAllCaps(Context context, CustomEditText view, boolean flag) {
        try {
            view.setAllCaps(flag);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "setAllCaps");
        }
    }

    public void setHint(Context context, CustomEditText view, String text) {
        try {
            view.setHint(text);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "setHint");
        }
    }

    public void setHintColor(Context context, CustomEditText view, String color) {
        try {
            view.setHintTextColor(Color.parseColor(color));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "setHintTextColor");
        }
    }

    public void setMaxLength(Context context, CustomEditText view, int length) {
        try {
            view.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "setMaxLength");
        }
    }

    public void setTextStyle(Context context, CustomEditText view, String type) {
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
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "setTextStyle");
        }
    }

    public void setGravity(Context context, CustomEditText view, String gravity) {
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
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "setGravity");
        }
    }

    public void setInputType(Context context, CustomEditText view, String type) {
        try {
            switch (type.toLowerCase()) {
                case ViewPropertiesConstant.Pattern_Email:
                    view.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                    break;

                case ViewPropertiesConstant.Pattern_Mobile:
                    view.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;

                case ViewPropertiesConstant.Pattern_Password:
                    view.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    break;

                case ViewPropertiesConstant.Pattern_Number:
                    view.setInputType(InputType.TYPE_CLASS_NUMBER);
                    break;

                default:
                    view.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "setInputType");
        }
    }

    public void setDrawablesLRTB(Context context, CustomEditText view, int left, int top, int right, int bottom) {
        try {
            view.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomEditText.class, "setDrawablesLRTB");
        }
    }
}