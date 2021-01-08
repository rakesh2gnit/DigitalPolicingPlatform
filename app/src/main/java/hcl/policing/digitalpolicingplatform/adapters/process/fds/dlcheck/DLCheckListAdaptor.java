package hcl.policing.digitalpolicingplatform.adapters.process.fds.dlcheck;

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
import hcl.policing.digitalpolicingplatform.listeners.process.fds.dlchek.DlCheckSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.dl.DrivinglicencebyidlistModel;

public class DLCheckListAdaptor extends RecyclerView.Adapter<DLCheckListAdaptor.MyViewHolder> {

    private List<DrivinglicencebyidlistModel> aDrivinglicencebyidlistModel;
    private int type;
    private DlCheckSelectionListener dlCheckSelectionListener;
    private ItemClickListener itemClickListener;
    private boolean isPopulate;

    public DLCheckListAdaptor(ArrayList<DrivinglicencebyidlistModel> datalist, DlCheckSelectionListener listener,
                              ItemClickListener detailListener, boolean populate) {
        this.aDrivinglicencebyidlistModel = datalist;
        this.dlCheckSelectionListener = listener;
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

        DrivinglicencebyidlistModel drivinglicencebyidlistModel = aDrivinglicencebyidlistModel.get(position);
        holder.tvName.setText(drivinglicencebyidlistModel.getName());
        holder.tvDOBValue.setText(drivinglicencebyidlistModel.getDob());
        holder.tvGenderValue.setText(drivinglicencebyidlistModel.getGender());
        holder.tvPlaceBirthValue.setText(drivinglicencebyidlistModel.getBirthplace());
        holder.tvPostcodeValue.setText(drivinglicencebyidlistModel.getPostcode());
        holder.tvDrivingStatusValue.setText(drivinglicencebyidlistModel.getDisqualified());

    }

    @Override
    public int getItemCount() {
        return aDrivinglicencebyidlistModel.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName, tvDOBValue, tvGenderValue, tvPlaceBirthValue, tvPostcodeValue, tvDrivingStatusValue;

        private MyViewHolder(View view) {
            super(view);
            ImageView ivPerson = view.findViewById(R.id.ivPerson);
            ImageView ivView = view.findViewById(R.id.ivView);
            tvName = view.findViewById(R.id.tvName);
            tvDOBValue = view.findViewById(R.id.tvDOBValue);
            tvGenderValue = view.findViewById(R.id.tvGenderValue);
            tvPlaceBirthValue = view.findViewById(R.id.tvPlaceBirthValue);
            tvPostcodeValue = view.findViewById(R.id.tvPostcodeValue);
            tvDrivingStatusValue = view.findViewById(R.id.tvDrivingStatusValue);
            LinearLayout llPerson = view.findViewById(R.id.llPerson);

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
                        PersonBean personBean = getPersonDetail(aDrivinglicencebyidlistModel.get(getAdapterPosition()));
                        dlCheckSelectionListener.onDlCheckSelected(personBean);
                    } else {
                        itemClickListener.onItemClicked(GenericConstant.TYPE_DL_CHECK);
                    }

                    break;

                case R.id.ivView:
                    itemClickListener.onItemClicked(GenericConstant.TYPE_DL_CHECK);
                    break;


            }

        }
    }

    /**
     * get DL Check Details
     *
     * @param person
     * @return
     */
    private PersonBean getPersonDetail(DrivinglicencebyidlistModel person) {
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
            personBean.setAddress(person.getBirthplace());
        }
        return personBean;
    }


}

