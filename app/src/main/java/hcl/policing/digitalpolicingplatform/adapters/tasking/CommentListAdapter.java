package hcl.policing.digitalpolicingplatform.adapters.tasking;

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
import hcl.policing.digitalpolicingplatform.models.tasking.CommentResponseDTO;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.MyViewHolder> {

    private Context mContext;
    private ArrayList<CommentResponseDTO> list;
    boolean isMoving = false;

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvComment, tvCreatedby, tvDate;
        ImageView imgView;
        //LinearLayout llCall;

        public MyViewHolder(View view) {
            super(view);
            tvComment = view.findViewById(R.id.tv_comment);
            tvCreatedby = view.findViewById(R.id.tv_createdby);
            tvDate = view.findViewById(R.id.tv_date);
        }
    }

    public CommentListAdapter(Context mContext, ArrayList<CommentResponseDTO> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.logs_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tvComment.setText(list.get(position).getComment());
        //subtitle?.text = listTask.subtitle
        holder.tvCreatedby.setText(list.get(position).getCreatedBy());
        String date = list.get(position).getCreatedDate();
        String date1, time;
        date1 = date.substring(0, 10);
        time = date.substring(11, 16);
        holder.tvDate.setText(date1+" "+time);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
