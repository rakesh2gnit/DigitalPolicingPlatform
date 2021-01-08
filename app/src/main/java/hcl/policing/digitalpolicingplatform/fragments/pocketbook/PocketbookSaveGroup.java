package hcl.policing.digitalpolicingplatform.fragments.pocketbook;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.adapters.pocketbook.LogEntriesAdapter;
import hcl.policing.digitalpolicingplatform.adapters.pocketbook.SaveGroupAdapter;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.MovableFloatingActionButton;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.GroupsLogDTO;
import hcl.policing.digitalpolicingplatform.models.process.ProcessLogDTO;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class PocketbookSaveGroup extends Fragment implements View.OnClickListener {

    private Context mContext;
    private AppSession appSession;
    private RecyclerView rvList;
    private ArrayList<ProcessLogDTO> entriesList;
    private SaveGroupAdapter saveGroupAdapter;
    TextInputEditText etTitle, etStormId, etAthenaId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pocketbook_save_group, container, false);

        mContext = getActivity();
        appSession = new AppSession(Objects.requireNonNull(mContext));
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        entriesList = (ArrayList<ProcessLogDTO>) Objects.requireNonNull(getArguments()).getSerializable(GenericConstant.ANSWER_LIST);
    }

    /**
     * Initialize the view
     */
    private void initView(View view) {
        etTitle = view.findViewById(R.id.et_title);
        etStormId = view.findViewById(R.id.et_storm_id);
        etAthenaId = view.findViewById(R.id.et_athena_id);

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
            pocketbookEntries();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), PocketbookSaveGroup.class, "onResume");
        }
    }

    private void pocketbookEntries () {
        saveGroupAdapter = new SaveGroupAdapter(mContext, entriesList, onClickItem, onClickDelete);
        rvList.setAdapter(saveGroupAdapter);
    }

    /**
     * Item Click lisnter
     */
    private OnItemClickListener.OnItemClickCallback onClickItem = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                ProcessCreationActivity.SECTION_COUNT = 0;
                ProcessCreationActivity.QUESTION_COUNT = 0;

                if(entriesList.get(position).getFileType().equalsIgnoreCase(GenericConstant.DRAFT_FILE)) {

                    String processName = entriesList.get(position).getProcessName();
                    String fileName = entriesList.get(position).getFileName().replace(".txt", "");
                    Intent intent1 = new Intent(mContext, ProcessCreationActivity.class);
                    intent1.putExtra(GenericConstant.FILE_NAME_DRAFT, fileName);

                    if (processName.equalsIgnoreCase(getResources().getString(R.string.tor_creation))) {

                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);

                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.TOR_PROCESS_JSON);

                    } else if (processName.equalsIgnoreCase(getResources().getString(R.string.stop_process))) {

                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.STOPNSEARCH_PROCESS);

                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.STOPNSEARCH_JSON);

                    } else if (processName.equalsIgnoreCase(getResources().getString(R.string.crime))) {

                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);

                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.CRIME_PROCESS_JSON);

                    } else if (processName.equalsIgnoreCase(getResources().getString(R.string.domestic_abuse))) {

                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);

                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.DOMESTIC_PROCESS_JSON);

                    } else if (processName.equalsIgnoreCase(getResources().getString(R.string.witness_statement))) {

                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);

                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.WITNESS_STATEMENT_JSON);

                    } else if (processName.equalsIgnoreCase(getResources().getString(R.string.incident))) {

                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);

                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.INCIDENT_JSON);

                    } else if (processName.equalsIgnoreCase(getResources().getString(R.string.intelligence))) {

                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);

                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.INTELLIGENCE_JSON);

                    } else if (processName.equalsIgnoreCase(getResources().getString(R.string.pocket_book))) {

                        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.PROCESS_NAME, GenericConstant.TOR_PROCESS);

                        intent1.putExtra(GenericConstant.JSON_FILE_NAME, GenericConstant.POCKET_BOOK_JSON);
                    }
                    intent1.putExtra(GenericConstant.PROCESS_NAME, processName);
                    intent1.putExtra(GenericConstant.JSON_OBJECT, "");
                    startActivity(intent1);
                    getActivity().overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);

                } else if(entriesList.get(position).getFileType().equalsIgnoreCase(GenericConstant.OFFLINE_FILE)) {

                    String dir = Environment.getExternalStorageDirectory() + GenericConstant.FILE_DIRECTORY
                            + new AppSession(mContext).getUserID()
                            + entriesList.get(position).getFilePath()
                            + entriesList.get(position).getFileName();

                    Uri path = FileProvider.getUriForFile(mContext, getActivity().getPackageName() + ".provider", new File(dir));
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                    intent.setDataAndType(path, "*/*");
                    startActivity(intent);
                }

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), PocketbookSaveGroup.class, "onClickItem");
            }
        }
    };

    /**
     * Item Click lisnter for delete
     */
    private OnItemClickListener.OnItemClickCallback onClickDelete = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                entriesList.remove(position);
                saveGroupAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), PocketbookSaveGroup.class, "onClickItem");
            }
        }
    };

    /**
     * Get the date from file
     *
     * @param fileN
     * @return
     */
    private String getDate(String fileN) {

        String date = "";

        String fileName = fileN.replace(".txt", "");

        String[] breakFileName = fileName.split("_");

        String timeinMillies = breakFileName[breakFileName.length - 1];

        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.US);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(timeinMillies));
        date = formatter.format(calendar.getTime());

        return date;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_add:
                ArrayList<GroupsLogDTO> groupList = new ArrayList<>();
                if(appSession.getLogGroupsList() != null && appSession.getLogGroupsList().size() > 0) {
                    groupList.addAll(appSession.getLogGroupsList());
                }
                GroupsLogDTO groupsLogDTO = new GroupsLogDTO();
                groupsLogDTO.setTitle(etTitle.getText().toString());
                groupsLogDTO.setStormId(etStormId.getText().toString());
                groupsLogDTO.setAthenaId(etAthenaId.getText().toString());
                groupsLogDTO.setLogsList(entriesList);
                groupList.add(groupsLogDTO);
                appSession.setLogGroupsList(null);
                appSession.setLogGroupsList(groupList);
                getActivity().finish();
                break;
        }
    }
}
