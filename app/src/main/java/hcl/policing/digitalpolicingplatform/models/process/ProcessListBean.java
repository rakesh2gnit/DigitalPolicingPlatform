package hcl.policing.digitalpolicingplatform.models.process;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProcessListBean implements Serializable {
    @Expose
    @SerializedName("PageSectionList")
    private List<PageSectionListBean> pageSectionList;
    @Expose
    @SerializedName("Process_Id")
    private int Process_Id;
    @Expose
    @SerializedName("Process_Name")
    private String Process_Name;

    public List<PageSectionListBean> getPageSectionList() {
        return pageSectionList;
    }

    public void setPageSectionList(List<PageSectionListBean> pageSectionList) {
        this.pageSectionList = pageSectionList;
    }

    public int getProcess_Id() {
        return Process_Id;
    }

    public void setProcess_Id(int process_Id) {
        Process_Id = process_Id;
    }

    public String getProcess_Name() {
        return Process_Name;
    }

    public void setProcess_Name(String process_Name) {
        Process_Name = process_Name;
    }
}
