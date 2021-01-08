package hcl.policing.digitalpolicingplatform.database;

import java.io.Serializable;

public class ProcessSubProcessMapper implements Serializable {

    public transient static final String TABLE_NAME = "processSubprocessMap";
    public transient static final String COLUMN_ID = "id";
    public transient static final String COLUMN_PROCESS_ID = "process_id";
    public transient static final String COLUMN_PROCESS_NAME = "process_name";
    public transient static final String COLUMN_PROCESS_ICON = "process_icon";
    public transient static final String COLUMN_PROCESS_ICON_VERSION = "process_icon_version";
    public transient static final String COLUMN_SHORTCUT_STATUS = "shortcut_status";
    public transient static final String COLUMN_SUB_PROCESS_ID = "sub_process_id";
    public transient static final String COLUMN_SUB_PROCESS_NAME = "sub_process_name";
    public transient static final String COLUMN_SUB_PROCESS_ICON = "sub_process_icon";
    public transient static final String COLUMN_SUB_PROCESS_ICON_VERSION = "sub_process_icon_version";

    private int id;
    private int processId;
    private String processName;
    private String processIcon;
    private int processIconVersion;
    private int shortcutStatus;
    private int subProcessId;
    private String subProcessName;
    private String subProcessIcon;
    private int subProcessIconVersion;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PROCESS_ID + " INTEGER,"
                    + COLUMN_PROCESS_NAME + " TEXT,"
                    + COLUMN_PROCESS_ICON + " TEXT DEFAULT NULL,"
                    + COLUMN_PROCESS_ICON_VERSION + " INTEGER DEFAULT 0,"
                    + COLUMN_SHORTCUT_STATUS + " INTEGER DEFAULT 0,"
                    + COLUMN_SUB_PROCESS_ID + " INTEGER,"
                    + COLUMN_SUB_PROCESS_NAME + " TEXT,"
                    + COLUMN_SUB_PROCESS_ICON + " TEXT DEFAULT NULL,"
                    + COLUMN_SUB_PROCESS_ICON_VERSION + " INTEGER DEFAULT 0)";


    public static final String QUERY_DISTINCT="SELECT DISTINCT "+COLUMN_PROCESS_NAME+", "+COLUMN_PROCESS_ID+", "+COLUMN_PROCESS_ICON+", "
            +COLUMN_SHORTCUT_STATUS+" FROM "+TABLE_NAME;

    public static final String QUERY_DISTINCT_PROCESS="SELECT DISTINCT "+COLUMN_PROCESS_NAME+", "+COLUMN_PROCESS_ID+", "+COLUMN_PROCESS_ICON+", "
            +COLUMN_SHORTCUT_STATUS+" FROM "+TABLE_NAME+ " WHERE "+COLUMN_SHORTCUT_STATUS+"= ?";
    public ProcessSubProcessMapper() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getProcessIcon() {
        return processIcon;
    }

    public void setProcessIcon(String processIcon) {
        this.processIcon = processIcon;
    }

    public int getProcessIconVersion() {
        return processIconVersion;
    }

    public void setProcessIconVersion(int processIconVersion) {
        this.processIconVersion = processIconVersion;
    }
    public int getShortcutStatus() {
        return shortcutStatus;
    }

    public void setShortcutStatus(int shortcutStatus) {
        this.shortcutStatus = shortcutStatus;
    }

    public int getSubProcessId() {
        return subProcessId;
    }

    public void setSubProcessId(int subProcessId) {
        this.subProcessId = subProcessId;
    }

    public String getSubProcessName() {
        return subProcessName;
    }

    public void setSubProcessName(String subProcessName) {
        this.subProcessName = subProcessName;
    }

    public String getSubProcessIcon() {
        return subProcessIcon;
    }

    public void setSubProcessIcon(String subProcessIcon) {
        this.subProcessIcon = subProcessIcon;
    }

    public int getSubProcessIconVersion() {
        return subProcessIconVersion;
    }

    public void setSubProcessIconVersion(int subProcessIconVersion) {
        this.subProcessIconVersion = subProcessIconVersion;
    }


}
