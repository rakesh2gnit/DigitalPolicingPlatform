package hcl.policing.digitalpolicingplatform.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.offline.BackupDataUtils;

public class OfflineDataAdaptor extends RecyclerView.Adapter<OfflineDataAdaptor.MyViewHolder> {

    private ArrayList<BackupDataUtils> aBackupDataUtils;
    private int type;
    private TextView tvNoServiceExist;
    private Context mContext;

    public OfflineDataAdaptor(Context context, ArrayList<BackupDataUtils> datalist) {
        this.mContext = context;
        this.aBackupDataUtils = datalist;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.offline_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        BackupDataUtils backupDataUtils = aBackupDataUtils.get(position);
        holder.tvProcessName.setText(backupDataUtils.getProcess());
        holder.tvFunctionName.setText(backupDataUtils.getFunction());
        holder.tvStatus.setText("Pending");
    }

    @Override
    public int getItemCount() {
        return aBackupDataUtils.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        protected TextView tvProcessName, tvFunctionName, tvStatus, tvDate;

        public MyViewHolder(View view) {
            super(view);
            tvProcessName = view.findViewById(R.id.tvProcessName);
            tvFunctionName = view.findViewById(R.id.tvFunctionName);
            tvStatus = view.findViewById(R.id.tvStatus);
            tvDate = view.findViewById(R.id.tvDate);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

            }

        }
    }
}
