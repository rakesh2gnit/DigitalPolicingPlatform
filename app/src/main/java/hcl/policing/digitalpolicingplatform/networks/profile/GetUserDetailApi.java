package hcl.policing.digitalpolicingplatform.networks.profile;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownServiceException;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.profile.ProfileActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.models.profile.GetUserDetailResponseModel;
import hcl.policing.digitalpolicingplatform.models.profile.UserDetailRequestModel;
import hcl.policing.digitalpolicingplatform.networks.ApiClient;
import hcl.policing.digitalpolicingplatform.networks.ApiService;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetUserDetailApi {

    private GetUserDetailResponseModel getUserDetailResponseModel = null;

    public GetUserDetailResponseModel getUserDetail(final ProfileActivity context, UserDetailRequestModel requestModel) {
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        Call<GetUserDetailResponseModel> userDetailCall = apiService.getUserDetails(requestModel);
        userDetailCall.enqueue(new Callback<GetUserDetailResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<GetUserDetailResponseModel> call, @NotNull Response<GetUserDetailResponseModel> response) {
                try {
                    if (response.code() == ApiConstants.STATUS_OK || response.isSuccessful()) {
                        getUserDetailResponseModel = response.body();
                        if (getUserDetailResponseModel.isSuccessflag()) {
                            context.setProfileData(getUserDetailResponseModel);
                        } else {
                            BasicMethodsUtil.getInstance().showToast(context, getUserDetailResponseModel.getMessage());
                        }
                    } else {
                        BasicMethodsUtil.getInstance().showToast(context, response.message());
                    }
                    DialogUtil.cancelProgressDialog(context.mProgressDialog);
                } catch (Exception e) {
                    DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), GetUserDetailApi.class, "getUserDetail");
                }
            }

            @Override
            public void onFailure(Call<GetUserDetailResponseModel> call, Throwable t) {
                try {
                    DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    if (t instanceof UnknownServiceException) {
                        BasicMethodsUtil.getInstance().showToast(context, context.getString(R.string.no_network));
                    } else {
                        throw new RuntimeException(t);
                    }
                    call.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return getUserDetailResponseModel;
    }
}
