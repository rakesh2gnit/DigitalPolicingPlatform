package hcl.policing.digitalpolicingplatform.offline;

public class BackupDataUtils {


    public static final String TABLE_NAME_BACKUP = "backup";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_PROCESS = "process";
    public static final String COLUMN_FUNCTION = "function";
    public static final String COLUMN_JSON = "backJson";
    public static final String COLUMN_URL = "url";
    public static final String COLUMN_UPLOAD_STATUS = "uploadStatus";

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME_BACKUP + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_PROCESS + " TEXT,"
                    + COLUMN_FUNCTION + " TEXT,"
                    + COLUMN_JSON + " TEXT,"
                    + COLUMN_URL + " INTEGER,"
                    + COLUMN_UPLOAD_STATUS + " INTEGER DEFAULT 0 )";


    private int id;
    private String process;
    private String function;
    private String json;
    private String url;
    private int status;


    public BackupDataUtils(int id, String process, String function, String json, String url, int status) {
        this.id = id;
        this.process = process;
        this.function = function;
        this.json = json;
        this.url = url;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


}
