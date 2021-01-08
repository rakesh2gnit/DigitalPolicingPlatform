package hcl.policing.digitalpolicingplatform.models.process.officer;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.process.crime.OrganisationDetailsList;

public class OfficerRecentLogsBean implements Serializable {

    @Expose
    @SerializedName("recentValue")
    private String recentValue;
    @Expose
    @SerializedName("objectList")
    private OfficerBean objectList;
    @Expose
    @SerializedName("recentLogsList")
    private ArrayList<AnswerValueDTO> recentLogsList;

    public String getRecentValue() {
        return recentValue;
    }

    public void setRecentValue(String recentValue) {
        this.recentValue = recentValue;
    }

    public OfficerBean getObjectList() {
        return objectList;
    }

    public void setObjectList(OfficerBean objectList) {
        this.objectList = objectList;
    }

    public ArrayList<AnswerValueDTO> getRecentLogsList() {
        return recentLogsList;
    }

    public void setRecentLogsList(ArrayList<AnswerValueDTO> recentLogsList) {
        this.recentLogsList = recentLogsList;
    }
}
