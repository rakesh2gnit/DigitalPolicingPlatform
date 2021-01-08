package hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.stops;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VehicleSTOPResponse {

    @Expose
    @SerializedName("searchVehicleList")
    private List<SearchVehicleListBean> searchvehiclelist;
    @Expose
    @SerializedName("BaseDataContract")
    private BasedatacontractBean basedatacontract;

    public List<SearchVehicleListBean> getSearchvehiclelist() {
        return searchvehiclelist;
    }

    public void setSearchvehiclelist(List<SearchVehicleListBean> searchvehiclelist) {
        this.searchvehiclelist = searchvehiclelist;
    }

    public BasedatacontractBean getBasedatacontract() {
        return basedatacontract;
    }

    public void setBasedatacontract(BasedatacontractBean basedatacontract) {
        this.basedatacontract = basedatacontract;
    }

    public static class SearchVehicleListBean {
        @Expose
        @SerializedName("VehicleID")
        private String vehicleid;
        @Expose
        @SerializedName("VRM")
        private String vrm;
        @Expose
        @SerializedName("Model")
        private String model;
        @Expose
        @SerializedName("Make")
        private String make;
        @Expose
        @SerializedName("Colour")
        private String colour;
        @Expose
        @SerializedName("TotalRecordCount")
        private int totalrecordcount;
        @Expose
        @SerializedName("TotalPageCount")
        private int totalpagecount;
        @Expose
        @SerializedName("CurrentPage")
        private int currentpage;

        public String getVehicleid() {
            return vehicleid;
        }

        public void setVehicleid(String vehicleid) {
            this.vehicleid = vehicleid;
        }

        public String getVrm() {
            return vrm;
        }

        public void setVrm(String vrm) {
            this.vrm = vrm;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

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

        public int getCurrentpage() {
            return currentpage;
        }

        public void setCurrentpage(int currentpage) {
            this.currentpage = currentpage;
        }
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
