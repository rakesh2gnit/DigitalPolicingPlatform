package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class ServerRequestByView {

    private static JSONObject objMaster;
    private static JSONObject objSection;
    static JSONObject objField;
    private static String servername = "", question = "", answer = "";

    /**
     * Create the server request
     * @param act
     */
    public static void createServerRequest(ProcessCreationActivity act) {
        try {
            objMaster = new JSONObject();
            int count = act.llTabLayout.getChildCount();
            Log.e("Child ", " count >>>>> " + count);
            for (int i = 0; i < count; i++) {
                View view = act.llTabLayout.getChildAt(i);
                objSection = new JSONObject();
                if (view instanceof CustomLinearLayout) {
                    CustomLinearLayout customLinearLayout = (CustomLinearLayout) view;
                    View v = customLinearLayout.getChildAt(1);
                    if (v instanceof CustomTextView) {
                        CustomTextView customTextView = (CustomTextView) v;
                        servername = BasicMethodsUtil.getInstance().getServerName(customTextView.getText().toString());
                    }
                    int id = customLinearLayout.getId();
                    CustomLinearLayout custmProcessLayout = act.findViewById(id - 100);
                    int childCount = custmProcessLayout.getChildCount();
                    Log.e("Child ", " count >>>>> " + childCount);
                    for (int j = 0; j < childCount; j++) {
                        if (j % 2 == 0) {
                            View viewProcess = custmProcessLayout.getChildAt(j);
                            if (viewProcess instanceof CustomLinearLayout) {
                                CustomLinearLayout customLinear = (CustomLinearLayout) viewProcess;
                                View vChild = customLinear.getChildAt(1);
                                if (vChild instanceof CustomTextView) {
                                    CustomTextView customTextChild = (CustomTextView) vChild;
                                    question = BasicMethodsUtil.getInstance().getServerName(customTextChild.getText().toString());
                                    //objField = new JSONObject();
                                }
                            }
                        } else {
                            View viewProcess = custmProcessLayout.getChildAt(j);
                            if (viewProcess instanceof CustomLinearLayout) {
                                CustomLinearLayout customLinear = (CustomLinearLayout) viewProcess;
                                if (customLinear.getChildCount() == 2) {
                                    View vChild = customLinear.getChildAt(1);
                                    if (vChild instanceof CustomLinearLayout) {
                                        CustomLinearLayout cLinear = (CustomLinearLayout) vChild;
                                        if (cLinear.getChildCount() == 1) {
                                            answer = "";
                                            View vC = cLinear.getChildAt(0);
                                            if (vC instanceof CustomTextView) {
                                                CustomTextView customTextViewChild = (CustomTextView) vC;
                                                answer = customTextViewChild.getText().toString();
                                                objSection.put(question, answer);
                                            }
                                        } else {
                                            question = "";
                                            answer = "";
                                            View vC = cLinear.getChildAt(0);
                                            if (vC instanceof CustomTextView) {
                                                CustomTextView customTextViewChild = (CustomTextView) vC;
                                                question = BasicMethodsUtil.getInstance().getServerName(customTextViewChild.getText().toString());
                                            }
                                            View vC2 = cLinear.getChildAt(2);
                                            if (vC2 instanceof CustomTextView) {
                                                CustomTextView customTextViewChild = (CustomTextView) vC2;
                                                answer = customTextViewChild.getText().toString();
                                            }
                                            objSection.put(question, answer);
                                        }
                                    }
                                } else {
                                    //objField = new JSONObject();
                                    question = "";
                                    answer = "";
                                    int cCount = customLinear.getChildCount();
                                    for (int k = 1; k < cCount; k++) {
                                        View vChild = customLinear.getChildAt(k);
                                        if (vChild instanceof CustomLinearLayout) {
                                            CustomLinearLayout cLinear = (CustomLinearLayout) vChild;
                                            View vC = cLinear.getChildAt(0);
                                            if (vC instanceof CustomTextView) {
                                                CustomTextView customTextViewChild = (CustomTextView) vC;
                                                question = BasicMethodsUtil.getInstance().getServerName(customTextViewChild.getText().toString());
                                            }
                                            View vC2 = cLinear.getChildAt(2);
                                            if (vC2 instanceof CustomTextView) {
                                                CustomTextView customTextViewChild = (CustomTextView) vC2;
                                                answer = customTextViewChild.getText().toString();
                                            }
                                        }
                                        objSection.put(question, answer);
                                        //objField.put(quesVal, answerVal);
                                    }
                                }
                            }
                        }
                    }
                }
                objMaster.put(servername, objSection);
            }
            String fileName = BasicMethodsUtil.getInstance().getServerName(act.mProcessListBean.getProcess_Name() + "_NEW");

            Utilities.getInstance(act).writeFile(objMaster.toString(), fileName, ProcessCreationActivity.directoryDraft);
            Log.e("SERVER REQUEST ", " NEW >>>>> " + objMaster.toString());
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequestByView.class, "createServerRequest");
        }
    }
}
