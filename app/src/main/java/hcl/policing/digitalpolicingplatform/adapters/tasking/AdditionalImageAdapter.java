package hcl.policing.digitalpolicingplatform.adapters.tasking;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskDetailResponseDTO;

public class AdditionalImageAdapter extends RecyclerView.Adapter<AdditionalImageAdapter.MyViewHolder> {

    private Context mContext;
    List<TaskDetailResponseDTO.TaskImagesDetail> relevantNominalDetails;
    boolean isMoving = false;

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView ivImage;
        public MyViewHolder(View view) {
            super(view);
            ivImage = view.findViewById(R.id.iv_image);
        }
    }

    public AdditionalImageAdapter(Context mContext, List<TaskDetailResponseDTO.TaskImagesDetail> relevantNominalDetails) {
        this.mContext = mContext;
        this.relevantNominalDetails = relevantNominalDetails;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        try {
            byte[] decodedString = Base64.decode(relevantNominalDetails.get(position).getThumbnailImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.ivImage.setImageBitmap(decodedByte);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @Override
    public int getItemCount() {
        return relevantNominalDetails.size();
    }
}
