package hcl.policing.digitalpolicingplatform.models.process.fds.pole;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class PersonList implements Serializable {

    @Expose
    @SerializedName("UpdatedDate")
    private String updateddate;
    @Expose
    @SerializedName("UpdatedBy")
    private String updatedby;
    @SerializedName("Address")
    @Expose
    private String address;
    @Expose
    @SerializedName("URN")
    private String urn;
    @Expose
    @SerializedName("Surname")
    private String surname;
    @Expose
    @SerializedName("PhoneNumbers")
    private List<PhoneNumbersBean> phonenumbers;
    @Expose
    @SerializedName("Occupations")
    private OccupationsBean occupations;
    @Expose
    @SerializedName("NameList")
    private List<NameListBean> namelist;
    @Expose
    @SerializedName("MainName")
    private String mainName;
    @Expose
    @SerializedName("MainAddress")
    private String mainAddress;
    @SerializedName("Marker")
    @Expose
    private String marker;
    @SerializedName("MetricHeight")
    @Expose
    private String metricHeight;
    @SerializedName("PNCID")
    @Expose
    private String PNCID;
    @Expose
    @SerializedName("ID")
    private String id;
    @Expose
    @SerializedName("History")
    private List<HistoryBean> history;
    @Expose
    @SerializedName("Gender")
    private String gender;
    @Expose
    @SerializedName("Forename")
    private String forename;
    @Expose
    @SerializedName("Flags")
    private FlagsBean flags;
    @SerializedName("FlagText")
    @Expose
    private String flagText;
    @Expose
    @SerializedName("ExtraProp2")
    private String extraprop2;
    @SerializedName("Nationality")
    @Expose
    private String nationality;
    @SerializedName("OfficerDefinedEthnicity")
    @Expose
    private String officerDefinedEthnicity;
    @Expose
    @SerializedName("ExtraProp1")
    private String extraprop1;
    @Expose
    @SerializedName("EmailAddresses")
    private List<EmailAddressesBean> emailaddresses;
    @Expose
    @SerializedName("DescriptiveFeatures")
    private DescriptivefeaturesBean descriptivefeatures;
    @Expose
    @SerializedName("DateOfBirth")
    private String dateOfBirth;
    @Expose
    @SerializedName("CreatedDate")
    private String createddate;
    @Expose
    @SerializedName("CreatedBy")
    private String createdby;
    @Expose
    @SerializedName("Association")
    private AssociationBean association;
    @Expose
    @SerializedName("Age")
    private String age;
    @Expose
    @SerializedName("RoleText")
    private String roleText;
    @Expose
    @SerializedName("AddressList")
    private List<AddressListBean> addresslist;
    @Expose
    @SerializedName("ActionMarkers")
    private List<ActionMarkersBean> actionmarkers;
    @Expose
    @SerializedName("WarningSignals")
    private List<WarningSignalsBean> warningsignals;

    public String getUpdateddate() {
        return updateddate;
    }

    public void setUpdateddate(String updateddate) {
        this.updateddate = updateddate;
    }

    public String getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(String updatedby) {
        this.updatedby = updatedby;
    }

    public String getUrn() {
        return urn;
    }

    public void setUrn(String urn) {
        this.urn = urn;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public List<PhoneNumbersBean> getPhonenumbers() {
        return phonenumbers;
    }

    public void setPhonenumbers(List<PhoneNumbersBean> phonenumbers) {
        this.phonenumbers = phonenumbers;
    }

    public OccupationsBean getOccupations() {
        return occupations;
    }

    public void setOccupations(OccupationsBean occupations) {
        this.occupations = occupations;
    }

    public List<NameListBean> getNamelist() {
        return namelist;
    }

    public void setNamelist(List<NameListBean> namelist) {
        this.namelist = namelist;
    }

    public String getMainName() {
        return mainName;
    }

    public void setMainName(String mainname) {
        this.mainName = mainname;
    }

    public String getMainAddress() {
        return mainAddress;
    }

    public void setMainAddress(String mainAddress) {
        this.mainAddress = mainAddress;
    }

    public String getMarker() {
        return marker;
    }

    public void setMarker(String marker) {
        this.marker = marker;
    }

    public String getMetricHeight() {
        return metricHeight;
    }

    public void setMetricHeight(String metricHeight) {
        this.metricHeight = metricHeight;
    }

    public String getPNCID() {
        return PNCID;
    }

    public void setPNCID(String PNCID) {
        this.PNCID = PNCID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<HistoryBean> getHistory() {
        return history;
    }

    public void setHistory(List<HistoryBean> history) {
        this.history = history;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getForename() {
        return forename;
    }

    public void setForename(String forename) {
        this.forename = forename;
    }

    public FlagsBean getFlags() {
        return flags;
    }

    public void setFlags(FlagsBean flags) {
        this.flags = flags;
    }

    public String getFlagText() {
        return flagText;
    }

    public void setFlagText(String flagText) {
        this.flagText = flagText;
    }

    public String getExtraprop2() {
        return extraprop2;
    }

    public void setExtraprop2(String extraprop2) {
        this.extraprop2 = extraprop2;
    }

    public String getOfficerDefinedEthnicity() {
        return officerDefinedEthnicity;
    }

    public void setOfficerDefinedEthnicity(String officerDefinedEthnicity) {
        this.officerDefinedEthnicity = officerDefinedEthnicity;
    }

    public String getExtraprop1() {
        return extraprop1;
    }

    public void setExtraprop1(String extraprop1) {
        this.extraprop1 = extraprop1;
    }

    public List<EmailAddressesBean> getEmailaddresses() {
        return emailaddresses;
    }

    public void setEmailaddresses(List<EmailAddressesBean> emailaddresses) {
        this.emailaddresses = emailaddresses;
    }

    public DescriptivefeaturesBean getDescriptivefeatures() {
        return descriptivefeatures;
    }

    public void setDescriptivefeatures(DescriptivefeaturesBean descriptivefeatures) {
        this.descriptivefeatures = descriptivefeatures;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateofbirth) {
        this.dateOfBirth = dateofbirth;
    }

    public String getCreateddate() {
        return createddate;
    }

    public void setCreateddate(String createddate) {
        this.createddate = createddate;
    }

    public String getCreatedby() {
        return createdby;
    }

    public void setCreatedby(String createdby) {
        this.createdby = createdby;
    }

    public AssociationBean getAssociation() {
        return association;
    }

    public void setAssociation(AssociationBean association) {
        this.association = association;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }
    public String getRoleText() {
        return roleText;
    }

    public void setRoleText(String roleText) {
        this.roleText = roleText;
    }

    public List<AddressListBean> getAddresslist() {
        return addresslist;
    }

    public void setAddresslist(List<AddressListBean> addresslist) {
        this.addresslist = addresslist;
    }

    public List<ActionMarkersBean> getActionmarkers() {
        return actionmarkers;
    }

    public void setActionmarkers(List<ActionMarkersBean> actionmarkers) {
        this.actionmarkers = actionmarkers;
    }

    public List<WarningSignalsBean> getWarningsignals() {
        return warningsignals;
    }

    public void setWarningsignals(List<WarningSignalsBean> warningsignals) {
        this.warningsignals = warningsignals;
    }

    public static class PhoneNumbersBean {
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("Number")
        private String number;
        @Expose
        @SerializedName("IsMain")
        private String ismain;
        @Expose
        @SerializedName("Extension")
        private String extension;
        @Expose
        @SerializedName("CountryCode")
        private String countrycode;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getNumber() {
            return number;
        }

        public void setNumber(String number) {
            this.number = number;
        }

        public String getIsmain() {
            return ismain;
        }

        public void setIsmain(String ismain) {
            this.ismain = ismain;
        }

        public String getExtension() {
            return extension;
        }

        public void setExtension(String extension) {
            this.extension = extension;
        }

        public String getCountrycode() {
            return countrycode;
        }

        public void setCountrycode(String countrycode) {
            this.countrycode = countrycode;
        }
    }

    public static class OccupationsBean {
        @Expose
        @SerializedName("UpdatedDate")
        private String updateddate;
        @Expose
        @SerializedName("UpdatedBy")
        private String updatedby;
        @SerializedName("ID")
        @Expose
        private String ID;
        @Expose
        @SerializedName("OccupationList")
        private List<OccupationListBean> occupationlist;
        @Expose
        @SerializedName("CreatedDate")
        private String createddate;
        @Expose
        @SerializedName("CreatedBy")
        private String createdby;

        public String getUpdateddate() {
            return updateddate;
        }

        public void setUpdateddate(String updateddate) {
            this.updateddate = updateddate;
        }

        public String getUpdatedby() {
            return updatedby;
        }

        public void setUpdatedby(String updatedby) {
            this.updatedby = updatedby;
        }

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public List<OccupationListBean> getOccupationlist() {
            return occupationlist;
        }

        public void setOccupationlist(List<OccupationListBean> occupationlist) {
            this.occupationlist = occupationlist;
        }

        public String getCreateddate() {
            return createddate;
        }

        public void setCreateddate(String createddate) {
            this.createddate = createddate;
        }

        public String getCreatedby() {
            return createdby;
        }

        public void setCreatedby(String createdby) {
            this.createdby = createdby;
        }
    }

    public static class OccupationListBean {
        @Expose
        @SerializedName("Occupation")
        private String occupation;

        public String getOccupation() {
            return occupation;
        }

        public void setOccupation(String occupation) {
            this.occupation = occupation;
        }
    }

    public static class NameListBean {
        @Expose
        @SerializedName("Title")
        private String title;
        @Expose
        @SerializedName("PNCFileName")
        private String pncfilename;
        @Expose
        @SerializedName("MainName")
        private String mainName;
        @Expose
        @SerializedName("Name")
        private String name;

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getPncfilename() {
            return pncfilename;
        }
        public String getMainName() {
            return mainName;
        }

        public void setMainName(String mainName) {
            this.mainName = mainName;
        }
        public void setPncfilename(String pncfilename) {
            this.pncfilename = pncfilename;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }


    public static class FlagsBean {
        @Expose
        @SerializedName("UpdatedDate")
        private String updateddate;
        @Expose
        @SerializedName("UpdatedBy")
        private String updatedby;
        @Expose
        @SerializedName("URN")
        private String urn;
        @Expose
        @SerializedName("CreatedDate")
        private String createddate;
        @Expose
        @SerializedName("CreatedBy")
        private String createdby;

        public String getUpdateddate() {
            return updateddate;
        }

        public void setUpdateddate(String updateddate) {
            this.updateddate = updateddate;
        }

        public String getUpdatedby() {
            return updatedby;
        }

        public void setUpdatedby(String updatedby) {
            this.updatedby = updatedby;
        }

        public String getUrn() {
            return urn;
        }

        public void setUrn(String urn) {
            this.urn = urn;
        }

        public String getCreateddate() {
            return createddate;
        }

        public void setCreateddate(String createddate) {
            this.createddate = createddate;
        }

        public String getCreatedby() {
            return createdby;
        }

        public void setCreatedby(String createdby) {
            this.createdby = createdby;
        }
    }

    public static class EmailAddressesBean {
        @Expose
        @SerializedName("IsMain")
        private String ismain;
        @Expose
        @SerializedName("EmailId")
        private String emailid;

        public String getIsmain() {
            return ismain;
        }

        public void setIsmain(String ismain) {
            this.ismain = ismain;
        }

        public String getEmailid() {
            return emailid;
        }

        public void setEmailid(String emailid) {
            this.emailid = emailid;
        }
    }


    public static class DescriptivefeaturesBean {
        @Expose
        @SerializedName("UpdatedDate")
        private String updateddate;
        @Expose
        @SerializedName("UpdatedBy")
        private String updatedby;
        @Expose
        @SerializedName("ID")
        private String id;
        @Expose
        @SerializedName("Hairs")
        private String hairs;
        @Expose
        @SerializedName("CreatedDate")
        private String createddate;
        @Expose
        @SerializedName("CreatedBy")
        private String createdby;

        public String getUpdateddate() {
            return updateddate;
        }

        public void setUpdateddate(String updateddate) {
            this.updateddate = updateddate;
        }

        public String getUpdatedby() {
            return updatedby;
        }

        public void setUpdatedby(String updatedby) {
            this.updatedby = updatedby;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getHairs() {
            return hairs;
        }

        public void setHairs(String hairs) {
            this.hairs = hairs;
        }

        public String getCreateddate() {
            return createddate;
        }

        public void setCreateddate(String createddate) {
            this.createddate = createddate;
        }

        public String getCreatedby() {
            return createdby;
        }

        public void setCreatedby(String createdby) {
            this.createdby = createdby;
        }
    }

    public static class AssociationBean {
        @Expose
        @SerializedName("PeopleList")
        private List<String> peoplelist;
        @Expose
        @SerializedName("OrganisationList")
        private List<String> organisationlist;
        @Expose
        @SerializedName("KnownAssociatesList")
        private List<KnownAssociatesListBean> knownassociateslist;

        public List<String> getPeoplelist() {
            return peoplelist;
        }

        public void setPeoplelist(List<String> peoplelist) {
            this.peoplelist = peoplelist;
        }

        public List<String> getOrganisationlist() {
            return organisationlist;
        }

        public void setOrganisationlist(List<String> organisationlist) {
            this.organisationlist = organisationlist;
        }

        public List<KnownAssociatesListBean> getKnownassociateslist() {
            return knownassociateslist;
        }

        public void setKnownassociateslist(List<KnownAssociatesListBean> knownassociateslist) {
            this.knownassociateslist = knownassociateslist;
        }
    }

    public static class KnownAssociatesListBean {
        @Expose
        @SerializedName("UpdatedDate")
        private String updateddate;
        @Expose
        @SerializedName("UpdatedBy")
        private String updatedby;
        @Expose
        @SerializedName("URN")
        private String urn;
        @Expose
        @SerializedName("RelationshipToPerson")
        private String relationshiptoperson;
        @Expose
        @SerializedName("Name")
        private String name;
        @Expose
        @SerializedName("ID")
        private String id;
        @Expose
        @SerializedName("DOB")
        private String dob;
        @Expose
        @SerializedName("CreatedDate")
        private String createddate;
        @Expose
        @SerializedName("CreatedBy")
        private String createdby;

        public String getUpdateddate() {
            return updateddate;
        }

        public void setUpdateddate(String updateddate) {
            this.updateddate = updateddate;
        }

        public String getUpdatedby() {
            return updatedby;
        }

        public void setUpdatedby(String updatedby) {
            this.updatedby = updatedby;
        }

        public String getUrn() {
            return urn;
        }

        public void setUrn(String urn) {
            this.urn = urn;
        }

        public String getRelationshiptoperson() {
            return relationshiptoperson;
        }

        public void setRelationshiptoperson(String relationshiptoperson) {
            this.relationshiptoperson = relationshiptoperson;
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

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getCreateddate() {
            return createddate;
        }

        public void setCreateddate(String createddate) {
            this.createddate = createddate;
        }

        public String getCreatedby() {
            return createdby;
        }

        public void setCreatedby(String createdby) {
            this.createdby = createdby;
        }
    }

    public static class AddressListBean {
        @Expose
        @SerializedName("Type")
        private String type;
        @Expose
        @SerializedName("Postcode")
        private String postcode;
        @Expose
        @SerializedName("Main")
        private String main;
        @Expose
        @SerializedName("ID")
        private String id;
        @Expose
        @SerializedName("Address")
        private String address;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getPostcode() {
            return postcode;
        }

        public void setPostcode(String postcode) {
            this.postcode = postcode;
        }

        public String getMain() {
            return main;
        }

        public void setMain(String main) {
            this.main = main;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }


}