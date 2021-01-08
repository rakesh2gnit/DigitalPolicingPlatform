package hcl.policing.digitalpolicingplatform.fragments.process.event;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.adapters.process.event.EventListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.stops.STOPSDetailDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.vehicle.VehicleReadDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.process.event.EventSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.crime.EventResponse;
import hcl.policing.digitalpolicingplatform.models.process.crime.EventSearchList;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class EventFragment extends Fragment implements ItemClickListener {

    private Context mContext;
    private RecyclerView rvPersonList;
    private TextView tvNoData;
    private ArrayList<EventSearchList> eventSearchList;
    private EventSelectionListener eventSelectionListener;
    private Dialog mProgressDialog;
    private String searchedWord;
    private STOPSDetailDialogFragment stopsDetailDialogFragment;
    public static String textRead;
    public static int listPos = 0;
    private static List<String> textReadList;
    private static List<List<String>> partition;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.person_fragment, container, false);

        if (getArguments() != null) {
            searchedWord = getArguments().getString(GenericConstant.SEARCH_KEYWORD);
            eventSelectionListener = (EventSelectionListener) getArguments().getSerializable("listener");
        }
        initView(view);
        return view;
    }

    /**
     * Load the initial views
     *
     * @param view
     */
    private void initView(View view) {
        rvPersonList = view.findViewById(R.id.rvPersonList);
        tvNoData = view.findViewById(R.id.tvNoData);
        setRecyclerViewProperty(mContext, rvPersonList);

        /**
         * Load the STOPS System Person list
         */
        loadStopPersons();
    }

    /**
     * Set the recyclerview property.
     *
     * @param context
     * @param rvComponentList
     */
    private static void setRecyclerViewProperty(Context context, RecyclerView rvComponentList) {
        rvComponentList.setHasFixedSize(true);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvComponentList.setLayoutManager(layoutmanager);
    }


    /**
     * Set Camera Photo in PagerAdaptor
     */
    private void setPersonPagerData() {
        if (eventSearchList != null && eventSearchList.size() > 0) {
            EventListAdaptor eventPoleListAdaptor = new EventListAdaptor(mContext, eventSearchList, eventSelectionListener, this);
            rvPersonList.setAdapter(eventPoleListAdaptor);

            setDataToRead();
        }
    }

    private void setDataToRead() {
        textReadList = new ArrayList<>();
        for (int i = 0; i < eventSearchList.size(); i++) {
            if(i == 0) {
                textReadList.add(i+1 + "st Incident with URN " + eventSearchList.get(i).getURN() + " has been reported on " + eventSearchList.get(i).getReportedOn() + " in " + eventSearchList.get(i).getCategory() + " category with classification " + eventSearchList.get(i).getClassification() + ". Location associated with incident is " + eventSearchList.get(i).getLocation());
            } else if(i == 1) {
                textReadList.add(i+1 + "nd Incident with URN " + eventSearchList.get(i).getURN() + " has been reported on " + eventSearchList.get(i).getReportedOn() + " in " + eventSearchList.get(i).getCategory() + " category with classification " + eventSearchList.get(i).getClassification() + ". Location associated with incident is " + eventSearchList.get(i).getLocation());
            } else if (i == 2) {
                textReadList.add(i+1 + "rd Incident with URN " + eventSearchList.get(i).getURN() + " has been reported on " + eventSearchList.get(i).getReportedOn() + " in " + eventSearchList.get(i).getCategory() + " category with classification " + eventSearchList.get(i).getClassification() + ". Location associated with incident is " + eventSearchList.get(i).getLocation());
            } else {
                textReadList.add(i+1 + "th Incident with URN " + eventSearchList.get(i).getURN() + " has been reported on " + eventSearchList.get(i).getReportedOn() + " in " + eventSearchList.get(i).getCategory() + " category with classification " + eventSearchList.get(i).getClassification() + ". Location associated with incident is " + eventSearchList.get(i).getLocation());
            }
        }
        partition = nPartition(textReadList, 3);
    }

    public static void readData() {
        if(listPos < partition.size()) {
            if (listPos == 0) {
                IncidentReadDialogFragment.isListen = true;
                IncidentReadDialogFragment.setFlag(false, true);
                textRead = "Found " + textReadList.size() + " records." +
                        partition.get(listPos) + "\n"
                        + " Do you want me to read more records.";
            } else if (listPos > 0 && listPos < partition.size()-1) {
                IncidentReadDialogFragment.setFlag(false, true);
                IncidentReadDialogFragment.isListen = true;
                textRead = partition.get(listPos).toString() + "\n"
                        + " Do you want me to read more records.";
            } else {
                IncidentReadDialogFragment.isListen = false;
                IncidentReadDialogFragment.setFlag(false, true);
                textRead = partition.get(listPos).toString();
            }
            listPos++;
        }
    }

    private <T> List<List<T>> nPartition(List<T> objs, final int N) {
        return new ArrayList<>(IntStream.range(0, objs.size()).boxed().collect(
                Collectors.groupingBy(e->e/N,Collectors.mapping(e->objs.get(e), Collectors.toList())
                )).values());
    }

    /**
     * Load Person JSON
     */
    public void loadStopPersons() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.EVENT_POLE_LIST_JSON);
                EventResponse eventResponse = (EventResponse) JsonUtil.getInstance().toModel(strJson, EventResponse.class);
                if (eventResponse != null) {
                    eventSearchList = (ArrayList<EventSearchList>) eventResponse.getEventSearchList();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (eventSearchList != null && eventSearchList.size() > 0) {
                        setPersonPagerData();
                    } else {
                        BasicMethodsUtil.getInstance().noDataAvailable(rvPersonList, tvNoData);
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), EventFragment.class, "loadStopPersons");
            }
        }).start();
    }


    /**
     * Load STOPS details Dialog
     */
    public void loadSTOPSDetailsDialog() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.DETAILS_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        stopsDetailDialogFragment = STOPSDetailDialogFragment.newInstance(GenericConstant.TYPE_PERSON, GenericConstant.POPULATE_TRUE);
        stopsDetailDialogFragment.show(ft, GenericConstant.DETAILS_DIALOG);
    }


    @Override
    public void onItemClicked(int clickType) {
        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_NAME, GenericConstant.PERSON_STOPS_DETAIL);
//        loadSTOPSDetailsDialog();

    }
}
