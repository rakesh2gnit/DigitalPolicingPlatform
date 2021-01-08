package hcl.policing.digitalpolicingplatform.models.layoutHelper;

import android.view.ViewGroup;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.process.PageSection_detailListBean;

public class LayoutFieldHelper implements Serializable {

    private String viewType;
    private PropertiesBean propertiesBean;
    private ViewGroup parentView;
    private String basicLayout;
    private String dropdownArray;
    private PageSection_detailListBean pageSectionDetailListBean;

    public String getViewType() {
        return viewType;
    }

    public void setViewType(String viewType) {
        this.viewType = viewType;
    }

    public PropertiesBean getPropertiesBean() {
        return propertiesBean;
    }

    public void setPropertiesBean(PropertiesBean propertiesBean) {
        this.propertiesBean = propertiesBean;
    }

    public ViewGroup getParentView() {
        return parentView;
    }

    public void setParentView(ViewGroup parentView) {
        this.parentView = parentView;
    }

    public String getBasicLayout() {
        return basicLayout;
    }

    public void setBasicLayout(String basicLayout) {
        this.basicLayout = basicLayout;
    }

    public String getDropdownArray() {
        return dropdownArray;
    }

    public void setDropdownArray(String dropdownArray) {
        this.dropdownArray = dropdownArray;
    }

    public PageSection_detailListBean getPageSectionDetailListBean() {
        return pageSectionDetailListBean;
    }

    public void setPageSectionDetailListBean(PageSection_detailListBean pageSectionDetailListBean) {
        this.pageSectionDetailListBean = pageSectionDetailListBean;
    }
}
