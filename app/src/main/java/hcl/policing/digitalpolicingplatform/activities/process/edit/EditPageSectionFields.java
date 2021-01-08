package hcl.policing.digitalpolicingplatform.activities.process.edit;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.flow.CreateField;
import hcl.policing.digitalpolicingplatform.activities.process.flow.DialogValue;
import hcl.policing.digitalpolicingplatform.activities.process.flow.ServerRequest;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedQuestionListBean;
import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class EditPageSectionFields {

    // for editing fields value in main json
    public static void editAnswer(ProcessCreationActivity act, int Id) {
        try {
            act.answerList = new ArrayList<>();
            String answer = "";
            if (act.aExpectedQuestionListBean != null && act.aExpectedQuestionListBean.size() > 0) {

                for (int j = 0; j < act.aExpectedQuestionListBean.size(); j++) {

                    if (act.aExpectedQuestionListBean.get(j).getQuestion_Id() == Id) {

                        int childCount = act.llProcessLayout.getChildCount();

                        for (int m = 0; m < childCount; m++) {
                            View view = act.llProcessLayout.getChildAt(m);
                            if (view instanceof CustomLinearLayout) {
                                CustomLinearLayout cChildLinear = (CustomLinearLayout) view;
                                if (cChildLinear.getId() == act.editId) {
                                    View vChild = cChildLinear.getChildAt(1);
                                    if (vChild instanceof CustomLinearLayout) {
                                        CustomLinearLayout cLinear = (CustomLinearLayout) vChild;
                                        View vC = cLinear.getChildAt(0);
                                        if (vC instanceof CustomTextView) {
                                            CustomTextView customTextView = (CustomTextView) vC;
                                            answer = customTextView.getText().toString();
                                        }
                                    }
                                    break;
                                }
                            }
                        }

                        ExpectedQuestionListBean mExpectedQuestionListBean = act.aExpectedQuestionListBean.get(j);
                        act.aExpectedAnswerListBeans = new ArrayList<>();
                        act.aExpectedAnswerListBeans = mExpectedQuestionListBean.getExpectedAnswerList();
                        act.aPageSection_detailListBean = mExpectedQuestionListBean.getPageSection_detailList();

                        if (act.aExpectedAnswerListBeans != null && act.aExpectedAnswerListBeans.size() > 0) {

                            if (act.aExpectedAnswerListBeans.size() > 1) {
                                for (int k = 0; k < act.aExpectedAnswerListBeans.size(); k++) {
                                    if (act.aExpectedAnswerListBeans.get(k).getAnswer().toLowerCase().equalsIgnoreCase(answer.toLowerCase())) {
                                        act.mExpectedAnswerListBean = act.aExpectedAnswerListBeans.get(k);

                                        act.aPageSection_detailListBean = act.mExpectedAnswerListBean.getPageSection_detailList();

                                        if (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {
                                            for (int m = 0; m < act.aPageSection_detailListBean.size(); m++) {
                                                AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                                                answerValueDTO.setKey(act.aPageSection_detailListBean.get(m).getField_Name());
                                                Log.e("ANSWER ", "FROM Edit >>>>> " + ServerRequest.getSavedAnswer(act, act.mPageSectionListBean.getPageSection_Name(), act.aPageSection_detailListBean.get(m).getField_Name()));
                                                answerValueDTO.setValue(ServerRequest.getSavedAnswer(act, act.mPageSectionListBean.getPageSection_Name(), act.aPageSection_detailListBean.get(m).getField_Name()));
                                                answerValueDTO.setMendatry(act.aPageSection_detailListBean.get(m).isField_Mendatry());
                                                act.answerList.add(answerValueDTO);
                                            }
                                            //call the Pagelist method
                                            //act.llAdd.removeAllViews();
                                            //createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                                            break;
                                        }
                                    }
                                }
                            } else {
                                act.mExpectedAnswerListBean = act.aExpectedAnswerListBeans.get(0);

                                act.aPageSection_detailListBean = act.mExpectedAnswerListBean.getPageSection_detailList();

                                if (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {
                                    for (int m = 0; m < act.aPageSection_detailListBean.size(); m++) {
                                        AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                                        answerValueDTO.setKey(act.aPageSection_detailListBean.get(m).getField_Name());
                                        answerValueDTO.setValue(ServerRequest.getSavedAnswer(act, act.mPageSectionListBean.getPageSection_Name(), act.aPageSection_detailListBean.get(m).getField_Name()));
                                        answerValueDTO.setMendatry(act.aPageSection_detailListBean.get(m).isField_Mendatry());
                                        act.answerList.add(answerValueDTO);
                                    }
                                    //act.llAdd.removeAllViews();
                                    //createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                                    break;
                                }
                            }
                        } else if (act.aExpectedAnswerListBeans == null && (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0)) {
                            //call the Pagelist method

                            act.answerList = new ArrayList<>();
                            for (int k = 0; k < act.aPageSection_detailListBean.size(); k++) {
                                AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                                answerValueDTO.setKey(act.aPageSection_detailListBean.get(k).getField_Name());
                                answerValueDTO.setValue(ServerRequest.getSavedAnswer(act, act.mPageSectionListBean.getPageSection_Name(), act.aPageSection_detailListBean.get(k).getField_Name()));
                                answerValueDTO.setMendatry(act.aPageSection_detailListBean.get(k).isField_Mendatry());
                                act.answerList.add(answerValueDTO);
                            }
                            //act.llAdd.removeAllViews();
                            //createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), EditPageSectionFields.class, "editAnswer");
        }
    }

    // for editing fields value in sub json
    public static void editAnswerTabProcess(ProcessCreationActivity act, int Id) {
        Log.e("called >>>>>" ," tab process edit");
        try {
            int tempId = 0;
            for (int i = 0; i < act.tabFlowPosList.size(); i++) {
                if(act.tabFlowPosList.get(i).getArrayName().equalsIgnoreCase(act.tabName)) {
                    act.aSubSectionListBean = act.tabFlowPosList.get(i).getSubSectionList();
                    act.aExpectedQuestionListBean = act.tabFlowPosList.get(i).getExpectedQuestionList();
                    act.aPageSection_detailListBean = act.tabFlowPosList.get(i).getPageSection_detailList();
                    tempId = act.tabFlowPosList.get(i).getId();
                    break;
                }
            }
            act.answerList = new ArrayList<>();
            String answer = "";
            if (act.aExpectedQuestionListBean != null && act.aExpectedQuestionListBean.size() > 0) {

                for (int i = 0; i < act.aExpectedQuestionListBean.size(); i++) {
                    if (act.aExpectedQuestionListBean.get(i).getQuestion_Id() == Id) {

                        int childCount = act.llProcessLayout.getChildCount();
                        for (int m = 0; m < childCount; m++) {
                            View view = act.llProcessLayout.getChildAt(m);
                            if (view instanceof CustomLinearLayout) {
                                CustomLinearLayout cChildLinear = (CustomLinearLayout) view;
                                if (cChildLinear.getId() == act.editId) {
                                    View vChild = cChildLinear.getChildAt(1);
                                    if (vChild instanceof CustomLinearLayout) {
                                        CustomLinearLayout cLinear = (CustomLinearLayout) vChild;
                                        View vC = cLinear.getChildAt(0);
                                        if (vC instanceof CustomTextView) {
                                            CustomTextView customTextView = (CustomTextView) vC;
                                            answer = customTextView.getText().toString();
                                        }
                                    }
                                    break;
                                }
                            }
                        }

                        ExpectedQuestionListBean mExpectedQuestionListBean = act.aExpectedQuestionListBean.get(i);
                        act.aExpectedAnswerListBeans = new ArrayList<>();
                        act.aExpectedAnswerListBeans = mExpectedQuestionListBean.getExpectedAnswerList();
                        act.aPageSection_detailListBean = mExpectedQuestionListBean.getPageSection_detailList();

                        if (act.aExpectedAnswerListBeans != null && act.aExpectedAnswerListBeans.size() > 0) {

                            if (act.aExpectedAnswerListBeans.size() > 1) {
                                for (int k = 0; k < act.aExpectedAnswerListBeans.size(); k++) {
                                    if (act.aExpectedAnswerListBeans.get(k).getAnswer().toLowerCase().equalsIgnoreCase(answer.toLowerCase())) {
                                        act.mExpectedAnswerListBean = act.aExpectedAnswerListBeans.get(k);

                                        act.aPageSection_detailListBean = act.mExpectedAnswerListBean.getPageSection_detailList();

                                        if (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {
                                            for (int m = 0; m < act.aPageSection_detailListBean.size(); m++) {
                                                AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                                                answerValueDTO.setKey(act.aPageSection_detailListBean.get(m).getField_Name());
                                                Log.e("ANSWER ", "FROM Edit >>>>> " + ServerRequest.getSubJsonSavedAnswer(act, act.aPageSection_detailListBean.get(m).getField_Name()));
                                                answerValueDTO.setValue(ServerRequest.getSubJsonSavedAnswer(act, act.aPageSection_detailListBean.get(m).getField_Name()));
                                                answerValueDTO.setMendatry(act.aPageSection_detailListBean.get(m).isField_Mendatry());
                                                act.answerList.add(answerValueDTO);
                                            }
                                            //call the Pagelist method
                                            //act.llAdd.removeAllViews();
                                            //createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                                            break;
                                        }
                                    }
                                }
                            } else {
                                act.mExpectedAnswerListBean = act.aExpectedAnswerListBeans.get(0);

                                act.aPageSection_detailListBean = act.mExpectedAnswerListBean.getPageSection_detailList();
                                if (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {
                                    for (int m = 0; m < act.aPageSection_detailListBean.size(); m++) {
                                        AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                                        answerValueDTO.setKey(act.aPageSection_detailListBean.get(m).getField_Name());
                                        answerValueDTO.setValue(ServerRequest.getSubJsonSavedAnswer(act, act.aPageSection_detailListBean.get(m).getField_Name()));
                                        answerValueDTO.setMendatry(act.aPageSection_detailListBean.get(m).isField_Mendatry());
                                        act.answerList.add(answerValueDTO);
                                    }
                                    //act.llAdd.removeAllViews();
                                    //createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                                    break;
                                }
                            }
                        } else if (act.aExpectedAnswerListBeans == null && (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0)) {
                            //call the Pagelist method

                            act.answerList = new ArrayList<>();
                            for (int k = 0; k < act.aPageSection_detailListBean.size(); k++) {
                                AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                                answerValueDTO.setKey(act.aPageSection_detailListBean.get(k).getField_Name());
                                answerValueDTO.setValue(ServerRequest.getSubJsonSavedAnswer(act, act.aPageSection_detailListBean.get(k).getField_Name()));
                                answerValueDTO.setMendatry(act.aPageSection_detailListBean.get(k).isField_Mendatry());
                                act.answerList.add(answerValueDTO);
                            }
                            //act.llAdd.removeAllViews();
                            //createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                            break;
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), EditPageSectionFields.class, "editAnswerTabProcess");
        }
    }

    /**
     * ATTRIBUTE_COUNT -> It will use when OK Button pressed for next view
     * Create the Page Section Details
     *
     * @param pageSection_detailListBeans
     */
    public static void createPageSectionDetails(ProcessCreationActivity act, List<PageSection_detailListBean> pageSection_detailListBeans, int attCount) {

        try {
            for (int i = attCount; i < pageSection_detailListBeans.size(); i++) {

                CreateField.createAttributes(act, pageSection_detailListBeans.get(i));

            }
            DialogValue.setValueInDialog(act, act.answerList);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), EditPageSectionFields.class, "createPageSectionDetails");
        }
    }
}
