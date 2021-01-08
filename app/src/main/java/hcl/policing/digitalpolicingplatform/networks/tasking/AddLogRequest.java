package hcl.policing.digitalpolicingplatform.networks.tasking;

import hcl.policing.digitalpolicingplatform.models.tasking.LogsRequestDTO;

public class AddLogRequest {

    public static LogsRequestDTO addComment(String taskId, String officerId, String comment, String page) {
        LogsRequestDTO logsRequestDTO = new LogsRequestDTO();
        logsRequestDTO.setTaskID(taskId);
        logsRequestDTO.setCreatedBy(officerId);
        logsRequestDTO.setComment(comment);
        logsRequestDTO.setPageNumber(page);
        return logsRequestDTO;
    }
}
