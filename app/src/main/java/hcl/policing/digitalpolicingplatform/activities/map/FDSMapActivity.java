package hcl.policing.digitalpolicingplatform.activities.map;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.List;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.services.MyLocationServices;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.LocationUtil;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class FDSMapActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback {

    private AppSession appSession;
    private GoogleMap googleMap;
    private Context mContext;
    private TextView tvAddress;
    private ImageView ivMarker;
    private String strAddress;
    private Dialog mProgressDialog;
    private LinearLayout llHeader;


    /**
     * Call the Map Activity
     */
    public static void callMapActivity(Context context, String nominalAddress) {
        Intent mapIntent = new Intent(context, FDSMapActivity.class);
        mapIntent.putExtra(GenericConstant.MAP_ADDRESS, nominalAddress);
        context.startActivity(mapIntent);
        ((Activity) context).overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fds_map_activity);
        mContext = this;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        appSession = new AppSession(this);
        appSession.setCurrentLatitude(null);
        appSession.setCurrentLongitude(null);
        initView();
    }

    /**
     * initialize the view
     */
    private void initView() {

        llHeader = findViewById(R.id.llHeader);
        tvAddress = findViewById(R.id.tvAddress);
        ImageView ivGps = findViewById(R.id.ivGps);
        ivMarker = findViewById(R.id.ivMarker);
        TextView tvHeader = llHeader.findViewById(R.id.tvHeader);
        ImageView ivBack = llHeader.findViewById(R.id.ivBack);

        tvHeader.setText(getString(R.string.location_on_map));
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            strAddress = bundle.getString(GenericConstant.MAP_ADDRESS);
        }

        ivBack.setOnClickListener(this);
        ivGps.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ivBack:

                DialogUtil.cancelProgressDialog(mProgressDialog);
                if (Utilities.isMyServiceRunning(mContext, MyLocationServices.class)) {
                    Intent i = new Intent(mContext, MyLocationServices.class);
                    i.setAction(MyLocationServices.ACTION_STOP_FOREGROUND_SERVICE);
                    ContextCompat.startForegroundService(mContext, i);
                }
                finish();
                break;

            case R.id.ivGps:


                mProgressDialog = DialogUtil.showProgressDialog(mContext);
                Intent i = new Intent(mContext, MyLocationServices.class);
                i.setAction(MyLocationServices.ACTION_START_FOREGROUND_SERVICE);
                ContextCompat.startForegroundService(mContext, i);

                Handler handler = new Handler();
                handler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (appSession.getCurrentLatitude() != null && !appSession.getCurrentLatitude().equalsIgnoreCase("")
                                && appSession.getCurrentLongitude() != null) {

                            LatLng latlngDest = new LatLng(Double.parseDouble(appSession.getCurrentLatitude()), Double.parseDouble(appSession.getCurrentLongitude()));
                            animateCamera(latlngDest, googleMap);

                            List<Address> addressList = LocationUtil.getInstance().getAddressFromLocation(mContext,
                                    Double.parseDouble(appSession.getCurrentLatitude()), Double.parseDouble(appSession.getCurrentLongitude()));

                            if (addressList != null && addressList.size() > 0) {
                                if (addressList.get(0).getAddressLine(0) != null) {
                                    tvAddress.setText(addressList.get(0).getAddressLine(0));
                                }
                            }
                            ivMarker.setVisibility(View.VISIBLE);
                        }
                        DialogUtil.cancelProgressDialog(mProgressDialog);
                    }
                }, 4000);
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            this.googleMap = googleMap;

            appSession.setLatitude(null);
            appSession.setLongitude(null);
            mProgressDialog = DialogUtil.showProgressDialog(mContext);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    LatLng address = LocationUtil.getInstance().getLocationFromAddress(mContext, strAddress);
                    googleMap.addMarker(new MarkerOptions().position(address).title(strAddress));
                    googleMap.moveCamera(CameraUpdateFactory.newLatLng(address));
                    tvAddress.setText(strAddress);
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                }
            }, 5000);

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), MapActivity.class, "onMapReady");
        }
    }

    /**
     * Animate the view of camera
     *
     * @param latLng
     * @param googleMap
     */
    private static void animateCamera(@NonNull LatLng latLng, GoogleMap googleMap) {
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
    }


    @NonNull
    private static CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(15).build();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }
}

