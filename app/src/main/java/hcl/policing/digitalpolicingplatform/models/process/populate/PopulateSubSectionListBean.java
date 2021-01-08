package hcl.policing.digitalpolicingplatform.models.process.populate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PopulateSubSectionListBean implements Serializable {
    @Expose
    @SerializedName("SubSection_Name")
    private String subSection_Name;
    @Expose
    @SerializedName("SubSection_Id")
    private int subSection_Id;
    @Expose
    @SerializedName("Populate_DetailList")
    private List<Populate_DetailListBean> populate_DetailList;
    @Expose
    @SerializedName("Populate_SubSection")
    private List<PopulateSubSectionListBean> populateSubSectionList;

    public String getSubSection_Name() {
        return subSection_Name;
    }

    public void setSubSection_Name(String subSection_Name) {
        this.subSection_Name = subSection_Name;
    }

    public int getSubSection_Id() {
        return subSection_Id;
    }

    public void setSubSection_Id(int subSection_Id) {
        this.subSection_Id = subSection_Id;
    }

    public List<Populate_DetailListBean> getPopulate_DetailList() {
        return populate_DetailList;
    }

    public void setPopulate_DetailList(List<Populate_DetailListBean> populate_DetailList) {
        this.populate_DetailList = populate_DetailList;
    }

    public List<PopulateSubSectionListBean> getPopulateSubSectionList() {
        return populateSubSectionList;
    }

    public void setPopulateSubSectionList(List<PopulateSubSectionListBean> populateSubSectionList) {
        this.populateSubSectionList = populateSubSectionList;
    }
}
