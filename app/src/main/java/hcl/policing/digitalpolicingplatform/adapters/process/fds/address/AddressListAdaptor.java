package hcl.policing.digitalpolicingplatform.adapters.process.fds.address;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
import hcl.policing.digitalpolicingplatform.models.process.fds.address.athena.AddressAthenaResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.nflms.AddressNFLMSResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.stops.SearchAddressListModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.qas.AddressdetailsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.pole.AddressList;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.LocationUtil;

public class AddressListAdaptor extends RecyclerView.Adapter<AddressListAdaptor.MyViewHolder> {

    private List<?> aAddressBean;
    private int systemType;
    private AddressSelectionListener addressSelectionListener;
    private Context mContext;
    private ItemClickListener itemClickListener;
    private boolean isPopulate;

    public AddressListAdaptor(Context context, int type, ArrayList<?> datalist, AddressSelectionListener listener,
                              ItemClickListener itemListener, boolean populate) {
        this.mContext = context;
        this.systemType = type;
        this.aAddressBean = datalist;
        this.addressSelectionListener = listener;
        this.itemClickListener = itemListener;
        this.isPopulate = populate;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.address_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        switch (systemType) {

            case GenericConstant.TYPE_PNC:

             /*   holder.layoutLost.setVisibility(View.VISIBLE);
                VehiclePNCResponse.MotorvehiclelistBean motorvehiclelistBean = (VehiclePNCResponse.MotorvehiclelistBean) aVehicleBean.get(position);
                holder.tvVRMNo.setText(motorvehiclelistBean.getNumberplate());
                holder.tvMakeValue.setText(motorvehiclelistBean.getMake());
                holder.tvModelValue.setText(motorvehiclelistBean.getModel());
                holder.tvColorValue.setText(motorvehiclelistBean.getColour());
                holder.tvlostValue.setText(motorvehiclelistBean.getStolen());
*/
                break;

            case GenericConstant.TYPE_ATHENA:

                holder.layoutLicence.setVisibility(View.GONE);
                holder.layoutWeapon.setVisibility(View.GONE);
                AddressAthenaResponse.AddressListBean addressListBean = (AddressAthenaResponse.AddressListBean) aAddressBean.get(position);
                List<AddressAthenaResponse.WarningsBean> warnings = addressListBean.getWarnings();

                if (warnings != null && warnings.size() > 0) {
                    holder.rlBadge.setVisibility(View.VISIBLE);
                    holder.tvBadgeCount.setText(String.valueOf(warnings.size()));
                }
                String address = getAddress(addressListBean);
                holder.tvAddressValue.setText(address);

                break;

            case GenericConstant.TYPE_NFLMS:

                holder.layoutLicence.setVisibility(View.VISIBLE);
                holder.layoutWeapon.setVisibility(View.VISIBLE);
                AddressNFLMSResponse.SearchResultBean searchResultBean = (AddressNFLMSResponse.SearchResultBean) aAddressBean.get(position);
                holder.tvLicenceNo.setText(searchResultBean.getLicencenumber());
                holder.tvWeaponType.setText(searchResultBean.getWeapontype());
                holder.tvAddressValue.setText(searchResultBean.getAddress());
                break;

            case GenericConstant.TYPE_QAS:

                holder.layoutLicence.setVisibility(View.GONE);
                holder.layoutWeapon.setVisibility(View.GONE);
                AddressdetailsModel addressesBean = (AddressdetailsModel) aAddressBean.get(position);
                String qasAddress = getAddress(addressesBean);
                holder.tvAddressValue.setText(qasAddress);


                break;

            case GenericConstant.TYPE_POLE:

                holder.layoutLicence.setVisibility(View.GONE);
                holder.layoutWeapon.setVisibility(View.GONE);
                AddressList addressList = (AddressList) aAddressBean.get(position);
                holder.tvAddressValue.setText(addressList.getAddressDescription());

                break;
        }


    }

    @Override
    public int getItemCount() {
        return aAddressBean.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvLicenceNo, tvWeaponType, tvAddressValue, tvBadgeCount;
        private RelativeLayout rlBadge;
        private LinearLayout layoutLicence, layoutWeapon;

        private MyViewHolder(View view) {
            super(view);
            ImageView ivView = view.findViewById(R.id.ivView);
            ImageView ivNavigate = view.findViewById(R.id.ivNavigate);
            tvLicenceNo = view.findViewById(R.id.tvLicenceNo);
            tvWeaponType = view.findViewById(R.id.tvWeaponType);
            tvAddressValue = view.findViewById(R.id.tvAddressValue);
            tvBadgeCount = view.findViewById(R.id.tvBadgeCount);
            LinearLayout llAddress = view.findViewById(R.id.llAddress);
            layoutLicence = view.findViewById(R.id.layoutLicence);
            layoutWeapon = view.findViewById(R.id.layoutWeapon);
            rlBadge = view.findViewById(R.id.rlBadge);

            if (!isPopulate) {
                ivView.setVisibility(View.INVISIBLE);
            }

            llAddress.setOnClickListener(this);
            ivView.setOnClickListener(this);
            rlBadge.setOnClickListener(this);
            ivNavigate.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.llAddress:

                    if (isPopulate) {
                        AddressBean addressBean = getAddressDetail(aAddressBean.get(getAdapterPosition()));
                        addressSelectionListener.onAddressSelected(addressBean);
                    } else {
                        itemClickListener.onItemClicked(GenericConstant.TYPE_ATHENA);
                    }

                    break;

                case R.id.ivView:
                    itemClickListener.onItemClicked(GenericConstant.TYPE_ATHENA);
                    break;

                case R.id.ivNavigate:

                    String address = null;

                    switch (systemType) {

                        case GenericConstant.TYPE_QAS:

                            AddressdetailsModel addressesBean = (AddressdetailsModel) aAddressBean.get(getAdapterPosition());
                            address = getAddress(addressesBean);

                            break;

                        case GenericConstant.TYPE_ATHENA:

                            AddressAthenaResponse.AddressListBean addressListBean = (AddressAthenaResponse.AddressListBean) aAddressBean.get(getAdapterPosition());
                            address = getAddress(addressListBean);

                            break;

                        case GenericConstant.TYPE_POLE:

                            AddressList addressList = (AddressList) aAddressBean.get(getAdapterPosition());
                            address = addressList.getAddressDescription();

                            break;
                    }

                    if (!TextUtils.isEmpty(address)) {

                        LocationUtil.getInstance().navigateTo(mContext, address);
                    }

                    break;

                case R.id.rlBadge:

                    AddressAthenaResponse.AddressListBean addressListBean = (AddressAthenaResponse.AddressListBean) aAddressBean.get(getAdapterPosition());
                    List<AddressAthenaResponse.WarningsBean> aWarningsBean = addressListBean.getWarnings();

                    if (aWarningsBean != null && aWarningsBean.size() > 0) {

                        DialogUtil.showWarningDialog(mContext, aWarningsBean, GenericConstant.TYPE_ADDRESS);
                    } else {
                        BasicMethodsUtil.getInstance().showToast(mContext, "No Warnings available!!");
                    }
                    break;

            }

        }
    }


    /**
     * get Person Details
     *
     * @param object
     * @return
     */
    private AddressBean getAddressDetail(Object object) {

        AddressBean addressBean = null;
        switch (systemType) {

            case GenericConstant.TYPE_PNC:

                AddressAthenaResponse.AddressListBean motorvehiclelistBean = (AddressAthenaResponse.AddressListBean) object;
                addressBean = new AddressBean();
                addressBean.setHouseNo(motorvehiclelistBean.getPremisesnumber());
                addressBean.setBuildingno(motorvehiclelistBean.getPremisesnumber());
                addressBean.setFlatno(motorvehiclelistBean.getPremisesnumber());
                addressBean.setStreet(motorvehiclelistBean.getStreet());
                addressBean.setCity(motorvehiclelistBean.getTown());
                addressBean.setCounty(motorvehiclelistBean.getCounty());
                addressBean.setPostcode(motorvehiclelistBean.getPostcode());
                addressBean.setId(motorvehiclelistBean.getId());
                addressBean.setAddress("");
                addressBean.setSystem(GenericConstant.PNC);

                break;

            case GenericConstant.TYPE_ATHENA:
                AddressAthenaResponse.AddressListBean addressListBean = (AddressAthenaResponse.AddressListBean) object;
                addressBean = new AddressBean();
                addressBean.setHouseNo(addressListBean.getPremisesnumber());
                addressBean.setBuildingno(addressListBean.getPremisesnumber());
                addressBean.setFlatno(addressListBean.getPremisesnumber());
                addressBean.setStreet(addressListBean.getStreet());
                addressBean.setCity(addressListBean.getTown());
                addressBean.setCounty(addressListBean.getCounty());
                addressBean.setPostcode(addressListBean.getPostcode());
                addressBean.setId(addressListBean.getId());
                addressBean.setAddress("");
                addressBean.setSystem(GenericConstant.ATHENA);

                break;

            case GenericConstant.TYPE_STOPS:

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
                addressBean.setAddress("");
                addressBean.setSystem(GenericConstant.STOPS);

                break;

            case GenericConstant.TYPE_NFLMS:

                AddressNFLMSResponse.SearchResultBean searchResultBean = (AddressNFLMSResponse.SearchResultBean) object;
                addressBean = new AddressBean();
                addressBean.setHouseNo(searchResultBean.getHouseno());
                addressBean.setBuildingno(searchResultBean.getHouseno());
                addressBean.setFlatno(searchResultBean.getHouseno());
                addressBean.setStreet(searchResultBean.getStreet());
                addressBean.setAddress(searchResultBean.getAddress());
                addressBean.setSystem(GenericConstant.NFLMS);

                break;

            case GenericConstant.TYPE_QAS:

                AddressdetailsModel addressesBean = (AddressdetailsModel) object;
                addressBean = new AddressBean();
                addressBean.setHouseNo(addressesBean.getHouseno());
                addressBean.setBuildingno(addressesBean.getHouseno());
                addressBean.setBuildingname(addressesBean.getBuilding());
                addressBean.setFlatno(addressesBean.getHouseno());
                addressBean.setFlatname(addressesBean.getBuilding());
                addressBean.setStreet(addressesBean.getStreet());
                addressBean.setCity(addressesBean.getCity());
                addressBean.setDistrict(addressesBean.getCity());
                addressBean.setCountry(addressesBean.getCountry());
                addressBean.setCounty(addressesBean.getCounty());
                addressBean.setPostcode(addressesBean.getPostcode());
                addressBean.setId(addressesBean.getAddressid());
                addressBean.setAddress("");
                addressBean.setSystem(GenericConstant.QAS);

                break;

            case GenericConstant.TYPE_POLE:

                AddressList addressList = (AddressList) object;
                addressBean = new AddressBean();
                addressBean.setBuildingno(addressList.getBuildingNo());
                addressBean.setBuildingname(addressList.getBuilding());
                addressBean.setFlatno(addressList.getFlatNo());
                addressBean.setFlatname(addressList.getFlat());
                addressBean.setStreet(addressList.getStreet());
                addressBean.setCity(addressList.getCity());
                addressBean.setDistrict(addressList.getDistrict());
                addressBean.setCountry(addressList.getCounty());
                addressBean.setCounty(addressList.getCounty());
                addressBean.setPostcode(addressList.getPostCode());
                addressBean.setAddress(addressList.getAddressDescription());
                addressBean.setId(addressList.getID());
                addressBean.setSystem(GenericConstant.POLE);

                break;
        }

        return addressBean;
    }


    /**
     * get the combined address from model class
     *
     * @param object
     * @return
     */
    private String getAddress(Object object) {

        StringBuilder sbAddress = new StringBuilder();

        switch (systemType) {

            case GenericConstant.TYPE_ATHENA:

                AddressAthenaResponse.AddressListBean addressListBean = (AddressAthenaResponse.AddressListBean) object;
                if (addressListBean != null) {
                    sbAddress.append(addressListBean.getPremisesnumber()).append(" ").append(addressListBean.getStreet()).append(" ")
                            .append(addressListBean.getTown()).append(" ").append(addressListBean.getCounty()).append(" ")
                            .append(addressListBean.getPostcode());
                }

                break;

            case GenericConstant.TYPE_STOPS:

                SearchAddressListModel searchAddressListBean = (SearchAddressListModel) object;
                if (searchAddressListBean != null) {
                    sbAddress.append(searchAddressListBean.getHousenumber()).append(" ").append(searchAddressListBean.getStreet()).append(" ")
                            .append(searchAddressListBean.getAddressid()).append(" ").append(searchAddressListBean.getCity()).append(" ")
                            .append(searchAddressListBean.getCountry()).append(" ").append(searchAddressListBean.getPostcode());
                }
                break;
            case GenericConstant.TYPE_QAS:

                AddressdetailsModel addressesBean = (AddressdetailsModel) object;
                if (addressesBean != null) {
                    sbAddress.append(addressesBean.getHouseno()).append(" ").append(addressesBean.getStreet()).append(" ")
                            .append(addressesBean.getAddressid()).append(" ").append(addressesBean.getCity()).append(" ")
                            .append(addressesBean.getCountry()).append(" ").append(addressesBean.getPostcode());
                }
                break;

        }

        return sbAddress.toString();
    }

}
