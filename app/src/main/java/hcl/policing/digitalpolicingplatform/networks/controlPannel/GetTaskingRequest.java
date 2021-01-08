package hcl.policing.digitalpolicingplatform.networks.controlPannel;

import java.io.Serializable;

import hcl.policing.digitalpolicingplatform.models.controlPannel.TaskingRequestDTO;

public class GetTaskingRequest implements Serializable {

    public static TaskingRequestDTO taskingRequest(String startdate, String enddate, String officerid, String pageno, String priority) {
        TaskingRequestDTO taskingRequest = new TaskingRequestDTO();
        taskingRequest.setTaskID("");
        taskingRequest.setTaskTypeID("");
        taskingRequest.setTaskStatus(priority);
        taskingRequest.setStartDate(startdate);
        taskingRequest.setEndDate(enddate);
        taskingRequest.setPriority("");
        taskingRequest.setPageNumber(pageno);
        taskingRequest.setOfficerID(officerid);
        return taskingRequest;
    }
}
