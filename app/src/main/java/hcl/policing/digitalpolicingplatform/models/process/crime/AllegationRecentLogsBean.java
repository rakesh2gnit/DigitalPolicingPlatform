package hcl.policing.digitalpolicingplatform.models.process.crime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;

public class AllegationRecentLogsBean implements Serializable {

    @Expose
    @SerializedName("recentValue")
    private String recentValue;
    @Expose
    @SerializedName("objectList")
    private OffenceDefinitionList objectList;
    @Expose
    @SerializedName("recentLogsList")
    private ArrayList<AnswerValueDTO> recentLogsList;

    public String getRecentValue() {
        return recentValue;
    }

    public void setRecentValue(String recentValue) {
        this.recentValue = recentValue;
    }

    public OffenceDefinitionList getObjectList() {
        return objectList;
    }

    public void setObjectList(OffenceDefinitionList objectList) {
        this.objectList = objectList;
    }

    public ArrayList<AnswerValueDTO> getRecentLogsList() {
        return recentLogsList;
    }

    public void setRecentLogsList(ArrayList<AnswerValueDTO> recentLogsList) {
        this.recentLogsList = recentLogsList;
    }
}
