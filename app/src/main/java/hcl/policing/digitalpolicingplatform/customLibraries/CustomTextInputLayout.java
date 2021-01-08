package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.graphics.Color;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.appcompat.view.ContextThemeWrapper;

import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Locale;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.DPtoPixelUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class CustomTextInputLayout extends TextInputLayout {

    private Context context;
    private int minLength = 0;
    private CustomCommonProperties customCommonProperties;

    private static final int MAX_LENGTH = 8;
    private static final int MIN_LENGTH = 2;
    private String updatedText;
    private boolean editing;

    private String current = "";
    private String ddmmyyyy = "DDMMYYYY";
    private Calendar cal = Calendar.getInstance();

    public CustomTextInputLayout(Context context) {
        //super(new ContextThemeWrapper(context, R.style.TextInputLayoutAppearanceWhite));
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextInputLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void SetTextInputLayout(Context context, PropertiesBean textInputLayoutDTO, CustomTextInputEditText customEditText) {
        if (textInputLayoutDTO.getHintAppearance() != null && !textInputLayoutDTO.getHintAppearance().equalsIgnoreCase("")) {
            switch (textInputLayoutDTO.getHintAppearance().toLowerCase()) {
                case ViewPropertiesConstant.White:
                    context = new ContextThemeWrapper(context, R.style.TextInputLayoutAppearanceWhite);
                    break;

                case ViewPropertiesConstant.Black:
                    context = new ContextThemeWrapper(context, R.style.TextInputLayoutAppearanceBlack);
                    break;
            }
        }
        //context = new ContextThemeWrapper(context, R.style.TextInputLayoutAppearance);

        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (textInputLayoutDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, textInputLayoutDTO.getWidth());
            }

            //Set Height
            if (textInputLayoutDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, textInputLayoutDTO.getHeight());
            }

            //set weight
            if (textInputLayoutDTO.getWeight() != 0) {
                customCommonProperties.setWeight(context, this, textInputLayoutDTO.getWeight());
            }

            //set margin
            if (textInputLayoutDTO.getMarginLeft() != 0 || textInputLayoutDTO.getMarginTop() != 0 || textInputLayoutDTO.getMarginRight() != 0 || textInputLayoutDTO.getMarginBottom() != 0) {
                customCommonProperties.setMargins(context, this, textInputLayoutDTO.getMarginLeft(), textInputLayoutDTO.getMarginTop(), textInputLayoutDTO.getMarginRight(), textInputLayoutDTO.getMarginBottom());
            }

            //To set Layout Gravity
            if (textInputLayoutDTO.getLayoutGravity() != null && !textInputLayoutDTO.getLayoutGravity().equalsIgnoreCase("")) {
                customCommonProperties.setLayoutGravity(context, this, textInputLayoutDTO.getLayoutGravity());
            }

            //To set Visibility
            if (textInputLayoutDTO.getVisibility() != null && !textInputLayoutDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, textInputLayoutDTO.getVisibility());
            }

            //To set ID
            if (textInputLayoutDTO.getId() != 0) {
                customCommonProperties.setID(context, this, textInputLayoutDTO.getId());
            }

            //To Set Hint
            if (textInputLayoutDTO.getHint() != null && !textInputLayoutDTO.getHint().equalsIgnoreCase("")) {
                setHint(context, this, textInputLayoutDTO.getHint());
            }

            //Set Hint Appreance
            if (textInputLayoutDTO.getHintAppearance() != null && !textInputLayoutDTO.getHintAppearance().equalsIgnoreCase("")) {
                setHintAppearance(context, this, textInputLayoutDTO.getHintAppearance());
            } else {
                setHintTextAppearance(R.style.HintTextDefault);
            }

            setErrorTextAppearance(R.style.ErrorText);

            if (textInputLayoutDTO.getDesignType() != null && !textInputLayoutDTO.getDesignType().equalsIgnoreCase("")) {
                if (textInputLayoutDTO.getDesignType().toLowerCase().equalsIgnoreCase("box")) {
                    setBoxBackgroundMode(TextInputLayout.BOX_BACKGROUND_OUTLINE);
                    if (textInputLayoutDTO.getBoxTopLeftRadius() != 0 || textInputLayoutDTO.getBoxTopRightRadius() != 0 || textInputLayoutDTO.getBoxBottomLeftRadius() != 0 || textInputLayoutDTO.getBoxBottomRightRadius() != 0) {
                        setBoxCornerRadii(DPtoPixelUtil.convertDpToPixel(textInputLayoutDTO.getBoxTopLeftRadius(), context), DPtoPixelUtil.convertDpToPixel(textInputLayoutDTO.getBoxTopRightRadius(), context), DPtoPixelUtil.convertDpToPixel(textInputLayoutDTO.getBoxBottomLeftRadius(), context), DPtoPixelUtil.convertDpToPixel(textInputLayoutDTO.getBoxBottomRightRadius(), context));
                    }
                    if (textInputLayoutDTO.getBoxColor() != null && textInputLayoutDTO.getBoxColor().equalsIgnoreCase("")) {
                        setboxColor(context, this, textInputLayoutDTO.getBoxColor());
                    }
                }
            }

            //customEditText.setHighlightColor(ContextCompat.getColor(context, R.color.white));

            // to set is this field mendatary
            if (textInputLayoutDTO.getMendatry()) {
                //setError(context, this, customEditText);
                if (textInputLayoutDTO.getMinLength() > 0) {
                    minLength = textInputLayoutDTO.getMinLength();
                }
                if (textInputLayoutDTO.getInputType() != null && !textInputLayoutDTO.getInputType().equalsIgnoreCase("")) {
                    initMendatryField(textInputLayoutDTO.getInputType().toLowerCase(), customEditText, textInputLayoutDTO);
                } else {
                    initMendatryField("", customEditText, textInputLayoutDTO);
                }
            } else {
                if (textInputLayoutDTO.getInputType() != null && !textInputLayoutDTO.getInputType().equalsIgnoreCase("")) {
                    if (textInputLayoutDTO.getMinLength() > 0) {
                        minLength = textInputLayoutDTO.getMinLength();
                    }
                    initInputTypeCheck(textInputLayoutDTO.getInputType().toLowerCase(), customEditText, textInputLayoutDTO);
                } else {
                    if (textInputLayoutDTO.getMinLength() > 0) {
                        minLength = textInputLayoutDTO.getMinLength();
                    }
                    initInputTypeCheck("", customEditText, textInputLayoutDTO);
                }
            }

            addView(customEditText);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputLayout.class, "");
        }
    }

    private void initMendatryField(final String type, CustomTextInputEditText customTextInputEditText, PropertiesBean textInputLayoutDTO) {

        //if text changes, take care of the button
        customTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (s.toString().length() == 0) {
                    //SET drawable left top right bottom
                    if(textInputLayoutDTO.getInputType().equalsIgnoreCase(ViewPropertiesConstant.Text_Multiline)) {
                        if(CustomTextInputEditText.isListening) {
                            customTextInputEditText.setDrawablesLRTB(context, customTextInputEditText, 0, 0, R.drawable.ic_mic_blue, 0);
                        } else {
                            customTextInputEditText.setDrawablesLRTB(context, customTextInputEditText, 0, 0, R.drawable.ic_mic_gray, 0);
                        }
                    } else {
                        customTextInputEditText.setDrawablesLRTB(context, customTextInputEditText, 0, 0, 0, 0);
                    }
                    CustomTextInputLayout.this.setErrorEnabled(true);
                    CustomTextInputLayout.this.setError(getResources().getString(R.string.field_cannot_blank));
                } else if (s.toString().length() > 0) {
                    //SET drawable left top right bottom
                    if(textInputLayoutDTO.isEnabled()) {
                        if(textInputLayoutDTO.getInputType().equalsIgnoreCase(ViewPropertiesConstant.Text_Multiline)) {
                            if(CustomTextInputEditText.isListening) {
                                customTextInputEditText.setDrawablesLRTB(context, customTextInputEditText, 0, 0, R.drawable.ic_mic_blue, 0);
                            } else {
                                customTextInputEditText.setDrawablesLRTB(context, customTextInputEditText, 0, 0, R.drawable.ic_mic_gray, 0);
                            }
                        }/* else {
                            customTextInputEditText.setDrawablesLRTB(context, customTextInputEditText, 0, 0, R.drawable.ic_close, 0);
                        }*/
                    }

                    if (minLength > 0 && s.toString().length() < minLength) {
                        CustomTextInputLayout.this.setErrorEnabled(true);
                        CustomTextInputLayout.this.setError("Minimum " + minLength + " characters required.");
                    } else if (type.equalsIgnoreCase(ViewPropertiesConstant.Pattern_Email)) {
                        if (!Utilities.getInstance(context).checkEmail(s.toString())) {
                            CustomTextInputLayout.this.setErrorEnabled(true);
                            CustomTextInputLayout.this.setError(getResources().getString(R.string.enter_valid_email));
                        } else {
                            CustomTextInputLayout.this.setErrorEnabled(false);
                        }
                    } else if (type.equalsIgnoreCase(ViewPropertiesConstant.Pattern_Postcode)) {
                        if (!Utilities.getInstance(context).checkPostcode(s.toString())) {
                            CustomTextInputLayout.this.setErrorEnabled(true);
                            CustomTextInputLayout.this.setError(getResources().getString(R.string.enter_valid_postcode));
                        } else {
                            CustomTextInputLayout.this.setErrorEnabled(false);
                        }
                    } else if (type.equalsIgnoreCase(ViewPropertiesConstant.Pattern_Mobile)) {
                        if (!Utilities.getInstance(context).checkMobile(s.toString())) {
                            CustomTextInputLayout.this.setErrorEnabled(true);
                            CustomTextInputLayout.this.setError(getResources().getString(R.string.enter_valid_mobile));
                        } else {
                            CustomTextInputLayout.this.setErrorEnabled(false);
                        }
                    } else if (type.equalsIgnoreCase(ViewPropertiesConstant.Pattern_Date)) {
                        if (s.length() > 0) {
                            CustomTextInputLayout.this.setErrorEnabled(false);
                        }
                        if (!s.toString().equals(current)) {
                            String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                            String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                            int cl = clean.length();
                            int sel = cl;
                            for (int i = 2; i <= cl && i < 6; i += 2) {
                                sel++;
                            }
                            //Fix for pressing delete next to a forward slash
                            if (clean.equals(cleanC)) sel--;

                            if (clean.length() < 8) {
                                clean = clean + ddmmyyyy.substring(clean.length());
                            } else {
                                //This part makes sure that when we finish entering numbers
                                //the date is correct, fixing it otherwise
                                int day = Integer.parseInt(clean.substring(0, 2));
                                int mon = Integer.parseInt(clean.substring(2, 4));
                                int year = Integer.parseInt(clean.substring(4, 8));

                                mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                                cal.set(Calendar.MONTH, mon - 1);
                                year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                                cal.set(Calendar.YEAR, year);
                                // ^ first set year for the line below to work correctly
                                //with leap years - otherwise, date e.g. 29/02/2012
                                //would be automatically corrected to 28/02/2012

                                day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                                clean = String.format(Locale.UK, "%02d%02d%02d", day, mon, year);
                            }

                            clean = String.format("%s/%s/%s", clean.substring(0, 2),
                                    clean.substring(2, 4),
                                    clean.substring(4, 8));

                            sel = sel < 0 ? 0 : sel;
                            current = clean;
                            customTextInputEditText.setText(current);
                            customTextInputEditText.setSelection(sel < current.length() ? sel : current.length());
                        }
                    } else {
                        CustomTextInputLayout.this.setErrorEnabled(false);
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

    private void initInputTypeCheck(final String type, CustomTextInputEditText customTextInputEditText, PropertiesBean textInputLayoutDTO) {
        //if text changes, take care of the button
        customTextInputEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().length() > 0) {
                    //SET drawable left top right bottom
                    if(textInputLayoutDTO.isEnabled()) {
                        if(textInputLayoutDTO.getInputType().equalsIgnoreCase(ViewPropertiesConstant.Text_Multiline)) {
                            if(CustomTextInputEditText.isListening) {
                                customTextInputEditText.setDrawablesLRTB(context, customTextInputEditText, 0, 0, R.drawable.ic_mic_blue, 0);
                            } else {
                                customTextInputEditText.setDrawablesLRTB(context, customTextInputEditText, 0, 0, R.drawable.ic_mic_gray, 0);
                            }
                        }/* else {
                            customTextInputEditText.setDrawablesLRTB(context, customTextInputEditText, 0, 0, R.drawable.ic_close, 0);
                        }*/
                    }

                    if (minLength > 0 && s.toString().length() < minLength) {
                        CustomTextInputLayout.this.setErrorEnabled(true);
                        CustomTextInputLayout.this.setError("Minimum " + minLength + " characters required.");
                    } else if (type.equalsIgnoreCase(ViewPropertiesConstant.Pattern_Email)) {
                        if (!Utilities.getInstance(context).checkEmail(s.toString())) {
                            CustomTextInputLayout.this.setErrorEnabled(true);
                            CustomTextInputLayout.this.setError(getResources().getString(R.string.enter_valid_email));
                        } else {
                            CustomTextInputLayout.this.setErrorEnabled(false);
                        }
                    } else if (type.equalsIgnoreCase(ViewPropertiesConstant.Pattern_Postcode)) {
                        if (!Utilities.getInstance(context).checkPostcode(s.toString())) {
                            CustomTextInputLayout.this.setErrorEnabled(true);
                            CustomTextInputLayout.this.setError(getResources().getString(R.string.enter_valid_postcode));
                        } else {
                            CustomTextInputLayout.this.setErrorEnabled(false);
                        }
                    } else if (type.equalsIgnoreCase(ViewPropertiesConstant.Pattern_Mobile)) {
                        if (!Utilities.getInstance(context).checkMobile(s.toString())) {
                            CustomTextInputLayout.this.setErrorEnabled(true);
                            CustomTextInputLayout.this.setError(getResources().getString(R.string.enter_valid_mobile));
                        } else {
                            CustomTextInputLayout.this.setErrorEnabled(false);
                        }
                    } else if (type.equalsIgnoreCase(ViewPropertiesConstant.Pattern_Date)) {
                        if (s.length() > 0) {
                            CustomTextInputLayout.this.setErrorEnabled(false);
                        }
                        if (!s.toString().equals(current)) {
                            String clean = s.toString().replaceAll("[^\\d.]|\\.", "");
                            String cleanC = current.replaceAll("[^\\d.]|\\.", "");

                            int cl = clean.length();
                            int sel = cl;
                            for (int i = 2; i <= cl && i < 6; i += 2) {
                                sel++;
                            }
                            //Fix for pressing delete next to a forward slash
                            if (clean.equals(cleanC)) sel--;

                            if (clean.length() < 8) {
                                clean = clean + ddmmyyyy.substring(clean.length());
                            } else {
                                //This part makes sure that when we finish entering numbers
                                //the date is correct, fixing it otherwise
                                int day = Integer.parseInt(clean.substring(0, 2));
                                int mon = Integer.parseInt(clean.substring(2, 4));
                                int year = Integer.parseInt(clean.substring(4, 8));

                                mon = mon < 1 ? 1 : mon > 12 ? 12 : mon;
                                cal.set(Calendar.MONTH, mon - 1);
                                year = (year < 1900) ? 1900 : (year > 2100) ? 2100 : year;
                                cal.set(Calendar.YEAR, year);
                                // ^ first set year for the line below to work correctly
                                //with leap years - otherwise, date e.g. 29/02/2012
                                //would be automatically corrected to 28/02/2012

                                day = (day > cal.getActualMaximum(Calendar.DATE)) ? cal.getActualMaximum(Calendar.DATE) : day;
                                clean = String.format(Locale.UK, "%02d%02d%02d", day, mon, year);
                            }

                            clean = String.format("%s/%s/%s", clean.substring(0, 2),
                                    clean.substring(2, 4),
                                    clean.substring(4, 8));

                            sel = sel < 0 ? 0 : sel;
                            current = clean;
                            customTextInputEditText.setText(current);
                            customTextInputEditText.setSelection(sel < current.length() ? sel : current.length());
                        }
                    } else {
                        CustomTextInputLayout.this.setErrorEnabled(false);
                    }
                } else {
                    //SET drawable left top right bottom
                    if(textInputLayoutDTO.getInputType().equalsIgnoreCase(ViewPropertiesConstant.Text_Multiline)) {
                        if(CustomTextInputEditText.isListening) {
                            customTextInputEditText.setDrawablesLRTB(context, customTextInputEditText, 0, 0, R.drawable.ic_mic_blue, 0);
                        } else {
                            customTextInputEditText.setDrawablesLRTB(context, customTextInputEditText, 0, 0, R.drawable.ic_mic_gray, 0);
                        }
                    } else {
                        customTextInputEditText.setDrawablesLRTB(context, customTextInputEditText, 0, 0, 0, 0);
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

    public void setHint(Context context, CustomTextInputLayout view, String hint) {
        try {
            view.setHint(hint);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputLayout.class, "setHint");
        }
    }

    public void setGravity(Context context, CustomTextInputLayout view, String gravity) {
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
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputLayout.class, "setGravity");
        }
    }

    public void setHintAppearance(Context context, CustomTextInputLayout view, String hintColor) {
        try {
            switch (hintColor.toLowerCase()) {
                case ViewPropertiesConstant.White:
                    view.setHintTextAppearance(R.style.HintTextWhite);
                    break;

                case ViewPropertiesConstant.Black:
                    view.setHintTextAppearance(R.style.HintTextBlack);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputLayout.class, "setGravity");
        }
    }

    public void setboxColor(Context context, CustomTextInputLayout view, String boxColor) {
        try {
            setBoxStrokeColor(Color.parseColor(boxColor));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputLayout.class, "setGravity");
        }
    }

    public void setError(Context context, CustomTextInputLayout view, CustomTextInputEditText customTextInputEditText) {
        try {
            if (customTextInputEditText.getText().toString().equalsIgnoreCase("")) {
                customTextInputEditText.setDrawablesLRTB(context, customTextInputEditText, 0, 0, 0, 0);
                view.setErrorEnabled(true);
                view.setError(context.getString(R.string.field_cannot_blank));
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputLayout.class, "setGravity");
        }
    }
}

