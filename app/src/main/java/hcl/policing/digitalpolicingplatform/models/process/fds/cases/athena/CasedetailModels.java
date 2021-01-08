package hcl.policing.digitalpolicingplatform.models.process.fds.cases.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.PersonModel;

public class CasedetailModels implements Serializable {
    @Expose
    @SerializedName("Warnings")
    private List<String> warnings;
    @Expose
    @SerializedName("UnitCode")
    private String unitcode;
    @Expose
    @SerializedName("Status")
    private String status;
    @Expose
    @SerializedName("SecureData")
    private boolean securedata;
    @Expose
    @SerializedName("Persons")
    private List<PersonModel> persons;
    @Expose
    @SerializedName("OvertFlag")
    private boolean overtflag;
    @Expose
    @SerializedName("OIC")
    private String oic;
    @Expose
    @SerializedName("ID")
    private String id;
    @Expose
    @SerializedName("FileSubType")
    private String filesubtype;
    @Expose
    @SerializedName("CaseWorker")
    private String caseworker;
    @Expose
    @SerializedName("WitnessCareOfficer")
    private String witnesscareofficer;
    @Expose
    @SerializedName("Hearings")
    private List<HearingsModel> hearings;
    @Expose
    @SerializedName("FlagMessage")
    private String flagmessage;
    @Expose
    @SerializedName("FlagDescription")
    private String flagdescription;
    @Expose
    @SerializedName("FileType")
    private String filetype;
    @Expose
    @SerializedName("DocumentStatus")
    private String documentstatus;
    @Expose
    @SerializedName("Defendants")
    private List<DefendantsModel> defendants;
    @Expose
    @SerializedName("CreatedOn")
    private String createdon;
    @Expose
    @SerializedName("CaseReference")
    private String casereference;
    @Expose
    @SerializedName("CPSCaseStatus")
    private String cpscasestatus;
    @Expose
    @SerializedName("CPSBranch")
    private String cpsbranch;

    public List<String> getWarnings() {
        return warnings;
    }

    public void setWarnings(List<String> warnings) {
        this.warnings = warnings;
    }

    public String getUnitcode() {
        return unitcode;
    }

    public void setUnitcode(String unitcode) {
        this.unitcode = unitcode;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public boolean getSecuredata() {
        return securedata;
    }

    public void setSecuredata(boolean securedata) {
        this.securedata = securedata;
    }

    public List<PersonModel> getPersons() {
        return persons;
    }

    public void setPersons(List<PersonModel> persons) {
        this.persons = persons;
    }

    public boolean getOvertflag() {
        return overtflag;
    }

    public void setOvertflag(boolean overtflag) {
        this.overtflag = overtflag;
    }

    public String getOic() {
        return oic;
    }

    public void setOic(String oic) {
        this.oic = oic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFilesubtype() {
        return filesubtype;
    }

    public void setFilesubtype(String filesubtype) {
        this.filesubtype = filesubtype;
    }

    public String getCaseworker() {
        return caseworker;
    }

    public void setCaseworker(String caseworker) {
        this.caseworker = caseworker;
    }

    public String getWitnesscareofficer() {
        return witnesscareofficer;
    }

    public void setWitnesscareofficer(String witnesscareofficer) {
        this.witnesscareofficer = witnesscareofficer;
    }


    public List<HearingsModel> getHearings() {
        return hearings;
    }

    public void setHearings(List<HearingsModel> hearings) {
        this.hearings = hearings;
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

    public String getFiletype() {
        return filetype;
    }

    public void setFiletype(String filetype) {
        this.filetype = filetype;
    }

    public String getDocumentstatus() {
        return documentstatus;
    }

    public void setDocumentstatus(String documentstatus) {
        this.documentstatus = documentstatus;
    }

    public List<DefendantsModel> getDefendants() {
        return defendants;
    }

    public void setDefendants(List<DefendantsModel> defendants) {
        this.defendants = defendants;
    }

    public String getCreatedon() {
        return createdon;
    }

    public void setCreatedon(String createdon) {
        this.createdon = createdon;
    }

    public String getCasereference() {
        return casereference;
    }

    public void setCasereference(String casereference) {
        this.casereference = casereference;
    }

    public String getCpscasestatus() {
        return cpscasestatus;
    }

    public void setCpscasestatus(String cpscasestatus) {
        this.cpscasestatus = cpscasestatus;
    }

    public String getCpsbranch() {
        return cpsbranch;
    }

    public void setCpsbranch(String cpsbranch) {
        this.cpsbranch = cpsbranch;
    }
}
