package hcl.policing.digitalpolicingplatform.activities.map;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.location.Address;
import android.os.Bundle;
import android.os.Handler;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.activities.sketch.SketchActivity;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.constants.fieldName.FieldsNameConstant;
import hcl.policing.digitalpolicingplatform.models.camera.PhotoListModel;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.services.MyLocationServices;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.LocationUtil;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class PocketbookMapActivity extends BaseActivity implements View.OnClickListener, OnMapReadyCallback {

    private AppSession appSession;
    private GoogleMap googleMap;
    private Context mContext;
    private RelativeLayout rlMain;
    private TextView tvAddress;
    private ArrayList<PhotoListModel> imageList;
    private Bitmap mBitmap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_activity);
        mContext = this;
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        appSession = new AppSession(this);
        initView();
    }

    /**
     * initialize the view
     */
    private void initView() {
        rlMain = findViewById(R.id.rl_main);
        tvAddress = findViewById(R.id.tv_address);
        Button btnOk = findViewById(R.id.btn_ok);
        btnOk.setOnClickListener(this);
        ImageView ivGps = findViewById(R.id.iv_gps);
        ivGps.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_ok:
                if (Utilities.isMyServiceRunning(mContext, MyLocationServices.class)) {
                    Intent i = new Intent(mContext, MyLocationServices.class);
                    i.setAction(MyLocationServices.ACTION_STOP_FOREGROUND_SERVICE);
                    ContextCompat.startForegroundService(mContext, i);
                }
                //mBitmap = getBitmapFromView(rlMain);
                captureScreen();
                break;

            case R.id.iv_gps:
                if (appSession.getCurrentLatitude() != null && !appSession.getCurrentLatitude().equalsIgnoreCase("")
                        && appSession.getCurrentLongitude() != null) {

                    LatLng latlngDest = new LatLng(Double.parseDouble(appSession.getCurrentLatitude()), Double.parseDouble(appSession.getCurrentLongitude()));
                    animateCamera(latlngDest, googleMap);

                    List<Address> addressList = null;
                    try {
                        addressList = LocationUtil.getInstance().getAddressFromLocation(mContext,
                                Double.parseDouble(appSession.getCurrentLatitude()), Double.parseDouble(appSession.getCurrentLongitude()));
                    } catch (Exception e) {
                        ExceptionLogger.Logger(e.getCause(), e.getMessage(), PocketbookMapActivity.class, "onMapReady");
                    }

                    if (addressList != null && addressList.size() > 0) {
                        if (addressList.get(0).getAddressLine(0) != null) {
                            tvAddress.setText(addressList.get(0).getAddressLine(0));
                        }
                    }

                }
                break;
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        try {
            this.googleMap = googleMap;

            appSession.setLatitude(null);
            appSession.setLongitude(null);

            Dialog mProgressDialog = DialogUtil.showProgressDialog(mContext);
            Intent i = new Intent(mContext, MyLocationServices.class);
            i.setAction(MyLocationServices.ACTION_START_FOREGROUND_SERVICE);
            ContextCompat.startForegroundService(mContext, i);

            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    Log.e("Location ", "Latitude: " + appSession.getLatitude() + "  Longitude: " + appSession.getLongitude());
                    if (appSession.getLatitude() != null && !appSession.getLatitude().equalsIgnoreCase("")
                            && appSession.getLongitude() != null) {

                        LatLng latlngDest = new LatLng(Double.parseDouble(appSession.getLatitude()), Double.parseDouble(appSession.getLongitude()));
                        animateCamera(latlngDest, googleMap);

                        List<Address> addressList = null;
                        try {
                            addressList = LocationUtil.getInstance().getAddressFromLocation(mContext,
                                    Double.parseDouble(appSession.getLatitude()), Double.parseDouble(appSession.getLongitude()));
                        } catch (Exception e) {
                            ExceptionLogger.Logger(e.getCause(), e.getMessage(), PocketbookMapActivity.class, "onMapReady");
                        }

                        if (addressList != null && addressList.size() > 0) {
                            if (addressList.get(0).getAddressLine(0) != null) {
                                tvAddress.setText(addressList.get(0).getAddressLine(0));
                            }
                        }
                    }
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                }
            }, 7000);

            googleMap.setOnCameraMoveStartedListener(new GoogleMap.OnCameraMoveStartedListener() {
                @Override
                public void onCameraMoveStarted(int i) {
                    if (i == GoogleMap.OnCameraMoveStartedListener.REASON_GESTURE) {
                        CameraPosition centre = googleMap.getCameraPosition();

                        appSession.setLatitude("" + centre.target.latitude);
                        appSession.setLongitude("" + centre.target.longitude);

                        List<Address> addressList = null;
                        try {
                            addressList = LocationUtil.getInstance().getAddressFromLocation(mContext,
                                    centre.target.latitude, centre.target.longitude);
                        } catch (Exception e) {
                            ExceptionLogger.Logger(e.getCause(), e.getMessage(), PocketbookMapActivity.class, "onMapReady");
                        }

                        if (addressList != null && addressList.size() > 0) {

                            if (addressList.get(0).getAddressLine(0) != null) {
                                tvAddress.setText(addressList.get(0).getAddressLine(0));
                            }
                        }
                    }
                }
            });
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), PocketbookMapActivity.class, "onMapReady");
        }
    }

    /**
     * Animate the view of camera
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

    /**
     * Get the bitmap from view
     *
     * @param view
     * @return
     */
    private Bitmap getBitmapFromView(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    public void captureScreen() {
        GoogleMap.SnapshotReadyCallback callback = new GoogleMap.SnapshotReadyCallback() {

            @Override
            public void onSnapshotReady(Bitmap snapshot) {
                // TODO Auto-generated method stub
                mBitmap = snapshot;

                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                Objects.requireNonNull(mBitmap).compress(Bitmap.CompressFormat.JPEG, 50, baos);
                byte[] byt = baos.toByteArray();
                String encodedImage = Base64.encodeToString(byt, Base64.DEFAULT);
                //rlMain.setDrawingCacheEnabled(false);
                Intent intentProcess = new Intent();
                intentProcess.putExtra(GenericConstant.SKETCH_LIST, encodedImage);
                setResult(RESULT_OK, intentProcess);
                finish();
                overridePendingTransition(R.anim.translate_in, R.anim.translate_out);

                //OutputStream fout = null;

                String filePath = System.currentTimeMillis() + ".jpeg";

                try
                {
                    //fout = openFileOutput(filePath, MODE_WORLD_READABLE);

                    // Write the string to the file
                    //mBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fout);
                    //fout.flush();
                    //fout.close();
                }
                catch (Exception e)
                {
                    // TODO Auto-generated catch block
                    Log.d("ImageCapture", "FileNotFoundException");
                    Log.d("ImageCapture", e.getMessage());
                    filePath = "";
                }

                //openShareImageDialog(filePath);
            }
        };

        googleMap.snapshot(callback);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.translate_in, R.anim.translate_out);
    }
}
