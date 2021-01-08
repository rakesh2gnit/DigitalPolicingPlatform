package hcl.policing.digitalpolicingplatform.networks.tasking;

import hcl.policing.digitalpolicingplatform.models.tasking.LogsRequestDTO;

public class GetCommentListRequest {

    public static LogsRequestDTO getComment(String taskId) {
        LogsRequestDTO logsRequestDTO = new LogsRequestDTO();
        logsRequestDTO.setTaskID(taskId);
        return logsRequestDTO;
    }
}
