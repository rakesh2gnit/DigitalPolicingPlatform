package hcl.policing.digitalpolicingplatform.fragments.process.fds.team.pole;

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

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.adapters.process.fds.team.TeamPoleListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.stops.STOPSDetailDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.team.TeamSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.pole.PersonList;
import hcl.policing.digitalpolicingplatform.models.process.fds.pole.PoleResponse;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class TeamPoleFragment extends Fragment implements ItemClickListener {

    private Context mContext;
    private RecyclerView rvPersonList;
    private TextView tvNoData;
    private ArrayList<PersonList> aSearchPersonListModel;
    private TeamSelectionListener teamSelectionListener;
    private Dialog mProgressDialog;
    private String searchedWord;
    private STOPSDetailDialogFragment stopsDetailDialogFragment;
    private boolean isPopulate;

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
            teamSelectionListener = (TeamSelectionListener) getArguments().getSerializable("listener");
            isPopulate = getArguments().getBoolean(GenericConstant.IS_POPULATE);
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
        if (aSearchPersonListModel != null && aSearchPersonListModel.size() > 0) {
            TeamPoleListAdaptor personPoleListAdaptor = new TeamPoleListAdaptor(mContext, aSearchPersonListModel, teamSelectionListener, this);
            rvPersonList.setAdapter(personPoleListAdaptor);
        }
    }

    /**
     * Load Person JSON
     */
    public void loadStopPersons() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.TEAM_POLE_LIST_JSON);
                PoleResponse poleResponse = (PoleResponse) JsonUtil.getInstance().toModel(strJson, PoleResponse.class);
                if (poleResponse != null) {
                    aSearchPersonListModel = (ArrayList<PersonList>) poleResponse.getPOLESearchResponse().getPersonList();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (aSearchPersonListModel != null && aSearchPersonListModel.size() > 0) {
                        setPersonPagerData();
                    } else {
                        BasicMethodsUtil.getInstance().noDataAvailable(rvPersonList, tvNoData);
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), TeamPoleFragment.class, "loadStopPersons");
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
        stopsDetailDialogFragment = STOPSDetailDialogFragment.newInstance(GenericConstant.TYPE_PERSON, isPopulate);
        stopsDetailDialogFragment.show(ft, GenericConstant.DETAILS_DIALOG);
    }


    @Override
    public void onItemClicked(int clickType) {
        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_NAME, GenericConstant.PERSON_STOPS_DETAIL);
//        loadSTOPSDetailsDialog();

    }
}
