package hcl.policing.digitalpolicingplatform.models.process;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import hcl.policing.digitalpolicingplatform.models.process.populate.PopulateListBean;

public class ResponseModel implements Serializable {

    @Expose
    @SerializedName("ProcessList")
    private List<ProcessListBean> processList;
    @Expose
    @SerializedName("PopulateList")
    private List<PopulateListBean> populateList;

    public List<ProcessListBean> getProcessList() {
        return processList;
    }

    public void setProcessList(List<ProcessListBean> processList) {
        this.processList = processList;
    }

    public List<PopulateListBean> getPopulateList() {
        return populateList;
    }

    public void setPopulateList(List<PopulateListBean> populateList) {
        this.populateList = populateList;
    }
}
