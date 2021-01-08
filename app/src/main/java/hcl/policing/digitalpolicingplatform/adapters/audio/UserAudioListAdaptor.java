package hcl.policing.digitalpolicingplatform.adapters.audio;

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
import hcl.policing.digitalpolicingplatform.listeners.OnItemLongClickListener;
import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;

public class UserAudioListAdaptor extends RecyclerView.Adapter<UserAudioListAdaptor.MyViewHolder> {

    private ArrayList<PhotoListModel> imageList;
    private OnItemClickListener.OnItemClickCallback onClickItem;
    private OnItemLongClickListener.OnItemLongClickCallback onLongClick;

    public UserAudioListAdaptor(Context context, ArrayList<PhotoListModel> imageList,
                                OnItemClickListener.OnItemClickCallback onClickItem,
                                OnItemLongClickListener.OnItemLongClickCallback onLongClick) {
        //this.mContext = context;
        this.imageList = imageList;
        this.onClickItem = onClickItem;
        this.onLongClick = onLongClick;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvAudioName.setText(imageList.get(position).getPhoto());
        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickItem));
        holder.itemView.setOnLongClickListener(new OnItemLongClickListener(position, onLongClick));
        if(imageList.get(position).getStatus() != null && imageList.get(position).getStatus().equalsIgnoreCase("1")) {
            holder.ivPlayPause.setImageResource(R.drawable.ic_pause_gray);
        } else {
            holder.ivPlayPause.setImageResource(R.drawable.ic_play_gray);
        }
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvAudioName;
        private ImageView ivPlayPause;

        private MyViewHolder(View view) {
            super(view);
            tvAudioName = view.findViewById(R.id.tv_audio_name);
            ivPlayPause = view.findViewById(R.id.iv_play_pause);
        }
    }
}