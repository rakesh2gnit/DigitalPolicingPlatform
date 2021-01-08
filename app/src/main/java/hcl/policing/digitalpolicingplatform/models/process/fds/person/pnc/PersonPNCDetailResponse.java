package hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class PersonPNCDetailResponse {

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
        private List<PersonListBean> personlist;
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

        public List<PersonListBean> getPersonlist() {
            return personlist;
        }

        public void setPersonlist(List<PersonListBean> personlist) {
            this.personlist = personlist;
        }

        public String getCurrentpage() {
            return currentpage;
        }

        public void setCurrentpage(String currentpage) {
            this.currentpage = currentpage;
        }
    }

    public static class PersonListBean {
        @Expose
        @SerializedName("Warnings")
        private String warnings;
        @Expose
        @SerializedName("WarningandAlertCount")
        private String warningandalertcount;
        @Expose
        @SerializedName("WarningAlert")
        private String warningalert;
        @Expose
        @SerializedName("WantedandMissingCount")
        private String wantedandmissingcount;
        @Expose
        @SerializedName("WantedMissing")
        private String wantedmissing;
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("SequenceNo")
        private String sequenceno;
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
        @SerializedName("MarksAndScarsCount")
        private String marksandscarscount;
        @Expose
        @SerializedName("LocalReferenceCount")
        private String localreferencecount;
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
        @SerializedName("InternalCrossReferencesCount")
        private String internalcrossreferencescount;
        @Expose
        @SerializedName("InformationMarkerCount")
        private String informationmarkercount;
        @Expose
        @SerializedName("InformationMarker")
        private String informationmarker;
        @Expose
        @SerializedName("ImpendingProsecution")
        private String impendingprosecution;
        @Expose
        @SerializedName("Height")
        private String height;
        @Expose
        @SerializedName("Gender")
        private String gender;
        @Expose
        @SerializedName("FirstName")
        private String firstname;
        @Expose
        @SerializedName("FireArmCertificateCount")
        private String firearmcertificatecount;
        @Expose
        @SerializedName("Ethnicity")
        private String ethnicity;
        @Expose
        @SerializedName("DisqualifiedDriverReport")
        private String disqualifieddriverreport;
        @Expose
        @SerializedName("DisqualifiedDriverCount")
        private String disqualifieddrivercount;
        @Expose
        @SerializedName("DisposalSummaryCount")
        private String disposalsummarycount;
        @Expose
        @SerializedName("DisposalSummary")
        private String disposalsummary;
        @Expose
        @SerializedName("DisposalHistory")
        private String disposalhistory;
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
        @SerializedName("DNAReport")
        private String dnareport;
        @Expose
        @SerializedName("DNACount")
        private String dnacount;
        @Expose
        @SerializedName("DLNo")
        private String dlno;
        @Expose
        @SerializedName("CRONo")
        private String crono;
        @Expose
        @SerializedName("BailConditionsCount")
        private String bailconditionscount;
        @Expose
        @SerializedName("BailConditions")
        private String bailconditions;
        @Expose
        @SerializedName("ArrestRemandCount")
        private String arrestremandcount;
        @Expose
        @SerializedName("AliasNicknameDOB")
        private String aliasnicknamedob;
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
        @SerializedName("Addresses")
        private String addresses;
        @Expose
        @SerializedName("AddressCount")
        private String addresscount;

        public String getWarnings() {
            return warnings;
        }

        public void setWarnings(String warnings) {
            this.warnings = warnings;
        }

        public String getWarningandalertcount() {
            return warningandalertcount;
        }

        public void setWarningandalertcount(String warningandalertcount) {
            this.warningandalertcount = warningandalertcount;
        }

        public String getWarningalert() {
            return warningalert;
        }

        public void setWarningalert(String warningalert) {
            this.warningalert = warningalert;
        }

        public String getWantedandmissingcount() {
            return wantedandmissingcount;
        }

        public void setWantedandmissingcount(String wantedandmissingcount) {
            this.wantedandmissingcount = wantedandmissingcount;
        }

        public String getWantedmissing() {
            return wantedmissing;
        }

        public void setWantedmissing(String wantedmissing) {
            this.wantedmissing = wantedmissing;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSequenceno() {
            return sequenceno;
        }

        public void setSequenceno(String sequenceno) {
            this.sequenceno = sequenceno;
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

        public String getMarksandscarscount() {
            return marksandscarscount;
        }

        public void setMarksandscarscount(String marksandscarscount) {
            this.marksandscarscount = marksandscarscount;
        }

        public String getLocalreferencecount() {
            return localreferencecount;
        }

        public void setLocalreferencecount(String localreferencecount) {
            this.localreferencecount = localreferencecount;
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

        public String getInternalcrossreferencescount() {
            return internalcrossreferencescount;
        }

        public void setInternalcrossreferencescount(String internalcrossreferencescount) {
            this.internalcrossreferencescount = internalcrossreferencescount;
        }

        public String getInformationmarkercount() {
            return informationmarkercount;
        }

        public void setInformationmarkercount(String informationmarkercount) {
            this.informationmarkercount = informationmarkercount;
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

        public String getHeight() {
            return height;
        }

        public void setHeight(String height) {
            this.height = height;
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

        public String getFirearmcertificatecount() {
            return firearmcertificatecount;
        }

        public void setFirearmcertificatecount(String firearmcertificatecount) {
            this.firearmcertificatecount = firearmcertificatecount;
        }

        public String getEthnicity() {
            return ethnicity;
        }

        public void setEthnicity(String ethnicity) {
            this.ethnicity = ethnicity;
        }

        public String getDisqualifieddriverreport() {
            return disqualifieddriverreport;
        }

        public void setDisqualifieddriverreport(String disqualifieddriverreport) {
            this.disqualifieddriverreport = disqualifieddriverreport;
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

        public String getDisposalsummary() {
            return disposalsummary;
        }

        public void setDisposalsummary(String disposalsummary) {
            this.disposalsummary = disposalsummary;
        }

        public String getDisposalhistory() {
            return disposalhistory;
        }

        public void setDisposalhistory(String disposalhistory) {
            this.disposalhistory = disposalhistory;
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

        public String getDnareport() {
            return dnareport;
        }

        public void setDnareport(String dnareport) {
            this.dnareport = dnareport;
        }

        public String getDnacount() {
            return dnacount;
        }

        public void setDnacount(String dnacount) {
            this.dnacount = dnacount;
        }

        public String getDlno() {
            return dlno;
        }

        public void setDlno(String dlno) {
            this.dlno = dlno;
        }

        public String getCrono() {
            return crono;
        }

        public void setCrono(String crono) {
            this.crono = crono;
        }

        public String getBailconditionscount() {
            return bailconditionscount;
        }

        public void setBailconditionscount(String bailconditionscount) {
            this.bailconditionscount = bailconditionscount;
        }

        public String getBailconditions() {
            return bailconditions;
        }

        public void setBailconditions(String bailconditions) {
            this.bailconditions = bailconditions;
        }

        public String getArrestremandcount() {
            return arrestremandcount;
        }

        public void setArrestremandcount(String arrestremandcount) {
            this.arrestremandcount = arrestremandcount;
        }

        public String getAliasnicknamedob() {
            return aliasnicknamedob;
        }

        public void setAliasnicknamedob(String aliasnicknamedob) {
            this.aliasnicknamedob = aliasnicknamedob;
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

        public String getAddresses() {
            return addresses;
        }

        public void setAddresses(String addresses) {
            this.addresses = addresses;
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
