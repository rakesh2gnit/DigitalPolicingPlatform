package hcl.policing.digitalpolicingplatform.networks.profile;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownServiceException;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.login.LoginActivity;
import hcl.policing.digitalpolicingplatform.activities.profile.ChangePasswordActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.profile.ChangePasswordRequestModel;
import hcl.policing.digitalpolicingplatform.models.profile.ChangePasswordResponseModel;
import hcl.policing.digitalpolicingplatform.networks.ApiClient;
import hcl.policing.digitalpolicingplatform.networks.ApiService;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePasswordApi {

    private AppSession appSession = null;
    private ChangePasswordResponseModel changePasswordResponseModel = null;

    public ChangePasswordResponseModel callChangePassword(final ChangePasswordActivity context, ChangePasswordRequestModel requestModel) {
        appSession = new AppSession(context);
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        Call<ChangePasswordResponseModel> call1 = apiService.changePassword(requestModel);
        call1.enqueue(new Callback<ChangePasswordResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ChangePasswordResponseModel> call, @NotNull Response<ChangePasswordResponseModel> response) {
                try {
                    if (response.code() == ApiConstants.STATUS_OK || response.isSuccessful()) {
                        changePasswordResponseModel = response.body();
                        if (changePasswordResponseModel.isSuccessflag()) {
                            appSession.setUserID(null);
                            appSession.setUserData(null);
                            appSession.setLogin(GenericConstant.LOGIN_NO);
                            BasicMethodsUtil.getInstance().launchInitialActivity(context, LoginActivity.class);
                        } else {
                            BasicMethodsUtil.getInstance().showToast(context, changePasswordResponseModel.getMessage());
                        }
                        DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    }
                } catch (Exception e) {
                    DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ChangePasswordApi.class, "callChangePassword");
                }
            }

            @Override
            public void onFailure(Call<ChangePasswordResponseModel> call, Throwable t) {
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
        return changePasswordResponseModel;
    }
}
