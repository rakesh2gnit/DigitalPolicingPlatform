package hcl.policing.digitalpolicingplatform.database;

public class TriggerSpeech {

    public static final String TABLE_NAME = "speech";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TRIGGER_NAME = "triggername";
    public static final String COLUMN_PROCESS_NAME = "process";
    public static final String COLUMN_SUBPROCESS = "subprocess";

    private int id;
    private int triggername;
    private String processname;
    private String subprocess;
    private String version;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_TRIGGER_NAME + " TEXT,"
                    + COLUMN_PROCESS_NAME + " TEXT,"
                    + COLUMN_SUBPROCESS + " TEXT,"
                    //+ COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public TriggerSpeech() {
    }

    /**
     * Constructor TriggerSpeech
     * @param id
     * @param triggername
     * @param processname
     * @param subprocess
     */
    public TriggerSpeech(int id, int triggername, String processname, String subprocess) {
        this.id = id;
        this.triggername = triggername;
        this.processname = processname;
        this.subprocess = subprocess;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTriggername() {
        return triggername;
    }

    public void setTriggername(int triggername) {
        this.triggername = triggername;
    }

    public String getProcessname() {
        return processname;
    }

    public void setProcessname(String processname) {
        this.processname = processname;
    }

    public String getSubprocess() {
        return subprocess;
    }

    public void setSubprocess(String subprocess) {
        this.subprocess = subprocess;
    }
}
