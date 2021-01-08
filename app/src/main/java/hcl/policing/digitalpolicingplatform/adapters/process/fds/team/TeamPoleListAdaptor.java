package hcl.policing.digitalpolicingplatform.adapters.process.fds.team;

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
import hcl.policing.digitalpolicingplatform.listeners.process.fds.team.TeamSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;
import hcl.policing.digitalpolicingplatform.models.process.fds.pole.PersonList;
import hcl.policing.digitalpolicingplatform.models.process.fds.team.TeamBean;

public class TeamPoleListAdaptor extends RecyclerView.Adapter<TeamPoleListAdaptor.MyViewHolder> {

    private ArrayList<PersonList> aSearchPersonListModel;
    private int type;
    private TextView tvNoServiceExist;
    private TeamSelectionListener teamSelectionListener;
    private ItemClickListener itemClickListener;
    private Context mContext;

    public TeamPoleListAdaptor(Context context, ArrayList<PersonList> datalist,
                               TeamSelectionListener listener, ItemClickListener detailListener) {
        this.mContext = context;
        this.aSearchPersonListModel = datalist;
        this.teamSelectionListener = listener;
        this.itemClickListener = detailListener;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_pole_item, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        PersonList personBean = aSearchPersonListModel.get(position);
        if(personBean.getMainName() != null) {
            holder.tvName.setText(""+personBean.getMainName());
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
        private TextView tvName;
        private ImageView ivView;
        private LinearLayout llPerson;

        private MyViewHolder(View view) {
            super(view);
            ivView = view.findViewById(R.id.ivView);
            tvName = view.findViewById(R.id.tvName);
            llPerson = view.findViewById(R.id.llPerson);

            llPerson.setOnClickListener(this);
            ivView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            switch (view.getId()) {

                case R.id.llPerson:


                    TeamBean personBean = getPersonDetail(aSearchPersonListModel.get(getAdapterPosition()));
                    teamSelectionListener.onTeamSelected(personBean);

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
    private TeamBean getPersonDetail(PersonList person) {
        TeamBean teamBean = null;
        if (person != null) {
            teamBean = new TeamBean();
            teamBean.setTeamname(person.getMainName());
            teamBean.setId(person.getId());
            teamBean.setSystem(GenericConstant.POLE);
        }
        return teamBean;
    }

}

