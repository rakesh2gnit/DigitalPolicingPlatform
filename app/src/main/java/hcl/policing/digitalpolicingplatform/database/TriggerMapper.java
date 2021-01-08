package hcl.policing.digitalpolicingplatform.database;

public class TriggerMapper {


    public static final String TABLE_NAME = "triggerMap";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CUSTOMER_ID = "customer_id";
    public static final String COLUMN_TRIGGER_ID = "trigger_id";
    public static final String COLUMN_PROCESS_ID = "process_id";
    public static final String COLUMN_SUB_PROCESS_ID = "sub_process_id";
    public static final String COLUMN_PROCESS_NAME = "process_name";
    public static final String COLUMN_SUB_PROCESS_NAME = "sub_process_name";
    public static final String COLUMN_TRIGGER_NAME = "trigger_name";
    public static final String COLUMN_CREATED_DATE = "created_date";
    public static final String COLUMN_MODIFIED_DATE = "modified_date";
    public static final String COLUMN_TRIGGER_VERSION = "trigger_version";

    private int id;
    private int customerId;
    private int processId;
    private int subProcessId;
    private int triggerId;
    private String processName;
    private String subProcessName;
    private String triggerName;
    private String createdDate;
    private String modifiedDate;
    private int triggerVersion;


    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CUSTOMER_ID + " INTEGER,"
                    + COLUMN_PROCESS_ID + " INTEGER,"
                    + COLUMN_SUB_PROCESS_ID + " INTEGER,"
                    + COLUMN_TRIGGER_ID + " INTEGER,"
                    + COLUMN_PROCESS_NAME + " TEXT,"
                    + COLUMN_SUB_PROCESS_NAME + " TEXT,"
                    + COLUMN_TRIGGER_NAME + " TEXT,"
                    + COLUMN_CREATED_DATE + " TEXT,"
                    + COLUMN_MODIFIED_DATE + " TEXT,"
                    + COLUMN_TRIGGER_VERSION + " INTEGER DEFAULT 0)";

    public TriggerMapper() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getProcessId() {
        return processId;
    }

    public void setProcessId(int processId) {
        this.processId = processId;
    }

    public int getSubProcessId() {
        return subProcessId;
    }

    public void setSubProcessId(int subProcessId) {
        this.subProcessId = subProcessId;
    }

    public int getTriggerId() {
        return triggerId;
    }

    public void setTriggerId(int triggerId) {
        this.triggerId = triggerId;
    }

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getSubProcessName() {
        return subProcessName;
    }

    public void setSubProcessName(String subProcessName) {
        this.subProcessName = subProcessName;
    }

    public String getTriggerName() {
        return triggerName;
    }

    public void setTriggerName(String triggerName) {
        this.triggerName = triggerName;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(String modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public int getTriggerVersion() {
        return triggerVersion;
    }

    public void setTriggerVersion(int triggerVersion) {
        this.triggerVersion = triggerVersion;
    }


}
