package hcl.policing.digitalpolicingplatform.adapters.process.fds.communication;

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
import hcl.policing.digitalpolicingplatform.listeners.process.fds.communication.CommunicationSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.communication.CommunicationAthenaListResponse;

public class CommunicationListAdaptor extends RecyclerView.Adapter<CommunicationListAdaptor.MyViewHolder> {

    private ArrayList<CommunicationAthenaListResponse.CommunicationListBean> aCommunicationListBean;
    private CommunicationSelectionListener communicationSelectionListener;
    private ItemClickListener itemClickListener;
    private boolean isPopulate;

    public CommunicationListAdaptor(ArrayList<CommunicationAthenaListResponse.CommunicationListBean> datalist, CommunicationSelectionListener listener,
                                    ItemClickListener detailListener,boolean populate) {
        this.aCommunicationListBean = datalist;
        this.communicationSelectionListener = listener;
        this.itemClickListener = detailListener;
        this.isPopulate = populate;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.communication_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        CommunicationAthenaListResponse.CommunicationListBean mCommunicationListBean = aCommunicationListBean.get(position);

        holder.tvCommDetailsValue.setText(mCommunicationListBean.getDetails());
        holder.tvCommTypeValue.setText(mCommunicationListBean.getType());

    }

    @Override
    public int getItemCount() {
        return aCommunicationListBean.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvCommDetailsValue, tvCommTypeValue;

        private MyViewHolder(View view) {
            super(view);
            ImageView ivView = view.findViewById(R.id.ivView);
            tvCommDetailsValue = view.findViewById(R.id.tvCommDetailsValue);
            tvCommTypeValue = view.findViewById(R.id.tvCommTypeValue);
            LinearLayout llCommunication = view.findViewById(R.id.llCommunication);

            if (!isPopulate) {
                ivView.setVisibility(View.INVISIBLE);
            }

            llCommunication.setOnClickListener(this);
            ivView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.llCommunication:
                    if (isPopulate) {
                        AddressBean addressBean = new AddressBean();
                        communicationSelectionListener.onCommunicationSelected(addressBean);
                    }else {
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
