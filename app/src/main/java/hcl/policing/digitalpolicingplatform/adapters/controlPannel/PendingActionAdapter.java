package hcl.policing.digitalpolicingplatform.adapters.controlPannel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.controlPannel.TaskResponseDTO;

public class PendingActionAdapter extends RecyclerView.Adapter<PendingActionAdapter.MyViewHolder> {

    private Context mContext;
    private OnItemClickListener.OnItemClickCallback onClickItem;
    private ArrayList<TaskResponseDTO> list;
    boolean isMoving = false;

    /**
     * MyViewHolder class
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title, subtitle, time;
        //ImageView imgView;

        MyViewHolder(View view) {
            super(view);
            title = view.findViewById(R.id.title);
            subtitle = view.findViewById(R.id.subtitle);
            time = view.findViewById(R.id.time);
            //imgView = view.findViewById(R.id.imgView);
        }
    }

    public PendingActionAdapter(Context mContext, ArrayList<TaskResponseDTO> list,
                                OnItemClickListener.OnItemClickCallback onClickItem) {
        this.mContext = mContext;
        this.list = list;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_pending_action_task_list, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.title.setText(list.get(position).getTaskTypeName());
        //holder.subtitle.setText(list.get(position).getSubtitle());
        holder.time.setText(convertTime(list.get(position).getTimeLeftExpire()));

        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickItem));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    /**
     * Convert the time
     * @param value
     * @return
     */
    private String convertTime(String value) {
        String requiredTime = "";
        value = padLeftZeros(value, 4);
        String hours = value.substring(0, 2);
        String minutes = value.substring(2, 4);
        Log.e("testtime", ">>>>>>>>>>>>>>>>>>" + hours + ">>" + minutes);
        int displayhours = Integer.parseInt(hours) / 24;
        if (displayhours > 0)
            requiredTime = displayhours + " days";
        else
            requiredTime = hours + ":" + minutes + " hours";
        return requiredTime;
    }

    /**
     * Call the method pad left zero
     * @param inputString
     * @param length
     * @return
     */
    private String padLeftZeros(String inputString, int length) {
        if (inputString.length() >= length) {
            return inputString;
        }
        StringBuilder sb = new StringBuilder();
        while (sb.length() < length - inputString.length()) {
            sb.append('0');
        }
        sb.append(inputString);
        return sb.toString();
    }
}
