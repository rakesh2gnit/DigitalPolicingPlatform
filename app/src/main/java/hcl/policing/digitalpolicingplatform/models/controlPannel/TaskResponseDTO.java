package hcl.policing.digitalpolicingplatform.models.controlPannel;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class TaskResponseDTO implements Serializable {

    @SerializedName("TaskID")
    @Expose
    private Integer taskID;
    @SerializedName("CreatedBy")
    @Expose
    private Object createdBy;
    @SerializedName("TaskTypeID")
    @Expose
    private Integer taskTypeID;
    @SerializedName("TaskTypeName")
    @Expose
    private String taskTypeName;
    @SerializedName("TaskDescription")
    @Expose
    private String taskDescription;
    @SerializedName("IntelligenceInformation")
    @Expose
    private Object intelligenceInformation;
    @SerializedName("Aim")
    @Expose
    private Object aim;
    @SerializedName("MethodUtilised")
    @Expose
    private Object methodUtilised;
    @SerializedName("OfficerSafety")
    @Expose
    private Object officerSafety;
    @SerializedName("PostCode")
    @Expose
    private Object postCode;
    @SerializedName("Town")
    @Expose
    private Object town;
    @SerializedName("Street")
    @Expose
    private Object street;
    @SerializedName("HouseNo")
    @Expose
    private Object houseNo;
    @SerializedName("County")
    @Expose
    private Object county;
    @SerializedName("Latitude")
    @Expose
    private Object latitude;
    @SerializedName("Longitude")
    @Expose
    private Object longitude;
    @SerializedName("CreatedDate")
    @Expose
    private Object createdDate;
    @SerializedName("Recurring")
    @Expose
    private Boolean recurring;
    @SerializedName("TaskStatus")
    @Expose
    private String taskStatus;
    @SerializedName("Priority")
    @Expose
    private String priority;
    @SerializedName("IsActive")
    @Expose
    private Boolean isActive;
    @SerializedName("PageNumber")
    @Expose
    private Integer pageNumber;
    @SerializedName("StartDate")
    @Expose
    private Object startDate;
    @SerializedName("EndDate")
    @Expose
    private Object endDate;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("ManageTask")
    @Expose
    private Object manageTask;
    @SerializedName("RelevantDate")
    @Expose
    private Object relevantDate;
    @SerializedName("RelevantTime")
    @Expose
    private Object relevantTime;
    @SerializedName("TaskImagesXML")
    @Expose
    private Object taskImagesXML;
    @SerializedName("ExpireDate")
    @Expose
    private Object expireDate;
    @SerializedName("TimeLeftExpire")
    @Expose
    private String timeLeftExpire;

    public Integer getTaskID() {
        return taskID;
    }

    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
    }

    public Object getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Object createdBy) {
        this.createdBy = createdBy;
    }

    public Integer getTaskTypeID() {
        return taskTypeID;
    }

    public void setTaskTypeID(Integer taskTypeID) {
        this.taskTypeID = taskTypeID;
    }

    public String getTaskTypeName() {
        return taskTypeName;
    }

    public void setTaskTypeName(String taskTypeName) {
        this.taskTypeName = taskTypeName;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public Object getIntelligenceInformation() {
        return intelligenceInformation;
    }

    public void setIntelligenceInformation(Object intelligenceInformation) {
        this.intelligenceInformation = intelligenceInformation;
    }

    public Object getAim() {
        return aim;
    }

    public void setAim(Object aim) {
        this.aim = aim;
    }

    public Object getMethodUtilised() {
        return methodUtilised;
    }

    public void setMethodUtilised(Object methodUtilised) {
        this.methodUtilised = methodUtilised;
    }

    public Object getOfficerSafety() {
        return officerSafety;
    }

    public void setOfficerSafety(Object officerSafety) {
        this.officerSafety = officerSafety;
    }

    public Object getPostCode() {
        return postCode;
    }

    public void setPostCode(Object postCode) {
        this.postCode = postCode;
    }

    public Object getTown() {
        return town;
    }

    public void setTown(Object town) {
        this.town = town;
    }

    public Object getStreet() {
        return street;
    }

    public void setStreet(Object street) {
        this.street = street;
    }

    public Object getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(Object houseNo) {
        this.houseNo = houseNo;
    }

    public Object getCounty() {
        return county;
    }

    public void setCounty(Object county) {
        this.county = county;
    }

    public Object getLatitude() {
        return latitude;
    }

    public void setLatitude(Object latitude) {
        this.latitude = latitude;
    }

    public Object getLongitude() {
        return longitude;
    }

    public void setLongitude(Object longitude) {
        this.longitude = longitude;
    }

    public Object getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Object createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getRecurring() {
        return recurring;
    }

    public void setRecurring(Boolean recurring) {
        this.recurring = recurring;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public Object getStartDate() {
        return startDate;
    }

    public void setStartDate(Object startDate) {
        this.startDate = startDate;
    }

    public Object getEndDate() {
        return endDate;
    }

    public void setEndDate(Object endDate) {
        this.endDate = endDate;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Object getManageTask() {
        return manageTask;
    }

    public void setManageTask(Object manageTask) {
        this.manageTask = manageTask;
    }

    public Object getRelevantDate() {
        return relevantDate;
    }

    public void setRelevantDate(Object relevantDate) {
        this.relevantDate = relevantDate;
    }

    public Object getRelevantTime() {
        return relevantTime;
    }

    public void setRelevantTime(Object relevantTime) {
        this.relevantTime = relevantTime;
    }

    public Object getTaskImagesXML() {
        return taskImagesXML;
    }

    public void setTaskImagesXML(Object taskImagesXML) {
        this.taskImagesXML = taskImagesXML;
    }

    public Object getExpireDate() {
        return expireDate;
    }

    public void setExpireDate(Object expireDate) {
        this.expireDate = expireDate;
    }

    public String getTimeLeftExpire() {
        return timeLeftExpire;
    }

    public void setTimeLeftExpire(String timeLeftExpire) {
        this.timeLeftExpire = timeLeftExpire;
    }

}