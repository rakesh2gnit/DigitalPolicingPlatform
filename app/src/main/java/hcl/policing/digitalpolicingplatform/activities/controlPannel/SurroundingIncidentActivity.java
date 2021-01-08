package hcl.policing.digitalpolicingplatform.activities.controlPannel;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Locale;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.customLibraries.MovableFloatingActionButton;
import hcl.policing.digitalpolicingplatform.utils.GPSTracker;
import hcl.policing.digitalpolicingplatform.utils.speak.SpeakToPersonUtil;

public class SurroundingIncidentActivity extends BaseActivity implements OnMapReadyCallback, View.OnClickListener, TextToSpeech.OnInitListener {

    private Marker destMarker;
    private GoogleMap googleMap;
    public TextToSpeech textToSpeech;
    private SpeakToPersonUtil speakText;
    private String text = "";
    private MovableFloatingActionButton fab;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.surrounding_incidentdetail_activity);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        speakText = new SpeakToPersonUtil();
        try {
            textToSpeech = new TextToSpeech(this, this);

            text = "Incident number KP-20190201-020 with type Robbery has been recorded at 12:03 PM today on location 21, Reid Street, Egham, TW20 9WB nearly 53.4 miles from your location. Shop has been robbed and their is no suspect identified. The incident is reported by John Smith with residence 22, Reid Street, Egham. The current officer in charge is Kevin Dawson. To know more, please click on More details button.";
        } catch (Exception e) {
            e.printStackTrace();
        }

        Button navButton = findViewById(R.id.btn_navigation);
        navButton.setOnClickListener(this);

        ImageView ivGps = findViewById(R.id.iv_gps);
        ivGps.setOnClickListener(this);

        fab = findViewById(R.id.fab);
        fab.setOnClickListener(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        this.googleMap = googleMap;

        LatLng latlngDest = new LatLng(51.43048067547976, -0.5424361913017037);
        showMarkerDestination(latlngDest);
        animateCamera(latlngDest);
    }

    /**
     * Show Marker Destination
     * @param latLng
     */
    private void showMarkerDestination(@NonNull LatLng latLng) {
        if (destMarker == null)
            destMarker = googleMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)).position(latLng));
    }

    /**
     * Animate camera method
     * @param latLng
     */
    private void animateCamera(@NonNull LatLng latLng) {
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(getCameraPositionWithBearing(latLng)));
    }

    @NonNull
    private CameraPosition getCameraPositionWithBearing(LatLng latLng) {
        return new CameraPosition.Builder().target(latLng).zoom(15).build();
    }

    /**
     * Call this method for Navigate in map by using lat,long
     * @param toLat
     * @param toLng
     */
    private void navigate(double toLat, double toLng) {
        double frmLat, frmLng;

        GPSTracker mGPS = new GPSTracker(this);
        if (mGPS.canGetLocation) {
            mGPS.getLocation();
            frmLat = mGPS.getLatitude();
            frmLng = mGPS.getLongitude();

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
                navigate(51.43048067547976, -0.5424361913017037);
                break;

            case R.id.fab:
                if (textToSpeech != null) {
                    if (textToSpeech.isSpeaking()) {
                        textToSpeech.stop();
                        fab.setImageResource(R.drawable.ic_play);
                    } else {
                        fab.setImageResource(R.drawable.ic_stop_blue);
                        speakText.speakToPerson(SurroundingIncidentActivity.this, textToSpeech, text);
                    }
                }
                break;

            case R.id.iv_gps:
                LatLng latlngDest = new LatLng(51.43048067547976, -0.5424361913017037);
                animateCamera(latlngDest);
                break;
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        /*SupportMapFragment mapFragment1 = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment1 != null) {
            getFragmentManager().beginTransaction()
                    .remove(mapFragment1).commit();
        }*/
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        destMarker = null;
        googleMap = null;
        //moveMarkerFlag = false;
        Log.e("DESTROYED ", "DESTROYED ");
    }

    @Override
    public void onInit(int status) {
        if (status == TextToSpeech.SUCCESS) {
            textToSpeech.setLanguage(Locale.UK);
            textToSpeech.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String utteranceId) {

                }
                @Override
                public void onDone(String utteranceId) {
                    fab.setImageResource(R.drawable.ic_play);
                }

                @Override
                public void onError(String utteranceId) {

                }
            });
        }
    }
}
