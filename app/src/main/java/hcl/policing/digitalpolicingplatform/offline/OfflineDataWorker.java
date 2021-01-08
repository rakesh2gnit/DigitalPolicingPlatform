package hcl.policing.digitalpolicingplatform.offline;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class OfflineDataWorker extends Worker {

    private static final String TAG = OfflineDataWorker.class.getSimpleName();
    private static final String WORK_RESULT = "work_result";
    public OfflineDataWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
    }

    @NonNull
    @Override
    public Result doWork() {

        Context applicationContext = getApplicationContext();
        Data inpuData=getInputData();

        WorkerUtils.makeStatusNotification("Work Manager is running",applicationContext);

        Data outData=new Data.Builder().putString(WORK_RESULT,"Job Finish").build();


        return Result.success(outData);
    }
}
