package hcl.policing.digitalpolicingplatform.constants.contolPannel;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.models.controlPannel.LocationData;

public class LocationDataService {

    /**
     * Get the location data
     * @return
     */
    public static ArrayList<LocationData> getLocationData() {
        ArrayList<LocationData> list = new ArrayList<>();

        LocationData locationData = new LocationData();
        locationData.setTitle("Task");
        locationData.setSubtitle("Priority and Arrest");
        locationData.setTime("10:04am");
        locationData.setDistance("1.1 Miles");
        locationData.setImage(R.drawable.ic_task);
        list.add(locationData);

        LocationData locationData1 = new LocationData();
        locationData1.setTitle("Crime");
        locationData1.setSubtitle("Domestic Abuse");
        locationData1.setTime("08:34am");
        locationData1.setDistance("1.4 Miles");
        locationData1.setImage(R.drawable.ic_handcuff);
        list.add(locationData1);

        LocationData locationData2 = new LocationData();
        locationData2.setTitle("Incident");
        locationData2.setSubtitle("Robbery");
        locationData2.setTime("04:23am");
        locationData2.setDistance("1.7 Miles");
        locationData2.setImage(R.drawable.ic_incident_yellow);
        list.add(locationData2);

        LocationData locationData3 = new LocationData();
        locationData3.setTitle("Task");
        locationData3.setSubtitle("Priority and Arrest");
        locationData3.setTime("02:04am");
        locationData3.setDistance("1.6 Miles");
        locationData3.setImage(R.drawable.ic_task);
        list.add(locationData3);

        LocationData locationData4 = new LocationData();
        locationData4.setTitle("Incident");
        locationData4.setSubtitle("Robbery");
        locationData4.setTime("01:23am");
        locationData4.setDistance("1.7 Miles");
        locationData4.setImage(R.drawable.ic_incident_yellow);
        list.add(locationData4);

        return list;
    }
}
