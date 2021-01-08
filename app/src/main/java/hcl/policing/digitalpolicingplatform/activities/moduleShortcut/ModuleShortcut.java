package hcl.policing.digitalpolicingplatform.activities.moduleShortcut;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.adapters.controlPannel.ModuleShortcutAdapter;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.database.DatabaseHelper;
import hcl.policing.digitalpolicingplatform.database.ProcessSubProcessMapper;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.controlPannel.ProcessIconsDTO;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class ModuleShortcut extends BaseActivity implements View.OnClickListener {

    private AppSession appSession;
    private RecyclerView rvIcons;
    private ModuleShortcutAdapter moduleShortcutAdapter;
    private ArrayList<ProcessSubProcessMapper> aProcessSubProcessMapper;
    private DatabaseHelper db;
    private Context mContext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        initDatabaseHelper();
        setContentView(R.layout.module_shortcut_activity);
        appSession = new AppSession(this);
        initView();
        setRecyclerview();
    }

    /**
     * Initialize the view
     */
    private void initView() {
        ImageView ivBack = findViewById(R.id.iv_back);
        ivBack.setOnClickListener(this);

        rvIcons = findViewById(R.id.rv_icons);
        //LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        rvIcons.setLayoutManager(new GridLayoutManager(this, 2));
        rvIcons.setItemAnimator(new DefaultItemAnimator());

        TextView tvDone = findViewById(R.id.tv_done);
        tvDone.setOnClickListener(this);
    }

    /**
     * initiliase the database
     */
    private void initDatabaseHelper() {
        if (db == null) {
            db = new DatabaseHelper(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_back:
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                break;

            case R.id.tv_done:
                appSession.setShortcutIcons(null);
                ArrayList<ProcessSubProcessMapper> tempList = new ArrayList<>();
                if (aProcessSubProcessMapper != null && aProcessSubProcessMapper.size() > 0) {
                    for (int i = 0; i < aProcessSubProcessMapper.size(); i++) {
                        if (aProcessSubProcessMapper.get(i).getShortcutStatus() == GenericConstant.SHORTCUT_YES) {
                            tempList.add(aProcessSubProcessMapper.get(i));
                        }
                    }
                    db.resetShortcutStatus(aProcessSubProcessMapper);
                    if (tempList != null && tempList.size() == 4) {
//                        appSession.setShortcutIcons(tempList);
                        db.updateShortcutStatus(tempList);
                        finish();
                        overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_left);
                    } else {
                        Toast.makeText(this, getResources().getString(R.string.four_module_required), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }


    /**
     * Set the recyclerview
     */
    private void setRecyclerview() {

        aProcessSubProcessMapper = db.getProcessList();
        if (aProcessSubProcessMapper != null && aProcessSubProcessMapper.size() > 0) {
            moduleShortcutAdapter = new ModuleShortcutAdapter(this, aProcessSubProcessMapper, onClickIcon);
            rvIcons.setAdapter(moduleShortcutAdapter);
        }

       /* listIcons = new ArrayList<>();
        listIcons.addAll(IconsDataService.getProcessIcon());

        if (listIcons != null && listIcons.size() > 0) {
            for (int i = 0; i < listIcons.size(); i++) {
                listIcons.get(i).setStatus("0");
            }
            if (appSession.getShortcutIcons() != null && appSession.getShortcutIcons().size() > 0) {
                for (int i = 0; i < listIcons.size(); i++) {
                    for (int j = 0; j < appSession.getShortcutIcons().size(); j++) {
                        if (listIcons.get(i).getId().equalsIgnoreCase(appSession.getShortcutIcons().get(j).getId())) {
                            listIcons.get(i).setStatus("1");
                        }
                    }
                }
            }
            moduleShortcutAdapter = new ModuleShortcutAdapter(this, listIcons, onClickIcon);
            rvIcons.setAdapter(moduleShortcutAdapter);
        }*/
    }

    /**
     * Icon click listener
     */
    private OnItemClickListener.OnItemClickCallback onClickIcon = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                int count = 0;
                for (int i = 0; i < aProcessSubProcessMapper.size(); i++) {
                    if (aProcessSubProcessMapper.get(i).getShortcutStatus() == GenericConstant.SHORTCUT_YES) {
                        count = count + 1;
                    }
                }
                for (int i = 0; i < aProcessSubProcessMapper.size(); i++) {
                    if (position == i) {
                        if (aProcessSubProcessMapper.get(position).getShortcutStatus() == GenericConstant.SHORTCUT_NO) {
                            if (count < 4)
                                aProcessSubProcessMapper.get(position).setShortcutStatus(GenericConstant.SHORTCUT_YES);
                        } else {
                            if (count <= 4)
                                aProcessSubProcessMapper.get(position).setShortcutStatus(GenericConstant.SHORTCUT_NO);
                        }
                    }
                }
                moduleShortcutAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ModuleShortcut.class, "onClickItem");
            }
        }
    };
}
