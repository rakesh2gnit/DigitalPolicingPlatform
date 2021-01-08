package hcl.policing.digitalpolicingplatform.database;

public class VersionMapper {

    public static final String TABLE_NAME = "versionmap";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_VERSION_NAME = "processname";
    public static final String COLUMN_VERSION_NO = "versionno";

    private int id;
    private String processname;
    private String versionno;

    /**
     * Constructor VersionMapper
     * @param id
     * @param processname
     * @param versionno
     */
    public VersionMapper(int id, String processname, String versionno) {
        this.id = id;
        this.processname = processname;
        this.versionno = versionno;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProcessname() {
        return processname;
    }

    public void setProcessname(String processname) {
        this.processname = processname;
    }

    public String getVersionno() {
        return versionno;
    }

    public void setVersionno(String versionno) {
        this.versionno = versionno;
    }



    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_VERSION_NAME + " TEXT,"
                    + COLUMN_VERSION_NO + " INTEGER DEFAULT 0)";

}
