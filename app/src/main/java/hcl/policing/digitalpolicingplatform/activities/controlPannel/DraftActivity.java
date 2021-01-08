package hcl.policing.digitalpolicingplatform.activities.controlPannel;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.io.FileNotFoundException;
import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.adapters.controlPannel.DraftAdapter;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.controlPannel.DraftDTO;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class DraftActivity extends BaseActivity implements View.OnClickListener {

    private AppSession appSession;
    private RecyclerView rvList;
    private ArrayList<DraftDTO> list;
    private ArrayList<String> dirList;
    private String directoryDraft = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recycler_activity);
        initView();
        appSession = new AppSession(this);

        directoryDraft = GenericConstant.FILE_DRAFT /*+ getResources().getString(R.string.tor_creation) + "/"*/;
    }

    /**
     * Initialize the view
     */
    private void initView() {
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);
        TextView tvTitle = findViewById(R.id.tv_title);
        tvTitle.setText(getResources().getString(R.string.draft));

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

    @Override
    protected void onResume() {
        super.onResume();
        try {
            dirList = new ArrayList<>();
            dirList = Utilities.getInstance(this).getDirectoryList(directoryDraft);

            int count = 0;
            if (dirList != null && dirList.size() > 0) {
                list = new ArrayList<>();
                for (int i = 0; i < dirList.size(); i++) {
                    count = Utilities.getInstance(this).getNumberOfFiles(directoryDraft + dirList.get(i) + "/");

                    DraftDTO draftDTO = new DraftDTO();
                    draftDTO.setName(dirList.get(i));
                    draftDTO.setCount(count);
                    list.add(draftDTO);
                }
                DraftAdapter draftAdapter = new DraftAdapter(this, list, onClickItem);
                rvList.setAdapter(draftAdapter);
            } else {
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            }
        } catch (FileNotFoundException e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DraftActivity.class, "onCreate");
        }
    }

    /**
     * Item Click listener
     */
    private OnItemClickListener.OnItemClickCallback onClickItem = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                Intent intent = new Intent(DraftActivity.this, DraftFileActivity.class);
                intent.putExtra(GenericConstant.DIRECTORY_NAME, list.get(position).getName());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), DraftActivity.class, "onClickItem");
            }
        }
    };
}
