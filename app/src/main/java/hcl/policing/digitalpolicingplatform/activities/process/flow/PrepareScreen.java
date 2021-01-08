package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.util.Log;
import android.view.View;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.draft.CreateQuestionDraft;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateDynamicView;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class PrepareScreen {

    /**
     * Prepare the tab click screen method
     *
     * @param act
     * @param id
     */
    public static void prepareMainScreen(ProcessCreationActivity act, int id) {
        try {
            act.mPageSectionListBean = act.aPageSectionListBean.get(id);

            act.llProcessLayout.removeAllViews();
            act.rvProcessLayout.setVisibility(View.GONE);
            ProcessCreationActivity.QUESTION_COUNT = 0;
            act.pageSectionId = 0;

            act.aExpectedQuestionListBean = act.aPageSectionListBean.get(id).getExpectedQuestionList();
            act.aPageSection_detailListBean = act.aPageSectionListBean.get(id).getPageSection_detailList();

            act.aSubSectionListBean = act.aPageSectionListBean.get(id).getSubSectionList();

            if (act.aSubSectionListBean != null && act.aSubSectionListBean.size() > 0) {

                act.isTabFlowEnabled = true;

                if(ProcessCreationActivity.processName.equalsIgnoreCase(act.getResources().getString(R.string.pocket_book))) {
                    ProcessCreationActivity.btnSubmit.setVisibility(View.VISIBLE);
                    ProcessCreationActivity.btnNext.setVisibility(View.GONE);
                } else {
                    ProcessCreationActivity.btnSubmit.setVisibility(View.GONE);
                    ProcessCreationActivity.btnNext.setVisibility(View.VISIBLE);
                }
                ProcessCreationActivity.cvEditText.setVisibility(View.GONE);

                PrepareSubSection.prepareScreen(act);
            } else {
                act.isTabFlowEnabled = false;
                ProcessCreationActivity.btnNext.setVisibility(View.GONE);
                ProcessCreationActivity.btnSubmit.setVisibility(View.GONE);
                ProcessCreationActivity.cvEditText.setVisibility(View.VISIBLE);

                if (act.aExpectedQuestionListBean != null && act.aExpectedQuestionListBean.size() > 0) {

                    //Call the create Question list method
                    act.appSession.setExpectedQuestionList(null);
                    act.appSession.setExpectedQuestionList(act.aExpectedQuestionListBean);
                    act.pageSectionId = 0;
                    CreateQuestionDraft.createQuestions(act, act.aExpectedQuestionListBean);

                } else if (act.aPageSection_detailListBean != null && act.aPageSection_detailListBean.size() > 0) {

                    //call the Pagelist method
                    act.captureValue = "";
                    act.mExpectedQuestionListBean = null;
                    act.pageSectionId = act.aPageSectionListBean.get(id).getPageSection_Id() * 1000;

                    act.answerList = new ArrayList<>();
                    for (int j = 0; j < act.aPageSection_detailListBean.size(); j++) {
                        AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                        answerValueDTO.setKey(act.aPageSection_detailListBean.get(j).getField_Name());
                        answerValueDTO.setValue(ServerRequest.getSavedAnswer(act, act.mPageSectionListBean.getPageSection_Name(), act.aPageSection_detailListBean.get(j).getField_Name()));
                        answerValueDTO.setId(act.aPageSection_detailListBean.get(j).getField_Id());
                        answerValueDTO.setSelectLogic(act.aPageSection_detailListBean.get(j).getSelect_Logic());
                        answerValueDTO.setMendatry(act.aPageSection_detailListBean.get(j).isField_Mendatry());
                        act.answerList.add(answerValueDTO);
                        Log.e("FIELD Section ", " NAME >>>>> " + act.aPageSection_detailListBean.get(j).getField_Name());
                    }
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
                        SetRecord.setStorageRecord(act, check, "");
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), PrepareScreen.class, "prepareMainScreen");
        }
    }
}
