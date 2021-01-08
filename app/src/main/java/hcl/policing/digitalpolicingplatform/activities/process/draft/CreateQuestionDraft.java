package hcl.policing.digitalpolicingplatform.activities.process.draft;

import android.annotation.SuppressLint;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.flow.AddSubJsonList;
import hcl.policing.digitalpolicingplatform.activities.process.flow.PageSection;
import hcl.policing.digitalpolicingplatform.activities.process.flow.PrepareSubSection;
import hcl.policing.digitalpolicingplatform.activities.process.flow.ServerRequest;
import hcl.policing.digitalpolicingplatform.activities.process.flow.SetClick;
import hcl.policing.digitalpolicingplatform.activities.process.flow.SetRecord;
import hcl.policing.digitalpolicingplatform.activities.process.flow.TabClick;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateDynamicView;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedAnswerListBean;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedQuestionListBean;
import hcl.policing.digitalpolicingplatform.models.process.TabFlowDTO;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CreateQuestionDraft {

    /**
     * Create Question basis on the list
     *
     * @param expectedQuestionListBeans
     */
    @SuppressLint("RestrictedApi")
    public static void createQuestions(ProcessCreationActivity act, List<ExpectedQuestionListBean> expectedQuestionListBeans) {

        //act.isNextQuestionOfSection = true;
        act.mExpectedQuestionListBean = null;
        act.captureValue = "";
        try {
            //CustomLinearLayout cLinearLayout = act.findViewById(ProcessCreationActivity.SECTION_COUNT + 1);

            for (int i = ProcessCreationActivity.QUESTION_COUNT; i < expectedQuestionListBeans.size(); i++) {

                String answer = "";
                act.mExpectedQuestionListBean = expectedQuestionListBeans.get(i);
                boolean showQ = false;

                if(!act.mExpectedQuestionListBean.isQuestion_Visibility()) {
                    if(act.idShowQuestionList != null && act.idShowQuestionList.size() > 0) {
                        for (int j = 0; j < act.idShowQuestionList.size(); j++) {
                            if(act.idShowQuestionList.get(i) == act.mExpectedQuestionListBean.getQuestion_Id()) {
                                showQ = true;
                                break;
                            }
                        }
                    }
                    if(!showQ) {
                        ProcessCreationActivity.QUESTION_COUNT++;
                        CreateQuestionDraft.createQuestions(act, act.aExpectedQuestionListBean);
                        break;
                    }
                }
                if (act.mExpectedQuestionListBean.isDisplayAnswerswithQuestion()) {
                    if (!act.mExpectedQuestionListBean.isAnswermultiselect()) {
                        act.isClicked = true;
                        if (!act.currentQuestion.equalsIgnoreCase(act.mExpectedQuestionListBean.getActualQuestion())) {
                            CreateDynamicView.assistantText(act, act.mExpectedQuestionListBean.getActualQuestion(), act.llProcessLayout,
                                    act.mExpectedQuestionListBean.getExpectedAnswerList(), SetClick.onClickAnswer);
                        }
                        act.currentQuestion = act.mExpectedQuestionListBean.getActualQuestion();
                    } else {
                        act.isClickedQuestionOk = true;
                        if (!act.currentQuestion.equalsIgnoreCase(act.mExpectedQuestionListBean.getActualQuestion())) {
                            CreateDynamicView.assistantTextwithMultiple(act, act.mExpectedQuestionListBean.getActualQuestion(), act.mExpectedQuestionListBean.getDialogHeading(), answer, act.llProcessLayout,
                                    act.mExpectedQuestionListBean.getExpectedAnswerList(), SetClick.onClickQuestionOk);
                        }
                        act.currentQuestion = act.mExpectedQuestionListBean.getActualQuestion();
                    }
                } else {
                    if (!act.currentQuestion.equalsIgnoreCase(act.mExpectedQuestionListBean.getActualQuestion())) {
                        CreateDynamicView.assistantText(act, act.mExpectedQuestionListBean.getActualQuestion(), act.llProcessLayout, null, null);
                    }
                    act.currentQuestion = act.mExpectedQuestionListBean.getActualQuestion();
                }

                if (act.isTabProcessEnabled) {
                    answer = ServerRequest.getSubJsonSavedAnswer(act, act.mExpectedQuestionListBean.getActualQuestion());
                } else {
                    answer = ServerRequest.getSavedAnswer(act, act.mPageSectionListBean.getPageSection_Name(), act.mExpectedQuestionListBean.getActualQuestion());
                }

                act.aExpectedAnswerListBeans = act.mExpectedQuestionListBean.getExpectedAnswerList();
                act.aPageSection_detailListBean = act.mExpectedQuestionListBean.getPageSection_detailList();
                act.aSubSectionListBean = act.mExpectedQuestionListBean.getSubSectionList();

                if (act.aExpectedAnswerListBeans != null && act.aExpectedAnswerListBeans.size() > 0) {
                    if (answer != null && !answer.equalsIgnoreCase("")) {
                        CreateAnswerDraft.createAnswers(act, answer);
                        break;
                    } else {
                        break;
                    }
                } else if (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {
                    //call the Pagelist method

                    act.specialLogic = null;
                    act.searchName = act.mExpectedQuestionListBean.getSpecialLogic();
                    act.searchId = act.mExpectedQuestionListBean.getQuestion_Id();
                    act.specialLogic = act.mExpectedQuestionListBean.getSpecialLogic();

                    ProcessCreationActivity.QUESTION_COUNT = ProcessCreationActivity.QUESTION_COUNT + act.mExpectedQuestionListBean.getSkipQuestion();

                    act.answerList = new ArrayList<>();

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
                    String check = null;
                    String checkQues = null;
                    for (int k = 0; k < act.answerList.size(); k++) {
                        if (!act.answerList.get(k).getKey().contains(FieldsNameConstant.ID)
                                && !act.answerList.get(k).getKey().contains(FieldsNameConstant.SYSTEM)
                                && act.answerList.get(k).getValue() != null
                                && !act.answerList.get(k).getValue().equalsIgnoreCase("")) {
                            check = act.answerList.get(k).getValue();
                            checkQues = act.answerList.get(k).getKey();
                            break;
                        }
                    }
                    if (check != null && !check.equalsIgnoreCase("")) {
                        SetRecord.setStorageRecord(act, check, checkQues);
                    } else {
                        PageSection.createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                        break;
                    }
                    ProcessCreationActivity.QUESTION_COUNT++;
                    if (act.aExpectedQuestionListBean != null) {
                        if (ProcessCreationActivity.QUESTION_COUNT != act.aExpectedQuestionListBean.size()) {
                            CreateQuestionDraft.createQuestions(act, act.aExpectedQuestionListBean);
                            break;
                        }
                    }
                } else if (act.aSubSectionListBean != null && act.aSubSectionListBean.size() > 0) {

                    if(act.mExpectedQuestionListBean.getSkipQuestion() != 0) {
                        ProcessCreationActivity.QUESTION_COUNT = ProcessCreationActivity.QUESTION_COUNT + act.mExpectedQuestionListBean.getSkipQuestion();
                    }

                    if(act.isTabFlowEnabled) {
                        if(act.tabFlowPosList != null && act.tabFlowPosList.size() > 0) {
                            act.tabFlowPosList.get(act.tabFlowPosList.size() - 1).setSubSectionList(act.aSubSectionListBean);
                        }
                        ProcessCreationActivity.btnNext.setVisibility(View.GONE);
                        ProcessCreationActivity.btnSubmit.setVisibility(View.GONE);
                        ProcessCreationActivity.btnSave.setVisibility(View.VISIBLE);
                    } else {
                        act.isQuestionFlowEnabled = true;
                        TabClick.setClick(act);
                        if(ProcessCreationActivity.processName.equalsIgnoreCase(act.getResources().getString(R.string.pocket_book))) {
                            ProcessCreationActivity.btnSubmit.setVisibility(View.VISIBLE);
                            ProcessCreationActivity.btnNext.setVisibility(View.GONE);
                        } else {
                            ProcessCreationActivity.btnSubmit.setVisibility(View.GONE);
                            ProcessCreationActivity.btnNext.setVisibility(View.VISIBLE);
                        }
                        ProcessCreationActivity.btnSave.setVisibility(View.GONE);

                        String mainName = BasicMethodsUtil.getInstance().getServerName(act.mPageSectionListBean.getPageSection_Name());
                        act.tabFlowPosList = new ArrayList<>();
                        TabFlowDTO tabFlowDTO = new TabFlowDTO();
                        tabFlowDTO.setArrayName(mainName);
                        tabFlowDTO.setId(0);
                        tabFlowDTO.setPosition(0);
                        tabFlowDTO.setCount(0);
                        tabFlowDTO.setSubSectionList(act.aSubSectionListBean);
                        tabFlowDTO.setExpectedQuestionList(null);
                        tabFlowDTO.setPageSection_detailList(null);
                        act.tabFlowPosList.add(tabFlowDTO);
                    }
                    ProcessCreationActivity.cvEditText.setVisibility(View.GONE);
                    ProcessCreationActivity.btnReturn.setVisibility(View.GONE);
                    ProcessCreationActivity.btnAdd.setVisibility(View.GONE);

                    PrepareSubSection.prepareTabBelowQuestions(act);
                }
                break;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateQuestionDraft.class, "createQuestions");
        }
    }
}
