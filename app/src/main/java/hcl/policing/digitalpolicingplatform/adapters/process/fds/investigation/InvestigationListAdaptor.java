package hcl.policing.digitalpolicingplatform.adapters.process.fds.investigation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.investigation.InvestigationSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.investigation.InvestigationAthenaListResponse;

public class InvestigationListAdaptor extends RecyclerView.Adapter<InvestigationListAdaptor.MyViewHolder> {

    private ArrayList<InvestigationAthenaListResponse.InvestigationListBean> aInvestigationListBean;
    private InvestigationSelectionListener investigationSelectionListener;
    private ItemClickListener itemClickListener;
    private boolean isPopulate;

    public InvestigationListAdaptor(ArrayList<InvestigationAthenaListResponse.InvestigationListBean> datalist,
                                    InvestigationSelectionListener listener, ItemClickListener detailListener, boolean populate) {
        this.aInvestigationListBean = datalist;
        this.investigationSelectionListener = listener;
        this.itemClickListener = detailListener;
        this.isPopulate = populate;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.investigation_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        InvestigationAthenaListResponse.InvestigationListBean mInvestigationListBean = aInvestigationListBean.get(position);

        holder.tvReferenceValue.setText(mInvestigationListBean.getInvestigationnumber());
        holder.tvCnCValue.setText(mInvestigationListBean.getCcNumber());
        holder.tvOICValue.setText(mInvestigationListBean.getOic());

    }

    @Override
    public int getItemCount() {
        return aInvestigationListBean.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvReferenceValue, tvCnCValue, tvOICValue;

        private MyViewHolder(View view) {
            super(view);
            ImageView ivView = view.findViewById(R.id.ivView);
            tvReferenceValue = view.findViewById(R.id.tvReferenceValue);
            tvCnCValue = view.findViewById(R.id.tvCnCValue);
            tvOICValue = view.findViewById(R.id.tvOICValue);
            LinearLayout llInvestigation = view.findViewById(R.id.llInvestigation);

            if (!isPopulate) {
                ivView.setVisibility(View.INVISIBLE);
            }

            llInvestigation.setOnClickListener(this);
            ivView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.llInvestigation:
                    if (isPopulate) {
                        AddressBean addressBean = new AddressBean();
                        investigationSelectionListener.onInvestigationSelected(addressBean);
                    } else {
                        itemClickListener.onItemClicked(GenericConstant.TYPE_INVESTIGATION);
                    }

                    break;

                case R.id.ivView:
                    itemClickListener.onItemClicked(GenericConstant.TYPE_INVESTIGATION);

                    break;

            }

        }
    }

}
