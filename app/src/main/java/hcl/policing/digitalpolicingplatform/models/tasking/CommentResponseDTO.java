package hcl.policing.digitalpolicingplatform.models.tasking;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class CommentResponseDTO implements Serializable {

    @SerializedName("CommentID")
    @Expose
    private Integer commentID;
    @SerializedName("OfficerName")
    @Expose
    private String officerName;
    @SerializedName("CreatedBy")
    @Expose
    private String createdBy;
    @SerializedName("Comment")
    @Expose
    private String comment;
    @SerializedName("TaskID")
    @Expose
    private Integer taskID;
    @SerializedName("PageNumber")
    @Expose
    private Integer pageNumber;
    @SerializedName("CreatedDate")
    @Expose
    private String createdDate;

    public Integer getCommentID() {
        return commentID;
    }

    public void setCommentID(Integer commentID) {
        this.commentID = commentID;
    }

    public String getOfficerName() {
        return officerName;
    }

    public void setOfficerName(String officerName) {
        this.officerName = officerName;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getTaskID() {
        return taskID;
    }

    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
    }

    public Integer getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(Integer pageNumber) {
        this.pageNumber = pageNumber;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

}