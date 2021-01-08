package hcl.policing.digitalpolicingplatform.models.search;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.process.PageSectionListBean;

public class RecentLogsBean implements Serializable {

    @Expose
    @SerializedName("recentValue")
    private String recentValue;
    @Expose
    @SerializedName("objectList")
    private Object objectList;
    @Expose
    @SerializedName("recentLogsList")
    private ArrayList<AnswerValueDTO> recentLogsList;

    public String getRecentValue() {
        return recentValue;
    }

    public void setRecentValue(String recentValue) {
        this.recentValue = recentValue;
    }

    public Object getObjectList() {
        return objectList;
    }

    public void setObjectList(Object objectList) {
        this.objectList = objectList;
    }

    public ArrayList<AnswerValueDTO> getRecentLogsList() {
        return recentLogsList;
    }

    public void setRecentLogsList(ArrayList<AnswerValueDTO> recentLogsList) {
        this.recentLogsList = recentLogsList;
    }
}
