package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import hcl.policing.digitalpolicingplatform.models.process.fds.investigation.athena.EnquiryLogsModel;

public class AthenaLinkedInvestigationDetailsModel {


    @Expose
    @SerializedName("investigationDetailResponse")
    private InvestigationdetailresponseBean investigationdetailresponse;

    public InvestigationdetailresponseBean getInvestigationdetailresponse() {
        return investigationdetailresponse;
    }

    public void setInvestigationdetailresponse(InvestigationdetailresponseBean investigationdetailresponse) {
        this.investigationdetailresponse = investigationdetailresponse;
    }

    public static class InvestigationdetailresponseBean {
        @Expose
        @SerializedName("Warnings")
        private List<String> warnings;
        @Expose
        @SerializedName("VictimIsCrown")
        private String victimiscrown;
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("Summary")
        private String summary;
        @Expose
        @SerializedName("SubGroup")
        private String subgroup;
        @Expose
        @SerializedName("Status")
        private String status;
        @Expose
        @SerializedName("SecureData")
        private boolean securedata;
        @Expose
        @SerializedName("Outcome")
        private String outcome;

        @Expose
        @SerializedName("ReportingOfficer")
        private String reportingofficer;
        @Expose
        @SerializedName("ReportedDate")
        private String reporteddate;
        @Expose
        @SerializedName("PrimaryOffence")
        private String primaryoffence;
        @Expose
        @SerializedName("OvertFlag")
        private boolean overtflag;
        @Expose
        @SerializedName("OIC_Unit")
        private String oicUnit;
        @Expose
        @SerializedName("OIC")
        private String oic;
        @Expose
        @SerializedName("Keywords")
        private String keywords;
        @Expose
        @SerializedName("InvestigationNumber")
        private String investigationnumber;
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
        @SerializedName("EventLocation")
        private String eventlocation;
        @Expose
        @SerializedName("EventDate")
        private String eventdate;
        @Expose
        @SerializedName("EnquiryLogs")
        private List<EnquiryLogsModel> enquirylogs;
        @Expose
        @SerializedName("DetailText")
        private String detailtext;
        @Expose
        @SerializedName("Categories")
        private String categories;
        @Expose
        @SerializedName("CC_Number")
        private String ccNumber;
        @Expose
        @SerializedName("CCCJS_Code")
        private String cccjsCode;
        @Expose
        @SerializedName("ACPO_Code")
        private String acpoCode;

        public List<String> getWarnings() {
            return warnings;
        }

        public void setWarnings(List<String> warnings) {
            this.warnings = warnings;
        }

        public String getVictimiscrown() {
            return victimiscrown;
        }

        public void setVictimiscrown(String victimiscrown) {
            this.victimiscrown = victimiscrown;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public String getSubgroup() {
            return subgroup;
        }

        public void setSubgroup(String subgroup) {
            this.subgroup = subgroup;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }
        public String getOutcome() {
            return outcome;
        }

        public void setOutcome(String outcome) {
            this.outcome = outcome;
        }

        public boolean getSecuredata() {
            return securedata;
        }

        public void setSecuredata(boolean securedata) {
            this.securedata = securedata;
        }

        public String getReportingofficer() {
            return reportingofficer;
        }

        public void setReportingofficer(String reportingofficer) {
            this.reportingofficer = reportingofficer;
        }

        public String getReporteddate() {
            return reporteddate;
        }

        public void setReporteddate(String reporteddate) {
            this.reporteddate = reporteddate;
        }

        public String getPrimaryoffence() {
            return primaryoffence;
        }

        public void setPrimaryoffence(String primaryoffence) {
            this.primaryoffence = primaryoffence;
        }

        public boolean getOvertflag() {
            return overtflag;
        }

        public void setOvertflag(boolean overtflag) {
            this.overtflag = overtflag;
        }

        public String getOicUnit() {
            return oicUnit;
        }

        public void setOicUnit(String oicUnit) {
            this.oicUnit = oicUnit;
        }

        public String getOic() {
            return oic;
        }

        public void setOic(String oic) {
            this.oic = oic;
        }

        public String getKeywords() {
            return keywords;
        }

        public void setKeywords(String keywords) {
            this.keywords = keywords;
        }

        public String getInvestigationnumber() {
            return investigationnumber;
        }

        public void setInvestigationnumber(String investigationnumber) {
            this.investigationnumber = investigationnumber;
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

        public String getEventlocation() {
            return eventlocation;
        }

        public void setEventlocation(String eventlocation) {
            this.eventlocation = eventlocation;
        }

        public String getEventdate() {
            return eventdate;
        }

        public void setEventdate(String eventdate) {
            this.eventdate = eventdate;
        }

        public List<EnquiryLogsModel> getEnquirylogs() {
            return enquirylogs;
        }

        public void setEnquirylogs(List<EnquiryLogsModel> enquirylogs) {
            this.enquirylogs = enquirylogs;
        }

        public String getDetailtext() {
            return detailtext;
        }

        public void setDetailtext(String detailtext) {
            this.detailtext = detailtext;
        }

        public String getCategories() {
            return categories;
        }

        public void setCategories(String categories) {
            this.categories = categories;
        }

        public String getCcNumber() {
            return ccNumber;
        }

        public void setCcNumber(String ccNumber) {
            this.ccNumber = ccNumber;
        }

        public String getCccjsCode() {
            return cccjsCode;
        }

        public void setCccjsCode(String cccjsCode) {
            this.cccjsCode = cccjsCode;
        }

        public String getAcpoCode() {
            return acpoCode;
        }

        public void setAcpoCode(String acpoCode) {
            this.acpoCode = acpoCode;
        }
    }

}
