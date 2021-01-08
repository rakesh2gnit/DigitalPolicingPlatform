package hcl.policing.digitalpolicingplatform.fragments.controlPannel;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.controlPannel.ControlPanelActivity;
import hcl.policing.digitalpolicingplatform.adapters.controlPannel.MediaAdapter;
import hcl.policing.digitalpolicingplatform.constants.contolPannel.MediaDataService;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;

public class MediaFragment extends Fragment implements View.OnTouchListener {

    Context context;
    private RecyclerView rvMedia;

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return false;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.media_fragment, container, false);
        context = getActivity();
        initToolbar();
        initView(view);
        setMediaList();
        return view;
    }

    private void initToolbar() {
        androidx.appcompat.app.ActionBar actionBar = ((ControlPanelActivity) context).getSupportActionBar();
        ((ControlPanelActivity) Objects.requireNonNull(getActivity())).createMenuButton(getResources().getString(R.string.media));
        /*ColorDrawable colorDrawable = new ColorDrawable();
        colorDrawable.setColor(ContextCompat.getColor(context, R.color.white));
        Objects.requireNonNull(actionBar).setBackgroundDrawable(colorDrawable);*/
        //Objects.requireNonNull(actionBar).setBackgroundDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.bg_toolbar));
        Objects.requireNonNull(actionBar).setDisplayHomeAsUpEnabled(true);
        actionBar.setDisplayShowTitleEnabled(false);
        //actionBar.setHomeAsUpIndicator(ContextCompat.getColor(getActivity(), R.drawable.arrow_back_white));
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initView(View view) {
        rvMedia = view.findViewById(R.id.rv_media);
        LinearLayoutManager mLayoutManagerTask = new LinearLayoutManager(context);
        rvMedia.setLayoutManager(mLayoutManagerTask);
        rvMedia.setItemAnimator(new DefaultItemAnimator());
    }

    private void setMediaList() {
        MediaAdapter mediaAdapter = new MediaAdapter(context, MediaDataService.getMediaData(), onClickItemMedia);
        rvMedia.setAdapter(mediaAdapter);
    }

    private OnItemClickListener.OnItemClickCallback onClickItemMedia = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };
}
