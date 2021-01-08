package hcl.policing.digitalpolicingplatform.models.tasking;

import java.io.Serializable;

public class TaskCompleteRequestDTO implements Serializable {
    private String TaskID;
    private String CreatedBy;
    private String TaskTypeID;
    private String TaskTypeName;
    private String TaskDescription;
    private String IntelligenceInformation;
    private String Aim;
    private String MethodUtilised;
    private String OfficerSafety;
    private String PostCode;
    private String Town;
    private String Street;
    private String HouseNo;
    private String County;
    private String Latitude;
    private String Longitude;
    private String CreatedDate;
    private String ExpireDate;
    private String Recurring;
    private String TaskStatus;
    private String Priority;
    private String IsActive;
    private String PageNumber;
    private String StartDate;
    private String EndDate;
    private String ManageTask;
    private String TaskImagesXML;
    private String RelevantDate;
    private String TimeLeftExpire;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
    }

    public String getCreatedBy() {
        return CreatedBy;
    }

    public void setCreatedBy(String createdBy) {
        CreatedBy = createdBy;
    }

    public String getTaskTypeID() {
        return TaskTypeID;
    }

    public void setTaskTypeID(String taskTypeID) {
        TaskTypeID = taskTypeID;
    }

    public String getTaskTypeName() {
        return TaskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        TaskTypeName = taskTypeName;
    }

    public String getTaskDescription() {
        return TaskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        TaskDescription = taskDescription;
    }

    public String getIntelligenceInformation() {
        return IntelligenceInformation;
    }

    public void setIntelligenceInformation(String intelligenceInformation) {
        IntelligenceInformation = intelligenceInformation;
    }

    public String getAim() {
        return Aim;
    }

    public void setAim(String aim) {
        Aim = aim;
    }

    public String getMethodUtilised() {
        return MethodUtilised;
    }

    public void setMethodUtilised(String methodUtilised) {
        MethodUtilised = methodUtilised;
    }

    public String getOfficerSafety() {
        return OfficerSafety;
    }

    public void setOfficerSafety(String officerSafety) {
        OfficerSafety = officerSafety;
    }

    public String getPostCode() {
        return PostCode;
    }

    public void setPostCode(String postCode) {
        PostCode = postCode;
    }

    public String getTown() {
        return Town;
    }

    public void setTown(String town) {
        Town = town;
    }

    public String getStreet() {
        return Street;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public String getHouseNo() {
        return HouseNo;
    }

    public void setHouseNo(String houseNo) {
        HouseNo = houseNo;
    }

    public String getCounty() {
        return County;
    }

    public void setCounty(String county) {
        County = county;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getCreatedDate() {
        return CreatedDate;
    }

    public void setCreatedDate(String createdDate) {
        CreatedDate = createdDate;
    }

    public String getExpireDate() {
        return ExpireDate;
    }

    public void setExpireDate(String expireDate) {
        ExpireDate = expireDate;
    }

    public String getRecurring() {
        return Recurring;
    }

    public void setRecurring(String recurring) {
        Recurring = recurring;
    }

    public String getTaskStatus() {
        return TaskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        TaskStatus = taskStatus;
    }

    public String getPriority() {
        return Priority;
    }

    public void setPriority(String priority) {
        Priority = priority;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }

    public String getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(String pageNumber) {
        PageNumber = pageNumber;
    }

    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }

    public String getEndDate() {
        return EndDate;
    }

    public void setEndDate(String endDate) {
        EndDate = endDate;
    }

    public String getManageTask() {
        return ManageTask;
    }

    public void setManageTask(String manageTask) {
        ManageTask = manageTask;
    }

    public String getTaskImagesXML() {
        return TaskImagesXML;
    }

    public void setTaskImagesXML(String taskImagesXML) {
        TaskImagesXML = taskImagesXML;
    }

    public String getRelevantDate() {
        return RelevantDate;
    }

    public void setRelevantDate(String relevantDate) {
        RelevantDate = relevantDate;
    }

    public String getTimeLeftExpire() {
        return TimeLeftExpire;
    }

    public void setTimeLeftExpire(String timeLeftExpire) {
        TimeLeftExpire = timeLeftExpire;
    }
}
