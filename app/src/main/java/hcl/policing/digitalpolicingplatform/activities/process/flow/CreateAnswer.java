package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.util.Log;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateDynamicView;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CreateAnswer {

    /**
     * Create Answer list basis on the questions
     * @param act
     */
    public static void createAnswers(ProcessCreationActivity act) {

        try {
            act.answerList = new ArrayList<>();
            String answerValue = act.captureValue; // Answer receive from user
            act.mExpectedAnswerListBean = null;
            if (act.aExpectedAnswerListBeans != null && act.aExpectedAnswerListBeans.size() > 0) {

                for (int i = 0; i < act.aExpectedAnswerListBeans.size(); i++) {

                    act.mExpectedAnswerListBean = act.aExpectedAnswerListBeans.get(i);

                    act.specialLogic = null;
                    act.searchName = act.mExpectedAnswerListBean.getSpecialLogic();
                    act.searchId = act.mExpectedAnswerListBean.getButton_Id();
                    act.specialLogic = act.mExpectedAnswerListBean.getSpecialLogic();
                    act.llAdd.removeAllViews();
                    act.answerList = new ArrayList<>();

                    act.aPageSection_detailListBean = act.mExpectedAnswerListBean.getPageSection_detailList();

                    if(act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {

                        for (int j = 0; j < act.aPageSection_detailListBean.size(); j++) {
                            AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                            answerValueDTO.setKey(act.aPageSection_detailListBean.get(j).getField_Name());
                            answerValueDTO.setDependentOn(act.aPageSection_detailListBean.get(j).getField_Dependent_On());
                            answerValueDTO.setId(act.aPageSection_detailListBean.get(j).getField_Id());
                            answerValueDTO.setSelectLogic(act.aPageSection_detailListBean.get(j).getSelect_Logic());
                            answerValueDTO.setMendatry(act.aPageSection_detailListBean.get(j).isField_Mendatry());
                            if (act.isTabProcessEnabled) {
                                answerValueDTO.setValue(ServerRequest.getSubJsonSavedAnswer(act, act.aPageSection_detailListBean.get(j).getField_Name()));
                            } else {
                                answerValueDTO.setValue(ServerRequest.getSavedAnswer(act, act.mPageSectionListBean.getPageSection_Name(), act.aPageSection_detailListBean.get(j).getField_Name()));
                            }
                            act.answerList.add(answerValueDTO);
                        }
                    }

                    Log.e("ANSWER", "mExpectedAnswerListBean : " + act.mExpectedAnswerListBean.getProcessingDetails());

                    if (act.mExpectedAnswerListBean.getMatchAnswer()) {

                        if (act.mExpectedAnswerListBean.getAnswer().toLowerCase().contains(answerValue.toLowerCase())
                                && act.mExpectedAnswerListBean.getButton_Id() == act.buttonId) {

                            ShowHide.showHideFromAnswer(act, act.buttonId);

                            if (act.mExpectedAnswerListBean.getIsFinalAnswer()) {
                                act.isNextQuestionOfSection = false;

                                if (act.mExpectedAnswerListBean.getIsBackendProcessingRequired()) {

                                    Log.d("ANSWER", "Backend Process Details : " + act.mExpectedAnswerListBean.getProcessingDetails());

                                }

                                if (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {

                                    boolean isMandatoryBlank = false;
                                    for (int k = 0; k < act.answerList.size(); k++) {
                                        if (!act.answerList.get(k).getKey().contains(FieldsNameConstant.ID)
                                                && !act.answerList.get(k).getKey().contains(FieldsNameConstant.SYSTEM)
                                                && act.answerList.get(k).isMendatry()
                                                && (act.answerList.get(k).getValue() == null
                                                || act.answerList.get(k).getValue().equalsIgnoreCase(""))) {
                                            isMandatoryBlank = true;
                                            break;
                                        }
                                    }

                                    if(!isMandatoryBlank) {
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

                                            if(act.isTabProcessEnabled) {
                                                act.isNextSectionTabProcess();
                                            } else {
                                                act.isNextSection();
                                            }
                                        } else {
                                            PageSection.createPageSectionDetails(act, act.mExpectedAnswerListBean.getPageSection_detailList(), GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                                            DialogValue.setValueInDialog(act, act.answerList);
                                        }
                                    } else {
                                        //Log.e("CHECK 2 >> ", "VALUE >>>>> "+check);
                                        PageSection.createPageSectionDetails(act, act.mExpectedAnswerListBean.getPageSection_detailList(), GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                                        DialogValue.setValueInDialog(act, act.answerList);
                                    }
                                    break;
                                } else {
                                    SetRecord.setRecords(act, answerValue);
                                    Log.d("ANSWER", "Else of PageSection Details - Moving to Next section/page");
                                    if(act.isTabProcessEnabled) {
                                        act.isNextSectionTabProcess();
                                    } else {
                                        act.isNextSection();
                                    }
                                }
                            } else {
                                if (act.mExpectedAnswerListBean.getSkipQuestion() != 0) {
                                    ProcessCreationActivity.QUESTION_COUNT = ProcessCreationActivity.QUESTION_COUNT + act.mExpectedAnswerListBean.getSkipQuestion();
                                }
                                act.isNextQuestionOfSection = true;

                                if (act.mExpectedAnswerListBean.getIsBackendProcessingRequired()) {

                                    Log.d("ANSWER", "Backend Process Details : " + act.mExpectedAnswerListBean.getProcessingDetails());

                                }

                                if (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {

                                    boolean isMandatoryBlank = false;
                                    for (int k = 0; k < act.answerList.size(); k++) {
                                        if (!act.answerList.get(k).getKey().contains(FieldsNameConstant.ID)
                                                && !act.answerList.get(k).getKey().contains(FieldsNameConstant.SYSTEM)
                                                && act.answerList.get(k).isMendatry()
                                                && (act.answerList.get(k).getValue() == null
                                                || act.answerList.get(k).getValue().equalsIgnoreCase(""))) {
                                            isMandatoryBlank = true;
                                            break;
                                        }
                                    }

                                    if(!isMandatoryBlank) {
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

                                            act.isNextQuestion();
                                            break;
                                        } else {
                                            PageSection.createPageSectionDetails(act, act.mExpectedAnswerListBean.getPageSection_detailList(), GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                                            DialogValue.setValueInDialog(act, act.answerList);
                                        }
                                    } else {
                                        //Log.e("CHECK 2 >> ", "VALUE >>>>> "+check);
                                        PageSection.createPageSectionDetails(act, act.mExpectedAnswerListBean.getPageSection_detailList(), GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                                        DialogValue.setValueInDialog(act, act.answerList);
                                    }
                                    break;
                                } else {
                                    SetRecord.setRecords(act, answerValue);
                                    Log.d("ANSWER", "Else of PageSection Details - Moving to Next qusetion");
                                    act.isNextQuestion();
                                    break;
                                }
                            }
//                            foundAnswer = true;

                        } else {
                            act.isClickedSpeak = true;
                            act.isClicked = true;
                            //else part pending. skip this iteration and move to next iteration
                            continue;
                        }

                    } else {
                        // Match Answer is not true

                        if (act.mExpectedAnswerListBean.getIsFinalAnswer()) {
                            act.isNextQuestionOfSection = false;

                            if (act.mExpectedAnswerListBean.getIsBackendProcessingRequired()) {
                                Log.e("ANSWER ", "Backend Process Details : " + act.mExpectedAnswerListBean.getProcessingDetails());
                            }

                            if (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {

                                //call the Pagelist method
                                boolean isMandatoryBlank = false;
                                for (int k = 0; k < act.answerList.size(); k++) {
                                    if (!act.answerList.get(k).getKey().contains(FieldsNameConstant.ID)
                                            && !act.answerList.get(k).getKey().contains(FieldsNameConstant.SYSTEM)
                                            && act.answerList.get(k).isMendatry()
                                            && (act.answerList.get(k).getValue() == null
                                            || act.answerList.get(k).getValue().equalsIgnoreCase(""))) {
                                        isMandatoryBlank = true;
                                        break;
                                    }
                                }

                                if(!isMandatoryBlank) {
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

                                        if(act.isTabProcessEnabled) {
                                            act.isNextSectionTabProcess();
                                        } else {
                                            act.isNextSection();
                                        }
                                    } else {
                                        PageSection.createPageSectionDetails(act, act.mExpectedAnswerListBean.getPageSection_detailList(), GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                                        DialogValue.setValueInDialog(act, act.answerList);
                                    }
                                } else {
                                    //Log.e("CHECK 2 >> ", "VALUE >>>>> "+check);
                                    PageSection.createPageSectionDetails(act, act.mExpectedAnswerListBean.getPageSection_detailList(), GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                                    DialogValue.setValueInDialog(act, act.answerList);
                                }
                                break;
                            }  else {
                                Log.d("CREATE ANS", "Moving to Next section/page");
                                if (answerValue != null && !answerValue.equalsIgnoreCase("")) {
                                    SetRecord.setRecords(act, answerValue);
                                }
                                if(act.isTabProcessEnabled) {
                                    act.isNextSectionTabProcess();
                                } else {
                                    act.isNextSection();
                                }
                            }
                        } else {
                            if (act.mExpectedAnswerListBean.getSkipQuestion() != 0) {
                                ProcessCreationActivity.QUESTION_COUNT = ProcessCreationActivity.QUESTION_COUNT + act.mExpectedAnswerListBean.getSkipQuestion();
                            }
                            act.isNextQuestionOfSection = true;

                            if (act.mExpectedAnswerListBean.getIsBackendProcessingRequired()) {
                                Log.e("ANSWER ", "Backend Process Details : " + act.mExpectedAnswerListBean.getProcessingDetails());
                            }
                            Log.e("ANSWER", "field values which will be populated");

                            if (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {

                                //call the Pagelist method
                                boolean isMandatoryBlank = false;
                                for (int k = 0; k < act.answerList.size(); k++) {
                                    if (!act.answerList.get(k).getKey().contains(FieldsNameConstant.ID)
                                            && !act.answerList.get(k).getKey().contains(FieldsNameConstant.SYSTEM)
                                            && act.answerList.get(k).isMendatry()
                                            && (act.answerList.get(k).getValue() == null
                                            || act.answerList.get(k).getValue().equalsIgnoreCase(""))) {
                                        isMandatoryBlank = true;
                                        break;
                                    }
                                }

                                if(!isMandatoryBlank) {
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

                                        act.isNextQuestion();
                                        break;
                                    } else {
                                        PageSection.createPageSectionDetails(act, act.mExpectedAnswerListBean.getPageSection_detailList(), GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                                        DialogValue.setValueInDialog(act, act.answerList);
                                    }
                                } else {
                                    //Log.e("CHECK 2 >> ", "VALUE >>>>> "+check);
                                    PageSection.createPageSectionDetails(act, act.mExpectedAnswerListBean.getPageSection_detailList(), GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                                    DialogValue.setValueInDialog(act, act.answerList);
                                }
                                break;
                            } else {
                                Log.d("CREATE ANS", "Moving to Next section/page");
                                if (answerValue != null && !answerValue.equalsIgnoreCase("")) {
                                    SetRecord.setRecords(act, answerValue);
                                }
                                act.isNextQuestion();
                                break;
                            }
                        }
                        //act.foundAnswer = true;
                    }
                }
            } else {
                BasicMethodsUtil.getInstance().showToast(act, act.getString(R.string.no_answer));
            }

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateAnswer.class, "createAnswers");
        }
    }
}
