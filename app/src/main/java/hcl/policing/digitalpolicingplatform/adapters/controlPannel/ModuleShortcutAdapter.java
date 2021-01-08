package hcl.policing.digitalpolicingplatform.adapters.controlPannel;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.database.ProcessSubProcessMapper;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;

public class ModuleShortcutAdapter extends RecyclerView.Adapter<ModuleShortcutAdapter.MyViewHolder> {

    private Context mContext;
    private OnItemClickListener.OnItemClickCallback onClickItem;
    private ArrayList<ProcessSubProcessMapper> aProcessSubProcessMapper;


    public ModuleShortcutAdapter(Context mContext, ArrayList<ProcessSubProcessMapper> list,
                                 OnItemClickListener.OnItemClickCallback onClickItem) {
        this.mContext = mContext;
        this.aProcessSubProcessMapper = list;
        this.onClickItem = onClickItem;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_module_shortcut, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull final MyViewHolder holder, final int position) {

        ProcessSubProcessMapper mProcessSubProcessMapper = aProcessSubProcessMapper.get(position);

        holder.tvName.setText(mProcessSubProcessMapper.getProcessName());


        /*String iconName = mProcessSubProcessMapper.getProcessIcon() + "_white";
        int drawableId = mContext.getResources().getIdentifier(iconName, "drawable", mContext.getPackageName());
        holder.ivIcon.setImageResource(drawableId);*/
        String iconUrl=mProcessSubProcessMapper.getProcessIcon()+".jpg";
        Picasso.get().load(iconUrl).error(R.drawable.ic_search_blue).placeholder(R.drawable.ic_search_white).into(holder.ivIcon);

        if (mProcessSubProcessMapper.getShortcutStatus() == GenericConstant.SHORTCUT_YES) {
            holder.ivCheck.setImageResource(R.drawable.ic_check_box_checked);
        } else {
            holder.ivCheck.setImageResource(R.drawable.ic_check_box_unchecked);
        }

        holder.itemView.setOnClickListener(new OnItemClickListener(position, onClickItem));
    }


    @Override
    public int getItemCount() {
        return aProcessSubProcessMapper.size();
    }


    /**
     * MyViewHolder class
     */
    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvName;
        ImageView ivIcon, ivCheck;

        MyViewHolder(View view) {
            super(view);
            tvName = view.findViewById(R.id.tv_name);
            ivIcon = view.findViewById(R.id.iv_icon);
            ivCheck = view.findViewById(R.id.iv_check);
        }
    }
}
