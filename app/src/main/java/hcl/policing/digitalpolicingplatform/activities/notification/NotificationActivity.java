package hcl.policing.digitalpolicingplatform.activities.notification;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.tasking.TaskDetailActivity;
import hcl.policing.digitalpolicingplatform.adapters.notification.NotificationListAdapter;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.controlPannel.TaskResponseDTO;
import hcl.policing.digitalpolicingplatform.models.notification.NotificationResponseDTO;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskDetailResponseDTO;
import hcl.policing.digitalpolicingplatform.networks.controlPannel.Task;
import hcl.policing.digitalpolicingplatform.networks.tasking.TaskDetail;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class NotificationActivity extends BaseActivity {

    private AppSession appSession;
    private RecyclerView rvList;
    private NotificationListAdapter notificationListAdapter;
    private ArrayList<NotificationResponseDTO> listNotification;
    private ArrayList<TaskResponseDTO> listTask;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_activity);

        //utilities = Utilities.getInstance(this);
        appSession = new AppSession(this);
        initView();

        if (Utilities.getInstance(this).isNetworkAvailable()) {
            Task getTaskList = new Task();
            getTaskList.getListofTask(NotificationActivity.this, "", "", appSession.getUserID(), "1", ApiConstants.PM_Assigned);
        } else {
            try {
                listTask = new ArrayList();
                JSONArray obj = new JSONArray(loadJSONFromAsset(GenericConstant.TASK_LIST_JSON));
                Gson gson = new Gson();
                listTask = gson.fromJson(obj.toString(), new TypeToken<ArrayList<TaskResponseDTO>>() {

                }.getType());
                notificationListAdapter = new NotificationListAdapter(this, listTask, onClickItem);
                rvList.setAdapter(notificationListAdapter);
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), NotificationActivity.class, "onCreate");
            }
        }
    }

    /**
     * Set the task list
     *
     * @param list
     */
    public void setTaskResult(ArrayList<TaskResponseDTO> list) {
        if (list != null && list.size() > 0) {
            listTask = new ArrayList();
            listTask = list;
            notificationListAdapter = new NotificationListAdapter(this, listTask, onClickItem);
            rvList.setAdapter(notificationListAdapter);
            //Log.e("ddghdfd", ">>>>>>> " + listTask[0].taskTypeName)
        } else {
            try {
                listTask = new ArrayList();
                JSONArray obj = new JSONArray(loadJSONFromAsset(GenericConstant.TASK_LIST_JSON));
                Gson gson = new Gson();
                listTask = gson.fromJson(obj.toString(), new TypeToken<ArrayList<TaskResponseDTO>>() {

                }.getType());
                notificationListAdapter = new NotificationListAdapter(this, listTask, onClickItem);
                rvList.setAdapter(notificationListAdapter);
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), NotificationActivity.class, "setTaskResult");
            }
        }
    }

    /**
     * load the json file from assets folder
     *
     * @param filename
     * @return
     */
    public String loadJSONFromAsset(String filename) {
        String json = null;
        try {
            InputStream is = getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, GenericConstant.UTF_8);
        } catch (IOException ex) {
            ExceptionLogger.Logger(ex.getCause(), ex.getMessage(), NotificationActivity.class, "loadJsonFromAsset");
            return null;
        }
        return json;
    }


    /**
     * Set the task details
     *
     * @param taskDetailResponse
     */
    public void setTaskDetailResult(TaskDetailResponseDTO taskDetailResponse) {
        if (taskDetailResponse != null) {
            appSession.setTaskDetail(null);
            appSession.setTaskDetail(taskDetailResponse);
            Intent intent = new Intent(NotificationActivity.this, TaskDetailActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Initialize the view
     */
    private void initView() {
        rvList = findViewById(R.id.rv_notification);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this);
        rvList.setLayoutManager(mLayoutManager);
        rvList.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * Item click listener
     */
    private OnItemClickListener.OnItemClickCallback onClickItem = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                if (Utilities.getInstance(NotificationActivity.this).isNetworkAvailable()) {
                    TaskDetail taskDetail = new TaskDetail();
                    taskDetail.getTaskDetail(NotificationActivity.this, listTask.get(position).getTaskID().toString(), position);
                } else {
                    if (position == 0) {
                        JSONObject obj = new JSONObject(loadJSONFromAsset(GenericConstant.TASK_11_JSON));
                        Gson gson = new Gson();
                        TaskDetailResponseDTO taskDetailResponse = gson.fromJson(obj.toString(), new TypeToken<TaskDetailResponseDTO>() {

                        }.getType());
                        setTaskDetailResult(taskDetailResponse);
                    } else if (position == 1) {
                        JSONObject obj = new JSONObject(loadJSONFromAsset(GenericConstant.TASK_9_JSON));
                        Gson gson = new Gson();
                        TaskDetailResponseDTO taskDetailResponse = gson.fromJson(obj.toString(), new TypeToken<TaskDetailResponseDTO>() {

                        }.getType());
                        setTaskDetailResult(taskDetailResponse);
                    } else if (position == 2) {
                        JSONObject obj = new JSONObject(loadJSONFromAsset(GenericConstant.TASK_8_JSON));
                        Gson gson = new Gson();
                        TaskDetailResponseDTO taskDetailResponse = gson.fromJson(obj.toString(), new TypeToken<TaskDetailResponseDTO>() {

                        }.getType());
                        setTaskDetailResult(taskDetailResponse);
                    } else if (position == 3) {
                        JSONObject obj = new JSONObject(loadJSONFromAsset(GenericConstant.TASK_4_JSON));
                        Gson gson = new Gson();
                        TaskDetailResponseDTO taskDetailResponse = gson.fromJson(obj.toString(), new TypeToken<TaskDetailResponseDTO>() {

                        }.getType());
                        setTaskDetailResult(taskDetailResponse);
                    } else if (position == 4) {
                        JSONObject obj = new JSONObject(loadJSONFromAsset(GenericConstant.TASK_3_JSON));
                        Gson gson = new Gson();
                        TaskDetailResponseDTO taskDetailResponse = gson.fromJson(obj.toString(), new TypeToken<TaskDetailResponseDTO>() {

                        }.getType());
                        setTaskDetailResult(taskDetailResponse);
                    }
                }

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), NotificationActivity.class, "onClickItem");
            }
        }
    };
}
