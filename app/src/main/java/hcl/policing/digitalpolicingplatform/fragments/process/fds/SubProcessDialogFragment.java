package hcl.policing.digitalpolicingplatform.fragments.process.fds;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.activities.BaseActivity;
import hcl.policing.digitalpolicingplatform.activities.process.ProcessCreationActivity;
import hcl.policing.digitalpolicingplatform.adapters.process.SubProcessListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.database.ProcessSubProcessMapper;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.address.AddressCustomDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.cases.CasesDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.communications.CommunicationDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.courtwarrant.CourtWarrantDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.dlcheck.DLCheckDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.investigation.InvestigationDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.PersonCustomDialogFragment;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.vehicle.VehicleCustomDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.dlchek.DlCheckSelectionListener;
import hcl.policing.digitalpolicingplatform.models.layoutHelper.AnswerValueDTO;
import hcl.policing.digitalpolicingplatform.models.process.ProcessLogDTO;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.PersonBean;
import hcl.policing.digitalpolicingplatform.models.search.ResponseSearchModel;
import hcl.policing.digitalpolicingplatform.models.search.SearchListBean;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;
import hcl.policing.digitalpolicingplatform.utils.SearchDialogUtil;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class SubProcessDialogFragment extends DialogFragment implements DlCheckSelectionListener {

    private Context mContext;
    private AppSession appSession;
    private RecyclerView rvSubProcessList;
    private Dialog mProgressDialog;
    private ArrayList<ProcessSubProcessMapper> aProcessSubProcessMapper;
    private String captureValue = "Peter Parker";
    private List<SearchListBean> aSearchListBean;
    private int processId, subProcessId;
    private String processName;

    public static SubProcessDialogFragment newInstance(ArrayList<ProcessSubProcessMapper> subProcessList) {

        SubProcessDialogFragment frag = new SubProcessDialogFragment();
        Bundle args = new Bundle();
        args.putSerializable(GenericConstant.SUB_PROCESS_LIST, subProcessList);
        frag.setArguments(args);
        return frag;

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        loadSearchJSON();
    }

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
            dialog.getWindow().setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        appSession = new AppSession(mContext);
        View view = inflater.inflate(R.layout.sub_process_fragment, container, false);

        if (getArguments() != null) {
            aProcessSubProcessMapper = (ArrayList<ProcessSubProcessMapper>) getArguments().getSerializable(GenericConstant.SUB_PROCESS_LIST);
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

        rvSubProcessList = view.findViewById(R.id.rvSubProcessList);
        setRecyclerViewProperty(mContext, rvSubProcessList);

        /**
         * Load the Sub Process List
         */
        setSubPorcessAdaptor();
    }

    /**
     * Set the recycler view property
     *
     * @param context
     * @param rvComponentList
     */
    private static void setRecyclerViewProperty(Context context, RecyclerView rvComponentList) {
        rvComponentList.setLayoutManager(new GridLayoutManager(context, 2));
        rvComponentList.setItemAnimator(new DefaultItemAnimator());
    }

    /**
     * Set Sub Process PagerAdaptor
     */
    private void setSubPorcessAdaptor() {
        if (aProcessSubProcessMapper != null && aProcessSubProcessMapper.size() > 0) {
            SubProcessListAdaptor subProcessListAdaptor = new SubProcessListAdaptor(mContext, aProcessSubProcessMapper, itemClickListener);
            rvSubProcessList.setAdapter(subProcessListAdaptor);
        }
    }

    private OnItemClickListener.OnItemClickCallback itemClickListener = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            dismiss();
            processId = aProcessSubProcessMapper.get(0).getProcessId();
            processName = aProcessSubProcessMapper.get(0).getProcessName();
            subProcessId = aProcessSubProcessMapper.get(position).getSubProcessId();
            String flowStr = ((BaseActivity) getActivity()).isProcessFlow(subProcessId);
            Log.e("FLOW STRING ", " DIALOG FRAG >>>>> " + flowStr);
            if (!TextUtils.isEmpty(flowStr)){
                ((BaseActivity) getActivity()).loadFlow(processName, flowStr);
            }else {
                ((BaseActivity) getActivity()).getProcessFlowApi(processId, subProcessId);
            }

/*            switch (subProcessId) {

                case 100:
                    SearchDialogUtil.createSearchDialog(SubProcessDialogFragment.this, aSearchListBean, GenericConstant.SEARCH_PERSON, null);
                    break;

                case 101:
                    SearchDialogUtil.createSearchDialog(SubProcessDialogFragment.this, aSearchListBean, GenericConstant.SEARCH_VEHICLE, null);
                    break;

                case 102:
                    SearchDialogUtil.createSearchDialog(SubProcessDialogFragment.this, aSearchListBean, GenericConstant.SEARCH_ADDRESS, null);
                    break;

                case 103:
                    SearchDialogUtil.createSearchDialog(SubProcessDialogFragment.this, aSearchListBean, GenericConstant.SEARCH_INVESTIGATION, null);
                    break;

                case 104:
                    SearchDialogUtil.createSearchDialog(SubProcessDialogFragment.this, aSearchListBean, GenericConstant.SEARCH_CASES, null);
                    break;

                case 105:
                    SearchDialogUtil.createSearchDialog(SubProcessDialogFragment.this, aSearchListBean, GenericConstant.SEARCH_DL_CHECK, null);
                    break;

                case 106:
                    SearchDialogUtil.createSearchDialog(SubProcessDialogFragment.this, aSearchListBean, GenericConstant.SEARCH_COMMUNICATION, null);
                    break;

                case 107:
                    SearchDialogUtil.createSearchDialog(SubProcessDialogFragment.this, aSearchListBean, GenericConstant.SEARCH_COURT_WARRANT, null);
                    break;

            }*/
        }
    };

    /**
     * Load Person Dialog
     */
    public void loadPersonDialog(ArrayList<AnswerValueDTO> searchList) {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.PERSON_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        PersonCustomDialogFragment personCustomDialogFragment = PersonCustomDialogFragment.newInstance(captureValue, "", false);
        personCustomDialogFragment.show(ft, GenericConstant.PERSON_LIST_DIALOG);

        String searchText = "person ";

        for (int i = 0; i < searchList.size(); i++) {
            if(searchList.get(i).getKey().equalsIgnoreCase("Forename")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {
                searchText = searchText + searchList.get(i).getValue();

            } else if(searchList.get(i).getKey().equalsIgnoreCase("Surname")

                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {
                searchText = searchText + " " + searchList.get(i).getValue();

            } else if(searchList.get(i).getKey().equalsIgnoreCase("Reason For Search")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {
                searchText = searchText + "\nReason For Search - " + searchList.get(i).getValue();
            }
        }
        saveLog(searchText);
    }

    /**
     * Load Vehicle Dialog
     */
    public void loadVehicleDialog(ArrayList<AnswerValueDTO> searchList) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.VEHICLE_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        VehicleCustomDialogFragment vehicleCustomDialogFragment = VehicleCustomDialogFragment.newInstance(captureValue, "", false);
        vehicleCustomDialogFragment.show(ft, GenericConstant.VEHICLE_LIST_DIALOG);

        String searchText = "vehicle ";

        for (int i = 0; i < searchList.size(); i++) {
            if(searchList.get(i).getKey().equalsIgnoreCase("VRM")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {

                searchText = searchText + searchList.get(i).getValue();

            } else if(searchList.get(i).getKey().equalsIgnoreCase("Reason For Search")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {
                searchText = searchText + "\nReason For Search - " + searchList.get(i).getValue();
            }
        }
        saveLog(searchText);
    }

    /**
     * Load Address Dialog
     */
    public void loadAddressDialog(ArrayList<AnswerValueDTO> searchList) {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.ADDRESS_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        AddressCustomDialogFragment addressCustomDialogFragment = AddressCustomDialogFragment.newInstance(captureValue, "", false);
        addressCustomDialogFragment.show(ft, GenericConstant.ADDRESS_LIST_DIALOG);

        String searchText = "address";

        for (int i = 0; i < searchList.size(); i++) {
            if(!searchList.get(i).getKey().equalsIgnoreCase("Reason For Search")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {

                searchText = searchText + " " + searchList.get(i).getKey() + " " + searchList.get(i).getValue();

            } else if(searchList.get(i).getKey().equalsIgnoreCase("Reason For Search")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {
                searchText = searchText + "\nReason For Search - " + searchList.get(i).getValue();
            }
        }
        saveLog(searchText);
    }

    /**
     * Load CASE Dialog
     */
    public void loadCasesDialog(ArrayList<AnswerValueDTO> searchList) {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.CASE_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        CasesDialogFragment casesDialogFragment = CasesDialogFragment.newInstance(captureValue, "", false);
        casesDialogFragment.show(ft, GenericConstant.CASE_LIST_DIALOG);

        String searchText = "case";

        for (int i = 0; i < searchList.size(); i++) {
            if(!searchList.get(i).getKey().equalsIgnoreCase("Reason For Search")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {

                searchText = searchText + " " + searchList.get(i).getKey() + " " + searchList.get(i).getValue();

            } else if(searchList.get(i).getKey().equalsIgnoreCase("Reason For Search")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {
                searchText = searchText + "\nReason For Search - " + searchList.get(i).getValue();
            }
        }
        saveLog(searchText);
    }

    /**
     * Load Communication Dialog
     */
    public void loadCommunicationDialog(ArrayList<AnswerValueDTO> searchList) {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.COMMUNICATION_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        CommunicationDialogFragment communicationDialogFragment = CommunicationDialogFragment.newInstance(captureValue, "", false);
        communicationDialogFragment.show(ft, GenericConstant.COMMUNICATION_LIST_DIALOG);

        String searchText = "communication";

        for (int i = 0; i < searchList.size(); i++) {
            if(!searchList.get(i).getKey().equalsIgnoreCase("Reason For Search")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {

                searchText = searchText + " " + searchList.get(i).getKey() + " " + searchList.get(i).getValue();

            } else if(searchList.get(i).getKey().equalsIgnoreCase("Reason For Search")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {
                searchText = searchText + "\nReason For Search - " + searchList.get(i).getValue();
            }
        }
        saveLog(searchText);
    }

    /**
     * Load Court Warrant Dialog
     */
    public void loadCourtWarrantDialog(ArrayList<AnswerValueDTO> searchList) {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.COURTWARRANT_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        CourtWarrantDialogFragment courtWarrantDialogFragment = CourtWarrantDialogFragment.newInstance(captureValue, "", false);
        courtWarrantDialogFragment.show(ft, GenericConstant.COURTWARRANT_LIST_DIALOG);

        String searchText = "court warrant";

        for (int i = 0; i < searchList.size(); i++) {
            if(!searchList.get(i).getKey().equalsIgnoreCase("Reason For Search")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {

                searchText = searchText + " " + searchList.get(i).getKey() + " " + searchList.get(i).getValue();

            } else if(searchList.get(i).getKey().equalsIgnoreCase("Reason For Search")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {
                searchText = searchText + "\nReason For Search - " + searchList.get(i).getValue();
            }
        }
        saveLog(searchText);
    }

    /**
     * Load Court Warrant Dialog
     */
    public void loadInvestigationDialog(ArrayList<AnswerValueDTO> searchList) {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.INVESTIGATION_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        InvestigationDialogFragment investigationDialogFragment = InvestigationDialogFragment.newInstance(captureValue, "", false);
        investigationDialogFragment.show(ft, GenericConstant.INVESTIGATION_LIST_DIALOG);

        String searchText = "investigation";

        for (int i = 0; i < searchList.size(); i++) {
            if(!searchList.get(i).getKey().equalsIgnoreCase("Reason For Search")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {

                searchText = searchText + " " + searchList.get(i).getKey() + " " + searchList.get(i).getValue();

            } else if(searchList.get(i).getKey().equalsIgnoreCase("Reason For Search")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {
                searchText = searchText + "\nReason For Search - " + searchList.get(i).getValue();
            }
        }
        saveLog(searchText);
    }

    /**
     * Load DL Check Dialog
     */
    public void loadDLCheckDialog(ArrayList<AnswerValueDTO> searchList) {

        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.DLCHECK_LIST_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);

        // Create and show the dialog.
        DLCheckDialogFragment dlCheckDialogFragment = DLCheckDialogFragment.newInstance(captureValue, this, false);
        dlCheckDialogFragment.show(ft, GenericConstant.DLCHECK_LIST_DIALOG);

        String searchText = "DL";

        for (int i = 0; i < searchList.size(); i++) {
            if(!searchList.get(i).getKey().equalsIgnoreCase("Reason For Search")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {

                searchText = searchText + " " + searchList.get(i).getKey() + " " + searchList.get(i).getValue();

            } else if(searchList.get(i).getKey().equalsIgnoreCase("Reason For Search")
                    && !searchList.get(i).getValue().equalsIgnoreCase("")) {
                searchText = searchText + "\nReason For Search - " + searchList.get(i).getValue();
            }
        }
        saveLog(searchText);
    }

    @Override
    public void onDlCheckSelected(PersonBean personBean) {

    }

    /**
     * Load Initial JSON
     */
    private void loadSearchJSON() {
        new Thread(() -> {
            try {
                String strJson = JsonUtil.getInstance().loadJsonFromAsset(mContext, GenericConstant.SEARCH_FIELDS);
                ResponseSearchModel responseModel = (ResponseSearchModel) JsonUtil.getInstance().toModel(strJson, ResponseSearchModel.class);

                getActivity().runOnUiThread(() -> {
                    if (responseModel != null) {
                        aSearchListBean = responseModel.getSearchList();
                    }
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), ProcessCreationActivity.class, "loadSearchJSON");
            }
        }).start();
    }

    public void saveLog(String search) {

        long time = System.currentTimeMillis();
        String backDate = getBackDate(time);
        //Utilities.getInstance(mContext).writeFile(mainJSON.toString(), fileName, directoryDraft);

        ArrayList<ProcessLogDTO> fdsList = new ArrayList<>();
        if(appSession.getFDSList() != null) {
            fdsList.addAll(appSession.getFDSList());
        }
        if(fdsList != null && fdsList.size() > 0) {
            for (Iterator<ProcessLogDTO> iterator = fdsList.iterator(); iterator.hasNext();) {
                ProcessLogDTO obj = iterator.next();
                int val = compareDates(obj.getDate(), backDate);
                if (val == 1) {
                    // Remove the current element from the iterator and the list.
                    iterator.remove();
                }
            }
        }
        ProcessLogDTO draftDTO = new ProcessLogDTO();
        draftDTO.setProcessName(getResources().getString(R.string.fds_search));
        draftDTO.setDisplayText("Searched " + search);
        draftDTO.setFileType(GenericConstant.FDS_FILE);
        draftDTO.setFilePath("");
        draftDTO.setFileName("");
        draftDTO.setTime(getDateTime(time));
        draftDTO.setDate(getDate(time));
        draftDTO.setGroupFlag("0");
        draftDTO.setPinFlag("0");
        fdsList.add(draftDTO);
        appSession.setFDSList(null);
        appSession.setFDSList(fdsList);
    }

    /**
     * Get the date from file
     *
     * @param longTime
     * @return
     */
    private String getDateTime(long longTime) {

        String date = "";

        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a", Locale.US);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(longTime);
        date = formatter.format(calendar.getTime());

        return date;
    }

    /**
     * Get the date from file
     *
     * @param longTime
     * @return
     */
    private String getDate(long longTime) {

        String date = "";

        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(longTime);
        date = formatter.format(calendar.getTime());

        return date;
    }

    /**
     * Get the date from file
     *
     * @param longTime
     * @return
     */
    private String getBackDate(long longTime) {

        String date = "";

        // Create a DateFormatter object for displaying date in specified format.
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        // Create a calendar object that will convert the date and time value in milliseconds to date.
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(longTime);
        calendar.add(Calendar.DATE, -15);
        date = formatter.format(calendar.getTime());

        return date;
    }

    private int compareDates(String listDate, String backDate) {
        SimpleDateFormat formatter = new SimpleDateFormat("dd MMM yyyy", Locale.US);

        Date d1 = null;
        Date d2 = null;
        try {
            d1 = formatter.parse(listDate);
            d2 = formatter.parse(backDate);
            Log.e("D1 ", " >>>>> " + d1);
            Log.e("D2 ", " >>>>> " + d2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if(d1.compareTo(d2) > 0) {
            //System.out.println("Date 1 occurs after Date 2");
            return 0;
        } else if(d1.compareTo(d2) < 0) {
            return 1;
            //System.out.println("Date 1 occurs before Date 2");
        } else if(d1.compareTo(d2) == 0) {
            return 0;
            //System.out.println("Both dates are equal");
        }
        return 0;
    }
}
