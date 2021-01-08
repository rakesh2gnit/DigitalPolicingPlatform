package hcl.policing.digitalpolicingplatform.networks.controlPannel;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownServiceException;
import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.activities.notification.NotificationActivity;
import hcl.policing.digitalpolicingplatform.fragments.controlPannel.ControlPanelMainFrafment;
import hcl.policing.digitalpolicingplatform.models.controlPannel.TaskResponseDTO;
import hcl.policing.digitalpolicingplatform.models.controlPannel.TaskingRequestDTO;
import hcl.policing.digitalpolicingplatform.networks.ApiClient;
import hcl.policing.digitalpolicingplatform.networks.ApiService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Task {

    ArrayList<TaskResponseDTO> responseList = null;

    public ArrayList<TaskResponseDTO> getListofTask(final ControlPanelMainFrafment context, String startdater, String enddate, final String officerId, String pageno, String status) {

        Log.e("called", "   callled>>>>");

        TaskingRequestDTO taskingRequest = null;
        taskingRequest = GetTaskingRequest.taskingRequest(startdater, enddate, officerId, pageno, status);

        //apiInterface = ApiClient.getRetrofitInstance().create(ApiService.class);
        ApiService apiInterface = ApiClient.getInstance().create(ApiService.class);

        Call<ResponseBody> call1 = apiInterface.getTaskList(taskingRequest);

        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 200) {
                        String result = response.body().string();

                        Log.e("TASK LIST ", ">>>>>> " + result);

                        responseList = new ArrayList<>();
                        if (result != null && !result.equalsIgnoreCase("")) {
                            Gson gson = new Gson();
                            responseList = gson.fromJson(result, new TypeToken<ArrayList<TaskResponseDTO>>() {
                            }.getType());
                        }

                        //Log.e("TASK LIST ", "NAME >>>>>> " + responseList.get(0).getTaskTypeName());
                        Log.e("TASK LIST ", "Size >>>>>> " + responseList.size());

                        if (responseList != null && responseList.size() > 0) {
                            context.setPendingActionList(responseList);
                        } else {
                            responseList = new ArrayList();
                            JSONArray obj = new JSONArray(loadJSONFromAsset(context.getActivity(), "task_list.json"));
                            Gson gson = new Gson();
                            responseList = gson.fromJson(obj.toString(), new TypeToken<ArrayList<TaskResponseDTO>>() {

                            }.getType());
                            context.setPendingActionList(responseList);
                        }
                    } else {
                        responseList = new ArrayList();
                        JSONArray obj = new JSONArray(loadJSONFromAsset(context.getActivity(), "task_list.json"));
                        Gson gson = new Gson();
                        responseList = gson.fromJson(obj.toString(), new TypeToken<ArrayList<TaskResponseDTO>>() {

                        }.getType());
                        context.setPendingActionList(responseList);
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
                    responseList = new ArrayList();
                    JSONArray obj = new JSONArray(loadJSONFromAsset(context.getActivity(), "task_list.json"));
                    Gson gson = new Gson();
                    responseList = gson.fromJson(obj.toString(), new TypeToken<ArrayList<TaskResponseDTO>>() {

                    }.getType());
                    context.setPendingActionList(responseList);
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
        return responseList;
    }

    public ArrayList<TaskResponseDTO> getListofTask(final NotificationActivity context, String startdater, String enddate, final String officerId, String pageno, String status) {
        Log.e("called", "   callled>>>>");

        TaskingRequestDTO taskingRequest = null;
        taskingRequest = GetTaskingRequest.taskingRequest(startdater, enddate, officerId, pageno, status);

        //apiInterface = ApiClient.getRetrofitInstance().create(ApiService.class);
        ApiService apiInterface = ApiClient.getInstance().create(ApiService.class);

        Call<ResponseBody> call1 = apiInterface.getTaskList(taskingRequest);

        call1.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if (response.code() == 200) {
                        String result = response.body().string();

                        Log.e("TASK LIST ", ">>>>>> " + result);

                        responseList = new ArrayList<>();
                        if (result != null && !result.equalsIgnoreCase("")) {
                            Gson gson = new Gson();
                            responseList = gson.fromJson(result, new TypeToken<ArrayList<TaskResponseDTO>>() {
                            }.getType());
                        }

                        //Log.e("TASK LIST ", "NAME >>>>>> " + responseList.get(0).getTaskTypeName());
                        Log.e("TASK LIST ", "Size >>>>>> " + responseList.size());

                        if (responseList != null && responseList.size() > 0) {
                            context.setTaskResult(responseList);
                        } else {
                            responseList = new ArrayList();
                            JSONArray obj = new JSONArray(loadJSONFromAsset(context, "task_list.json"));
                            Gson gson = new Gson();
                            responseList = gson.fromJson(obj.toString(), new TypeToken<ArrayList<TaskResponseDTO>>() {

                            }.getType());
                            context.setTaskResult(responseList);
                        }

                        //return responseList;
                    } else {
                        responseList = new ArrayList();
                        JSONArray obj = new JSONArray(loadJSONFromAsset(context, "task_list.json"));
                        Gson gson = new Gson();
                        responseList = gson.fromJson(obj.toString(), new TypeToken<ArrayList<TaskResponseDTO>>() {

                        }.getType());
                        context.setTaskResult(responseList);
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
                    responseList = new ArrayList();
                    JSONArray obj = new JSONArray(loadJSONFromAsset(context, "task_list.json"));
                    Gson gson = new Gson();
                    responseList = gson.fromJson(obj.toString(), new TypeToken<ArrayList<TaskResponseDTO>>() {

                    }.getType());
                    context.setTaskResult(responseList);
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
        return responseList;
    }

    public String loadJSONFromAsset(Context context, String filename) {
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
