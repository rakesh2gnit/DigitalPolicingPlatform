package hcl.policing.digitalpolicingplatform.models.process.fds.address.stops;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddressStopDetailsResponse {

    @Expose
    @SerializedName("searchVehicleList")
    private List<SearchVehicleListModel> searchvehiclelist;
    @Expose
    @SerializedName("searchStopList")
    private List<SearchStopListModel> searchstoplist;
    @Expose
    @SerializedName("searchPersonList")
    private List<SearchPersonListModel> searchpersonlist;
    @Expose
    @SerializedName("searchAddressList")
    private List<SearchAddressListModel> searchaddresslist;
    @Expose
    @SerializedName("BaseDataContract")
    private BasedatacontractBean basedatacontract;

    public List<SearchVehicleListModel> getSearchvehiclelist() {
        return searchvehiclelist;
    }

    public void setSearchvehiclelist(List<SearchVehicleListModel> searchvehiclelist) {
        this.searchvehiclelist = searchvehiclelist;
    }

    public List<SearchStopListModel> getSearchstoplist() {
        return searchstoplist;
    }

    public void setSearchstoplist(List<SearchStopListModel> searchstoplist) {
        this.searchstoplist = searchstoplist;
    }

    public List<SearchPersonListModel> getSearchpersonlist() {
        return searchpersonlist;
    }

    public void setSearchpersonlist(List<SearchPersonListModel> searchpersonlist) {
        this.searchpersonlist = searchpersonlist;
    }

    public List<SearchAddressListModel> getSearchaddresslist() {
        return searchaddresslist;
    }

    public void setSearchaddresslist(List<SearchAddressListModel> searchaddresslist) {
        this.searchaddresslist = searchaddresslist;
    }

    public BasedatacontractBean getBasedatacontract() {
        return basedatacontract;
    }

    public void setBasedatacontract(BasedatacontractBean basedatacontract) {
        this.basedatacontract = basedatacontract;
    }

    public static class BasedatacontractBean {
        @Expose
        @SerializedName("TotalRecordCount")
        private int totalrecordcount;
        @Expose
        @SerializedName("TotalPageCount")
        private int totalpagecount;
        @Expose
        @SerializedName("SubProcess")
        private String subprocess;
        @Expose
        @SerializedName("ReasonCode")
        private String reasoncode;
        @Expose
        @SerializedName("PropertyExt5")
        private String propertyext5;
        @Expose
        @SerializedName("PropertyExt3")
        private String propertyext3;
        @Expose
        @SerializedName("Process")
        private String process;
        @Expose
        @SerializedName("PNCDATA")
        private String pncdata;
        @Expose
        @SerializedName("LayerName")
        private String layername;
        @Expose
        @SerializedName("CurrentPage")
        private int currentpage;

        public int getTotalrecordcount() {
            return totalrecordcount;
        }

        public void setTotalrecordcount(int totalrecordcount) {
            this.totalrecordcount = totalrecordcount;
        }

        public int getTotalpagecount() {
            return totalpagecount;
        }

        public void setTotalpagecount(int totalpagecount) {
            this.totalpagecount = totalpagecount;
        }

        public String getSubprocess() {
            return subprocess;
        }

        public void setSubprocess(String subprocess) {
            this.subprocess = subprocess;
        }

        public String getReasoncode() {
            return reasoncode;
        }

        public void setReasoncode(String reasoncode) {
            this.reasoncode = reasoncode;
        }

        public String getPropertyext5() {
            return propertyext5;
        }

        public void setPropertyext5(String propertyext5) {
            this.propertyext5 = propertyext5;
        }

        public String getPropertyext3() {
            return propertyext3;
        }

        public void setPropertyext3(String propertyext3) {
            this.propertyext3 = propertyext3;
        }

        public String getProcess() {
            return process;
        }

        public void setProcess(String process) {
            this.process = process;
        }

        public String getPncdata() {
            return pncdata;
        }

        public void setPncdata(String pncdata) {
            this.pncdata = pncdata;
        }

        public String getLayername() {
            return layername;
        }

        public void setLayername(String layername) {
            this.layername = layername;
        }

        public int getCurrentpage() {
            return currentpage;
        }

        public void setCurrentpage(int currentpage) {
            this.currentpage = currentpage;
        }
    }
}
