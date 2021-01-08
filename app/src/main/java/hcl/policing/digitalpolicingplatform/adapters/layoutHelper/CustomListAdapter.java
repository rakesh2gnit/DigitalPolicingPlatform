package hcl.policing.digitalpolicingplatform.adapters.layoutHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCommonProperties;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomRecyclerView;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateDynamicView;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.subjsonlist.KeyValueListDTO;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;

public class CustomListAdapter extends CustomRecyclerView.Adapter<CustomListAdapter.MyViewHolder> {

    private Context mContext;
    private OnItemClickListener.OnItemClickCallback onClickItemEdit, onClickItemDelete, onClickItemViewMore;
    private List<KeyValueListDTO> list;
    boolean isMoving = false;

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvViewMore, tvViewLess;
        ImageView ivDelete;
        LinearLayout llAdd;

        public MyViewHolder(View view) {
            super(view);
            llAdd = view.findViewById(R.id.ll_add_fields);
            ivDelete = view.findViewById(R.id.iv_delete);
            tvViewMore = view.findViewById(R.id.tv_view_more);
            tvViewLess = view.findViewById(R.id.tv_view_less);
        }
    }

    public CustomListAdapter(Context mContext, List<KeyValueListDTO> list,
                             OnItemClickListener.OnItemClickCallback onClickItemEdit,
                             OnItemClickListener.OnItemClickCallback onClickItemDelete,
                             OnItemClickListener.OnItemClickCallback onClickItemViewMore) {
        this.mContext = mContext;
        this.list = list;
        this.onClickItemEdit = onClickItemEdit;
        this.onClickItemDelete = onClickItemDelete;
        this.onClickItemViewMore = onClickItemViewMore;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_customlist, parent, false);

        return new MyViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        for (int i = 0; i < list.get(position).getListKeyValue().size(); i++) {
            String key = BasicMethodsUtil.getInstance().getNormalServerName(list.get(position).getListKeyValue().get(i).getKey());
            String value = list.get(position).getListKeyValue().get(i).getValue();
            CreateDynamicView.subJsonList(mContext, key, value, holder.llAdd);
        }

        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickItemEdit));
        holder.ivDelete.setOnClickListener(new OnItemClickListener(position, onClickItemDelete));

        holder.tvViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvViewMore.setVisibility(View.GONE);
                holder.tvViewLess.setVisibility(View.VISIBLE);
                CustomCommonProperties customCommonProperties = new CustomCommonProperties();
                customCommonProperties.setHeight(mContext, holder.llAdd, 0);
            }
        });
        holder.tvViewLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvViewMore.setVisibility(View.VISIBLE);
                holder.tvViewLess.setVisibility(View.GONE);

                CustomCommonProperties customCommonProperties = new CustomCommonProperties();
                customCommonProperties.setHeight(mContext, holder.llAdd, 70);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
