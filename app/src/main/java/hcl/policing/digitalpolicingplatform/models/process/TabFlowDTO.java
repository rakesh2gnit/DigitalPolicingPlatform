package hcl.policing.digitalpolicingplatform.models.process;

import java.io.Serializable;
import java.util.List;

public class TabFlowDTO implements Serializable {

    private String arrayName;
    private int id;
    private int position;
    private int count;
    private boolean visibility;
    private List<SubSectionListBean> subSectionList;
    private List<ExpectedQuestionListBean> expectedQuestionList;
    private List<PageSection_detailListBean> pageSection_detailList;

    public String getArrayName() {
        return arrayName;
    }

    public void setArrayName(String arrayName) {
        this.arrayName = arrayName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public boolean isVisibility() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility = visibility;
    }

    public List<SubSectionListBean> getSubSectionList() {
        return subSectionList;
    }

    public void setSubSectionList(List<SubSectionListBean> subSectionList) {
        this.subSectionList = subSectionList;
    }

    public List<ExpectedQuestionListBean> getExpectedQuestionList() {
        return expectedQuestionList;
    }

    public void setExpectedQuestionList(List<ExpectedQuestionListBean> expectedQuestionList) {
        this.expectedQuestionList = expectedQuestionList;
    }

    public List<PageSection_detailListBean> getPageSection_detailList() {
        return pageSection_detailList;
    }

    public void setPageSection_detailList(List<PageSection_detailListBean> pageSection_detailList) {
        this.pageSection_detailList = pageSection_detailList;
    }
}
