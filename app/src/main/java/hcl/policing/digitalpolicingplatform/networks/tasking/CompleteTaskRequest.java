package hcl.policing.digitalpolicingplatform.networks.tasking;

import hcl.policing.digitalpolicingplatform.models.tasking.TaskCompleteRequestDTO;

public class CompleteTaskRequest {

    public static TaskCompleteRequestDTO completeTask(String taskId, String officerId, String status) {
        TaskCompleteRequestDTO taskCompleteRequest = new TaskCompleteRequestDTO();
        taskCompleteRequest.setTaskID(taskId);
        taskCompleteRequest.setCreatedBy(officerId);
        taskCompleteRequest.setTaskStatus(status);
        return taskCompleteRequest;
    }
}
