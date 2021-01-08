package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import org.json.JSONObject;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.process.draft.CreateQuestionDraft;
import hcl.policing.digitalpolicingplatform.adapters.layoutHelper.CustomListAdapter;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.process.TabFlowDTO;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class TabClick {

    static ProcessCreationActivity activity;

    //used for managing clicks for sub json
    public static void setClick(ProcessCreationActivity act) {

        activity = act;
    }

    //to open section inside tab
    public static OnItemClickListener.OnItemClickCallback onClickAssistantTab = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                ProcessCreationActivity.EditPosition = 0;
                activity.isTabProcessListPage = true;
                activity.isTabProcessAddPage = false;
                if (activity.aSubSectionListBean != null && activity.aSubSectionListBean.size() > 0) {
                    activity.mSubSectionListBean = activity.aSubSectionListBean.get(position);

                    String mainName = BasicMethodsUtil.getInstance().getNormalServerName(activity.tabFlowPosList.get(activity.tabFlowPosList.size()-1).getArrayName());

                    String btnText = activity.getResources().getString(R.string.return_to) + " " + mainName;

                    ProcessCreationActivity.btnReturn.setText(btnText);

                    activity.tabName = activity.listTab.get(position).getServerName();

                    TabFlowDTO tabFlowDTO = new TabFlowDTO();
                    tabFlowDTO.setArrayName(activity.listTab.get(position).getServerName());
                    tabFlowDTO.setId(activity.mSubSectionListBean.getPageSection_Id());
                    tabFlowDTO.setPosition(0);
                    tabFlowDTO.setCount(0);
                    tabFlowDTO.setSubSectionList(activity.mSubSectionListBean.getSubSectionList());
                    tabFlowDTO.setExpectedQuestionList(activity.mSubSectionListBean.getExpectedQuestionList());
                    tabFlowDTO.setPageSection_detailList(activity.mSubSectionListBean.getPageSection_detailList());
                    activity.tabFlowPosList.add(tabFlowDTO);

                    AddSubJsonList.showList(activity);

                    if(activity.listTab.get(position).getCount() == 0) {
                        openAddScreen();
                    }
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), TabClick.class, "onClickAssistantTab");
            }
        }
    };

    /**
     * Call the method fro call back
     */
    @SuppressLint("RestrictedApi")
    public static void clickBack() {
        try {
            activity.isTabProcessListPage = false;
            activity.isTabProcessAddPage = false;
            activity.llProcessLayout.removeAllViews();
            activity.rvProcessLayout.setVisibility(View.GONE);
            if(activity.tabFlowPosList != null && activity.tabFlowPosList.size() > 0) {

                String mainName = BasicMethodsUtil.getInstance().getNormalServerName(activity.tabFlowPosList.get(activity.tabFlowPosList.size()-1).getArrayName());
                String btnText = activity.getResources().getString(R.string.return_to) + " " + mainName;
                ProcessCreationActivity.btnReturn.setText(btnText);

                if (activity.tabFlowPosList.size() > 1) {
                    activity.tabName = activity.tabFlowPosList.get(activity.tabFlowPosList.size()-1).getArrayName();
                    openEditScreen();
                } else {
                    activity.aSubSectionListBean = activity.appSession.getSubSectionList();
                    PrepareSubSection.prepareScreen(activity);
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), TabClick.class, "clickBack");
        }
    }

    /**
     * Call the method openAddscreen
     */
    @SuppressLint("RestrictedApi")
    public static void openAddScreen() {
        try {
            for (int i = 0; i < activity.tabFlowPosList.size(); i++) {
                if (activity.tabFlowPosList.get(i).getArrayName().equalsIgnoreCase(activity.tabName)) {
                    activity.tabFlowPosList.get(i).setPosition(0);
                    break;
                }
            }
            activity.isTabProcessListPage = true;
            activity.isTabProcessAddPage = true;
            ProcessCreationActivity.btnNext.setVisibility(View.GONE);
            ProcessCreationActivity.btnSubmit.setVisibility(View.GONE);
            ProcessCreationActivity.cvEditText.setVisibility(View.VISIBLE);
            ProcessCreationActivity.btnReturn.setVisibility(View.GONE);
            ProcessCreationActivity.btnAdd.setVisibility(View.GONE);

            ProcessCreationActivity.QUESTION_COUNT = 0;
            activity.currentQuestion = "";
            activity.llProcessLayout.removeAllViews();
            activity.rvProcessLayout.setVisibility(View.GONE);

            activity.aExpectedQuestionListBean = activity.mSubSectionListBean.getExpectedQuestionList();
            activity.aPageSection_detailListBean = activity.mSubSectionListBean.getPageSection_detailList();
            activity.aSubSectionListBean = activity.mSubSectionListBean.getSubSectionList();

            activity.appSession.setExpectedQuestionList(null);
            activity.appSession.setExpectedQuestionList(activity.aExpectedQuestionListBean);

            if (activity.aExpectedQuestionListBean != null) {
                activity.subDetailJson = new JSONObject();
                activity.subDetailJson = ServerRequest.createSubJsonDetail(activity);

                CreateQuestion.createQuestions(activity, activity.aExpectedQuestionListBean);

            } else if (activity.aPageSection_detailListBean != null && activity.aPageSection_detailListBean.size() > 0) {
                //call the Pagelist method
                activity.subDetailJson = new JSONObject();
                activity.subDetailJson = ServerRequest.createSubJsonDetail(activity);

                activity.QUESTION_COUNT = 0;
                activity.captureValue = "";
                activity.mExpectedQuestionListBean = null;
                activity.pageSectionId = activity.mSubSectionListBean.getPageSection_Id() * 1000;
                activity.answerList = new ArrayList<>();
                for (int j = 0; j < activity.aPageSection_detailListBean.size(); j++) {
                    AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                    answerValueDTO.setKey(activity.aPageSection_detailListBean.get(j).getField_Name());
                    answerValueDTO.setValue("");
                    answerValueDTO.setMendatry(activity.aPageSection_detailListBean.get(j).isField_Mendatry());
                    activity.answerList.add(answerValueDTO);
                }
                activity.llAdd.removeAllViews();
                PageSection.createPageSectionDetails(activity, activity.aPageSection_detailListBean, GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);

            } else if (activity.aSubSectionListBean != null && activity.aSubSectionListBean.size() > 0) {
                activity.isTabProcessListPage = false;
                activity.isTabProcessAddPage = false;
                activity.isTabProcessEnabled = true;
                activity.subDetailJson = null;
                ProcessCreationActivity.btnNext.setVisibility(View.GONE);
                ProcessCreationActivity.btnSubmit.setVisibility(View.GONE);
                ProcessCreationActivity.cvEditText.setVisibility(View.GONE);
                ProcessCreationActivity.btnSave.setVisibility(View.GONE);
                ProcessCreationActivity.btnReturn.setVisibility(View.VISIBLE);
                ProcessCreationActivity.btnAdd.setVisibility(View.GONE);

                PrepareSubSection.prepareTabBelowQuestions(activity);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), TabClick.class, "onClickAssistantTabAdd");
        }
    }

    // to move from add screen to list page
    @SuppressLint("RestrictedApi")
    public static void clickCancel(ProcessCreationActivity act) {
        try {
            act.isTabProcessListPage = true;
            act.isTabProcessAddPage = false;
            act.llProcessLayout.removeAllViews();
            act.rvProcessLayout.setVisibility(View.GONE);

            ProcessCreationActivity.btnNext.setVisibility(View.GONE);
            ProcessCreationActivity.btnSubmit.setVisibility(View.GONE);
            ProcessCreationActivity.cvEditText.setVisibility(View.GONE);
            //ProcessCreationActivity.btnSave.setVisibility(View.GONE);
            ProcessCreationActivity.btnReturn.setVisibility(View.VISIBLE);
            ProcessCreationActivity.btnAdd.setVisibility(View.VISIBLE);

            if(act.tabFlowPosList.get(act.tabFlowPosList.size()-1).getPosition() != 0) {
                act.isNextSectionTabProcess();
            }

            for (int i = 0; i < act.listTab.size(); i++) {
                //String mainName = BasicMethodsUtil.getInstance().getServerName(act.mSubSectionListBean.getPageSection_Name());
                if (act.listTab.get(i).getServerName().equalsIgnoreCase(act.tabFlowPosList.get(act.tabFlowPosList.size() - 1).getArrayName())) {
                    if (act.listTab.get(i).getCount() == 0) {
                        act.isTabProcessListPage = false;
                        Log.e("Back ", "NAME " + act.listTab.get(i).getServerName());
                        activity.tabFlowPosList.remove(activity.tabFlowPosList.size()-1);
                        clickBack();
                        break;
                    }
                }
            }

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), TabClick.class, "clickCancel");
        }
    }

    public static OnItemClickListener.OnItemClickCallback onClickItemDelete = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                for (int i = 0; i < activity.tabFlowPosList.size(); i++) {
                    if (activity.tabFlowPosList.get(i).getArrayName().equalsIgnoreCase(activity.tabName)) {
                        activity.tabFlowPosList.get(i).setPosition(position + 1);
                        break;
                    }
                }
                ServerRequest.deleteSubJsonDetail(activity);
                if (activity.listKeyValue != null && activity.listKeyValue.size() > 0) {
                    activity.listKeyValue.remove(position);

                    CustomListAdapter customListAdapter = new CustomListAdapter(activity, activity.listKeyValue, onClickItemEdit,
                            onClickItemDelete, onClickItemViewMore);
                    activity.rvProcessLayout.setAdapter(customListAdapter);
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), TabClick.class, "onClickItemDelete");
            }
        }
    };

    public static OnItemClickListener.OnItemClickCallback onClickItemEdit = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                for (int i = 0; i < activity.tabFlowPosList.size(); i++) {
                    if (activity.tabFlowPosList.get(i).getArrayName().equalsIgnoreCase(activity.tabName)) {
                        activity.tabFlowPosList.get(i).setPosition(position + 1);
                        break;
                    }
                }
                activity.isTabProcessListPage = true;
                activity.isTabProcessAddPage = true;
                openEditScreen();
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), TabClick.class, "onClickItemEdit");
            }
        }
    };

    /**
     * Call the method openEdit Screen
     */
    @SuppressLint("RestrictedApi")
    private static void openEditScreen() {
        try {
            ProcessCreationActivity.btnNext.setVisibility(View.GONE);
            ProcessCreationActivity.btnSubmit.setVisibility(View.GONE);
            //ProcessCreationActivity.btnSave.setVisibility(View.GONE);
            ProcessCreationActivity.cvEditText.setVisibility(View.VISIBLE);
            ProcessCreationActivity.btnReturn.setVisibility(View.GONE);
            ProcessCreationActivity.btnAdd.setVisibility(View.GONE);

            ProcessCreationActivity.QUESTION_COUNT = 0;
            activity.currentQuestion = "";
            activity.llProcessLayout.removeAllViews();
            activity.rvProcessLayout.setVisibility(View.GONE);

            activity.aExpectedQuestionListBean = activity.tabFlowPosList.get(activity.tabFlowPosList.size()-1).getExpectedQuestionList();
            activity.aPageSection_detailListBean = activity.tabFlowPosList.get(activity.tabFlowPosList.size()-1).getPageSection_detailList();
            activity.aSubSectionListBean = activity.tabFlowPosList.get(activity.tabFlowPosList.size()-1).getSubSectionList();

            activity.appSession.setExpectedQuestionList(null);
            activity.appSession.setExpectedQuestionList(activity.aExpectedQuestionListBean);

            if (activity.aExpectedQuestionListBean != null) {
                activity.subDetailJson = new JSONObject();
                activity.subDetailJson = ServerRequest.createSubJsonDetailEdit(activity);

                CreateQuestionDraft.createQuestions(activity, activity.aExpectedQuestionListBean);

            } else if (activity.aPageSection_detailListBean != null && activity.aPageSection_detailListBean.size() > 0) {
                //call the Pagelist method
                activity.subDetailJson = new JSONObject();
                activity.subDetailJson = ServerRequest.createSubJsonDetailEdit(activity);

                activity.QUESTION_COUNT = 0;
                activity.captureValue = "";
                activity.mExpectedQuestionListBean = null;
                activity.pageSectionId = activity.mSubSectionListBean.getPageSection_Id() * 1000;
                activity.answerList = new ArrayList<>();
                for (int j = 0; j < activity.aPageSection_detailListBean.size(); j++) {
                    AnswerValueDTO answerValueDTO = new AnswerValueDTO();
                    answerValueDTO.setKey(activity.aPageSection_detailListBean.get(j).getField_Name());
                    answerValueDTO.setValue("");
                    answerValueDTO.setMendatry(activity.aPageSection_detailListBean.get(j).isField_Mendatry());
                    activity.answerList.add(answerValueDTO);
                }
                activity.llAdd.removeAllViews();
                PageSection.createPageSectionDetails(activity, activity.mSubSectionListBean.getPageSection_detailList(), GenericConstant.ATTRIBUTE_INITIALIZE_COUNT);

            } else if (activity.aSubSectionListBean != null && activity.aSubSectionListBean.size() > 0) {
                activity.isTabProcessListPage = false;
                activity.isTabProcessAddPage = false;
                activity.isTabProcessEnabled = true;

                activity.subDetailJson = null;

                ProcessCreationActivity.btnNext.setVisibility(View.GONE);
                ProcessCreationActivity.btnSubmit.setVisibility(View.GONE);
                ProcessCreationActivity.cvEditText.setVisibility(View.GONE);
                ProcessCreationActivity.btnSave.setVisibility(View.GONE);
                ProcessCreationActivity.btnReturn.setVisibility(View.VISIBLE);
                ProcessCreationActivity.btnAdd.setVisibility(View.GONE);

                PrepareSubSection.prepareTabBelowQuestions(activity);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), TabClick.class, "openEditScreen");
        }
    }

    public static OnItemClickListener.OnItemClickCallback onClickItemViewMore = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), TabClick.class, "onClickItemEdit");
            }
        }
    };
}
