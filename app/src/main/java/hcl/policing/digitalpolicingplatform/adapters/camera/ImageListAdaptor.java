package hcl.policing.digitalpolicingplatform.adapters.camera;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
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
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class ImageListAdaptor extends RecyclerView.Adapter<ImageListAdaptor.MyViewHolder> {

    private String dir;
    private ArrayList<PhotoListModel> imageList;
    private int type;
    private TextView tvNoServiceExist;
    private OnItemClickListener.OnItemClickCallback onClickClose;
    private OnEditTextChangedListener onEditTextChanged;
    private Context mContext;

    public ImageListAdaptor(Context context, ArrayList<PhotoListModel> imageList,
                            OnItemClickListener.OnItemClickCallback onClickClose,
                            OnEditTextChangedListener onEditTextChanged, String dir) {
        this.mContext = context;
        this.imageList = imageList;
        this.onClickClose = onClickClose;
        this.onEditTextChanged = onEditTextChanged;
        this.dir = dir;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.camera_list_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        String image = Utilities.getInstance(mContext).readfile(imageList.get(position).getPhoto(), dir);
        if (image != null && !image.equalsIgnoreCase("")) {
            byte[] decodedString = Base64.decode(image, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            holder.ivImage.setImageBitmap(bitmap);
        }
        holder.tvCount.setText((position + 1) + " / " + imageList.size());
        holder.ivClose.setOnClickListener(new OnItemClickListener(position, onClickClose));
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
        private ImageView ivImage, ivClose;
        private TextView tvCount;

        private MyViewHolder(View view) {
            super(view);
            etDescription = view.findViewById(R.id.et_description);
            ivImage = view.findViewById(R.id.iv_image);
            ivClose = view.findViewById(R.id.iv_close);
            tvCount = view.findViewById(R.id.tv_count);
        }
    }
}