package hcl.policing.digitalpolicingplatform.adapters.tasking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.controlPannel.TaskResponseDTO;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.MyViewHolder> {

    private Context mContext;
    private OnItemClickListener.OnItemClickCallback onClickAccept;
    private OnItemClickListener.OnItemClickCallback onClickReject;
    private ArrayList<TaskResponseDTO> list;
    boolean isMoving = false;

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        //TextView appointment_id, app_date, app_time, person_name, time_left, tv_service, mobile_number, tvPrice, tvBookBy, tvHomeService, tvStatus;
        //ImageView ivMale, ivFemale;
        //LinearLayout llCall;

        public MyViewHolder(View view) {
            super(view);
            /*appointment_id = view.findViewById(R.id.appointment_id);
            app_date = view.findViewById(R.id.app_date);
            app_time = view.findViewById(R.id.app_time);
            person_name = view.findViewById(R.id.person_name);
            time_left = view.findViewById(R.id.time_left);
            tv_service = view.findViewById(R.id.tv_service);
            mobile_number = view.findViewById(R.id.mobile_number);
            tvPrice = view.findViewById(R.id.tv_price);
            tvBookBy = view.findViewById(R.id.tv_booking_by);
            tvHomeService = view.findViewById(R.id.tv_home_service);
            tvStatus = view.findViewById(R.id.tv_status);
            ivMale = view.findViewById(R.id.iv_male);
            ivFemale = view.findViewById(R.id.iv_female);

            llCall = view.findViewById(R.id.ll_call);*/
        }
    }

    public TaskListAdapter(Context mContext, ArrayList<TaskResponseDTO> list/*,
                           OnItemClickListener.OnItemClickCallback onClickAccept,
                           OnItemClickListener.OnItemClickCallback onClickCall*/) {
        this.mContext = mContext;
        this.list = list;
        //this.onClickAccept = onClickAccept;
        //this.onClickReject = onClickReject;
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
        /*holder.appointment_id.setText(String.valueOf(list.get(position).getID()));
        holder.app_date.setText(list.get(position).getAppointmentDate());
        holder.app_time.setText(list.get(position).getTimeSlot());
        holder.person_name.setText(list.get(position).getName());

        holder.appointment_id.setText("" + list.get(position).getAppointmentNo());
        holder.app_date.setText(list.get(position).getAppointmentDate());
        holder.app_time.setText(list.get(position).getTimeSlot());
        holder.person_name.setText("" + list.get(position).getName());
        holder.tvPrice.setText("₹ " + list.get(position).getPaybleAmount());*/

        //holder.check_out_ll.setOnClickListener(new OnItemClickListener(position, onClickItem));
        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickAccept));
        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickReject));


    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
