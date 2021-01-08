package hcl.policing.digitalpolicingplatform.adapters.process.fds.courtwarrant;

import android.os.Build;
import android.text.Html;
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
import hcl.policing.digitalpolicingplatform.listeners.process.fds.courtwarrant.CourtWarrantSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.courtwarrant.CourtWarrantAthenaListResponse;

public class CourtWarranrListAdaptor extends RecyclerView.Adapter<CourtWarranrListAdaptor.MyViewHolder> {

    private ArrayList<CourtWarrantAthenaListResponse.CourtWarrentListBean> aCourtWarrentListBean;
    private CourtWarrantSelectionListener courtWarrantSelectionListener;
    private ItemClickListener itemClickListener;
    private boolean isPopulate;

    public CourtWarranrListAdaptor(ArrayList<CourtWarrantAthenaListResponse.CourtWarrentListBean> datalist, CourtWarrantSelectionListener listener,
                                   ItemClickListener detailListener, boolean populate) {
        this.aCourtWarrentListBean = datalist;
        this.courtWarrantSelectionListener = listener;
        this.itemClickListener = detailListener;
        this.isPopulate = populate;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.court_warrant_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        CourtWarrantAthenaListResponse.CourtWarrentListBean mCourtWarrentListBean = aCourtWarrentListBean.get(position);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            holder.tvCourtDetails.setText(Html.fromHtml(mCourtWarrentListBean.getSurname(), Html.FROM_HTML_MODE_COMPACT));
        } else {
            holder.tvCourtDetails.setText(Html.fromHtml(mCourtWarrentListBean.getSurname()));
        }

        holder.tvReferenceValue.setText(mCourtWarrentListBean.getWarrantreference());
        holder.tvStatusValue.setText(mCourtWarrentListBean.getStatus());
        holder.tvCategoryValue.setText(mCourtWarrentListBean.getCategory());
        holder.tvIssueDateValue.setText(mCourtWarrentListBean.getIssuedate());
        holder.tvDueDateValue.setText(mCourtWarrentListBean.getDuedate());

    }

    @Override
    public int getItemCount() {
        return aCourtWarrentListBean.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvCourtDetails, tvReferenceValue, tvCategoryValue, tvStatusValue, tvIssueDateValue, tvDueDateValue;

        private MyViewHolder(View view) {
            super(view);
            ImageView ivView = view.findViewById(R.id.ivView);
            tvCourtDetails = view.findViewById(R.id.tvCourtDetails);
            tvReferenceValue = view.findViewById(R.id.tvReferenceValue);
            tvStatusValue = view.findViewById(R.id.tvStatusValue);
            tvCategoryValue = view.findViewById(R.id.tvCategoryValue);
            tvIssueDateValue = view.findViewById(R.id.tvIssueDateValue);
            tvDueDateValue = view.findViewById(R.id.tvDueDateValue);
            LinearLayout llCourt = view.findViewById(R.id.llCourt);

            if (!isPopulate) {
                ivView.setVisibility(View.INVISIBLE);
            }

            llCourt.setOnClickListener(this);
            ivView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.llCourt:
                    if (isPopulate) {
                        AddressBean addressBean = new AddressBean();
                        courtWarrantSelectionListener.onCourtWarrantSelected(addressBean);
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

