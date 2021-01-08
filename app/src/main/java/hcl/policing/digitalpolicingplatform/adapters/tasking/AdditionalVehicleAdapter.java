package hcl.policing.digitalpolicingplatform.adapters.tasking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskDetailResponseDTO;

public class AdditionalVehicleAdapter extends RecyclerView.Adapter<AdditionalVehicleAdapter.MyViewHolder> {

    private Context mContext;
    List<TaskDetailResponseDTO.RelevantVehicleDetail> relevantNominalDetails;
    boolean isMoving = false;

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvVrm, tvMake, tvModel, tvColor, tvSpecificInfo;

        public MyViewHolder(View view) {
            super(view);
            tvVrm = view.findViewById(R.id.tv_vrm);
            tvMake = view.findViewById(R.id.tv_make);
            tvModel = view.findViewById(R.id.tv_model);
            tvColor = view.findViewById(R.id.tv_color);
            tvSpecificInfo = view.findViewById(R.id.tv_specific_info);
        }
    }

    public AdditionalVehicleAdapter(Context mContext, List<TaskDetailResponseDTO.RelevantVehicleDetail> relevantNominalDetails) {
        this.mContext = mContext;
        this.relevantNominalDetails = relevantNominalDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.additional_vehicle_details_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try {
            holder.tvVrm.setText(relevantNominalDetails.get(position).getVehicleVRM());
            holder.tvMake.setText(relevantNominalDetails.get(position).getMake());
            holder.tvModel.setText(relevantNominalDetails.get(position).getModel());
            holder.tvColor.setText(relevantNominalDetails.get(position).getColour());
            holder.tvSpecificInfo.setText(relevantNominalDetails.get(position).getSpecificInfo());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return relevantNominalDetails.size();
    }
}
