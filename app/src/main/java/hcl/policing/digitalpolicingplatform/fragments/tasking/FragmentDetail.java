package hcl.policing.digitalpolicingplatform.fragments.tasking;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.models.tasking.TaskDetailResponseDTO;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.GPSTracker;
import hcl.policing.digitalpolicingplatform.utils.TimeConverterUtil;

public class FragmentDetail extends Fragment implements OnMapReadyCallback, View.OnClickListener {

    private Marker destMarker;
    private GoogleMap googleMap;
    private Context context;
    private double toLat, toLng;
    private AppSession appSession;
    private TextView tvTaskType, tvExpiryDate, tvDescription, tvIntelligence, tvMethod, tvAim, tvOfficer, tvLocation;
    private TaskDetailResponseDTO taskDetailResponse;
    private CardView cvMain;
    private RelativeLayout rvMain;
    private Animation slideUp;
    private Animation slideDown;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pager_tast_detail, container, false);
        MapFragment mapFragment = (MapFragment) Objects.requireNonNull(getActivity()).getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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

                    if(taskDetailResponse != null && !taskDetailResponse.equals("")) {
                        setData(taskDetailResponse);
                    }
                    Log.e("Called", "Detail >>>>>>");

                }
            }, 1000);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), FragmentDetail.class, "onViewCreated");
        }
    }

    private void setData(final TaskDetailResponseDTO model) {

        try {
            tvTaskType.setText(model.getTask().getTaskTypeName());

            //TimeConverterKt.convertTime(model.getTask().getTimeLeftExpire());
            tvExpiryDate.setText(TimeConverterUtil.convertTime(model.getTask().getTimeLeftExpire()));
            tvDescription.setText(model.getTask().getTaskDescription());
            tvIntelligence.setText(model.getTask().getIntelligenceInformation());
            tvMethod.setText(model.getTask().getMethodUtilised());
            tvAim.setText(model.getTask().getAim());
            tvOfficer.setText(model.getTask().getOfficerSafety());

            final String address = model.getTask().getHouseNo() + ", " + model.getTask().getStreet() + ", " + model.getTask().getTown() + ", " +
                    model.getTask().getCounty() + ", " + model.getTask().getPostCode();
            tvLocation.setText(address);

            toLat = Double.parseDouble(model.getTask().getLatitude());
            toLng = Double.parseDouble(model.getTask().getLongitude());

            LatLng latlngDest = new LatLng(toLat, toLng);
            showMarkerDestination(latlngDest);
            animateCamera(latlngDest);

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), FragmentDetail.class, "setData");
        }
    }

    private void initView(View view) {

        cvMain = view.findViewById(R.id.cv_main);
        rvMain = view.findViewById(R.id.rv_main);
        tvTaskType = view.findViewById(R.id.tv_task_type);
        tvExpiryDate = view.findViewById(R.id.tv_expiry_date);
        tvDescription = view.findViewById(R.id.tv_task_description);
        tvIntelligence = view.findViewById(R.id.tv_intelligence);
        tvMethod = view.findViewById(R.id.tv_method);
        tvAim = view.findViewById(R.id.tv_aim);
        tvOfficer = view.findViewById(R.id.tv_safety);
        tvLocation = view.findViewById(R.id.tv_location);

        Button navButton = view.findViewById(R.id.btn_navigation);
        navButton.setOnClickListener(this);

        ImageView ivGps = view.findViewById(R.id.iv_gps);
        ivGps.setOnClickListener(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        this.googleMap = googleMap;
    }

    private void showMarkerDestination(@NonNull LatLng latLng) {
        if (destMarker == null)
            destMarker = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(latLng));
    }

    private void animateCamera(@NonNull LatLng latLng) {
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
    }

    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(15).build();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        /*TaskDetailActivity myActivity = (TaskDetailActivity) getActivity();
        myActivity.speakCancel();*/
        MapFragment mapFragment1 = (MapFragment) Objects.requireNonNull(getActivity())
                .getFragmentManager().findFragmentById(R.id.map);
        if (mapFragment1 != null) {
            getActivity().getFragmentManager().beginTransaction()
                    .remove(mapFragment1).commit();
        }
        destMarker = null;
        googleMap = null;
        //moveMarkerFlag = false;
        Log.e("Detail ", "DESTROYED ");
    }

    private void navigate(double toLat, double toLng) {
        double frmLat , frmLng ;

        GPSTracker mGPS= new GPSTracker(context);
        if(mGPS.canGetLocation){
            mGPS.getLocation();
            frmLat = mGPS.getLatitude();
            frmLng =  mGPS.getLongitude();

            Intent navigation = new Intent(Intent.ACTION_VIEW, Uri
                    .parse("http://maps.google.com/maps?saddr="
                            + frmLat + ","
                            + frmLng + "&daddr="
                            + toLat + "," + toLng));
            startActivity(navigation);
        }



    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_navigation:
                navigate(toLat, toLng);
                break;

            case R.id.iv_gps:
                LatLng latlngDest = new LatLng(toLat, toLng);
                animateCamera(latlngDest);
                break;
        }
    }
}
