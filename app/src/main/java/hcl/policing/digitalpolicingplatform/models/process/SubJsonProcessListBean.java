package hcl.policing.digitalpolicingplatform.models.process;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SubJsonProcessListBean implements Serializable {

    @Expose
    @SerializedName("SubSectionProcess_Name")
    private String SubSectionProcess_Name;
    @Expose
    @SerializedName("SubSectionProcess_Id")
    private int SubSectionProcess_Id;
    @Expose
    @SerializedName("SubSectionList")
    private List<SubSectionListBean> subJsonList;

    public String getSubSectionProcess_Name() {
        return SubSectionProcess_Name;
    }

    public void setSubSectionProcess_Name(String subSectionProcess_Name) {
        SubSectionProcess_Name = subSectionProcess_Name;
    }

    public int getSubSectionProcess_Id() {
        return SubSectionProcess_Id;
    }

    public void setSubSectionProcess_Id(int subSectionProcess_Id) {
        SubSectionProcess_Id = subSectionProcess_Id;
    }

    public List<SubSectionListBean> getSubJsonList() {
        return subJsonList;
    }

    public void setSubJsonList(List<SubSectionListBean> subJsonList) {
        this.subJsonList = subJsonList;
    }
}
