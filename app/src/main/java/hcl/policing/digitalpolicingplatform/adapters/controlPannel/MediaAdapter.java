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
import hcl.policing.digitalpolicingplatform.models.controlPannel.MediaData;

public class MediaAdapter extends RecyclerView.Adapter<MediaAdapter.MyViewHolder> {

    private Context mContext;
    private OnItemClickListener.OnItemClickCallback onClickItem;
    private ArrayList<MediaData> list;
    boolean isMoving = false;

    /**
     * MyViewHolder class
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, date, description;
        ImageView ivMedia, ivIcon;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            date = view.findViewById(R.id.date);
            description = view.findViewById(R.id.description);
            ivMedia = view.findViewById(R.id.iv_media);
            ivIcon = view.findViewById(R.id.iv_icon);
        }
    }

    public MediaAdapter(Context mContext, ArrayList<MediaData> list,
                        OnItemClickListener.OnItemClickCallback onClickItem) {
        this.mContext = mContext;
        this.list = list;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_media, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.date.setText(list.get(position).getDate());
        holder.description.setText(list.get(position).getDescription());
        holder.ivMedia.setImageResource(list.get(position).getImg2());
        holder.ivIcon.setImageResource(list.get(position).getImg1());

        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickItem));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
