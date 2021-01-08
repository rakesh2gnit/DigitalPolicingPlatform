package hcl.policing.digitalpolicingplatform.models.tasking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class TaskDetailResponseDTO implements Serializable {

    @SerializedName("Task")
    @Expose
    private Task task;
    @SerializedName("RelevantNominalDetails")
    @Expose
    private List<RelevantNominalDetail> relevantNominalDetails = null;
    @SerializedName("RelevantVehicleDetails")
    @Expose
    private List<RelevantVehicleDetail> relevantVehicleDetails = null;
    @SerializedName("TaskAssignOfficerDetails")
    @Expose
    private List<Object> taskAssignOfficerDetails = null;
    @SerializedName("TaskImagesDetails")
    @Expose
    private List<TaskImagesDetail> taskImagesDetails = null;
    @SerializedName("TaskCommentsDetails")
    @Expose
    private List<Object> taskCommentsDetails = null;

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }

    public List<RelevantNominalDetail> getRelevantNominalDetails() {
        return relevantNominalDetails;
    }

    public void setRelevantNominalDetails(List<RelevantNominalDetail> relevantNominalDetails) {
        this.relevantNominalDetails = relevantNominalDetails;
    }

    public List<RelevantVehicleDetail> getRelevantVehicleDetails() {
        return relevantVehicleDetails;
    }

    public void setRelevantVehicleDetails(List<RelevantVehicleDetail> relevantVehicleDetails) {
        this.relevantVehicleDetails = relevantVehicleDetails;
    }

    public List<Object> getTaskAssignOfficerDetails() {
        return taskAssignOfficerDetails;
    }

    public void setTaskAssignOfficerDetails(List<Object> taskAssignOfficerDetails) {
        this.taskAssignOfficerDetails = taskAssignOfficerDetails;
    }

    public List<TaskImagesDetail> getTaskImagesDetails() {
        return taskImagesDetails;
    }

    public void setTaskImagesDetails(List<TaskImagesDetail> taskImagesDetails) {
        this.taskImagesDetails = taskImagesDetails;
    }

    public List<Object> getTaskCommentsDetails() {
        return taskCommentsDetails;
    }

    public void setTaskCommentsDetails(List<Object> taskCommentsDetails) {
        this.taskCommentsDetails = taskCommentsDetails;
    }

    public class Task {

        @SerializedName("TaskID")
        @Expose
        private Integer taskID;
        @SerializedName("CreatedBy")
        @Expose
        private String createdBy;
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
        private String intelligenceInformation;
        @SerializedName("Aim")
        @Expose
        private String aim;
        @SerializedName("MethodUtilised")
        @Expose
        private String methodUtilised;
        @SerializedName("OfficerSafety")
        @Expose
        private String officerSafety;
        @SerializedName("PostCode")
        @Expose
        private String postCode;
        @SerializedName("Town")
        @Expose
        private String town;
        @SerializedName("Street")
        @Expose
        private String street;
        @SerializedName("HouseNo")
        @Expose
        private String houseNo;
        @SerializedName("County")
        @Expose
        private String county;
        @SerializedName("Latitude")
        @Expose
        private String latitude;
        @SerializedName("Longitude")
        @Expose
        private String longitude;
        @SerializedName("CreatedDate")
        @Expose
        private String createdDate;
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
        private Object location;
        @SerializedName("ManageTask")
        @Expose
        private Object manageTask;
        @SerializedName("RelevantDate")
        @Expose
        private String relevantDate;
        @SerializedName("RelevantTime")
        @Expose
        private String relevantTime;
        @SerializedName("TaskImagesXML")
        @Expose
        private Object taskImagesXML;
        @SerializedName("ExpireDate")
        @Expose
        private String expireDate;
        @SerializedName("TimeLeftExpire")
        @Expose
        private String timeLeftExpire;

        public Integer getTaskID() {
            return taskID;
        }

        public void setTaskID(Integer taskID) {
            this.taskID = taskID;
        }

        public String getCreatedBy() {
            return createdBy;
        }

        public void setCreatedBy(String createdBy) {
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

        public String getIntelligenceInformation() {
            return intelligenceInformation;
        }

        public void setIntelligenceInformation(String intelligenceInformation) {
            this.intelligenceInformation = intelligenceInformation;
        }

        public String getAim() {
            return aim;
        }

        public void setAim(String aim) {
            this.aim = aim;
        }

        public String getMethodUtilised() {
            return methodUtilised;
        }

        public void setMethodUtilised(String methodUtilised) {
            this.methodUtilised = methodUtilised;
        }

        public String getOfficerSafety() {
            return officerSafety;
        }

        public void setOfficerSafety(String officerSafety) {
            this.officerSafety = officerSafety;
        }

        public String getPostCode() {
            return postCode;
        }

        public void setPostCode(String postCode) {
            this.postCode = postCode;
        }

        public String getTown() {
            return town;
        }

        public void setTown(String town) {
            this.town = town;
        }

        public String getStreet() {
            return street;
        }

        public void setStreet(String street) {
            this.street = street;
        }

        public String getHouseNo() {
            return houseNo;
        }

        public void setHouseNo(String houseNo) {
            this.houseNo = houseNo;
        }

        public String getCounty() {
            return county;
        }

        public void setCounty(String county) {
            this.county = county;
        }

        public String getLatitude() {
            return latitude;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
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

        public Object getLocation() {
            return location;
        }

        public void setLocation(Object location) {
            this.location = location;
        }

        public Object getManageTask() {
            return manageTask;
        }

        public void setManageTask(Object manageTask) {
            this.manageTask = manageTask;
        }

        public String getRelevantDate() {
            return relevantDate;
        }

        public void setRelevantDate(String relevantDate) {
            this.relevantDate = relevantDate;
        }

        public String getRelevantTime() {
            return relevantTime;
        }

        public void setRelevantTime(String relevantTime) {
            this.relevantTime = relevantTime;
        }

        public Object getTaskImagesXML() {
            return taskImagesXML;
        }

        public void setTaskImagesXML(Object taskImagesXML) {
            this.taskImagesXML = taskImagesXML;
        }

        public String getExpireDate() {
            return expireDate;
        }

        public void setExpireDate(String expireDate) {
            this.expireDate = expireDate;
        }

        public String getTimeLeftExpire() {
            return timeLeftExpire;
        }

        public void setTimeLeftExpire(String timeLeftExpire) {
            this.timeLeftExpire = timeLeftExpire;
        }

    }

    public class RelevantNominalDetail {

        @SerializedName("NominalID")
        @Expose
        private Integer nominalID;
        @SerializedName("TaskID")
        @Expose
        private Integer taskID;
        @SerializedName("FirstName")
        @Expose
        private String firstName;
        @SerializedName("LastName")
        @Expose
        private String lastName;
        @SerializedName("Alias")
        @Expose
        private String alias;
        @SerializedName("DOB")
        @Expose
        private String dOB;
        @SerializedName("ContactDetails")
        @Expose
        private String contactDetails;
        @SerializedName("SpecificInfo")
        @Expose
        private String specificInfo;
        @SerializedName("IsActive")
        @Expose
        private Boolean isActive;
        @SerializedName("CreatedDate")
        @Expose
        private String createdDate;

        public Integer getNominalID() {
            return nominalID;
        }

        public void setNominalID(Integer nominalID) {
            this.nominalID = nominalID;
        }

        public Integer getTaskID() {
            return taskID;
        }

        public void setTaskID(Integer taskID) {
            this.taskID = taskID;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getAlias() {
            return alias;
        }

        public void setAlias(String alias) {
            this.alias = alias;
        }

        public String getDOB() {
            return dOB;
        }

        public void setDOB(String dOB) {
            this.dOB = dOB;
        }

        public String getContactDetails() {
            return contactDetails;
        }

        public void setContactDetails(String contactDetails) {
            this.contactDetails = contactDetails;
        }

        public String getSpecificInfo() {
            return specificInfo;
        }

        public void setSpecificInfo(String specificInfo) {
            this.specificInfo = specificInfo;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

    }

    public class RelevantVehicleDetail {

        @SerializedName("VehicleID")
        @Expose
        private Integer vehicleID;
        @SerializedName("TaskID")
        @Expose
        private Integer taskID;
        @SerializedName("VehicleVRM")
        @Expose
        private String vehicleVRM;
        @SerializedName("Make")
        @Expose
        private String make;
        @SerializedName("Model")
        @Expose
        private String model;
        @SerializedName("Colour")
        @Expose
        private String colour;
        @SerializedName("SpecificInfo")
        @Expose
        private String specificInfo;
        @SerializedName("IsActive")
        @Expose
        private Boolean isActive;
        @SerializedName("CreatedDate")
        @Expose
        private String createdDate;

        public Integer getVehicleID() {
            return vehicleID;
        }

        public void setVehicleID(Integer vehicleID) {
            this.vehicleID = vehicleID;
        }

        public Integer getTaskID() {
            return taskID;
        }

        public void setTaskID(Integer taskID) {
            this.taskID = taskID;
        }

        public String getVehicleVRM() {
            return vehicleVRM;
        }

        public void setVehicleVRM(String vehicleVRM) {
            this.vehicleVRM = vehicleVRM;
        }

        public String getMake() {
            return make;
        }

        public void setMake(String make) {
            this.make = make;
        }

        public String getModel() {
            return model;
        }

        public void setModel(String model) {
            this.model = model;
        }

        public String getColour() {
            return colour;
        }

        public void setColour(String colour) {
            this.colour = colour;
        }

        public String getSpecificInfo() {
            return specificInfo;
        }

        public void setSpecificInfo(String specificInfo) {
            this.specificInfo = specificInfo;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

        public String getCreatedDate() {
            return createdDate;
        }

        public void setCreatedDate(String createdDate) {
            this.createdDate = createdDate;
        }

    }

    public class TaskImagesDetail {

        @SerializedName("ImageID")
        @Expose
        private Integer imageID;
        @SerializedName("TaskID")
        @Expose
        private Integer taskID;
        @SerializedName("ThumbnailImage")
        @Expose
        private String thumbnailImage;
        @SerializedName("LargeImage")
        @Expose
        private Object largeImage;
        @SerializedName("IsActive")
        @Expose
        private Boolean isActive;

        public Integer getImageID() {
            return imageID;
        }

        public void setImageID(Integer imageID) {
            this.imageID = imageID;
        }

        public Integer getTaskID() {
            return taskID;
        }

        public void setTaskID(Integer taskID) {
            this.taskID = taskID;
        }

        public String getThumbnailImage() {
            return thumbnailImage;
        }

        public void setThumbnailImage(String thumbnailImage) {
            this.thumbnailImage = thumbnailImage;
        }

        public Object getLargeImage() {
            return largeImage;
        }

        public void setLargeImage(Object largeImage) {
            this.largeImage = largeImage;
        }

        public Boolean getIsActive() {
            return isActive;
        }

        public void setIsActive(Boolean isActive) {
            this.isActive = isActive;
        }

    }

}