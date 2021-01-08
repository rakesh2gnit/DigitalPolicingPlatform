package hcl.policing.digitalpolicingplatform.activities.process.flow;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomLinearLayout;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateDynamicView;
import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedAnswerListBean;
import hcl.policing.digitalpolicingplatform.models.process.ExpectedQuestionListBean;
import hcl.policing.digitalpolicingplatform.models.process.PageSectionListBean;
import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;
import hcl.policing.digitalpolicingplatform.models.process.SubSectionListBean;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class ServerRequest {

    /**
     * Create the server request
     *
     * @param act
     * @return
     */
    public static JSONObject createServerRequest(ProcessCreationActivity act) {
        JSONObject mainJSON = new JSONObject();
        try {
            if (act.aPageSectionListBean != null && act.aPageSectionListBean.size() > 0) {
                List<PageSectionListBean> aPageSectionListBean = act.aPageSectionListBean;

                for (int i = 0; i < aPageSectionListBean.size(); i++) {
                    List<ExpectedQuestionListBean> aExpectedQuestionListBean = aPageSectionListBean.get(i).getExpectedQuestionList();
                    List<PageSection_detailListBean> aPageSection_detailListBean = aPageSectionListBean.get(i).getPageSection_detailList();
                    List<SubSectionListBean> aSubSectionListBean = aPageSectionListBean.get(i).getSubSectionList();

                    JSONObject sectionJSON = new JSONObject();
                    String sectionName = BasicMethodsUtil.getInstance().getServerName(aPageSectionListBean.get(i).getPageSection_Name());

                    sectionJSON.put(GenericConstant.SECTION_COMPLETE, "false");
                    sectionJSON.put(GenericConstant.SECTION_VISIBLE, "true");

                    if (aExpectedQuestionListBean != null && aExpectedQuestionListBean.size() > 0) {
                        for (int j = 0; j < aExpectedQuestionListBean.size(); j++) {
                            List<SubSectionListBean> aSubSectionListBean1 = aExpectedQuestionListBean.get(j).getSubSectionList();
                            List<ExpectedAnswerListBean> aExpectedAnswerListBeans = aExpectedQuestionListBean.get(j).getExpectedAnswerList();
                            List<PageSection_detailListBean> aPageSection_detailListBean1 = aExpectedQuestionListBean.get(j).getPageSection_detailList();

                            String fieldName = BasicMethodsUtil.getInstance().getServerName(aExpectedQuestionListBean.get(j).getActualQuestion());
                            sectionJSON.put(fieldName, "");

                            if (aExpectedAnswerListBeans != null && aExpectedAnswerListBeans.size() > 0) {
                                for (int k = 0; k < aExpectedAnswerListBeans.size(); k++) {
                                    List<PageSection_detailListBean> aPageSection_detailListBean2 = aExpectedAnswerListBeans.get(k).getPageSection_detailList();

                                    if (aPageSection_detailListBean2 != null && aPageSection_detailListBean2.size() > 0) {
                                        for (int l = 0; l < aPageSection_detailListBean2.size(); l++) {
                                            String fieldNameAnswer = BasicMethodsUtil.getInstance().getServerName(aPageSection_detailListBean2.get(l).getField_Name());
                                            if (CheckArray.isArray(fieldNameAnswer)) {
                                                JSONArray jsonArray = new JSONArray();
                                                sectionJSON.put(fieldNameAnswer, jsonArray);
                                            } else {
                                                sectionJSON.put(fieldNameAnswer, "");
                                            }
                                        }
                                    }
                                }
                            } else if (aPageSection_detailListBean1 != null && aPageSection_detailListBean1.size() > 0) {
                                for (int k = 0; k < aPageSection_detailListBean1.size(); k++) {
                                    String fieldNameSection = BasicMethodsUtil.getInstance().getServerName(aPageSection_detailListBean1.get(k).getField_Name());
                                    if (CheckArray.isArray(fieldNameSection)) {
                                        JSONArray jsonArray = new JSONArray();
                                        sectionJSON.put(fieldNameSection, jsonArray);
                                    } else {
                                        sectionJSON.put(fieldNameSection, "");
                                    }
                                }
                            } else if (aSubSectionListBean1 != null && aSubSectionListBean1.size() > 0) {
                                createSubSection(act, aSubSectionListBean1, sectionJSON);
                            }
                        }
                    } else if (aPageSection_detailListBean != null && aPageSection_detailListBean.size() > 0) {
                        for (int j = 0; j < aPageSection_detailListBean.size(); j++) {
                            String fieldName = BasicMethodsUtil.getInstance().getServerName(aPageSection_detailListBean.get(j).getField_Name());
                            if (CheckArray.isArray(fieldName)) {
                                JSONArray jsonArray = new JSONArray();
                                sectionJSON.put(fieldName, jsonArray);
                            } else {
                                sectionJSON.put(fieldName, "");
                            }
                        }
                    } else if (aSubSectionListBean != null && aSubSectionListBean.size() > 0) {
                        createSubSection(act, aSubSectionListBean, sectionJSON);
                        //sectionJSON.put(sectionName, createSubSection(act, aSubSectionListBean, ));

                    }
                    mainJSON.put(sectionName, sectionJSON);
                }
            }
            Log.e("SERVER ", "REQUEST >> " + mainJSON.toString());
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequest.class, "createServerRequest");
        }
        return mainJSON;
    }

    /**
     * Set the server request based on the follow parameters
     *
     * @param act
     * @param section
     * @param question
     * @param answer
     * @param list
     */
    public static void setServerRequest(ProcessCreationActivity act, String section, String question, String answer, ArrayList<PhotoListModel> list) {
        try {
            String questionEdit = BasicMethodsUtil.getInstance().getServerName(question);

            if (act.aPageSectionListBean != null && act.aPageSectionListBean.size() > 0) {
                List<PageSectionListBean> aPageSectionListBean = act.aPageSectionListBean;

                for (int i = 0; i < aPageSectionListBean.size(); i++) {
                    if (aPageSectionListBean.get(i).getPageSection_Name().equalsIgnoreCase(section)) {

                        String sectionName = BasicMethodsUtil.getInstance().getServerName(aPageSectionListBean.get(i).getPageSection_Name());
                        JSONObject jsonSection = act.mainJSON.getJSONObject(sectionName);
                        for (Iterator<String> it = jsonSection.keys(); it.hasNext(); ) {
                            String key = it.next();
                            //if (jsonSection.get(key) instanceof JSONObject) {
                            //}
                            if (key.equalsIgnoreCase(questionEdit)) {
                                if (CheckArray.isArray(key)) {
                                    if (list != null && list.size() > 0) {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int j = 0; j < list.size(); j++) {
                                            JSONObject jsonObject = new JSONObject();
                                            jsonObject.put(FieldsNameConstant.Item_Name, list.get(j).getPhoto());
                                            jsonObject.put(FieldsNameConstant.Item_Description, list.get(j).getDescription());
                                            jsonObject.put(FieldsNameConstant.Item_Path, list.get(j).getPath());
                                            jsonObject.put(FieldsNameConstant.Item_Status, list.get(j).getStatus());
                                            jsonArray.put(jsonObject);
                                        }
                                        jsonSection.put(key, jsonArray);
                                    } else {
                                        act.imageListAct = new ArrayList<>();
                                        act.signatureListAct = new ArrayList<>();
                                        act.sketchListAct = new ArrayList<>();
                                        act.docListAct = new ArrayList<>();
                                        act.audioListAct = new ArrayList<>();
                                        jsonSection.put(key, new ArrayList<>());
                                    }
                                } else {
                                    jsonSection.put(key, answer);
                                }
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequest.class, "setServerRequest");
        }
    }

    /**
     * Get the saved answer values
     *
     * @param act
     * @param section
     * @param question
     * @return
     */
    public static String getSavedAnswer(ProcessCreationActivity act, String section, String question) {
        String answer = "";
        try {
            String questionDraft = BasicMethodsUtil.getInstance().getServerName(question);

            if (act.aPageSectionListBean != null && act.aPageSectionListBean.size() > 0) {
                List<PageSectionListBean> aPageSectionListBean = act.aPageSectionListBean;

                for (int i = 0; i < aPageSectionListBean.size(); i++) {
                    if (aPageSectionListBean.get(i).getPageSection_Name().equalsIgnoreCase(section)) {

                        String sectionName = BasicMethodsUtil.getInstance().getServerName(aPageSectionListBean.get(i).getPageSection_Name());
                        JSONObject jsonSection = act.mainJSON.getJSONObject(sectionName);
                        for (Iterator<String> it = jsonSection.keys(); it.hasNext(); ) {
                            String key = it.next();
                            if (key.equalsIgnoreCase(questionDraft)) {
                                answer = getAnswer(act, key, jsonSection);
                                break;
                            }
                        }
                        break;
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequest.class, "getSavedAnswer");
        }
        return answer;
    }


    /**
     * method for create sub section
     *
     * @param act
     * @param aSubJsonLisBean
     * @param mainJSON
     */
    public static void createSubSection(ProcessCreationActivity act, List<SubSectionListBean> aSubJsonLisBean, JSONObject mainJSON) {

        //JSONObject mainJSON = new JSONObject();
        try {
            for (int i = 0; i < aSubJsonLisBean.size(); i++) {
                JSONArray sectionArray = new JSONArray();
                JSONObject sectionJSON = new JSONObject();
                String sectionName = BasicMethodsUtil.getInstance().getServerName(aSubJsonLisBean.get(i).getPageSection_Name());

                List<ExpectedQuestionListBean> aExpectedQuestionListBean = aSubJsonLisBean.get(i).getExpectedQuestionList();
                List<PageSection_detailListBean> aPageSection_detailListBeanMain = aSubJsonLisBean.get(i).getPageSection_detailList();
                List<SubSectionListBean> aSubSectionListBeanMain = aSubJsonLisBean.get(i).getSubSectionList();

                if (aExpectedQuestionListBean != null && aExpectedQuestionListBean.size() > 0) {

                    for (int k = 0; k < aExpectedQuestionListBean.size(); k++) {

                        List<ExpectedAnswerListBean> aExpectedAnswerListBeans = aExpectedQuestionListBean.get(k).getExpectedAnswerList();
                        List<PageSection_detailListBean> aPageSection_detailListBean1 = aExpectedQuestionListBean.get(k).getPageSection_detailList();
                        List<SubSectionListBean> aSubSectionListBean1 = aExpectedQuestionListBean.get(k).getSubSectionList();

                        String fieldName = BasicMethodsUtil.getInstance().getServerName(aExpectedQuestionListBean.get(k).getActualQuestion());
                        sectionJSON.put(fieldName, "");

                        if (aExpectedAnswerListBeans != null && aExpectedAnswerListBeans.size() > 0) {

                            for (int l = 0; l < aExpectedAnswerListBeans.size(); l++) {

                                List<PageSection_detailListBean> aPageSection_detailListBean2 = aExpectedAnswerListBeans.get(l).getPageSection_detailList();

                                if (aPageSection_detailListBean2 != null && aPageSection_detailListBean2.size() > 0) {
                                    for (int m = 0; m < aPageSection_detailListBean2.size(); m++) {
                                        String fieldNameAnswer = BasicMethodsUtil.getInstance().getServerName(aPageSection_detailListBean2.get(m).getField_Name());
                                        if (CheckArray.isArray(fieldNameAnswer)) {
                                            JSONArray jsonArray = new JSONArray();
                                            sectionJSON.put(fieldNameAnswer, jsonArray);
                                        } else {
                                            sectionJSON.put(fieldNameAnswer, "");
                                        }
                                    }
                                }
                            }
                        } else if (aPageSection_detailListBean1 != null && aPageSection_detailListBean1.size() > 0) {
                            for (int l = 0; l < aPageSection_detailListBean1.size(); l++) {
                                String fieldNameSection = BasicMethodsUtil.getInstance().getServerName(aPageSection_detailListBean1.get(l).getField_Name());
                                if (CheckArray.isArray(fieldNameSection)) {
                                    JSONArray jsonArray = new JSONArray();
                                    sectionJSON.put(fieldNameSection, jsonArray);
                                } else {
                                    sectionJSON.put(fieldNameSection, "");
                                }
                            }
                        } else if (aSubSectionListBean1 != null && aSubSectionListBean1.size() > 0) {
                            createSubSection(act, aSubSectionListBean1, sectionJSON);
                        }
                    }

                } else if (aPageSection_detailListBeanMain != null && aPageSection_detailListBeanMain.size() > 0) {
                    for (int l = 0; l < aPageSection_detailListBeanMain.size(); l++) {
                        String fieldNameSection = BasicMethodsUtil.getInstance().getServerName(aPageSection_detailListBeanMain.get(l).getField_Name());
                        if (CheckArray.isArray(fieldNameSection)) {
                            JSONArray jsonArray = new JSONArray();
                            sectionJSON.put(fieldNameSection, jsonArray);
                        } else {
                            sectionJSON.put(fieldNameSection, "");
                        }
                    }
                } else if (aSubSectionListBeanMain != null && aSubSectionListBeanMain.size() > 0) {
                    createSubSection(act, aSubSectionListBeanMain, sectionJSON);
                }

                sectionArray.put(sectionJSON);
                mainJSON.put(sectionName, sectionArray);
            }
            //Log.e("SUB JSON SERVER ", "REQUEST >> " + mainJSON.toString());
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequest.class, "createSubJsonServerRequest");
        }
        //return mainJSON;
    }

    /**
     * create the sub json details
     *
     * @param act
     * @return
     */
    public static JSONObject createSubJsonDetailEdit(ProcessCreationActivity act) {

        JSONObject jsonMain = null;
        try {
            JSONObject jsonM = act.mainJSON.getJSONObject(act.tabFlowPosList.get(0).getArrayName());

            if (act.tabFlowPosList.size() > 1) {
                for (int i = 1; i < act.tabFlowPosList.size(); i++) {
                    //String mName = BasicMethodsUtil.getInstance().getServerName(track[i]);
                    jsonMain = new JSONObject();
                    jsonMain = jsonM.getJSONArray(act.tabFlowPosList.get(i).getArrayName()).getJSONObject(act.tabFlowPosList.get(i).getPosition());
                    jsonM = new JSONObject();
                    jsonM = jsonMain;
                }
            }
            Log.e("SUB JSON DETAIL ", "REQUEST >> " + jsonMain.toString());
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequest.class, "createSubJsonDetail");
        }
        return jsonMain;
    }

    public static JSONObject createSubJsonDetail(ProcessCreationActivity act) {

        JSONObject sectionJSON = new JSONObject();
        JSONObject jsonMain = null;
        try {
            if (act.mainJSON != null) {

                List<ExpectedQuestionListBean> aExpectedQuestionListBean = act.mSubSectionListBean.getExpectedQuestionList();

                if(aExpectedQuestionListBean != null && aExpectedQuestionListBean.size() > 0) {

                    for (int k = 0; k < aExpectedQuestionListBean.size(); k++) {

                        List<ExpectedAnswerListBean> aExpectedAnswerListBeans = aExpectedQuestionListBean.get(k).getExpectedAnswerList();
                        List<PageSection_detailListBean> aPageSection_detailListBean1 = aExpectedQuestionListBean.get(k).getPageSection_detailList();
                        List<SubSectionListBean> aSubSectionListBean1 = aExpectedQuestionListBean.get(k).getSubSectionList();

                        String fieldName = BasicMethodsUtil.getInstance().getServerName(aExpectedQuestionListBean.get(k).getActualQuestion());
                        sectionJSON.put(fieldName, "");

                        if(aExpectedAnswerListBeans != null && aExpectedAnswerListBeans.size() > 0) {

                            for (int l = 0; l < aExpectedAnswerListBeans.size(); l++) {

                                List<PageSection_detailListBean> aPageSection_detailListBean2 = aExpectedAnswerListBeans.get(l).getPageSection_detailList();

                                if(aPageSection_detailListBean2 != null && aPageSection_detailListBean2.size() > 0) {
                                    for (int m = 0; m < aPageSection_detailListBean2.size(); m++) {
                                        String fieldNameAnswer = BasicMethodsUtil.getInstance().getServerName(aPageSection_detailListBean2.get(m).getField_Name());
                                        if(CheckArray.isArray(fieldNameAnswer)) {
                                            JSONArray jsonArray = new JSONArray();
                                            sectionJSON.put(fieldNameAnswer, jsonArray);
                                        } else {
                                            sectionJSON.put(fieldNameAnswer, "");
                                        }
                                    }
                                }
                            }
                        } else if (aPageSection_detailListBean1 != null && aPageSection_detailListBean1.size() > 0) {
                            for (int l = 0; l < aPageSection_detailListBean1.size(); l++) {
                                String fieldNameSection = BasicMethodsUtil.getInstance().getServerName(aPageSection_detailListBean1.get(l).getField_Name());
                                if(CheckArray.isArray(fieldNameSection)) {
                                    JSONArray jsonArray = new JSONArray();
                                    sectionJSON.put(fieldNameSection, jsonArray);
                                } else {
                                    sectionJSON.put(fieldNameSection, "");
                                }
                            }
                        } else if (aSubSectionListBean1 != null && aSubSectionListBean1.size() > 0) {
                            createSubSection(act, aSubSectionListBean1, sectionJSON);
                        }
                    }
                }
                //mainJSON.put(sectionName, sectionJSON);

                /*try {
                    JSONObject jsonM = act.mainJSON.getJSONObject(act.tabFlowPosList.get(0).getArrayName());

                    if (act.tabFlowPosList.size() > 1) {
                        for (int i = 1; i < act.tabFlowPosList.size(); i++) {
                            //String mName = BasicMethodsUtil.getInstance().getServerName(track[i]);
                            jsonMain = new JSONObject();
                            jsonMain = jsonM.getJSONArray(act.tabFlowPosList.get(i).getArrayName()).getJSONObject(0);
                            jsonM = new JSONObject();
                            jsonM = jsonMain;
                        }
                    }
                    //Log.e("SUB JSON DETAIL ", "REQUEST >> " + jsonMain.toString());
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequest.class, "createSubJsonDetail");
                }*/
            }
            /*for (Iterator<String> it = sectionJSON.keys(); it.hasNext(); ) {
                String key = it.next();
                if (sectionJSON.get(key) instanceof JSONArray) {
                    CopyRequest(sectionJSON, key, "", jsonMain.getJSONArray(key));
                } else {
                    String answer = jsonMain.getString(key);
                    String ques = BasicMethodsUtil.getInstance().getServerName("Please click OK to submit");
                    if(!key.equalsIgnoreCase(ques)) {
                        CopyRequest(sectionJSON, key, answer, null);
                    }
                }
            }*/
            Log.e("SUB JSON DETAIL ", "REQUEST >> "+sectionJSON.toString());
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequest.class, "createSubJsonDetail");
        }
        return sectionJSON;
    }

    /**
     * Set the server request based on the follow parameters
     *
     * @param jsonSection
     * @param question
     * @param answer
     * @param jsonArray
     */
    public static void CopyRequest(JSONObject jsonSection, String question, String answer, JSONArray jsonArray) {
        try {
            //String questionEdit = BasicMethodsUtil.getInstance().getServerName(question);

            if(jsonSection != null && jsonSection.length() > 0) {
                for (Iterator<String> it = jsonSection.keys(); it.hasNext(); ) {
                    String key = it.next();
                    if (jsonSection.get(key) instanceof JSONArray) {
                        if (key.equalsIgnoreCase(question)) {
                            jsonSection.put(key, jsonArray);
                            break;
                        }
                    } else {
                        if (key.equalsIgnoreCase(question)) {
                            jsonSection.put(key, answer);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequest.class, "setCopyRequest");
        }
    }

    /**
     * CReate the Sub json server request
     *
     * @param act
     * @param question
     * @param answer
     * @param list
     */
    public static void setSubJsonServerRequest(ProcessCreationActivity act, String question, String answer, ArrayList<PhotoListModel> list) {
        try {
            String questionEdit = BasicMethodsUtil.getInstance().getServerName(question);

            JSONObject jsonSection = act.subDetailJson;

            for (Iterator<String> it = jsonSection.keys(); it.hasNext(); ) {
                String key = it.next();
                if (key.equalsIgnoreCase(questionEdit)) {
                    if (CheckArray.isArray(key)) {
                        if (list != null && list.size() > 0) {
                            JSONArray jsonArray = new JSONArray();
                            for (int j = 0; j < list.size(); j++) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put(FieldsNameConstant.Item_Name, list.get(j).getPhoto());
                                jsonObject.put(FieldsNameConstant.Item_Description, list.get(j).getDescription());
                                jsonObject.put(FieldsNameConstant.Item_Path, list.get(j).getPath());
                                jsonObject.put(FieldsNameConstant.Item_Status, list.get(j).getStatus());
                                jsonArray.put(jsonObject);
                            }
                            jsonSection.put(key, jsonArray);
                        } else {
                            act.imageListAct = new ArrayList<>();
                            act.signatureListAct = new ArrayList<>();
                            act.sketchListAct = new ArrayList<>();
                            act.docListAct = new ArrayList<>();
                            act.audioListAct = new ArrayList<>();
                            jsonSection.put(key, new ArrayList<>());
                        }
                    } else {
                        jsonSection.put(key, answer);
                    }
                    break;
                }
            }
            Log.e("DEATIL ", "JSON >> " + jsonSection.toString());
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequest.class, "setSubJsonServerRequest");
        }
    }

    /**
     * Get the saved sub json answer
     *
     * @param act
     * @param question
     * @return
     */
    public static String getSubJsonSavedAnswer(ProcessCreationActivity act, String question) {
        String answer = "";
        try {
            String questionDraft = BasicMethodsUtil.getInstance().getServerName(question);
            Log.e("questionDraft ", "questionDraft >>>>> " + questionDraft);

            JSONObject jsonSection = act.subDetailJson;
            Log.e("subDetailJson ", "subDetailJson >>>>> " + act.subDetailJson.toString());
            for (Iterator<String> it = jsonSection.keys(); it.hasNext(); ) {
                String key = it.next();
                if (key.equalsIgnoreCase(questionDraft)) {
                    Log.e("key ", "key >>>>> " + key.toString());
                    answer = getAnswer(act, key, jsonSection);
                    break;
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequest.class, "getSubJsonSavedAnswer");
        }
        return answer;
    }

    /**
     * create the sub json details
     *
     * @param act
     * @return
     */
    public static void deleteSubJsonDetail(ProcessCreationActivity act) {

        JSONObject jsonMain = null;
        try {
            JSONObject jsonM = act.mainJSON.getJSONObject(act.tabFlowPosList.get(0).getArrayName());

            if (act.tabFlowPosList.size() > 1) {
                for (int i = 1; i < act.tabFlowPosList.size(); i++) {
                    //String mName = BasicMethodsUtil.getInstance().getServerName(track[i]);
                    if(i == act.tabFlowPosList.size() - 1) {
                        jsonM.getJSONArray(act.tabFlowPosList.get(i).getArrayName()).remove(act.tabFlowPosList.get(i).getPosition());
                    } else {
                        jsonMain = new JSONObject();
                        jsonMain = jsonM.getJSONArray(act.tabFlowPosList.get(i).getArrayName()).getJSONObject(act.tabFlowPosList.get(i).getPosition());
                        jsonM = new JSONObject();
                        jsonM = jsonMain;
                    }
                }
            }
            //Log.e("SUB JSON DETAIL ", "REQUEST >> " + jsonMain.toString());
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequest.class, "deleteSubJsonDetail");
        }
    }

    /**
     * create the sub json details
     *
     * @param act
     * @param copyObject
     * @return
     */
    public static void copyJSON(ProcessCreationActivity act, JSONObject copyObject) {
        String answer = null;
        try {
            //String questionDraft = BasicMethodsUtil.getInstance().getServerName(question);

            if (act.aPageSectionListBean != null && act.aPageSectionListBean.size() > 0) {
                List<PageSectionListBean> aPageSectionListBean = act.aPageSectionListBean;

                for (int i = 0; i < aPageSectionListBean.size(); i++) {
                    //if (aPageSectionListBean.get(i).getPageSection_Name().equalsIgnoreCase(section)) {

                        String sectionName = BasicMethodsUtil.getInstance().getServerName(aPageSectionListBean.get(i).getPageSection_Name());
                        JSONObject jsonSection = copyObject.getJSONObject(sectionName);
                        for (Iterator<String> it = jsonSection.keys(); it.hasNext(); ) {
                            String key = it.next();
                            if (jsonSection.get(key) instanceof JSONArray) {
                                setCopyRequest(act, sectionName, key, "", jsonSection.getJSONArray(key));
                            } else {
                                answer = jsonSection.getString(key);
                                String ques = BasicMethodsUtil.getInstance().getServerName("Please click OK to submit");
                                if(!key.equalsIgnoreCase(ques)) {
                                    setCopyRequest(act, sectionName, key, answer, null);
                                }
                            }
                        }
                        //break;
                    //}
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequest.class, "copyJSON");
        }
    }

    /**
     * Set the server request based on the follow parameters
     *
     * @param act
     * @param section
     * @param question
     * @param answer
     * @param jsonArray
     */
    public static void setCopyRequest(ProcessCreationActivity act, String section, String question, String answer, JSONArray jsonArray) {
        try {
            //String questionEdit = BasicMethodsUtil.getInstance().getServerName(question);

            JSONObject jsonSection = act.mainJSON.getJSONObject(section);
            if(jsonSection != null && jsonSection.length() > 0) {
                for (Iterator<String> it = jsonSection.keys(); it.hasNext(); ) {
                    String key = it.next();
                    if (jsonSection.get(key) instanceof JSONArray) {
                        if (key.equalsIgnoreCase(question)) {
                            jsonSection.put(key, jsonArray);
                            break;
                        }
                    } else {
                        if (key.equalsIgnoreCase(question)) {
                            jsonSection.put(key, answer);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequest.class, "setCopyRequest");
        }
    }

    /**
     * Set the server request based on the follow parameters
     *
     * @param question
     * @param answer
     * @param list
     */
    public static void setSubSectionObject(JSONObject jsonSection, String question, String answer, ArrayList<PhotoListModel> list) {
        try {
            String questionEdit = BasicMethodsUtil.getInstance().getServerName(question);

            for (Iterator<String> it = jsonSection.keys(); it.hasNext(); ) {
                String key = it.next();
                //if (jsonSection.get(key) instanceof JSONObject) {
                //}
                if (key.equalsIgnoreCase(questionEdit)) {
                    if (CheckArray.isArray(key)) {
                        if (list != null && list.size() > 0) {
                            JSONArray jsonArray = new JSONArray();
                            for (int j = 0; j < list.size(); j++) {
                                JSONObject jsonObject = new JSONObject();
                                jsonObject.put(FieldsNameConstant.Item_Name, list.get(j).getPhoto());
                                jsonObject.put(FieldsNameConstant.Item_Description, list.get(j).getDescription());
                                jsonObject.put(FieldsNameConstant.Item_Path, list.get(j).getPath());
                                jsonObject.put(FieldsNameConstant.Item_Status, list.get(j).getStatus());
                                jsonArray.put(jsonObject);
                            }
                            jsonSection.put(key, jsonArray);
                        } else {
                            jsonSection.put(key, new JSONArray());
                        }
                    } else {
                        jsonSection.put(key, answer);
                    }
                    break;
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequest.class, "setSubSectionObject");
        }
    }

    private static String getAnswer(ProcessCreationActivity act, String key, JSONObject jsonSection) {
        String answer = "";
        try {
            if (key.equalsIgnoreCase(FieldsNameConstant.Images)) {
                JSONArray jsonArray = jsonSection.getJSONArray(FieldsNameConstant.Images);

                if (jsonArray != null && jsonArray.length() > 0) {
                    act.imageListAct = new ArrayList<>();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        PhotoListModel photoListModel = new PhotoListModel();
                        photoListModel.setPhoto(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Name));
                        photoListModel.setDescription(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Description));
                        photoListModel.setPath(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Path));
                        photoListModel.setStatus(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Status));
                        act.imageListAct.add(photoListModel);
                    }
                    answer = GenericConstant.Image_Present;
                } else {
                    answer =  "";
                    act.imageListAct = new ArrayList<>();
                }
            } else if (key.equalsIgnoreCase(FieldsNameConstant.Signature)) {
                JSONArray jsonArray = jsonSection.getJSONArray(FieldsNameConstant.Signature);

                if (jsonArray != null && jsonArray.length() > 0) {
                    act.signatureListAct = new ArrayList<>();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        PhotoListModel photoListModel = new PhotoListModel();
                        photoListModel.setPhoto(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Name));
                        photoListModel.setDescription(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Description));
                        photoListModel.setPath(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Path));
                        photoListModel.setStatus(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Status));
                        act.signatureListAct.add(photoListModel);
                    }
                    answer = GenericConstant.Signature_Present;
                    //CreateDynamicView.userSignatureList(act, new AppSession(act).getUserID(), act.llProcessLayout, act.signatureListAct, SetClick.onClickSignature, SetClick.onClickLongSignature);
                } else {
                    answer =  "";
                    act.signatureListAct = new ArrayList<>();
                }
            } else if (key.equalsIgnoreCase(FieldsNameConstant.Sketch)) {
                JSONArray jsonArray = jsonSection.getJSONArray(FieldsNameConstant.Sketch);

                if (jsonArray != null && jsonArray.length() > 0) {
                    act.sketchListAct = new ArrayList<>();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        PhotoListModel photoListModel = new PhotoListModel();
                        photoListModel.setPhoto(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Name));
                        photoListModel.setDescription(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Description));
                        photoListModel.setPath(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Path));
                        photoListModel.setStatus(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Status));
                        act.sketchListAct.add(photoListModel);
                    }
                    answer = GenericConstant.Sketch_Present;
                    //CreateDynamicView.userSignatureList(act, new AppSession(act).getUserID(), act.llProcessLayout, act.signatureListAct, SetClick.onClickSignature, SetClick.onClickLongSignature);
                } else {
                    answer =  "";
                    act.sketchListAct = new ArrayList<>();
                }
            } else if (key.equalsIgnoreCase(FieldsNameConstant.Documents)) {
                JSONArray jsonArray = jsonSection.getJSONArray(FieldsNameConstant.Documents);

                if (jsonArray != null && jsonArray.length() > 0) {
                    act.docListAct = new ArrayList<>();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        PhotoListModel photoListModel = new PhotoListModel();
                        photoListModel.setPhoto(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Name));
                        photoListModel.setDescription(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Description));
                        photoListModel.setPath(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Path));
                        photoListModel.setStatus(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Status));
                        act.docListAct.add(photoListModel);
                    }
                    answer = GenericConstant.Document_Present;
                    //CreateDynamicView.userSignatureList(act, new AppSession(act).getUserID(), act.llProcessLayout, act.signatureListAct, SetClick.onClickSignature, SetClick.onClickLongSignature);
                } else {
                    answer =  "";
                    act.docListAct = new ArrayList<>();
                }
            } else if (key.equalsIgnoreCase(FieldsNameConstant.Audios)) {
                JSONArray jsonArray = jsonSection.getJSONArray(FieldsNameConstant.Audios);

                if (jsonArray != null && jsonArray.length() > 0) {
                    act.audioListAct = new ArrayList<>();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        PhotoListModel photoListModel = new PhotoListModel();
                        photoListModel.setPhoto(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Name));
                        photoListModel.setDescription(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Description));
                        photoListModel.setPath(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Path));
                        photoListModel.setStatus(jsonArray.getJSONObject(j).getString(FieldsNameConstant.Item_Status));
                        act.audioListAct.add(photoListModel);
                    }
                    answer = GenericConstant.Audio_Present;
                    //CreateDynamicView.userSignatureList(act, new AppSession(act).getUserID(), act.llProcessLayout, act.signatureListAct, SetClick.onClickSignature, SetClick.onClickLongSignature);
                } else {
                    answer =  "";
                    act.audioListAct = new ArrayList<>();
                }
            } else if (key.equalsIgnoreCase(FieldsNameConstant.Pocketbook)) {
                act.pocketbook = jsonSection.getString(key);
                answer = jsonSection.getString(key);
            } else {
                answer = jsonSection.getString(key);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ServerRequest.class, "getAnswer");
        }
        return answer;
    }
}
