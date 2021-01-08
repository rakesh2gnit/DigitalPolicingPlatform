package hcl.policing.digitalpolicingplatform.models.process;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class SubSectionListBean implements Serializable {

    @Expose
    @SerializedName("PageSection_Name")
    private String PageSection_Name;
    @Expose
    @SerializedName("PageSection_Id")
    private int PageSection_Id;
    @Expose
    @SerializedName("PageSection_Visibility")
    private boolean PageSection_Visibility;
    @Expose
    @SerializedName("Statement")
    private String Statement;
    @Expose
    @SerializedName("SubSectionList")
    private List<SubSectionListBean> subSectionList;
    @Expose
    @SerializedName("ExpectedQuestionList")
    private List<ExpectedQuestionListBean> ExpectedQuestionList;
    @Expose
    @SerializedName("PageSection_detailList")
    private List<PageSection_detailListBean> PageSection_detailList;

    public String getPageSection_Name() {
        return PageSection_Name;
    }

    public void setPageSection_Name(String pageSection_Name) {
        PageSection_Name = pageSection_Name;
    }

    public int getPageSection_Id() {
        return PageSection_Id;
    }

    public void setPageSection_Id(int pageSection_Id) {
        PageSection_Id = pageSection_Id;
    }

    public boolean isPageSection_Visibility() {
        return PageSection_Visibility;
    }

    public void setPageSection_Visibility(boolean pageSection_Visibility) {
        PageSection_Visibility = pageSection_Visibility;
    }

    public String getStatement() {
        return Statement;
    }

    public void setStatement(String statement) {
        Statement = statement;
    }

    public List<SubSectionListBean> getSubSectionList() {
        return subSectionList;
    }

    public void setSubSectionList(List<SubSectionListBean> subSectionList) {
        this.subSectionList = subSectionList;
    }

    public List<ExpectedQuestionListBean> getExpectedQuestionList() {
        return ExpectedQuestionList;
    }

    public void setExpectedQuestionList(List<ExpectedQuestionListBean> expectedQuestionList) {
        ExpectedQuestionList = expectedQuestionList;
    }

    public List<PageSection_detailListBean> getPageSection_detailList() {
        return PageSection_detailList;
    }

    public void setPageSection_detailList(List<PageSection_detailListBean> pageSection_detailList) {
        PageSection_detailList = pageSection_detailList;
    }
}
