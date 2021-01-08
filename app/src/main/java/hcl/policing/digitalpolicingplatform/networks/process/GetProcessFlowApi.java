package hcl.policing.digitalpolicingplatform.networks.process;

import android.util.Log;

import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;

import java.net.UnknownServiceException;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.models.process.ProcessFlowModel;
import hcl.policing.digitalpolicingplatform.models.process.ProcessFlowRequestModel;
import hcl.policing.digitalpolicingplatform.models.process.ProcessFlowResponseModel;
import hcl.policing.digitalpolicingplatform.networks.ApiClient;
import hcl.policing.digitalpolicingplatform.networks.ApiService;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetProcessFlowApi {

    private ProcessFlowResponseModel processFlowResponseModel = null;

    public ProcessFlowResponseModel callProcessFlow(final BaseActivity context, ProcessFlowRequestModel requestModel, int processId, int subProcessid) {
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        Call<ProcessFlowResponseModel> processFlowCall = apiService.getProcessFlow(requestModel);
        processFlowCall.enqueue(new Callback<ProcessFlowResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ProcessFlowResponseModel> call, @NotNull Response<ProcessFlowResponseModel> response) {
                try {
                    if (response.code() == ApiConstants.STATUS_OK || response.isSuccessful()) {
                        Log.e("PROCESS API ", "RESPONSE >>>>> " + new Gson().toJson(response.body()));
                        processFlowResponseModel = response.body();
                        if (processFlowResponseModel.getSuccessflag()) {

                            ProcessFlowModel processFlowModel = processFlowResponseModel.getProcessflow();
                            int version = (int) processFlowModel.getVersion();
                            if (!context.db.isFlowExist(processFlowModel.getProcessid(), processFlowModel.getSubprocessid(), version)) {
                                context.db.addProcessFlow(processFlowModel);
                            } else {
                                context.db.updateProcessFlow(processId, subProcessid, processFlowModel);
                            }
                            DialogUtil.cancelProgressDialog(context.mProgressDialog);
                            context.loadFlow(processFlowModel.getProcessname(), processFlowModel.getProcessflowstring());
                        } else {
                            DialogUtil.cancelProgressDialog(context.mProgressDialog);
                            BasicMethodsUtil.getInstance().showToast(context, processFlowResponseModel.getMessage());
                        }
                    } else {
                        DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    }
                } catch (Exception e) {
                    DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), GetProcessFlowApi.class, "callProcessFlow");
                }
            }

            @Override
            public void onFailure(Call<ProcessFlowResponseModel> call, Throwable t) {
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
        return processFlowResponseModel;
    }

    public ProcessFlowResponseModel callUpdateProcessFlow(final BaseActivity context, ProcessFlowRequestModel requestModel, int processId, int subProcessid) {
        ApiService apiService = ApiClient.getInstance().create(ApiService.class);
        Call<ProcessFlowResponseModel> processFlowCall = apiService.getProcessFlow(requestModel);
        processFlowCall.enqueue(new Callback<ProcessFlowResponseModel>() {
            @Override
            public void onResponse(@NotNull Call<ProcessFlowResponseModel> call, @NotNull Response<ProcessFlowResponseModel> response) {
                try {
                    if (response.code() == ApiConstants.STATUS_OK || response.isSuccessful()) {
                        Log.e("PROCESS UPDATE API ", "RESPONSE >>>>> " + new Gson().toJson(response.body()));
                        processFlowResponseModel = response.body();
                        if (processFlowResponseModel.getSuccessflag()) {

                            ProcessFlowModel processFlowModel = processFlowResponseModel.getProcessflow();
                            int version = (int) processFlowModel.getVersion();
                            if (!context.db.isFlowExist(processFlowModel.getProcessid(), processFlowModel.getSubprocessid(), version)) {
                                context.db.addProcessFlow(processFlowModel);
                            } else {
                                context.db.updateProcessFlow(processId, subProcessid, processFlowModel);
                            }
                            DialogUtil.cancelProgressDialog(context.mProgressDialog);
                        } else {
                            DialogUtil.cancelProgressDialog(context.mProgressDialog);
                            BasicMethodsUtil.getInstance().showToast(context, processFlowResponseModel.getMessage());
                        }
                    } else {
                        DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    }
                } catch (Exception e) {
                    DialogUtil.cancelProgressDialog(context.mProgressDialog);
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), GetProcessFlowApi.class, "callProcessFlow");
                }
            }

            @Override
            public void onFailure(Call<ProcessFlowResponseModel> call, Throwable t) {
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
        return processFlowResponseModel;
    }
}
