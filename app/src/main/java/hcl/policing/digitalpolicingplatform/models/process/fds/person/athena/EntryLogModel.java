package hcl.policing.digitalpolicingplatform.models.process.fds.person.athena;

public class EntryLogModel {

    String investigationId;

    public String getInvestigationId() {
        return investigationId;
    }

    public void setInvestigationId(String investigationId) {
        this.investigationId = investigationId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public String getEntryLog() {
        return entryLog;
    }

    public void setEntryLog(String entryLog) {
        this.entryLog = entryLog;
    }

    String reason;
    String entryLog;
}
