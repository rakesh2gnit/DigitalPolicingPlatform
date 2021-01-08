package hcl.policing.digitalpolicingplatform.networks;

import hcl.policing.digitalpolicingplatform.models.controlPannel.TaskingRequestDTO;
import hcl.policing.digitalpolicingplatform.models.notification.NotificationRequestDTO;
import hcl.policing.digitalpolicingplatform.models.login.LoginRequestDTO;
import hcl.policing.digitalpolicingplatform.models.login.LoginResponseDTO;
import hcl.policing.digitalpolicingplatform.models.tasking.LogsRequestDTO;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskCompleteRequestDTO;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskDetailResponseDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface DPP_API {

    @POST("authenticateuser")
    Call<LoginResponseDTO> doLoginNew(@Body LoginRequestDTO user);

    @Headers({"Accept: application/json"})
    @POST("taskassign")
    Call<ResponseBody> updateTaskAssign(@Body NotificationRequestDTO user);

    @Headers({"Accept: application/json"})
    @POST("tasklist")
    Call<ResponseBody> getTaskList(@Body TaskingRequestDTO user);

    //taskdetails
    @Headers({"Accept: application/json"})
    @POST("taskdetails")
    Call<TaskDetailResponseDTO> getTaskDetail(@Body TaskingRequestDTO user);

    @Headers({"Accept: application/json"})
    @POST("updatetaskstatus")
    Call<ResponseBody> completeTask(@Body TaskCompleteRequestDTO user);

    //taskcommentsaction
    @Headers({"Accept: application/json"})
    @POST("taskcommentsaction")
    Call<ResponseBody> addComment(@Body LogsRequestDTO user);

    //getcommentslist
    @Headers({"Accept: application/json"})
    @POST("getcommentslist")
    Call<ResponseBody> getComments(@Body LogsRequestDTO user);

    /*@POST("AuthenticationService.svc/AuthenticateUser")
    Call<LoginResponseDTO> doLogin(@Body LoginRequestDTO user);*/
}
