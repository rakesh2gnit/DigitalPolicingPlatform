package hcl.policing.digitalpolicingplatform.adapters.process.fds.person.athena;

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
import hcl.policing.digitalpolicingplatform.models.process.fds.person.athena.PersonAthenaResponse;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;

public class PersonAthenaListAdaptor extends RecyclerView.Adapter<PersonAthenaListAdaptor.MyViewHolder> {

    private List<PersonAthenaResponse.AthenaPersonlistBean> aPersonBean;
    private int type;
    private TextView tvNoServiceExist;
    private PersonSelectionListener personSelectionListener;
    private ItemClickListener itemClickListener;
    private Context mContext;
    private boolean isPopulate;

    public PersonAthenaListAdaptor(Context context, ArrayList<PersonAthenaResponse.AthenaPersonlistBean> datalist,
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
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.person_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PersonAthenaResponse.AthenaPersonlistBean personBean = aPersonBean.get(position);
        holder.tvName.setText(personBean.getFirstname1() + " " + personBean.getLastname());
        holder.tvDob.setText(personBean.getDob());
        holder.tvGender.setText(personBean.getGender());
        holder.tvAddress.setText(personBean.getLatesthomeaddress());

        List<PersonAthenaResponse.WarningsBean> aWarningsBean = personBean.getWarnings();
        if (aWarningsBean != null && aWarningsBean.size() > 0) {
            holder.ivWarninmg.setVisibility(View.VISIBLE);
            holder.tvBadgeCount.setVisibility(View.VISIBLE);
            holder.tvBadgeCount.setText(String.valueOf(personBean.getWarnings().size()));
        } else {
            holder.ivWarninmg.setVisibility(View.INVISIBLE);
            holder.tvBadgeCount.setVisibility(View.INVISIBLE);
        }

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
                    } else {
                        itemClickListener.onItemClicked(GenericConstant.TYPE_ATHENA);
                    }

                    break;

                case R.id.ivView:

                    itemClickListener.onItemClicked(GenericConstant.TYPE_ATHENA);

                    break;

                case R.id.ivWarninmg:

                    List<PersonAthenaResponse.WarningsBean> aWarningsBean = aPersonBean.get(getAdapterPosition()).getWarnings();

                    if (aWarningsBean != null && aWarningsBean.size() > 0) {

                        DialogUtil.showWarningDialog(mContext, aPersonBean.get(getAdapterPosition()).getWarnings(), GenericConstant.TYPE_PERSON);
                    } else {
                        BasicMethodsUtil.getInstance().showToast(mContext, "No Warnings available!!");
                    }
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
    private PersonBean getPersonDetail(PersonAthenaResponse.AthenaPersonlistBean person) {
        PersonBean personBean = null;
        if (person != null) {
            personBean = new PersonBean();
            personBean.setFirstname(person.getFirstname1());
            personBean.setLastname(person.getLastname());
            personBean.setDob(person.getDob());
            personBean.setGender(person.getGender());
            personBean.setAddress(person.getLatesthomeaddress());
        }
        return personBean;
    }

}
