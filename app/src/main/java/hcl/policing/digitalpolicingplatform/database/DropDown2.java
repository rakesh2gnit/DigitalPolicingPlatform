package hcl.policing.digitalpolicingplatform.database;

public class DropDown2 {

    public static final String TABLE_NAME = "dropdown";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_SNO = "sno";
    public static final String COLUMN_CODE = "code";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_GROUPCODE = "groupcode";
    public static final String COLUMN_PROCESSNAME = "processname";
    public static final String COLUMN_PARENTID = "parentid";
    public static final String COLUMN_ORDERBY = "orderby";
    public static final String COLUMN_ORDERRANK = "orderrank";

    private int id;
    private String sno;
    private String code;
    private String description;
    private String groupcode;
    private String processname;
    private String parentid;
    private String orderby;
    private String orderrank;
    private String status;

    // Create table SQL query
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + "("
                    + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                    + COLUMN_SNO + " TEXT,"
                    + COLUMN_CODE + " TEXT,"
                    + COLUMN_DESCRIPTION + " TEXT,"
                    + COLUMN_GROUPCODE + " TEXT,"
                    + COLUMN_PROCESSNAME + " TEXT,"
                    + COLUMN_PARENTID + " TEXT,"
                    + COLUMN_ORDERBY + " TEXT,"
                    + COLUMN_ORDERRANK + " TEXT"
                    //+ COLUMN_TIMESTAMP + " DATETIME DEFAULT CURRENT_TIMESTAMP"
                    + ")";

    public DropDown2() {
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGroupcode() {
        return groupcode;
    }

    public void setGroupcode(String groupcode) {
        this.groupcode = groupcode;
    }

    public String getProcessname() {
        return processname;
    }

    public void setProcessname(String processname) {
        this.processname = processname;
    }

    public String getParentid() {
        return parentid;
    }

    public void setParentid(String parentid) {
        this.parentid = parentid;
    }

    public String getOrderby() {
        return orderby;
    }

    public void setOrderby(String orderby) {
        this.orderby = orderby;
    }

    public String getOrderrank() {
        return orderrank;
    }

    public void setOrderrank(String orderrank) {
        this.orderrank = orderrank;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
