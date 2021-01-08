package hcl.policing.digitalpolicingplatform.adapters.process.fds.address;

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
import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.address.AddressSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.AddressBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.stops.SearchAddressListModel;
import hcl.policing.digitalpolicingplatform.utils.LocationUtil;

public class AddressStopsListAdaptor extends RecyclerView.Adapter<AddressStopsListAdaptor.MyViewHolder> {

    private List<?> aAddressBean;
    private AddressSelectionListener addressSelectionListener;
    private Context mContext;
    private ItemClickListener itemClickListener;
    private boolean isPopulate;

    public AddressStopsListAdaptor(Context context, int type, ArrayList<?> datalist, AddressSelectionListener listener,
                                   ItemClickListener itemListener, boolean populate) {
        this.mContext = context;
        this.aAddressBean = datalist;
        this.addressSelectionListener = listener;
        this.itemClickListener = itemListener;
        this.isPopulate=populate;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_stop_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        SearchAddressListModel searchAddressListBean = (SearchAddressListModel) aAddressBean.get(position);
        holder.tvHouseNoValue.setText(searchAddressListBean.getHousenumber());
        holder.tvStreetValue.setText(searchAddressListBean.getStreet());
        holder.tvCityValue.setText(searchAddressListBean.getCity());
        holder.tvPostcodeValue.setText(searchAddressListBean.getPostcode());
    }

    @Override
    public int getItemCount() {
        return aAddressBean.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvHouseNoValue, tvStreetValue, tvCityValue, tvPostcodeValue;
        private ImageView ivView, ivNavigate;
        private LinearLayout llAddress;

        private MyViewHolder(View view) {
            super(view);
            ivView = view.findViewById(R.id.ivView);
            ivNavigate = view.findViewById(R.id.ivNavigate);
            tvHouseNoValue = view.findViewById(R.id.tvHouseNoValue);
            tvStreetValue = view.findViewById(R.id.tvStreetValue);
            tvCityValue = view.findViewById(R.id.tvCityValue);
            tvPostcodeValue = view.findViewById(R.id.tvPostcodeValue);
            llAddress = view.findViewById(R.id.llAddress);

            if (!isPopulate){
                ivView.setVisibility(View.INVISIBLE);
            }
            llAddress.setOnClickListener(this);
            ivView.setOnClickListener(this);
            ivNavigate.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.llAddress:

                    if (isPopulate){
                        AddressBean addressBean = getAddressDetail(aAddressBean.get(getAdapterPosition()));
                        addressSelectionListener.onAddressSelected(addressBean);
                    }else {
                        itemClickListener.onItemClicked(GenericConstant.TYPE_ATHENA);
                    }

                    break;

                case R.id.ivView:
                    itemClickListener.onItemClicked(GenericConstant.TYPE_ATHENA);
                    break;

                case R.id.ivNavigate:
                    StringBuilder sbAddress = new StringBuilder();
                    SearchAddressListModel searchAddressListBean = (SearchAddressListModel) aAddressBean.get(getAdapterPosition());
                    sbAddress.append(searchAddressListBean.getHousenumber())
                            .append(",").append(searchAddressListBean.getStreet())
                            .append(",").append(searchAddressListBean.getCity())
                            .append(",").append(searchAddressListBean.getPostcode());

                    LocationUtil.getInstance().navigateTo(mContext, sbAddress.toString());

                    break;

            }

        }
    }


    /**
     * get Person Details
     * @param object
     * @return
     */
    private AddressBean getAddressDetail(Object object) {

        AddressBean addressBean = null;

        SearchAddressListModel searchAddressListBean = (SearchAddressListModel) object;
        addressBean = new AddressBean();
        addressBean.setHouseNo(searchAddressListBean.getHousenumber());
        addressBean.setBuildingno(searchAddressListBean.getHousenumber());
        addressBean.setFlatno(searchAddressListBean.getFlatnumber());
        addressBean.setStreet(searchAddressListBean.getStreet());
        addressBean.setCity(searchAddressListBean.getCity());
        addressBean.setCountry(searchAddressListBean.getCountry());
        addressBean.setPostcode(searchAddressListBean.getPostcode());
        addressBean.setId(searchAddressListBean.getAddressid());
        addressBean.setSystem(GenericConstant.STOPS);

        return addressBean;
    }
}

