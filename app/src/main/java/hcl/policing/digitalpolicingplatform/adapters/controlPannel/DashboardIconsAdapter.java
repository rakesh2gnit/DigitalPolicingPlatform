package hcl.policing.digitalpolicingplatform.adapters.controlPannel;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.database.ProcessSubProcessMapper;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;

public class DashboardIconsAdapter extends RecyclerView.Adapter<DashboardIconsAdapter.MyViewHolder> {

    private Context mContext;
    private OnItemClickListener.OnItemClickCallback onClickItem;
    private ArrayList<ProcessSubProcessMapper> aProcessSubProcessMapper;

    public DashboardIconsAdapter(Context mContext, ArrayList<ProcessSubProcessMapper> list,
                                 OnItemClickListener.OnItemClickCallback onClickItem) {
        this.mContext = mContext;
        this.aProcessSubProcessMapper = list;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_dashboard_icons, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        ProcessSubProcessMapper mProcessSubProcessMapper = aProcessSubProcessMapper.get(position);

        DisplayMetrics displaymetrics = new DisplayMetrics();
        ((Activity) mContext).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        //if you need three fix imageview in width
        int devicewidth = displaymetrics.widthPixels / 4;
        //int deviceheight = displaymetrics.heightPixels / 4;
        holder.itemView.getLayoutParams().width = devicewidth/*(devicewidth - (DPtoPixelUtil.convertDpToPixel(10, mContext)))*/;
        //if you need same height as width you can set devicewidth in holder.image_view.getLayoutParams().height
        holder.tvName.setText(aProcessSubProcessMapper.get(position).getProcessName());


        /*String iconName = aProcessSubProcessMapper.get(position).getProcessIcon()+"_white";
        int drawableId = mContext.getResources().getIdentifier(iconName, "drawable", mContext.getPackageName());
        holder.ivIcon.setImageResource(drawableId);*/
        String iconUrl = mProcessSubProcessMapper.getProcessIcon()+".jpg";

        Picasso.get().load(iconUrl).error(R.drawable.ic_stop_white).placeholder(R.drawable.ic_stop_white).into(holder.ivIcon);
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
        LinearLayout llMain;
        TextView tvName;
        ImageView ivIcon;

        MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            ivIcon = view.findViewById(R.id.iv_icon);
        }
    }
}
