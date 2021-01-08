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

public class AdditionalPersonAdapter extends RecyclerView.Adapter<AdditionalPersonAdapter.MyViewHolder> {

    private Context mContext;
    List<TaskDetailResponseDTO.RelevantNominalDetail> relevantNominalDetails;
    boolean isMoving = false;

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvDob, tvContact, tvSpecificInfo;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvDob = view.findViewById(R.id.tv_dob);
            tvContact = view.findViewById(R.id.tv_contact);
            tvSpecificInfo = view.findViewById(R.id.tv_specific_info);
        }
    }

    public AdditionalPersonAdapter(Context mContext, List<TaskDetailResponseDTO.RelevantNominalDetail> relevantNominalDetails) {
        this.mContext = mContext;
        this.relevantNominalDetails = relevantNominalDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.additional_person_details_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        holder.tvName.setText(relevantNominalDetails.get(position).getFirstName() + " " + relevantNominalDetails.get(position).getLastName());
        String dob = relevantNominalDetails.get(position).getDOB();
        dob = dob.substring(0, 10);
        holder.tvDob.setText(dob);
        holder.tvContact.setText(relevantNominalDetails.get(position).getContactDetails());
        holder.tvSpecificInfo.setText(relevantNominalDetails.get(position).getSpecificInfo());

    }


    @Override
    public int getItemCount() {
        return relevantNominalDetails.size();
    }
}
