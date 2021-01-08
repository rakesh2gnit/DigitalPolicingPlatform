package hcl.policing.digitalpolicingplatform.adapters.process;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.database.ProcessSubProcessMapper;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;

public class SubProcessListAdaptor extends RecyclerView.Adapter<SubProcessListAdaptor.MyViewHolder> {

    private ArrayList<ProcessSubProcessMapper> aProcessSubProcessMapper;
    private int type;
    private OnItemClickListener.OnItemClickCallback onClickListener;
    private Context mContext;

    public SubProcessListAdaptor(Context context, ArrayList<ProcessSubProcessMapper> datalist, OnItemClickListener.OnItemClickCallback onClickItem) {
        this.mContext = context;
        this.aProcessSubProcessMapper = datalist;
        this.onClickListener = onClickItem;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sub_process_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        ProcessSubProcessMapper processSubProcessMapper = aProcessSubProcessMapper.get(position);
        holder.tvName.setText(processSubProcessMapper.getSubProcessName());
        String iconName = processSubProcessMapper.getSubProcessIcon() + "_blue";
        int drawableId = mContext.getResources().getIdentifier(iconName, "drawable", mContext.getPackageName());
        holder.ivIcon.setImageResource(drawableId);
        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickListener));
    }

    @Override
    public int getItemCount() {
        return aProcessSubProcessMapper.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView ivIcon;

        private MyViewHolder(View view) {
            super(view);
            ivIcon = view.findViewById(R.id.ivIcon);
            tvName = view.findViewById(R.id.tvName);
        }
    }
}
