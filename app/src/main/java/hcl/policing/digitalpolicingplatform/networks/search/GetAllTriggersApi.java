package hcl.policing.digitalpolicingplatform.networks.search;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownServiceException;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.login.LoginActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.search.TriggerRequestModel;
import hcl.policing.digitalpolicingplatform.models.search.TriggerResponse;
import hcl.policing.digitalpolicingplatform.networks.ApiClient;
import hcl.policing.digitalpolicingplatform.networks.ApiService;
import hcl.policing.digitalpolicingplatform.networks.profile.ChangePasswordApi;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetAllTriggersApi {

    private TriggerResponse triggerResponse = null;

    public TriggerResponse getTriggers(final BaseActivity context, TriggerRequestModel requestModel) {
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        Call<TriggerResponse> call1 = apiService.getTriggers(requestModel);
        call1.enqueue(new Callback<TriggerResponse>() {
            @Override
            public void onResponse(@NotNull Call<TriggerResponse> call, @NotNull Response<TriggerResponse> response) {
                try {
                    if (response.code() == ApiConstants.STATUS_OK || response.isSuccessful()) {
                        triggerResponse = response.body();
                        if (triggerResponse.getSuccessflag()) {
                            context.db.addTriggerList(triggerResponse.getTriggers());
                        } else {
                            BasicMethodsUtil.getInstance().showToast(context, triggerResponse.getMessage());
                        }
                    }
                } catch (Exception e) {
                    DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ChangePasswordApi.class, "callChangePassword");
                }
            }

            @Override
            public void onFailure(Call<TriggerResponse> call, Throwable t) {
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
        return triggerResponse;
    }
}
