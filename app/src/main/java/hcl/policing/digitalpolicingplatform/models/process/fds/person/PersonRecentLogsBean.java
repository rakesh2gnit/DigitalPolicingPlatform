package hcl.policing.digitalpolicingplatform.models.process.fds.person;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;

public class PersonRecentLogsBean implements Serializable {

    @Expose
    @SerializedName("recentValue")
    private String recentValue;
    @Expose
    @SerializedName("objectList")
    private PersonBean objectList;
    @Expose
    @SerializedName("recentLogsList")
    private ArrayList<AnswerValueDTO> recentLogsList;

    public String getRecentValue() {
        return recentValue;
    }

    public void setRecentValue(String recentValue) {
        this.recentValue = recentValue;
    }

    public PersonBean getObjectList() {
        return objectList;
    }

    public void setObjectList(PersonBean objectList) {
        this.objectList = objectList;
    }

    public ArrayList<AnswerValueDTO> getRecentLogsList() {
        return recentLogsList;
    }

    public void setRecentLogsList(ArrayList<AnswerValueDTO> recentLogsList) {
        this.recentLogsList = recentLogsList;
    }
}
