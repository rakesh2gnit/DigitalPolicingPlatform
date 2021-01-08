package hcl.policing.digitalpolicingplatform.fragments.process.fds.dlcheck;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.adapters.process.fds.dlcheck.DLCheckListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.athena.AddressAthenaFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.dl.DLDetailDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.dialog.ManuallyClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.dlchek.DlCheckSelectionListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.dl.DLResponse;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.dl.DrivinglicencebyidlistModel;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class DLCheckDialogFragment extends DialogFragment implements ItemClickListener, View.OnClickListener {

    private Context mContext;
    private LinearLayout rlMain, llHeader;
    private RecyclerView rvPersonList;
    private ArrayList<DrivinglicencebyidlistModel> aDrivinglicencebyidlistModel;
    private DlCheckSelectionListener dlCheckSelectionListener;
    private ManuallyClickListener manuallyClickListener;
    private Dialog mProgressDialog;
    private String searchedWord;
    private boolean isPopulate;

    public static DLCheckDialogFragment newInstance(String searchPerson, DlCheckSelectionListener listener, boolean isPopulate) {

        DLCheckDialogFragment frag = new DLCheckDialogFragment();
        Bundle args = new Bundle();
        args.putString(GenericConstant.SEARCH_KEYWORD, searchPerson);
        args.putSerializable(GenericConstant.LISTENER, listener);
        args.putBoolean(GenericConstant.IS_POPULATE, isPopulate);
        frag.setArguments(args);
        return frag;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        dialog.getWindow().getAttributes().windowAnimations = R.style.custom_dialog;
        // request a window without the title
        dialog.getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        return dialog;
    }

    @Override
    public void onStart() {
        super.onStart();

        Dialog dialog = getDialog();
        if (dialog != null) {
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        manuallyClickListener = (ManuallyClickListener) getActivity();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        View view = inflater.inflate(R.layout.dl_fragment, container, false);

        if (getArguments() != null) {
            searchedWord = getArguments().getString(GenericConstant.SEARCH_KEYWORD);
            dlCheckSelectionListener = (DlCheckSelectionListener) getArguments().getSerializable(GenericConstant.LISTENER);
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

        rlMain = view.findViewById(R.id.rlMain);
        llHeader = view.findViewById(R.id.llHeader);
        TextView tvManually = view.findViewById(R.id.tvManually);
        TextView tvPersonHeader = view.findViewById(R.id.tvPersonHeader);

        rvPersonList = view.findViewById(R.id.rvPersonList);
        tvPersonHeader.setText(getString(R.string.select_dl));

        isPopulateView();
        setRecyclerViewProperty(mContext, rvPersonList);


        /**
         * Load the DLCheck System address list
         */
        loadDlCheckList();
        tvManually.setOnClickListener(this);
    }

    /**
     * Check the condition for populate view.
     */
    private void isPopulateView() {
        if (!isPopulate) {
            llHeader.setVisibility(View.GONE);
            CoordinatorLayout.LayoutParams relativeParams = new CoordinatorLayout.LayoutParams(CoordinatorLayout.LayoutParams.MATCH_PARENT, CoordinatorLayout.LayoutParams.MATCH_PARENT);
            relativeParams.setMargins(0, 0, 0, 0);
            rlMain.setLayoutParams(relativeParams);
        }
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.tvManually:

                manuallyClickListener.onManuallyClicker(GenericConstant.TYPE_ADDRESS);

                break;
        }
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
     * Set DLCheck PagerAdaptor
     */
    private void setDLCheckPagerData() {
        if (aDrivinglicencebyidlistModel != null && aDrivinglicencebyidlistModel.size() > 0) {
            DLCheckListAdaptor dlCheckListAdaptor = new DLCheckListAdaptor(aDrivinglicencebyidlistModel,
                    dlCheckSelectionListener, this, isPopulate);
            rvPersonList.setAdapter(dlCheckListAdaptor);
        }
    }

    /**
     * Load DL Check JSON
     */
    private void loadDlCheckList() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.DLCHECK_DL_LIST_JSON);
                DLResponse dlResponse = (DLResponse) JsonUtil.getInstance().toModel(strJson, DLResponse.class);
                if (dlResponse != null) {
                    aDrivinglicencebyidlistModel = (ArrayList<DrivinglicencebyidlistModel>) dlResponse.getDrivinglicencebyidlist();
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (aDrivinglicencebyidlistModel != null && aDrivinglicencebyidlistModel.size() > 0) {
                        setDLCheckPagerData();

                    } else {
                        BasicMethodsUtil.getInstance().showToast(getActivity(), getString(R.string.no_data));
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), AddressAthenaFragment.class, "loadCaseAthenaList");
            }
        }).start();
    }

    /**
     * Load DL details Dialog
     */
    private void loadDLDetailsDialog() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.DETAILS_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        DLDetailDialogFragment dlDetailDialogFragment = DLDetailDialogFragment.newInstance(GenericConstant.TYPE_DL_CHECK, isPopulate);
        dlDetailDialogFragment.show(ft, GenericConstant.DETAILS_DIALOG);
    }


    @Override
    public void onItemClicked(int clickType) {
        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_NAME, GenericConstant.DL_CHECK_DETAIL);
        loadDLDetailsDialog();

    }
}
