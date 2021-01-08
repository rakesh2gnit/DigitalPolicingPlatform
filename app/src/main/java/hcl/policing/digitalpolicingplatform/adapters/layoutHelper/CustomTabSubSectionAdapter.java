package hcl.policing.digitalpolicingplatform.adapters.layoutHelper;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomRecyclerView;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.subjsonlist.TabNameDTO;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;

public class CustomTabSubSectionAdapter extends CustomRecyclerView.Adapter<CustomTabSubSectionAdapter.MyViewHolder> {

    private Context mContext;
    private OnItemClickListener.OnItemClickCallback onClickItem;
    private List<TabNameDTO> list;
    boolean isMoving = false;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvCount;

        public MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            tvCount = view.findViewById(R.id.tv_count);
        }
    }

    public CustomTabSubSectionAdapter(Context mContext, List<TabNameDTO> list,
                                      OnItemClickListener.OnItemClickCallback onClickItem) {
        this.mContext = mContext;
        this.list = list;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_customtab, parent, false);

        return new MyViewHolder(view);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {
        String name = BasicMethodsUtil.getInstance().getNormalServerName(list.get(position).getServerName());
        holder.tvName.setText(name);
        if (list.get(position).getCount() > 0) {
            holder.tvCount.setVisibility(View.VISIBLE);
            holder.tvCount.setText("" + (list.get(position).getCount()));
        } else {
            holder.tvCount.setVisibility(View.GONE);
        }
        Log.e(" LIST ", " ID >> " + list.get(position).getId() + " VISIBILITY >> " + list.get(position).isVisibility());

        if (list.get(position).isVisibility()) {
            holder.itemView.setVisibility(View.VISIBLE);
        } else {
            holder.itemView.setVisibility(View.GONE);
            holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(0, 0));

            if (ProcessCreationActivity.idShowSubSectionList != null && ProcessCreationActivity.idShowSubSectionList.size() > 0) {

                for (int i = 0; i < ProcessCreationActivity.idShowSubSectionList.size(); i++) {

                    int id = ProcessCreationActivity.idShowSubSectionList.get(i);

                    if (list.get(position).getId() == id) {
                        holder.itemView.setVisibility(View.VISIBLE);
                        holder.itemView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
                    }
                }
            }
        }

        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickItem));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
