package hcl.policing.digitalpolicingplatform.models.controlPannel;

import java.io.Serializable;

public class TaskingRequestDTO implements Serializable {

    private String TaskID;
    private String TaskTypeID;
    private String TaskStatus;
    private String Priority;
    private String StartDate;
    private String EndDate;
    private String PageNumber;
    private String OfficerID;

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
    }

    public String getTaskTypeID() {
        return TaskTypeID;
    }

    public void setTaskTypeID(String taskTypeID) {
        TaskTypeID = taskTypeID;
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

    public String getPageNumber() {
        return PageNumber;
    }

    public void setPageNumber(String pageNumber) {
        PageNumber = pageNumber;
    }

    public String getOfficerID() {
        return OfficerID;
    }

    public void setOfficerID(String officerID) {
        OfficerID = officerID;
    }
}
