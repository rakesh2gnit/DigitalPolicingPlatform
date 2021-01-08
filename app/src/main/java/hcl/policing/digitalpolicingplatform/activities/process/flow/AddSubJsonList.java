package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.annotation.SuppressLint;
import android.util.Log;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.adapters.layoutHelper.CustomListAdapter;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class AddSubJsonList {

    @SuppressLint("RestrictedApi")
    public static void addList(ProcessCreationActivity act) {
        try {
            ProcessCreationActivity.btnNext.setVisibility(View.GONE);
            ProcessCreationActivity.btnSubmit.setVisibility(View.GONE);
            ProcessCreationActivity.cvEditText.setVisibility(View.GONE);
            //ProcessCreationActivity.btnSave.setVisibility(View.GONE);
            ProcessCreationActivity.btnReturn.setVisibility(View.VISIBLE);
            ProcessCreationActivity.btnAdd.setVisibility(View.VISIBLE);

            if(act.subDetailJson != null && act.subDetailJson.length() > 0) {
                Log.e("SUB JSON DETAIL ", "ADD JSON >> " + act.subDetailJson.toString());

                JSONObject jsonM = act.mainJSON.getJSONObject(act.tabFlowPosList.get(0).getArrayName());
                JSONArray jsonMain = null;

                if(act.tabFlowPosList.size() == 2) {
                    jsonMain = new JSONArray();
                    jsonMain = jsonM.getJSONArray(act.tabFlowPosList.get(1).getArrayName());

                    if (act.tabFlowPosList.get(1).getPosition() != 0) {

                        jsonMain.put(act.tabFlowPosList.get(1).getPosition(), act.subDetailJson);

                    } else {
                        jsonMain.put(act.subDetailJson);
                        act.listPos = jsonMain.length()-1;
                    }
                } else if(act.tabFlowPosList.size() == 3) {
                    jsonMain = new JSONArray();
                    jsonMain = jsonM.getJSONArray(act.tabFlowPosList.get(1).getArrayName()).
                            getJSONObject(act.tabFlowPosList.get(1).getPosition()).getJSONArray(act.tabFlowPosList.get(2).getArrayName());
                    if (act.tabFlowPosList.get(2).getPosition() != 0) {
                        jsonMain.put(act.tabFlowPosList.get(2).getPosition(), act.subDetailJson);
                    } else {
                        jsonMain.put(act.subDetailJson);
                        act.listPos = jsonMain.length()-1;
                    }
                } else if (act.tabFlowPosList.size() == 4) {
                    jsonMain = new JSONArray();
                    jsonMain = jsonM.getJSONArray(act.tabFlowPosList.get(1).getArrayName()).
                            getJSONObject(act.tabFlowPosList.get(1).getPosition()).getJSONArray(act.tabFlowPosList.get(2).getArrayName()).
                            getJSONObject(act.tabFlowPosList.get(2).getPosition()).getJSONArray(act.tabFlowPosList.get(3).getArrayName());
                    if (act.tabFlowPosList.get(3).getPosition() != 0) {
                        jsonMain.put(act.tabFlowPosList.get(3).getPosition(), act.subDetailJson);
                    } else {
                        jsonMain.put(act.subDetailJson);
                        act.listPos = jsonMain.length()-1;
                    }
                } else if (act.tabFlowPosList.size() == 5) {
                    jsonMain = new JSONArray();
                    jsonMain = jsonM.getJSONArray(act.tabFlowPosList.get(1).getArrayName()).
                            getJSONObject(act.tabFlowPosList.get(1).getPosition()).getJSONArray(act.tabFlowPosList.get(2).getArrayName()).
                            getJSONObject(act.tabFlowPosList.get(2).getPosition()).getJSONArray(act.tabFlowPosList.get(3).getArrayName()).
                            getJSONObject(act.tabFlowPosList.get(3).getPosition()).getJSONArray(act.tabFlowPosList.get(4).getArrayName());
                    if (act.tabFlowPosList.get(4).getPosition() != 0) {
                        jsonMain.put(act.tabFlowPosList.get(4).getPosition(), act.subDetailJson);
                    } else {
                        jsonMain.put(act.subDetailJson);
                        act.listPos = jsonMain.length()-1;
                    }
                }
                //CreateListFromJson.createAddedList(act, act.subDetailJson);

                act.saveDraft();
                //Utilities.getInstance(act).writeFile(act.mainJSON.toString(), act.fileName, ProcessCreationActivity.directoryDraft);
                act.subDetailJson = null;
            }

            Log.e("Main JSON ", " >>>>> " + act.mainJSON.toString());
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), AddSubJsonList.class, "addList");
        }
    }

    @SuppressLint("RestrictedApi")
    public static void showList(ProcessCreationActivity act) {
        try {
            act.isTabProcessAddPage = false;
            act.isTabProcessListPage = true;
            ProcessCreationActivity.btnNext.setVisibility(View.GONE);
            ProcessCreationActivity.btnSubmit.setVisibility(View.GONE);
            ProcessCreationActivity.cvEditText.setVisibility(View.GONE);
            ProcessCreationActivity.btnReturn.setVisibility(View.VISIBLE);
            ProcessCreationActivity.btnAdd.setVisibility(View.VISIBLE);

            for (int i = 0; i < act.listTab.size(); i++) {
                if (act.listTab.get(i).getServerName().equalsIgnoreCase(act.tabFlowPosList.get(act.tabFlowPosList.size() - 1).getArrayName())) {
                    act.listKeyValue = new ArrayList<>();
                    act.listKeyValue.addAll(act.listTab.get(i).getListKeyValue());
                    act.listKeyValue.remove(0);
                    break;
                }
            }

            if (act.listKeyValue != null && act.listKeyValue.size() > 0) {
                CustomListAdapter customListAdapter = new CustomListAdapter(act, act.listKeyValue, TabClick.onClickItemEdit,
                        TabClick.onClickItemDelete, TabClick.onClickItemViewMore);
                act.llProcessLayout.removeAllViews();
                act.rvProcessLayout.setVisibility(View.GONE);
                act.rvProcessLayout.setLayoutManager(new LinearLayoutManager(act));
                act.rvProcessLayout.setVisibility(View.VISIBLE);
                act.rvProcessLayout.setAdapter(customListAdapter);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), AddSubJsonList.class, "showList");
        }
    }
}
