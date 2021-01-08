package hcl.policing.digitalpolicingplatform.receivers;

import android.os.Bundle;
import android.os.Handler;
import android.os.ResultReceiver;

import hcl.policing.digitalpolicingplatform.services.DownloadService;

public class DownloadReceiver extends ResultReceiver {

    public DownloadReceiver(Handler handler) {
        super(handler);
    }

    @Override
    protected void onReceiveResult(int resultCode, Bundle resultData) {

        super.onReceiveResult(resultCode, resultData);

        if (resultCode == DownloadService.UPDATE_PROGRESS) {

            int progress = resultData.getInt("progress"); //get the progress
            //dialog.setProgress(progress);

            if (progress == 100) {
                //dialog.dismiss();
            }
        }
    }
}