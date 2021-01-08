package hcl.policing.digitalpolicingplatform.adapters.attachment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.OnItemLongClickListener;
import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;

public class UserDocListAdaptor extends RecyclerView.Adapter<UserDocListAdaptor.MyViewHolder> {

    private ArrayList<PhotoListModel> imageList;
    private OnItemClickListener.OnItemClickCallback onClickItem;
    private OnItemLongClickListener.OnItemLongClickCallback onLongClick;

    public UserDocListAdaptor(Context context, ArrayList<PhotoListModel> imageList,
                              OnItemClickListener.OnItemClickCallback onClickItem,
                              OnItemLongClickListener.OnItemLongClickCallback onLongClick) {
        //this.mContext = context;
        this.imageList = imageList;
        this.onClickItem = onClickItem;
        this.onLongClick = onLongClick;
        //this.onEditTextChanged = onEditTextChanged;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.doc_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tvDoc.setText(imageList.get(position).getPhoto());
        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickItem));
        holder.itemView.setOnLongClickListener(new OnItemLongClickListener(position, onLongClick));
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }


    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvDoc;

        private MyViewHolder(View view) {
            super(view);
            tvDoc = view.findViewById(R.id.tv_doc);
        }
    }
}