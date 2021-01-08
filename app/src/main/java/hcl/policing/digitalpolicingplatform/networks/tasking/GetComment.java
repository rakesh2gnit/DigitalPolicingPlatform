package hcl.policing.digitalpolicingplatform.networks.tasking;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.net.UnknownServiceException;
import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.fragments.tasking.FragmentLogs;
import hcl.policing.digitalpolicingplatform.models.tasking.CommentResponseDTO;
import hcl.policing.digitalpolicingplatform.models.tasking.LogsRequestDTO;
import hcl.policing.digitalpolicingplatform.networks.ApiClient;
import hcl.policing.digitalpolicingplatform.networks.ApiService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetComment {

    ArrayList<CommentResponseDTO> responseBody = null;

    public ArrayList<CommentResponseDTO> getComment(final FragmentLogs context, String taskId) {

        Log.e("called", "   callled>>>>");

        String masterDataVersionNo = "";//Preferences.getStringValue(this, Preferences.MASTERDATAVERSION_NO, "");

        LogsRequestDTO addLogRequest = null;
        addLogRequest = GetCommentListRequest.getComment(taskId);

        //apiInterface = VAClient.getRetrofitInstance().create(VAAPI.class);
        ApiService apiInterface = ApiClient.getInstance().create(ApiService.class);

        Call<ResponseBody> call1 = apiInterface.getComments(addLogRequest);

        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 200) {
                        String result = response.body().string();
                        responseBody = new ArrayList<>();
                        Gson gson = new Gson();
                        responseBody = gson.fromJson(result.toString(), new TypeToken<ArrayList<CommentResponseDTO>>() {

                        }.getType());

                        context.resultList(responseBody);
                    } else {
                        //Toast.makeText(context, "Bad Request!!!" + response.code(), Toast.LENGTH_SHORT).show();
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
                        //Toast.makeText(context, "Internet not working!!!", Toast.LENGTH_SHORT).show();
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
