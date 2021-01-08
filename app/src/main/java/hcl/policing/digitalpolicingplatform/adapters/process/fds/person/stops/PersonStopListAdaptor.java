package hcl.policing.digitalpolicingplatform.adapters.process.fds.person.stops;

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
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.stops.SearchPersonListModel;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;

public class PersonStopListAdaptor extends RecyclerView.Adapter<PersonStopListAdaptor.MyViewHolder> {

    private ArrayList<SearchPersonListModel> aSearchPersonListModel;
    private int type;
    private TextView tvNoServiceExist;
    private PersonSelectionListener personSelectionListener;
    private ItemClickListener itemClickListener;
    private Context mContext;
    private boolean isPopulate;

    public PersonStopListAdaptor(Context context, ArrayList<SearchPersonListModel> datalist,
                                 PersonSelectionListener listener, ItemClickListener detailListener,boolean populate) {
        this.mContext = context;
        this.aSearchPersonListModel = datalist;
        this.personSelectionListener = listener;
        this.itemClickListener = detailListener;
        this.isPopulate = populate;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_stop_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        SearchPersonListModel personBean = aSearchPersonListModel.get(position);
        holder.tvName.setText(personBean.getFirstname() + " " + personBean.getLastname());
        holder.tvDobValue.setText(personBean.getDob());
        holder.tvGenderValue.setText(personBean.getGender());
        holder.tvEthnicityValue.setText(personBean.getEthnicity());

    }

    @Override
    public int getItemCount() {
        return aSearchPersonListModel.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName, tvDobValue, tvGenderValue, tvEthnicityValue;
        private ImageView ivView;
        private LinearLayout llPerson;

        private MyViewHolder(View view) {
            super(view);
            ivView = view.findViewById(R.id.ivView);
            tvName = view.findViewById(R.id.tvName);
            tvDobValue = view.findViewById(R.id.tvDobValue);
            tvGenderValue = view.findViewById(R.id.tvGenderValue);
            tvEthnicityValue = view.findViewById(R.id.tvEthnicityValue);
            llPerson = view.findViewById(R.id.llPerson);
            if (!isPopulate) {
                ivView.setVisibility(View.INVISIBLE);
            }
            llPerson.setOnClickListener(this);
            ivView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.llPerson:

                    if (isPopulate) {
                        PersonBean personBean = getPersonDetail(aSearchPersonListModel.get(getAdapterPosition()));
                        personSelectionListener.onPersonSelected(personBean);
                    }else {
                        itemClickListener.onItemClicked(GenericConstant.TYPE_STOPS);
                    }

                    break;

                case R.id.ivView:
                    itemClickListener.onItemClicked(GenericConstant.TYPE_STOPS);
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
    private PersonBean getPersonDetail(SearchPersonListModel person) {
        PersonBean personBean = null;
        if (person != null) {

            personBean = new PersonBean();
            personBean.setFirstname(person.getFirstname());
            personBean.setLastname(person.getLastname());
            personBean.setDob(person.getDob());
            personBean.setGender(person.getGender());
            personBean.setAddress(person.getEthnicity());
        }
        return personBean;
    }

}

