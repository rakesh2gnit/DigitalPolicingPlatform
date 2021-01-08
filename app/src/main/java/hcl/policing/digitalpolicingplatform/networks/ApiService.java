package hcl.policing.digitalpolicingplatform.networks;

import hcl.policing.digitalpolicingplatform.models.controlPannel.TaskingRequestDTO;
import hcl.policing.digitalpolicingplatform.models.login.ForgotPasswordRequestModel;
import hcl.policing.digitalpolicingplatform.models.login.ForgotPasswordResponseModel;
import hcl.policing.digitalpolicingplatform.models.login.LoginRequestDTO;
import hcl.policing.digitalpolicingplatform.models.login.LoginResponseDTO;
import hcl.policing.digitalpolicingplatform.models.logout.LogoutRequestModel;
import hcl.policing.digitalpolicingplatform.models.logout.LogoutResponseModel;
import hcl.policing.digitalpolicingplatform.models.notification.NotificationRequestDTO;
import hcl.policing.digitalpolicingplatform.models.process.ProcessFlowRequestModel;
import hcl.policing.digitalpolicingplatform.models.process.ProcessFlowResponseModel;
import hcl.policing.digitalpolicingplatform.models.process.GetSubOrdinateListRequestModel;
import hcl.policing.digitalpolicingplatform.models.process.GetSubOrdinateListResponseModel;
import hcl.policing.digitalpolicingplatform.models.process.officer.SearchUserByIdRequestModel;
import hcl.policing.digitalpolicingplatform.models.process.officer.SearchUserByWordRequestModel;
import hcl.policing.digitalpolicingplatform.models.process.officer.SearchUserResponseModel;
import hcl.policing.digitalpolicingplatform.models.process.officer.UpdateUserRequestModel;
import hcl.policing.digitalpolicingplatform.models.profile.ChangePasswordRequestModel;
import hcl.policing.digitalpolicingplatform.models.profile.ChangePasswordResponseModel;
import hcl.policing.digitalpolicingplatform.models.profile.GetUserDetailResponseModel;
import hcl.policing.digitalpolicingplatform.models.profile.UpdateUserResponseModel;
import hcl.policing.digitalpolicingplatform.models.profile.UserDetailRequestModel;
import hcl.policing.digitalpolicingplatform.models.search.DropdownListResponse;
import hcl.policing.digitalpolicingplatform.models.search.DropdownRequestModel;
import hcl.policing.digitalpolicingplatform.models.search.DropdownResponseModel;
import hcl.policing.digitalpolicingplatform.models.search.TriggerRequestModel;
import hcl.policing.digitalpolicingplatform.models.search.TriggerResponse;
import hcl.policing.digitalpolicingplatform.models.search.TriggerUploadRequest;
import hcl.policing.digitalpolicingplatform.models.tasking.LogsRequestDTO;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskCompleteRequestDTO;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskDetailResponseDTO;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiService {

    //    @Headers({"Accept: application/json"})
    @POST("Account/SignIn")
    Call<LoginResponseDTO> login(@Body LoginRequestDTO user);

    @POST("Account/Signout")
    Call<LogoutResponseModel> logout(@Body LogoutRequestModel logoutRequestModel);

    @POST("Account/ChangePassword")
    Call<ChangePasswordResponseModel> changePassword(@Body ChangePasswordRequestModel changePasswordRequestModel);

    @POST("Account/ForgotPassword")
    Call<ForgotPasswordResponseModel> forgotPassword(@Body ForgotPasswordRequestModel forgotPasswordRequestModel);

    @POST("Configuration/GetProcessFlow")
    Call<ProcessFlowResponseModel> getProcessFlow(@Body ProcessFlowRequestModel processFlowRequestModel);

    @POST("User/SearchUser")
    Call<SearchUserResponseModel> searchUser(@Body SearchUserByWordRequestModel searchUserByWordRequestModel);

    @POST("User/SearchUser")
    Call<SearchUserResponseModel> searchUser(@Body SearchUserByIdRequestModel searchUserByIdRequestModel);

    @POST("User/GetSubOrdinatesList")
    Call<GetSubOrdinateListResponseModel> getSubOrdinateList(@Body GetSubOrdinateListRequestModel getSubOrdinateListRequestModel);

    @POST("User/UpdateUser")
    Call<UpdateUserResponseModel> updateUser(@Body UpdateUserRequestModel updateUserRequestModel);

    @POST("User/GetUserDetailbyUserID")
    Call<GetUserDetailResponseModel> getUserDetails(@Body UserDetailRequestModel userDetailRequestModel);

    @POST("Configuration/SearchAllTrigger")
    Call<TriggerResponse> getTriggers(@Body TriggerRequestModel triggerRequestModel);

    @POST("Configuration/SearchAllTrigger")
    Call<TriggerResponse> uploadTriggers(@Body TriggerUploadRequest triggerUploadRequest);


    @POST("Configuration/GetMasterData")
    Call<DropdownResponseModel> getDropdown(@Body DropdownRequestModel dropdownRequestModel);


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
