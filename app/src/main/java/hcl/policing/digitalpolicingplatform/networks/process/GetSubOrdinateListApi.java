package hcl.policing.digitalpolicingplatform.networks.process;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownServiceException;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.profile.ChangePasswordActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.models.process.GetSubOrdinateListRequestModel;
import hcl.policing.digitalpolicingplatform.models.process.GetSubOrdinateListResponseModel;
import hcl.policing.digitalpolicingplatform.networks.ApiClient;
import hcl.policing.digitalpolicingplatform.networks.ApiService;
import hcl.policing.digitalpolicingplatform.networks.process.officer.SearchUserApi;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetSubOrdinateListApi {

    private AppSession appSession = null;
    private GetSubOrdinateListResponseModel getSubOrdinateListResponseModel = null;

    public GetSubOrdinateListResponseModel GetSubOrdinateList(final ChangePasswordActivity context, GetSubOrdinateListRequestModel requestModel) {
        appSession = new AppSession(context);
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        Call<GetSubOrdinateListResponseModel> subOrdinateListCall = apiService.getSubOrdinateList(requestModel);
        subOrdinateListCall.enqueue(new Callback<GetSubOrdinateListResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<GetSubOrdinateListResponseModel> call, @NotNull Response<GetSubOrdinateListResponseModel> response) {
                try {
                    if (response.code() == ApiConstants.STATUS_OK || response.isSuccessful()) {
                        getSubOrdinateListResponseModel = response.body();
                        if (getSubOrdinateListResponseModel.isSuccessflag()) {
                            BasicMethodsUtil.getInstance().showToast(context, getSubOrdinateListResponseModel.getMessage());
                        } else {
                            BasicMethodsUtil.getInstance().showToast(context, getSubOrdinateListResponseModel.getMessage());
                        }
                        DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    }
                } catch (Exception e) {
                    DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), SearchUserApi.class, "SearchUser");
                }
            }

            @Override
            public void onFailure(Call<GetSubOrdinateListResponseModel> call, Throwable t) {
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
        return getSubOrdinateListResponseModel;
    }
}
