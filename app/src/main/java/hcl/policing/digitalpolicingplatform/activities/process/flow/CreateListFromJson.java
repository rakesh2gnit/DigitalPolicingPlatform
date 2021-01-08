package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.models.process.SubSectionListBean;
import hcl.policing.digitalpolicingplatform.models.process.TabFlowDTO;
import hcl.policing.digitalpolicingplatform.models.process.subjsonlist.KeyValueDTO;
import hcl.policing.digitalpolicingplatform.models.process.subjsonlist.KeyValueListDTO;
import hcl.policing.digitalpolicingplatform.models.process.subjsonlist.TabNameDTO;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class CreateListFromJson {

    /**
     * Create list
     *
     * @param act
     */
    public static void createList(ProcessCreationActivity act) {
        try {
            List<SubSectionListBean> aSubSectionListBean = new ArrayList<>();
            act.listTab = new ArrayList<>();
            List<KeyValueListDTO> listKeyValue = new ArrayList<>();

            JSONObject jsonM = act.mainJSON.getJSONObject(act.tabFlowPosList.get(0).getArrayName());
            JSONObject jsonMain = null;

            if (act.tabFlowPosList.size() > 1) {
                for (int i = 1; i < act.tabFlowPosList.size(); i++) {
                    jsonMain = new JSONObject();
                    jsonMain = jsonM.getJSONArray(act.tabFlowPosList.get(i).getArrayName()).getJSONObject(act.tabFlowPosList.get(i).getPosition());
                    aSubSectionListBean = act.tabFlowPosList.get(i).getSubSectionList();
                    jsonM = new JSONObject();
                    jsonM = jsonMain;
                }
            } else {
                jsonMain = new JSONObject();
                jsonMain = jsonM;
                aSubSectionListBean = act.tabFlowPosList.get(0).getSubSectionList();
            }

            if (jsonMain != null && jsonMain.length() > 0) {

                for (Iterator<String> it = jsonMain.keys(); it.hasNext(); ) {
                    String key = it.next();
                    if (jsonMain.get(key) instanceof JSONArray) {
                        TabNameDTO tabNameDTO = new TabNameDTO();
                        tabNameDTO.setServerName(key);
                        tabNameDTO.setName("");
                        tabNameDTO.setCount(0);
                        tabNameDTO.setListKeyValue(null);
                        if(aSubSectionListBean != null && aSubSectionListBean.size() > 0) {
                            for (int i = 0; i < aSubSectionListBean.size(); i++) {
                                String secName = BasicMethodsUtil.getInstance().getServerName(aSubSectionListBean.get(i).getPageSection_Name());

                                if(key.equalsIgnoreCase(secName)) {
                                    tabNameDTO.setVisibility(aSubSectionListBean.get(i).isPageSection_Visibility());
                                    tabNameDTO.setId(aSubSectionListBean.get(i).getPageSection_Id());
                                }
                            }
                        }
                        act.listTab.add(tabNameDTO);
                    }
                }
                for (int i = 0; i < act.listTab.size(); i++) {

                    listKeyValue = new ArrayList<>();

                    JSONArray jsonArray = jsonMain.getJSONArray(act.listTab.get(i).getServerName());

                    for (int j = 0; j < jsonArray.length(); j++) {

                        List<KeyValueDTO> list = new ArrayList<>();

                        JSONObject jsonObject = jsonArray.getJSONObject(j);

                        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                            String key = it.next();
                            if (jsonObject.get(key) instanceof JSONArray) {

                            } else {
                                KeyValueDTO keyValueDTO = new KeyValueDTO();
                                keyValueDTO.setKey(key);
                                keyValueDTO.setValue(jsonObject.getString(key));
                                list.add(keyValueDTO);
                            }
                        }
                        KeyValueListDTO keyValueListDTO = new KeyValueListDTO();
                        keyValueListDTO.setListKeyValue(list);
                        listKeyValue.add(keyValueListDTO);
                    }
                    act.listTab.get(i).setCount(listKeyValue.size() - 1);
                    act.listTab.get(i).setListKeyValue(listKeyValue);
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateListFromJson.class, "createList");
        }
    }

    /**
     * Create list from Add and Back
     *
     * @param act
     */
    public static void createListFromAddNBack(ProcessCreationActivity act) {
        try {
            List<SubSectionListBean> aSubSectionListBean = new ArrayList<>();
            act.listTab = new ArrayList<>();
            List<KeyValueListDTO> listKeyValue = new ArrayList<>();

            ArrayList<TabFlowDTO> tabFlowPosListTemp = new ArrayList<>();
            tabFlowPosListTemp.addAll(act.tabFlowPosList);
            tabFlowPosListTemp.remove(tabFlowPosListTemp.size() - 1);

            JSONObject jsonM = act.mainJSON.getJSONObject(tabFlowPosListTemp.get(0).getArrayName());
            JSONObject jsonMain = null;

            if (tabFlowPosListTemp.size() > 1) {
                for (int i = 1; i < tabFlowPosListTemp.size(); i++) {
                    //String mName = BasicMethodsUtil.getInstance().getServerName(track[i]);
                    jsonMain = new JSONObject();
                    jsonMain = jsonM.getJSONArray(tabFlowPosListTemp.get(i).getArrayName()).getJSONObject(act.tabFlowPosList.get(i).getPosition());
                    aSubSectionListBean = tabFlowPosListTemp.get(i).getSubSectionList();
                    jsonM = new JSONObject();
                    jsonM = jsonMain;
                }
            } else {
                jsonMain = new JSONObject();
                jsonMain = jsonM;
                aSubSectionListBean = tabFlowPosListTemp.get(0).getSubSectionList();
            }
            //jsonMain = act.subJson;

            if (jsonMain != null && jsonMain.length() > 0) {
                for (Iterator<String> it = jsonMain.keys(); it.hasNext(); ) {
                    String key = it.next();
                    if (jsonMain.get(key) instanceof JSONArray) {
                        TabNameDTO tabNameDTO = new TabNameDTO();
                        tabNameDTO.setServerName(key);
                        tabNameDTO.setName("");
                        tabNameDTO.setCount(0);
                        tabNameDTO.setListKeyValue(null);
                        if(aSubSectionListBean != null && aSubSectionListBean.size() > 0) {
                            for (int i = 0; i < aSubSectionListBean.size(); i++) {
                                String secName = BasicMethodsUtil.getInstance().getServerName(aSubSectionListBean.get(i).getPageSection_Name());

                                if(key.equalsIgnoreCase(secName)) {
                                    tabNameDTO.setVisibility(aSubSectionListBean.get(i).isPageSection_Visibility());
                                    tabNameDTO.setId(aSubSectionListBean.get(i).getPageSection_Id());
                                }
                            }
                        }
                        act.listTab.add(tabNameDTO);
                    }
                }
                for (int i = 0; i < act.listTab.size(); i++) {

                    listKeyValue = new ArrayList<>();
                    //List<TabNameDTO> listTab = new ArrayList<>();

                    JSONArray jsonArray = jsonMain.getJSONArray(act.listTab.get(i).getServerName());

                    for (int j = 0; j < jsonArray.length(); j++) {

                        List<KeyValueDTO> list = new ArrayList<>();

                        JSONObject jsonObject = jsonArray.getJSONObject(j);

                        for (Iterator<String> it = jsonObject.keys(); it.hasNext(); ) {
                            String key = it.next();
                            if (jsonObject.get(key) instanceof JSONArray) {

                            } else {
                                KeyValueDTO keyValueDTO = new KeyValueDTO();
                                keyValueDTO.setKey(key);
                                keyValueDTO.setValue(jsonObject.getString(key));
                                list.add(keyValueDTO);
                            }
                        }
                        KeyValueListDTO keyValueListDTO = new KeyValueListDTO();
                        keyValueListDTO.setListKeyValue(list);
                        listKeyValue.add(keyValueListDTO);
                    }
                    act.listTab.get(i).setCount(listKeyValue.size() - 1);
                    act.listTab.get(i).setListKeyValue(listKeyValue);
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreateListFromJson.class, "createList");
        }
    }
}
