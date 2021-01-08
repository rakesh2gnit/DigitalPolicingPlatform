package hcl.policing.digitalpolicingplatform.adapters.attachment;

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

public class AttachmentListAdaptor extends RecyclerView.Adapter<AttachmentListAdaptor.MyViewHolder> {

    private ArrayList<PhotoListModel> imageList;
    private OnItemClickListener.OnItemClickCallback onClickDelete, onClickItem;
    private OnEditTextChangedListener onEditTextChanged;
    private Context mContext;

    public AttachmentListAdaptor(Context context, ArrayList<PhotoListModel> imageList,
                                 OnItemClickListener.OnItemClickCallback onClickItem,
                                 OnItemClickListener.OnItemClickCallback onClickDelete,
                                 OnEditTextChangedListener onEditTextChanged) {
        this.mContext = context;
        this.imageList = imageList;
        this.onClickItem = onClickItem;
        this.onClickDelete = onClickDelete;
        this.onEditTextChanged = onEditTextChanged;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.attachment_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvName.setText(imageList.get(position).getPhoto());
        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickItem));
        holder.ivClose.setOnClickListener(new OnItemClickListener(position, onClickDelete));
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
        private ImageView ivClose;
        private TextView tvName;

        private MyViewHolder(View view) {
            super(view);
            etDescription = view.findViewById(R.id.et_description);
            ivClose = view.findViewById(R.id.iv_close);
            tvName = view.findViewById(R.id.tv_name);
        }
    }
}