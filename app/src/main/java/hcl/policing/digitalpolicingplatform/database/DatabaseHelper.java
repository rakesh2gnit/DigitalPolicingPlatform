package hcl.policing.digitalpolicingplatform.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.text.TextUtils;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import hcl.policing.digitalpolicingplatform.constants.common.GenericConstant;
import hcl.policing.digitalpolicingplatform.models.login.LoginResponseDTO;
import hcl.policing.digitalpolicingplatform.models.login.Right;
import hcl.policing.digitalpolicingplatform.models.masterdata.Item;
import hcl.policing.digitalpolicingplatform.models.masterdata.MasterDataGroup;
import hcl.policing.digitalpolicingplatform.models.masterdata.MasterDataProcess;
import hcl.policing.digitalpolicingplatform.models.process.ProcessFlowModel;
import hcl.policing.digitalpolicingplatform.models.search.DropdownListResponse;
import hcl.policing.digitalpolicingplatform.models.search.TriggerResponse;
import hcl.policing.digitalpolicingplatform.offline.BackupDataUtils;
import hcl.policing.digitalpolicingplatform.prefs.AppSession;
import hcl.policing.digitalpolicingplatform.utils.ExceptionLogger;

public class DatabaseHelper extends SQLiteOpenHelper implements Serializable {

    private AppSession appSession;
    // Database Version
    private transient static final int DATABASE_VERSION = 1;

    // Database Name
    private transient static final String DATABASE_NAME = "dpp_db";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        appSession = new AppSession(context);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DropDown2.CREATE_TABLE);
        db.execSQL(RightsMapper.CREATE_TABLE);
        db.execSQL(VersionMapper.CREATE_TABLE);
        db.execSQL(ProcessSubProcessMapper.CREATE_TABLE);
        db.execSQL(SectionMapper.CREATE_TABLE);
        db.execSQL(ProcessFlowMapper.CREATE_TABLE);
        db.execSQL(TriggerMapper.CREATE_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + DropDown2.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + RightsMapper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + VersionMapper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ProcessSubProcessMapper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + SectionMapper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + ProcessFlowMapper.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + TriggerMapper.TABLE_NAME);
        // Create tables again
        onCreate(db);
    }

    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }


    /**
     * Add version Number
     *
     * @param processname
     * @param versionNo
     */
    public void addVersion(String processname, int versionNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(VersionMapper.COLUMN_VERSION_NAME, processname);
            values.put(VersionMapper.COLUMN_VERSION_NO, versionNo);
            db.insert(VersionMapper.TABLE_NAME, null, values);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "addVersion");
        } finally {
            closeDB();
        }
    }


    //Inserting DropDown Values
    public void addDropDownList(List<DropdownListResponse> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (DropdownListResponse dropdown : list) {
                values.put(DropDown.COLUMN_PROCESSNAME, dropdown.getProcessname());
                values.put(DropDown.COLUMN_GROUPCODE, dropdown.getLookupname());
                values.put(DropDown.COLUMN_SNO, dropdown.getLookupid());
                values.put(DropDown.COLUMN_PARENTID, dropdown.getParentid());
                values.put(DropDown.COLUMN_CODE, dropdown.getCode());
                values.put(DropDown.COLUMN_DESCRIPTION, dropdown.getDescription());
                db.insert(DropDown.TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "add_dropDownList");
        } finally {
            db.endTransaction();
            closeDB();
        }
    }


    //Inserting DropDown Values
    public void addDropDownList2(List<MasterDataProcess> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (MasterDataProcess masterDataProcess : list) {
                for (MasterDataGroup masterDataGroup : masterDataProcess.getMasterDataGroups()) {
                    for (Item item : masterDataGroup.getItems()) {
                        values.put(DropDown2.COLUMN_PROCESSNAME, masterDataProcess.getCode());
                        values.put(DropDown2.COLUMN_GROUPCODE, masterDataGroup.getCode());
                        values.put(DropDown2.COLUMN_SNO, item.getSNo());
                        values.put(DropDown2.COLUMN_ORDERRANK, item.getOrderRank());
                        values.put(DropDown2.COLUMN_PARENTID, item.getParentKey());
                        values.put(DropDown2.COLUMN_CODE, item.getKey());
                        values.put(DropDown2.COLUMN_DESCRIPTION, item.getValue());
                        db.insert(DropDown2.TABLE_NAME, null, values);
                    }
                }
            }
            db.setTransactionSuccessful();
            Log.d("Dropdown","Record Inserted");
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "add_dropDownList");
        } finally {
            db.endTransaction();
            closeDB();
        }
    }

    /**
     * Inserting Process Sub Process Values
     *
     * @param list
     */
    public void addProcessSubProcessList(List<LoginResponseDTO.ProcessesBean> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (LoginResponseDTO.ProcessesBean processesBean : list) {
                if (!TextUtils.isEmpty(processesBean.getProcesslogourl())) {
                    for (LoginResponseDTO.SubProcessesBean subProcess : processesBean.getSubprocesses()) {
                        if (!TextUtils.isEmpty(subProcess.getSubprocesslogourl())) {
                            values.put(ProcessSubProcessMapper.COLUMN_PROCESS_ID, processesBean.getProcessid());
                            values.put(ProcessSubProcessMapper.COLUMN_PROCESS_NAME, processesBean.getProcessname());
                            values.put(ProcessSubProcessMapper.COLUMN_PROCESS_ICON, processesBean.getProcesslogourl());
                            values.put(ProcessSubProcessMapper.COLUMN_SUB_PROCESS_ID, subProcess.getSubprocessid());
                            values.put(ProcessSubProcessMapper.COLUMN_SUB_PROCESS_NAME, subProcess.getSubprocessname());
                            values.put(ProcessSubProcessMapper.COLUMN_SUB_PROCESS_ICON, subProcess.getSubprocesslogourl());

                            db.insert(ProcessSubProcessMapper.TABLE_NAME, null, values);
                            /*Calling the section inserting methode*/
                            addSectionList(subProcess, db, processesBean.getProcessid());
                        }
                    }
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "addProcessSubProcessList");
        } finally {
            db.endTransaction();
            closeDB();
        }
    }

    /**
     * Inserting Process Flow Values
     *
     * @param processFlowVersionsBean
     */
    public void addProcessFlowVersion(LoginResponseDTO.ProcessFlowVersionsBean processFlowVersionsBean) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(ProcessFlowMapper.COLUMN_CUSTOMER_ID, processFlowVersionsBean.getCustomerid());
            values.put(ProcessFlowMapper.COLUMN_PROCESS_ID, processFlowVersionsBean.getProcessid());
            values.put(ProcessFlowMapper.COLUMN_SUB_PROCESS_ID, processFlowVersionsBean.getSubprocessid());
            values.put(ProcessFlowMapper.COLUMN_PROCESS_NAME, processFlowVersionsBean.getProcessname());
            values.put(ProcessFlowMapper.COLUMN_SUB_PROCESS_NAME, processFlowVersionsBean.getSubprocessname());
            values.put(ProcessFlowMapper.COLUMN_FLOW_VERSION, processFlowVersionsBean.getVersionnumber());
            db.insert(ProcessFlowMapper.TABLE_NAME, null, values);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "addProcessFlowList");
        } finally {
            closeDB();
        }
    }

    /**
     * Inserting Process Flow Values
     *
     * @param processFlowModel
     */
    public void addProcessFlow(ProcessFlowModel processFlowModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(ProcessFlowMapper.COLUMN_CUSTOMER_ID, processFlowModel.getCustomerid());
            values.put(ProcessFlowMapper.COLUMN_PROCESS_ID, processFlowModel.getProcessid());
            values.put(ProcessFlowMapper.COLUMN_SUB_PROCESS_ID, processFlowModel.getSubprocessid());
            values.put(ProcessFlowMapper.COLUMN_PROCESS_NAME, processFlowModel.getProcessname());
            values.put(ProcessFlowMapper.COLUMN_SUB_PROCESS_NAME, processFlowModel.getSubprocessname());
            values.put(ProcessFlowMapper.COLUMN_FLOW_ID, processFlowModel.getProcessflowid());
            values.put(ProcessFlowMapper.COLUMN_PROCESS_FLOW, processFlowModel.getProcessflowstring());
            values.put(ProcessFlowMapper.COLUMN_FLOW_VERSION, processFlowModel.getVersion());
            db.insert(ProcessFlowMapper.TABLE_NAME, null, values);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "addProcessFlowList");
        } finally {
            closeDB();
        }
    }

    /**
     * Inserting Section Values
     *
     * @param subProcess
     * @param db
     */
    public void addSectionList(LoginResponseDTO.SubProcessesBean subProcess, SQLiteDatabase db, String processId) {
        try {
            ContentValues values = new ContentValues();

            if (subProcess != null) {
                for (LoginResponseDTO.SectionsBean sectionsBean : subProcess.getSections()) {
                    values.put(SectionMapper.COLUMN_PROCESS_ID, processId);
                    values.put(SectionMapper.COLUMN_SUB_PROCESS_ID, subProcess.getSubprocessid());
                    values.put(SectionMapper.COLUMN_SECTION_ID, sectionsBean.getSectionid());
                    values.put(SectionMapper.COLUMN_SECTION_NAME, sectionsBean.getSectionname());
                    db.insert(SectionMapper.TABLE_NAME, null, values);
                }
            }

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "addSectionList");
        }
    }

    /**
     * inserting Trigger Values
     *
     * @param triggersBeans
     */
    public void addTriggerList(List<TriggerResponse.TriggersBean> triggersBeans) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (TriggerResponse.TriggersBean model : triggersBeans) {

                values.put(TriggerMapper.COLUMN_CUSTOMER_ID, model.getCustomerid());
                values.put(TriggerMapper.COLUMN_PROCESS_ID, model.getProcessid());
                values.put(TriggerMapper.COLUMN_SUB_PROCESS_ID, model.getSubprocessid());
                values.put(TriggerMapper.COLUMN_TRIGGER_ID, model.getTriggerid());
                values.put(TriggerMapper.COLUMN_PROCESS_NAME, model.getProcessname());
                values.put(TriggerMapper.COLUMN_SUB_PROCESS_NAME, model.getSubprocessname());
                values.put(TriggerMapper.COLUMN_TRIGGER_NAME, model.getTriggername());
                values.put(TriggerMapper.COLUMN_CREATED_DATE, model.getCreateddate());
                values.put(TriggerMapper.COLUMN_MODIFIED_DATE, model.getModifieddate());
                db.insert(TriggerMapper.TABLE_NAME, null, values);

            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "addTriggerList");
        } finally {
            db.endTransaction();
            closeDB();
        }
    }


    //Getting DropDown Values
    public ArrayList<DropDown> getDropdown(String processName, String groupCode) {
        // get readable database as we are not inserting anything
        ArrayList<DropDown> aDropDown = new ArrayList<>();
        aDropDown.clear();
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.query(DropDown.TABLE_NAME, null,
                    DropDown.COLUMN_GROUPCODE + "=?"/* AND " + DropDown.COLUMN_PROCESSNAME + "=?"*/,
                    new String[]{groupCode/*, processName*/}, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    // prepare note object

                    DropDown mDropDown = new DropDown();
                    mDropDown.setId(cursor.getInt(cursor.getColumnIndex(DropDown.COLUMN_ID)));
                    mDropDown.setSno(cursor.getString(cursor.getColumnIndex(DropDown.COLUMN_SNO)));
                    mDropDown.setCode(cursor.getString(cursor.getColumnIndex(DropDown.COLUMN_CODE)));
                    mDropDown.setDescription(cursor.getString(cursor.getColumnIndex(DropDown.COLUMN_DESCRIPTION)));
                    mDropDown.setGroupcode(cursor.getString(cursor.getColumnIndex(DropDown.COLUMN_GROUPCODE)));
                    mDropDown.setProcessname(cursor.getString(cursor.getColumnIndex(DropDown.COLUMN_PROCESSNAME)));
                    mDropDown.setParentid(cursor.getString(cursor.getColumnIndex(DropDown.COLUMN_PARENTID)));
                    mDropDown.setOrderby(cursor.getString(cursor.getColumnIndex(DropDown.COLUMN_ORDERBY)));
                    aDropDown.add(mDropDown);
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getDropdown");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return aDropDown;
    }

    //Getting Dependant DropDown Values
    public ArrayList<DropDown> dependantDropdown(String processName, String groupCode, String parentId) {
        // get readable database as we are not inserting anything
        ArrayList<DropDown> aDropDown = new ArrayList<>();
        aDropDown.clear();
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            if (parentId != null && !parentId.equalsIgnoreCase("")) {
                cursor = db.query(DropDown.TABLE_NAME, null,
                        DropDown.COLUMN_PARENTID + "=? AND " + DropDown.COLUMN_GROUPCODE + "=? "/*AND " + DropDown.COLUMN_PROCESSNAME + "=?"*/,
                        new String[]{parentId, groupCode/*, processName*/}, null, null, null, null);
            } else {
                cursor = db.query(DropDown.TABLE_NAME, null,
                        DropDown.COLUMN_GROUPCODE + "=? "/*AND " + DropDown.COLUMN_PROCESSNAME + "=?"*/,
                        new String[]{groupCode/*, processName*/}, null, null, null, null);
            }

            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    // prepare note object
                    DropDown mDropDown = new DropDown();
                    mDropDown.setId(cursor.getInt(cursor.getColumnIndex(DropDown.COLUMN_ID)));
                    mDropDown.setSno(cursor.getString(cursor.getColumnIndex(DropDown.COLUMN_SNO)));
                    mDropDown.setCode(cursor.getString(cursor.getColumnIndex(DropDown.COLUMN_CODE)));
                    mDropDown.setDescription(cursor.getString(cursor.getColumnIndex(DropDown.COLUMN_DESCRIPTION)));
                    mDropDown.setGroupcode(cursor.getString(cursor.getColumnIndex(DropDown.COLUMN_GROUPCODE)));
                    mDropDown.setProcessname(cursor.getString(cursor.getColumnIndex(DropDown.COLUMN_PROCESSNAME)));
                    mDropDown.setParentid(cursor.getString(cursor.getColumnIndex(DropDown.COLUMN_PARENTID)));
                    mDropDown.setOrderby(cursor.getString(cursor.getColumnIndex(DropDown.COLUMN_ORDERBY)));
                    aDropDown.add(mDropDown);

                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getDropdown");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return aDropDown;
    }


    /**
     * Updated Version Number
     *
     * @param verson
     * @param versionName
     */
    public void updateVersion(int verson, String versionName) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(VersionMapper.COLUMN_VERSION_NO, verson);
            db.update(VersionMapper.TABLE_NAME, values, VersionMapper.COLUMN_VERSION_NAME + "=?", new String[]{versionName});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "updateVersion");
        } finally {
            db.endTransaction();
            closeDB();
        }

    }


    //Deleting All Values From DropDown Table
    public void deleteAllDropdownValues() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(DropDown.TABLE_NAME, null, null);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "deleteAllDropdownValues");
        } finally {
            closeDB();
        }
    }

    //Deleting All Values From DropDown Table
    public void deleteTriggerValues() {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TriggerMapper.TABLE_NAME, null, null);
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "deleteTriggerValues");
        } finally {
            closeDB();
        }
    }

    //Inserting Rights Values
    public void add_rightsList(List<Right> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (Right right : list) {
                values.put(RightsMapper.COLUMN_NAME, right.getName());
                values.put(RightsMapper.COLUMN_CODE, right.getObjectId());
                values.put(RightsMapper.COLUMN_TYPE, right.getObjectType());
                values.put(RightsMapper.COLUMN_PARENTID, right.getParentId());
                values.put(RightsMapper.COLUMN_LEVEL, 1);
                values.put(RightsMapper.COLUMN_ACTIVE, 1);
                db.insert(RightsMapper.TABLE_NAME, null, values);
                ContentValues contentValues = new ContentValues();
                for (Right.SubProcess subProcess : right.getSubProcesses()) {
                    contentValues.put(RightsMapper.COLUMN_NAME, subProcess.getName());
                    contentValues.put(RightsMapper.COLUMN_CODE, subProcess.getObjectId());
                    contentValues.put(RightsMapper.COLUMN_TYPE, subProcess.getObjectType());
                    contentValues.put(RightsMapper.COLUMN_PARENTID, subProcess.getParentId());
                    contentValues.put(RightsMapper.COLUMN_LEVEL, 2);
                    contentValues.put(RightsMapper.COLUMN_ICON, subProcess.getUrl());
                    contentValues.put(RightsMapper.COLUMN_ACTIVE, 1);
                    db.insert(RightsMapper.TABLE_NAME, null, contentValues);
                }
            }
            db.setTransactionSuccessful();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "deleteAllDropdownValues");
        } finally {
            db.endTransaction();
            closeDB();
        }
    }

    //Getting Rights Values
    public RightsMapper getRights(int level, int active) {
        // get readable database as we are not inserting anything
        Cursor cursor = null;
        RightsMapper mRightsMapper = null;
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.query(RightsMapper.TABLE_NAME,
                    new String[]{RightsMapper.COLUMN_ID, RightsMapper.COLUMN_NAME, RightsMapper.COLUMN_CODE, RightsMapper.COLUMN_TYPE,
                            RightsMapper.COLUMN_LEVEL, RightsMapper.COLUMN_PARENTID, RightsMapper.COLUMN_ICON,
                            RightsMapper.COLUMN_ACTIVE},
                    RightsMapper.COLUMN_LEVEL + "=? AND " + RightsMapper.COLUMN_ACTIVE + "=?",
                    new String[]{String.valueOf(level), String.valueOf(active)}, null, null, null, null);

            if (cursor != null)
                cursor.moveToFirst();

            // prepare note object

            mRightsMapper = new RightsMapper();
            mRightsMapper.setId(cursor.getInt(cursor.getColumnIndex(RightsMapper.COLUMN_ID)));
            mRightsMapper.setName(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_NAME)));
            mRightsMapper.setCode(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_CODE)));
            mRightsMapper.setType(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_TYPE)));
            mRightsMapper.setLevel(cursor.getInt(cursor.getColumnIndex(RightsMapper.COLUMN_LEVEL)));
            mRightsMapper.setParentid(cursor.getInt(cursor.getColumnIndex(RightsMapper.COLUMN_PARENTID)));
            mRightsMapper.setIcon(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_ICON)));
            mRightsMapper.setIconname(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_ICON_NAME)));
            mRightsMapper.setIconaddress(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_ICON_ADDRESS)));
            mRightsMapper.setActive(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_ACTIVE)));

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getRights");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return mRightsMapper;
    }

    /**
     * Getting Sub Rights Values
     *
     * @param level
     * @param active
     * @param parentId
     * @return
     */
    public RightsMapper getSubRights(int level, int active, int parentId) {

        Cursor cursor = null;
        RightsMapper mRightsMapper = null;
        // get readable database as we are not inserting anything
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.query(RightsMapper.TABLE_NAME,
                    new String[]{RightsMapper.COLUMN_ID, RightsMapper.COLUMN_NAME, RightsMapper.COLUMN_CODE, RightsMapper.COLUMN_TYPE,
                            RightsMapper.COLUMN_LEVEL, RightsMapper.COLUMN_PARENTID, RightsMapper.COLUMN_ICON,
                            RightsMapper.COLUMN_ACTIVE},
                    RightsMapper.COLUMN_LEVEL + "=? AND " + RightsMapper.COLUMN_ACTIVE + "=? AND " + RightsMapper.COLUMN_PARENTID + "=?",
                    new String[]{String.valueOf(level), String.valueOf(active), String.valueOf(parentId)}, null, null, null, null);

            if (cursor != null)
                cursor.moveToFirst();
            // prepare note object

            mRightsMapper = new RightsMapper();
            mRightsMapper.setId(cursor.getInt(cursor.getColumnIndex(RightsMapper.COLUMN_ID)));
            mRightsMapper.setName(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_NAME)));
            mRightsMapper.setCode(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_CODE)));
            mRightsMapper.setType(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_TYPE)));
            mRightsMapper.setLevel(cursor.getInt(cursor.getColumnIndex(RightsMapper.COLUMN_LEVEL)));
            mRightsMapper.setParentid(cursor.getInt(cursor.getColumnIndex(RightsMapper.COLUMN_PARENTID)));
            mRightsMapper.setIcon(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_ICON)));
            mRightsMapper.setIconname(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_ICON_NAME)));
            mRightsMapper.setIconaddress(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_ICON_ADDRESS)));
            mRightsMapper.setActive(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_ACTIVE)));

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getRights");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return mRightsMapper;
    }


    /**
     * Get the Distinct Process Name
     *
     * @return
     */
    public ArrayList<ProcessSubProcessMapper> getProcessList() {

        Cursor cursor = null;
        ArrayList<ProcessSubProcessMapper> aProcessSubProcessMapper = new ArrayList<>();
        aProcessSubProcessMapper.clear();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery(ProcessSubProcessMapper.QUERY_DISTINCT, null);
            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    ProcessSubProcessMapper mProcessSubProcessMapper = new ProcessSubProcessMapper();
                    mProcessSubProcessMapper.setProcessId(cursor.getInt(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_PROCESS_ID)));
                    mProcessSubProcessMapper.setProcessName(cursor.getString(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_PROCESS_NAME)));
                    mProcessSubProcessMapper.setProcessIcon(cursor.getString(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_PROCESS_ICON)));
                    mProcessSubProcessMapper.setShortcutStatus(cursor.getInt(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_SHORTCUT_STATUS)));
                    aProcessSubProcessMapper.add(mProcessSubProcessMapper);

                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getProcessList");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return aProcessSubProcessMapper;
    }


    /**
     * Get the Distinct Process Name with shortcut status
     *
     * @param status
     * @return
     */
    public ArrayList<ProcessSubProcessMapper> getShortcutProcessList(int status) {

        Cursor cursor = null;
        ArrayList<ProcessSubProcessMapper> aProcessSubProcessMapper = new ArrayList<>();
        aProcessSubProcessMapper.clear();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery(ProcessSubProcessMapper.QUERY_DISTINCT_PROCESS, new String[]{String.valueOf(status)});
            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    ProcessSubProcessMapper mProcessSubProcessMapper = new ProcessSubProcessMapper();
                    mProcessSubProcessMapper.setProcessId(cursor.getInt(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_PROCESS_ID)));
                    mProcessSubProcessMapper.setProcessName(cursor.getString(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_PROCESS_NAME)));
                    mProcessSubProcessMapper.setProcessIcon(cursor.getString(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_PROCESS_ICON)));
                    mProcessSubProcessMapper.setShortcutStatus(cursor.getInt(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_SHORTCUT_STATUS)));
                    aProcessSubProcessMapper.add(mProcessSubProcessMapper);

                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getProcessList");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return aProcessSubProcessMapper;
    }

    /**
     * Get the Sub Process Name List
     *
     * @param processId
     * @return
     */
    public ArrayList<ProcessSubProcessMapper> getSubProcessList(String processName, int processId) {

        Cursor cursor = null;
        ArrayList<ProcessSubProcessMapper> aProcessSubProcessMapper = new ArrayList<>();
        aProcessSubProcessMapper.clear();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.query(ProcessSubProcessMapper.TABLE_NAME, null, ProcessSubProcessMapper.COLUMN_PROCESS_ID + "=?",
                    new String[]{String.valueOf(processId)}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    ProcessSubProcessMapper mProcessSubProcessMapper = new ProcessSubProcessMapper();
                    mProcessSubProcessMapper.setProcessId(processId);
                    mProcessSubProcessMapper.setProcessName(processName);
                    mProcessSubProcessMapper.setSubProcessId(cursor.getInt(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_SUB_PROCESS_ID)));
                    mProcessSubProcessMapper.setSubProcessName(cursor.getString(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_SUB_PROCESS_NAME)));
                    mProcessSubProcessMapper.setSubProcessIcon(cursor.getString(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_SUB_PROCESS_ICON)));
                    mProcessSubProcessMapper.setSubProcessIconVersion(cursor.getInt(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_SUB_PROCESS_ICON_VERSION)));
                    aProcessSubProcessMapper.add(mProcessSubProcessMapper);
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getSubProcessList");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return aProcessSubProcessMapper;
    }


    /**
     * Get the Sub Process Name List
     *
     * @param processName
     * @return
     */
    public int getProcessId(String processName) {

        int processId = 0;
        Cursor cursor = null;
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            cursor = db.query(ProcessSubProcessMapper.TABLE_NAME, null, ProcessSubProcessMapper.COLUMN_PROCESS_NAME + "=?",
                    new String[]{processName}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    processId = cursor.getInt(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_PROCESS_ID));
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getProcessId");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return processId;
    }


    /**
     * Get the Sub Process Name List
     *
     * @param processName
     * @return
     */
    public ArrayList<ProcessSubProcessMapper> getSubProcessList(String processName) {

        Cursor cursor = null;
        ArrayList<ProcessSubProcessMapper> aProcessSubProcessMapper = new ArrayList<>();
        aProcessSubProcessMapper.clear();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.query(ProcessSubProcessMapper.TABLE_NAME, null, ProcessSubProcessMapper.COLUMN_PROCESS_NAME + "=?",
                    new String[]{processName}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    ProcessSubProcessMapper mProcessSubProcessMapper = new ProcessSubProcessMapper();
                    mProcessSubProcessMapper.setSubProcessId(cursor.getInt(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_SUB_PROCESS_ID)));
                    mProcessSubProcessMapper.setSubProcessName(cursor.getString(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_SUB_PROCESS_NAME)));
                    mProcessSubProcessMapper.setSubProcessIcon(cursor.getString(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_SUB_PROCESS_ICON)));
                    mProcessSubProcessMapper.setSubProcessIconVersion(cursor.getInt(cursor.getColumnIndexOrThrow(ProcessSubProcessMapper.COLUMN_SUB_PROCESS_ICON_VERSION)));
                    aProcessSubProcessMapper.add(mProcessSubProcessMapper);
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getSubProcessList");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return aProcessSubProcessMapper;
    }


    /**
     * Get the list of Section
     *
     * @return
     */
    public ArrayList<SectionMapper> getSectionList() {

        Cursor cursor = null;
        ArrayList<SectionMapper> aSectionMapper = new ArrayList<>();
        aSectionMapper.clear();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.query(SectionMapper.TABLE_NAME, null, null,
                    null, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    SectionMapper mSectionMapper = new SectionMapper();
                    mSectionMapper.setSectionId(cursor.getInt(cursor.getColumnIndexOrThrow(SectionMapper.COLUMN_SECTION_ID)));
                    mSectionMapper.setSectionName(cursor.getString(cursor.getColumnIndexOrThrow(SectionMapper.COLUMN_SECTION_NAME)));
                    aSectionMapper.add(mSectionMapper);
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getSectionList");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return aSectionMapper;
    }


    /**
     * Get the Process Flow JSON List
     *
     * @param subProcessId
     * @return
     */
    public ProcessFlowMapper getProcessflowJSON(int subProcessId) {

        Cursor cursor = null;
        ProcessFlowMapper mProcessFlowMapper = null;
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.query(ProcessFlowMapper.TABLE_NAME, null, ProcessFlowMapper.COLUMN_SUB_PROCESS_ID + "=?",
                    new String[]{String.valueOf(subProcessId)}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    mProcessFlowMapper = new ProcessFlowMapper();
                    mProcessFlowMapper.setProcessFlow(cursor.getString(cursor.getColumnIndexOrThrow(ProcessFlowMapper.COLUMN_PROCESS_FLOW)));
                    mProcessFlowMapper.setFlowVersion(cursor.getInt(cursor.getColumnIndexOrThrow(ProcessFlowMapper.COLUMN_FLOW_VERSION)));
                    cursor.moveToNext();
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getProcessflowJSON");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return mProcessFlowMapper;
    }

    /**
     * Get the Trigger List
     *
     * @param processId
     * @param subProcessId
     * @return
     */
    private ArrayList<TriggerMapper> getTriggerList(String processId, String subProcessId) {

        Cursor cursor = null;
        ArrayList<TriggerMapper> aTriggerMapper = new ArrayList<>();
        aTriggerMapper.clear();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.query(TriggerMapper.TABLE_NAME, null, TriggerMapper.COLUMN_PROCESS_ID + "=? AND " + TriggerMapper.COLUMN_SUB_PROCESS_ID +
                    "=? ", new String[]{processId, subProcessId}, null, null, null);
            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    TriggerMapper mTriggerMapper = new TriggerMapper();
                    mTriggerMapper.setProcessId(cursor.getInt(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_PROCESS_ID)));
                    mTriggerMapper.setSubProcessId(cursor.getInt(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_SUB_PROCESS_ID)));
                    mTriggerMapper.setTriggerId(cursor.getInt(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_TRIGGER_ID)));
                    mTriggerMapper.setProcessName(cursor.getString(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_PROCESS_NAME)));
                    mTriggerMapper.setSubProcessName(cursor.getString(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_SUB_PROCESS_NAME)));
                    mTriggerMapper.setTriggerName(cursor.getString(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_TRIGGER_NAME)));
                    mTriggerMapper.setCreatedDate(cursor.getString(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_CREATED_DATE)));
                    mTriggerMapper.setModifiedDate(cursor.getString(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_MODIFIED_DATE)));
                    aTriggerMapper.add(mTriggerMapper);

                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getTriggerList");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return aTriggerMapper;
    }

    /**
     * Get the all Trigger list
     *
     * @return
     */
    public ArrayList<TriggerMapper> getAllTriggerList() {

        Cursor cursor = null;
        ArrayList<TriggerMapper> aTriggerMapper = new ArrayList<>();
        aTriggerMapper.clear();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.rawQuery("SELECT * FROM " + TriggerMapper.TABLE_NAME, null);
            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    TriggerMapper mTriggerMapper = new TriggerMapper();
                    mTriggerMapper.setProcessId(cursor.getInt(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_PROCESS_ID)));
                    mTriggerMapper.setSubProcessId(cursor.getInt(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_SUB_PROCESS_ID)));
                    mTriggerMapper.setTriggerId(cursor.getInt(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_TRIGGER_ID)));
                    mTriggerMapper.setProcessName(cursor.getString(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_PROCESS_NAME)));
                    mTriggerMapper.setSubProcessName(cursor.getString(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_SUB_PROCESS_NAME)));
                    mTriggerMapper.setTriggerName(cursor.getString(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_TRIGGER_NAME)));
                    mTriggerMapper.setCreatedDate(cursor.getString(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_CREATED_DATE)));
                    mTriggerMapper.setModifiedDate(cursor.getString(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_MODIFIED_DATE)));
                    aTriggerMapper.add(mTriggerMapper);

                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getTriggerList");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return aTriggerMapper;
    }

    /**
     * Get the trigger details
     *
     * @param triggerName
     * @return
     */
    public TriggerMapper getTriggerDetails(String triggerName) {

        Cursor cursor = null;
        TriggerMapper mTriggerMapper = null;
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            cursor = db.query(TriggerMapper.TABLE_NAME, null, TriggerMapper.COLUMN_TRIGGER_NAME + "=? ", new String[]{triggerName},
                    null, null, null);
            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {

                    mTriggerMapper = new TriggerMapper();
                    mTriggerMapper.setProcessId(cursor.getInt(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_PROCESS_ID)));
                    mTriggerMapper.setSubProcessId(cursor.getInt(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_SUB_PROCESS_ID)));
                    mTriggerMapper.setTriggerId(cursor.getInt(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_TRIGGER_ID)));
                    mTriggerMapper.setProcessName(cursor.getString(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_PROCESS_NAME)));
                    mTriggerMapper.setSubProcessName(cursor.getString(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_SUB_PROCESS_NAME)));
                    mTriggerMapper.setTriggerName(cursor.getString(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_TRIGGER_NAME)));
                    mTriggerMapper.setCreatedDate(cursor.getString(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_CREATED_DATE)));
                    mTriggerMapper.setModifiedDate(cursor.getString(cursor.getColumnIndexOrThrow(TriggerMapper.COLUMN_MODIFIED_DATE)));

                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getTriggerList");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return mTriggerMapper;
    }

    /**
     * Delete Sub Process Entry
     *
     * @param subProcessId
     */
    public void deleteSubProcessEntry(int subProcessId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(ProcessSubProcessMapper.TABLE_NAME, ProcessSubProcessMapper.COLUMN_SUB_PROCESS_ID + "=?",
                    new String[]{String.valueOf(subProcessId)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
    }

    /**
     * Delete Process Entry
     *
     * @param processId
     */
    public void deleteProcessEntry(int processId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(ProcessSubProcessMapper.TABLE_NAME, ProcessSubProcessMapper.COLUMN_PROCESS_ID + "=?",
                    new String[]{String.valueOf(processId)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
    }

    /**
     * Delete Process Flow Entry
     *
     * @param subProcessId
     */
    public void deleteProcessFlowEntry(int subProcessId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(ProcessFlowMapper.TABLE_NAME, ProcessFlowMapper.COLUMN_SUB_PROCESS_ID + "=?",
                    new String[]{String.valueOf(subProcessId)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
    }

    /**
     * Delete Trigger Entry
     *
     * @param triggerId
     */
    public void deleteTriggerEntry(int triggerId) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(TriggerMapper.TABLE_NAME, TriggerMapper.COLUMN_TRIGGER_ID + "=? ",
                    new String[]{String.valueOf(triggerId)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeDB();
        }
    }

    //Update with 0 Active Rights Values
    public void update_rightsActiveList() {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(RightsMapper.COLUMN_ACTIVE, 0);
            db.update(RightsMapper.TABLE_NAME, values, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "update_rightsActiveList");
        } finally {
            db.endTransaction();
        }
    }

    //Updating & Inserting Rights Values
    public void update_rightsList(List<Right> list) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (Right right : list) {
                values.put(RightsMapper.COLUMN_NAME, right.getName());
                values.put(RightsMapper.COLUMN_CODE, right.getObjectId());
                values.put(RightsMapper.COLUMN_TYPE, right.getObjectType());
                values.put(RightsMapper.COLUMN_PARENTID, right.getParentId());
                values.put(RightsMapper.COLUMN_ACTIVE, 1);
                int id = getID(right.getObjectId(), right.getName());
                if (id == -1) {
                    db.insert(RightsMapper.TABLE_NAME, null, values);
                } else {
                    db.update(RightsMapper.TABLE_NAME, values, RightsMapper.COLUMN_CODE + "=? AND " + RightsMapper.COLUMN_NAME + "=?", new String[]{right.getObjectId(), right.getName()});
                }
                ContentValues contentValues = new ContentValues();
                for (Right.SubProcess subProcess : right.getSubProcesses()) {
                    contentValues.put(RightsMapper.COLUMN_NAME, subProcess.getName());
                    contentValues.put(RightsMapper.COLUMN_CODE, subProcess.getObjectId());
                    contentValues.put(RightsMapper.COLUMN_TYPE, subProcess.getObjectType());
                    contentValues.put(RightsMapper.COLUMN_PARENTID, subProcess.getParentId());
                    contentValues.put(RightsMapper.COLUMN_ICON, subProcess.getUrl());
                    contentValues.put(RightsMapper.COLUMN_ACTIVE, 1);
                    int id1 = getID(subProcess.getObjectId(), subProcess.getName());
                    if (id1 == -1) {
                        db.insert(RightsMapper.TABLE_NAME, null, values);
                    } else {
                        db.update(RightsMapper.TABLE_NAME, values, RightsMapper.COLUMN_CODE + "=? AND " + RightsMapper.COLUMN_NAME + "=?", new String[]{subProcess.getObjectId(), subProcess.getName()});
                    }
                }
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }

    //Check existing values in table
    private int getID(String objectId, String objectName) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(RightsMapper.TABLE_NAME, new String[]{"id"}, RightsMapper.COLUMN_CODE + " =? AND " + RightsMapper.COLUMN_NAME + " =?", new String[]{objectId, objectName}, null, null, null, null);
        if (c.moveToFirst()) //if the row exist then return the id
            return c.getInt(c.getColumnIndex("_id"));
        return -1;
    }

    public int getNullImage() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.query(RightsMapper.TABLE_NAME, new String[]{"id"}, RightsMapper.COLUMN_ICON + " IS NULL OR " + RightsMapper.COLUMN_ICON + " =? ", new String[]{""}, null, null, null, null);
        if (c.moveToFirst()) //if the row exist then return the id
            return c.getInt(c.getColumnIndex("_id"));
        return -1;
    }


    /**
     * Get the dropdown count
     *
     * @return
     */
    public int getDropDownCount() {
        String countQuery = "SELECT  * FROM " + DropDown.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /**
     * get the rights count
     *
     * @return
     */
    public int getRightsCount() {
        String countQuery = "SELECT  * FROM " + RightsMapper.TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }

    /**
     * Dump Offline Records
     *
     * @param backupDataUtils
     */
    public void dumpOfflineRecords(List<BackupDataUtils> backupDataUtils) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (BackupDataUtils model : backupDataUtils) {
                values.put(BackupDataUtils.COLUMN_PROCESS, model.getProcess());
                values.put(BackupDataUtils.COLUMN_FUNCTION, model.getFunction());
                values.put(BackupDataUtils.COLUMN_JSON, model.getJson());
                values.put(BackupDataUtils.COLUMN_URL, model.getUrl());
                db.insert(BackupDataUtils.TABLE_NAME_BACKUP, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }
    }


    /**
     * Check the dropdown is exist or not
     *
     * @return
     */
    public boolean isDropDownExist() {
        boolean isExist = false;
        try {
            int count = getDropDownCount();
            if (count > 0) {
                isExist = true;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "isDropDownExist");
        }

        return isExist;
    }


    /**
     * Check the version is available or not
     *
     * @param versionName
     * @param newVersion
     * @return
     */
    public int isVersionExist(String versionName, int newVersion) {
        int isVersionExist = 0;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int oldVersionNo = 0;

        try {
            cursor = db.query(VersionMapper.TABLE_NAME, null, VersionMapper.COLUMN_VERSION_NAME + " =?",
                    new String[]{versionName}, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    oldVersionNo = cursor.getInt(cursor.getColumnIndex(VersionMapper.COLUMN_VERSION_NO));
                }
            }
            if (oldVersionNo != 0) {
                if (newVersion != oldVersionNo) {
                    isVersionExist = 1;
                } else {
                    isVersionExist = 2;
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "isVersionAvailable");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }
        return isVersionExist;
    }

    /**
     * Check the rights are exist or not
     *
     * @return
     */
    public boolean isRightExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean isExist = false;
        try {
            cursor = db.query(RightsMapper.TABLE_NAME, null, null, null, null,
                    null, null);

            if (cursor != null && cursor.getCount() > 0) {
                isExist = true;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "isRightExist");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return isExist;
    }


    /**
     * Check the process are exist or not
     *
     * @return
     */
    public boolean isProcessExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean isExist = false;
        try {
            cursor = db.query(ProcessSubProcessMapper.TABLE_NAME, null, null, null, null,
                    null, null);

            if (cursor != null && cursor.getCount() > 0) {
                isExist = true;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "isProcessExist");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return isExist;
    }

    /**
     * Check the process flow exist
     *
     * @return
     */
    public boolean isProcessFlowExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean isExist = false;
        try {
            cursor = db.query(ProcessFlowMapper.TABLE_NAME, null, null, null, null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                isExist = true;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "isProcessFlowExist");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return isExist;
    }


    /**
     * Check the process are exist or not
     *
     * @return
     */
    public boolean isTriggerExist() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        boolean isExist = false;
        try {
            cursor = db.query(TriggerMapper.TABLE_NAME, null, null, null, null,
                    null, null);

            if (cursor != null && cursor.getCount() > 0) {
                isExist = true;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "isTriggerExist");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return isExist;
    }

    /**
     * Check the process flow is exist or not
     *
     * @param processId
     * @param subProcessId
     * @param version
     * @return
     */
    public boolean isFlowExist(int processId, int subProcessId, int version) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        int flowVersion = 0;
        boolean isExist = false;
        try {
            cursor = db.query(ProcessFlowMapper.TABLE_NAME, null, ProcessFlowMapper.COLUMN_PROCESS_ID + " =? AND "
                            + ProcessFlowMapper.COLUMN_SUB_PROCESS_ID + " =?", new String[]{String.valueOf(processId), String.valueOf(subProcessId)},
                    null, null, null);

            if (cursor != null && cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    flowVersion = cursor.getInt(cursor.getColumnIndex(ProcessFlowMapper.COLUMN_FLOW_VERSION));
                }
                if (flowVersion < version) {
                    isExist = false;
                } else {
                    isExist = true;
                }
            } else {
                isExist = false;
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "isFlowExist");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }
        return isExist;
    }

    /**
     * get SubProcess Right at Level 2
     *
     * @param parentId
     * @return
     */
    public ArrayList<RightsMapper> getSubProcessRights(int parentId) {
        ArrayList<RightsMapper> aRightsMapper = new ArrayList<>();
        aRightsMapper.clear();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        RightsMapper mRightsMapper = null;

        try {
            cursor = db.query(RightsMapper.TABLE_NAME, null,
                    RightsMapper.COLUMN_PARENTID + "=? AND " + RightsMapper.COLUMN_LEVEL + " =?", new String[]{String.valueOf(parentId), "2"}, null,
                    null, null, null);

            int count = cursor.getCount();
            if (count > 0) {

                while (cursor.moveToNext()) {

                    mRightsMapper = new RightsMapper();
                    mRightsMapper.setId(cursor.getInt(cursor.getColumnIndex(RightsMapper.COLUMN_ID)));
                    mRightsMapper.setName(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_NAME)));
                    mRightsMapper.setCode(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_CODE)));
                    mRightsMapper.setType(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_TYPE)));
                    mRightsMapper.setLevel(cursor.getInt(cursor.getColumnIndex(RightsMapper.COLUMN_LEVEL)));
                    mRightsMapper.setParentid(cursor.getInt(cursor.getColumnIndex(RightsMapper.COLUMN_PARENTID)));
                    mRightsMapper.setIcon(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_ICON)));
                    mRightsMapper.setIconname(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_ICON_NAME)));
                    mRightsMapper.setIconaddress(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_ICON_ADDRESS)));
                    mRightsMapper.setActive(cursor.getString(cursor.getColumnIndex(RightsMapper.COLUMN_ACTIVE)));

                    aRightsMapper.add(mRightsMapper);
                }
            }
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "getSubProcessRights");
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
            closeDB();
        }

        return aRightsMapper;
    }


    /**
     * Get the Backup data list basis on the upload status
     *
     * @param syncStatus
     * @return
     */
    public ArrayList<BackupDataUtils> getPendingData(int syncStatus) {

        ArrayList<BackupDataUtils> aBackupDataUtils = new ArrayList<>();
        aBackupDataUtils.clear();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.query(BackupDataUtils.TABLE_NAME_BACKUP, null,
                    BackupDataUtils.COLUMN_UPLOAD_STATUS + "=?", new String[]{String.valueOf(syncStatus)}, null,
                    null, null, null);

            if (cursor != null && cursor.getCount() > 0) {

                while (cursor.moveToNext()) {
                    BackupDataUtils backupDataUtils = new BackupDataUtils(
                            cursor.getInt(cursor.getColumnIndex(BackupDataUtils.COLUMN_ID)),
                            cursor.getString(cursor.getColumnIndex(BackupDataUtils.COLUMN_PROCESS)),
                            cursor.getString(cursor.getColumnIndex(BackupDataUtils.COLUMN_FUNCTION)),
                            cursor.getString(cursor.getColumnIndex(BackupDataUtils.COLUMN_JSON)),
                            cursor.getString(cursor.getColumnIndex(BackupDataUtils.COLUMN_URL)),
                            cursor.getInt(cursor.getColumnIndex(BackupDataUtils.COLUMN_UPLOAD_STATUS))
                    );

                    aBackupDataUtils.add(backupDataUtils);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (!cursor.isClosed()) {
                cursor.close();
            }
        }

        return aBackupDataUtils;
    }


    /**
     * Delete the Backup Entry basis on
     *
     * @param id
     * @param process
     * @param function
     */
    public void deleteBackupEntry(int id, String process, String function) {
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            db.delete(DropDown.TABLE_NAME, BackupDataUtils.COLUMN_ID + "=? AND " + BackupDataUtils.COLUMN_PROCESS + "=? AND " + BackupDataUtils.COLUMN_FUNCTION + "=?",
                    new String[]{String.valueOf(id), process, function});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (db.isOpen()) {
                db.close();
            }
        }
    }

    /**
     * Delete the table content
     *
     * @param table
     */
    private void deleteTableContent(String table) {
        SQLiteDatabase db = this.getReadableDatabase();
        try {
            db.execSQL("delete from " + table);
            Log.d("table deleted", table);
        } catch (Exception e) {
            Log.d("delete table content", e.toString());
        }
    }

    /**
     * Update the Shortcut status
     *
     * @param processSubProcessMappers
     */
    public void updateShortcutStatus(ArrayList<ProcessSubProcessMapper> processSubProcessMappers) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ProcessSubProcessMapper model : processSubProcessMappers) {
                ContentValues values = new ContentValues();
                values.put(ProcessSubProcessMapper.COLUMN_SHORTCUT_STATUS, model.getShortcutStatus());
                db.update(ProcessSubProcessMapper.TABLE_NAME, values, ProcessSubProcessMapper.COLUMN_PROCESS_NAME + "=?", new String[]{model.getProcessName()});

            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "updateShortcutStatus");
        } finally {
            db.endTransaction();
            closeDB();
        }

    }

    /**
     * Update Image Methods
     *
     * @param value
     */
    public void updateImage(String value) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(RightsMapper.COLUMN_ICON, value);
            values.put(RightsMapper.COLUMN_ICON_NAME, value);
            values.put(RightsMapper.COLUMN_ICON_ADDRESS, value);
            db.update(RightsMapper.TABLE_NAME, values, null, null);
            db.setTransactionSuccessful();
        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "updateImage");
        } finally {
            db.endTransaction();
            closeDB();
        }

    }

    /**
     * Update the Shortcut status
     *
     * @param processSubProcessMappers
     */
    public void resetShortcutStatus(ArrayList<ProcessSubProcessMapper> processSubProcessMappers) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            for (ProcessSubProcessMapper model : processSubProcessMappers) {
                ContentValues values = new ContentValues();
                values.put(ProcessSubProcessMapper.COLUMN_SHORTCUT_STATUS, GenericConstant.SHORTCUT_NO);
                db.update(ProcessSubProcessMapper.TABLE_NAME, values, null, null);
            }
            db.setTransactionSuccessful();

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "updateShortcutStatus");
        } finally {
            db.endTransaction();
            closeDB();
        }

    }

    /**
     * Updated the Process Flow
     *
     * @param processId
     * @param subProcessId
     * @param processFlowModel
     */
    public void updateProcessFlow(int processId, int subProcessId, ProcessFlowModel processFlowModel) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            values.put(ProcessFlowMapper.COLUMN_FLOW_ID, processFlowModel.getProcessflowid());
            values.put(ProcessFlowMapper.COLUMN_PROCESS_FLOW, processFlowModel.getProcessflowstring());
            db.update(ProcessFlowMapper.TABLE_NAME, values, ProcessFlowMapper.COLUMN_PROCESS_ID + "=? AND " + ProcessFlowMapper.COLUMN_SUB_PROCESS_ID + "=?",
                    new String[]{String.valueOf(processId), String.valueOf(subProcessId)});
            db.setTransactionSuccessful();

        } catch (Exception e) {
            ExceptionLogger.Logger(e.getCause(), e.getMessage(), DatabaseHelper.class, "updateVersion");
        } finally {
            db.endTransaction();
            closeDB();
        }
    }

    /**
     * Clear database
     */
    public void clearDB() {
        try {
            deleteTableContent(DropDown.TABLE_NAME);
            deleteTableContent(ProcessSubProcessMapper.TABLE_NAME);
            deleteTableContent(ProcessFlowMapper.TABLE_NAME);
            deleteTableContent(RightsMapper.TABLE_NAME);
            deleteTableContent(SectionMapper.TABLE_NAME);
            deleteTableContent(TriggerMapper.TABLE_NAME);
            deleteTableContent(VersionMapper.TABLE_NAME);
        } catch (Exception e) {
            Log.e("Utils", "Exception:", e);
        }
    }

}
