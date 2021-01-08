package hcl.policing.digitalpolicingplatform.adapters.process;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCommonProperties;
import hcl.policing.digitalpolicingplatform.customLibraries.layoutHelper.CreateDynamicView;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.event.EventSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.crime.EventSearchList;
import hcl.policing.digitalpolicingplatform.models.search.RecentLogsBean;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;

public class RecentLogsAdaptor extends RecyclerView.Adapter<RecentLogsAdaptor.MyViewHolder> {

    private ArrayList<RecentLogsBean> recentList;
    private int type;
    private TextView tvNoServiceExist;
    private EventSelectionListener eventSelectionListener;
    private OnItemClickListener.OnItemClickCallback itemClickCallback;
    private Context mContext;

    public RecentLogsAdaptor(Context context, ArrayList<RecentLogsBean> datalist,
                             OnItemClickListener.OnItemClickCallback itemClickCallback) {
        this.mContext = context;
        this.recentList = datalist;
        this.itemClickCallback = itemClickCallback;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recent_logs, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

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
                customCommonProperties.setHeight(mContext, holder.llAdd, 75);
            }
        });
        holder.itemView.setOnClickListener(new OnItemClickListener(position, itemClickCallback));

        for (int i = 0; i < recentList.get(position).getRecentLogsList().size(); i++) {
            if (!recentList.get(position).getRecentLogsList().get(i).getKey().contains(FieldsNameConstant.ID)
                    && !recentList.get(position).getRecentLogsList().get(i).getKey().contains(FieldsNameConstant.SYSTEM)
                    && recentList.get(position).getRecentLogsList().get(i).getValue() != null
                    && !recentList.get(position).getRecentLogsList().get(i).getValue().equalsIgnoreCase("")) {
                String key = BasicMethodsUtil.getInstance().getNormalServerName(recentList.get(position).getRecentLogsList().get(i).getKey());
                String value = recentList.get(position).getRecentLogsList().get(i).getValue();
                CreateDynamicView.subJsonList(mContext, key, value, holder.llAdd);
            }
        }
    }

    @Override
    public int getItemCount() {
        return recentList.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvKey, tvValue;

        TextView tvViewMore, tvViewLess;
        LinearLayout llAdd;

        private MyViewHolder(View view) {
            super(view);
            llAdd = view.findViewById(R.id.ll_add_fields);
            tvViewMore = view.findViewById(R.id.tv_view_more);
            tvViewLess = view.findViewById(R.id.tv_view_less);

            tvKey = view.findViewById(R.id.tv_key);
            tvValue = view.findViewById(R.id.tv_value);
        }
    }
}

