package hcl.policing.digitalpolicingplatform.database;

public class JSONMapper {

    public static final String TABLE_NAME = "jsonmap";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SNO = "rightscode";
    public static final String COLUMN_JSON = "json";
    public static final String COLUMN_PROCESS_NAME = "process";
    public static final String COLUMN_SUBPROCESS = "subprocess";
    public static final String COLUMN_VERSION = "version";

    private int id;
    private int sno;
    private String json;
    private String processname;
    private String subprocess;
    private String version;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SNO + " INTEGER,"
                    + COLUMN_JSON + " TEXT,"
                    + COLUMN_PROCESS_NAME + " TEXT,"
                    + COLUMN_SUBPROCESS + " TEXT,"
                    //+ COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public JSONMapper() {
    }

    /**
     * Constructor JSON Mapper
     * @param id
     * @param sno
     * @param json
     * @param processname
     * @param subprocess
     */
    public JSONMapper(int id, int sno, String json, String processname, String subprocess) {
        this.id = id;
        this.sno = sno;
        this.json = json;
        this.processname = processname;
        this.subprocess = subprocess;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSno() {
        return sno;
    }

    public void setSno(int sno) {
        this.sno = sno;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
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

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
