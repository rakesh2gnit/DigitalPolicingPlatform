package hcl.policing.digitalpolicingplatform.adapters.process.crimegroup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCommonProperties;
import hcl.policing.digitalpolicingplatform.listeners.process.crimegroup.CrimeGroupSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.offence.OffenceSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.crime.CrimeGroupList;
import hcl.policing.digitalpolicingplatform.models.process.crime.HOOffenceList;

public class CrimeGroupListAdaptor extends RecyclerView.Adapter<CrimeGroupListAdaptor.MyViewHolder> {

    private ArrayList<CrimeGroupList> crimeGroupList;
    private int type;
    private TextView tvNoServiceExist;
    private CrimeGroupSelectionListener crimeGroupSelectionListener;
    private ItemClickListener itemClickListener;
    private Context mContext;

    public CrimeGroupListAdaptor(Context context, ArrayList<CrimeGroupList> datalist,
                                 CrimeGroupSelectionListener listener, ItemClickListener detailListener) {
        this.mContext = context;
        this.crimeGroupList = datalist;
        this.crimeGroupSelectionListener = listener;
        this.itemClickListener = detailListener;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_crime_group, parent, false);
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
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CrimeGroupList crimeList = crimeGroupList.get(position);
                crimeList.setSystem(GenericConstant.POLE);
                crimeGroupSelectionListener.onCrimeGroupSelected(crimeList);
            }
        });
        if(crimeGroupList.get(position).getName() != null) {
            holder.tvName.setText(crimeGroupList.get(position).getName());
        }
        if(crimeGroupList.get(position).getType() != null) {
            holder.tvType.setText(crimeGroupList.get(position).getType());
        }
        if(crimeGroupList.get(position).getRefNo() != null) {
            holder.tvRefNo.setText(crimeGroupList.get(position).getRefNo());
        }
        if(crimeGroupList.get(position).getStatus() != null) {
            holder.tvStatus.setText(crimeGroupList.get(position).getStatus());
        }
    }

    @Override
    public int getItemCount() {
        return crimeGroupList.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvType, tvRefNo, tvStatus;

        TextView tvViewMore, tvViewLess;
        LinearLayout llAdd;

        private MyViewHolder(View view) {
            super(view);
            llAdd = view.findViewById(R.id.ll_add_fields);
            tvViewMore = view.findViewById(R.id.tv_view_more);
            tvViewLess = view.findViewById(R.id.tv_view_less);

            tvName = view.findViewById(R.id.tv_name);
            tvType = view.findViewById(R.id.tv_type);
            tvRefNo = view.findViewById(R.id.tv_ref_no);
            tvStatus = view.findViewById(R.id.tv_status);
        }
    }
}

