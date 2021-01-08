package hcl.policing.digitalpolicingplatform.adapters.process.fds.person.pole;

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
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.pole.PersonList;

public class PersonPoleListAdaptor extends RecyclerView.Adapter<PersonPoleListAdaptor.MyViewHolder> {

    private ArrayList<PersonList> aSearchPersonListModel;
    private int type;
    private TextView tvNoServiceExist;
    private PersonSelectionListener personSelectionListener;
    private ItemClickListener itemClickListener;
    private Context mContext;
    private boolean isPopulate;

    public PersonPoleListAdaptor(Context context, ArrayList<PersonList> datalist,
                                 PersonSelectionListener listener, ItemClickListener detailListener, boolean populate) {
        this.mContext = context;
        this.aSearchPersonListModel = datalist;
        this.personSelectionListener = listener;
        this.itemClickListener = detailListener;
        this.isPopulate = populate;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_pole_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PersonList personBean = aSearchPersonListModel.get(position);
        if (personBean.getMainName() != null) {
            holder.tvName.setText("" + personBean.getMainName());
        }
        if (personBean.getDateOfBirth() != null) {
            holder.tvDobValue.setText("" + personBean.getDateOfBirth());
        }
        if (personBean.getAge() != null) {
            holder.tvAge.setText("" + personBean.getAge());
        }
        if (personBean.getGender() != null) {
            holder.tvGenderValue.setText("" + personBean.getGender());
        }
        if (personBean.getMarker() != null) {
            holder.tvMarker.setText("" + personBean.getMarker());
        }
        if (personBean.getFlagText() != null) {
            holder.tvFlags.setText("" + personBean.getFlagText());
        }
        if (personBean.getPNCID() != null) {
            holder.tvPNCId.setText("" + personBean.getPNCID());
        }
        if (personBean.getOfficerDefinedEthnicity() != null) {
            holder.tvEthnicityValue.setText("" + personBean.getOfficerDefinedEthnicity());
        }
        if (personBean.getAddress() != null) {
            holder.tvAddress.setText("" + personBean.getAddress());
        }
    }

    @Override
    public int getItemCount() {
        return aSearchPersonListModel.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName, tvDobValue, tvAge, tvGenderValue, tvPNCId, tvMarker, tvFlags, tvEthnicityValue, tvAddress;
        private ImageView ivView;
        private LinearLayout llPerson;

        private MyViewHolder(View view) {
            super(view);
            ivView = view.findViewById(R.id.ivView);
            tvName = view.findViewById(R.id.tvName);
            tvDobValue = view.findViewById(R.id.tvDOBValue);
            tvAge = view.findViewById(R.id.tvAgeValue);
            tvGenderValue = view.findViewById(R.id.tvGenderValue);
            tvPNCId = view.findViewById(R.id.tvPNCId);
            tvMarker = view.findViewById(R.id.tvMarkerValue);
            tvFlags = view.findViewById(R.id.tvFlagsValue);
            tvEthnicityValue = view.findViewById(R.id.tvEthnicityValue);
            tvAddress = view.findViewById(R.id.tvAddressValue);
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
                    } else {
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
    private PersonBean getPersonDetail(PersonList person) {
        PersonBean personBean = null;
        if (person != null) {

            personBean = new PersonBean();
            String[] nameArray = person.getMainName().split("\\s+");

            if (nameArray.length == 1) {
                personBean.setFirstname(nameArray[0]);
                personBean.setLastname(nameArray[0]);
            } else if (nameArray.length == 2) {
                personBean.setFirstname(nameArray[0]);
                personBean.setLastname(nameArray[1]);

            } else if (nameArray.length > 2) {
                personBean.setFirstname(nameArray[0] + " " + nameArray[1]);
                personBean.setLastname(nameArray[2]);
            }
            personBean.setDob(person.getDateOfBirth());
            personBean.setAge(person.getAge());
            personBean.setOccupation("" + person.getOccupations().getID());
            personBean.setGender(person.getGender());
            personBean.setAddress(person.getMainAddress());
            personBean.setNationality(person.getNationality());
            personBean.setOfficerDefinedEthnicity(person.getOfficerDefinedEthnicity());
            personBean.setId(person.getId());
            personBean.setSystem(GenericConstant.POLE);
        }
        return personBean;
    }

}

