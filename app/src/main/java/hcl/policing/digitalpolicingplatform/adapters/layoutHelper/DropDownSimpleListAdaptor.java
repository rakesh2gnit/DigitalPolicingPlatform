package hcl.policing.digitalpolicingplatform.adapters.layoutHelper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.database.DropDown;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;

public class DropDownSimpleListAdaptor extends RecyclerView.Adapter<DropDownSimpleListAdaptor.MyViewHolder> {

    private ArrayList<String> list;
    private OnItemClickListener.OnItemClickCallback onItemClickCallback;
    private Context mContext;

    public DropDownSimpleListAdaptor(Context context, ArrayList<String> datalist, OnItemClickListener.OnItemClickCallback onItemClickCallback) {
        this.mContext = context;
        this.list = datalist;
        this.onItemClickCallback = onItemClickCallback;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.dropdown_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tvName.setText(list.get(position));

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

        private MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tvName);
        }
    }
}