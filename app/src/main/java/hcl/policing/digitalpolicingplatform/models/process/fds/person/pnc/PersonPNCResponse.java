package hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonPNCResponse {

    @Expose
    @SerializedName("PersonByName")
    private PersonbynameBean personbyname;
    @Expose
    @SerializedName("BaseDataContract")
    private BasedatacontractBean basedatacontract;

    public PersonbynameBean getPersonbyname() {
        return personbyname;
    }

    public void setPersonbyname(PersonbynameBean personbyname) {
        this.personbyname = personbyname;
    }

    public BasedatacontractBean getBasedatacontract() {
        return basedatacontract;
    }

    public void setBasedatacontract(BasedatacontractBean basedatacontract) {
        this.basedatacontract = basedatacontract;
    }

    public static class PersonbynameBean {
        @Expose
        @SerializedName("TotalRecordCountString")
        private String totalrecordcountstring;
        @Expose
        @SerializedName("TotalRecordCount")
        private String totalrecordcount;
        @Expose
        @SerializedName("TotalPageCount")
        private String totalpagecount;
        @Expose
        @SerializedName("PersonList")
        private List<PNCPersonlistBean> personlist;
        @Expose
        @SerializedName("CurrentPage")
        private String currentpage;

        public String getTotalrecordcountstring() {
            return totalrecordcountstring;
        }

        public void setTotalrecordcountstring(String totalrecordcountstring) {
            this.totalrecordcountstring = totalrecordcountstring;
        }

        public String getTotalrecordcount() {
            return totalrecordcount;
        }

        public void setTotalrecordcount(String totalrecordcount) {
            this.totalrecordcount = totalrecordcount;
        }

        public String getTotalpagecount() {
            return totalpagecount;
        }

        public void setTotalpagecount(String totalpagecount) {
            this.totalpagecount = totalpagecount;
        }

        public List<PNCPersonlistBean> getPersonlist() {
            return personlist;
        }

        public void setPersonlist(List<PNCPersonlistBean> personlist) {
            this.personlist = personlist;
        }

        public String getCurrentpage() {
            return currentpage;
        }

        public void setCurrentpage(String currentpage) {
            this.currentpage = currentpage;
        }
    }

    public static class PNCPersonlistBean {
        @Expose
        @SerializedName("Warnings")
        private String warnings;
        @Expose
        @SerializedName("WantedandMissingCount")
        private String wantedandmissingcount;
        @Expose
        @SerializedName("PlaceofBirth")
        private String placeofbirth;
        @Expose
        @SerializedName("PhotoLocationCount")
        private String photolocationcount;
        @Expose
        @SerializedName("PNCID")
        private String pncid;
        @Expose
        @SerializedName("OtherInformation")
        private String otherinformation;
        @Expose
        @SerializedName("OperationalInfoCount")
        private String operationalinfocount;
        @Expose
        @SerializedName("NominalAddress")
        private String nominaladdress;
        @Expose
        @SerializedName("Name")
        private String name;
        @Expose
        @SerializedName("MarksScars")
        private String marksscars;
        @Expose
        @SerializedName("LastUpdatedDateTime")
        private String lastupdateddatetime;
        @Expose
        @SerializedName("LastName")
        private String lastname;
        @Expose
        @SerializedName("LastAddressTypeandDate")
        private String lastaddresstypeanddate;
        @Expose
        @SerializedName("InformationMarker")
        private String informationmarker;
        @Expose
        @SerializedName("ImpendingProsecution")
        private String impendingprosecution;
        @Expose
        @SerializedName("Gender")
        private String gender;
        @Expose
        @SerializedName("FirstName")
        private String firstname;
        @Expose
        @SerializedName("Ethnicity")
        private String ethnicity;
        @Expose
        @SerializedName("DisqualifiedDriverCount")
        private String disqualifieddrivercount;
        @Expose
        @SerializedName("DisposalSummaryCount")
        private String disposalsummarycount;
        @Expose
        @SerializedName("DisposableHistoryCount")
        private String disposablehistorycount;
        @Expose
        @SerializedName("DescriptionCount")
        private String descriptioncount;
        @Expose
        @SerializedName("DOB")
        private String dob;
        @Expose
        @SerializedName("DNACount")
        private String dnacount;
        @Expose
        @SerializedName("BailConditionsCount")
        private String bailconditionscount;
        @Expose
        @SerializedName("ArrestRemandCount")
        private String arrestremandcount;
        @Expose
        @SerializedName("AliasNickNameCount")
        private String aliasnicknamecount;
        @Expose
        @SerializedName("AliasDOBCount")
        private String aliasdobcount;
        @Expose
        @SerializedName("AliasCount")
        private String aliascount;
        @Expose
        @SerializedName("AddressCount")
        private String addresscount;

        public String getWarnings() {
            return warnings;
        }

        public void setWarnings(String warnings) {
            this.warnings = warnings;
        }

        public String getWantedandmissingcount() {
            return wantedandmissingcount;
        }

        public void setWantedandmissingcount(String wantedandmissingcount) {
            this.wantedandmissingcount = wantedandmissingcount;
        }

        public String getPlaceofbirth() {
            return placeofbirth;
        }

        public void setPlaceofbirth(String placeofbirth) {
            this.placeofbirth = placeofbirth;
        }

        public String getPhotolocationcount() {
            return photolocationcount;
        }

        public void setPhotolocationcount(String photolocationcount) {
            this.photolocationcount = photolocationcount;
        }

        public String getPncid() {
            return pncid;
        }

        public void setPncid(String pncid) {
            this.pncid = pncid;
        }

        public String getOtherinformation() {
            return otherinformation;
        }

        public void setOtherinformation(String otherinformation) {
            this.otherinformation = otherinformation;
        }

        public String getOperationalinfocount() {
            return operationalinfocount;
        }

        public void setOperationalinfocount(String operationalinfocount) {
            this.operationalinfocount = operationalinfocount;
        }

        public String getNominaladdress() {
            return nominaladdress;
        }

        public void setNominaladdress(String nominaladdress) {
            this.nominaladdress = nominaladdress;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getMarksscars() {
            return marksscars;
        }

        public void setMarksscars(String marksscars) {
            this.marksscars = marksscars;
        }

        public String getLastupdateddatetime() {
            return lastupdateddatetime;
        }

        public void setLastupdateddatetime(String lastupdateddatetime) {
            this.lastupdateddatetime = lastupdateddatetime;
        }

        public String getLastname() {
            return lastname;
        }

        public void setLastname(String lastname) {
            this.lastname = lastname;
        }

        public String getLastaddresstypeanddate() {
            return lastaddresstypeanddate;
        }

        public void setLastaddresstypeanddate(String lastaddresstypeanddate) {
            this.lastaddresstypeanddate = lastaddresstypeanddate;
        }

        public String getInformationmarker() {
            return informationmarker;
        }

        public void setInformationmarker(String informationmarker) {
            this.informationmarker = informationmarker;
        }

        public String getImpendingprosecution() {
            return impendingprosecution;
        }

        public void setImpendingprosecution(String impendingprosecution) {
            this.impendingprosecution = impendingprosecution;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getFirstname() {
            return firstname;
        }

        public void setFirstname(String firstname) {
            this.firstname = firstname;
        }

        public String getEthnicity() {
            return ethnicity;
        }

        public void setEthnicity(String ethnicity) {
            this.ethnicity = ethnicity;
        }

        public String getDisqualifieddrivercount() {
            return disqualifieddrivercount;
        }

        public void setDisqualifieddrivercount(String disqualifieddrivercount) {
            this.disqualifieddrivercount = disqualifieddrivercount;
        }

        public String getDisposalsummarycount() {
            return disposalsummarycount;
        }

        public void setDisposalsummarycount(String disposalsummarycount) {
            this.disposalsummarycount = disposalsummarycount;
        }

        public String getDisposablehistorycount() {
            return disposablehistorycount;
        }

        public void setDisposablehistorycount(String disposablehistorycount) {
            this.disposablehistorycount = disposablehistorycount;
        }

        public String getDescriptioncount() {
            return descriptioncount;
        }

        public void setDescriptioncount(String descriptioncount) {
            this.descriptioncount = descriptioncount;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getDnacount() {
            return dnacount;
        }

        public void setDnacount(String dnacount) {
            this.dnacount = dnacount;
        }

        public String getBailconditionscount() {
            return bailconditionscount;
        }

        public void setBailconditionscount(String bailconditionscount) {
            this.bailconditionscount = bailconditionscount;
        }

        public String getArrestremandcount() {
            return arrestremandcount;
        }

        public void setArrestremandcount(String arrestremandcount) {
            this.arrestremandcount = arrestremandcount;
        }

        public String getAliasnicknamecount() {
            return aliasnicknamecount;
        }

        public void setAliasnicknamecount(String aliasnicknamecount) {
            this.aliasnicknamecount = aliasnicknamecount;
        }

        public String getAliasdobcount() {
            return aliasdobcount;
        }

        public void setAliasdobcount(String aliasdobcount) {
            this.aliasdobcount = aliasdobcount;
        }

        public String getAliascount() {
            return aliascount;
        }

        public void setAliascount(String aliascount) {
            this.aliascount = aliascount;
        }

        public String getAddresscount() {
            return addresscount;
        }

        public void setAddresscount(String addresscount) {
            this.addresscount = addresscount;
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
        @SerializedName("LayerName")
        private String layername;
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

        public String getLayername() {
            return layername;
        }

        public void setLayername(String layername) {
            this.layername = layername;
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
