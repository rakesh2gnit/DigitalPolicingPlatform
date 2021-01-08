package hcl.policing.digitalpolicingplatform.models.process;

import java.io.Serializable;
import java.util.ArrayList;

public class GroupsLogDTO implements Serializable {

    private String processName;
    private String time;
    private String title;
    private String stormId;
    private String athenaId;
    private String entriesCount;
    private String groupFlag;
    private ArrayList<ProcessLogDTO> logsList;

    public String getProcessName() {
        return processName;
    }

    public void setProcessName(String processName) {
        this.processName = processName;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStormId() {
        return stormId;
    }

    public void setStormId(String stormId) {
        this.stormId = stormId;
    }

    public String getAthenaId() {
        return athenaId;
    }

    public void setAthenaId(String athenaId) {
        this.athenaId = athenaId;
    }

    public String getEntriesCount() {
        return entriesCount;
    }

    public void setEntriesCount(String entriesCount) {
        this.entriesCount = entriesCount;
    }

    public String getGroupFlag() {
        return groupFlag;
    }

    public void setGroupFlag(String groupFlag) {
        this.groupFlag = groupFlag;
    }

    public ArrayList<ProcessLogDTO> getLogsList() {
        return logsList;
    }

    public void setLogsList(ArrayList<ProcessLogDTO> logsList) {
        this.logsList = logsList;
    }
}
