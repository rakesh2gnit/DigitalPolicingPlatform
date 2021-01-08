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

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.database.ProcessSubProcessMapper;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;

public class ControlPanelAdapter extends RecyclerView.Adapter<ControlPanelAdapter.MyViewHolder> {

    private Context mContext;
    private OnItemClickListener.OnItemClickCallback onClickItem;
    private ArrayList<ProcessSubProcessMapper> aProcessSubProcessMapper;


    public ControlPanelAdapter(Context mContext, ArrayList<ProcessSubProcessMapper> list,
                               OnItemClickListener.OnItemClickCallback onClickItem) {
        this.mContext = mContext;
        this.aProcessSubProcessMapper = list;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_controlpanel, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        ProcessSubProcessMapper mProcessSubProcessMapper=aProcessSubProcessMapper.get(position);

        holder.tvName.setText(mProcessSubProcessMapper.getProcessName());

       /* String iconName = mProcessSubProcessMapper.getProcessIcon()+"_blue";
        int drawableId = mContext.getResources().getIdentifier(iconName, "drawable", mContext.getPackageName());
        holder.ivIcon.setImageResource(drawableId);*/
        String iconUrl=mProcessSubProcessMapper.getProcessIcon()+".jpg";
        Picasso.get().load(iconUrl).error(R.drawable.ic_search_white).placeholder(R.drawable.ic_search_white).into(holder.ivIcon);
        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickItem));
    }

    @Override
    public int getItemCount() {
        return aProcessSubProcessMapper.size();
    }

    /**
     * MyViewHolder class
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivIcon;

        MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            ivIcon = view.findViewById(R.id.iv_icon);
        }
    }
}
