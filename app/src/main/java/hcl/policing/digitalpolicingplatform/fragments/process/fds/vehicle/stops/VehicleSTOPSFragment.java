package hcl.policing.digitalpolicingplatform.fragments.process.fds.vehicle.stops;

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
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.stops.STOPSDetailDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.PersonReadDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.vehicle.VehicleReadDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.vehicle.VehicleSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.vehicle.stops.VehicleSTOPResponse;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class VehicleSTOPSFragment extends Fragment implements ItemClickListener {

    private Context mContext;
    private RecyclerView rvPersonList;
    private ArrayList<VehicleSTOPResponse.SearchVehicleListBean> aSearchVehicleListBean;
    private VehicleSelectionListener vehicleSelectionListener;
    private Dialog mProgressDialog;
    private String searchedWord;
    private STOPSDetailDialogFragment stopsDetailDialogFragment;
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
        loadStopsVehicle();
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
        VehiclePNCListAdaptor vehiclePNCListAdaptor = new VehiclePNCListAdaptor(mContext, GenericConstant.TYPE_STOPS,
                aSearchVehicleListBean, vehicleSelectionListener, this, isPopulate);
        rvPersonList.setAdapter(vehiclePNCListAdaptor);

        setDataToRead();
    }

    private void setDataToRead() {
        textReadList = new ArrayList<>();
        for (int i = 0; i < aSearchVehicleListBean.size(); i++) {
            if(i == 0) {
                textReadList.add(i+1 + "st vehicle VRM is " + aSearchVehicleListBean.get(i).getVrm() + ", make is " + aSearchVehicleListBean.get(i).getMake() + ", model is " + aSearchVehicleListBean.get(i).getModel() + " and color is " + aSearchVehicleListBean.get(i).getColour() + ".");
            } else if(i == 1) {
                textReadList.add(i+1 + "nd vehicle VRM is " + aSearchVehicleListBean.get(i).getVrm() + ", make is " + aSearchVehicleListBean.get(i).getMake() + ", model is " + aSearchVehicleListBean.get(i).getModel() + " and color is " + aSearchVehicleListBean.get(i).getColour() + ".");
            } else if (i == 2) {
                textReadList.add(i+1 + "rd vehicle VRM is " + aSearchVehicleListBean.get(i).getVrm() + ", make is " + aSearchVehicleListBean.get(i).getMake() + ", model is " + aSearchVehicleListBean.get(i).getModel() + " and color is " + aSearchVehicleListBean.get(i).getColour() + ".");
            } else {
                textReadList.add(i+1 + "th vehicle VRM is " + aSearchVehicleListBean.get(i).getVrm() + ", make is " + aSearchVehicleListBean.get(i).getMake() + ", model is " + aSearchVehicleListBean.get(i).getModel() + " and color is " + aSearchVehicleListBean.get(i).getColour() + ".");
            }
        }
        partition = nPartition(textReadList, 3);
    }

    public static void readData() {
        if(listPos < partition.size()) {
            if (listPos == 0) {
                VehicleReadDialogFragment.isListen = true;
                VehicleReadDialogFragment.setFlag(false, true);
                textRead = "Found " + textReadList.size() + " records for stops." +
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
    public void loadStopsVehicle() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.VEHICLE_STOPS_LIST_JSON);
                VehicleSTOPResponse vehicleSTOPResponse = (VehicleSTOPResponse) JsonUtil.getInstance().toModel(strJson, VehicleSTOPResponse.class);
                if (vehicleSTOPResponse != null) {
                    aSearchVehicleListBean = (ArrayList<VehicleSTOPResponse.SearchVehicleListBean>) vehicleSTOPResponse.getSearchvehiclelist();
                    /*if (aSearchVehicleListBean != null && aSearchVehicleListBean.size() > 0) {
                        aSearchVehicleListBean = BasicMethodsUtil.getInstance().searchPNCPerson(aSearchVehicleListBean, searchedWord);
                    }*/
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (aSearchVehicleListBean != null && aSearchVehicleListBean.size() > 0) {
                        setVehiclPagerData();

                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), getString(R.string.no_data));
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), VehicleSTOPSFragment.class, "loadStopsVehicle");
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
        stopsDetailDialogFragment = STOPSDetailDialogFragment.newInstance(GenericConstant.TYPE_VEHICLE, isPopulate);
        stopsDetailDialogFragment.show(ft, GenericConstant.DETAILS_DIALOG);
    }


    @Override
    public void onItemClicked(int clickType) {
        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_NAME, GenericConstant.VEHICLE_STOPS_DETAIL);
        loadSTOPSDetailsDialog();

    }
}
