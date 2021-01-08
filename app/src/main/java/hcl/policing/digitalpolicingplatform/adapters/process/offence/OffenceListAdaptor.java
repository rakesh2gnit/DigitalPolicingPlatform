package hcl.policing.digitalpolicingplatform.adapters.process.offence;

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
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.offence.OffenceSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.crime.HOOffenceList;

public class OffenceListAdaptor extends RecyclerView.Adapter<OffenceListAdaptor.MyViewHolder> {

    private ArrayList<HOOffenceList> offenceList;
    private int type;
    private TextView tvNoServiceExist;
    private OffenceSelectionListener offenceSelectionListener;
    private ItemClickListener itemClickListener;
    private Context mContext;

    public OffenceListAdaptor(Context context, ArrayList<HOOffenceList> datalist,
                              OffenceSelectionListener listener, ItemClickListener detailListener) {
        this.mContext = context;
        this.offenceList = datalist;
        this.offenceSelectionListener = listener;
        this.itemClickListener = detailListener;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_offence, parent, false);
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
                HOOffenceList hoOffenceList = offenceList.get(position);
                hoOffenceList.setSystem(GenericConstant.POLE);
                offenceSelectionListener.onOffenceSelected(hoOffenceList);
            }
        });
        if(offenceList.get(position).getOffence() != null) {
            holder.tvOffence.setText(offenceList.get(position).getOffence());
        }
        if(offenceList.get(position).getOffenceCategory() != null) {
            holder.tvCategory.setText(offenceList.get(position).getOffenceCategory());
        }
        if(offenceList.get(position).getAct() != null) {
            holder.tvAct.setText(offenceList.get(position).getAct());
        }
        if(offenceList.get(position).get_Class() != null) {
            holder.tvClass.setText(offenceList.get(position).get_Class());
        }
        if(offenceList.get(position).getSubClass() != null) {
            holder.tvSubClass.setText(offenceList.get(position).getSubClass());
        }
        if(offenceList.get(position).getHOCode() != null) {
            holder.tvHOCode.setText(offenceList.get(position).getHOCode());
        }
    }

    @Override
    public int getItemCount() {
        return offenceList.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvOffence, tvCategory, tvClass, tvSubClass, tvHOCode, tvAct;

        TextView tvViewMore, tvViewLess;
        LinearLayout llAdd;

        private MyViewHolder(View view) {
            super(view);
            llAdd = view.findViewById(R.id.ll_add_fields);
            tvViewMore = view.findViewById(R.id.tv_view_more);
            tvViewLess = view.findViewById(R.id.tv_view_less);

            tvOffence = view.findViewById(R.id.tv_offence);
            tvCategory = view.findViewById(R.id.tv_category);
            tvClass = view.findViewById(R.id.tv_class);
            tvSubClass = view.findViewById(R.id.tv_sub_class);
            tvHOCode = view.findViewById(R.id.tv_ho_code);
            tvAct = view.findViewById(R.id.tv_act);
        }
    }
}

