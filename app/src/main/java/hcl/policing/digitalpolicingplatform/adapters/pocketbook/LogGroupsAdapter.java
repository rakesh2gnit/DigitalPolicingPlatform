package hcl.policing.digitalpolicingplatform.adapters.pocketbook;

import android.annotation.SuppressLint;
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
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.GroupsLogDTO;

public class LogGroupsAdapter extends RecyclerView.Adapter<LogGroupsAdapter.MyViewHolder> {

    private Context mContext;
    private OnItemClickListener.OnItemClickCallback onClickItem, onClickSelect;
    private ArrayList<GroupsLogDTO> list;

    /**
     * MyViewHolder class
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitle, tvStormId, tvAthenaId, tvEntriesCount, tvTime;
        ImageView ivSelect;

        MyViewHolder(View view) {
            super(view);
            tvTitle = view.findViewById(R.id.tv_title);
            tvStormId = view.findViewById(R.id.tv_storm_id);
            tvAthenaId = view.findViewById(R.id.tv_athena_id);
            tvEntriesCount = view.findViewById(R.id.tv_entries_count);
            tvTime = view.findViewById(R.id.tv_time);
            ivSelect = view.findViewById(R.id.iv_select);
        }
    }

    public LogGroupsAdapter(Context mContext, ArrayList<GroupsLogDTO> list,
                            OnItemClickListener.OnItemClickCallback onClickItem,
                            OnItemClickListener.OnItemClickCallback onClickSelect) {
        this.mContext = mContext;
        this.list = list;
        this.onClickItem = onClickItem;
        this.onClickSelect = onClickSelect;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_log_groups, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.tvTitle.setText(list.get(position).getTitle());
        holder.tvStormId.setText(""+list.get(position).getStormId());
        holder.tvAthenaId.setText(""+list.get(position).getAthenaId());
        holder.tvEntriesCount.setText(list.get(position).getLogsList().size() + " Entries");
        holder.tvTime.setText(list.get(position).getTime());

        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickItem));
        holder.ivSelect.setOnClickListener(new OnItemClickListener(position, onClickSelect));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
