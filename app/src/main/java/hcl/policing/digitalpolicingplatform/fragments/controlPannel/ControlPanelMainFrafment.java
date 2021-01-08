package hcl.policing.digitalpolicingplatform.fragments.controlPannel;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.controlPannel.ControlPanelActivity;
import hcl.policing.digitalpolicingplatform.activities.controlPannel.DraftActivity;
import hcl.policing.digitalpolicingplatform.activities.controlPannel.SurroundingCrimeActivity;
import hcl.policing.digitalpolicingplatform.activities.controlPannel.SurroundingIncidentActivity;
import hcl.policing.digitalpolicingplatform.activities.controlPannel.SurroundingTaskActivity;
import hcl.policing.digitalpolicingplatform.activities.tasking.TaskDetailActivity;
import hcl.policing.digitalpolicingplatform.adapters.controlPannel.BriefingAdapter;
import hcl.policing.digitalpolicingplatform.adapters.controlPannel.MySurroundingAdapter;
import hcl.policing.digitalpolicingplatform.adapters.controlPannel.PendingActionAdapter;
import hcl.policing.digitalpolicingplatform.constants.api.ApiConstants;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.contolPannel.LocationDataService;
import hcl.policing.digitalpolicingplatform.constants.contolPannel.MediaDataService;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.controlPannel.TaskResponseDTO;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskDetailResponseDTO;
import hcl.policing.digitalpolicingplatform.networks.controlPannel.Task;
import hcl.policing.digitalpolicingplatform.networks.tasking.TaskDetail;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class ControlPanelMainFrafment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private AppSession appSession;
    private Context context;
    private RecyclerView rvSurrounding, rvTask, rvBriefing;
    private MySurroundingAdapter mySurroundingAdapter;

    private BriefingAdapter briefingAdapter;

    private ArrayList<TaskResponseDTO> listPendingAction;
    private PendingActionAdapter pendingActionAdapter;
    private SwipeRefreshLayout swipeRefreshLayout;

    private CardView cvDraft;
    private TextView tvCountDraft;

    private ArrayList<String> dirList;
    private String directoryDraft = "";


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.controlpanel_main_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        appSession = new AppSession(Objects.requireNonNull(context));
        initToolbar();
        initView(view);

        setSurroundingList();
        setBriefingList();
        callPendingActionTask();

        directoryDraft = GenericConstant.FILE_DRAFT /*+ getResources().getString(R.string.tor_creation) + "/"*/;
        //getDraft();
    }

    private void initToolbar() {
        androidx.appcompat.app.ActionBar actionBar = ((ControlPanelActivity) context).getSupportActionBar();
        ((ControlPanelActivity) Objects.requireNonNull(getActivity())).createMenuButton(getResources().getString(R.string.my_dashboard));
        /*ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(ContextCompat.getColor(context, R.color.white));
        Objects.requireNonNull(actionBar).setBackgroundDrawable(colorDrawable);*/
        //Objects.requireNonNull(actionBar).setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.bg_toolbar));
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setHomeAsUpIndicator(ContextCompat.getColor(getActivity(), R.drawable.arrow_back_white));
    }

    /**
     * Initialize the views
     *
     * @param view
     */
    private void initView(View view) {

        swipeRefreshLayout = view.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setOnRefreshListener(this);

        ImageView ivLocation = view.findViewById(R.id.iv_location);
        AnimatedVectorDrawable locAnimation = (AnimatedVectorDrawable) ivLocation.getDrawable();
        locAnimation.start();

        rvSurrounding = view.findViewById(R.id.rv_surrounding);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvSurrounding.setLayoutManager(mLayoutManager);
        rvSurrounding.setItemAnimator(new DefaultItemAnimator());

        rvTask = view.findViewById(R.id.rv_task);
        LinearLayoutManager mLayoutManagerTask = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvTask.setLayoutManager(mLayoutManagerTask);
        rvTask.setItemAnimator(new DefaultItemAnimator());

        rvBriefing = view.findViewById(R.id.rv_briefing);
        LinearLayoutManager mLayoutManagerMedia = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        rvBriefing.setLayoutManager(mLayoutManagerMedia);
        rvBriefing.setItemAnimator(new DefaultItemAnimator());

        cvDraft = view.findViewById(R.id.cv_draft);
        tvCountDraft = view.findViewById(R.id.tv_count_draft);
    }

    @Override
    public void onResume() {
        getDraft();
        super.onResume();
    }

    private void setSurroundingList() {
        mySurroundingAdapter = new MySurroundingAdapter(context, LocationDataService.getLocationData(), onClickItemSurrounding);
        rvSurrounding.setAdapter(mySurroundingAdapter);
    }

    private OnItemClickListener.OnItemClickCallback onClickItemSurrounding = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                staticSurroundingData(position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    private void callPendingActionTask() {
        try {
            if (Utilities.getInstance(context).isNetworkAvailable()) {
                Task task = new Task();
                task.getListofTask(this, "", "", appSession.getUserID(), "1", ApiConstants.PM_Assigned);
            } else {
                listPendingAction = new ArrayList();
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(context, GenericConstant.TASK_LIST_JSON);
                Gson gson = new Gson();
                listPendingAction = gson.fromJson(strJson, new TypeToken<ArrayList<TaskResponseDTO>>() {

                }.getType());

                setPendingActionList(listPendingAction);
            }
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public void setPendingActionList(ArrayList<TaskResponseDTO> listTask) {
        try {
            if (listTask != null && listTask.size() > 0) {
                Log.e("called", "dynamic >>>>>>> ");
                listPendingAction = new ArrayList();
                listPendingAction.addAll(listTask);

                pendingActionAdapter = new PendingActionAdapter(context, listPendingAction, onClickItemPending);
                rvTask.setAdapter(pendingActionAdapter);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private OnItemClickListener.OnItemClickCallback onClickItemPending = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                Log.e("ddghdfd", ">>>>>>> " + listPendingAction.get(position).getTaskTypeName());
                TaskDetail taskDetail = new TaskDetail();
                taskDetail.getTaskDetail(ControlPanelMainFrafment.this, listPendingAction.get(position).getTaskID().toString(), position);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    public void setTaskDetailResult(final TaskDetailResponseDTO model) {
        if (model != null) {
            appSession.setTaskDetail(null);
            appSession.setTaskDetail(model);

            Intent intent = new Intent(context, TaskDetailActivity.class);
            startActivity(intent);
        }
    }

    private void setBriefingList() {
        briefingAdapter = new BriefingAdapter(context, MediaDataService.getMediaData(), onClickItemBriefing);
        rvBriefing.setAdapter(briefingAdapter);
    }

    private OnItemClickListener.OnItemClickCallback onClickItemBriefing = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ControlPanelMainFrafment.class, "onClickItemBriefing");
            }
        }
    };

    private void staticSurroundingData(int index) {
        if(index == 0){
            Intent intent = new Intent(context, SurroundingTaskActivity.class);
            startActivity(intent);
        } else if (index == 1) {
            Intent intent = new Intent(context, SurroundingCrimeActivity.class);
            startActivity(intent);
        } else if (index == 2) {
            Intent intent = new Intent(context, SurroundingIncidentActivity.class);
            startActivity(intent);
        } else if (index == 3) {
            Intent intent = new Intent(context, SurroundingTaskActivity.class);
            startActivity(intent);
        } else if (index == 4) {
            Intent intent = new Intent(context, SurroundingIncidentActivity.class);
            startActivity(intent);
        }
    }

    private void getDraft() {
        try {
            dirList = new ArrayList<>();
            dirList = Utilities.getInstance(context).getDirectoryList(directoryDraft);

            int count = 0;

            if(dirList != null && dirList.size() > 0) {
                for (int i = 0; i < dirList.size(); i++) {
                    count = count + Utilities.getInstance(context).getNumberOfFiles(directoryDraft + dirList.get(i)+ "/");
                }
            }
            if(count > 0) {
                cvDraft.setVisibility(View.VISIBLE);
                tvCountDraft.setText(""+count);
                cvDraft.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, DraftActivity.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    }
                });
            } else {
                cvDraft.setVisibility(View.GONE);
            }
        } catch (FileNotFoundException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ControlPanelMainFrafment.class, "getDraft");
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*TaskDetailActivity myActivity = (TaskDetailActivity) getActivity();
        myActivity.speakCancel();*/
        Log.e("Additional ", "DESTROYED ");
    }

    @Override
    public void onRefresh() {
        getDraft();
        swipeRefreshLayout.setRefreshing(false);
        //your code on swipe refresh
        //we are checking networking connectivity
    }
}
