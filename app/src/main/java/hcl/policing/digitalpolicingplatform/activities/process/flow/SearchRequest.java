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
import hcl.policing.digitalpolicingplatform.utils.SearchDialogUtil;

public class SearchRequest {

    /**
     * Create the server request
     *
     * @param list
     * @return
     */
    public static JSONObject createSearchRequest(List<PageSection_detailListBean> list) {
        JSONObject mainJSON = new JSONObject();
        try {
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {

                    String sectionName = BasicMethodsUtil.getInstance().getServerName(list.get(i).getField_Name());

                    mainJSON.put(sectionName, "");
                }
            }
            Log.e("Search ", "REQUEST >> " + mainJSON.toString());
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), SearchRequest.class, "createServerRequest");
        }
        return mainJSON;
    }

    /**
     * Set the server request based on the follow parameters
     *
     * @param question
     * @param answer
     */
    public static void setSearchRequest(String question, String answer) {
        try {
            String questionEdit = BasicMethodsUtil.getInstance().getServerName(question);

            JSONObject jsonSection = SearchDialogUtil.searchObject;
            for (Iterator<String> it = jsonSection.keys(); it.hasNext(); ) {
                String key = it.next();
                if (key.equalsIgnoreCase(questionEdit)) {
                    jsonSection.put(key, answer);
                    break;
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), SearchRequest.class, "setServerRequest");
        }
    }

    /**
     * Get the saved answer values
     *
     * @param question
     * @return
     */
    public static String getSavedAnswer(String question) {
        String answer = null;
        try {
            String questionA = BasicMethodsUtil.getInstance().getServerName(question);

            JSONObject jsonSection = SearchDialogUtil.searchObject;
            for (Iterator<String> it = jsonSection.keys(); it.hasNext(); ) {
                String key = it.next();
                if (key.equalsIgnoreCase(questionA)) {
                    answer = jsonSection.getString(key);
                    break;
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), SearchRequest.class, "getSavedAnswer");
        }
        return answer;
    }
}
