package hcl.policing.digitalpolicingplatform.networks.login;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownServiceException;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.login.LoginActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.models.login.ForgotPasswordRequestModel;
import hcl.policing.digitalpolicingplatform.models.login.ForgotPasswordResponseModel;
import hcl.policing.digitalpolicingplatform.networks.ApiClient;
import hcl.policing.digitalpolicingplatform.networks.ApiService;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ForgotPasswordApi {

    AppSession appSession = null;
    private ForgotPasswordResponseModel forgotPasswordResponseModel = null;

    public ForgotPasswordResponseModel callForgotPassword(final LoginActivity context, ForgotPasswordRequestModel requestModel) {
        appSession = new AppSession(context);
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        Call<ForgotPasswordResponseModel> call1 = apiService.forgotPassword(requestModel);
        call1.enqueue(new Callback<ForgotPasswordResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ForgotPasswordResponseModel> call, @NotNull Response<ForgotPasswordResponseModel> response) {
                try {
                    if (response.code() == ApiConstants.STATUS_OK || response.isSuccessful()) {
                        forgotPasswordResponseModel = response.body();
                        if (forgotPasswordResponseModel.isSuccessflag()) {
                            BasicMethodsUtil.getInstance().showToast(context, forgotPasswordResponseModel.getMessage());
                        } else {
                            BasicMethodsUtil.getInstance().showToast(context, forgotPasswordResponseModel.getMessage());
                        }
                        DialogUtil.dismiss();
                        DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    }
                } catch (Exception e) {
                    DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ForgotPasswordApi.class, "callForgotPassword");
                }
            }

            @Override
            public void onFailure(Call<ForgotPasswordResponseModel> call, Throwable t) {
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
        return forgotPasswordResponseModel;
    }
}
