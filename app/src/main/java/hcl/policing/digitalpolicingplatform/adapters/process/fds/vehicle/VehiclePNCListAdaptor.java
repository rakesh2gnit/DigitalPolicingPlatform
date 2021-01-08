package hcl.policing.digitalpolicingplatform.adapters.process.fds.vehicle;

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
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.vehicle.VehicleSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.pole.VehicleList;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.VehicleBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.athena.VehicleAthenaResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.pnc.VehiclePNCResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.stops.VehicleSTOPResponse;

public class VehiclePNCListAdaptor extends RecyclerView.Adapter<VehiclePNCListAdaptor.MyViewHolder> {

    private List<?> aVehicleBean;
    private int systemType;
    private VehicleSelectionListener vehicleSelectionListener;
    private ItemClickListener itemClickListener;
    private Context mContext;
    private boolean isPopulate;

    public VehiclePNCListAdaptor(Context context, int type, ArrayList<?> datalist, VehicleSelectionListener listener,
                                 ItemClickListener itemListener,boolean populate) {
        this.mContext = context;
        this.systemType = type;
        this.aVehicleBean = datalist;
        this.vehicleSelectionListener = listener;
        this.itemClickListener = itemListener;
        this.isPopulate=populate;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.vehicle_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        switch (systemType) {

            case GenericConstant.TYPE_PNC:
                holder.layoutLost.setVisibility(View.VISIBLE);
                VehiclePNCResponse.MotorvehiclelistBean motorvehiclelistBean = (VehiclePNCResponse.MotorvehiclelistBean) aVehicleBean.get(position);
                holder.tvVRMNo.setText(motorvehiclelistBean.getNumberplate());
                holder.tvMakeValue.setText(motorvehiclelistBean.getMake());
                holder.tvModelValue.setText(motorvehiclelistBean.getModel());
                holder.tvColorValue.setText(motorvehiclelistBean.getColour());
                holder.tvlostValue.setText(motorvehiclelistBean.getStolen());

                break;

            case GenericConstant.TYPE_ATHENA:

                holder.layoutLost.setVisibility(View.GONE);
                VehicleAthenaResponse.VehicleListBean vehicleListBean = (VehicleAthenaResponse.VehicleListBean) aVehicleBean.get(position);
                holder.tvVRMNo.setText(vehicleListBean.getRegistrationnumber());
                holder.tvMakeValue.setText(vehicleListBean.getMake());
                holder.tvModelValue.setText(vehicleListBean.getModel());
                holder.tvColorValue.setText(vehicleListBean.getVehiclecolour());

                break;

            case GenericConstant.TYPE_STOPS:

                holder.layoutLost.setVisibility(View.GONE);
                VehicleSTOPResponse.SearchVehicleListBean searchVehicleListBean = (VehicleSTOPResponse.SearchVehicleListBean) aVehicleBean.get(position);
                holder.tvVRMNo.setText(searchVehicleListBean.getVrm());
                holder.tvMakeValue.setText(searchVehicleListBean.getMake());
                holder.tvModelValue.setText(searchVehicleListBean.getModel());
                holder.tvColorValue.setText(searchVehicleListBean.getColour());

                break;

            case GenericConstant.TYPE_POLE:
                holder.layoutLost.setVisibility(View.VISIBLE);
                VehicleList vehicleList = (VehicleList) aVehicleBean.get(position);
                holder.tvVRMNo.setText(vehicleList.getVRM());
                holder.tvMakeValue.setText(vehicleList.getMake());
                holder.tvModelValue.setText(vehicleList.getModel());
                holder.tvColorValue.setText(vehicleList.getColor1());
                holder.tvlostValue.setText(vehicleList.getLDS());

                break;
        }


    }

    @Override
    public int getItemCount() {
        return aVehicleBean.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvVRMNo, tvMakeValue, tvModelValue, tvColorValue, tvlostValue;
        private ImageView ivView;
        private LinearLayout llVehicle, layoutLost;

        private MyViewHolder(View view) {
            super(view);
            ivView = view.findViewById(R.id.ivView);
            tvVRMNo = view.findViewById(R.id.tvVRMNo);
            tvMakeValue = view.findViewById(R.id.tvMakeValue);
            tvModelValue = view.findViewById(R.id.tvModelValue);
            tvColorValue = view.findViewById(R.id.tvColorValue);
            tvlostValue = view.findViewById(R.id.tvlostValue);
            llVehicle = view.findViewById(R.id.llVehicle);
            layoutLost = view.findViewById(R.id.layoutLost);

            if (!isPopulate){
                ivView.setVisibility(View.INVISIBLE);
            }

            llVehicle.setOnClickListener(this);
            ivView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.llVehicle:

                    if (isPopulate){
                        VehicleBean vehicleBean = getVehicleDetail(aVehicleBean.get(getAdapterPosition()));
                        vehicleSelectionListener.onVehicleSelected(vehicleBean);
                    }else {
                        itemClickListener.onItemClicked(GenericConstant.TYPE_ATHENA);
                    }

                    break;

                case R.id.ivView:

                    itemClickListener.onItemClicked(GenericConstant.TYPE_ATHENA);

                    break;


            }

        }
    }


    /**
     * get Vehicle Details
     *
     * @param object
     * @return
     */
    private VehicleBean getVehicleDetail(Object object) {

        VehicleBean vehicleBean = null;
        switch (systemType) {

            case GenericConstant.TYPE_PNC:
                VehiclePNCResponse.MotorvehiclelistBean motorvehiclelistBean = (VehiclePNCResponse.MotorvehiclelistBean) object;
                vehicleBean = new VehicleBean();
                vehicleBean.setVrm(motorvehiclelistBean.getNumberplate());
                vehicleBean.setMake(motorvehiclelistBean.getMake());
                vehicleBean.setModel(motorvehiclelistBean.getModel());
                vehicleBean.setColor_primary(motorvehiclelistBean.getColour());
                vehicleBean.setColor_secondary(motorvehiclelistBean.getColour());
                vehicleBean.setSystem(GenericConstant.PNC);

                break;

            case GenericConstant.TYPE_ATHENA:
                VehicleAthenaResponse.VehicleListBean vehicleListBean = (VehicleAthenaResponse.VehicleListBean) object;
                vehicleBean = new VehicleBean();
                vehicleBean.setVrm(vehicleListBean.getRegistrationnumber());
                vehicleBean.setMake(vehicleListBean.getMake());
                vehicleBean.setModel(vehicleListBean.getModel());
                vehicleBean.setColor_primary(vehicleListBean.getVehiclecolour());
                vehicleBean.setColor_secondary(vehicleListBean.getVehiclecolour());
                vehicleBean.setId(vehicleListBean.getId());
                vehicleBean.setSystem(GenericConstant.ATHENA);

                break;

            case GenericConstant.TYPE_STOPS:

                VehicleSTOPResponse.SearchVehicleListBean searchVehicleListBean = (VehicleSTOPResponse.SearchVehicleListBean) object;
                vehicleBean = new VehicleBean();
                vehicleBean.setVrm(searchVehicleListBean.getVrm());
                vehicleBean.setMake(searchVehicleListBean.getMake());
                vehicleBean.setModel(searchVehicleListBean.getModel());
                vehicleBean.setColor_primary(searchVehicleListBean.getColour());
                vehicleBean.setColor_secondary(searchVehicleListBean.getColour());
                vehicleBean.setId(searchVehicleListBean.getVehicleid());
                vehicleBean.setSystem(GenericConstant.STOPS);

                break;

            case GenericConstant.TYPE_POLE:

                VehicleList vehicleList = (VehicleList) object;
                vehicleBean = new VehicleBean();
                vehicleBean.setVrm(vehicleList.getVRM());
                vehicleBean.setMake(vehicleList.getMake());
                vehicleBean.setModel(vehicleList.getModel());
                vehicleBean.setColor_primary(vehicleList.getColor1());
                vehicleBean.setColor_secondary(vehicleList.getColor1());
                vehicleBean.setId(vehicleList.getID());
                vehicleBean.setSystem(GenericConstant.POLE);

                break;
        }

        return vehicleBean;
    }

}
