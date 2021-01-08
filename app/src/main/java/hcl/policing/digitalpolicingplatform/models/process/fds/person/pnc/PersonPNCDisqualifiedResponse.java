package hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonPNCDisqualifiedResponse {

    @Expose
    @SerializedName("DisqualifiedDriverList")
    private List<DisqualifiedDriverListBean> disqualifieddriverlist;
    @Expose
    @SerializedName("BaseDataContract")
    private BasedatacontractBean basedatacontract;

    public List<DisqualifiedDriverListBean> getDisqualifieddriverlist() {
        return disqualifieddriverlist;
    }

    public void setDisqualifieddriverlist(List<DisqualifiedDriverListBean> disqualifieddriverlist) {
        this.disqualifieddriverlist = disqualifieddriverlist;
    }

    public BasedatacontractBean getBasedatacontract() {
        return basedatacontract;
    }

    public void setBasedatacontract(BasedatacontractBean basedatacontract) {
        this.basedatacontract = basedatacontract;
    }

    public static class DisqualifiedDriverListBean {
        @Expose
        @SerializedName("Text")
        private String text;
        @Expose
        @SerializedName("Status")
        private String status;
        @Expose
        @SerializedName("Note")
        private String note;
        @Expose
        @SerializedName("FsRef")
        private String fsref;
        @Expose
        @SerializedName("DateEffectiveFrom")
        private String dateeffectivefrom;
        @Expose
        @SerializedName("Court")
        private String court;

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

        public String getNote() {
            return note;
        }

        public void setNote(String note) {
            this.note = note;
        }

        public String getFsref() {
            return fsref;
        }

        public void setFsref(String fsref) {
            this.fsref = fsref;
        }

        public String getDateeffectivefrom() {
            return dateeffectivefrom;
        }

        public void setDateeffectivefrom(String dateeffectivefrom) {
            this.dateeffectivefrom = dateeffectivefrom;
        }

        public String getCourt() {
            return court;
        }

        public void setCourt(String court) {
            this.court = court;
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
