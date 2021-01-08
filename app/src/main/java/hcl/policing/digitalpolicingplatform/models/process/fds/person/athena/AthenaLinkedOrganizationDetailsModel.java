package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AthenaLinkedOrganizationDetailsModel {

    @Expose
    @SerializedName("organisationDetailResponse")
    private OrganisationdetailresponseBean organisationdetailresponse;

    public OrganisationdetailresponseBean getOrganisationdetailresponse() {
        return organisationdetailresponse;
    }

    public void setOrganisationdetailresponse(OrganisationdetailresponseBean organisationdetailresponse) {
        this.organisationdetailresponse = organisationdetailresponse;
    }

    public static class OrganisationdetailresponseBean {
        @Expose
        @SerializedName("TypeTwo")
        private String typetwo;
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("SecureData")
        private boolean securedata;
        @Expose
        @SerializedName("OvertFlag")
        private boolean overtflag;
        @Expose
        @SerializedName("OrganisationRemarks")
        private String organisationremarks;
        @Expose
        @SerializedName("Name")
        private String name;
        @Expose
        @SerializedName("ID")
        private String id;
        @Expose
        @SerializedName("FlagMessage")
        private String flagmessage;
        @Expose
        @SerializedName("FlagDescription")
        private String flagdescription;
        @Expose
        @SerializedName("Branch")
        private String branch;

        public String getTypetwo() {
            return typetwo;
        }

        public void setTypetwo(String typetwo) {
            this.typetwo = typetwo;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean getSecuredata() {
            return securedata;
        }

        public void setSecuredata(boolean securedata) {
            this.securedata = securedata;
        }

        public boolean getOvertflag() {
            return overtflag;
        }

        public void setOvertflag(boolean overtflag) {
            this.overtflag = overtflag;
        }

        public String getOrganisationremarks() {
            return organisationremarks;
        }

        public void setOrganisationremarks(String organisationremarks) {
            this.organisationremarks = organisationremarks;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFlagmessage() {
            return flagmessage;
        }

        public void setFlagmessage(String flagmessage) {
            this.flagmessage = flagmessage;
        }

        public String getFlagdescription() {
            return flagdescription;
        }

        public void setFlagdescription(String flagdescription) {
            this.flagdescription = flagdescription;
        }

        public String getBranch() {
            return branch;
        }

        public void setBranch(String branch) {
            this.branch = branch;
        }
    }
}
