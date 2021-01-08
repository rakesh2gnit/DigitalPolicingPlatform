package hcl.policing.digitalpolicingplatform.adapters.pocketbook;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.OnItemLongClickListener;
import hcl.policing.digitalpolicingplatform.models.process.ProcessLogDTO;

public class SaveGroupAdapter extends RecyclerView.Adapter<SaveGroupAdapter.MyViewHolder> {

    private Context mContext;
    private OnItemClickListener.OnItemClickCallback onClickItem, onClickDelete;
    private ArrayList<ProcessLogDTO> list;

    /**
     * MyViewHolder class
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cvCard;
        TextView tvDate, tvType, tvName, tvCreatedBy, tvTime;
        ImageView ivType, ivPrivate, ivPin, ivDelete;

        MyViewHolder(View view) {
            super(view);
            cvCard = view.findViewById(R.id.cv_card);
            tvDate = view.findViewById(R.id.tv_date);
            tvType = view.findViewById(R.id.tv_type);
            tvName = view.findViewById(R.id.tv_name);
            tvCreatedBy = view.findViewById(R.id.tv_created_by);
            tvTime = view.findViewById(R.id.tv_time);
            ivDelete = view.findViewById(R.id.iv_delete);
            ivType = view.findViewById(R.id.iv_type);
            ivPin = view.findViewById(R.id.iv_pin);
            ivPrivate = view.findViewById(R.id.iv_private);
        }
    }

    public SaveGroupAdapter(Context mContext, ArrayList<ProcessLogDTO> list,
                            OnItemClickListener.OnItemClickCallback onClickItem,
                            OnItemClickListener.OnItemClickCallback onClickDelete) {
        this.mContext = mContext;
        this.list = list;
        this.onClickItem = onClickItem;
        this.onClickDelete = onClickDelete;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_save_group, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.tvName.setText(list.get(position).getDisplayText());
        holder.tvDate.setText(list.get(position).getDate());
        holder.tvTime.setText(list.get(position).getTime());

        if(position > 0) {
            if(list.get(position).getDate().equalsIgnoreCase(list.get(position-1).getDate())) {
                holder.tvDate.setVisibility(View.GONE);
            } else {
                holder.tvDate.setVisibility(View.VISIBLE);
            }
        }

        if(list.get(position).getFileType().equalsIgnoreCase(GenericConstant.DRAFT_FILE)) {
            holder.ivType.setVisibility(View.VISIBLE);
            holder.ivType.setImageResource(R.drawable.ic_edit);
        } else if (list.get(position).getFileType().equalsIgnoreCase(GenericConstant.OFFLINE_FILE)) {
            holder.ivType.setVisibility(View.VISIBLE);
            holder.ivType.setImageResource(R.drawable.ic_offline);
        } else if (list.get(position).getFileType().equalsIgnoreCase(GenericConstant.FDS_FILE)) {
            holder.ivType.setVisibility(View.INVISIBLE);
        }

        if(list.get(position).getProcessName().equalsIgnoreCase(mContext.getResources().getString(R.string.fds_search))) {
            holder.tvType.setText("FDS");
        } else if (list.get(position).getProcessName().equalsIgnoreCase(mContext.getResources().getString(R.string.crime))) {
            holder.tvType.setText("CR");
        } else if (list.get(position).getProcessName().equalsIgnoreCase(mContext.getResources().getString(R.string.pocket_book))) {
            holder.tvType.setText("PB");
        }

        if(list.get(position).getPinFlag().equalsIgnoreCase("1")) {
            holder.ivPin.setVisibility(View.VISIBLE);
        } else {
            holder.ivPin.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickItem));
        holder.ivDelete.setOnClickListener(new OnItemClickListener(position, onClickDelete));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
