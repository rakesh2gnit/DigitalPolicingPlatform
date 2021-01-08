package hcl.policing.digitalpolicingplatform.adapters.controlPannel;

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
import hcl.policing.digitalpolicingplatform.models.controlPannel.DraftDTO;

public class DraftFileAdapter extends RecyclerView.Adapter<DraftFileAdapter.MyViewHolder> {

    private Context mContext;
    private OnItemClickListener.OnItemClickCallback onClickItem, onClickDelete;
    private ArrayList<DraftDTO> list;

    /**
     * MyViewHolder class
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvTime;
        ImageView ivDelete;

        MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvTime = view.findViewById(R.id.tv_time);
            ivDelete = view.findViewById(R.id.iv_delete);
        }
    }

    public DraftFileAdapter(Context mContext, ArrayList<DraftDTO> list,
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_file, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        holder.tvName.setText(list.get(position).getName());
        holder.tvTime.setText(list.get(position).getTime());

        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickItem));
        holder.ivDelete.setOnClickListener(new OnItemClickListener(position, onClickDelete));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
