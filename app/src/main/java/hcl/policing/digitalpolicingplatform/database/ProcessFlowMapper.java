package hcl.policing.digitalpolicingplatform.database;

public class ProcessFlowMapper {

    static final String TABLE_NAME = "processFlowMap";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_CUSTOMER_ID = "customer_id";
    public static final String COLUMN_PROCESS_ID = "process_id";
    public static final String COLUMN_PROCESS_NAME = "process_name";
    public static final String COLUMN_SUB_PROCESS_ID = "sub_process_id";
    public static final String COLUMN_SUB_PROCESS_NAME = "sub_process_name";
    public static final String COLUMN_FLOW_ID = "flowId";
    public static final String COLUMN_PROCESS_FLOW = "process_flow";
    public static final String COLUMN_FLOW_VERSION = "flow_version";

    private int id;
    private int customerId;
    private int processId;
    private int subProcessId;
    private int flowId;
    private String processName;
    private String subProcessName;
    private String processFlow;
    private int flowVersion;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_CUSTOMER_ID + " INTEGER,"
                    + COLUMN_PROCESS_ID + " INTEGER,"
                    + COLUMN_SUB_PROCESS_ID + " INTEGER,"
                    + COLUMN_FLOW_ID + " INTEGER DEFAULT 0,"
                    + COLUMN_PROCESS_NAME + " TEXT,"
                    + COLUMN_SUB_PROCESS_NAME + " TEXT,"
                    + COLUMN_PROCESS_FLOW + " TEXT DEFAULT NULL,"
                    + COLUMN_FLOW_VERSION + " INTEGER DEFAULT 0)";

    public ProcessFlowMapper() {
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

    public int getFlowId() {
        return flowId;
    }

    public void setFlowId(int flowId) {
        this.flowId = flowId;
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

    public String getProcessFlow() {
        return processFlow;
    }

    public void setProcessFlow(String processFlow) {
        this.processFlow = processFlow;
    }

    public int getFlowVersion() {
        return flowVersion;
    }

    public void setFlowVersion(int flowVersion) {
        this.flowVersion = flowVersion;
    }
}
