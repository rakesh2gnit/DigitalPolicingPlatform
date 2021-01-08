package hcl.policing.digitalpolicingplatform.customLibraries;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.speech.RecognitionListener;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Locale;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.micSearch.MicSearchActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.flow.ServerRequest;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.DPtoPixelUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.Utilities;
import hcl.policing.digitalpolicingplatform.utils.speak.SpeakToPersonUtil;
import hcl.policing.digitalpolicingplatform.utils.speak.SpeakToPhoneUtil;

public class CustomTextInputEditText extends TextInputEditText {

    private Context context;
    private int minLength = 0;
    private Drawable dRight;
    private Rect rBounds;
    private CustomCommonProperties customCommonProperties;
    private String inputType;
    private SpeechRecognizer mSpeechRecognizer;
    private Intent mSpeechRecognizerIntent;
    public static boolean isListening = false;
    private Dialog mProgressDialog;

    public CustomTextInputEditText(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomTextInputEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomTextInputEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void SetTextInputEditText(Context context, PropertiesBean editTextDTO) {
        this.context = context;
        try {
            initializeSpeech(context);
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

            setMaxLines(editTextDTO.getMaxLines());

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

            //setEnabled(false);

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

            //Set InputType
            if (editTextDTO.getInputType() != null && !editTextDTO.getInputType().equalsIgnoreCase("")) {
                setInputType(context, this, editTextDTO.getInputType());
            } else {
                setInputType(InputType.TYPE_CLASS_TEXT);
            }

            //To set imeOptions
            if (editTextDTO.getKeyboardAction() != null && !editTextDTO.getKeyboardAction().equalsIgnoreCase("")) {
                setImeOptions(context, this, editTextDTO.getKeyboardAction());
            }
            //setTextCursorDrawable(R.drawable.cursor_drawable);

            // setting field enable and disable
            setEnabled(editTextDTO.isEnabled());

            inputType = editTextDTO.getInputType();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputEditText.class, "setWidth");
        }
    }

    @Override
    public void setCompoundDrawables(@Nullable Drawable left, @Nullable Drawable top, @Nullable Drawable right, @Nullable Drawable bottom) {
        if (right != null) {
            dRight = right;
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        final int DRAWABLE_LEFT = 0;
        final int DRAWABLE_TOP = 1;
        final int DRAWABLE_RIGHT = 2;
        final int DRAWABLE_BOTTOM = 3;

        if (event.getAction() == MotionEvent.ACTION_UP && dRight != null) {

            if (event.getRawX() >= (getRight() - dRight.getBounds().width())) {
                //Set up to click the EditText icon on the right to lose focus.
                // Prevent clicking EditText icon on the right side of EditText to get focus and pop-up the soft keyboard
                //setFocusableInTouchMode(false);
                //setFocusable(false);
                performClick();
                return true;
            }/* else {
                //setFocusableInTouchMode(true);
                //setFocusable(true);
            }*/
        }
        return super.onTouchEvent(event);
    }

    @Override
    public boolean performClick() {
        if(inputType != null && inputType.equalsIgnoreCase(ViewPropertiesConstant.Text_Multiline)) {
            startListening();
        }/* else {
            this.setText("");
            dRight = null;
        }*/
        return super.performClick();
    }

    public void setMinWidth(Context context, CustomTextInputEditText view, int width) {
        try {
            view.setMinWidth(DPtoPixelUtil.convertDpToPixel(width, context));
            view.setMinimumWidth(DPtoPixelUtil.convertDpToPixel(width, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputEditText.class, "setMinWidth");
        }
    }

    public void setMinHeight(Context context, CustomTextInputEditText view, int height) {
        try {
            view.setMinHeight(DPtoPixelUtil.convertDpToPixel(height, context));
            view.setMinimumHeight(DPtoPixelUtil.convertDpToPixel(height, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputEditText.class, "setMinHeight");
        }
    }

    public void setMaxHeight(Context context, CustomTextInputEditText view, int height) {
        try {
            view.setMaxHeight(DPtoPixelUtil.convertDpToPixel(height, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputEditText.class, "setMaxHeight");
        }
    }

    public void setMaxWidth(Context context, CustomTextInputEditText view, int width) {
        try {
            view.setMaxWidth(DPtoPixelUtil.convertDpToPixel(width, context));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputEditText.class, "setMaxWidth");
        }
    }

    public void setText(Context context, CustomTextInputEditText view, String text) {
        try {
            view.setText(text);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputEditText.class, "setText");
        }
    }

    public void setTextColor(Context context, CustomTextInputEditText view, String color) {
        try {
            view.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputEditText.class, "setTextColor");
        }
    }

    public void setTextSize(Context context, CustomTextInputEditText view, int size) {
        try {
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputEditText.class, "setTextSize");
        }
    }

    public void setAllCaps(Context context, CustomTextInputEditText view, boolean flag) {
        try {
            view.setAllCaps(flag);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputEditText.class, "setAllCaps");
        }
    }

    public void setMaxLength(Context context, CustomTextInputEditText view, int length) {
        try {
            view.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputEditText.class, "setMaxLength");
        }
    }

    public void setTextStyle(Context context, CustomTextInputEditText view, String type) {
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
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputEditText.class, "setTextStyle");
        }
    }

    public void setGravity(Context context, CustomTextInputEditText view, String gravity) {
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
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputEditText.class, "setGravity");
        }
    }

    public void setInputType(Context context, CustomTextInputEditText view, String type) {
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

                case ViewPropertiesConstant.Pattern_Date:
                    view.setInputType(InputType.TYPE_CLASS_DATETIME | InputType.TYPE_DATETIME_VARIATION_DATE);
                    break;

                case ViewPropertiesConstant.Text_Multiline:
                    view.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    break;

                case ViewPropertiesConstant.First_Letter_Capital:
                    view.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
                    break;

                default:
                    view.setInputType(InputType.TYPE_CLASS_TEXT);
                    break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputEditText.class, "setInputType");
        }
    }

    public void setImeOptions(Context context, CustomTextInputEditText view, String action) {
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

    public void setDrawablesLRTB(Context context, CustomTextInputEditText view, int left, int top, int right, int bottom) {
        try {
            view.setCompoundDrawablesWithIntrinsicBounds(left, top, right, bottom);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomTextInputEditText.class, "setDrawablesLRTB");
        }
    }

    /**
     * Initialize the speech engine.
     */
    private void initializeSpeech(Context context) throws Exception {
        try {
            mSpeechRecognizer = SpeechRecognizer.createSpeechRecognizer(context);
            mSpeechRecognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, "en-US");
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PARTIAL_RESULTS, false);
            mSpeechRecognizerIntent.putExtra("android.speech.extra.DICTATION_MODE", true);
            // mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_PREFER_OFFLINE, true);
            mSpeechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, context.getPackageName());

            mSpeechRecognizer.setRecognitionListener(new RecognitionListener() {
                @Override
                public void onReadyForSpeech(Bundle params) {
                    Log.e("TextInputLayout", "onReadyForSpeech");
                    setDrawablesLRTB(context, CustomTextInputEditText.this, 0, 0, R.drawable.ic_mic_blue, 0);
                }

                @Override
                public void onBeginningOfSpeech() {
                    Log.e("TextInputLayout", "onBeginningOfSpeech");
                    setDrawablesLRTB(context, CustomTextInputEditText.this, 0, 0, R.drawable.ic_mic_blue, 0);
                }

                @Override
                public void onRmsChanged(float rmsdB) {
                    Log.d("TextInputLayout", "onRmsChanged");
                    setDrawablesLRTB(context, CustomTextInputEditText.this, 0, 0, R.drawable.ic_mic_blue, 0);
                }

                @Override
                public void onBufferReceived(byte[] buffer) {
                    Log.d("TextInputLayout", "onBufferReceived");
                }

                @Override
                public void onEndOfSpeech() {
                    //Toast.makeText(MainActivity.this,"onEndOfSpeech",Toast.LENGTH_SHORT).show();
                    Log.e("TextInputLayout", "onEndOfSpeech");
                    isListening = false;
                    mProgressDialog = DialogUtil.showProgressDialog(context);
                    setDrawablesLRTB(context, CustomTextInputEditText.this, 0, 0, R.drawable.ic_mic_gray, 0);
                }

                @Override
                public void onError(int error) {
                    Log.e("TextInputLayout", "onError" + error);
                    isListening = false;
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    setDrawablesLRTB(context, CustomTextInputEditText.this, 0, 0, R.drawable.ic_mic_gray, 0);
                }

                @Override
                public void onResults(Bundle results) {
                    try {
                        Log.e("TextInputLayout", "onResults");
                        isListening = false;
                        DialogUtil.cancelProgressDialog(mProgressDialog);
                        setDrawablesLRTB(context, CustomTextInputEditText.this, 0, 0, R.drawable.ic_mic_gray, 0);

                        ArrayList<String> matches = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);

                        Log.e("TextInputLayout ", matches.get(0).toString());

                        if (matches != null) {
                            //searchSpeechText(matches.get(0).toString());
                            String writeText = "";
                            if(CustomTextInputEditText.this.getText() != null && !CustomTextInputEditText.this.getText().toString().equalsIgnoreCase("")) {
                                writeText = CustomTextInputEditText.this.getText() + matches.get(0).toString() + " ";
                            } else {
                                writeText = matches.get(0).toString() + " ";
                            }
                            CustomTextInputEditText.this.setText(writeText);
                        }
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), MicSearchActivity.class, "speechResult");
                    }
                }

                @Override
                public void onPartialResults(Bundle partialResults) {
                    Log.d("TextInputLayout", "onPartialResults");
                }

                @Override
                public void onEvent(int eventType, Bundle params) {
                    Log.d("TextInputLayout", "onEvent");
                }
            });
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "initializeSpeech");
        }
    }

    /**
     * Call the method start listening
     */
    private void startListening() {
        try {
            isListening = true;
            setDrawablesLRTB(context, CustomTextInputEditText.this, 0, 0, R.drawable.ic_mic_blue, 0);
            mSpeechRecognizer.startListening(mSpeechRecognizerIntent);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), MicSearchActivity.class, "startListening");
            //Toast.makeText(this, "There was an error " + e.toString(), Toast.LENGTH_LONG).show()
        }
    }
}