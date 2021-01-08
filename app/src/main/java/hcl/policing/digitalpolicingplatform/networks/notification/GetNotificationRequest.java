package hcl.policing.digitalpolicingplatform.networks.notification;

import hcl.policing.digitalpolicingplatform.models.notification.NotificationRequestDTO;

public class GetNotificationRequest {

    public static NotificationRequestDTO getNotification(String taskId, String officerId, String status) {
        NotificationRequestDTO notificationRequest = new NotificationRequestDTO();
        notificationRequest.setTaskID(taskId);
        notificationRequest.setOfficerID(officerId);
        notificationRequest.setTaskAccept(status);
        notificationRequest.setTaskAssignedBy("Self");
        return notificationRequest;
    }
}
