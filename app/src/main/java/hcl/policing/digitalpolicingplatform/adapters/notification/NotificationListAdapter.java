package hcl.policing.digitalpolicingplatform.adapters.notification;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.controlPannel.TaskResponseDTO;
import hcl.policing.digitalpolicingplatform.utils.TimeConverterUtil;

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.MyViewHolder> {

    private Context mContext;
    private OnItemClickListener.OnItemClickCallback onClickAccept;
    private ArrayList<TaskResponseDTO> list;
    boolean isMoving = false;

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle, time, tvDescription;
        ImageView ivSide;
        //LinearLayout llCall;

        public MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            subtitle = view.findViewById(R.id.subtitle);
            time = view.findViewById(R.id.time);
            tvDescription = view.findViewById(R.id.tv_description);
            ivSide = view.findViewById(R.id.iv_side);
        }
    }

    public NotificationListAdapter(Context mContext, ArrayList<TaskResponseDTO> completedAppointmentModelArrayList,
                                   OnItemClickListener.OnItemClickCallback onClickAccept) {
        this.mContext = mContext;
        this.list = completedAppointmentModelArrayList;
        this.onClickAccept = onClickAccept;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_notification, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.title.setText(list.get(position).getTaskTypeName());
        //subtitle?.text = listTask.subtitle
        holder.time.setText(TimeConverterUtil.convertTime(list.get(position).getTimeLeftExpire()));
        //distance?.text = listTask.distance
        //imgView?.setImageResource(listTask.image)

        if (list.get(position).getTaskDescription() != null && !list.get(position).getTaskDescription().equalsIgnoreCase("")) {
            holder.tvDescription.setText(list.get(position).getTaskDescription());
        }
        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickAccept));

        if (position % 2 == 0) {
            holder.ivSide.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_yellow_circle));
        } else {
            holder.ivSide.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_blue_circle));
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
