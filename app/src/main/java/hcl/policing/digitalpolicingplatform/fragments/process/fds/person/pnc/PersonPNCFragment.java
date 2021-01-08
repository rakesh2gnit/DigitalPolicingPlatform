package hcl.policing.digitalpolicingplatform.fragments.process.fds.person.pnc;

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
import hcl.policing.digitalpolicingplatform.adapters.process.fds.person.pnc.PersonPNCListAdaptor;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.fragments.process.fds.person.PersonReadDialogFragment;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.ItemClickListener;
import hcl.policing.digitalpolicingplatform.listeners.process.fds.person.PersonSelectionListener;
import hcl.policing.digitalpolicingplatform.models.process.fds.person.pnc.PersonPNCResponse;
import hcl.policing.digitalpolicingplatform.prefs.SharedPrefUtils;
import hcl.policing.digitalpolicingplatform.utils.BasicMethodsUtil;
import hcl.policing.digitalpolicingplatform.utils.DialogUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.JsonUtil;

public class PersonPNCFragment extends Fragment implements ItemClickListener {

    private Context mContext;
    private RecyclerView rvPersonList;
    private TextView tvNoData;
    private ArrayList<PersonPNCResponse.PNCPersonlistBean> aPNCPersonlistBean;
    private PersonSelectionListener personSelectionListener;
    private Dialog mProgressDialog;
    private String searchedWord;
    private PersonPNCDetailDialogFragment personPNCDetailDialogFragment;
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
        View view = inflater.inflate(R.layout.person_fragment, container, false);

        if (getArguments() != null) {
            searchedWord = getArguments().getString(GenericConstant.SEARCH_KEYWORD);
            personSelectionListener = (PersonSelectionListener) getArguments().getSerializable("listener");
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
         * Load the PNC System Person list
         */
        loadPNCPersons();
    }

    private static void setRecyclerViewProperty(Context context, RecyclerView rvComponentList) {
        rvComponentList.setHasFixedSize(true);
        LinearLayoutManager layoutmanager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        rvComponentList.setLayoutManager(layoutmanager);
    }


    /**
     * Set Person PNC in PagerAdaptor
     */
    private void setPersonPagerData() {
        if (aPNCPersonlistBean != null && aPNCPersonlistBean.size() > 0) {
            PersonPNCListAdaptor personPNCListAdaptor = new PersonPNCListAdaptor(mContext, aPNCPersonlistBean, personSelectionListener,
                    this,isPopulate);
            rvPersonList.setAdapter(personPNCListAdaptor);

            setDataToRead();
        }
    }

    private void setDataToRead() {
        textReadList = new ArrayList<>();
        for (int i = 0; i < aPNCPersonlistBean.size(); i++) {
            if(i == 0) {
                textReadList.add(i+1 + "st person name is " + aPNCPersonlistBean.get(i).getFirstname() + " " + aPNCPersonlistBean.get(i).getLastname() + " his / her Date Of Birth is " + aPNCPersonlistBean.get(i).getDob() + ".");
            } else if(i == 1) {
                textReadList.add(i+1 + "nd person name is " + aPNCPersonlistBean.get(i).getFirstname() + " " + aPNCPersonlistBean.get(i).getLastname() + " his / her Date Of Birth is " + aPNCPersonlistBean.get(i).getDob() + ".");
            } else if (i == 2) {
                textReadList.add(i+1 + "rd person name is " + aPNCPersonlistBean.get(i).getFirstname() + " " + aPNCPersonlistBean.get(i).getLastname() + " his / her Date Of Birth is " + aPNCPersonlistBean.get(i).getDob() + ".");
            } else {
                textReadList.add(i+1 + "th person name is " + aPNCPersonlistBean.get(i).getFirstname() + " " + aPNCPersonlistBean.get(i).getLastname() + " his / her Date Of Birth is " + aPNCPersonlistBean.get(i).getDob() + ".");
            }
        }
        partition = nPartition(textReadList, 3);
    }

    public static void readData() {
        if(listPos < partition.size()) {
            if (listPos == 0) {
                PersonReadDialogFragment.isListen = true;
                PersonReadDialogFragment.setFlag(false, true);
                textRead = "Found " + textReadList.size() + " records for PNC." +
                        partition.get(listPos) + "\n"
                        + " Do you want me to read more records.";
            } else if (listPos > 0 && listPos < partition.size()-1) {
                PersonReadDialogFragment.setFlag(false, true);
                PersonReadDialogFragment.isListen = true;
                textRead = partition.get(listPos).toString() + "\n"
                        + " Do you want me to read more records.";
            } else {
                PersonReadDialogFragment.isListen = false;
                PersonReadDialogFragment.setFlag(false, true);
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
    public void loadPNCPersons() {

        mProgressDialog = DialogUtil.showProgressDialog(getActivity());
        new Thread(() -> {
            try {

                String strJson = JsonUtil.getInstance().loadJsonFromAsset(getActivity(), GenericConstant.PERSON_PNC_LIST_JSON);
                PersonPNCResponse personPncResponse = (PersonPNCResponse) JsonUtil.getInstance().toModel(strJson, PersonPNCResponse.class);
                if (personPncResponse != null) {
                    aPNCPersonlistBean = (ArrayList<PersonPNCResponse.PNCPersonlistBean>) personPncResponse.getPersonbyname().getPersonlist();
                    /*if (aPNCPersonlistBean != null && aPNCPersonlistBean.size() > 0) {
                        aPNCPersonlistBean = BasicMethodsUtil.getInstance().searchPNCPerson(aPNCPersonlistBean, searchedWord);
                    }*/
                }
                getActivity().runOnUiThread(() -> {
                    DialogUtil.cancelProgressDialog(mProgressDialog);
                    if (aPNCPersonlistBean != null && aPNCPersonlistBean.size() > 0) {
                        setPersonPagerData();

                    } else {
                        BasicMethodsUtil.getInstance().noDataAvailable(rvPersonList, tvNoData);
                    }

                });

            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), PersonPNCFragment.class, "loadPNCPersons");
            }
        }).start();
    }


    @Override
    public void onItemClicked(int clickType) {
        SharedPrefUtils.getInstance(mContext).setString(SharedPrefUtils.Key.SYSTEM_NAME, GenericConstant.PERSON_PNC_DETAIL);
        loadPNCDetailsDialog();
    }

    /**
     * Load Person Dialog
     */
    public void loadPNCDetailsDialog() {
        FragmentTransaction ft = getActivity().getSupportFragmentManager().beginTransaction();
        Fragment prev = getActivity().getSupportFragmentManager().findFragmentByTag(GenericConstant.DETAILS_DIALOG);
        if (prev != null) {
            ft.remove(prev);
        }
        ft.addToBackStack(null);
        // Create and show the dialog.
        personPNCDetailDialogFragment = PersonPNCDetailDialogFragment.newInstance(GenericConstant.TYPE_PERSON,isPopulate);
        personPNCDetailDialogFragment.show(ft, GenericConstant.DETAILS_DIALOG);
    }

}
