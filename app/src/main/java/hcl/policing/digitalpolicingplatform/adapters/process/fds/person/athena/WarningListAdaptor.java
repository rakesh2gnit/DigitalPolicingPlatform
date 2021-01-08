package hcl.policing.digitalpolicingplatform.adapters.process.fds.person.athena;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.athena.AddressAthenaResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.PersonAthenaResponse;

public class WarningListAdaptor extends RecyclerView.Adapter<WarningListAdaptor.MyViewHolder> {

    private List<?> aWarningsBean;
    private int systemType;

    public WarningListAdaptor(Context context, List<?> list, int type) {
        this.aWarningsBean = list;
        this.systemType = type;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.warning_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        switch (systemType) {

            case GenericConstant.TYPE_PERSON:

                PersonAthenaResponse.WarningsBean warningsBean = (PersonAthenaResponse.WarningsBean) aWarningsBean.get(position);
                holder.tvWarningName.setText(warningsBean.getMarkervalue());
                holder.tvFromDate.setText(warningsBean.getFromdate());
                holder.tvToDate.setText(warningsBean.getTodate());
                holder.tvDescription.setText(warningsBean.getDescription());

                break;

            case GenericConstant.TYPE_ADDRESS:

                AddressAthenaResponse.WarningsBean athenaWarningsBean = (AddressAthenaResponse.WarningsBean) aWarningsBean.get(position);
                holder.tvWarningName.setText(athenaWarningsBean.getMarkervalue());
                holder.tvFromDate.setText(athenaWarningsBean.getFromdate());
                holder.tvToDate.setText(athenaWarningsBean.getTodate());
                holder.tvDescription.setText(athenaWarningsBean.getDescription());

                break;

        }


    }

    @Override
    public int getItemCount() {
        return aWarningsBean.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView tvWarningName, tvFromDate, tvToDate, tvDescription;

        private MyViewHolder(View view) {
            super(view);
            tvWarningName = view.findViewById(R.id.tvWarningName);
            tvFromDate = view.findViewById(R.id.tvFromDate);
            tvToDate = view.findViewById(R.id.tvToDate);
            tvDescription = view.findViewById(R.id.tvDescription);
        }

    }
}
