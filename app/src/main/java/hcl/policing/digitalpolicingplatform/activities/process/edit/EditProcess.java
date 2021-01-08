package hcl.policing.digitalpolicingplatform.activities.process.edit;

import android.view.View;

import java.util.ArrayList;
import java.util.Collections;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.flow.ServerRequest;
import hcl.policing.digitalpolicingplatform.activities.process.flow.SetRecord;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.viewProperties.ViewPropertiesConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomImageView;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomTextView;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateDynamicView;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class EditProcess {

    //edit process for main json
    public static void processEdit(ProcessCreationActivity act) {
        try {
            String dialogHeading = "";
            //CustomLinearLayout customLinearLayout = null;
            int count = 0;
            act.listView = new ArrayList<>();
            if (act.aExpectedQuestionListBean != null) {
                for (int j = 0; j < act.aExpectedQuestionListBean.size(); j++) {
                    if (act.aExpectedQuestionListBean.get(j).getQuestion_Id() == act.editId) {
                        dialogHeading = act.aExpectedQuestionListBean.get(j).getDialogHeading();

                        int childCount = act.llProcessLayout.getChildCount();
                        for (int m = 0; m < childCount; m++) {
                            View view = act.llProcessLayout.getChildAt(m);
                            if (view instanceof CustomLinearLayout) {
                                CustomLinearLayout cChildLinear = (CustomLinearLayout) view;
                                if (cChildLinear.getId() == act.editId) {
                                    count = m;
                                    act.listView.add(cChildLinear);
                                }
                            }
                        }
                        break;
                    }
                }
            }
            if (act.QuestionDependent) {
                act.isEditEnabled = true;
                NextQuestion.editNextQuestion(act);
            } else {
                if (act.listView.size() == 1) {
                    if (act.captureValueEdit != null && !act.captureValueEdit.equalsIgnoreCase("")) {

                        if (act.answerList != null && act.answerList.size() > 0) {
                            String check = null;
                            for (int k = 0; k < act.answerList.size(); k++) {
                                if (act.answerList.get(k).getValue() != null && !act.answerList.get(k).getValue().equalsIgnoreCase("")) {
                                    check = act.answerList.get(k).getValue();
                                    break;
                                }
                            }
                            if (check != null && !check.equalsIgnoreCase("")) {
                                act.listView.get(0).removeAllViews();
                                SetRecord.setRecordsFromEdit(act, act.captureValueEdit, act.listView.get(0));
                                CreateDynamicView.assistantTextAt(act, dialogHeading, act.llProcessLayout, null, null, count + 1);
                                SetRecord.setRecordListFromEditAt(act, act.llProcessLayout, act.editId, count + 2);
                            }
                        } else {
                            act.listView.get(0).removeAllViews();
                            SetRecord.setRecordsFromEdit(act, act.captureValueEdit, act.listView.get(0));
                        }
                    } else {
                        if (act.answerList != null && act.answerList.size() > 0) {
                            String check = null;
                            for (int k = 0; k < act.answerList.size(); k++) {
                                if (act.answerList.get(k).getValue() != null && !act.answerList.get(k).getValue().equalsIgnoreCase("")) {
                                    check = act.answerList.get(k).getValue();
                                    break;
                                }
                            }
                            if (check != null && !check.equalsIgnoreCase("")) {
                                act.listView.get(0).removeAllViews();
                                SetRecord.setRecordsFromEdit(act, act.listView.get(0));
                            }
                        }
                    }
                } else if (act.listView.size() == 2) {
                    if (act.answerList != null && act.answerList.size() > 0) {
                        String check = null;
                        for (int k = 0; k < act.answerList.size(); k++) {
                            if (act.answerList.get(k).getValue() != null && !act.answerList.get(k).getValue().equalsIgnoreCase("")) {
                                check = act.answerList.get(k).getValue();
                                break;
                            }
                        }
                        if (check != null && !check.equalsIgnoreCase("")) {
                            act.listView.get(0).removeAllViews();
                            act.listView.get(1).removeAllViews();
                            SetRecord.setRecordsFromEdit(act, act.captureValueEdit, act.listView.get(0));
                            SetRecord.setRecordsFromEdit(act, act.listView.get(1));
                        }
                    } else {
                        act.listView.get(0).removeAllViews();
                        SetRecord.setRecordsFromEdit(act, act.captureValueEdit, act.listView.get(0));
                        if (act.llProcessLayout != null) {
                            act.llProcessLayout.removeViewAt(count);
                            act.llProcessLayout.removeViewAt(count - 1);
                        }
                    }
                }
                if (act.SectionDependent) {
                    int tabIdShow = 0;
                    boolean sectionCheck = false;
                    if (act.idShowSectionList != null && act.idShowSectionList.size() > 0) {
                        for (int i = 0; i < act.idShowSectionList.size(); i++) {
                            int showId = act.idShowSectionList.get(i);
                            CustomLinearLayout customTabLayout = act.findViewById(showId);
                            if(customTabLayout != null)
                                customTabLayout.setVisibility(View.VISIBLE);

                            for (int j = 0; j < act.aPageSectionListBean.size(); j++) {
                                if (act.aPageSectionListBean.get(j).getPageSection_Id() == showId) {
                                    ServerRequest.setServerRequest(act, act.aPageSectionListBean.get(j).getPageSection_Name(), GenericConstant.SECTION_VISIBLE, "true", null);
                                }
                            }
                        }
                        tabIdShow = Collections.min(act.idShowSectionList);
                    }
                    if (act.idSectinHideList != null && act.idSectinHideList.size() > 0) {

                        for (int i = 0; i < act.idSectinHideList.size(); i++) {
                            int hideId = act.idSectinHideList.get(i);
                            CustomLinearLayout customTabLayout = act.findViewById(hideId);
                            if(customTabLayout != null)
                                customTabLayout.setVisibility(View.GONE);

                            for (int j = 0; j < act.aPageSectionListBean.size(); j++) {
                                if (act.aPageSectionListBean.get(j).getPageSection_Id() == hideId) {
                                    ServerRequest.setServerRequest(act, act.aPageSectionListBean.get(j).getPageSection_Name(), GenericConstant.SECTION_VISIBLE, "false", null);
                                    ServerRequest.setServerRequest(act, act.aPageSectionListBean.get(j).getPageSection_Name(), GenericConstant.SECTION_COMPLETE, "false", null);
                                    RemoveValue.removeSectionValue(act, act.aPageSectionListBean.get(j).getPageSection_Name(), act.aPageSectionListBean.get(j).getExpectedQuestionList());
                                }
                            }

                            if (ProcessCreationActivity.SectionTabID == hideId) {
                                ProcessCreationActivity.QUESTION_COUNT = 0;
                                ProcessCreationActivity.QuestionID = 0;
                                sectionCheck = true;
                            }
                        }
                    }
                    if (tabIdShow != 0 && ProcessCreationActivity.SectionTabID > tabIdShow) {
                        for (int k = 0; k < act.aPageSectionListBean.size(); k++) {
                            if(act.aPageSectionListBean.get(k).getPageSection_Id() == tabIdShow) {
                                ProcessCreationActivity.SECTION_COUNT = k;
                                break;
                            }
                        }
                        int startCount = ProcessCreationActivity.SECTION_COUNT + 1;
                        for (int j = startCount; j < act.aPageSectionListBean.size(); j++) {

                            int tabId = act.aPageSectionListBean.get(j).getPageSection_Id();
                            ServerRequest.setServerRequest(act, act.aPageSectionListBean.get(j).getPageSection_Name(), GenericConstant.SECTION_VISIBLE, "false", null);
                            ServerRequest.setServerRequest(act, act.aPageSectionListBean.get(j).getPageSection_Name(), GenericConstant.SECTION_COMPLETE, "false", null);

                            CustomLinearLayout customLinear = act.findViewById(tabId);
                            View viewImage = customLinear.getChildAt(0);
                            CustomImageView customImageView = (CustomImageView) viewImage;
                            customImageView.setImageResource(R.drawable.ic_dot_gray);

                            View view = customLinear.getChildAt(1);
                            CustomTextView customTextView = (CustomTextView) view;
                            customTextView.setTextColor(act, customTextView, ViewPropertiesConstant.Color_Gray_Light);
                        }
                        ProcessCreationActivity.SECTION_COUNT--;
                        ProcessCreationActivity.SectionTabID = 0;
                        act.isNextSection();

                    } else if (sectionCheck) {

                        ProcessCreationActivity.SectionTabID = 0;
                        act.isNextSection();

                    } else {
                        ProcessCreationActivity.QUESTION_COUNT = 0;
                        act.currentQuestion = "";
                        OpenSection.openSection(act, ProcessCreationActivity.SectionTabID);
                    }
                } else {
                    if(act.isTabProcessEnabled) {
                        if (act.isFinalAnswerEdit) {
                            act.isTabProcessAddPage = false;
                            act.isNextSectionTabProcess();
                        }
                    } else {
                        OpenSection.openSection(act, ProcessCreationActivity.SectionTabID);
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), EditProcess.class, "processEdit");
        }
    }
}
