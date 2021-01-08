package hcl.policing.digitalpolicingplatform.customLibraries;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.core.content.ContextCompat;
import androidx.core.widget.CompoundButtonCompat;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.PropertiesBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CustomCheckBox extends AppCompatCheckBox {

    private CustomCommonProperties customCommonProperties;

    public CustomCheckBox(Context context) {
        super(context);
        customCommonProperties = new CustomCommonProperties();
    }

    public CustomCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void SetCheckBox(Context context, PropertiesBean checkBoxDTO) {
        try {
            LinearLayout.LayoutParams layoutparams;
            layoutparams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            setLayoutParams(layoutparams);

            //To Set Width
            if (checkBoxDTO.getWidth() != 0) {
                customCommonProperties.setWidth(context, this, checkBoxDTO.getWidth());
            }

            //Set Height
            if (checkBoxDTO.getHeight() != 0) {
                customCommonProperties.setHeight(context, this, checkBoxDTO.getHeight());
            }

            //Set weight
            if (checkBoxDTO.getWeight() != 0) {
                customCommonProperties.setWeight(context, this, checkBoxDTO.getWeight());
            }

            //To set Layout Gravity
            if (checkBoxDTO.getLayoutGravity() != null && !checkBoxDTO.getLayoutGravity().equalsIgnoreCase("")) {
                customCommonProperties.setLayoutGravity(context, this, checkBoxDTO.getLayoutGravity());
            }

            //Set Margin
            if (checkBoxDTO.getMarginLeft() != 0 || checkBoxDTO.getMarginRight() != 0 || checkBoxDTO.getMarginTop() != 0 || checkBoxDTO.getMarginBottom() != 0) {

                customCommonProperties.setMargins(context, this, checkBoxDTO.getMarginLeft(), checkBoxDTO.getMarginTop(), checkBoxDTO.getMarginRight(), checkBoxDTO.getMarginBottom());
            }

            //To set ID
            if (checkBoxDTO.getId() != 0) {
                customCommonProperties.setID(context, this, checkBoxDTO.getId());
            }

            // To set PADDING
            if (checkBoxDTO.getPaddingLeft() != 0 || checkBoxDTO.getPaddingRight() != 0 || checkBoxDTO.getPaddingTop() != 0 || checkBoxDTO.getPaddingBottom() != 0) {
                customCommonProperties.setPadding(context, this, checkBoxDTO.getPaddingLeft(), checkBoxDTO.getPaddingTop(), checkBoxDTO.getPaddingRight(), checkBoxDTO.getPaddingBottom());
            } else {
                customCommonProperties.setPadding(context, this, 5, 0, 0, 0);
            }

            //To Set Text
            if (checkBoxDTO.getText() != null && !checkBoxDTO.getText().equalsIgnoreCase("")) {
                setText(context, this, checkBoxDTO.getText());
            }

            //TO set Text Size
            if (checkBoxDTO.getTextSize() != 0) {
                setTextSize(context, this, checkBoxDTO.getTextSize());
            }

            //To set text color
            if (checkBoxDTO.getTextColor() != null && !checkBoxDTO.getTextColor().equalsIgnoreCase("")) {
                setTextColor(context, this, checkBoxDTO.getTextColor());
            } else {
                setTextColor(ContextCompat.getColor(context, R.color.text_color_default));
            }

            //To set Visibility
            if (checkBoxDTO.getVisibility() != null && !checkBoxDTO.getVisibility().equalsIgnoreCase("")) {
                customCommonProperties.setVisibility(context, this, checkBoxDTO.getVisibility().toLowerCase());
            }

            //To set check uncheck color
            if (checkBoxDTO.getCheckColor() != null && !checkBoxDTO.getCheckColor().equalsIgnoreCase("") && checkBoxDTO.getUnCheckColor() != null && !checkBoxDTO.getUnCheckColor().equalsIgnoreCase("")) {
                setCheckAndUncheckColor(context, this, checkBoxDTO.getCheckColor(), checkBoxDTO.getUnCheckColor());
            }

            //Set drawable selector
            if (checkBoxDTO.getButtonSelector() != null && !checkBoxDTO.getButtonSelector().equalsIgnoreCase("")) {
                setDrawableSelector(context, this, R.drawable.checkbox_selector);
            }

            setOnCheckedChangeListener(new OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(ProcessCreationActivity.checkboxList != null && ProcessCreationActivity.checkboxList.size() > 0) {
                        if(buttonView.getText().equals("Victim")) {
                            for (int i = 0; i < ProcessCreationActivity.checkboxList.size(); i++) {
                                if(ProcessCreationActivity.checkboxList.get(i).getText().toString().equalsIgnoreCase("Witness")) {
                                    ProcessCreationActivity.checkboxList.get(i).setChecked(false);
                                    break;
                                }
                            }
                        } else if(buttonView.getText().equals("Witness")) {
                            for (int i = 0; i < ProcessCreationActivity.checkboxList.size(); i++) {
                                if(ProcessCreationActivity.checkboxList.get(i).getText().toString().equalsIgnoreCase("Victim")) {
                                    ProcessCreationActivity.checkboxList.get(i).setChecked(false);
                                    break;
                                }
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setText(Context context, CustomCheckBox view, String text) {
        try {
            view.setText(text);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCheckBox.class, "setText");
        }
    }

    public void setTextColor(Context context, CustomCheckBox view, String color) {
        try {
            view.setTextColor(Color.parseColor(color));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCheckBox.class, "setTextColor");
        }
    }

    public void setTextSize(Context context, CustomCheckBox view, int size) {
        try {
            view.setTextSize(TypedValue.COMPLEX_UNIT_SP, size);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCheckBox.class, "setTextSize");
        }
    }

    public void setCheckAndUncheckColor(Context context, CustomCheckBox view, String checkColor, String unCheckColor) {
        try {
            int[][] states = {{android.R.attr.state_checked}, {}};
            int[] colors = {Color.parseColor(checkColor), Color.parseColor(unCheckColor)};
            CompoundButtonCompat.setButtonTintList(view, new ColorStateList(states, colors));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCheckBox.class, "setCheckAndUncheckColor");
        }
    }

    public void setDrawableSelector(Context context, CustomCheckBox view, int drawable) {
        try {
            setButtonDrawable(ContextCompat.getDrawable(context, drawable));
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CustomCheckBox.class, "setDrawableSelector");
        }
    }
}