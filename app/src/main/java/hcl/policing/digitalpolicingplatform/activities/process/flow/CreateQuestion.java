package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateDynamicView;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedQuestionListBean;
import hcl.policing.digitalpolicingplatform.models.process.TabFlowDTO;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CreateQuestion {

    /**
     * Create Question basis on the list
     * @param expectedQuestionListBeans
     */
    @SuppressLint("RestrictedApi")
    public static void createQuestions(ProcessCreationActivity act, List<ExpectedQuestionListBean> expectedQuestionListBeans) {

        act.isNextQuestionOfSection = true;
        act.mExpectedQuestionListBean = null;
        act.mExpectedAnswerListBean = null;
        act.captureValue = "";
        String answer = "";
        try {
            for (int i = act.QUESTION_COUNT; i < expectedQuestionListBeans.size() && act.isNextQuestionOfSection; i++) {

                act.mExpectedQuestionListBean = expectedQuestionListBeans.get(i);
                Log.e("CREATE", "Question : " + act.mExpectedQuestionListBean.getActualQuestion());

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
                        act.isNextQuestion();
                        break;
                    }
                }
                act.tvHeader.setText(act.mExpectedQuestionListBean.getDialogHeading());
                //speakNow(statement + "" + "" + mExpectedQuestionListBean.getActualQuestion());
                // statement removed as discussed with joy. till we solve recursive statement problem.
                if (act.isListening)
                    act.speakNow(act.mExpectedQuestionListBean.getActualQuestion());

                // Call the TextView library for Question
                act.isClickedSpeak = true;
                if (act.mExpectedQuestionListBean.isDisplayAnswerswithQuestion()) {
                    act.isListen = true;
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
                            CreateDynamicView.assistantTextwithMultiple(act, act.mExpectedQuestionListBean.getActualQuestion(), act.mExpectedQuestionListBean.getDialogHeading(),
                                    "", act.llProcessLayout, act.mExpectedQuestionListBean.getExpectedAnswerList(), SetClick.onClickQuestionOk);
                        }
                        act.currentQuestion = act.mExpectedQuestionListBean.getActualQuestion();
                    }
                } else {
                    act.isListen = false;
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

                Log.e("ANSWER", "Question : " + answer);

                if (act.aExpectedAnswerListBeans != null && act.aExpectedAnswerListBeans.size() > 0) {
                    if (answer != null && !answer.equalsIgnoreCase("")) {
                        act.captureValue = answer;
                        for (int j = 0; j < act.aExpectedAnswerListBeans.size(); j++) {
                            if (act.aExpectedAnswerListBeans.get(j).getAnswer().toLowerCase().contains(answer.toLowerCase())) {
                                act.buttonId = act.aExpectedAnswerListBeans.get(j).getButton_Id();
                                break;
                            }
                        }
                        CreateAnswer.createAnswers(act);
                        break;
                    }
                }

                if (act.aExpectedAnswerListBeans == null && (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0)) {
                    //call the Pagelist method
                    act.llAdd.removeAllViews();
                    act.specialLogic = null;
                    act.searchName = act.mExpectedQuestionListBean.getSpecialLogic();
                    act.searchId = act.mExpectedQuestionListBean.getQuestion_Id();
                    act.specialLogic = act.mExpectedQuestionListBean.getSpecialLogic();
                    act.llAdd.removeAllViews();

                    if (act.mExpectedQuestionListBean.getSkipQuestion() != 0) {
                        ProcessCreationActivity.QUESTION_COUNT = ProcessCreationActivity.QUESTION_COUNT + act.mExpectedQuestionListBean.getSkipQuestion();
                    }

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
                            Log.e("CHECK ", "VALUE >>>>> "+check);
                            SetRecord.setStorageRecord(act, check, checkQues);
                            act.isNextQuestion();
                            break;
                        } else {
                            //Log.e("CHECK 2 >> ", "VALUE >>>>> "+check);
                            PageSection.createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                            DialogValue.setValueInDialog(act, act.answerList);
                        }
                    } else {
                        //Log.e("CHECK 2 >> ", "VALUE >>>>> "+check);
                        PageSection.createPageSectionDetails(act, act.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);
                        DialogValue.setValueInDialog(act, act.answerList);
                    }
                } else if (act.aExpectedAnswerListBeans == null && (act.aSubSectionListBean != null && act.aSubSectionListBean.size() > 0)) {

                    if(act.mExpectedQuestionListBean.getSkipQuestion() != 0) {
                        ProcessCreationActivity.QUESTION_COUNT = ProcessCreationActivity.QUESTION_COUNT + act.mExpectedQuestionListBean.getSkipQuestion();
                    }

                    if(act.isTabFlowEnabled) {
                        AddSubJsonList.addList(act);
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
                    }

                    if(act.tabFlowPosList != null && act.tabFlowPosList.size() > 0) {
                        act.tabFlowPosList.get(act.tabFlowPosList.size() - 1).setPosition(act.listPos);
                        act.tabFlowPosList.get(act.tabFlowPosList.size() - 1).setSubSectionList(act.mExpectedQuestionListBean.getSubSectionList());
                    } else {
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
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateQuestion.class, "createQuestions");
        }
    }
}
