package hcl.policing.digitalpolicingplatform.adapters.process.officer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.officer.OfficerResponse;
import hcl.policing.digitalpolicingplatform.models.process.officer.UserModel;

public class OfficerListAdaptor extends RecyclerView.Adapter<OfficerListAdaptor.MyViewHolder> {

    private List<UserModel> aUserModel;
    private int type;
    private TextView tvNoServiceExist;
    private OnItemClickListener.OnItemClickCallback officerSelectionListener;
    private Context mContext;

    public OfficerListAdaptor(Context context, List<UserModel> users, OnItemClickListener.OnItemClickCallback officerSelectionListener) {
        this.mContext = context;
        this.aUserModel = users;
        this.officerSelectionListener = officerSelectionListener;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.officer_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        UserModel mUserModel = aUserModel.get(position);
        String name = mUserModel.getForename()+" "+mUserModel.getSurname();

        holder.tvName.setText(name);
        holder.tvRank.setText(String.valueOf(mUserModel.getRank()));
        holder.tvOfficerNo.setText("" + mUserModel.getSupserviceno());
        holder.tvStation.setText(mUserModel.getStation());
        holder.itemView.setOnClickListener(new OnItemClickListener(position, officerSelectionListener));
    }

    @Override
    public int getItemCount() {
        return aUserModel.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName, tvRank, tvOfficerNo, tvStation;
        private ImageView ivOfficer;
        private LinearLayout llOfficer;

        private MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            tvRank = view.findViewById(R.id.tvRank);
            tvOfficerNo = view.findViewById(R.id.tv_officer_no);
            tvStation = view.findViewById(R.id.tv_station);
            llOfficer = view.findViewById(R.id.llOfficer);
        }
    }
}