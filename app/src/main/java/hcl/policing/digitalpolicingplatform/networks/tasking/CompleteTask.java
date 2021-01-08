package hcl.policing.digitalpolicingplatform.networks.tasking;

import android.util.Log;
import android.widget.Toast;

import java.net.UnknownServiceException;

import hcl.policing.digitalpolicingplatform.activities.tasking.TaskDetailActivity;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskCompleteRequestDTO;
import hcl.policing.digitalpolicingplatform.networks.ApiClient;
import hcl.policing.digitalpolicingplatform.networks.ApiService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompleteTask {

    ResponseBody responseBody = null;

    public ResponseBody completeTask(final TaskDetailActivity context, String taskId, final String officerId, final String status) {

        Log.e("called", "   callled>>>>");

        String masterDataVersionNo = "";//Preferences.getStringValue(this, Preferences.MASTERDATAVERSION_NO, "");

        TaskCompleteRequestDTO taskCompleteRequest = null;
        taskCompleteRequest = CompleteTaskRequest.completeTask(taskId, officerId, status);

        //apiInterface = VAClient.getRetrofitInstance().create(VAAPI.class);
        ApiService apiInterface = ApiClient.getInstance().create(ApiService.class);

        Call<ResponseBody> call1 = apiInterface.completeTask(taskCompleteRequest);

        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 200) {
                        responseBody = response.body();
                        //loginResponse = response.body();
                        Log.e("RESULT ", ">>>>>> " + response.body().string());
                        //{"Flag":true,"Message":""}
                        context.result();
                    } else {
                        Toast.makeText(context, "Bad Request!!!" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //findViewById(R.id.pBar).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                try {
                    if (t instanceof UnknownServiceException) {
                        Toast.makeText(context, "Internet not working!!!", Toast.LENGTH_SHORT).show();
                    } else {
                        throw new RuntimeException(t);
                    }
                    call.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //findViewById(R.id.pBar).setVisibility(View.GONE);
            }
        });
        return responseBody;
    }
}
