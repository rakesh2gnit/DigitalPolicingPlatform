package hcl.policing.digitalpolicingplatform.fragments.process.fds.address.nflms;

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
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.nflms.NFLMSDetailDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.address.AddressSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.address.nflms.AddressNFLMSResponse;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class AddressNFLMSFragment extends Fragment implements ItemClickListener {

    private Context mContext;
    private RecyclerView rvPersonList;
    private ArrayList<AddressNFLMSResponse.SearchResultBean> aSearchResultBean;
    private AddressSelectionListener addressSelectionListener;
    private Dialog mProgressDialog;
    private String searchedWord;
    private NFLMSDetailDialogFragment nflmsDetailDialogFragment;
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
         * Load the NFLMS System Address list
         */
        loadNFLMSAddressList();
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
    private void setVehiclPagerData() {
        if(aSearchResultBean != null && aSearchResultBean.size() > 0) {
            AddressListAdaptor addressListAdaptor = new AddressListAdaptor(mContext, GenericConstant.TYPE_NFLMS,
                    aSearchResultBean, addressSelectionListener, this, isPopulate);
            rvPersonList.setAdapter(addressListAdaptor);

            setDataToRead();
        }
    }

    private void setDataToRead() {
        textReadList = new ArrayList<>();
        for (int i = 0; i < aSearchResultBean.size(); i++) {
            if(i == 0) {
                textReadList.add(i+1 + "st address is " + aSearchResultBean.get(i).getAddress() + ".");
            } else if(i == 1) {
                textReadList.add(i+1 + "nd address is " + aSearchResultBean.get(i).getAddress() + ".");
            } else if (i == 2) {
                textReadList.add(i+1 + "rd address is " + aSearchResultBean.get(i).getAddress() + ".");
            } else {
                textReadList.add(i+1 + "th address is " + aSearchResultBean.get(i).getAddress() + ".");
            }
        }
        partition = nPartition(textReadList, 3);
    }

    public static void readData() {
        if(listPos < partition.size()) {
            if (listPos == 0) {
                AddressReadDialogFragment.isListen = true;
                AddressReadDialogFragment.setFlag(false, true);
                textRead = "Found " + textReadList.size() + " records for NFLMS." +
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
    public void loadNFLMSAddressList() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.ADDRESS_NFLMS_LIST_JSON);
                AddressNFLMSResponse addressNFLMSResponse = (AddressNFLMSResponse) JsonUtil.getInstance().toModel(strJson, AddressNFLMSResponse.class);
                if (addressNFLMSResponse != null) {
                    aSearchResultBean = (ArrayList<AddressNFLMSResponse.SearchResultBean>) addressNFLMSResponse.getSearchresult();
                    /*if (aSearchResultBean != null && aSearchResultBean.size() > 0) {
                        aSearchResultBean = BasicMethodsUtil.getInstance().searchPNCPerson(aSearchResultBean, searchedWord);
                    }*/
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (aSearchResultBean != null && aSearchResultBean.size() > 0) {
                        setVehiclPagerData();

                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), getString(R.string.no_data));
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), AddressNFLMSFragment.class, "loadNFLMSAddressList");
            }
        }).start();
    }

    @Override
    public void onItemClicked(int clickType) {
        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_NAME, GenericConstant.ADDRESS_NFLMS_DETAIL);
        loadNFLMSDetailsDialog();
    }

    /**
     * Load Person Dialog
     */
    public void loadNFLMSDetailsDialog() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.DETAILS_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        nflmsDetailDialogFragment = NFLMSDetailDialogFragment.newInstance(GenericConstant.TYPE_ADDRESS, isPopulate);
        nflmsDetailDialogFragment.show(ft, GenericConstant.DETAILS_DIALOG);
    }
}
