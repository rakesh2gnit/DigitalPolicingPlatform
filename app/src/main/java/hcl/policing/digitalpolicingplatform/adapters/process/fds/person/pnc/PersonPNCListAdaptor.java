package hcl.policing.digitalpolicingplatform.adapters.process.fds.person.pnc;

import android.content.Context;
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
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc.PersonPNCResponse;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;

public class PersonPNCListAdaptor extends RecyclerView.Adapter<PersonPNCListAdaptor.MyViewHolder> {

    private List<PersonPNCResponse.PNCPersonlistBean> aPersonBean;
    private int type;
    private PersonSelectionListener personSelectionListener;
    private ItemClickListener itemClickListener;
    private Context mContext;
    private boolean isPopulate;

    public PersonPNCListAdaptor(Context context, ArrayList<PersonPNCResponse.PNCPersonlistBean> datalist, PersonSelectionListener listener,
                                ItemClickListener detailListener,boolean populate) {
        this.mContext = context;
        this.aPersonBean = datalist;
        this.personSelectionListener = listener;
        this.itemClickListener = detailListener;
        this.isPopulate = populate;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PersonPNCResponse.PNCPersonlistBean personBean = aPersonBean.get(position);
        holder.tvName.setText(personBean.getFirstname() + " " + personBean.getLastname());
        holder.tvDob.setText(personBean.getDob());
        holder.tvGender.setText(personBean.getGender());
        holder.tvAddress.setText(personBean.getPlaceofbirth());


    }

    @Override
    public int getItemCount() {
        return aPersonBean.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName, tvDob, tvGender, tvAddress, tvBadgeCount;
        private ImageView ivPerson, ivView, ivWarninmg;
        private LinearLayout llPerson;
        private RelativeLayout rlBadge;

        private MyViewHolder(View view) {
            super(view);
            ivPerson = view.findViewById(R.id.ivPerson);
            ivView = view.findViewById(R.id.ivView);
            ivWarninmg = view.findViewById(R.id.ivWarninmg);
            tvName = view.findViewById(R.id.tvName);
            tvDob = view.findViewById(R.id.tvDob);
            tvGender = view.findViewById(R.id.tvGender);
            tvAddress = view.findViewById(R.id.tvAddress);
            tvBadgeCount = view.findViewById(R.id.tvBadgeCount);
            rlBadge = view.findViewById(R.id.rlBadge);
            llPerson = view.findViewById(R.id.llPerson);
            if (!isPopulate) {
                ivView.setVisibility(View.INVISIBLE);
            }
            llPerson.setOnClickListener(this);
            ivView.setOnClickListener(this);
            ivWarninmg.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.llPerson:

                    if (isPopulate) {
                        PersonBean personBean = getPersonDetail(aPersonBean.get(getAdapterPosition()));
                        personSelectionListener.onPersonSelected(personBean);
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
     * get Person Details
     *
     * @param person
     * @return
     */
    private PersonBean getPersonDetail(PersonPNCResponse.PNCPersonlistBean person) {
        PersonBean personBean = null;
        if (person != null) {
            personBean = new PersonBean();
            personBean.setFirstname(person.getFirstname());
            personBean.setLastname(person.getLastname());
            personBean.setDob(person.getDob());
            personBean.setGender(person.getGender());
            personBean.setAddress(person.getPlaceofbirth());
            personBean.setOfficerDefinedEthnicity(person.getEthnicity());
            personBean.setSelfDefinedEthnicity(person.getEthnicity());
            personBean.setId(person.getPncid());
            personBean.setSystem(GenericConstant.PNC);
        }
        return personBean;
    }


}
