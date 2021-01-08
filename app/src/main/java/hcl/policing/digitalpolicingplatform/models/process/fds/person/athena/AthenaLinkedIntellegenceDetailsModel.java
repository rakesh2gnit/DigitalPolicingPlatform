package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AthenaLinkedIntellegenceDetailsModel {

    @Expose
    @SerializedName("IntelligenceDetailResponse")
    private IntelligencedetailresponseBean intelligencedetailresponse;

    public IntelligencedetailresponseBean getIntelligencedetailresponse() {
        return intelligencedetailresponse;
    }

    public void setIntelligencedetailresponse(IntelligencedetailresponseBean intelligencedetailresponse) {
        this.intelligencedetailresponse = intelligencedetailresponse;
    }

    public static class IntelligencedetailresponseBean {
        @Expose
        @SerializedName("shortSummary")
        private String shortsummary;
        @Expose
        @SerializedName("Ward")
        private String ward;
        @Expose
        @SerializedName("TypeOfInfo")
        private String typeofinfo;
        @Expose
        @SerializedName("SourceCode")
        private String sourcecode;
        @Expose
        @SerializedName("Source")
        private SourceBean source;
        @Expose
        @SerializedName("Sensitive")
        private String sensitive;
        @Expose
        @SerializedName("SecureData")
        private boolean securedata;
        @Expose
        @SerializedName("SanitisedText")
        private String sanitisedtext;
        @Expose
        @SerializedName("RiskAssessments")
        private List<RiskAssessmentsBean> riskassessments;
        @Expose
        @SerializedName("ReportTitle")
        private String reporttitle;
        @Expose
        @SerializedName("Provenance")
        private String provenance;
        @Expose
        @SerializedName("Priority")
        private String priority;
        @Expose
        @SerializedName("OvertFlag")
        private boolean overtflag;
        @Expose
        @SerializedName("NIMLevel")
        private String nimlevel;
        @Expose
        @SerializedName("IntelligenceNumber")
        private String intelligencenumber;
        @Expose
        @SerializedName("IntelligenceCommunications")
        private List<String> intelligencecommunications;
        @Expose
        @SerializedName("ID")
        private String id;
        @Expose
        @SerializedName("HandlingCondition")
        private String handlingcondition;
        @Expose
        @SerializedName("Handling")
        private String handling;
        @Expose
        @SerializedName("Force")
        private String force;
        @Expose
        @SerializedName("FlagMessage")
        private String flagmessage;
        @Expose
        @SerializedName("FlagDescription")
        private String flagdescription;
        @Expose
        @SerializedName("EventFromTo")
        private String eventfromto;
        @Expose
        @SerializedName("Evaluation")
        private String evaluation;
        @Expose
        @SerializedName("District")
        private String district;
        @Expose
        @SerializedName("DateAdded")
        private String dateadded;
        @Expose
        @SerializedName("Date")
        private String date;
        @Expose
        @SerializedName("BCU")
        private String bcu;

        public String getShortsummary() {
            return shortsummary;
        }

        public void setShortsummary(String shortsummary) {
            this.shortsummary = shortsummary;
        }

        public String getWard() {
            return ward;
        }

        public void setWard(String ward) {
            this.ward = ward;
        }

        public String getTypeofinfo() {
            return typeofinfo;
        }

        public void setTypeofinfo(String typeofinfo) {
            this.typeofinfo = typeofinfo;
        }

        public String getSourcecode() {
            return sourcecode;
        }

        public void setSourcecode(String sourcecode) {
            this.sourcecode = sourcecode;
        }

        public SourceBean getSource() {
            return source;
        }

        public void setSource(SourceBean source) {
            this.source = source;
        }

        public String getSensitive() {
            return sensitive;
        }

        public void setSensitive(String sensitive) {
            this.sensitive = sensitive;
        }

        public boolean getSecuredata() {
            return securedata;
        }

        public void setSecuredata(boolean securedata) {
            this.securedata = securedata;
        }

        public String getSanitisedtext() {
            return sanitisedtext;
        }

        public void setSanitisedtext(String sanitisedtext) {
            this.sanitisedtext = sanitisedtext;
        }

        public List<RiskAssessmentsBean> getRiskassessments() {
            return riskassessments;
        }

        public void setRiskassessments(List<RiskAssessmentsBean> riskassessments) {
            this.riskassessments = riskassessments;
        }

        public String getReporttitle() {
            return reporttitle;
        }

        public void setReporttitle(String reporttitle) {
            this.reporttitle = reporttitle;
        }

        public String getProvenance() {
            return provenance;
        }

        public void setProvenance(String provenance) {
            this.provenance = provenance;
        }

        public String getPriority() {
            return priority;
        }

        public void setPriority(String priority) {
            this.priority = priority;
        }

        public boolean getOvertflag() {
            return overtflag;
        }

        public void setOvertflag(boolean overtflag) {
            this.overtflag = overtflag;
        }

        public String getNimlevel() {
            return nimlevel;
        }

        public void setNimlevel(String nimlevel) {
            this.nimlevel = nimlevel;
        }

        public String getIntelligencenumber() {
            return intelligencenumber;
        }

        public void setIntelligencenumber(String intelligencenumber) {
            this.intelligencenumber = intelligencenumber;
        }

        public List<String> getIntelligencecommunications() {
            return intelligencecommunications;
        }

        public void setIntelligencecommunications(List<String> intelligencecommunications) {
            this.intelligencecommunications = intelligencecommunications;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHandlingcondition() {
            return handlingcondition;
        }

        public void setHandlingcondition(String handlingcondition) {
            this.handlingcondition = handlingcondition;
        }

        public String getHandling() {
            return handling;
        }

        public void setHandling(String handling) {
            this.handling = handling;
        }

        public String getForce() {
            return force;
        }

        public void setForce(String force) {
            this.force = force;
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

        public String getEventfromto() {
            return eventfromto;
        }

        public void setEventfromto(String eventfromto) {
            this.eventfromto = eventfromto;
        }

        public String getEvaluation() {
            return evaluation;
        }

        public void setEvaluation(String evaluation) {
            this.evaluation = evaluation;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getDateadded() {
            return dateadded;
        }

        public void setDateadded(String dateadded) {
            this.dateadded = dateadded;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getBcu() {
            return bcu;
        }

        public void setBcu(String bcu) {
            this.bcu = bcu;
        }
    }

    public static class SourceBean {
        @Expose
        @SerializedName("SubmittingOfficer")
        private String submittingofficer;
        @Expose
        @SerializedName("SourceOfInfo")
        private String sourceofinfo;
        @Expose
        @SerializedName("OtherSource")
        private String othersource;
        @Expose
        @SerializedName("OtherReference")
        private String otherreference;
        @Expose
        @SerializedName("OtherFouceOfficerDetails")
        private String otherfouceofficerdetails;
        @Expose
        @SerializedName("OtherForce")
        private String otherforce;

        @Expose
        @SerializedName("MemberPublicName")
        private String memberPublicName;

        @Expose
        @SerializedName("OrganisationName")
        private String organisationname;
        @Expose
        @SerializedName("ISRNumber")
        private String isrnumber;
        @Expose
        @SerializedName("ID")
        private String id;
        @Expose
        @SerializedName("DOB")
        private String dob;

        public String getSubmittingofficer() {
            return submittingofficer;
        }

        public void setSubmittingofficer(String submittingofficer) {
            this.submittingofficer = submittingofficer;
        }

        public String getSourceofinfo() {
            return sourceofinfo;
        }

        public void setSourceofinfo(String sourceofinfo) {
            this.sourceofinfo = sourceofinfo;
        }

        public String getOthersource() {
            return othersource;
        }

        public void setOthersource(String othersource) {
            this.othersource = othersource;
        }

        public String getOtherreference() {
            return otherreference;
        }

        public void setOtherreference(String otherreference) {
            this.otherreference = otherreference;
        }

        public String getOtherfouceofficerdetails() {
            return otherfouceofficerdetails;
        }

        public void setOtherfouceofficerdetails(String otherfouceofficerdetails) {
            this.otherfouceofficerdetails = otherfouceofficerdetails;
        }

        public String getOtherforce() {
            return otherforce;
        }

        public void setOtherforce(String otherforce) {
            this.otherforce = otherforce;
        }
        public String getMemberPublicName() {
            return memberPublicName;
        }

        public void setMemberPublicName(String memberPublicName) {
            this.memberPublicName = memberPublicName;
        }

        public String getOrganisationname() {
            return organisationname;
        }

        public void setOrganisationname(String organisationname) {
            this.organisationname = organisationname;
        }

        public String getIsrnumber() {
            return isrnumber;
        }

        public void setIsrnumber(String isrnumber) {
            this.isrnumber = isrnumber;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }
    }

    public static class RiskAssessmentsBean {
        @Expose
        @SerializedName("Summary")
        private String summary;
        @Expose
        @SerializedName("SecureData")
        private boolean securedata;
        @Expose
        @SerializedName("OvertFlag")
        private boolean overtflag;
        @Expose
        @SerializedName("ID")
        private String id;

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
