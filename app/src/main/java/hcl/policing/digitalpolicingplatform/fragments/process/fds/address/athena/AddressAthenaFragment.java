package hcl.policing.digitalpolicingplatform.fragments.process.fds.address.athena;

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
import hcl.policing.digitalpolicingplatform.adapters.process.fds.address.AddressListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.AddressReadDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.PersonReadDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.athena.AthenaListDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.address.AddressSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.athena.AddressAthenaResponse;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class AddressAthenaFragment extends Fragment implements ItemClickListener {

    private Context mContext;
    private RecyclerView rvPersonList;
    private ArrayList<AddressAthenaResponse.AddressListBean> aAddressListBean;
    private AddressSelectionListener addressSelectionListener;
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
        View view = inflater.inflate(R.layout.address_fragment, container, false);

        if (getArguments() != null) {
            searchedWord = getArguments().getString(GenericConstant.SEARCH_KEYWORD);
            addressSelectionListener = (AddressSelectionListener) getArguments().getSerializable("listener");
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
        loadAthenaAddressList();
    }

    /**
     * Set the recycler view property
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
    private void setVehiclPagerData() {
        if(aAddressListBean != null && aAddressListBean.size() > 0) {
            AddressListAdaptor addressListAdaptor = new AddressListAdaptor(mContext, GenericConstant.TYPE_ATHENA,
                    aAddressListBean, addressSelectionListener, this, isPopulate);
            rvPersonList.setAdapter(addressListAdaptor);

            setDataToRead();
        }
    }

    private void setDataToRead() {
        textReadList = new ArrayList<>();
        for (int i = 0; i < aAddressListBean.size(); i++) {
            if(i == 0) {
                textReadList.add(i+1 + "st address is of " + aAddressListBean.get(i).getPostcode() + " postcode. " + aAddressListBean.get(i).getStreet() + " street, " + aAddressListBean.get(i).getTown() + " town, " + aAddressListBean.get(i).getCounty() + " county.");
            } else if(i == 1) {
                textReadList.add(i+1 + "nd address is of " + aAddressListBean.get(i).getPostcode() + " postcode. " + aAddressListBean.get(i).getStreet() + " street, " + aAddressListBean.get(i).getTown() + " town, " + aAddressListBean.get(i).getCounty() + " county.");
            } else if (i == 2) {
                textReadList.add(i+1 + "rd address is of " + aAddressListBean.get(i).getPostcode() + " postcode. " + aAddressListBean.get(i).getStreet() + " street, " + aAddressListBean.get(i).getTown() + " town, " + aAddressListBean.get(i).getCounty() + " county.");
            } else {
                textReadList.add(i+1 + "th address is of " + aAddressListBean.get(i).getPostcode() + " postcode. " + aAddressListBean.get(i).getStreet() + " street, " + aAddressListBean.get(i).getTown() + " town, " + aAddressListBean.get(i).getCounty() + " county.");
            }
        }
        partition = nPartition(textReadList, 3);
    }

    public static void readData() {
        if(listPos < partition.size()) {
            if (listPos == 0) {
                AddressReadDialogFragment.isListen = true;
                AddressReadDialogFragment.setFlag(false, true);
                textRead = "Found " + textReadList.size() + " records for Athena." +
                        partition.get(listPos) + "\n"
                        + " Do you want me to read more records.";
            } else if (listPos > 0 && listPos < partition.size()-1) {
                AddressReadDialogFragment.setFlag(false, true);
                AddressReadDialogFragment.isListen = true;
                textRead = partition.get(listPos).toString() + "\n"
                        + " Do you want me to read more records.";
            } else {
                AddressReadDialogFragment.isListen = false;
                AddressReadDialogFragment.setFlag(false, true);
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
    public void loadAthenaAddressList() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.ADDRESS_ATHENA_LIST_JSON);
                AddressAthenaResponse athenaAddressResponse = (AddressAthenaResponse) JsonUtil.getInstance().toModel(strJson, AddressAthenaResponse.class);
                if (athenaAddressResponse != null) {
                    aAddressListBean = (ArrayList<AddressAthenaResponse.AddressListBean>) athenaAddressResponse.getSearchathenalistresponse().getAddresslist();
                    /*if (aAddressListBean != null && aAddressListBean.size() > 0) {
                        aAddressListBean = BasicMethodsUtil.getInstance().searchPNCPerson(aAddressListBean, searchedWord);
                    }*/
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (aAddressListBean != null && aAddressListBean.size() > 0) {
                        setVehiclPagerData();

                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), getString(R.string.no_data));
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), AddressAthenaFragment.class, "loadAthenaAddressList");
            }
        }).start();
    }

    @Override
    public void onItemClicked(int clickType) {
        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_NAME, GenericConstant.ADDRESS_ATHENA_DETAIL);
        loadATHENADetailsDialog();
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
        athenaListDialogFragment = AthenaListDialogFragment.newInstance(GenericConstant.TYPE_ADDRESS, isPopulate);
        athenaListDialogFragment.show(ft, GenericConstant.DETAILS_DIALOG);
    }
}
