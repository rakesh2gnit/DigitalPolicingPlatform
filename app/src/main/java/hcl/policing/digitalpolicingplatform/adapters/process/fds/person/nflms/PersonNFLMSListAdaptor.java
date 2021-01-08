package hcl.policing.digitalpolicingplatform.adapters.process.fds.person.nflms;

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
import hcl.policing.digitalpolicingplatform.models.process.fds.person.nflms.PersonNFLMSResponse;
import hcl.policing.digitalpolicingplatform.utils.LocationUtil;

public class PersonNFLMSListAdaptor extends RecyclerView.Adapter<PersonNFLMSListAdaptor.MyViewHolder> {

    private List<PersonNFLMSResponse.SearchResultBean> aPersonBean;
    private int type;
    private TextView tvNoServiceExist;
    private PersonSelectionListener personSelectionListener;
    private ItemClickListener itemClickListener;
    private Context mContext;
    private boolean isPopulate;

    public PersonNFLMSListAdaptor(Context context, ArrayList<PersonNFLMSResponse.SearchResultBean> datalist,
                                  PersonSelectionListener listener, ItemClickListener detailListener, boolean populate) {
        this.mContext = context;
        this.aPersonBean = datalist;
        this.personSelectionListener = listener;
        this.itemClickListener = detailListener;
        this.isPopulate = populate;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_nflms_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PersonNFLMSResponse.SearchResultBean personBean = aPersonBean.get(position);
        holder.tvName.setText(personBean.getFirstname() + " " + personBean.getSurname());
        holder.tvLicenceNo.setText(personBean.getLicencenumber());
        holder.tvValidity.setText(personBean.getValidto());
        holder.tvAddress.setText(personBean.getAddress());
        holder.tvWeaponType.setText(personBean.getWeapontype());

    }

    @Override
    public int getItemCount() {
        return aPersonBean.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView tvName, tvLicenceNo, tvValidity, tvAddress, tvWeaponType;
        private ImageView ivView, ivNavigate;
        private LinearLayout llPerson;

        private MyViewHolder(View view) {
            super(view);
            ivView = view.findViewById(R.id.ivView);
            ivNavigate = view.findViewById(R.id.ivNavigate);
            tvName = view.findViewById(R.id.tvName);
            tvLicenceNo = view.findViewById(R.id.tvLicenceNo);
            tvValidity = view.findViewById(R.id.tvValidity);
            tvAddress = view.findViewById(R.id.tvAddress);
            tvWeaponType = view.findViewById(R.id.tvWeaponType);
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
                        PersonBean personBean = getPersonDetail(aPersonBean.get(getAdapterPosition()));
                        personSelectionListener.onPersonSelected(personBean);
                    } else {
                        itemClickListener.onItemClicked(GenericConstant.TYPE_ATHENA);
                    }


                    break;

                case R.id.ivView:

                    itemClickListener.onItemClicked(GenericConstant.TYPE_ATHENA);

                    break;

                case R.id.ivNavigate:

                    LocationUtil.getInstance().navigateTo(mContext, aPersonBean.get(getAdapterPosition()).getAddress());

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
    private PersonBean getPersonDetail(PersonNFLMSResponse.SearchResultBean person) {
        PersonBean personBean = null;
        if (person != null) {

            personBean = new PersonBean();
            personBean.setFirstname(person.getFirstname());
            personBean.setLastname(person.getSurname());
            personBean.setDob(person.getDob());
            personBean.setGender(person.getWeapontype());
            personBean.setAddress(person.getAddress());
        }
        return personBean;
    }

}
