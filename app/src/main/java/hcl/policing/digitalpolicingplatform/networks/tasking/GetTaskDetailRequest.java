package hcl.policing.digitalpolicingplatform.networks.tasking;

import hcl.policing.digitalpolicingplatform.models.controlPannel.TaskingRequestDTO;

public class GetTaskDetailRequest {

    public static TaskingRequestDTO taskingRequest(String taskid) {
        TaskingRequestDTO taskingRequest = new TaskingRequestDTO();
        taskingRequest.setTaskID(taskid);
        taskingRequest.setTaskTypeID("");
        taskingRequest.setTaskStatus("");
        taskingRequest.setStartDate("");
        taskingRequest.setEndDate("");
        taskingRequest.setPriority("");
        taskingRequest.setPageNumber("");
        taskingRequest.setOfficerID("");
        return taskingRequest;
    }
}
