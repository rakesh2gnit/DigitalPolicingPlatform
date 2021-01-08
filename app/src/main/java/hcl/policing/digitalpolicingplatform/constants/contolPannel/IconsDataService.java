package hcl.policing.digitalpolicingplatform.constants.contolPannel;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.models.controlPannel.ProcessIconsDTO;

public class IconsDataService {

    /**
     * Get the process icons
     * @return
     */
    public static ArrayList<ProcessIconsDTO> getProcessIcon() {
        ArrayList<ProcessIconsDTO> list = new ArrayList<>();

        ProcessIconsDTO processIconsDTO = new ProcessIconsDTO();
        processIconsDTO.setName("Search");
        processIconsDTO.setId("1");
        processIconsDTO.setIconImage(R.drawable.ic_search_white);
        list.add(processIconsDTO);

        ProcessIconsDTO processIconsDTO5 = new ProcessIconsDTO();
        processIconsDTO5.setName("Crime");
        processIconsDTO5.setId("2");
        processIconsDTO5.setIconImage(R.drawable.ic_crime_white);
        list.add(processIconsDTO5);

        ProcessIconsDTO processIconsDTO2 = new ProcessIconsDTO();
        processIconsDTO2.setName("TOR");
        processIconsDTO2.setId("3");
        processIconsDTO2.setIconImage(R.drawable.ic_incident_white);
        list.add(processIconsDTO2);

        ProcessIconsDTO processIconsDTO3 = new ProcessIconsDTO();
        processIconsDTO3.setName("Stops & Search");
        processIconsDTO3.setId("4");
        processIconsDTO3.setIconImage(R.drawable.ic_stop_white);
        list.add(processIconsDTO3);

        ProcessIconsDTO processIconsDTO7 = new ProcessIconsDTO();
        processIconsDTO7.setName("Witness Statement");
        processIconsDTO7.setId("7");
        processIconsDTO7.setIconImage(R.drawable.ic_pocketbook_white);
        list.add(processIconsDTO7);

        ProcessIconsDTO processIconsDTO4 = new ProcessIconsDTO();
        processIconsDTO4.setName("Incident");
        processIconsDTO4.setId("5");
        processIconsDTO4.setIconImage(R.drawable.ic_incident_white);
        list.add(processIconsDTO4);

        ProcessIconsDTO processIconsDTO1 = new ProcessIconsDTO();
        processIconsDTO1.setName("Pocket Book");
        processIconsDTO1.setId("6");
        processIconsDTO1.setIconImage(R.drawable.ic_pocketbook_white);
        list.add(processIconsDTO1);

        return list;
    }

    /**
     * Get the Control Icons
     * @return
     */
    public static ArrayList<ProcessIconsDTO> getControlIcon() {
        ArrayList<ProcessIconsDTO> list = new ArrayList<>();

        ProcessIconsDTO processIconsDTO = new ProcessIconsDTO();
        processIconsDTO.setName("Search");
        processIconsDTO.setId("1");
        processIconsDTO.setIconImage(R.drawable.ic_search_blue);
        list.add(processIconsDTO);

        ProcessIconsDTO processIconsDTO5 = new ProcessIconsDTO();
        processIconsDTO5.setName("Crime");
        processIconsDTO5.setId("2");
        processIconsDTO5.setIconImage(R.drawable.ic_crime_blue);
        list.add(processIconsDTO5);

        ProcessIconsDTO processIconsDTO2 = new ProcessIconsDTO();
        processIconsDTO2.setName("TOR");
        processIconsDTO2.setId("3");
        processIconsDTO2.setIconImage(R.drawable.ic_report_blue);
        list.add(processIconsDTO2);

        ProcessIconsDTO processIconsDTO3 = new ProcessIconsDTO();
        processIconsDTO3.setName("Stops & Search");
        processIconsDTO3.setId("4");
        processIconsDTO3.setIconImage(R.drawable.ic_stop_blue);
        list.add(processIconsDTO3);

        ProcessIconsDTO processIconsDTO4 = new ProcessIconsDTO();
        processIconsDTO4.setName("Incident");
        processIconsDTO4.setId("5");
        processIconsDTO4.setIconImage(R.drawable.ic_incident_blue);
        list.add(processIconsDTO4);

        ProcessIconsDTO processIconsDTO1 = new ProcessIconsDTO();
        processIconsDTO1.setName("Pocket Book");
        processIconsDTO1.setId("6");
        processIconsDTO1.setIconImage(R.drawable.ic_pocketbook_blue);
        list.add(processIconsDTO1);

        ProcessIconsDTO processIconsDTO7 = new ProcessIconsDTO();
        processIconsDTO7.setName("Witness Statement");
        processIconsDTO7.setId("7");
        processIconsDTO7.setIconImage(R.drawable.ic_pocketbook_blue);
        list.add(processIconsDTO7);

        return list;
    }
}
