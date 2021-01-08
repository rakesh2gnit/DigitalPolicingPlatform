package hcl.policing.digitalpolicingplatform.fragments.tasking;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.adapters.tasking.AdditionalImageAdapter;
import hcl.policing.digitalpolicingplatform.adapters.tasking.AdditionalPersonAdapter;
import hcl.policing.digitalpolicingplatform.adapters.tasking.AdditionalVehicleAdapter;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskDetailResponseDTO;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.SnapHelperOneByOne;

public class FragmentAdditional extends Fragment {

    private AppSession appSession;
    private Context context;
    private RecyclerView rvPerson, rvVehicle, rvImage;
    private LinearLayout llImages;
    private List<TaskDetailResponseDTO.RelevantNominalDetail> relevantNominalDetails;
    private List<TaskDetailResponseDTO.RelevantVehicleDetail> relevantVehicleDetails;
    private List<TaskDetailResponseDTO.TaskImagesDetail> taskImagesDetails;
    private AdditionalPersonAdapter additionalPersonAdapter;
    private AdditionalVehicleAdapter additionalVehicleAdapter;
    private AdditionalImageAdapter additionalImageAdapter;
    private TaskDetailResponseDTO taskDetailResponse;
    private ImageView ivLeftPerson, ivRightPerson, ivLeftVehicle, ivRightVehicle;
    private LinearLayoutManager linearPerson, linearVehicle;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_additional_detail, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        context = getActivity();
        appSession = new AppSession(Objects.requireNonNull(context));
        initView(view);
        try {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    taskDetailResponse = new TaskDetailResponseDTO();
                    taskDetailResponse = appSession.getTaskDetail();
                    Log.e("SEssion ", ">>>>>> " + taskDetailResponse.getTask().getAim());

                    if (taskDetailResponse != null && !taskDetailResponse.equals("")) {
                        setData(taskDetailResponse);
                    }
                    Log.e("Called", "Additional >>>>>>");

                }
            }, 1000);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), FragmentAdditional.class, "onViewCreated");
        }
    }

    private void setData(TaskDetailResponseDTO model) {
        try {
            relevantNominalDetails = new ArrayList<>();
            relevantNominalDetails.addAll(model.getRelevantNominalDetails());

            relevantVehicleDetails = new ArrayList<>();
            relevantVehicleDetails.addAll(model.getRelevantVehicleDetails());

            taskImagesDetails = new ArrayList<>();
            taskImagesDetails.addAll(model.getTaskImagesDetails());

            if (relevantNominalDetails != null && relevantNominalDetails.size() > 0) {
                additionalPersonAdapter = new AdditionalPersonAdapter(context, relevantNominalDetails);
                rvPerson.setAdapter(additionalPersonAdapter);

                if (relevantNominalDetails.size() > 1) {
                    ivRightPerson.setVisibility(View.VISIBLE);
                } else {
                    ivRightPerson.setVisibility(View.GONE);
                    ivLeftPerson.setVisibility(View.GONE);
                }
            }

            if (relevantVehicleDetails != null && relevantVehicleDetails.size() > 0) {
                additionalVehicleAdapter = new AdditionalVehicleAdapter(context, relevantVehicleDetails);
                rvVehicle.setAdapter(additionalVehicleAdapter);

                if (relevantVehicleDetails.size() > 1) {
                    ivRightVehicle.setVisibility(View.VISIBLE);
                } else {
                    ivRightVehicle.setVisibility(View.GONE);
                    ivLeftVehicle.setVisibility(View.GONE);
                }
            }

            if (taskImagesDetails != null && taskImagesDetails.size() > 0) {
                llImages.setVisibility(View.VISIBLE);
                additionalImageAdapter = new AdditionalImageAdapter(context, taskImagesDetails);
                rvImage.setAdapter(additionalImageAdapter);
            } else {
                llImages.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), FragmentAdditional.class, "setData");
        }
    }

    /**
     * Initialize the views
     *
     * @param view
     */
    private void initView(View view) {
        llImages = view.findViewById(R.id.ll_images);

        ivLeftPerson = view.findViewById(R.id.iv_arrow_left_p);
        ivRightPerson = view.findViewById(R.id.iv_arrow_right_p);
        ivLeftVehicle = view.findViewById(R.id.iv_arrow_left_v);
        ivRightVehicle = view.findViewById(R.id.iv_arrow_right_v);

        rvPerson = view.findViewById(R.id.rv_personal);
        linearPerson = new LinearLayoutManager(context);
        linearPerson.setOrientation(RecyclerView.HORIZONTAL);
        rvPerson.setLayoutManager(linearPerson);
        rvPerson.setItemAnimator(new DefaultItemAnimator());
        LinearSnapHelper linearSnapHelper = new SnapHelperOneByOne();
        linearSnapHelper.attachToRecyclerView(rvPerson);
        rvPerson.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                int firstVisible = layoutManager.findFirstVisibleItemPosition();

                if (firstVisible == 0) {
                    ivLeftPerson.setVisibility(View.GONE);
                } else {
                    ivLeftPerson.setVisibility(View.VISIBLE);
                }

                if (lastVisible >= totalItemCount - 1) {
                    ivRightPerson.setVisibility(View.GONE);
                    //you have reached to the bottom of your recycler view
                } else {
                    ivRightPerson.setVisibility(View.VISIBLE);
                }
            }
        });
        ivRightPerson.setOnClickListener(v -> {
            if (linearPerson.findLastCompletelyVisibleItemPosition() < (additionalPersonAdapter.getItemCount() - 1)) {
                linearPerson.scrollToPosition(linearPerson.findLastCompletelyVisibleItemPosition() + 1);
                ivLeftPerson.setVisibility(View.VISIBLE);
            }

        });

        ivLeftPerson.setOnClickListener(v -> {
            if (linearPerson.findLastCompletelyVisibleItemPosition() > 0) {
                linearPerson.scrollToPosition(linearPerson.findLastCompletelyVisibleItemPosition() - 1);
                ivRightPerson.setVisibility(View.VISIBLE);
            }

        });

        rvVehicle = view.findViewById(R.id.rv_vehicle);
        linearVehicle = new LinearLayoutManager(context);
        linearVehicle.setOrientation(RecyclerView.HORIZONTAL);
        rvVehicle.setLayoutManager(linearVehicle);
        rvVehicle.setItemAnimator(new DefaultItemAnimator());
        LinearSnapHelper linearSnapHelper1 = new SnapHelperOneByOne();
        linearSnapHelper1.attachToRecyclerView(rvVehicle);
        rvVehicle.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                LinearLayoutManager layoutManager = LinearLayoutManager.class.cast(recyclerView.getLayoutManager());
                int totalItemCount = layoutManager.getItemCount();
                int lastVisible = layoutManager.findLastVisibleItemPosition();
                int firstVisible = layoutManager.findFirstVisibleItemPosition();

                if (firstVisible == 0) {
                    ivLeftVehicle.setVisibility(View.GONE);
                } else {
                    ivLeftVehicle.setVisibility(View.VISIBLE);
                }

                if (lastVisible >= totalItemCount - 1) {
                    ivRightVehicle.setVisibility(View.GONE);
                    //you have reached to the bottom of your recycler view
                } else {
                    ivRightVehicle.setVisibility(View.VISIBLE);
                }
            }
        });
        ivRightVehicle.setOnClickListener(v -> {
            if (linearVehicle.findLastCompletelyVisibleItemPosition() < (additionalVehicleAdapter.getItemCount() - 1)) {
                linearVehicle.scrollToPosition(linearVehicle.findLastCompletelyVisibleItemPosition() + 1);
                ivLeftVehicle.setVisibility(View.VISIBLE);
            }

        });
        ivLeftVehicle.setOnClickListener(v -> {
            if (linearVehicle.findLastCompletelyVisibleItemPosition() > 0) {
                linearVehicle.scrollToPosition(linearVehicle.findLastCompletelyVisibleItemPosition() - 1);
                ivRightVehicle.setVisibility(View.VISIBLE);
            }

        });

        rvImage = view.findViewById(R.id.rv_images);
        LinearLayoutManager mLayoutManager2 = new LinearLayoutManager(context);
        mLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        rvImage.setLayoutManager(mLayoutManager2);
        rvImage.setItemAnimator(new DefaultItemAnimator());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*TaskDetailActivity myActivity = (TaskDetailActivity) getActivity();
        myActivity.speakCancel();*/
        Log.e("Additional ", "DESTROYED ");
    }
}
