package hcl.policing.digitalpolicingplatform.networks.search;

import android.util.Log;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownServiceException;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.login.LoginActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.models.search.DropdownListResponse;
import hcl.policing.digitalpolicingplatform.models.search.DropdownRequestModel;
import hcl.policing.digitalpolicingplatform.models.search.DropdownResponseModel;
import hcl.policing.digitalpolicingplatform.networks.ApiClient;
import hcl.policing.digitalpolicingplatform.networks.ApiService;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DropdownApi {

    private DropdownResponseModel dropdownResponseModel = null;
    private DropdownListResponse dropdownListResponse = null;

    public DropdownListResponse getDropdown(final BaseActivity context, DropdownRequestModel requestModel) {
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        Call<DropdownResponseModel> call1 = apiService.getDropdown(requestModel);
        call1.enqueue(new Callback<DropdownResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<DropdownResponseModel> call, @NotNull Response<DropdownResponseModel> response) {
                try {
                    if (response.code() == ApiConstants.STATUS_OK || response.isSuccessful()) {
                        Log.e("DROPDOWN API ", "RESPONSE >>>>> " + new Gson().toJson(response.body()));
                        dropdownResponseModel = response.body();
                        if (dropdownResponseModel.getSuccessflag()) {
                            String dropdownStr = dropdownResponseModel.getJsondata();
                            context.loadDropdownData(dropdownStr);
                        } else {
                            BasicMethodsUtil.getInstance().showToast(context, dropdownResponseModel.getMessage());
                        }
                    }
                } catch (Exception e) {
                    DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), DropdownApi.class, "getDropdown");
                }
            }

            @Override
            public void onFailure(Call<DropdownResponseModel> call, Throwable t) {
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
        return dropdownListResponse;
    }
}
