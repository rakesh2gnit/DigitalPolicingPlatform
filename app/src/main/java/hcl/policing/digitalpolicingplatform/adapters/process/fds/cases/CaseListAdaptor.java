package hcl.policing.digitalpolicingplatform.adapters.process.fds.cases;

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
import hcl.policing.digitalpolicingplatform.listeners.process.fds.cases.CaseSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.cases.CaseAthenaListResponse;

public class CaseListAdaptor extends RecyclerView.Adapter<CaseListAdaptor.MyViewHolder> {

    private ArrayList<CaseAthenaListResponse.CaseListBean> aCaseListBean;
    private CaseSelectionListener caseSelectionListener;
    private ItemClickListener itemClickListener;
    private boolean isPopulate;

    public CaseListAdaptor(ArrayList<CaseAthenaListResponse.CaseListBean> datalist, CaseSelectionListener listener,
                           ItemClickListener detailListener, boolean populate) {
        this.aCaseListBean = datalist;
        this.caseSelectionListener = listener;
        this.itemClickListener = detailListener;
        this.isPopulate = populate;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cases_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        CaseAthenaListResponse.CaseListBean mCaseListBean = aCaseListBean.get(position);

        holder.tvReferenceValue.setText(mCaseListBean.getCasereference());
        holder.tvStatusValue.setText(mCaseListBean.getStatus());
        holder.tvFileTypeValue.setText(mCaseListBean.getFiletype());
        holder.tvOICValue.setText(mCaseListBean.getOic());

    }

    @Override
    public int getItemCount() {
        return aCaseListBean.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvReferenceValue, tvStatusValue, tvFileTypeValue, tvOICValue;

        private MyViewHolder(View view) {
            super(view);
            ImageView ivView = view.findViewById(R.id.ivView);
            tvReferenceValue = view.findViewById(R.id.tvReferenceValue);
            tvStatusValue = view.findViewById(R.id.tvStatusValue);
            tvFileTypeValue = view.findViewById(R.id.tvFileTypeValue);
            tvOICValue = view.findViewById(R.id.tvOICValue);
            LinearLayout llCases = view.findViewById(R.id.llCases);

            if (!isPopulate) {
                ivView.setVisibility(View.INVISIBLE);
            }

            llCases.setOnClickListener(this);
            ivView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.llCases:
                    if (isPopulate) {
                        AddressBean addressBean = new AddressBean();
                        caseSelectionListener.onCaseSelected(addressBean);
                    } else {
                        itemClickListener.onItemClicked(GenericConstant.TYPE_CASES);
                    }

                    break;

                case R.id.ivView:
                    itemClickListener.onItemClicked(GenericConstant.TYPE_CASES);

                    break;

            }

        }
    }

}
