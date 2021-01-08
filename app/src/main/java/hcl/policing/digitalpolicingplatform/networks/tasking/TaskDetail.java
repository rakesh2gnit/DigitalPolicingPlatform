package hcl.policing.digitalpolicingplatform.networks.tasking;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownServiceException;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.notification.NotificationActivity;
import hcl.policing.digitalpolicingplatform.activities.notification.NotificationDetailActivity;
import hcl.policing.digitalpolicingplatform.fragments.controlPannel.ControlPanelMainFrafment;
import hcl.policing.digitalpolicingplatform.models.controlPannel.TaskingRequestDTO;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskDetailResponseDTO;
import hcl.policing.digitalpolicingplatform.networks.ApiClient;
import hcl.policing.digitalpolicingplatform.networks.ApiService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TaskDetail {

    TaskDetailResponseDTO taskDetailResponse = null;
    ProgressDialog progressDoalog;

    public TaskDetailResponseDTO getTaskDetail(ControlPanelMainFrafment context, String taskid, final int index) {

        Log.e("called", "   callled>>>>");

        progressDoalog = ProgressDialog.show(context.getActivity(), null, null);
        progressDoalog.setContentView(R.layout.progress_loader_login);
        Objects.requireNonNull(progressDoalog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDoalog.setCancelable(false);
        progressDoalog.show();

        TaskingRequestDTO taskingRequest = null;
        taskingRequest = GetTaskDetailRequest.taskingRequest(taskid);

        //apiInterface = VAClient.getRetrofitInstance().create(VAAPI.class);
        ApiService apiInterface = ApiClient.getInstance().create(ApiService.class);

        Call<TaskDetailResponseDTO> call1 = apiInterface.getTaskDetail(taskingRequest);

        call1.enqueue(new Callback<TaskDetailResponseDTO>() {
            @Override
            public void onResponse(Call<TaskDetailResponseDTO> call, Response<TaskDetailResponseDTO> response) {
                try {
                    if (response.code() == 200) {
                        progressDoalog.dismiss();
                        taskDetailResponse = new TaskDetailResponseDTO();
                        taskDetailResponse = response.body();
                        Log.e("detail inside ", " >>>>>>  " + taskDetailResponse.getTask().getAim());
                        context.setTaskDetailResult(taskDetailResponse);
                    } else {
                        progressDoalog.dismiss();
                        taskDetailResponse = new TaskDetailResponseDTO();
                        JSONObject obj = new JSONObject(loadJSONFromAsset(context.getActivity(), index));
                        Gson gson = new Gson();
                        taskDetailResponse = gson.fromJson(obj.toString(), new TypeToken<TaskDetailResponseDTO>() {

                        }.getType());
                        context.setTaskDetailResult(taskDetailResponse);
                        //Toast.makeText(context, "Bad Request!!!" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //findViewById(R.id.pBar).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TaskDetailResponseDTO> call, Throwable t) {
                try {
                    progressDoalog.dismiss();
                    taskDetailResponse = new TaskDetailResponseDTO();
                    JSONObject obj = new JSONObject(loadJSONFromAsset(context.getActivity(), index));
                    Gson gson = new Gson();
                    taskDetailResponse = gson.fromJson(obj.toString(), new TypeToken<TaskDetailResponseDTO>() {

                    }.getType());
                    context.setTaskDetailResult(taskDetailResponse);
                    if (t instanceof UnknownServiceException) {
                        Toast.makeText(context.getActivity(), "Internet not working!!!", Toast.LENGTH_SHORT).show();
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
        return taskDetailResponse;
    }

    public TaskDetailResponseDTO getTaskDetail(final NotificationActivity context, String taskid, final int index) {

        Log.e("called", "   callled>>>>");

        progressDoalog = ProgressDialog.show(context, null, null);
        progressDoalog.setContentView(R.layout.progress_loader_login);
        Objects.requireNonNull(progressDoalog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDoalog.setCancelable(false);
        progressDoalog.show();

        TaskingRequestDTO taskingRequest = null;
        taskingRequest = GetTaskDetailRequest.taskingRequest(taskid);

        //apiInterface = VAClient.getRetrofitInstance().create(VAAPI.class);
        ApiService apiInterface = ApiClient.getInstance().create(ApiService.class);

        Call<TaskDetailResponseDTO> call1 = apiInterface.getTaskDetail(taskingRequest);

        call1.enqueue(new Callback<TaskDetailResponseDTO>() {
            @Override
            public void onResponse(Call<TaskDetailResponseDTO> call, Response<TaskDetailResponseDTO> response) {
                try {
                    if (response.code() == 200) {
                        progressDoalog.dismiss();
                        taskDetailResponse = new TaskDetailResponseDTO();
                        taskDetailResponse = response.body();
                        Log.e("detail inside ", " >>>>>>  " + taskDetailResponse.getTask().getAim());
                        context.setTaskDetailResult(taskDetailResponse);
                    } else {
                        progressDoalog.dismiss();
                        taskDetailResponse = new TaskDetailResponseDTO();
                        JSONObject obj = new JSONObject(loadJSONFromAsset(context, index));
                        Gson gson = new Gson();
                        taskDetailResponse = gson.fromJson(obj.toString(), new TypeToken<TaskDetailResponseDTO>() {

                        }.getType());
                        context.setTaskDetailResult(taskDetailResponse);
                        //Toast.makeText(context, "Bad Request!!!" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //findViewById(R.id.pBar).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TaskDetailResponseDTO> call, Throwable t) {
                try {
                    progressDoalog.dismiss();
                    taskDetailResponse = new TaskDetailResponseDTO();
                    JSONObject obj = new JSONObject(loadJSONFromAsset(context, index));
                    Gson gson = new Gson();
                    taskDetailResponse = gson.fromJson(obj.toString(), new TypeToken<TaskDetailResponseDTO>() {

                    }.getType());
                    context.setTaskDetailResult(taskDetailResponse);
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
        return taskDetailResponse;
    }

    public TaskDetailResponseDTO getTaskDetail(final NotificationDetailActivity context, String taskid) {
        Log.e("called", "   callled>>>>");

        progressDoalog = ProgressDialog.show(context, null, null);
        progressDoalog.setContentView(R.layout.progress_loader_login);
        Objects.requireNonNull(progressDoalog.getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        progressDoalog.setCancelable(false);
        progressDoalog.show();

        TaskingRequestDTO taskingRequest = null;
        taskingRequest = GetTaskDetailRequest.taskingRequest(taskid);

        //apiInterface = VAClient.getRetrofitInstance().create(VAAPI.class);
        ApiService apiInterface = ApiClient.getInstance().create(ApiService.class);

        Call<TaskDetailResponseDTO> call1 = apiInterface.getTaskDetail(taskingRequest);

        call1.enqueue(new Callback<TaskDetailResponseDTO>() {
            @Override
            public void onResponse(Call<TaskDetailResponseDTO> call, Response<TaskDetailResponseDTO> response) {
                try {
                    if (response.code() == 200) {
                        progressDoalog.dismiss();
                        taskDetailResponse = new TaskDetailResponseDTO();
                        taskDetailResponse = response.body();
                        Log.e("detail inside ", " >>>>>>  " + taskDetailResponse.getTask().getAim());
                        context.setTaskDetailResult(taskDetailResponse);
                    } else {
                        Toast.makeText(context, "Bad Request!!!" + response.code(), Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //findViewById(R.id.pBar).setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<TaskDetailResponseDTO> call, Throwable t) {
                try {
                    progressDoalog.dismiss();
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
        return taskDetailResponse;
    }

    public String loadJSONFromAsset(Context context, int position) {
        String filename = "";
        if (position == 0) {
            filename = "task_11.json";
        } else if (position == 1) {
            filename = "task_9.json";
        } else if (position == 2) {
            filename = "task_8.json";
        } else if (position == 3) {
            filename = "task_4.json";
        } else if (position == 4) {
            filename = "task_3.json";
        }
        String json = null;
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }
}
