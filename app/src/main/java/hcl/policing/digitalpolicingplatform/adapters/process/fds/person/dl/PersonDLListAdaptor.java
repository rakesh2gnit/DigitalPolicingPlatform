package hcl.policing.digitalpolicingplatform.adapters.process.fds.person.dl;

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
import hcl.policing.digitalpolicingplatform.models.process.fds.person.dl.DrivinglicencebynamelistModel;

public class PersonDLListAdaptor extends RecyclerView.Adapter<PersonDLListAdaptor.MyViewHolder> {

    private List<DrivinglicencebynamelistModel> aPersonBean;
    private int type;
    private PersonSelectionListener personSelectionListener;
    private ItemClickListener itemClickListener;
    private Context mContext;
    private boolean isPopulate;

    public PersonDLListAdaptor(Context context, ArrayList<DrivinglicencebynamelistModel> datalist, PersonSelectionListener listener,
                               ItemClickListener detailListener, boolean populate) {
        this.mContext = context;
        this.aPersonBean = datalist;
        this.personSelectionListener = listener;
        this.itemClickListener = detailListener;
        this.isPopulate = populate;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_dl_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        DrivinglicencebynamelistModel personBean = aPersonBean.get(position);
        holder.tvName.setText(personBean.getName());
        holder.tvDOBValue.setText(personBean.getDob());
        holder.tvGenderValue.setText(personBean.getGender());
        holder.tvPlaceBirthValue.setText(personBean.getPlaceofbirth());
        holder.tvPostcodeValue.setText(personBean.getPostcode());
        holder.tvDrivingStatusValue.setText(personBean.getDisqualifieddriving());

    }

    @Override
    public int getItemCount() {
        return aPersonBean.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName, tvDOBValue, tvGenderValue, tvPlaceBirthValue, tvPostcodeValue, tvDrivingStatusValue;
        private ImageView ivPerson, ivView;
        private LinearLayout llPerson;

        private MyViewHolder(View view) {
            super(view);
            ivPerson = view.findViewById(R.id.ivPerson);
            ivView = view.findViewById(R.id.ivView);
            tvName = view.findViewById(R.id.tvName);
            tvDOBValue = view.findViewById(R.id.tvDOBValue);
            tvGenderValue = view.findViewById(R.id.tvGenderValue);
            tvPlaceBirthValue = view.findViewById(R.id.tvPlaceBirthValue);
            tvPostcodeValue = view.findViewById(R.id.tvPostcodeValue);
            tvDrivingStatusValue = view.findViewById(R.id.tvDrivingStatusValue);
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

                        PersonBean personBean = getPersonDetail(aPersonBean.get(getAdapterPosition()));
                        personSelectionListener.onPersonSelected(personBean);
                    } else {
                        itemClickListener.onItemClicked(GenericConstant.TYPE_DL);
                    }

                    break;

                case R.id.ivView:
                    itemClickListener.onItemClicked(GenericConstant.TYPE_DL);
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
    private PersonBean getPersonDetail(DrivinglicencebynamelistModel person) {
        PersonBean personBean = null;
        if (person != null) {

            personBean = new PersonBean();
            String[] nameArray = person.getName().split("\\s+");

            if (nameArray.length == 1) {
                personBean.setFirstname(nameArray[0]);
                personBean.setLastname(nameArray[0]);
            } else if (nameArray.length > 1) {
                personBean.setFirstname(nameArray[0]);
                personBean.setLastname(nameArray[1]);
            }
            personBean.setDob(person.getDob());
            personBean.setGender(person.getGender());
            personBean.setAddress(person.getPlaceofbirth());
        }
        return personBean;
    }


}
