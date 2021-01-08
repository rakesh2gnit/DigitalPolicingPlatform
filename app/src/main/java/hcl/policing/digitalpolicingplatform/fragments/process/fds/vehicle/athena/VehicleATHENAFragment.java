package hcl.policing.digitalpolicingplatform.fragments.process.fds.vehicle.athena;

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
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.adapters.process.fds.vehicle.VehiclePNCListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.PersonReadDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.athena.AthenaListDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.vehicle.VehicleReadDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.vehicle.VehicleSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.athena.VehicleAthenaResponse;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class VehicleATHENAFragment extends Fragment implements ItemClickListener {

    private Context mContext;
    private RecyclerView rvPersonList;
    private ArrayList<VehicleAthenaResponse.VehicleListBean> aVehicleListBean;
    private VehicleSelectionListener vehicleSelectionListener;
    private Dialog mProgressDialog;
    private String searchedWord;
    private AthenaListDialogFragment athenaListDialogFragment;
    private boolean isPopulate;
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
        View view = inflater.inflate(R.layout.vehicle_fragment, container, false);

        if (getArguments() != null) {
            searchedWord = getArguments().getString(GenericConstant.SEARCH_KEYWORD);
            vehicleSelectionListener = (VehicleSelectionListener) getArguments().getSerializable("listener");
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
         * Load the PNC System Person list
         */
        loadAthenaVehicle();
    }

    private static void setRecyclerViewProperty(Context context, RecyclerView rvComponentList) {
        rvComponentList.setHasFixedSize(true);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvComponentList.setLayoutManager(layoutmanager);
    }

    /**
     * Set Camera Photo in PagerAdaptor
     */
    private void setVehiclPagerData() {
        VehiclePNCListAdaptor vehiclePNCListAdaptor = new VehiclePNCListAdaptor(mContext, GenericConstant.TYPE_ATHENA,
                aVehicleListBean, vehicleSelectionListener, this,isPopulate);
        rvPersonList.setAdapter(vehiclePNCListAdaptor);

        setDataToRead();
    }

    private void setDataToRead() {
        textReadList = new ArrayList<>();
        for (int i = 0; i < aVehicleListBean.size(); i++) {
            if(i == 0) {
                textReadList.add(i+1 + "st vehicle VRM is " + aVehicleListBean.get(i).getRegistrationnumber() + ", make is " + aVehicleListBean.get(i).getMake() + ", model is " + aVehicleListBean.get(i).getModel() + " and color is " + aVehicleListBean.get(i).getVehiclecolour() + ".");
            } else if(i == 1) {
                textReadList.add(i+1 + "nd vehicle VRM is " + aVehicleListBean.get(i).getRegistrationnumber() + ", make is " + aVehicleListBean.get(i).getMake() + ", model is " + aVehicleListBean.get(i).getModel() + " and color is " + aVehicleListBean.get(i).getVehiclecolour() + ".");
            } else if (i == 2) {
                textReadList.add(i+1 + "rd vehicle VRM is " + aVehicleListBean.get(i).getRegistrationnumber() + ", make is " + aVehicleListBean.get(i).getMake() + ", model is " + aVehicleListBean.get(i).getModel() + " and color is " + aVehicleListBean.get(i).getVehiclecolour() + ".");
            } else {
                textReadList.add(i+1 + "th vehicle VRM is " + aVehicleListBean.get(i).getRegistrationnumber() + ", make is " + aVehicleListBean.get(i).getMake() + ", model is " + aVehicleListBean.get(i).getModel() + " and color is " + aVehicleListBean.get(i).getVehiclecolour() + ".");
            }
        }
        partition = nPartition(textReadList, 3);
    }

    public static void readData() {
        if(listPos < partition.size()) {
            if (listPos == 0) {
                VehicleReadDialogFragment.isListen = true;
                VehicleReadDialogFragment.setFlag(false, true);
                textRead = "Found " + textReadList.size() + " records for athena." +
                        partition.get(listPos) + "\n"
                        + " Do you want me to read more records.";
            } else if (listPos > 0 && listPos < partition.size()-1) {
                VehicleReadDialogFragment.setFlag(false, true);
                VehicleReadDialogFragment.isListen = true;
                textRead = partition.get(listPos).toString() + "\n"
                        + " Do you want me to read more records.";
            } else {
                VehicleReadDialogFragment.isListen = false;
                VehicleReadDialogFragment.setFlag(false, true);
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
    public void loadAthenaVehicle() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.VEHICLE_ATHENA_LIST_JSON);
                VehicleAthenaResponse vehicleAthenaResponse = (VehicleAthenaResponse) JsonUtil.getInstance().toModel(strJson, VehicleAthenaResponse.class);
                if (vehicleAthenaResponse != null) {
                    aVehicleListBean = (ArrayList<VehicleAthenaResponse.VehicleListBean>) vehicleAthenaResponse.getSearchathenalistresponse().getVehiclelist();
                    /*if (aVehicleListBean != null && aVehicleListBean.size() > 0) {
                        aVehicleListBean = BasicMethodsUtil.getInstance().searchPNCPerson(aVehicleListBean, searchedWord);
                    }*/
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (aVehicleListBean != null && aVehicleListBean.size() > 0) {
                        setVehiclPagerData();

                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), getString(R.string.no_data));
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), VehicleATHENAFragment.class, "loadAthenaVehicle");
            }
        }).start();
    }

    @Override
    public void onItemClicked(int clickType) {
        switch (clickType) {
            case GenericConstant.TYPE_ATHENA:
                SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_NAME, GenericConstant.VEHICLE_ATHENA_DETAIL);
                loadATHENADetailsDialog();

                break;
        }
    }

    /**
     * Load Person Dialog
     */
    public void loadATHENADetailsDialog() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.DETAILS_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        athenaListDialogFragment = AthenaListDialogFragment.newInstance(GenericConstant.TYPE_VEHICLE, isPopulate);
        athenaListDialogFragment.show(ft, GenericConstant.DETAILS_DIALOG);
    }
}
