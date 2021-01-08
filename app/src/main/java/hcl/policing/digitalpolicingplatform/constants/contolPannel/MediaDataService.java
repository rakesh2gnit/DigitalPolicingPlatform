package hcl.policing.digitalpolicingplatform.constants.contolPannel;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.models.controlPannel.MediaData;

public class MediaDataService {

    /**
     * Get the media data
     * @return
     */
    public static ArrayList<MediaData> getMediaData() {
        ArrayList<MediaData> list = new ArrayList<>();

        MediaData mediaData = new MediaData();
        mediaData.setTitle("Incident");
        mediaData.setDate("09 Apr 2019");
        mediaData.setDescription("A man is in a critical condition after a collision on Broadhalgh ...");
        mediaData.setImg1(R.drawable.ic_twitter);
        mediaData.setImg2(R.drawable.gmp1);
        list.add(mediaData);

        MediaData mediaData1 = new MediaData();
        mediaData1.setTitle("Stalking");
        mediaData1.setDate("09 Apr 2019");
        mediaData1.setDescription("Across Greater #Manchester, high-risk victims of #stalking ...");
        mediaData1.setImg1(R.drawable.ic_twitter);
        mediaData1.setImg2(R.drawable.gmp2);
        list.add(mediaData1);

        MediaData mediaData2 = new MediaData();
        mediaData2.setTitle("Charged");
        mediaData2.setDate("08 Apr 2019");
        mediaData2.setDescription("A 17-year-old boy has been charged with assault and  ...");
        mediaData2.setImg1(R.drawable.ic_facebook);
        mediaData2.setImg2(R.drawable.gmp3);
        list.add(mediaData2);

        MediaData mediaData3 = new MediaData();
        mediaData3.setTitle("Investigation");
        mediaData3.setDate("05 Apr 2019");
        mediaData3.setDescription("Police investigating after a woman was robbed ...");
        mediaData3.setImg1(R.drawable.ic_youtube);
        mediaData3.setImg2(R.drawable.gmp4);
        list.add(mediaData3);

        return list;
    }
}
