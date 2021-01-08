package hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonPNCDescriptionResponse {

    @Expose
    @SerializedName("PersonDescriptionList")
    private List<PersonDescriptionListBean> persondescriptionlist;
    @Expose
    @SerializedName("BaseDataContract")
    private BasedatacontractBean basedatacontract;

    public List<PersonDescriptionListBean> getPersondescriptionlist() {
        return persondescriptionlist;
    }

    public void setPersondescriptionlist(List<PersonDescriptionListBean> persondescriptionlist) {
        this.persondescriptionlist = persondescriptionlist;
    }

    public BasedatacontractBean getBasedatacontract() {
        return basedatacontract;
    }

    public void setBasedatacontract(BasedatacontractBean basedatacontract) {
        this.basedatacontract = basedatacontract;
    }

    public static class PersonDescriptionListBean {
        @Expose
        @SerializedName("ShoeSize")
        private String shoesize;
        @Expose
        @SerializedName("Nationality")
        private String nationality;
        @Expose
        @SerializedName("Height")
        private String height;
        @Expose
        @SerializedName("Handedness")
        private String handedness;
        @Expose
        @SerializedName("Hair")
        private String hair;
        @Expose
        @SerializedName("Glasses")
        private String glasses;
        @Expose
        @SerializedName("FacialHair")
        private String facialhair;
        @Expose
        @SerializedName("EyeColour")
        private String eyecolour;
        @Expose
        @SerializedName("Build")
        private String build;
        @Expose
        @SerializedName("Accent")
        private String accent;

        public String getShoesize() {
            return shoesize;
        }

        public void setShoesize(String shoesize) {
            this.shoesize = shoesize;
        }

        public String getNationality() {
            return nationality;
        }

        public void setNationality(String nationality) {
            this.nationality = nationality;
        }

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
        }

        public String getHandedness() {
            return handedness;
        }

        public void setHandedness(String handedness) {
            this.handedness = handedness;
        }

        public String getHair() {
            return hair;
        }

        public void setHair(String hair) {
            this.hair = hair;
        }

        public String getGlasses() {
            return glasses;
        }

        public void setGlasses(String glasses) {
            this.glasses = glasses;
        }

        public String getFacialhair() {
            return facialhair;
        }

        public void setFacialhair(String facialhair) {
            this.facialhair = facialhair;
        }

        public String getEyecolour() {
            return eyecolour;
        }

        public void setEyecolour(String eyecolour) {
            this.eyecolour = eyecolour;
        }

        public String getBuild() {
            return build;
        }

        public void setBuild(String build) {
            this.build = build;
        }

        public String getAccent() {
            return accent;
        }

        public void setAccent(String accent) {
            this.accent = accent;
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
