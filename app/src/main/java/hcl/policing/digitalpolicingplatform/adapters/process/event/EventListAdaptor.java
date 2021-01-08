package hcl.policing.digitalpolicingplatform.adapters.process.event;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.CustomCommonProperties;
import hcl.policing.digitalpolicingplatform.listeners.process.event.EventSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.crime.EventSearchList;

public class EventListAdaptor extends RecyclerView.Adapter<EventListAdaptor.MyViewHolder> {

    private ArrayList<EventSearchList> eventSearchList;
    private int type;
    private TextView tvNoServiceExist;
    private EventSelectionListener eventSelectionListener;
    private ItemClickListener itemClickListener;
    private Context mContext;

    public EventListAdaptor(Context context, ArrayList<EventSearchList> datalist,
                            EventSelectionListener listener, ItemClickListener detailListener) {
        this.mContext = context;
        this.eventSearchList = datalist;
        this.eventSelectionListener = listener;
        this.itemClickListener = detailListener;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_event, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.tvViewMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvViewMore.setVisibility(View.GONE);
                holder.tvViewLess.setVisibility(View.VISIBLE);
                CustomCommonProperties customCommonProperties = new CustomCommonProperties();
                customCommonProperties.setHeight(mContext, holder.llAdd, 0);
            }
        });
        holder.tvViewLess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.tvViewMore.setVisibility(View.VISIBLE);
                holder.tvViewLess.setVisibility(View.GONE);

                CustomCommonProperties customCommonProperties = new CustomCommonProperties();
                customCommonProperties.setHeight(mContext, holder.llAdd, 75);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EventSearchList eventList = eventSearchList.get(position);
                eventList.setSystem(GenericConstant.POLE);
                eventSelectionListener.onEventSelected(eventList);
            }
        });
        if(eventSearchList.get(position).getURN() != null) {
            holder.tvUrn.setText(eventSearchList.get(position).getURN());
        }
        if(eventSearchList.get(position).getCategory() != null) {
            holder.tvCategory.setText(eventSearchList.get(position).getCategory());
        }
        if(eventSearchList.get(position).getClassification() != null) {
            holder.tvClassification.setText(eventSearchList.get(position).getClassification());
        }
        if(eventSearchList.get(position).getIncidentDescription() != null) {
            holder.tvDescription.setText(eventSearchList.get(position).getIncidentDescription());
        }
        if(eventSearchList.get(position).getLocation() != null) {
            holder.tvLocation.setText(eventSearchList.get(position).getLocation());
        }
        if(eventSearchList.get(position).getStage() != null) {
            holder.tvStage.setText(eventSearchList.get(position).getStage());
        }
        if(eventSearchList.get(position).getEventMemberName() != null) {
            holder.tvSubject.setText(eventSearchList.get(position).getEventMemberName());
        }
        if(eventSearchList.get(position).getRecordingPerson() != null) {
            holder.tvOfficer.setText(eventSearchList.get(position).getRecordingPerson());
        }
        if(eventSearchList.get(position).getReportedOn() != null) {
            holder.tvReported.setText(eventSearchList.get(position).getReportedOn());
        }
        if(eventSearchList.get(position).getDivision() != null) {
            holder.tvDivision.setText(eventSearchList.get(position).getDivision());
        }
        if(eventSearchList.get(position).getState() != null) {
            holder.tvState.setText(eventSearchList.get(position).getState());
            if(eventSearchList.get(position).getState().equalsIgnoreCase("Open")) {
                holder.tvState.setTextColor(ContextCompat.getColor(mContext, R.color.green));
            }
        }
        if(eventSearchList.get(position).getLDS() != null) {
            holder.tvLds.setText(eventSearchList.get(position).getLDS());
        }
    }

    @Override
    public int getItemCount() {
        return eventSearchList.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvUrn, tvCategory, tvClassification, tvDescription, tvLocation, tvStage,
            tvSubject, tvOfficer, tvReported, tvDivision, tvState, tvLds;

        TextView tvViewMore, tvViewLess;
        LinearLayout llAdd;

        private MyViewHolder(View view) {
            super(view);
            llAdd = view.findViewById(R.id.ll_add_fields);
            tvViewMore = view.findViewById(R.id.tv_view_more);
            tvViewLess = view.findViewById(R.id.tv_view_less);

            tvUrn = view.findViewById(R.id.tv_urn);
            tvCategory = view.findViewById(R.id.tv_category);
            tvClassification = view.findViewById(R.id.tv_classification);
            tvDescription = view.findViewById(R.id.tv_incident);
            tvLocation = view.findViewById(R.id.tv_location);
            tvStage = view.findViewById(R.id.tv_stage);
            tvSubject = view.findViewById(R.id.tv_subject);
            tvOfficer = view.findViewById(R.id.tv_officer);
            tvReported = view.findViewById(R.id.tv_reported);
            tvDivision = view.findViewById(R.id.tv_division);
            tvState = view.findViewById(R.id.tv_state);
            tvLds = view.findViewById(R.id.tv_lds);
        }
    }
}

