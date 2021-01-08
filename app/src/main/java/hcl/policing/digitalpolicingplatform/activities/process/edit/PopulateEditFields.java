package hcl.policing.digitalpolicingplatform.activities.process.edit;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.flow.DialogValue;
import hcl.policing.digitalpolicingplatform.activities.process.flow.PageSection;
import hcl.policing.digitalpolicingplatform.activities.process.flow.ServerRequest;
import hcl.policing.digitalpolicingplatform.activities.process.flow.SetRecord;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextInputEditText;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateDynamicView;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedQuestionListBean;
import hcl.policing.digitalpolicingplatform.models.process.SubSectionListBean;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserBean;
import hcl.policing.digitalpolicingplatform.models.process.populate.PopulateSubSectionListBean;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class PopulateEditFields {

    public static void populateSingleField(Context context, CustomTextInputEditText editText, String selectLogic) {
        if(selectLogic.equalsIgnoreCase(GenericConstant.POPULATE_OFFICER_NAME)) {
            editText.setText(new AppSession(context).getUser().getOfficer_Name());
        } else if(selectLogic.equalsIgnoreCase(GenericConstant.POPULATE_OFFICER_NUMBER)) {
            editText.setText(new AppSession(context).getUser().getOfficer_Collar_Number());
        }
    }

    public static void populateFieldList(EditAnswerActivity act, Object object) {
        if (act.aPopulateListBean != null && act.aPopulateListBean.size() > 0) {
            for (int i = 0; i < act.aPopulateListBean.size(); i++) {
                act.mPopulateListBean = act.aPopulateListBean.get(i);
                Log.e("Populate ", "LIST ");

                if(act.mPopulateListBean.getSearch_Name().equalsIgnoreCase(act.searchName)
                        && act.mPopulateListBean.getSearch_Id() == act.searchId) {

                    act.aPopulateSectionList = act.mPopulateListBean.getPopulateSectionList();

                    for (int j = 0; j < act.aPopulateSectionList.size(); j++) {
                        act.mPopulateSectionList = act.aPopulateSectionList.get(j);
                        Log.e("Populate ", "SECTION ");

                        for (int k = 0; k < act.aPageSectionListBean.size(); k++) {

                            if(act.mPopulateSectionList.getPageSection_Name().equalsIgnoreCase(act.aPageSectionListBean.get(k).getPageSection_Name())
                                    && act.mPopulateSectionList.getPageSection_Id() == act.aPageSectionListBean.get(k).getPageSection_Id()) {

                                List<ExpectedQuestionListBean> aExpectedQuestionList = act.aPageSectionListBean.get(k).getExpectedQuestionList();
                                List<SubSectionListBean> aSubSectionList = act.aPageSectionListBean.get(k).getSubSectionList();

                                act.aPopulate_DetailList = act.mPopulateSectionList.getPopulate_DetailList();
                                act.aPopulateSubSectionList = act.mPopulateSectionList.getPopulateSubSectionList();

                                if(act.aPopulate_DetailList != null && act.aPopulate_DetailList.size() > 0) {

                                    for (int m = 0; m < act.aPopulate_DetailList.size(); m++) {
                                        String populateFrom = act.aPopulate_DetailList.get(m).getPopulate_From();
                                        String fieldName = act.aPopulate_DetailList.get(m).getField_Name();
                                        int fieldId = act.aPopulate_DetailList.get(m).getField_Id();
                                        Log.e("Populate ", "Field Name " + fieldName);

                                        for (int n = 0; n < act.answerList.size(); n++) {

                                            if(fieldName != null && fieldName.equalsIgnoreCase(act.answerList.get(n).getKey())
                                                && fieldId == act.answerList.get(n).getId()) {
                                                try {
                                                    Field field = object.getClass().getDeclaredField(populateFrom);
                                                    field.setAccessible(true);
                                                    String value = (String) field.get(object);
                                                    if(value != null) {
                                                        act.answerList.get(n).setValue(value);
                                                        Log.e("Populate ", "Field VALUE " + value);
                                                    }
                                                } catch (Exception e) {
                                                    e.printStackTrace();
                                                }
                                            }
                                        }
                                    }
                                }
                                if (act.aPopulateSubSectionList != null && act.aPopulateSubSectionList.size() > 0) {

                                    if (aSubSectionList != null && aSubSectionList.size() > 0) {
                                        populateSubSectionFieldList(act, act.aPopulateSubSectionList, aSubSectionList, object);
                                    }
                                    if(aExpectedQuestionList != null && aExpectedQuestionList.size() > 0) {
                                        for (int l = 0; l < aExpectedQuestionList.size(); l++) {

                                            ExpectedQuestionListBean mExpectedQuestionListBean = aExpectedQuestionList.get(l);

                                            if (mExpectedQuestionListBean.getSubSectionList() != null && mExpectedQuestionListBean.getSubSectionList().size() > 0) {
                                                populateSubSectionFieldList(act, act.aPopulateSubSectionList, mExpectedQuestionListBean.getSubSectionList(), object);
                                            }
                                        }
                                    }
                                }
                                break;
                            }
                        }
                    }
                    break;
                }
            }
        }
    }

    private static void populateSubSectionFieldList(EditAnswerActivity act, List<PopulateSubSectionListBean> aPopulateSubSectionList, List<SubSectionListBean> aSubSectionList, Object object) {

        if(aPopulateSubSectionList != null && aPopulateSubSectionList.size() > 0) {

            for (int i = 0; i < aPopulateSubSectionList.size(); i++) {

                PopulateSubSectionListBean mPopulateSubSectionList = aPopulateSubSectionList.get(i);

                for (int j = 0; j < aSubSectionList.size(); j++) {

                    if (mPopulateSubSectionList.getSubSection_Name().equalsIgnoreCase(aSubSectionList.get(j).getPageSection_Name())
                            && mPopulateSubSectionList.getSubSection_Id() == aSubSectionList.get(j).getPageSection_Id()) {

                        List<ExpectedQuestionListBean> aExpectedQuestionList = aSubSectionList.get(j).getExpectedQuestionList();
                        List<SubSectionListBean> aSubSectionListBean = aSubSectionList.get(j).getSubSectionList();

                        act.aPopulate_DetailList = mPopulateSubSectionList.getPopulate_DetailList();
                        List<PopulateSubSectionListBean> aPopulateSubSectionListBean = mPopulateSubSectionList.getPopulateSubSectionList();

                        if (act.aPopulate_DetailList != null && act.aPopulate_DetailList.size() > 0) {

                            for (int m = 0; m < act.aPopulate_DetailList.size(); m++) {
                                String populateFrom = act.aPopulate_DetailList.get(m).getPopulate_From();
                                String fieldName = act.aPopulate_DetailList.get(m).getField_Name();
                                int fieldId = act.aPopulate_DetailList.get(m).getField_Id();
                                Log.e("Populate Sub", "Field Name " + fieldName);

                                for (int n = 0; n < act.answerList.size(); n++) {

                                    if (fieldName != null && fieldName.equalsIgnoreCase(act.answerList.get(n).getKey())
                                            && fieldId == act.answerList.get(n).getId()) {
                                        try {
                                            Field field = object.getClass().getDeclaredField(populateFrom);
                                            field.setAccessible(true);
                                            String value = (String) field.get(object);
                                            if (value != null) {
                                                act.answerList.get(n).setValue(value);
                                                Log.e("Populate Sub", "Field VALUE " + value);
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            }
                        }
                        if (aPopulateSubSectionListBean != null && aPopulateSubSectionListBean.size() > 0) {

                            if (aSubSectionListBean != null && aSubSectionListBean.size() > 0) {
                                populateSubSectionFieldList(act, aPopulateSubSectionListBean, aSubSectionListBean, object);
                            }
                            if (aExpectedQuestionList != null && aExpectedQuestionList.size() > 0) {
                                for (int l = 0; l < aExpectedQuestionList.size(); l++) {

                                    ExpectedQuestionListBean mExpectedQuestionListBean = aExpectedQuestionList.get(l);

                                    if (mExpectedQuestionListBean.getSubSectionList() != null && mExpectedQuestionListBean.getSubSectionList().size() > 0) {
                                        populateSubSectionFieldList(act, aPopulateSubSectionListBean, mExpectedQuestionListBean.getSubSectionList(), object);
                                    }
                                }
                            }
                        }
                        break;
                    }
                }
            }
        }
    }
}
