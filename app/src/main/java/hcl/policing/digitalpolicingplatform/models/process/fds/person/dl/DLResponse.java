package hcl.policing.digitalpolicingplatform.models.process.fds.person.dl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class DLResponse implements Serializable {

    @Expose
    @SerializedName("dLMenuSMList")
    private List<DLMenuSMListModel> dlmenusmlist;
    @Expose
    @SerializedName("dLMenuPEList")
    private List<DLMenuPEListModel> dlmenupelist;
    @Expose
    @SerializedName("dLMenuFEList")
    private List<DLMenuFEListModel> dlmenufelist;
    @Expose
    @SerializedName("dLMenuDTList")
    private List<DLMenuDTListModel> dlmenudtlist;
    @Expose
    @SerializedName("dLMenuXRList")
    private List<DLMenuXRListModel> dlmenuxrlist;
    @Expose
    @SerializedName("dLMenuUTList")
    private List<DLMenuUTListModel> dlmenuutlist;
    @Expose
    @SerializedName("dLMenuESList")
    private List<DLMenuESListModel> dlmenueslist;
    @Expose
    @SerializedName("dLMenuEDList")
    private List<DLMenuEDListModel> dlmenuedlist;
    @Expose
    @SerializedName("DrivingLicenceByIDList")
    private List<DrivinglicencebyidlistModel> drivinglicencebyidlist;
    @Expose
    @SerializedName("DrivingLicenceByNameList")
    private List<DrivinglicencebynamelistModel> drivinglicencebynamelist;


    public List<DLMenuSMListModel> getDlmenusmlist() {
        return dlmenusmlist;
    }

    public void setDlmenusmlist(List<DLMenuSMListModel> dlmenusmlist) {
        this.dlmenusmlist = dlmenusmlist;
    }

    public List<DLMenuPEListModel> getDlmenupelist() {
        return dlmenupelist;
    }

    public void setDlmenupelist(List<DLMenuPEListModel> dlmenupelist) {
        this.dlmenupelist = dlmenupelist;
    }

    public List<DLMenuFEListModel> getDlmenufelist() {
        return dlmenufelist;
    }

    public void setDlmenufelist(List<DLMenuFEListModel> dlmenufelist) {
        this.dlmenufelist = dlmenufelist;
    }

    public List<DLMenuDTListModel> getDlmenudtlist() {
        return dlmenudtlist;
    }

    public void setDlmenudtlist(List<DLMenuDTListModel> dlmenudtlist) {
        this.dlmenudtlist = dlmenudtlist;
    }

    public List<DLMenuXRListModel> getDlmenuxrlist() {
        return dlmenuxrlist;
    }

    public void setDlmenuxrlist(List<DLMenuXRListModel> dlmenuxrlist) {
        this.dlmenuxrlist = dlmenuxrlist;
    }

    public List<DLMenuUTListModel> getDlmenuutlist() {
        return dlmenuutlist;
    }

    public void setDlmenuutlist(List<DLMenuUTListModel> dlmenuutlist) {
        this.dlmenuutlist = dlmenuutlist;
    }

    public List<DLMenuESListModel> getDlmenueslist() {
        return dlmenueslist;
    }

    public void setDlmenueslist(List<DLMenuESListModel> dlmenueslist) {
        this.dlmenueslist = dlmenueslist;
    }

    public List<DLMenuEDListModel> getDlmenuedlist() {
        return dlmenuedlist;
    }

    public void setDlmenuedlist(List<DLMenuEDListModel> dlmenuedlist) {
        this.dlmenuedlist = dlmenuedlist;
    }

    public List<DrivinglicencebyidlistModel> getDrivinglicencebyidlist() {
        return drivinglicencebyidlist;
    }

    public void setDrivinglicencebyidlist(List<DrivinglicencebyidlistModel> drivinglicencebyidlist) {
        this.drivinglicencebyidlist = drivinglicencebyidlist;
    }

    public List<DrivinglicencebynamelistModel> getDrivinglicencebynamelist() {
        return drivinglicencebynamelist;
    }

    public void setDrivinglicencebynamelist(List<DrivinglicencebynamelistModel> drivinglicencebynamelist) {
        this.drivinglicencebynamelist = drivinglicencebynamelist;
    }


}
