package hcl.policing.digitalpolicingplatform.models.process.populate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PopulateSectionListBean implements Serializable {
    @Expose
    @SerializedName("PageSection_Name")
    private String pageSection_Name;
    @Expose
    @SerializedName("PageSection_Id")
    private int pageSection_Id;
    @Expose
    @SerializedName("Populate_DetailList")
    private List<Populate_DetailListBean> populate_DetailList;
    @Expose
    @SerializedName("Populate_SubSection")
    private List<PopulateSubSectionListBean> populateSubSectionList;

    public String getPageSection_Name() {
        return pageSection_Name;
    }

    public void setPageSection_Name(String pageSection_Name) {
        this.pageSection_Name = pageSection_Name;
    }

    public int getPageSection_Id() {
        return pageSection_Id;
    }

    public void setPageSection_Id(int pageSection_Id) {
        this.pageSection_Id = pageSection_Id;
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
