package hcl.policing.digitalpolicingplatform.adapters.process.organisation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.organisation.OrganisationSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.crime.OffenceDefinitionList;
import hcl.policing.digitalpolicingplatform.models.process.crime.OrganisationDetailsList;

public class OrganisationListAdaptor extends RecyclerView.Adapter<OrganisationListAdaptor.MyViewHolder> {

    private ArrayList<OrganisationDetailsList> organisationList;
    private int type;
    private TextView tvNoServiceExist;
    private OrganisationSelectionListener organisationSelectionListener;
    private ItemClickListener itemClickListener;
    private Context mContext;

    public OrganisationListAdaptor(Context context, ArrayList<OrganisationDetailsList> datalist,
                                   OrganisationSelectionListener listener, ItemClickListener detailListener) {
        this.mContext = context;
        this.organisationList = datalist;
        this.organisationSelectionListener = listener;
        this.itemClickListener = detailListener;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_organisation, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OrganisationDetailsList organisationDetailsList = organisationList.get(position);
                organisationDetailsList.setSystem(GenericConstant.POLE);
                organisationSelectionListener.onOrganisationSelected(organisationDetailsList);
            }
        });
        if(organisationList.get(position).getOrganisationName() != null) {
            holder.tvName.setText(organisationList.get(position).getOrganisationName());
        }
        if(organisationList.get(position).getCompanyNumber() != null) {
            holder.tvNumber.setText(organisationList.get(position).getCompanyNumber());
        }
        if(organisationList.get(position).getContact().getMainPhoneNumber() != null) {
            holder.tvContact.setText(organisationList.get(position).getContact().getMainPhoneNumber());
        }
        if(organisationList.get(position).getContact().getMainEmail() != null) {
            holder.tvEmail.setText(organisationList.get(position).getContact().getMainEmail());
        }
    }

    @Override
    public int getItemCount() {
        return organisationList.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvNumber, tvContact, tvEmail;

        private MyViewHolder(View view) {
            super(view);

            tvName = view.findViewById(R.id.tv_name);
            tvNumber = view.findViewById(R.id.tv_number);
            tvContact = view.findViewById(R.id.tv_contact_number);
            tvEmail = view.findViewById(R.id.tv_email);
        }
    }
}

