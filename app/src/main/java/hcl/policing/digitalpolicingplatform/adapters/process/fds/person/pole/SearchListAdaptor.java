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
import hcl.policing.digitalpolicingplatform.models.searchList.LabelSection;

public class SearchListAdaptor extends RecyclerView.Adapter<SearchListAdaptor.MyViewHolder> {

    private ArrayList<LabelSection> aSearchPersonListModel;
    private int type;
    private TextView tvNoServiceExist;
    private PersonSelectionListener personSelectionListener;
    private ItemClickListener itemClickListener;
    private Context mContext;
    private boolean isPopulate;

    public SearchListAdaptor(Context context, ArrayList<LabelSection> datalist,
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_list_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        LabelSection labelSection = aSearchPersonListModel.get(position);
        /*for (int i = 0; i < aSearchPersonListModel.get(position).getLabelSectionDetails().size(); i++) {
           holder.tvName.setText("" + labelSection.getLabelSectionDetails().get(position).getValue());
        }*/
        holder.tvName.setText("" + labelSection.getLabelSectionDetails().get(0).getValue());
        holder.tvDobValue.setText("" + labelSection.getLabelSectionDetails().get(1).getValue());
        holder.tvAge.setText("" + labelSection.getLabelSectionDetails().get(2).getValue());
        holder.tvGenderValue.setText("" + labelSection.getLabelSectionDetails().get(3).getValue());
        holder.tvMarker.setText("" + labelSection.getLabelSectionDetails().get(4).getValue());
        holder.tvFlags.setText("" + labelSection.getLabelSectionDetails().get(5).getValue());
        holder.tvPNCId.setText("" + labelSection.getLabelSectionDetails().get(6).getValue());
        holder.tvEthnicityValue.setText("" + labelSection.getLabelSectionDetails().get(7).getValue());
        holder.tvAddress.setText("" + labelSection.getLabelSectionDetails().get(8).getName());
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
            //ivView = view.findViewById(R.id.ivView);
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
                //ivView.setVisibility(View.INVISIBLE);
            }
            llPerson.setOnClickListener(this);
            //ivView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.llPerson:

                    if (isPopulate) {
                        //LabelSection personBean = getPersonDetail(aSearchPersonListModel.get(getAdapterPosition()));
                        //personSelectionListener.onPersonSelected(personBean);
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

