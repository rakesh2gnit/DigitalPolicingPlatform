package hcl.policing.digitalpolicingplatform.networks.process.officer;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownServiceException;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.officer.OfficerSearchActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.models.process.officer.SearchUserByIdRequestModel;
import hcl.policing.digitalpolicingplatform.models.process.officer.SearchUserByWordRequestModel;
import hcl.policing.digitalpolicingplatform.models.process.officer.SearchUserResponseModel;
import hcl.policing.digitalpolicingplatform.networks.ApiClient;
import hcl.policing.digitalpolicingplatform.networks.ApiService;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchUserApi {

    private SearchUserResponseModel searchUserResponseModel = null;
    Call<SearchUserResponseModel> searchUserCall;

    public SearchUserResponseModel SearchUser(final OfficerSearchActivity context, Object requestModel, int type) {
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);

        if (type == ApiConstants.SEARCH_BY_ID) {
            searchUserCall = apiService.searchUser((SearchUserByIdRequestModel) requestModel);
        } else if (type == ApiConstants.SEARCH_BY_WORD) {
            searchUserCall = apiService.searchUser((SearchUserByWordRequestModel) requestModel);
        }

        searchUserCall.enqueue(new Callback<SearchUserResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<SearchUserResponseModel> call, @NotNull Response<SearchUserResponseModel> response) {
                try {
                    if (response.code() == ApiConstants.STATUS_OK || response.isSuccessful()) {
                        searchUserResponseModel = response.body();
                        if (searchUserResponseModel.isSuccessflag()) {
                            context.setOfficerList(searchUserResponseModel.getUsers());
                        } else {
                            BasicMethodsUtil.getInstance().showToast(context, searchUserResponseModel.getMessage());
                        }
                    } else {
                        BasicMethodsUtil.getInstance().showToast(context, response.message());
                    }
                    DialogUtil.cancelProgressDialog(context.mProgressDialog);
                } catch (Exception e) {
                    DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), SearchUserApi.class, "SearchUser");
                }
            }

            @Override
            public void onFailure(Call<SearchUserResponseModel> call, Throwable t) {
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
        return searchUserResponseModel;
    }
}
