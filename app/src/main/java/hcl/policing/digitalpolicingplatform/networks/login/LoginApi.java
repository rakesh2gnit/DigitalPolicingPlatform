package hcl.policing.digitalpolicingplatform.networks.login;

import android.util.Log;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownServiceException;
import java.util.Date;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.login.LoginActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.login.LoginRequestDTO;
import hcl.policing.digitalpolicingplatform.models.login.LoginResponseDTO;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserModel;
import hcl.policing.digitalpolicingplatform.networks.ApiClient;
import hcl.policing.digitalpolicingplatform.networks.ApiService;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginApi {

    private LoginResponseDTO result = null;
    AppSession appSession = null;

    public LoginResponseDTO callLogin(final BaseActivity context, LoginRequestDTO loginRequestDTO) {
        appSession = new AppSession(context);
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        Call<LoginResponseDTO> call1 = apiService.login(loginRequestDTO);
        call1.enqueue(new Callback<LoginResponseDTO>() {

            @Override
            public void onResponse(@NotNull Call<LoginResponseDTO> call, @NotNull Response<LoginResponseDTO> response) {
                try {
                    Log.e("LOGIN ", "RESPONSE CODE >> " + response.code() +" >> "+response.isSuccessful());
                    if (response.code() == ApiConstants.STATUS_OK || response.isSuccessful()) {
                        result = response.body();
                        Log.e("LOGIN API", "RESPONSE >>>>> " + new Gson().toJson(response.body()));
                        if (result.getSuccessflag()) {
                            UserModel userModel = result.getUser();
                            SharedPrefUtils.getInstance(context).setLong(SharedPrefUtils.Key.LAST_TOKEN_TIME, new Date().getTime());
                            //appSession.setUserID(loginRequestDTO.getUsername());
                            if (userModel != null) {
                                SharedPrefUtils.getInstance(context).setString(SharedPrefUtils.Key.SESSION_ID, userModel.getToken());
                                appSession.setUserData(userModel);
                                appSession.setLogin(GenericConstant.LOGIN_YES);
                            }
                            Log.e("LOGIN ", "NAME >> " + result.getUser().getForename());
                            if (context.getCallingActivity() != null) {
                                Log.e("LOGIN ", "called >> flow");
                                context.loginFlow();
                            } else {
                                Log.e("LOGIN ", "called >> rights");
                                context.loadUserRights(result);
                            }
                        } else {
                            DialogUtil.cancelProgressDialog(context.mProgressDialog);
                            appSession.setUserID(loginRequestDTO.getUsername());
                            appSession.setLogin(GenericConstant.LOGIN_NO);
                            BasicMethodsUtil.getInstance().showToast(context, result.getMessage());
                        }
                    }
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), LoginActivity.class, "callLogin");
                }
            }

            @Override
            public void onFailure(Call<LoginResponseDTO> call, Throwable t) {
                try {
                    DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    appSession.setUserID(loginRequestDTO.getUsername());
                    appSession.setLogin(GenericConstant.LOGIN_NO);
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
        return result;
    }
}
