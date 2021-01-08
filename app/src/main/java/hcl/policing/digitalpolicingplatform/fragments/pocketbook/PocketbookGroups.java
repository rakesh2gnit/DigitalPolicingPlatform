package hcl.policing.digitalpolicingplatform.fragments.pocketbook;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.pocketbook.PocketbookCreateGroup;
import hcl.policing.digitalpolicingplatform.adapters.pocketbook.LogGroupsAdapter;
import hcl.policing.digitalpolicingplatform.customLibraries.MovableFloatingActionButton;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.GroupsLogDTO;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class PocketbookGroups extends Fragment implements View.OnClickListener {

    private Context mContext;
    private AppSession appSession;
    private RecyclerView rvList;
    private ArrayList<GroupsLogDTO> entriesList;
    private LogGroupsAdapter logGroupsAdapter;
    private String processName;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pocketbook_groups, container, false);

        mContext = getActivity();
        appSession = new AppSession(Objects.requireNonNull(mContext));
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    /**
     * Initialize the view
     */
    private void initView(View view) {
        rvList = view.findViewById(R.id.rv_list);
        LinearLayoutManager mLayoutManagerTask = new LinearLayoutManager(mContext);
        rvList.setLayoutManager(mLayoutManagerTask);
        rvList.setItemAnimator(new DefaultItemAnimator());

        MovableFloatingActionButton btnCreate = view.findViewById(R.id.btn_add);
        btnCreate.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            logGroups();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), PocketbookGroups.class, "onResume");
        }
    }

    private void logGroups() {
        entriesList = new ArrayList<>();
        if(appSession.getLogGroupsList() != null && appSession.getLogGroupsList().size() > 0) {
            entriesList.addAll(appSession.getLogGroupsList());
        }
        logGroupsAdapter = new LogGroupsAdapter(mContext, entriesList, onClickItem, onClickSelect);
        rvList.setAdapter(logGroupsAdapter);
    }

    /**
     * Item Click lisnter
     */
    private OnItemClickListener.OnItemClickCallback onClickItem = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), PocketbookGroups.class, "onClickItem");
            }
        }
    };

    /**
     * Item Click lisnter for delete
     */
    private OnItemClickListener.OnItemClickCallback onClickSelect = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), PocketbookGroups.class, "onClickItem");
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                Intent intent1 = new Intent(mContext, PocketbookCreateGroup.class);
                startActivity(intent1);
                getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }
    }
}
