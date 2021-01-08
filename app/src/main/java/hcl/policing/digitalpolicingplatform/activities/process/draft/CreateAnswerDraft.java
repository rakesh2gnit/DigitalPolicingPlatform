package hcl.policing.digitalpolicingplatform.activities.process.draft;

import android.util.Log;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.flow.ServerRequest;
import hcl.policing.digitalpolicingplatform.activities.process.flow.SetClick;
import hcl.policing.digitalpolicingplatform.activities.process.flow.SetRecord;
import hcl.policing.digitalpolicingplatform.activities.process.flow.ShowHide;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateDynamicView;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CreateAnswerDraft {

    /**
     * Create Answer list basis on the questions
     */
    public static void createAnswers(ProcessCreationActivity act, String captureValue) {

        try {
            act.answerList = new ArrayList<>();
            String answerValue = captureValue; // Answer receive from user
            act.mExpectedAnswerListBean = null;
            if (act.aExpectedAnswerListBeans != null && act.aExpectedAnswerListBeans.size() > 0) {

                for (int i = 0; i < act.aExpectedAnswerListBeans.size(); i++) {

                    act.mExpectedAnswerListBean = act.aExpectedAnswerListBeans.get(i);

                    act.specialLogic = null;
                    act.searchName = act.mExpectedAnswerListBean.getSpecialLogic();
                    act.searchId = act.mExpectedAnswerListBean.getButton_Id();
                    act.llAdd.removeAllViews();
                    act.specialLogic = act.mExpectedAnswerListBean.getSpecialLogic();
                    act.answerList = new ArrayList<>();

                    act.aPageSection_detailListBean = act.mExpectedAnswerListBean.getPageSection_detailList();

                    if(act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {

                        for (int j = 0; j < act.aPageSection_detailListBean.size(); j++) {
                            AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                            answerValueDTO.setKey(act.aPageSection_detailListBean.get(j).getField_Name());
                            if (act.isTabProcessEnabled) {
                                answerValueDTO.setValue(ServerRequest.getSubJsonSavedAnswer(act, act.aPageSection_detailListBean.get(j).getField_Name()));
                            } else {
                                answerValueDTO.setValue(ServerRequest.getSavedAnswer(act, act.mPageSectionListBean.getPageSection_Name(), act.aPageSection_detailListBean.get(j).getField_Name()));
                            }
                            answerValueDTO.setDependentOn(act.aPageSection_detailListBean.get(j).getField_Dependent_On());
                            answerValueDTO.setId(act.aPageSection_detailListBean.get(j).getField_Id());
                            answerValueDTO.setSelectLogic(act.aPageSection_detailListBean.get(j).getSelect_Logic());
                            answerValueDTO.setMendatry(act.aPageSection_detailListBean.get(j).isField_Mendatry());
                            act.answerList.add(answerValueDTO);
                        }
                    }

                    if (act.mExpectedAnswerListBean.getMatchAnswer()) {

                        if (act.mExpectedAnswerListBean.getAnswer().toLowerCase().contains(answerValue.toLowerCase())) {

                            ShowHide.showHideFromAnswer(act, act.mExpectedAnswerListBean.getButton_Id());

                            if (act.mExpectedAnswerListBean.getIsFinalAnswer()) {
                                act.isNextQuestionOfSection = false;

                                if (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {
                                    //CustomLinearLayout cLinearLayout = act.findViewById(act.SECTION_COUNT + 1);
                                    //CreateDynamicView.assistantText(act, act.mExpectedQuestionListBean.getDialogHeading(), cLinearLayout, null, null);
                                    String check = null;
                                    for (int k = 0; k < act.answerList.size(); k++) {
                                        if (!act.answerList.get(k).getKey().contains(FieldsNameConstant.ID)
                                                && !act.answerList.get(k).getKey().contains(FieldsNameConstant.SYSTEM)
                                                && act.answerList.get(k).getValue() != null
                                                && !act.answerList.get(k).getValue().equalsIgnoreCase("")) {
                                            check = act.answerList.get(k).getValue();
                                            break;
                                        }
                                    }
                                    if (check != null && !check.equalsIgnoreCase("")) {
                                        if (answerValue != null && !answerValue.equalsIgnoreCase("")) {
                                            SetRecord.setRecords(act, answerValue);

                                            CreateDynamicView.assistantText(act, act.mExpectedQuestionListBean.getDialogHeading(), act.llProcessLayout, null, null);
                                        }
                                        SetRecord.setStorageRecord(act, check, "");
                                    }
                                    break;
                                } else {
                                    SetRecord.setRecords(act, answerValue);
                                    Log.d("ANSWER", "Else of PageSection Details - Moving to Next section/page");
                                    break;
                                }
                            } else {
                                if (act.mExpectedAnswerListBean.getSkipQuestion() > 0) {
                                    ProcessCreationActivity.QUESTION_COUNT = ProcessCreationActivity.QUESTION_COUNT + act.mExpectedAnswerListBean.getSkipQuestion();
                                }
                                act.isNextQuestionOfSection = true;

                                if (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {
                                    //CustomLinearLayout cLinearLayout = act.findViewById(act.SECTION_COUNT + 1);
                                    //CreateDynamicView.assistantText(act, act.mExpectedQuestionListBean.getDialogHeading(), cLinearLayout, null, null);
                                    String check = null;
                                    for (int k = 0; k < act.answerList.size(); k++) {
                                        if (!act.answerList.get(k).getKey().contains(FieldsNameConstant.ID)
                                                && !act.answerList.get(k).getKey().contains(FieldsNameConstant.SYSTEM)
                                                && act.answerList.get(k).getValue() != null
                                                && !act.answerList.get(k).getValue().equalsIgnoreCase("")) {
                                            check = act.answerList.get(k).getValue();
                                            break;
                                        }
                                    }
                                    if (check != null && !check.equalsIgnoreCase("")) {
                                        if (answerValue != null && !answerValue.equalsIgnoreCase("")) {
                                            SetRecord.setRecords(act, answerValue);

                                            CreateDynamicView.assistantText(act, act.mExpectedQuestionListBean.getDialogHeading(), act.llProcessLayout, null, null);
                                        }
                                        SetRecord.setStorageRecord(act, check, "");
                                    }
                                } else {
                                    SetRecord.setRecords(act, answerValue);
                                    Log.d("ANSWER >>> ", "Else of PageSection Details - Moving to Next qusetion");
                                }
                                ProcessCreationActivity.QUESTION_COUNT++;
                                if (act.aExpectedQuestionListBean != null) {
                                    CreateQuestionDraft.createQuestions(act, act.aExpectedQuestionListBean);
                                }
                                break;
                            }
//                            foundAnswer = true;

                        } else {
                            act.isClickedSpeak = true;
                            //else part pending. skip this iteration and move to next iteration
                            continue;
                        }

                    } else {
                        // Match Answer is not true

                        //SetRecord.setRecords(act, answerValue);

                        if (act.mExpectedAnswerListBean.getIsFinalAnswer()) {
                            act.isNextQuestionOfSection = false;

                            act.aPageSection_detailListBean = act.mExpectedAnswerListBean.getPageSection_detailList();
                            if (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {
                                //CustomLinearLayout cLinearLayout = act.findViewById(act.SECTION_COUNT + 1);
                                //CreateDynamicView.assistantText(act, act.mExpectedQuestionListBean.getDialogHeading(), cLinearLayout, null, null);
                                String check = null;
                                for (int k = 0; k < act.answerList.size(); k++) {
                                    if (!act.answerList.get(k).getKey().contains(FieldsNameConstant.ID)
                                            && !act.answerList.get(k).getKey().contains(FieldsNameConstant.SYSTEM)
                                            && act.answerList.get(k).getValue() != null
                                            && !act.answerList.get(k).getValue().equalsIgnoreCase("")) {
                                        check = act.answerList.get(k).getValue();
                                        break;
                                    }
                                }
                                if (check != null && !check.equalsIgnoreCase("")) {
                                    if (answerValue != null && !answerValue.equalsIgnoreCase("")) {
                                        SetRecord.setRecords(act, answerValue);

                                        CreateDynamicView.assistantText(act, act.mExpectedQuestionListBean.getDialogHeading(), act.llProcessLayout, null, null);
                                    }
                                    SetRecord.setStorageRecord(act, check, "");
                                }
                                break;
                            } else {
                                SetRecord.setRecords(act, answerValue);
                                Log.d("ANSWER", "Else of PageSection Details - Moving to Next section/page");
                                break;
                            }
                        } else {
                            if (act.mExpectedAnswerListBean.getSkipQuestion() != 0) {
                                ProcessCreationActivity.QUESTION_COUNT = ProcessCreationActivity.QUESTION_COUNT + act.mExpectedAnswerListBean.getSkipQuestion();
                            }
                            act.isNextQuestionOfSection = true;

                            act.aPageSection_detailListBean = act.mExpectedAnswerListBean.getPageSection_detailList();
                            if (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {
                                //CustomLinearLayout cLinearLayout = act.findViewById(act.SECTION_COUNT + 1);
                                //CreateDynamicView.assistantText(act, act.mExpectedQuestionListBean.getDialogHeading(), cLinearLayout, null, null);
                                String check = null;
                                for (int k = 0; k < act.answerList.size(); k++) {
                                    if (!act.answerList.get(k).getKey().contains(FieldsNameConstant.ID)
                                            && !act.answerList.get(k).getKey().contains(FieldsNameConstant.SYSTEM)
                                            && act.answerList.get(k).getValue() != null
                                            && !act.answerList.get(k).getValue().equalsIgnoreCase("")) {
                                        check = act.answerList.get(k).getValue();
                                        break;
                                    }
                                }
                                if (check != null && !check.equalsIgnoreCase("")) {
                                    if (answerValue != null && !answerValue.equalsIgnoreCase("")) {
                                        SetRecord.setRecords(act, answerValue);

                                        CreateDynamicView.assistantText(act, act.mExpectedQuestionListBean.getDialogHeading(), act.llProcessLayout, null, null);
                                    }
                                    SetRecord.setStorageRecord(act, check, "");
                                }
                            } else {
                                SetRecord.setRecords(act);
                            }
                            ProcessCreationActivity.QUESTION_COUNT++;
                            if (act.aExpectedQuestionListBean != null) {
                                CreateQuestionDraft.createQuestions(act, act.aExpectedQuestionListBean);
                            }
                            break;
                        }
                        //act.foundAnswer = true;
                    }
                }
            } else {

//                speakNow(getString(R.string.no_answer));
                BasicMethodsUtil.getInstance().showToast(act, act.getString(R.string.no_answer));
            }

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateAnswerDraft.class, "createAnswers");
        }
    }
}
