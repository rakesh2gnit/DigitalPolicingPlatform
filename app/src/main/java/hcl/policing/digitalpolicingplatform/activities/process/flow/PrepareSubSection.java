package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;

import org.json.JSONObject;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.adapters.layoutHelper.CustomTabSubSectionAdapter;
import hcl.policing.digitalpolicingplatform.models.process.TabFlowDTO;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class PrepareSubSection {

    @SuppressLint("RestrictedApi")
    /**
     * Prepare the screen from ProcessCreation Activity
     */
    public static void prepareScreen(ProcessCreationActivity act) {
        try {
            if (act.aSubSectionListBean != null && act.aSubSectionListBean.size() > 0) {
                act.isTabProcessEnabled = true;
                act.llProcessLayout.removeAllViews();
                act.rvProcessLayout.setVisibility(View.GONE);

                for (int i = 0; i < act.idShowSubSectionList.size(); i++) {
                    Log.e("SHOW ID", " >>>>> "+act.idShowSubSectionList.get(i));
                }

                if(ProcessCreationActivity.processName.equalsIgnoreCase(act.getResources().getString(R.string.pocket_book))) {
                    ProcessCreationActivity.btnSubmit.setVisibility(View.VISIBLE);
                    ProcessCreationActivity.btnNext.setVisibility(View.GONE);
                } else {
                    ProcessCreationActivity.btnSubmit.setVisibility(View.GONE);
                    ProcessCreationActivity.btnNext.setVisibility(View.VISIBLE);
                }
                ProcessCreationActivity.cvEditText.setVisibility(View.GONE);
                //ProcessCreationActivity.btnSave.setVisibility(View.GONE);
                ProcessCreationActivity.btnReturn.setVisibility(View.GONE);
                ProcessCreationActivity.btnAdd.setVisibility(View.GONE);

                String btnText = act.getResources().getString(R.string.return_to) + " " + act.mPageSectionListBean.getPageSection_Name();

                ProcessCreationActivity.btnReturn.setText(btnText);

                act.appSession.setSubSectionList(null);
                act.appSession.setSubSectionList(act.aSubSectionListBean);

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

                CreateListFromJson.createList(act);

                TabClick.setClick(act);
                act.customTabSubSectionAdapter = new CustomTabSubSectionAdapter(act, act.listTab, TabClick.onClickAssistantTab);
                act.rvProcessLayout.setLayoutManager(new GridLayoutManager(act, 2));
                act.rvProcessLayout.setVisibility(View.VISIBLE);
                act.rvProcessLayout.setAdapter(act.customTabSubSectionAdapter);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), PrepareSubSection.class, "prepareScreen");
        }
    }

    /**
     * Prepare the Question with Tab screen
     * @param act
     */
    @SuppressLint("RestrictedApi")
    public static void prepareTabBelowQuestions(ProcessCreationActivity act) {
        try {
            if (act.aSubSectionListBean != null && act.aSubSectionListBean.size() > 0) {
                act.isTabProcessEnabled = true;
                act.rvProcessLayout.setVisibility(View.GONE);

                CreateListFromJson.createList(act);

                act.customTabSubSectionAdapter = new CustomTabSubSectionAdapter(act, act.listTab, TabClick.onClickAssistantTab);
                act.rvProcessLayout.setLayoutManager(new GridLayoutManager(act, 2));
                act.rvProcessLayout.setVisibility(View.VISIBLE);
                act.rvProcessLayout.setAdapter(act.customTabSubSectionAdapter);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), PrepareSubSection.class, "prepareScreen");
        }
    }
}
