package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.core.content.ContextCompat;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.edit.EditAnswerActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomRelativeLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomSwitchButton;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextInputLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.utils.CompareDate;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class DialogValue {

    /**
     * Set the value in dialog
     *
     * @param act
     * @param fieldArray
     */
    public static void setValueInDialog(ProcessCreationActivity act, ArrayList<AnswerValueDTO> fieldArray) {

        new Handler().postDelayed(() -> {
            try {
                int count = act.llAdd.getChildCount();
                Log.e("CHILD COUNT ", "setValue in dialog >>>>> " + count);
                if ((fieldArray != null && fieldArray.size() > 0) && (count > 0)) {
                    for (int i = 0; i < fieldArray.size(); i++) {
                        Log.e("FIELD ", "HEADING >>>>> " + fieldArray.get(i).getKey() + " mendatry >>>>> " + fieldArray.get(i).isMendatry() + "\n");
                        View view = act.llAdd.getChildAt(i);
                        if (view instanceof CustomTextInputLayout) {
                            CustomTextInputLayout textInputLayout = (CustomTextInputLayout) view;
                            EditText etFields = textInputLayout.getEditText();
                            if (etFields != null) {
                                if (etFields.getHint().toString().contains(fieldArray.get(i).getKey())) {
                                    if(etFields.getText() == null || etFields.getText().toString().equalsIgnoreCase(""))
                                    etFields.setText(fieldArray.get(i).getValue());
                                }
                            }
                            if (fieldArray.get(i).isMendatry() && fieldArray.get(i).getValue().equalsIgnoreCase("")) {
                                textInputLayout.setErrorEnabled(true);
                                textInputLayout.setError(act.getResources().getString(R.string.field_cannot_blank));
                            }
                        } else if (view instanceof CustomRelativeLayout) {
                            CustomRelativeLayout customRelativeLayout = (CustomRelativeLayout) view;

                            View vHeading = customRelativeLayout.getChildAt(1);
                            View v = customRelativeLayout.getChildAt(0);

                            if (vHeading instanceof CustomTextView) {
                                CustomTextView customTextView = (CustomTextView) vHeading;
                                if (customTextView.getText().toString().contains(fieldArray.get(i).getKey())) {
                                    if (v instanceof CustomTextView) {
                                        CustomTextView customText = (CustomTextView) v;
                                        if(customText.getText() == null || customText.getText().toString().equalsIgnoreCase(""))
                                        customText.setText(fieldArray.get(i).getValue());
                                        if (fieldArray.get(i).isMendatry() && fieldArray.get(i).getValue().equalsIgnoreCase("")) {
                                            customTextView.setTextColor(ContextCompat.getColor(act, R.color.red));
                                            customText.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                                        }
                                    }
                                }
                            }
                        } else if (view instanceof CustomSwitchButton) {
                            CustomSwitchButton customSwitchButton = (CustomSwitchButton) view;

                            if (customSwitchButton.getText().toString().contains(fieldArray.get(i).getKey())) {
                                if (fieldArray.get(i).getValue().toLowerCase().equalsIgnoreCase(act.getResources().getString(R.string.yes).toLowerCase())) {
                                    customSwitchButton.setChecked(true);
                                } else {
                                    customSwitchButton.setChecked(false);
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), DialogValue.class, "setValueInDialog");
            }
        }, 1500);

    }

    /**
     * Get the values from Dialogs and check validations
     *
     * @param act
     */
    public static void getValuesFromDialog(ProcessCreationActivity act) {
        try {
            int count = act.llAdd.getChildCount();

            for (int i = 0; i < count; i++) {
                View view = act.llAdd.getChildAt(i);

                if (view instanceof CustomTextInputLayout) {

                    CustomTextInputLayout textInputLayout = (CustomTextInputLayout) view;
                    act.answerList.get(i).setValue(textInputLayout.getEditText().getText().toString());

                    if (act.answerList.get(i).isMendatry() && act.answerList.get(i).getValue().equalsIgnoreCase("")) {
                        textInputLayout.setErrorEnabled(true);
                        textInputLayout.setError(act.getResources().getString(R.string.field_cannot_blank));
                    }
                } else if (view instanceof CustomRelativeLayout) {

                    CustomRelativeLayout customRelativeLayout = (CustomRelativeLayout) view;
                    View vHeading = customRelativeLayout.getChildAt(1);
                    View v = customRelativeLayout.getChildAt(0);

                    if (v instanceof CustomTextView) {
                        CustomTextView customTextHead = (CustomTextView) vHeading;
                        CustomTextView customTextView = (CustomTextView) v;
                        Log.e("TEXT", "VALUE >>>>> " + customTextView.getText().toString());
                        Log.e("TEXT", "Heading >>>>> " + customTextHead.getText().toString());
                        act.answerList.get(i).setValue(customTextView.getText().toString());
                        act.answerList.get(i).setCode(customTextView.getTag(customTextView));
                        if (act.answerList.get(i).isMendatry() && act.answerList.get(i).getValue().equalsIgnoreCase("")) {
                            customTextHead.setTextColor(ContextCompat.getColor(act, R.color.red));
                            customTextView.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                        }
                        if (act.answerList.get(i).getDependentOn() != 0 && act.answerList.get(i).getSelectLogic().equalsIgnoreCase(GenericConstant.LESSER_DATE)) {
                            for (int j = 0; j < act.answerList.size(); j++) {
                                if(act.answerList.get(i).getDependentOn() == act.answerList.get(j).getId()) {
                                    int answer = CompareDate.compareDates(act.answerList.get(j).getValue(), act.answerList.get(i).getValue());
                                    if (answer == 2) {
                                        customTextHead.setTextColor(ContextCompat.getColor(act, R.color.red));
                                        customTextView.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                                        String error = act.answerList.get(i).getKey() + " can not be greater than " + act.answerList.get(j).getKey();
                                        DialogUtil.errorDialog(act, error);
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                        if (act.answerList.get(i).getDependentOn() != 0 && act.answerList.get(i).getSelectLogic().equalsIgnoreCase(GenericConstant.GREATER_DATE)) {
                            for (int j = 0; j < act.answerList.size(); j++) {
                                if (act.answerList.get(i).getDependentOn() == act.answerList.get(j).getId()) {
                                    Log.e("GREATER DATES ", " >> "+act.answerList.get(j).getValue() + " >>>>> " + act.answerList.get(i).getValue());
                                    int answer = CompareDate.compareDates(act.answerList.get(j).getValue(), act.answerList.get(i).getValue());
                                    if (answer == 1) {
                                        customTextHead.setTextColor(ContextCompat.getColor(act, R.color.red));
                                        customTextView.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                                        String error = act.answerList.get(i).getKey() + " can not be lesser than " + act.answerList.get(j).getKey();
                                        DialogUtil.errorDialog(act, error);
                                        break;
                                    }
                                    break;
                                }
                            }
                        }
                    }
                } else if (view instanceof CustomSwitchButton) {
                    CustomSwitchButton customSwitchButton = (CustomSwitchButton) view;
                    String buttonStatus = act.getString(R.string.no);
                    ;
                    if (customSwitchButton.isChecked()) {
                        buttonStatus = act.getString(R.string.yes);
                    }
                    act.answerList.get(i).setValue(buttonStatus);
                    if (act.answerList.get(i).isMendatry() && TextUtils.isEmpty(act.answerList.get(i).getValue())) {
                        customSwitchButton.setTextColor(ContextCompat.getColor(act, R.color.red));
                        customSwitchButton.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DialogValue.class, "getValuesFromDialog");
        }
    }

    /**
     * Get the values from Dialogs and check validation
     *
     * @param act
     */
    public static void getValuesFromDialog(EditAnswerActivity act) {
        try {
            int count = act.llAdd.getChildCount();
            Log.e("CHILD COUNT ", "getValue from dialog >>>>> " + count);
            for (int i = 0; i < count; i++) {
                View view = act.llAdd.getChildAt(i);

                if (view instanceof CustomTextInputLayout) {

                    CustomTextInputLayout textInputLayout = (CustomTextInputLayout) view;
                    act.answerList.get(i).setValue(textInputLayout.getEditText().getText().toString());
                    if (act.answerList.get(i).isMendatry() && act.answerList.get(i).getValue().equalsIgnoreCase("")) {
                        textInputLayout.setErrorEnabled(true);
                        textInputLayout.setError(act.getResources().getString(R.string.field_cannot_blank));
                    }
                } else if (view instanceof CustomRelativeLayout) {

                    CustomRelativeLayout customRelativeLayout = (CustomRelativeLayout) view;
                    View vHeading = customRelativeLayout.getChildAt(1);
                    View v = customRelativeLayout.getChildAt(0);

                    if (v instanceof CustomTextView) {
                        CustomTextView customTextHead = (CustomTextView) vHeading;
                        CustomTextView customTextView = (CustomTextView) v;
                        act.answerList.get(i).setValue(customTextView.getText().toString());
                        act.answerList.get(i).setCode(customTextView.getTag(customTextView));
                        if (act.answerList.get(i).isMendatry() && act.answerList.get(i).getValue().equalsIgnoreCase("")) {
                            customTextHead.setTextColor(ContextCompat.getColor(act, R.color.red));
                            customTextView.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                        }
                        if (act.answerList.get(0).getSelectLogic() != null && !act.answerList.get(0).getSelectLogic().equalsIgnoreCase("")
                                && act.answerList.get(0).getSelectLogic().equalsIgnoreCase(GenericConstant.DATE_LOGIC)) {
                            if (i > 0) {
                                if (act.answerList.get(i).getSelectLogic() != null && !act.answerList.get(i).getSelectLogic().equalsIgnoreCase("")
                                        && act.answerList.get(i).getSelectLogic().equalsIgnoreCase(GenericConstant.DATE_LOGIC)) {
                                    if (act.answerList.get(0).getValue() != null && !act.answerList.get(0).getValue().equalsIgnoreCase("")
                                            && act.answerList.get(i).getValue() != null && !act.answerList.get(i).getValue().equalsIgnoreCase("")) {
                                        int answer = CompareDate.compareDates(act.answerList.get(0).getValue(), act.answerList.get(i).getValue());
                                        if (answer == 1) {
                                            customTextHead.setTextColor(ContextCompat.getColor(act, R.color.red));
                                            customTextView.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                                            String error = act.answerList.get(i).getKey() + " can not be lesser than " + act.answerList.get(0).getKey();
                                            DialogUtil.errorDialog(act, error);
                                            break;
                                        }
                                    }

                                }
                            }
                        }
                    }

                } else if (view instanceof CustomSwitchButton) {
                    CustomSwitchButton customSwitchButton = (CustomSwitchButton) view;
                    String buttonStatus = act.getString(R.string.no);
                    ;
                    if (customSwitchButton.isChecked()) {
                        buttonStatus = act.getString(R.string.yes);
                    }
                    act.answerList.get(i).setValue(buttonStatus);
                    if (act.answerList.get(i).isMendatry() && TextUtils.isEmpty(act.answerList.get(i).getValue())) {
                        customSwitchButton.setTextColor(ContextCompat.getColor(act, R.color.red));
                        customSwitchButton.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DialogValue.class, "getValuesFromDialog");
        }
    }


    /**
     * Set the value in dialog
     *
     * @param act
     * @param fieldArray
     */
    public static void setValueInDialog(EditAnswerActivity act, ArrayList<AnswerValueDTO> fieldArray) {

        new Handler().postDelayed(() -> {
            try {
                int count = act.llAdd.getChildCount();
                Log.e("CHILD COUNT ", "setValue in dialog >>>>> " + count);
                if ((fieldArray != null && fieldArray.size() > 0) && (count > 0)) {
                    for (int i = 0; i < fieldArray.size(); i++) {
                        Log.e("FIELD ", "HEADING >>>>> " + fieldArray.get(i).getKey() + "\n");
                        View view = act.llAdd.getChildAt(i);
                        if (view instanceof CustomTextInputLayout) {
                            CustomTextInputLayout textInputLayout = (CustomTextInputLayout) view;
                            EditText etFields = textInputLayout.getEditText();
                            if (etFields != null) {
                                if (etFields.getHint().toString().contains(fieldArray.get(i).getKey())) {
                                    etFields.setText(fieldArray.get(i).getValue());
                                }
                            }
                            if (fieldArray.get(i).isMendatry() && fieldArray.get(i).getValue().equalsIgnoreCase("")) {
                                textInputLayout.setErrorEnabled(true);
                                textInputLayout.setError(act.getResources().getString(R.string.field_cannot_blank));
                            }
                        } else if (view instanceof CustomRelativeLayout) {
                            CustomRelativeLayout customRelativeLayout = (CustomRelativeLayout) view;

                            View vHeading = customRelativeLayout.getChildAt(1);
                            View v = customRelativeLayout.getChildAt(0);

                            if (vHeading instanceof CustomTextView) {
                                CustomTextView customTextView = (CustomTextView) vHeading;
                                if (customTextView.getText().toString().contains(fieldArray.get(i).getKey())) {
                                    if (v instanceof CustomTextView) {
                                        CustomTextView customText = (CustomTextView) v;
                                        customText.setText(fieldArray.get(i).getValue());
                                        if (fieldArray.get(i).isMendatry() && fieldArray.get(i).getValue().equalsIgnoreCase("")) {
                                            customTextView.setTextColor(ContextCompat.getColor(act, R.color.red));
                                            customText.setBackground(ContextCompat.getDrawable(act, R.drawable.bg_red_corner));
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), DialogValue.class, "setValueInDialog");
            }
        }, 1500);

    }
}
