package hcl.policing.digitalpolicingplatform.fragments.process.fds.courtwarrant.athena;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.adapters.process.fds.courtwarrant.CourtWarranrListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.athena.AddressAthenaFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.athena.AthenaListDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.courtwarrant.CourtWarrantSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.courtwarrant.CourtWarrantAthenaListResponse;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class CourtWarrantAthenaFragment extends Fragment implements ItemClickListener {

    private Context mContext;
    private RecyclerView rvPersonList;
    private ArrayList<CourtWarrantAthenaListResponse.CourtWarrentListBean> aCourtWarrentListBean;
    private CourtWarrantSelectionListener courtWarrantSelectionListener;
    private Dialog mProgressDialog;
    private String searchedWord;
    private boolean isPopulate;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.address_fragment, container, false);

        if (getArguments() != null) {
            searchedWord = getArguments().getString(GenericConstant.SEARCH_KEYWORD);
            courtWarrantSelectionListener = (CourtWarrantSelectionListener) getArguments().getSerializable("listener");
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
        setRecyclerViewProperty(mContext, rvPersonList);

        /**
         * Load the Athena System address list
         */
        loadCaseAthenaList();
    }

    /**
     * Set the recycler view property
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
    private void setPagerData() {
        CourtWarranrListAdaptor courtWarranrListAdaptor = new CourtWarranrListAdaptor(aCourtWarrentListBean, courtWarrantSelectionListener,
                this, isPopulate);
        rvPersonList.setAdapter(courtWarranrListAdaptor);
    }

    /**
     * Load Cases JSON
     */
    private void loadCaseAthenaList() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.COURT_WARRANT_ATHENA_LIST_JSON);
                CourtWarrantAthenaListResponse courtWarrantAthenaListResponse = (CourtWarrantAthenaListResponse) JsonUtil.getInstance().toModel(strJson, CourtWarrantAthenaListResponse.class);
                if (courtWarrantAthenaListResponse != null) {
                    aCourtWarrentListBean = (ArrayList<CourtWarrantAthenaListResponse.CourtWarrentListBean>) courtWarrantAthenaListResponse.getSearchathenalistresponse().getCourtwarrentlist();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (aCourtWarrentListBean != null && aCourtWarrentListBean.size() > 0) {
                        setPagerData();

                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), getString(R.string.no_data));
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), AddressAthenaFragment.class, "loadCaseAthenaList");
            }
        }).start();
    }

    @Override
    public void onItemClicked(int clickType) {

        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_NAME, GenericConstant.COOURT_WARRANT_ATHENA_DETAIL);
        loadATHENADetailsDialog();
    }

    /**
     * Load Person Dialog
     */
    private void loadATHENADetailsDialog() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.COOURT_WARRANT_ATHENA_DETAIL);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        AthenaListDialogFragment athenaListDialogFragment = AthenaListDialogFragment.newInstance(GenericConstant.TYPE_COURT_WARRENT, isPopulate);
        athenaListDialogFragment.show(ft, GenericConstant.COOURT_WARRANT_ATHENA_DETAIL);
    }
}
