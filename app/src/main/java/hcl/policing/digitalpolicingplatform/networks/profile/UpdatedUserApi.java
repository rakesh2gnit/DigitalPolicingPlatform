package hcl.policing.digitalpolicingplatform.networks.profile;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownServiceException;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.profile.ProfileActivity;
import hcl.policing.digitalpolicingplatform.activities.profile.ProfileEditActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.models.process.officer.UpdateUserRequestModel;
import hcl.policing.digitalpolicingplatform.models.profile.UpdateUserResponseModel;
import hcl.policing.digitalpolicingplatform.networks.ApiClient;
import hcl.policing.digitalpolicingplatform.networks.ApiService;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdatedUserApi {

    private UpdateUserResponseModel updateUserResponseModel = null;

    public void updateUser(final ProfileEditActivity context, UpdateUserRequestModel requestModel) {
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        Call<UpdateUserResponseModel> updateUserCall = apiService.updateUser(requestModel);
        updateUserCall.enqueue(new Callback<UpdateUserResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<UpdateUserResponseModel> call, @NotNull Response<UpdateUserResponseModel> response) {
                try {
                    if (response.code() == ApiConstants.STATUS_OK || response.isSuccessful()) {
                        updateUserResponseModel = response.body();
                        if (updateUserResponseModel.isSuccessflag()) {
                            BasicMethodsUtil.getInstance().launchActivity(context,ProfileActivity.class);
                        } else {
                            BasicMethodsUtil.getInstance().showToast(context, updateUserResponseModel.getMessage());
                        }
                        DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    }
                } catch (Exception e) {
                    DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), UpdatedUserApi.class, "updateUser");
                }
            }

            @Override
            public void onFailure(Call<UpdateUserResponseModel> call, Throwable t) {
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
    }
}
