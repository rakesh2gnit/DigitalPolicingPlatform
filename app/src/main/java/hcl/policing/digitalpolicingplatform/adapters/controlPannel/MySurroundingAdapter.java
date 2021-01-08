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
import hcl.policing.digitalpolicingplatform.models.controlPannel.LocationData;

public class MySurroundingAdapter extends RecyclerView.Adapter<MySurroundingAdapter.MyViewHolder> {

    private Context mContext;
    private OnItemClickListener.OnItemClickCallback onClickItem;
    private ArrayList<LocationData> list;
    boolean isMoving = false;

    /**
     * MyViewHolder class
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle, time, distance;
        ImageView imgView;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            subtitle = view.findViewById(R.id.subtitle);
            time = view.findViewById(R.id.time);
            distance = view.findViewById(R.id.distance);
            imgView = view.findViewById(R.id.imgView);
        }
    }

    public MySurroundingAdapter(Context mContext, ArrayList<LocationData> list,
                                OnItemClickListener.OnItemClickCallback onClickItem) {
        this.mContext = mContext;
        this.list = list;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_mysurrounding, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.title.setText(list.get(position).getTitle());
        holder.subtitle.setText(list.get(position).getSubtitle());
        holder.time.setText(list.get(position).getTime());
        holder.distance.setText(list.get(position).getDistance());
        holder.imgView.setImageResource(list.get(position).getImage());

        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickItem));
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
