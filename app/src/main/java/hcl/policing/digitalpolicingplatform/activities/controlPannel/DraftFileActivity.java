package hcl.policing.digitalpolicingplatform.activities.controlPannel;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.adapters.controlPannel.DraftFileAdapter;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;
import hcl.policing.digitalpolicingplatform.database.ProcessSubProcessMapper;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.controlPannel.DraftDTO;
import hcl.policing.digitalpolicingplatform.models.process.ResponseModel;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class DraftFileActivity extends BaseActivity implements View.OnClickListener {

    private AppSession appSession;
    private TextView tvTitle;
    private RecyclerView rvList;
    private ArrayList<DraftDTO> list;
    private ArrayList<String> fileList;
    private DraftFileAdapter draftAdapter;
    private String processName;
    private String directoryDraft = "";
    private DatabaseHelper db;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity);
        initView();
        appSession = new AppSession(this);
        if (db == null) {
            db = new DatabaseHelper(this);
        }
        list = new ArrayList<>();

        processName = getIntent().getStringExtra(GenericConstant.DIRECTORY_NAME);
        tvTitle.setText(processName);
        directoryDraft = GenericConstant.FILE_DRAFT + processName + "/";

        try {
            fileList = new ArrayList<>();
            fileList = Utilities.getInstance(this).getFilesList(directoryDraft);

            if (fileList != null && fileList.size() > 0) {
                for (int i = 0; i < fileList.size(); i++) {
                    DraftDTO draftDTO = new DraftDTO();
                    draftDTO.setName(fileList.get(i));
                    draftDTO.setCount(0);
                    draftDTO.setTime(getDate(fileList.get(i)));
                    list.add(draftDTO);
                }
                draftAdapter = new DraftFileAdapter(this, list, onClickItem, onClickDelete);
                rvList.setAdapter(draftAdapter);
            }

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DraftFileActivity.class, "onCreate");
        }
    }

    /**
     * Initialize the view
     */
    private void initView() {
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        tvTitle = findViewById(R.id.tv_title);

        rvList = findViewById(R.id.rv_list);
        LinearLayoutManager mLayoutManagerTask = new LinearLayoutManager(this);
        rvList.setLayoutManager(mLayoutManagerTask);
        rvList.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;
        }
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
                String fileName = list.get(position).getName().replace(".txt", "");
                int processId = db.getProcessId(processName);
                ArrayList<ProcessSubProcessMapper> processSubProcessMappers = db.getSubProcessList(processName, processId);
                int subProcessId = processSubProcessMappers.get(0).getSubProcessId();
                String flowStr = isProcessFlow(subProcessId);

                if (!TextUtils.isEmpty(flowStr)) {
                    loadFlowDraft(processName, flowStr, fileName);
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), DraftFileActivity.class, "onClickItem");
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
                String fileName = list.get(position).getName().replace(".txt", "");
                Utilities.getInstance(DraftFileActivity.this).deleteProcessFile(fileName, directoryDraft);
                list.remove(position);
                draftAdapter.notifyDataSetChanged();

                if (list == null || list.size() == 0) {
                    finish();
                    overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                }
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), DraftFileActivity.class, "onClickItem");
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
}
