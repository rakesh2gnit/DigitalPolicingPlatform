package hcl.policing.digitalpolicingplatform.adapters.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.OnItemLongClickListener;
import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class UserImageListAdaptor extends RecyclerView.Adapter<UserImageListAdaptor.MyViewHolder> {

    private Context mContext;
    private ArrayList<PhotoListModel> imageList;
    private OnItemClickListener.OnItemClickCallback onClickItem;
    private OnItemLongClickListener.OnItemLongClickCallback onLongClick;
    private String dir;

    public UserImageListAdaptor(Context context, ArrayList<PhotoListModel> imageList,
                                OnItemClickListener.OnItemClickCallback onClickItem,
                                OnItemLongClickListener.OnItemLongClickCallback onLongClick,
                                String dir) {
        this.mContext = context;
        this.imageList = imageList;
        this.onClickItem = onClickItem;
        this.onLongClick = onLongClick;
        this.dir = dir;
        //this.onEditTextChanged = onEditTextChanged;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.image_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NotNull MyViewHolder holder, int position) {
        String image = Utilities.getInstance(mContext).readfile(imageList.get(position).getPhoto(), dir);
        if(image != null && !image.equalsIgnoreCase("")) {
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.ivImage.setImageBitmap(bitmap);
        }
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
        private ImageView ivImage;

        private MyViewHolder(View view) {
            super(view);
            ivImage = view.findViewById(R.id.iv_image);
        }
    }
}