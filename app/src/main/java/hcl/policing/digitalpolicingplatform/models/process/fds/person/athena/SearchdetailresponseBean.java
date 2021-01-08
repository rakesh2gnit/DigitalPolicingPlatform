package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import hcl.policing.digitalpolicingplatform.models.process.fds.address.athena.AddressModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.cases.athena.CasedetailModels;
import hcl.policing.digitalpolicingplatform.models.process.fds.communication.athena.CommunicationModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.courtwarrant.athena.CourtWarrantModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.investigation.athena.InvestigationModels;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.athena.VehicleModel;

public class SearchdetailresponseBean implements Serializable {
    @Expose
    @SerializedName("linkedVehicle")
    private List<LinkedVehicleBean> linkedvehicle;
    @Expose
    @SerializedName("linkedSearches")
    private List<LinkedSearchesBean> linkedsearches;
    @Expose
    @SerializedName("linkedSample")
    private List<LinkedSampleBean> linkedsample;
    @Expose
    @SerializedName("linkedRiskAssessment")
    private List<LinkedRiskAssessmentBean> linkedriskassessment;
    @Expose
    @SerializedName("linkedPropertyItems")
    private List<LinkedPropertyItemsBean> linkedpropertyitems;
    @Expose
    @SerializedName("linkedPersonSearched")
    private List<LinkedPersonSearchedBean> linkedpersonsearched;
    @Expose
    @SerializedName("linkedPerson")
    private List<LinkedPersonBean> linkedperson;
    @Expose
    @SerializedName("linkedOrganisation")
    private List<LinkedOrganisationBean> linkedorganisation;
    @Expose
    @SerializedName("linkedOperation")
    private List<String> linkedoperation;
    @Expose
    @SerializedName("linkedOffence")
    private List<LinkedOffenceBean> linkedoffence;
    @Expose
    @SerializedName("linkedObjectIteration")
    private List<LinkedObjectIterationBean> linkedobjectiteration;
    @Expose
    @SerializedName("linkedLocation")
    private List<LinkedLocationBean> linkedlocation;
    @Expose
    @SerializedName("linkedInvestigation")
    private List<LinkedInvestigationBean> linkedinvestigation;
    @Expose
    @SerializedName("linkedIntelligence")
    private List<LinkedIntelligenceBean> linkedintelligence;
    @Expose
    @SerializedName("linkedDocument")
    private List<LinkedDocumentBean> linkeddocument;
    @Expose
    @SerializedName("linkedCustody")
    private List<LinkedCustodyBean> linkedcustody;

    @Expose
    @SerializedName("linkedCourtWarrent")
    private List<LinkedCourtWarrentBean> linkedcourtwarrent;
    @Expose
    @SerializedName("linkedCommunication")
    private List<LinkedCommunicationBean> linkedcommunication;
    @Expose
    @SerializedName("linkedCategory")
    private List<LinkedCategoryBean> linkedcategory;
    @Expose
    @SerializedName("linkedCases")
    private List<LinkedCasesBean> linkedcases;
    @Expose
    @SerializedName("linkedBriefing")
    private List<LinkedBriefingBean> linkedbriefing;
    @Expose
    @SerializedName("linkedBailRefusal")
    private List<LinkedBailRefusalBean> linkedbailrefusal;
    @Expose
    @SerializedName("linkedBail")
    private List<LinkedBailBean> linkedbail;
    @Expose
    @SerializedName("linkedArrest")
    private List<LinkedArrestBean> linkedarrest;
    @Expose
    @SerializedName("VehicleCount")
    private String vehiclecount;
    @Expose
    @SerializedName("SearchCount")
    private String searchcount;
    @Expose
    @SerializedName("SamplesTakenCount")
    private String samplestakencount;
    @Expose
    @SerializedName("RiskAssessmentCount")
    private String riskassessmentcount;
    @Expose
    @SerializedName("PropertyItemsCount")
    private String propertyitemscount;
    @Expose
    @SerializedName("PerssonSerachCount")
    private String perssonserachcount;
    @Expose
    @SerializedName("Person")
    private PersonModel person;
    @Expose
    @SerializedName("vehicle")
    private VehicleModel vehicle;
    @Expose
    @SerializedName("address")
    private AddressModel address;
    @Expose
    @SerializedName("investigation")
    private InvestigationModels investigation;
    @Expose
    @SerializedName("caseDetails")
    private CasedetailModels casedetails;
    @Expose
    @SerializedName("communication")
    private CommunicationModel communication;


    @Expose
    @SerializedName("courtWarrant")
    private CourtWarrantModel courtwarrant;

    @Expose
    @SerializedName("OrganisationCount")
    private String organisationcount;
    @Expose
    @SerializedName("OperationCount")
    private String operationcount;
    @Expose
    @SerializedName("OffenceCount")
    private String offencecount;
    @Expose
    @SerializedName("ObjectIterationCount")
    private String objectiterationcount;
    @Expose
    @SerializedName("LocationCount")
    private String locationcount;
    @Expose
    @SerializedName("LinkedPersonCount")
    private String linkedpersoncount;
    @Expose
    @SerializedName("InvestigationCount")
    private String investigationcount;
    @Expose
    @SerializedName("IntelligenceCount")
    private String intelligencecount;
    @Expose
    @SerializedName("DocumentCount")
    private String documentcount;
    @Expose
    @SerializedName("CustodyCount")
    private String custodycount;
    @Expose
    @SerializedName("CourtWarrentCount")
    private String courtwarrentcount;
    @Expose
    @SerializedName("CommunicationCount")
    private String communicationcount;
    @Expose
    @SerializedName("CategoryCount")
    private String categorycount;
    @Expose
    @SerializedName("CaseCount")
    private String casecount;
    @Expose
    @SerializedName("BriefingCount")
    private String briefingcount;
    @Expose
    @SerializedName("BailRefusalCount")
    private String bailrefusalcount;
    @Expose
    @SerializedName("BailCount")
    private String bailcount;
    @Expose
    @SerializedName("ArrestCount")
    private String arrestcount;

    public List<LinkedVehicleBean> getLinkedvehicle() {
        return linkedvehicle;
    }

    public void setLinkedvehicle(List<LinkedVehicleBean> linkedvehicle) {
        this.linkedvehicle = linkedvehicle;
    }

    public List<LinkedSearchesBean> getLinkedsearches() {
        return linkedsearches;
    }

    public void setLinkedsearches(List<LinkedSearchesBean> linkedsearches) {
        this.linkedsearches = linkedsearches;
    }

    public List<LinkedSampleBean> getLinkedsample() {
        return linkedsample;
    }

    public void setLinkedsample(List<LinkedSampleBean> linkedsample) {
        this.linkedsample = linkedsample;
    }

    public List<LinkedRiskAssessmentBean> getLinkedriskassessment() {
        return linkedriskassessment;
    }

    public void setLinkedriskassessment(List<LinkedRiskAssessmentBean> linkedriskassessment) {
        this.linkedriskassessment = linkedriskassessment;
    }

    public List<LinkedPropertyItemsBean> getLinkedpropertyitems() {
        return linkedpropertyitems;
    }

    public void setLinkedpropertyitems(List<LinkedPropertyItemsBean> linkedpropertyitems) {
        this.linkedpropertyitems = linkedpropertyitems;
    }

    public List<LinkedPersonSearchedBean> getLinkedpersonsearched() {
        return linkedpersonsearched;
    }

    public void setLinkedpersonsearched(List<LinkedPersonSearchedBean> linkedpersonsearched) {
        this.linkedpersonsearched = linkedpersonsearched;
    }

    public List<LinkedPersonBean> getLinkedperson() {
        return linkedperson;
    }

    public void setLinkedperson(List<LinkedPersonBean> linkedperson) {
        this.linkedperson = linkedperson;
    }

    public List<LinkedOrganisationBean> getLinkedorganisation() {
        return linkedorganisation;
    }

    public void setLinkedorganisation(List<LinkedOrganisationBean> linkedorganisation) {
        this.linkedorganisation = linkedorganisation;
    }

    public List<String> getLinkedoperation() {
        return linkedoperation;
    }

    public void setLinkedoperation(List<String> linkedoperation) {
        this.linkedoperation = linkedoperation;
    }

    public List<LinkedOffenceBean> getLinkedoffence() {
        return linkedoffence;
    }

    public void setLinkedoffence(List<LinkedOffenceBean> linkedoffence) {
        this.linkedoffence = linkedoffence;
    }

    public List<LinkedObjectIterationBean> getLinkedobjectiteration() {
        return linkedobjectiteration;
    }

    public void setLinkedobjectiteration(List<LinkedObjectIterationBean> linkedobjectiteration) {
        this.linkedobjectiteration = linkedobjectiteration;
    }

    public List<LinkedLocationBean> getLinkedlocation() {
        return linkedlocation;
    }

    public void setLinkedlocation(List<LinkedLocationBean> linkedlocation) {
        this.linkedlocation = linkedlocation;
    }

    public List<LinkedInvestigationBean> getLinkedinvestigation() {
        return linkedinvestigation;
    }

    public void setLinkedinvestigation(List<LinkedInvestigationBean> linkedinvestigation) {
        this.linkedinvestigation = linkedinvestigation;
    }

    public List<LinkedIntelligenceBean> getLinkedintelligence() {
        return linkedintelligence;
    }

    public void setLinkedintelligence(List<LinkedIntelligenceBean> linkedintelligence) {
        this.linkedintelligence = linkedintelligence;
    }

    public List<LinkedDocumentBean> getLinkeddocument() {
        return linkeddocument;
    }

    public void setLinkeddocument(List<LinkedDocumentBean> linkeddocument) {
        this.linkeddocument = linkeddocument;
    }

    public List<LinkedCustodyBean> getLinkedcustody() {
        return linkedcustody;
    }

    public void setLinkedcustody(List<LinkedCustodyBean> linkedcustody) {
        this.linkedcustody = linkedcustody;
    }

    public List<LinkedCourtWarrentBean> getLinkedcourtwarrent() {
        return linkedcourtwarrent;
    }

    public void setLinkedcourtwarrent(List<LinkedCourtWarrentBean> linkedcourtwarrent) {
        this.linkedcourtwarrent = linkedcourtwarrent;
    }


    public List<LinkedCommunicationBean> getLinkedcommunication() {
        return linkedcommunication;
    }

    public void setLinkedcommunication(List<LinkedCommunicationBean> linkedcommunication) {
        this.linkedcommunication = linkedcommunication;
    }

    public List<LinkedCategoryBean> getLinkedcategory() {
        return linkedcategory;
    }

    public void setLinkedcategory(List<LinkedCategoryBean> linkedcategory) {
        this.linkedcategory = linkedcategory;
    }

    public List<LinkedCasesBean> getLinkedcases() {
        return linkedcases;
    }

    public void setLinkedcases(List<LinkedCasesBean> linkedcases) {
        this.linkedcases = linkedcases;
    }

    public List<LinkedBriefingBean> getLinkedbriefing() {
        return linkedbriefing;
    }

    public void setLinkedbriefing(List<LinkedBriefingBean> linkedbriefing) {
        this.linkedbriefing = linkedbriefing;
    }

    public List<LinkedBailRefusalBean> getLinkedbailrefusal() {
        return linkedbailrefusal;
    }

    public void setLinkedbailrefusal(List<LinkedBailRefusalBean> linkedbailrefusal) {
        this.linkedbailrefusal = linkedbailrefusal;
    }

    public List<LinkedBailBean> getLinkedbail() {
        return linkedbail;
    }

    public void setLinkedbail(List<LinkedBailBean> linkedbail) {
        this.linkedbail = linkedbail;
    }

    public List<LinkedArrestBean> getLinkedarrest() {
        return linkedarrest;
    }

    public void setLinkedarrest(List<LinkedArrestBean> linkedarrest) {
        this.linkedarrest = linkedarrest;
    }

    public String getVehiclecount() {
        return vehiclecount;
    }

    public void setVehiclecount(String vehiclecount) {
        this.vehiclecount = vehiclecount;
    }

    public String getSearchcount() {
        return searchcount;
    }

    public void setSearchcount(String searchcount) {
        this.searchcount = searchcount;
    }

    public String getSamplestakencount() {
        return samplestakencount;
    }

    public void setSamplestakencount(String samplestakencount) {
        this.samplestakencount = samplestakencount;
    }

    public String getRiskassessmentcount() {
        return riskassessmentcount;
    }

    public void setRiskassessmentcount(String riskassessmentcount) {
        this.riskassessmentcount = riskassessmentcount;
    }

    public String getPropertyitemscount() {
        return propertyitemscount;
    }

    public void setPropertyitemscount(String propertyitemscount) {
        this.propertyitemscount = propertyitemscount;
    }

    public String getPerssonserachcount() {
        return perssonserachcount;
    }

    public void setPerssonserachcount(String perssonserachcount) {
        this.perssonserachcount = perssonserachcount;
    }

    public PersonModel getPerson() {
        return person;
    }

    public void setPerson(PersonModel person) {
        this.person = person;
    }

    public VehicleModel getVehicle() {
        return vehicle;
    }

    public void setVehicle(VehicleModel vehicle) {
        this.vehicle = vehicle;
    }

    public AddressModel getAddress() {
        return address;
    }

    public void setAddress(AddressModel address) {
        this.address = address;
    }

    public InvestigationModels getInvestigation() {
        return investigation;
    }

    public void setInvestigation(InvestigationModels investigation) {
        this.investigation = investigation;
    }

    public CasedetailModels getCasedetails() {
        return casedetails;
    }

    public void setCasedetails(CasedetailModels casedetails) {
        this.casedetails = casedetails;
    }

    public CommunicationModel getCommunication() {
        return communication;
    }

    public void setCommunication(CommunicationModel communication) {
        this.communication = communication;
    }
    public CourtWarrantModel getCourtwarrant() {
        return courtwarrant;
    }

    public void setCourtwarrant(CourtWarrantModel courtwarrant) {
        this.courtwarrant = courtwarrant;
    }


    public String getOrganisationcount() {
        return organisationcount;
    }

    public void setOrganisationcount(String organisationcount) {
        this.organisationcount = organisationcount;
    }

    public String getOperationcount() {
        return operationcount;
    }

    public void setOperationcount(String operationcount) {
        this.operationcount = operationcount;
    }

    public String getOffencecount() {
        return offencecount;
    }

    public void setOffencecount(String offencecount) {
        this.offencecount = offencecount;
    }

    public String getObjectiterationcount() {
        return objectiterationcount;
    }

    public void setObjectiterationcount(String objectiterationcount) {
        this.objectiterationcount = objectiterationcount;
    }

    public String getLocationcount() {
        return locationcount;
    }

    public void setLocationcount(String locationcount) {
        this.locationcount = locationcount;
    }

    public String getLinkedpersoncount() {
        return linkedpersoncount;
    }

    public void setLinkedpersoncount(String linkedpersoncount) {
        this.linkedpersoncount = linkedpersoncount;
    }

    public String getInvestigationcount() {
        return investigationcount;
    }

    public void setInvestigationcount(String investigationcount) {
        this.investigationcount = investigationcount;
    }

    public String getIntelligencecount() {
        return intelligencecount;
    }

    public void setIntelligencecount(String intelligencecount) {
        this.intelligencecount = intelligencecount;
    }

    public String getDocumentcount() {
        return documentcount;
    }

    public void setDocumentcount(String documentcount) {
        this.documentcount = documentcount;
    }

    public String getCustodycount() {
        return custodycount;
    }

    public void setCustodycount(String custodycount) {
        this.custodycount = custodycount;
    }

    public String getCourtwarrentcount() {
        return courtwarrentcount;
    }

    public void setCourtwarrentcount(String courtwarrentcount) {
        this.courtwarrentcount = courtwarrentcount;
    }

    public String getCommunicationcount() {
        return communicationcount;
    }

    public void setCommunicationcount(String communicationcount) {
        this.communicationcount = communicationcount;
    }

    public String getCategorycount() {
        return categorycount;
    }

    public void setCategorycount(String categorycount) {
        this.categorycount = categorycount;
    }

    public String getCasecount() {
        return casecount;
    }

    public void setCasecount(String casecount) {
        this.casecount = casecount;
    }

    public String getBriefingcount() {
        return briefingcount;
    }

    public void setBriefingcount(String briefingcount) {
        this.briefingcount = briefingcount;
    }

    public String getBailrefusalcount() {
        return bailrefusalcount;
    }

    public void setBailrefusalcount(String bailrefusalcount) {
        this.bailrefusalcount = bailrefusalcount;
    }

    public String getBailcount() {
        return bailcount;
    }

    public void setBailcount(String bailcount) {
        this.bailcount = bailcount;
    }

    public String getArrestcount() {
        return arrestcount;
    }

    public void setArrestcount(String arrestcount) {
        this.arrestcount = arrestcount;
    }
}
