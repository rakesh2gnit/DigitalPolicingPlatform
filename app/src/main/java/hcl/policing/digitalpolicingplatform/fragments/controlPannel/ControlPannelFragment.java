package hcl.policing.digitalpolicingplatform.fragments.controlPannel;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.controlPannel.ControlPanelActivity;
import hcl.policing.digitalpolicingplatform.adapters.controlPannel.ControlPanelAdapter;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;
import hcl.policing.digitalpolicingplatform.database.ProcessSubProcessMapper;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.SubProcessDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class ControlPannelFragment extends Fragment implements View.OnTouchListener {

    Context context;
    private AppSession appSession;
    private RecyclerView rvIcons;
    private ArrayList<ProcessSubProcessMapper> aProcessSubProcessMapper;
    private ControlPanelAdapter controlPanelAdapter;
    private DatabaseHelper db;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.controlpannel_fragment, container, false);
        context = getActivity();
        initToolbar();
        initView(view);

        return view;
    }

    /**
     * initiliase the database
     */
    private void initDatabaseHelper() {
        if (db == null) {
            db = new DatabaseHelper(getActivity());
        }
    }

    private void initToolbar() {
        androidx.appcompat.app.ActionBar actionBar = ((ControlPanelActivity) context).getSupportActionBar();
        ((ControlPanelActivity) Objects.requireNonNull(getActivity())).createMenuButton(getResources().getString(R.string.control_pannel));
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initDatabaseHelper();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setRecyclerview();
    }

    private void initView(View view) {
        rvIcons = view.findViewById(R.id.rv_icons);
        rvIcons.setLayoutManager(new GridLayoutManager(context, 3));
        rvIcons.setItemAnimator(new DefaultItemAnimator());
    }

    private void setRecyclerview() {
        aProcessSubProcessMapper = db.getProcessList();
        if (aProcessSubProcessMapper != null && aProcessSubProcessMapper.size() > 0) {
            controlPanelAdapter = new ControlPanelAdapter(context, aProcessSubProcessMapper, onClickIcon);
            rvIcons.setAdapter(controlPanelAdapter);
        }
    }

    private OnItemClickListener.OnItemClickCallback onClickIcon = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                int processId = aProcessSubProcessMapper.get(position).getProcessId();
                String processName = aProcessSubProcessMapper.get(position).getProcessName();
                loadSubProcessDialog(processId, processName);

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ControlPannelFragment.class, "onClickItem");
            }
        }
    };

    /**
     * Load Sub Process Dialog
     */
    private void loadSubProcessDialog(int processId, String processName) {

        try {
            ArrayList<ProcessSubProcessMapper> processSubProcessMappers = db.getSubProcessList(processName, processId);
            if (processSubProcessMappers != null) {
                if (processSubProcessMappers.size() > 1) {

                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                    Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.SUB_PROCESS_LIST_DIALOG);
                    if (prev != null) {
                        ft.remove(prev);
                    }
                    ft.addToBackStack(null);
                    // Create and show the dialog.
                    SubProcessDialogFragment subProcessDialogFragment = SubProcessDialogFragment.newInstance(processSubProcessMappers);
                    subProcessDialogFragment.show(ft, GenericConstant.SUB_PROCESS_LIST_DIALOG);

                } else if (processSubProcessMappers.size() == 1) {
                    int subProcessId = processSubProcessMappers.get(0).getSubProcessId();
                    String flowStr = ((BaseActivity) getActivity()).isProcessFlow(subProcessId);
                    if (!TextUtils.isEmpty(flowStr)) {
                        ((BaseActivity) getActivity()).loadFlow(processName, flowStr);
                    } else {
                        ((BaseActivity) getActivity()).getProcessFlowApi(processId, subProcessId);
                    }
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), ControlPannelFragment.class, "loadSubProcessDialog");
        }
    }


    /**
     * Load Sub Process Dialog
     */
/*    public void loadSubProcessDialog(int processId, String processName) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    ArrayList<ProcessSubProcessMapper> processSubProcessMappers = db.getSubProcessList(processId);

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            if (processSubProcessMappers != null) {
                                if (processSubProcessMappers.size() > 1) {

                                    FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
                                    Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.SUB_PROCESS_LIST_DIALOG);
                                    if (prev != null) {
                                        ft.remove(prev);
                                    }
                                    ft.addToBackStack(null);

                                    // Create and show the dialog.
                                    SubProcessDialogFragment subProcessDialogFragment = SubProcessDialogFragment.newInstance(processSubProcessMappers);
                                    subProcessDialogFragment.show(ft, GenericConstant.SUB_PROCESS_LIST_DIALOG);

                                } else if (processSubProcessMappers.size() == 1) {

                                    if (processName.equalsIgnoreCase("Search")) {
                                        new AppSession(getActivity()).setImageList(null);
                                        SharedPrefUtils.getInstance(getActivity()).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.FDS_PROCESS);
                                        Intent intent = new Intent(context, FDSSearchActivity.class);
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.TOR_PROCESS_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.fds_search));
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("TOR")) {
                                        new AppSession(getActivity()).setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(getActivity()).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent = new Intent(context, ProcessCreationActivity.class);
                                        intent.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.TOR_PROCESS_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.tor_creation));
                                        intent.putExtra(GenericConstant.JSON_OBJECT, "");
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Stops & Search")) {

                                        new AppSession(getActivity()).setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(getActivity()).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.STOPNSEARCH_PROCESS);
                                        Intent intent1 = new Intent(context, ProcessCreationActivity.class);
                                        intent1.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.STOPNSEARCH_JSON);
                                        intent1.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.stop_process));
                                        intent1.putExtra(GenericConstant.JSON_OBJECT, "");
                                        startActivity(intent1);
                                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                                    } else if (processName.equalsIgnoreCase("Crime")) {

                                        new AppSession(getActivity()).setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(getActivity()).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent1 = new Intent(context, ProcessCreationActivity.class);
                                        intent1.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.CRIME_PROCESS_JSON);
                                        intent1.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.crime));
                                        intent1.putExtra(GenericConstant.JSON_OBJECT, "");
                                        startActivity(intent1);
                                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Domestic Abuse")) {

                                        new AppSession(getActivity()).setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(getActivity()).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent1 = new Intent(context, ProcessCreationActivity.class);
                                        intent1.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent1.putExtra(GenericConstant.JSON_OBJECT, "");
                                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.DOMESTIC_PROCESS_JSON);
                                        intent1.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.domestic_abuse));
                                        startActivity(intent1);
                                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Witness Statement")) {
                                        new AppSession(getActivity()).setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(getActivity()).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent = new Intent(context, ProcessCreationActivity.class);
                                        intent.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.WITNESS_STATEMENT_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.witness_statement));
                                        intent.putExtra(GenericConstant.JSON_OBJECT, "");
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Pocket Book")) {
                                        new AppSession(getActivity()).setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(getActivity()).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent = new Intent(context, ProcessCreationActivity.class);
                                        intent.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.POCKET_BOOK_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.pocket_book));
                                        intent.putExtra(GenericConstant.JSON_OBJECT, "");
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Incident")) {
                                        new AppSession(getActivity()).setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(getActivity()).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent = new Intent(context, ProcessCreationActivity.class);
                                        intent.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.INCIDENT_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.incident));
                                        intent.putExtra(GenericConstant.JSON_OBJECT, "");
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Intelligence")) {
                                        new AppSession(getActivity()).setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(getActivity()).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent = new Intent(context, ProcessCreationActivity.class);
                                        intent.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.INTELLIGENCE_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.intelligence));
                                        intent.putExtra(GenericConstant.JSON_OBJECT, "");
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    } else if (processName.equalsIgnoreCase("Problem Solving")) {
                                        new AppSession(getActivity()).setImageList(null);
                                        ProcessCreationActivity.SECTION_COUNT = 0;
                                        ProcessCreationActivity.QUESTION_COUNT = 0;
                                        SharedPrefUtils.getInstance(getActivity()).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);
                                        Intent intent = new Intent(context, ProcessCreationActivity.class);
                                        intent.putExtra(GenericConstant.FILE_NAME_DRAFT, "");
                                        intent.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.PROBLEM_SOLVING_JSON);
                                        intent.putExtra(GenericConstant.PROCESS_NAME, getResources().getString(R.string.problem_solving));
                                        intent.putExtra(GenericConstant.JSON_OBJECT, "");
                                        startActivity(intent);
                                        getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                                    }
                                }
                            }
                        }
                    });
                } catch (Exception e) {
                    ExceptionLogger.Logger(e.getCause(), e.getMessage(), ControlPannelFragment.class, "loadSubProcessDialog");
                }
            }
        }).start();
    }*/
}
