package hcl.policing.digitalpolicingplatform.networks.logout;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownServiceException;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.controlPannel.ControlPanelActivity;
import hcl.policing.digitalpolicingplatform.activities.login.LoginActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;
import hcl.policing.digitalpolicingplatform.models.logout.LogoutRequestModel;
import hcl.policing.digitalpolicingplatform.models.logout.LogoutResponseModel;
import hcl.policing.digitalpolicingplatform.networks.ApiClient;
import hcl.policing.digitalpolicingplatform.networks.ApiService;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LogoutApi {

    private LogoutResponseModel logoutResponseModel = null;
    AppSession appSession = null;

    public LogoutResponseModel callLogout(final Object context, LogoutRequestModel logoutRequestModel, Dialog mProgressDialog) {
        appSession = new AppSession((Context) context);
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        Call<LogoutResponseModel> call1 = apiService.logout(logoutRequestModel);
        call1.enqueue(new Callback<LogoutResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<LogoutResponseModel> call, @NotNull Response<LogoutResponseModel> response) {
                try {
                    if (response.code() == ApiConstants.STATUS_OK || response.isSuccessful()) {
                        logoutResponseModel = response.body();
                        if (logoutResponseModel.isSuccessflag()) {
                            //resetApp(((ControlPanelActivity) context).db);
                            BasicMethodsUtil.getInstance().launchInitialActivity((Context) context, LoginActivity.class);
                        } else {
                            BasicMethodsUtil.getInstance().showToast((Context) context, logoutResponseModel.getMessage());
                        }
                        DialogUtil.cancelProgressDialog(mProgressDialog);
                    }
                } catch (Exception e) {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), LoginActivity.class, "callLogin");
                }
            }

            @Override
            public void onFailure(Call<LogoutResponseModel> call, Throwable t) {
                try {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (t instanceof UnknownServiceException) {
                        BasicMethodsUtil.getInstance().showToast((Context) context, ((Activity) context).getString(R.string.no_network));
                    } else {
                        throw new RuntimeException(t);
                    }
                    call.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        return logoutResponseModel;
    }


    /**
     * Reset app at the time of login
     *
     * @param db
     */
    private void resetApp(DatabaseHelper db) {
        appSession.clear();
        db.clearDB();
    }
}
