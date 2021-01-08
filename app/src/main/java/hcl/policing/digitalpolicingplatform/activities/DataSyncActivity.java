package hcl.policing.digitalpolicingplatform.activities;

import android.content.Context;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.work.Constraints;
import androidx.work.NetworkType;
import androidx.work.OneTimeWorkRequest;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkInfo;
import androidx.work.WorkManager;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.adapters.OfflineDataAdaptor;
import hcl.policing.digitalpolicingplatform.offline.BackupDataUtils;
import hcl.policing.digitalpolicingplatform.offline.OfflineDataWorker;
import hcl.policing.digitalpolicingplatform.receivers.NetworkStateReceiver;

public class DataSyncActivity extends AppCompatActivity implements NetworkStateReceiver.ConnectivityReceiverListener {

    private Context mContext;
    private RecyclerView rvOfflineData;
    private TextView tvStatus;
    private ArrayList<BackupDataUtils> aBackupDataUtils;
    private OfflineDataAdaptor offlineDataAdaptor;
    private WorkManager manager = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        setContentView(R.layout.datasync_activity);
        initView();
    }

    /**
     * Initialize the views.
     */
    private void initView() {
        rvOfflineData = findViewById(R.id.rvOfflineData);
        tvStatus = findViewById(R.id.tvStatus);
        manager = WorkManager.getInstance(getApplication());
        Constraints constraints = new Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .setRequiresBatteryNotLow(true).build();

        final OneTimeWorkRequest oneTimeWorkRequest = new OneTimeWorkRequest
                .Builder(OfflineDataWorker.class).setConstraints(constraints).build();
        manager.enqueue(oneTimeWorkRequest);

        final PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(OfflineDataWorker.class, 5, TimeUnit.SECONDS)
                .setConstraints(constraints).build();

        manager.getWorkInfoByIdLiveData(periodicWorkRequest.getId()).observe(this, workInfo -> {
            if (workInfo != null) {
                WorkInfo.State state = workInfo.getState();
                tvStatus.append(state.toString());
            }
        });
    }


    /**
     * Set Recyclerview
     */
    private void setRecyclerview() {
        if (aBackupDataUtils != null && aBackupDataUtils.size() > 0) {
            offlineDataAdaptor = new OfflineDataAdaptor(mContext, aBackupDataUtils);
            rvOfflineData.setAdapter(offlineDataAdaptor);
        }
    }

    /**
     * Set the recycler property
     * @param rvDataOffline
     */
    private void setRecyclerViewProperty(RecyclerView rvDataOffline) {
        rvDataOffline.setHasFixedSize(true);
//        rvDataOffline.addItemDecoration(new SpacesItemDecoration(20));
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext);
        rvDataOffline.setLayoutManager(mLayoutManager);
//      rvUpgradeList.addItemDecoration(new SimpleDividerItemDecoration(getResources()));
    }


    /**
     * get the list of Offline data
     */
    private void getOfflineList() {
        aBackupDataUtils = new ArrayList<>();
        aBackupDataUtils.clear();
        for (int i = 0; i < 10; i++) {
            BackupDataUtils backupDataUtils = new BackupDataUtils(i, "PocketBook : " + i, "Pocket Book Create : " + i, "Not Available", "Not Available", 0);
            aBackupDataUtils.add(backupDataUtils);
        }
    }

    @Override
    public void onNetworkConnectionChanged(String isConnected) {
        Toast.makeText(mContext, "Network is On!!" + isConnected, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        manager.cancelAllWork();
    }
}
