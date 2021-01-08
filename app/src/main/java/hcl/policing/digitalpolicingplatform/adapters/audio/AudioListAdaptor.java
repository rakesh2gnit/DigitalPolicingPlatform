package hcl.policing.digitalpolicingplatform.adapters.audio;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.listeners.OnEditTextChangedListener;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;

public class AudioListAdaptor extends RecyclerView.Adapter<AudioListAdaptor.MyViewHolder> {

    private ArrayList<PhotoListModel> imageList;
    private OnItemClickListener.OnItemClickCallback onClickClose, onClickPlay;
    private OnEditTextChangedListener onEditTextChanged;
    private Context mContext;

    public AudioListAdaptor(Context context, ArrayList<PhotoListModel> imageList,
                            OnItemClickListener.OnItemClickCallback onClickClose,
                            OnItemClickListener.OnItemClickCallback onClickPlay,
                            OnEditTextChangedListener onEditTextChanged) {
        this.mContext = context;
        this.imageList = imageList;
        this.onClickClose = onClickClose;
        this.onClickPlay = onClickPlay;
        this.onEditTextChanged = onEditTextChanged;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.audio_add_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvName.setText(imageList.get(position).getPhoto());
        holder.ivClose.setOnClickListener(new OnItemClickListener(position, onClickClose));
        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickPlay));
        holder.etDescription.setText(imageList.get(position).getDescription());
        holder.etDescription.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                onEditTextChanged.onTextChanged(position, s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        if(imageList.get(position).getStatus() != null && imageList.get(position).getStatus().equalsIgnoreCase("1")) {
            holder.ivPlay.setImageResource(R.drawable.ic_pause_gray);
        } else {
            holder.ivPlay.setImageResource(R.drawable.ic_play_gray);
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
        private EditText etDescription;
        private ImageView ivClose, ivPlay;
        private TextView tvName;

        private MyViewHolder(View view) {
            super(view);
            etDescription = view.findViewById(R.id.et_description);
            ivClose = view.findViewById(R.id.iv_close);
            ivPlay = view.findViewById(R.id.iv_play);
            tvName = view.findViewById(R.id.tv_name);
        }
    }
}