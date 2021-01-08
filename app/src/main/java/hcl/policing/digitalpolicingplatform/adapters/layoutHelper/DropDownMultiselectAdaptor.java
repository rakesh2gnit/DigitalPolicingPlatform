package hcl.policing.digitalpolicingplatform.adapters.layoutHelper;

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
import hcl.policing.digitalpolicingplatform.database.DropDown;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;

public class DropDownMultiselectAdaptor extends RecyclerView.Adapter<DropDownMultiselectAdaptor.MyViewHolder> {

    private ArrayList<DropDown> list;
    private OnItemClickListener.OnItemClickCallback onItemClickCallback;
    private Context mContext;

    public DropDownMultiselectAdaptor(Context context, ArrayList<DropDown> datalist, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.mContext = context;
        this.list = datalist;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dropdown_multiselect_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tvName.setText(list.get(position).getDescription());

        if (list.get(position).getStatus().equalsIgnoreCase("1")) {
            holder.ivCheck.setImageResource(R.drawable.ic_check_box_checked);
        } else {
            holder.ivCheck.setImageResource(R.drawable.ic_check_box_unchecked);
        }

        holder.itemView.setOnClickListener(new OnItemClickListener(position, onItemClickCallback));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvName;
        private ImageView ivCheck;

        private MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
            ivCheck = view.findViewById(R.id.iv_check);
        }
    }
}