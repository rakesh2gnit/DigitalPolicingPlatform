package hcl.policing.digitalpolicingplatform.fragments.pocketbook;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.Objects;

import hcl.policing.digitalpolicingplatform.R;
import hcl.policing.digitalpolicingplatform.adapters.pocketbook.CreateLogGroupsAdapter;
import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.customLibraries.MovableFloatingActionButton;
import hcl.policing.digitalpolicingplatform.listeners.OnItemClickListener;
import hcl.policing.digitalpolicingplatform.models.process.ProcessLogDTO;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.DateUtil;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;
import hcl.policing.digitalpolicingplatform.utils.OnSwipeTouchListner;
import hcl.policing.digitalpolicingplatform.utils.Utilities;

public class CreatePocketbookGroups extends Fragment implements View.OnClickListener {

    private Context mContext;
    private AppSession appSession;
    private RecyclerView rvList;
    private ArrayList<ProcessLogDTO> processDirList;
    private ArrayList<ProcessLogDTO> offlineProcessDirList;
    private ArrayList<ProcessLogDTO> draftFileList;
    private ArrayList<ProcessLogDTO> offlineFileList;
    private ArrayList<ProcessLogDTO> entriesList;
    private CreateLogGroupsAdapter logGroupsAdapter;
    private String processName;
    private String directoryDraft = "";
    private String directoryFileDraft = "";
    private String directoryOffline = "";
    private String directoryFileOffline = "";
    private Animation slideOutRight, slideOutLeft;
    private TextView tvPocketbook, tvAll, tvPocketbookBack, tvAllBack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.pocketbook_single_entries, container, false);

        mContext = getActivity();
        appSession = new AppSession(Objects.requireNonNull(mContext));
        initView(view);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        directoryDraft = GenericConstant.FILE_DRAFT /*+ getResources().getString(R.string.tor_creation) + "/"*/;
        //directoryFileDraft = GenericConstant.FILE_DRAFT + processName + "/";

        directoryOffline = GenericConstant.FILE_OFFLINE;
        //directoryFileOffline = GenericConstant.FILE_OFFLINE + processName + "/";
    }

    /**
     * Initialize the view
     */
    private void initView(View view) {
        slideOutLeft = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_left);
        slideOutRight = AnimationUtils.loadAnimation(mContext, R.anim.slide_out_right);
        rvList = view.findViewById(R.id.rv_list);
        LinearLayoutManager mLayoutManagerTask = new LinearLayoutManager(mContext);
        rvList.setLayoutManager(mLayoutManagerTask);
        rvList.setItemAnimator(new DefaultItemAnimator());

        tvPocketbook = view.findViewById(R.id.tv_pocketbook);
        tvAll = view.findViewById(R.id.tv_all);
        tvPocketbookBack = view.findViewById(R.id.tv_pocketbook_back);
        tvAllBack = view.findViewById(R.id.tv_all_back);

        tvPocketbookBack.setOnClickListener(this);
        tvAllBack.setOnClickListener(this);

        tvPocketbook.setOnTouchListener(new OnSwipeTouchListner(mContext) {
            public void onSwipeRight() {
                onClickAllAnimation();
            }
        });
        tvAll.setOnTouchListener(new OnSwipeTouchListner(mContext) {
            public void onSwipeLeft() {
                onClickPocketbookAnimation();
            }
        });

        MovableFloatingActionButton btnCreate = view.findViewById(R.id.btn_add);
        btnCreate.setImageResource(R.drawable.ic_arrow_right_white);
        btnCreate.setOnClickListener(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            ArrayList<String> draftDirList = new ArrayList<>();
            draftDirList = Utilities.getInstance(mContext).getDirectoryList(directoryDraft);

            if (draftDirList != null && draftDirList.size() > 0) {
                processDirList = new ArrayList<>();
                for (int i = 0; i < draftDirList.size(); i++) {
                    ProcessLogDTO draftDTO = new ProcessLogDTO();
                    draftDTO.setProcessName(draftDirList.get(i));
                    processDirList.add(draftDTO);
                }
            }
            draftFileList = new ArrayList<>();
            ArrayList<String> fileList;
            if(processDirList != null && processDirList.size() > 0) {
                for (int i = 0; i < processDirList.size(); i++) {
                    directoryFileDraft = GenericConstant.FILE_DRAFT + processDirList.get(i).getProcessName() + "/";

                    fileList = new ArrayList<>();
                    fileList = Utilities.getInstance(mContext).getFilesList(directoryFileDraft);

                    if (fileList != null && fileList.size() > 0) {
                        for (int j = 0; j < fileList.size(); j++) {
                            ProcessLogDTO draftDTO = new ProcessLogDTO();
                            draftDTO.setProcessName(processDirList.get(i).getProcessName());
                            draftDTO.setDisplayText("");
                            draftDTO.setFileType(GenericConstant.DRAFT_FILE);
                            draftDTO.setFilePath(directoryFileDraft);
                            draftDTO.setFileName(fileList.get(j));
                            draftDTO.setTime(DateUtil.getDateTime(fileList.get(j).replace(".txt", "")));
                            draftDTO.setDate(DateUtil.getDate(fileList.get(j).replace(".txt", "")));
                            draftDTO.setGroupFlag("0");
                            draftDTO.setPinFlag("0");
                            draftFileList.add(draftDTO);
                        }
                    }
                }
            }
            if(draftFileList != null && draftFileList.size() > 0) {
                for (int i = 0; i < draftFileList.size(); i++) {
                    if (draftFileList.get(i).getProcessName().equalsIgnoreCase(getResources().getString(R.string.pocket_book))) {
                        String fileName = draftFileList.get(i).getFileName().replace(".txt", "");
                        if (fileName != null && !fileName.equalsIgnoreCase("")) {
                            Log.e("File ", "NAME >>>>> " + fileName);
                            String draftJSON = Utilities.getInstance(mContext).readfile(fileName, draftFileList.get(i).getFilePath());
                            if (draftJSON != null && !draftJSON.equalsIgnoreCase("")) {
                                try {
                                    JSONObject mainJSON = new JSONObject(draftJSON);
                                    JSONObject jsonSection = mainJSON.getJSONObject("New_Entry");
                                    String displayName = jsonSection.getString(getResources().getString(R.string.pocket_book));
                                    draftFileList.get(i).setDisplayText(displayName);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }

            Log.e("DRAFT ", " FILE NAME >> " + draftFileList.get(0).getFileName());

            ArrayList<String> offlineDirList = new ArrayList<>();
            offlineDirList = Utilities.getInstance(mContext).getDirectoryList(directoryOffline);

            if (offlineDirList != null && offlineDirList.size() > 0) {
                offlineProcessDirList = new ArrayList<>();
                for (int i = 0; i < offlineDirList.size(); i++) {
                    ProcessLogDTO draftDTO = new ProcessLogDTO();
                    draftDTO.setProcessName(offlineDirList.get(i));
                    offlineProcessDirList.add(draftDTO);
                }
            }

            offlineFileList = new ArrayList<>();
            if(offlineProcessDirList != null && offlineProcessDirList.size() > 0) {
                for (int i = 0; i < offlineProcessDirList.size(); i++) {
                    directoryFileOffline = GenericConstant.FILE_OFFLINE + offlineProcessDirList.get(i).getProcessName() + "/";

                    fileList = new ArrayList<>();
                    fileList = Utilities.getInstance(mContext).getFilesList(directoryFileOffline);

                    if (fileList != null && fileList.size() > 0) {
                        for (int j = 0; j < fileList.size(); j++) {
                            ProcessLogDTO draftDTO = new ProcessLogDTO();
                            draftDTO.setProcessName(offlineProcessDirList.get(i).getProcessName());
                            draftDTO.setFileType(GenericConstant.OFFLINE_FILE);
                            draftDTO.setDisplayText("");
                            draftDTO.setFilePath(directoryFileOffline);
                            draftDTO.setFileName(fileList.get(j));
                            draftDTO.setTime(DateUtil.getDateTime(fileList.get(j).replace(".txt", "")));
                            draftDTO.setDate(DateUtil.getDate(fileList.get(j).replace(".txt", "")));
                            draftDTO.setGroupFlag("0");
                            draftDTO.setPinFlag("0");
                            offlineFileList.add(draftDTO);
                        }
                    }
                }
            }
            if(offlineFileList != null && offlineFileList.size() > 0) {
                for (int i = 0; i < offlineFileList.size(); i++) {
                    if (offlineFileList.get(i).getProcessName().equalsIgnoreCase(getResources().getString(R.string.pocket_book))) {
                        String fileName = offlineFileList.get(i).getFileName().replace(".txt", "");
                        if (fileName != null && !fileName.equalsIgnoreCase("")) {
                            Log.e("File ", "NAME >>>>> " + fileName);
                            String draftJSON = Utilities.getInstance(mContext).readfile(fileName, offlineFileList.get(i).getFilePath());
                            if (draftJSON != null && !draftJSON.equalsIgnoreCase("")) {
                                try {
                                    JSONObject mainJSON = new JSONObject(draftJSON);
                                    JSONObject jsonSection = mainJSON.getJSONObject("New_Entry");
                                    String displayName = jsonSection.getString(getResources().getString(R.string.pocket_book));
                                    offlineFileList.get(i).setDisplayText(displayName);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                }
            }
            pocketbookEntries();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreatePocketbookGroups.class, "onResume");
        }
    }

    private void pocketbookEntries () {
        entriesList = new ArrayList<>();
        if(draftFileList != null && draftFileList.size() > 0) {
            for (int i = 0; i < draftFileList.size(); i++) {
                if(draftFileList.get(i).getProcessName().toLowerCase().equalsIgnoreCase(getResources().getString(R.string.pocket_book).toLowerCase())) {
                    entriesList.add(draftFileList.get(i));
                }
            }
        }
        if(offlineFileList != null && offlineFileList.size() > 0) {
            Log.e("OFFLINE ", " FILE NAME >> " + offlineFileList.get(0).getFileName());
            for (int i = 0; i < offlineFileList.size(); i++) {
                if(offlineFileList.get(i).getProcessName().toLowerCase().equalsIgnoreCase(getResources().getString(R.string.pocket_book).toLowerCase())) {
                    entriesList.add(offlineFileList.get(i));
                }
            }
        }
        Collections.sort(entriesList, new Comparator<ProcessLogDTO>() {
            DateFormat f = new SimpleDateFormat("dd MMM yyyy", Locale.US);
            public int compare(ProcessLogDTO o1, ProcessLogDTO o2) {
                if (o1.getDate() == null || o2.getDate() == null)
                    return 0;
                try {
                    return f.parse(o1.getDate()).compareTo(f.parse(o2.getDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        Collections.reverse(entriesList);
        logGroupsAdapter = new CreateLogGroupsAdapter(mContext, entriesList, onClickItem);
        rvList.setAdapter(logGroupsAdapter);
    }

    private void allEntries () {
        entriesList = new ArrayList<>();
        if(draftFileList != null && draftFileList.size() > 0) {
            for (int i = 0; i < draftFileList.size(); i++) {
                if(!draftFileList.get(i).getProcessName().toLowerCase().equalsIgnoreCase(getResources().getString(R.string.pocket_book).toLowerCase())) {
                    entriesList.add(draftFileList.get(i));
                }
            }
        }
        if(offlineFileList != null && offlineFileList.size() > 0) {
            Log.e("OFFLINE ", " FILE NAME >> " + offlineFileList.get(0).getFileName());
            for (int i = 0; i < offlineFileList.size(); i++) {
                if(!offlineFileList.get(i).getProcessName().toLowerCase().equalsIgnoreCase(getResources().getString(R.string.pocket_book).toLowerCase())) {
                    entriesList.add(offlineFileList.get(i));
                }
            }
        }
        if(appSession.getFDSList() != null && appSession.getFDSList().size() > 0) {
            entriesList.addAll(appSession.getFDSList());
        }
        ArrayList<ProcessLogDTO> fdsList = new ArrayList<>();
        if(appSession.getFDSList() != null && appSession.getFDSList().size() > 0) {
            fdsList.addAll(appSession.getFDSList());
        }
        if(fdsList.size() > 0) {
            for (Iterator<ProcessLogDTO> iterator = fdsList.iterator(); iterator.hasNext();) {
                ProcessLogDTO obj = iterator.next();
                int val = DateUtil.compareDates(obj.getDate(), DateUtil.getBackDate(System.currentTimeMillis()));
                if (val == 1) {
                    // Remove the current element from the iterator and the list.
                    iterator.remove();
                }
            }
        }
        if(fdsList.size() > 0) {
            entriesList.addAll(fdsList);
        }
        Collections.sort(entriesList, new Comparator<ProcessLogDTO>() {
            DateFormat f = new SimpleDateFormat("dd MMM yyyy", Locale.US);
            public int compare(ProcessLogDTO o1, ProcessLogDTO o2) {
                if (o1.getDate() == null || o2.getDate() == null)
                    return 0;
                try {
                    return f.parse(o1.getDate()).compareTo(f.parse(o2.getDate()));
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
        Collections.reverse(entriesList);
        logGroupsAdapter = new CreateLogGroupsAdapter(mContext, entriesList, onClickItem);
        rvList.setAdapter(logGroupsAdapter);
    }

    /**
     * Item Click lisnter
     */
    private OnItemClickListener.OnItemClickCallback onClickItem = new OnItemClickListener.OnItemClickCallback() {
        @Override
        public void onItemClicked(View view, int position) {
            try {
                for (int i = 0; i < entriesList.size(); i++) {
                    if (position == i) {
                        if (entriesList.get(position).getGroupFlag().equalsIgnoreCase("0")) {
                            entriesList.get(position).setGroupFlag("1");
                        } else {
                            entriesList.get(position).setGroupFlag("0");
                        }
                    }
                }
                logGroupsAdapter.notifyDataSetChanged();
            } catch (Exception e) {
                ExceptionLogger.Logger(e.getCause(), e.getMessage(), CreatePocketbookGroups.class, "onClickItem");
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_pocketbook_back:
                onClickPocketbookAnimation();
                break;

            case R.id.tv_all_back:
                onClickAllAnimation();
                break;

            case R.id.btn_add:
                ArrayList<ProcessLogDTO> finalList = new ArrayList<>();
                for (int i = 0; i < entriesList.size(); i++) {
                    if(entriesList.get(i).getGroupFlag().equalsIgnoreCase("1")) {
                        finalList.add(entriesList.get(i));
                    }
                }

                if(finalList != null && finalList.size() > 0) {
                    Fragment fragment = new PocketbookSaveGroup();
                    Bundle bun = new Bundle();
                    bun.putSerializable(GenericConstant.ANSWER_LIST, finalList);
                    fragment.setArguments(bun);
                    FragmentManager fm = getActivity().getSupportFragmentManager();
                    //fm.popBackStack("DashBoard", FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    FragmentTransaction ft = fm.beginTransaction();
                    ft.setCustomAnimations(R.anim.slide_in_left, R.anim.slide_out_left, R.anim.slide_in_left, R.anim.slide_out_left);
                    ft.replace(R.id.frame_layout, fragment);
                    ft.addToBackStack(null);
                    ft.commit();
                }
                break;
        }
    }

    /**
     * LoginApi click animation
     */
    public void onClickPocketbookAnimation() {
        tvPocketbookBack.setClickable(false);
        tvAllBack.setClickable(true);
        tvAll.setVisibility(View.VISIBLE);
        tvAll.startAnimation(slideOutLeft);

        slideOutLeft.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                pocketbookEntries();
                tvAll.setVisibility(View.INVISIBLE);
                tvPocketbook.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    /**
     * On click pin animation method
     */
    public void onClickAllAnimation() {
        tvPocketbookBack.setClickable(true);
        tvAllBack.setClickable(false);

        tvPocketbook.setVisibility(View.VISIBLE);
        tvPocketbook.startAnimation(slideOutRight);

        slideOutRight.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                allEntries();
                tvAll.setVisibility(View.VISIBLE);
                tvPocketbook.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }
}
