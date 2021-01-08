package hcl.policing.digitalpolicingplatform.adapters.process.allegation;

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
import hcl.policing.digitalpolicingplatform.listeners.process.allegation.AllegationSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.crime.OffenceDefinitionList;

public class AllegationListAdaptor extends RecyclerView.Adapter<AllegationListAdaptor.MyViewHolder> {

    private ArrayList<OffenceDefinitionList> allegationList;
    private int type;
    private TextView tvNoServiceExist;
    private AllegationSelectionListener allegationSelectionListener;
    private ItemClickListener itemClickListener;
    private Context mContext;

    public AllegationListAdaptor(Context context, ArrayList<OffenceDefinitionList> datalist,
                                 AllegationSelectionListener listener, ItemClickListener detailListener) {
        this.mContext = context;
        this.allegationList = datalist;
        this.allegationSelectionListener = listener;
        this.itemClickListener = detailListener;
    }

    @Override
    @NonNull
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_allegation, parent, false);
        return new MyViewHolder(itemView);
    }


    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OffenceDefinitionList offenceDefinitionList = allegationList.get(position);
                offenceDefinitionList.setSystem(GenericConstant.POLE);
                allegationSelectionListener.onAllegationSelected(offenceDefinitionList);
            }
        });
        if(allegationList.get(position).getCode() != null) {
            holder.tvCode.setText(allegationList.get(position).getCode());
        }
        if(allegationList.get(position).getStatute() != null) {
            holder.tvStatute.setText(allegationList.get(position).getStatute());
        }
        if(allegationList.get(position).getDescription() != null) {
            holder.tvAllegation.setText(allegationList.get(position).getDescription());
        }
    }

    @Override
    public int getItemCount() {
        return allegationList.size();
    }

    /**
     * MyViewHolder class
     */
    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvCode, tvStatute, tvAllegation;

        private MyViewHolder(View view) {
            super(view);

            tvCode = view.findViewById(R.id.tv_code);
            tvStatute = view.findViewById(R.id.tv_statute);
            tvAllegation = view.findViewById(R.id.tv_allegation);
        }
    }
}

