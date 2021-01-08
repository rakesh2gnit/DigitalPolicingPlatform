package hcl.policing.digitalpolicingplatform.models.notification;

import java.io.Serializable;

public class NotificationRequestDTO implements Serializable {

    private String AssignID;
    private String TaskID;
    private String OfficerID;
    private String OfficerName;
    private String MobileID;
    private String TaskAccept;
    private String Rank;
    private String TaskAssignDate;
    private String TaskAcceptDenyDate;
    private String TaskAssignedBy;
    private String DenyReason;
    private String IsActive;

    public String getAssignID() {
        return AssignID;
    }

    public void setAssignID(String assignID) {
        AssignID = assignID;
    }

    public String getTaskID() {
        return TaskID;
    }

    public void setTaskID(String taskID) {
        TaskID = taskID;
    }

    public String getOfficerID() {
        return OfficerID;
    }

    public void setOfficerID(String officerID) {
        OfficerID = officerID;
    }

    public String getOfficerName() {
        return OfficerName;
    }

    public void setOfficerName(String officerName) {
        OfficerName = officerName;
    }

    public String getMobileID() {
        return MobileID;
    }

    public void setMobileID(String mobileID) {
        MobileID = mobileID;
    }

    public String getTaskAccept() {
        return TaskAccept;
    }

    public void setTaskAccept(String taskAccept) {
        TaskAccept = taskAccept;
    }

    public String getRank() {
        return Rank;
    }

    public void setRank(String rank) {
        Rank = rank;
    }

    public String getTaskAssignDate() {
        return TaskAssignDate;
    }

    public void setTaskAssignDate(String taskAssignDate) {
        TaskAssignDate = taskAssignDate;
    }

    public String getTaskAcceptDenyDate() {
        return TaskAcceptDenyDate;
    }

    public void setTaskAcceptDenyDate(String taskAcceptDenyDate) {
        TaskAcceptDenyDate = taskAcceptDenyDate;
    }

    public String getTaskAssignedBy() {
        return TaskAssignedBy;
    }

    public void setTaskAssignedBy(String taskAssignedBy) {
        TaskAssignedBy = taskAssignedBy;
    }

    public String getDenyReason() {
        return DenyReason;
    }

    public void setDenyReason(String denyReason) {
        DenyReason = denyReason;
    }

    public String getIsActive() {
        return IsActive;
    }

    public void setIsActive(String isActive) {
        IsActive = isActive;
    }
}
