package hcl.policing.digitalpolicingplatform.adapters.process.fds.person.qas;

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
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.qas.AddressdetailsModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.qas.CitizensModel;
import hcl.policing.digitalpolicingplatform.utils.LocationUtil;

public class PersonQASListAdaptor extends RecyclerView.Adapter<PersonQASListAdaptor.MyViewHolder> {

    private List<CitizensModel> aCitizensModel;
    private PersonSelectionListener personSelectionListener;
    private ItemClickListener itemClickListener;
    private Context mContext;
    private boolean isPopulate;

    public PersonQASListAdaptor(Context context, ArrayList<CitizensModel> datalist, PersonSelectionListener listener,
                                ItemClickListener detailListener, boolean populate) {
        this.mContext = context;
        this.aCitizensModel = datalist;
        this.personSelectionListener = listener;
        this.itemClickListener = detailListener;
        this.isPopulate = populate;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_qas_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        CitizensModel citizensModel = aCitizensModel.get(position);
        holder.tvName.setText(citizensModel.getDisplayname());
        String address = getAddress(citizensModel);
        holder.tvAddress.setText(address);

    }

    @Override
    public int getItemCount() {
        return aCitizensModel.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName, tvAddress;
        private ImageView ivView, ivNavigate;
        private LinearLayout llPerson;

        private MyViewHolder(View view) {
            super(view);
            ivView = view.findViewById(R.id.ivView);
            tvName = view.findViewById(R.id.tvName);
            tvAddress = view.findViewById(R.id.tvAddress);
            ivNavigate = view.findViewById(R.id.ivNavigate);
            llPerson = view.findViewById(R.id.llPerson);
            if (!isPopulate) {
                ivView.setVisibility(View.INVISIBLE);
            }
            llPerson.setOnClickListener(this);
            ivView.setOnClickListener(this);
            ivNavigate.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.llPerson:

                    if (isPopulate) {
                        PersonBean personBean = getPersonDetail(aCitizensModel.get(getAdapterPosition()));
                        personSelectionListener.onPersonSelected(personBean);
                    }else {
                        itemClickListener.onItemClicked(GenericConstant.TYPE_QAS);
                    }

                    break;

                case R.id.ivView:

                    itemClickListener.onItemClicked(GenericConstant.TYPE_QAS);

                    break;

                case R.id.ivNavigate:
                    String address = getAddress(aCitizensModel.get(getAdapterPosition()));
                    LocationUtil.getInstance().navigateTo(mContext, address);

                    break;

            }

        }
    }


    /**
     * get Person Details
     *
     * @param person
     * @return
     */
    private PersonBean getPersonDetail(CitizensModel person) {
        PersonBean personBean = null;
        if (person != null) {

            personBean = new PersonBean();
            personBean.setTitle(person.getTitle());
            personBean.setFirstname(person.getCitizennameforename());
            personBean.setLastname(person.getCitizennamesurname());
            personBean.setAddress(getAddress(person));
            personBean.setSystem(GenericConstant.QAS);
        }
        return personBean;
    }


    /**
     * get Address from person address
     *
     * @param person
     * @return
     */
    private String getAddress(CitizensModel person) {
        StringBuilder sbAddress = new StringBuilder();

        AddressdetailsModel addressdetailsBean = person.getAddressdetails();
        if (addressdetailsBean != null) {

            sbAddress.append(addressdetailsBean.getHouseno()).append(",").append(addressdetailsBean.getStreet()).append("\n")
                    .append(addressdetailsBean.getLocation()).append(" ").append(addressdetailsBean.getCity()).append("\n")
                    .append(addressdetailsBean.getPostcode());

        }

        return sbAddress.toString();
    }

}
