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
import hcl.policing.digitalpolicingplatform.models.process.ProcessLogDTO;

public class CreateLogGroupsAdapter extends RecyclerView.Adapter<CreateLogGroupsAdapter.MyViewHolder> {

    private Context mContext;
    private OnItemClickListener.OnItemClickCallback onClickItem;
    private ArrayList<ProcessLogDTO> list;

    /**
     * MyViewHolder class
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cvCard;
        TextView tvDate, tvType, tvName, tvCreatedBy, tvTime;
        ImageView ivType, ivPrivate, ivPin, ivSelect;

        MyViewHolder(View view) {
            super(view);
            cvCard = view.findViewById(R.id.cv_card);
            tvDate = view.findViewById(R.id.tv_date);
            tvType = view.findViewById(R.id.tv_type);
            tvName = view.findViewById(R.id.tv_name);
            tvCreatedBy = view.findViewById(R.id.tv_created_by);
            tvTime = view.findViewById(R.id.tv_time);
            ivSelect = view.findViewById(R.id.iv_select);
            ivType = view.findViewById(R.id.iv_type);
            ivPin = view.findViewById(R.id.iv_pin);
            ivPrivate = view.findViewById(R.id.iv_private);
        }
    }

    public CreateLogGroupsAdapter(Context mContext, ArrayList<ProcessLogDTO> list,
                                  OnItemClickListener.OnItemClickCallback onClickItem) {
        this.mContext = mContext;
        this.list = list;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_create_log_groups, parent, false);

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
            holder.ivType.setImageResource(R.drawable.ic_edit);
        } else if (list.get(position).getFileType().equalsIgnoreCase(GenericConstant.OFFLINE_FILE)) {
            holder.ivType.setImageResource(R.drawable.ic_offline);
        }

        if(list.get(position).getGroupFlag().equalsIgnoreCase("1")) {
            holder.ivSelect.setImageResource(R.drawable.ic_check_box_checked);
            holder.cvCard.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.grey_variant_2));
        } else {
            holder.ivSelect.setImageResource(R.drawable.ic_check_box_unchecked);
            holder.cvCard.setCardBackgroundColor(ContextCompat.getColor(mContext, R.color.white));
        }

        if(list.get(position).getPinFlag().equalsIgnoreCase("1")) {
            holder.ivPin.setVisibility(View.VISIBLE);
        } else {
            holder.ivPin.setVisibility(View.INVISIBLE);
        }

        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickItem));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
