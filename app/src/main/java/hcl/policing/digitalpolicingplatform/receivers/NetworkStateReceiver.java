package hcl.policing.digitalpolicingplatform.receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import hcl.policing.digitalpolicingplatform.utils.NetworkUtil;

public class NetworkStateReceiver extends BroadcastReceiver {

    public static ConnectivityReceiverListener connectivityReceiverListener;

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        String status = NetworkUtil.getInstance().getConnectivityStatusString(context);
        Toast.makeText(context, status, Toast.LENGTH_LONG).show();

        if (connectivityReceiverListener != null)
            connectivityReceiverListener.onNetworkConnectionChanged(status);
    }

    public interface ConnectivityReceiverListener {
        void onNetworkConnectionChanged(String isConnected);
    }
}
