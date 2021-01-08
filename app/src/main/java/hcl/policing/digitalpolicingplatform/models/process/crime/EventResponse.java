package hcl.policing.digitalpolicingplatform.models.process.crime;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class EventResponse implements Serializable {
    @SerializedName("ADGUID")
    @Expose
    private Object aDGUID;
    @SerializedName("CollarId")
    @Expose
    private Object collarId;
    @SerializedName("ComponentName")
    @Expose
    private Object componentName;
    @SerializedName("ComponentVersionNo")
    @Expose
    private Object componentVersionNo;
    @SerializedName("ConnectionString")
    @Expose
    private Object connectionString;
    @SerializedName("CurrentPage")
    @Expose
    private Integer currentPage;
    @SerializedName("DevicePhoneNo")
    @Expose
    private Object devicePhoneNo;
    @SerializedName("DistrictCode")
    @Expose
    private Object districtCode;
    @SerializedName("EndPoint")
    @Expose
    private Object endPoint;
    @SerializedName("ExternalURL")
    @Expose
    private Object externalURL;
    @SerializedName("FDSRequestString")
    @Expose
    private Object fDSRequestString;
    @SerializedName("ForceCode")
    @Expose
    private Object forceCode;
    @SerializedName("Function")
    @Expose
    private Object function;
    @SerializedName("GUID")
    @Expose
    private Object gUID;
    @SerializedName("GeoCoordinates")
    @Expose
    private Object geoCoordinates;
    @SerializedName("IMEINo")
    @Expose
    private Object iMEINo;
    @SerializedName("LayerName")
    @Expose
    private Object layerName;
    @SerializedName("Location")
    @Expose
    private Object location;
    @SerializedName("PNCDATA")
    @Expose
    private Object pNCDATA;
    @SerializedName("Process")
    @Expose
    private Object process;
    @SerializedName("ProcessEndTime")
    @Expose
    private Object processEndTime;
    @SerializedName("ProcessStartTime")
    @Expose
    private Object processStartTime;
    @SerializedName("PropertyExt1")
    @Expose
    private Object propertyExt1;
    @SerializedName("PropertyExt2")
    @Expose
    private Object propertyExt2;
    @SerializedName("PropertyExt3")
    @Expose
    private Object propertyExt3;
    @SerializedName("PropertyExt4")
    @Expose
    private Object propertyExt4;
    @SerializedName("PropertyExt5")
    @Expose
    private Object propertyExt5;
    @SerializedName("ReasonCode")
    @Expose
    private Object reasonCode;
    @SerializedName("Screen")
    @Expose
    private Object screen;
    @SerializedName("ScreenName")
    @Expose
    private Object screenName;
    @SerializedName("SecurityContext")
    @Expose
    private Object securityContext;
    @SerializedName("Session")
    @Expose
    private Object session;
    @SerializedName("SessionID")
    @Expose
    private Object sessionID;
    @SerializedName("StationCode")
    @Expose
    private Object stationCode;
    @SerializedName("SubProcess")
    @Expose
    private Object subProcess;
    @SerializedName("SubProcessName")
    @Expose
    private Object subProcessName;
    @SerializedName("TotalPageCount")
    @Expose
    private Integer totalPageCount;
    @SerializedName("TotalRecordCount")
    @Expose
    private Integer totalRecordCount;
    @SerializedName("TransactionId")
    @Expose
    private Object transactionId;
    @SerializedName("UserName")
    @Expose
    private Object userName;
    @SerializedName("AssessmentList")
    @Expose
    private Object assessmentList;
    @SerializedName("CompletedSearchesList")
    @Expose
    private Object completedSearchesList;
    @SerializedName("CrimeGroupList")
    @Expose
    private List<CrimeGroupList> crimeGroupList = null;
    @SerializedName("CrimeObjectList")
    @Expose
    private Object crimeObjectList;
    @SerializedName("DescriptionHistory")
    @Expose
    private Object descriptionHistory;
    @SerializedName("DocumentDetails")
    @Expose
    private Object documentDetails;
    @SerializedName("EventDetailsList")
    @Expose
    private Object eventDetailsList;
    @SerializedName("EventLocation")
    @Expose
    private Object eventLocation;
    @SerializedName("EventSearchList")
    @Expose
    private List<EventSearchList> eventSearchList = null;
    @SerializedName("ExceptionsOccurred")
    @Expose
    private Object exceptionsOccurred;
    @SerializedName("Flag")
    @Expose
    private Object flag;
    @SerializedName("HOOffenceList")
    @Expose
    private List<HOOffenceList> hOOffenceList = null;
    @SerializedName("ID")
    @Expose
    private Object iD;
    @SerializedName("InitialAssessment")
    @Expose
    private Object initialAssessment;
    @SerializedName("InjuryDetailsList")
    @Expose
    private Object injuryDetailsList;
    @SerializedName("IntelligenceSearchResponse")
    @Expose
    private Object intelligenceSearchResponse;
    @SerializedName("InvestigationPlanDetails")
    @Expose
    private Object investigationPlanDetails;
    @SerializedName("Issues")
    @Expose
    private Object issues;
    @SerializedName("LinkedEvents")
    @Expose
    private Object linkedEvents;
    @SerializedName("LogEntryList")
    @Expose
    private Object logEntryList;
    @SerializedName("MODetailsList")
    @Expose
    private Object mODetailsList;
    @SerializedName("MarkersDetailsList")
    @Expose
    private Object markersDetailsList;
    @SerializedName("Msg")
    @Expose
    private Object msg;
    @SerializedName("NonAvailabilityList")
    @Expose
    private Object nonAvailabilityList;
    @SerializedName("OffenceDefinitionList")
    @Expose
    private List<OffenceDefinitionList> offenceDefinitionList = null;
    @SerializedName("OfficerDetailsList")
    @Expose
    private Object officerDetailsList;
    @SerializedName("OrganisationDetailsList")
    @Expose
    private List<OrganisationDetailsList> organisationDetailsList = null;
    @SerializedName("OutcomeList")
    @Expose
    private Object outcomeList;
    @SerializedName("PoliceEmployeeList")
    @Expose
    private Object policeEmployeeList;
    @SerializedName("PropertyObject")
    @Expose
    private Object propertyObject;
    @SerializedName("Reasons")
    @Expose
    private Object reasons;
    @SerializedName("RecordOfContactList")
    @Expose
    private Object recordOfContactList;
    @SerializedName("SupervisionDetailsList")
    @Expose
    private Object supervisionDetailsList;
    @SerializedName("URN")
    @Expose
    private Object uRN;

    public Object getADGUID() {
        return aDGUID;
    }

    public void setADGUID(Object aDGUID) {
        this.aDGUID = aDGUID;
    }

    public Object getCollarId() {
        return collarId;
    }

    public void setCollarId(Object collarId) {
        this.collarId = collarId;
    }

    public Object getComponentName() {
        return componentName;
    }

    public void setComponentName(Object componentName) {
        this.componentName = componentName;
    }

    public Object getComponentVersionNo() {
        return componentVersionNo;
    }

    public void setComponentVersionNo(Object componentVersionNo) {
        this.componentVersionNo = componentVersionNo;
    }

    public Object getConnectionString() {
        return connectionString;
    }

    public void setConnectionString(Object connectionString) {
        this.connectionString = connectionString;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Object getDevicePhoneNo() {
        return devicePhoneNo;
    }

    public void setDevicePhoneNo(Object devicePhoneNo) {
        this.devicePhoneNo = devicePhoneNo;
    }

    public Object getDistrictCode() {
        return districtCode;
    }

    public void setDistrictCode(Object districtCode) {
        this.districtCode = districtCode;
    }

    public Object getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(Object endPoint) {
        this.endPoint = endPoint;
    }

    public Object getExternalURL() {
        return externalURL;
    }

    public void setExternalURL(Object externalURL) {
        this.externalURL = externalURL;
    }

    public Object getFDSRequestString() {
        return fDSRequestString;
    }

    public void setFDSRequestString(Object fDSRequestString) {
        this.fDSRequestString = fDSRequestString;
    }

    public Object getForceCode() {
        return forceCode;
    }

    public void setForceCode(Object forceCode) {
        this.forceCode = forceCode;
    }

    public Object getFunction() {
        return function;
    }

    public void setFunction(Object function) {
        this.function = function;
    }

    public Object getGUID() {
        return gUID;
    }

    public void setGUID(Object gUID) {
        this.gUID = gUID;
    }

    public Object getGeoCoordinates() {
        return geoCoordinates;
    }

    public void setGeoCoordinates(Object geoCoordinates) {
        this.geoCoordinates = geoCoordinates;
    }

    public Object getIMEINo() {
        return iMEINo;
    }

    public void setIMEINo(Object iMEINo) {
        this.iMEINo = iMEINo;
    }

    public Object getLayerName() {
        return layerName;
    }

    public void setLayerName(Object layerName) {
        this.layerName = layerName;
    }

    public Object getLocation() {
        return location;
    }

    public void setLocation(Object location) {
        this.location = location;
    }

    public Object getPNCDATA() {
        return pNCDATA;
    }

    public void setPNCDATA(Object pNCDATA) {
        this.pNCDATA = pNCDATA;
    }

    public Object getProcess() {
        return process;
    }

    public void setProcess(Object process) {
        this.process = process;
    }

    public Object getProcessEndTime() {
        return processEndTime;
    }

    public void setProcessEndTime(Object processEndTime) {
        this.processEndTime = processEndTime;
    }

    public Object getProcessStartTime() {
        return processStartTime;
    }

    public void setProcessStartTime(Object processStartTime) {
        this.processStartTime = processStartTime;
    }

    public Object getPropertyExt1() {
        return propertyExt1;
    }

    public void setPropertyExt1(Object propertyExt1) {
        this.propertyExt1 = propertyExt1;
    }

    public Object getPropertyExt2() {
        return propertyExt2;
    }

    public void setPropertyExt2(Object propertyExt2) {
        this.propertyExt2 = propertyExt2;
    }

    public Object getPropertyExt3() {
        return propertyExt3;
    }

    public void setPropertyExt3(Object propertyExt3) {
        this.propertyExt3 = propertyExt3;
    }

    public Object getPropertyExt4() {
        return propertyExt4;
    }

    public void setPropertyExt4(Object propertyExt4) {
        this.propertyExt4 = propertyExt4;
    }

    public Object getPropertyExt5() {
        return propertyExt5;
    }

    public void setPropertyExt5(Object propertyExt5) {
        this.propertyExt5 = propertyExt5;
    }

    public Object getReasonCode() {
        return reasonCode;
    }

    public void setReasonCode(Object reasonCode) {
        this.reasonCode = reasonCode;
    }

    public Object getScreen() {
        return screen;
    }

    public void setScreen(Object screen) {
        this.screen = screen;
    }

    public Object getScreenName() {
        return screenName;
    }

    public void setScreenName(Object screenName) {
        this.screenName = screenName;
    }

    public Object getSecurityContext() {
        return securityContext;
    }

    public void setSecurityContext(Object securityContext) {
        this.securityContext = securityContext;
    }

    public Object getSession() {
        return session;
    }

    public void setSession(Object session) {
        this.session = session;
    }

    public Object getSessionID() {
        return sessionID;
    }

    public void setSessionID(Object sessionID) {
        this.sessionID = sessionID;
    }

    public Object getStationCode() {
        return stationCode;
    }

    public void setStationCode(Object stationCode) {
        this.stationCode = stationCode;
    }

    public Object getSubProcess() {
        return subProcess;
    }

    public void setSubProcess(Object subProcess) {
        this.subProcess = subProcess;
    }

    public Object getSubProcessName() {
        return subProcessName;
    }

    public void setSubProcessName(Object subProcessName) {
        this.subProcessName = subProcessName;
    }

    public Integer getTotalPageCount() {
        return totalPageCount;
    }

    public void setTotalPageCount(Integer totalPageCount) {
        this.totalPageCount = totalPageCount;
    }

    public Integer getTotalRecordCount() {
        return totalRecordCount;
    }

    public void setTotalRecordCount(Integer totalRecordCount) {
        this.totalRecordCount = totalRecordCount;
    }

    public Object getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Object transactionId) {
        this.transactionId = transactionId;
    }

    public Object getUserName() {
        return userName;
    }

    public void setUserName(Object userName) {
        this.userName = userName;
    }

    public Object getAssessmentList() {
        return assessmentList;
    }

    public void setAssessmentList(Object assessmentList) {
        this.assessmentList = assessmentList;
    }

    public Object getCompletedSearchesList() {
        return completedSearchesList;
    }

    public void setCompletedSearchesList(Object completedSearchesList) {
        this.completedSearchesList = completedSearchesList;
    }

    public List<CrimeGroupList> getCrimeGroupList() {
        return crimeGroupList;
    }

    public void setCrimeGroupList(List<CrimeGroupList> crimeGroupList) {
        this.crimeGroupList = crimeGroupList;
    }

    public Object getCrimeObjectList() {
        return crimeObjectList;
    }

    public void setCrimeObjectList(Object crimeObjectList) {
        this.crimeObjectList = crimeObjectList;
    }

    public Object getDescriptionHistory() {
        return descriptionHistory;
    }

    public void setDescriptionHistory(Object descriptionHistory) {
        this.descriptionHistory = descriptionHistory;
    }

    public Object getDocumentDetails() {
        return documentDetails;
    }

    public void setDocumentDetails(Object documentDetails) {
        this.documentDetails = documentDetails;
    }

    public Object getEventDetailsList() {
        return eventDetailsList;
    }

    public void setEventDetailsList(Object eventDetailsList) {
        this.eventDetailsList = eventDetailsList;
    }

    public Object getEventLocation() {
        return eventLocation;
    }

    public void setEventLocation(Object eventLocation) {
        this.eventLocation = eventLocation;
    }

    public List<EventSearchList> getEventSearchList() {
        return eventSearchList;
    }

    public void setEventSearchList(List<EventSearchList> eventSearchList) {
        this.eventSearchList = eventSearchList;
    }

    public Object getExceptionsOccurred() {
        return exceptionsOccurred;
    }

    public void setExceptionsOccurred(Object exceptionsOccurred) {
        this.exceptionsOccurred = exceptionsOccurred;
    }

    public Object getFlag() {
        return flag;
    }

    public void setFlag(Object flag) {
        this.flag = flag;
    }

    public List<HOOffenceList> gethOOffenceList() {
        return hOOffenceList;
    }

    public void sethOOffenceList(List<HOOffenceList> hOOffenceList) {
        this.hOOffenceList = hOOffenceList;
    }

    public Object getID() {
        return iD;
    }

    public void setID(Object iD) {
        this.iD = iD;
    }

    public Object getInitialAssessment() {
        return initialAssessment;
    }

    public void setInitialAssessment(Object initialAssessment) {
        this.initialAssessment = initialAssessment;
    }

    public Object getInjuryDetailsList() {
        return injuryDetailsList;
    }

    public void setInjuryDetailsList(Object injuryDetailsList) {
        this.injuryDetailsList = injuryDetailsList;
    }

    public Object getIntelligenceSearchResponse() {
        return intelligenceSearchResponse;
    }

    public void setIntelligenceSearchResponse(Object intelligenceSearchResponse) {
        this.intelligenceSearchResponse = intelligenceSearchResponse;
    }

    public Object getInvestigationPlanDetails() {
        return investigationPlanDetails;
    }

    public void setInvestigationPlanDetails(Object investigationPlanDetails) {
        this.investigationPlanDetails = investigationPlanDetails;
    }

    public Object getIssues() {
        return issues;
    }

    public void setIssues(Object issues) {
        this.issues = issues;
    }

    public Object getLinkedEvents() {
        return linkedEvents;
    }

    public void setLinkedEvents(Object linkedEvents) {
        this.linkedEvents = linkedEvents;
    }

    public Object getLogEntryList() {
        return logEntryList;
    }

    public void setLogEntryList(Object logEntryList) {
        this.logEntryList = logEntryList;
    }

    public Object getMODetailsList() {
        return mODetailsList;
    }

    public void setMODetailsList(Object mODetailsList) {
        this.mODetailsList = mODetailsList;
    }

    public Object getMarkersDetailsList() {
        return markersDetailsList;
    }

    public void setMarkersDetailsList(Object markersDetailsList) {
        this.markersDetailsList = markersDetailsList;
    }

    public Object getMsg() {
        return msg;
    }

    public void setMsg(Object msg) {
        this.msg = msg;
    }

    public Object getNonAvailabilityList() {
        return nonAvailabilityList;
    }

    public void setNonAvailabilityList(Object nonAvailabilityList) {
        this.nonAvailabilityList = nonAvailabilityList;
    }

    public List<OffenceDefinitionList> getOffenceDefinitionList() {
        return offenceDefinitionList;
    }

    public void setOffenceDefinitionList(List<OffenceDefinitionList> offenceDefinitionList) {
        this.offenceDefinitionList = offenceDefinitionList;
    }

    public Object getOfficerDetailsList() {
        return officerDetailsList;
    }

    public void setOfficerDetailsList(Object officerDetailsList) {
        this.officerDetailsList = officerDetailsList;
    }

    public List<OrganisationDetailsList> getOrganisationDetailsList() {
        return organisationDetailsList;
    }

    public void setOrganisationDetailsList(List<OrganisationDetailsList> organisationDetailsList) {
        this.organisationDetailsList = organisationDetailsList;
    }

    public Object getOutcomeList() {
        return outcomeList;
    }

    public void setOutcomeList(Object outcomeList) {
        this.outcomeList = outcomeList;
    }

    public Object getPoliceEmployeeList() {
        return policeEmployeeList;
    }

    public void setPoliceEmployeeList(Object policeEmployeeList) {
        this.policeEmployeeList = policeEmployeeList;
    }

    public Object getPropertyObject() {
        return propertyObject;
    }

    public void setPropertyObject(Object propertyObject) {
        this.propertyObject = propertyObject;
    }

    public Object getReasons() {
        return reasons;
    }

    public void setReasons(Object reasons) {
        this.reasons = reasons;
    }

    public Object getRecordOfContactList() {
        return recordOfContactList;
    }

    public void setRecordOfContactList(Object recordOfContactList) {
        this.recordOfContactList = recordOfContactList;
    }

    public Object getSupervisionDetailsList() {
        return supervisionDetailsList;
    }

    public void setSupervisionDetailsList(Object supervisionDetailsList) {
        this.supervisionDetailsList = supervisionDetailsList;
    }

    public Object getURN() {
        return uRN;
    }

    public void setURN(Object uRN) {
        this.uRN = uRN;
    }

}