package hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.pnc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VehiclePNCReportResponse {

    @Expose
    @SerializedName("VehicleReports")
    private List<VehicleReportsBean> vehiclereports;
    @Expose
    @SerializedName("BaseDataContract")
    private BasedatacontractBean basedatacontract;

    public List<VehicleReportsBean> getVehiclereports() {
        return vehiclereports;
    }

    public void setVehiclereports(List<VehicleReportsBean> vehiclereports) {
        this.vehiclereports = vehiclereports;
    }

    public BasedatacontractBean getBasedatacontract() {
        return basedatacontract;
    }

    public void setBasedatacontract(BasedatacontractBean basedatacontract) {
        this.basedatacontract = basedatacontract;
    }

    public static class VehicleReportsBean {
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("Text")
        private String text;
        @Expose
        @SerializedName("Status")
        private String status;
        @Expose
        @SerializedName("ReportKeeperChanged")
        private String reportkeeperchanged;
        @Expose
        @SerializedName("ReportHazard")
        private String reporthazard;
        @Expose
        @SerializedName("Reference")
        private String reference;
        @Expose
        @SerializedName("Owner")
        private String owner;
        @Expose
        @SerializedName("IncDate")
        private String incdate;
        @Expose
        @SerializedName("Creator")
        private String creator;
        @Expose
        @SerializedName("CreatedDateTime")
        private String createddatetime;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getReportkeeperchanged() {
            return reportkeeperchanged;
        }

        public void setReportkeeperchanged(String reportkeeperchanged) {
            this.reportkeeperchanged = reportkeeperchanged;
        }

        public String getReporthazard() {
            return reporthazard;
        }

        public void setReporthazard(String reporthazard) {
            this.reporthazard = reporthazard;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getOwner() {
            return owner;
        }

        public void setOwner(String owner) {
            this.owner = owner;
        }

        public String getIncdate() {
            return incdate;
        }

        public void setIncdate(String incdate) {
            this.incdate = incdate;
        }

        public String getCreator() {
            return creator;
        }

        public void setCreator(String creator) {
            this.creator = creator;
        }

        public String getCreateddatetime() {
            return createddatetime;
        }

        public void setCreateddatetime(String createddatetime) {
            this.createddatetime = createddatetime;
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
        @SerializedName("PropertyExt3")
        private String propertyext3;
        @Expose
        @SerializedName("Process")
        private String process;
        @Expose
        @SerializedName("FDSRequestString")
        private String fdsrequeststring;
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

        public String getFdsrequeststring() {
            return fdsrequeststring;
        }

        public void setFdsrequeststring(String fdsrequeststring) {
            this.fdsrequeststring = fdsrequeststring;
        }

        public int getCurrentpage() {
            return currentpage;
        }

        public void setCurrentpage(int currentpage) {
            this.currentpage = currentpage;
        }
    }
}
