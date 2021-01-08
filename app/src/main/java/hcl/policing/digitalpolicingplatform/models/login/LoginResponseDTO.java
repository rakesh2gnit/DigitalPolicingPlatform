package hcl.policing.digitalpolicingplatform.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

import hcl.policing.digitalpolicingplatform.models.process.officer.UserModel;

public class LoginResponseDTO implements Serializable {

    @Expose
    @SerializedName("SuccessFlag")
    private boolean successflag;
    @Expose
    @SerializedName("Message")
    private String message;
    @Expose
    @SerializedName("ProcessFlowVersions")
    private List<ProcessFlowVersionsBean> processflowversions;
    @Expose
    @SerializedName("VersionManagements")
    private List<VersionManagementsBean> versionmanagements;
    @Expose
    @SerializedName("UserAccessRights")
    private List<UserAccessRightsBean> useraccessrights;
    @Expose
    @SerializedName("UserAccessRightsFormatted")
    private UseraccessrightsformattedBean useraccessrightsformatted;
    @Expose
    @SerializedName("User")
    private UserModel user;

    public boolean getSuccessflag() {
        return successflag;
    }

    public void setSuccessflag(boolean successflag) {
        this.successflag = successflag;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<ProcessFlowVersionsBean> getProcessflowversions() {
        return processflowversions;
    }

    public void setProcessflowversions(List<ProcessFlowVersionsBean> processflowversions) {
        this.processflowversions = processflowversions;
    }

    public List<VersionManagementsBean> getVersionmanagements() {
        return versionmanagements;
    }

    public void setVersionmanagements(List<VersionManagementsBean> versionmanagements) {
        this.versionmanagements = versionmanagements;
    }

    public List<UserAccessRightsBean> getUseraccessrights() {
        return useraccessrights;
    }

    public void setUseraccessrights(List<UserAccessRightsBean> useraccessrights) {
        this.useraccessrights = useraccessrights;
    }

    public UseraccessrightsformattedBean getUseraccessrightsformatted() {
        return useraccessrightsformatted;
    }

    public void setUseraccessrightsformatted(UseraccessrightsformattedBean useraccessrightsformatted) {
        this.useraccessrightsformatted = useraccessrightsformatted;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }

    public static class ProcessFlowVersionsBean {
        @Expose
        @SerializedName("VersionNumber")
        private int versionnumber;
        @Expose
        @SerializedName("SubProcessName")
        private String subprocessname;
        @Expose
        @SerializedName("SubProcessId")
        private int subprocessid;
        @Expose
        @SerializedName("ProcessName")
        private String processname;
        @Expose
        @SerializedName("ProcessId")
        private int processid;
        @Expose
        @SerializedName("CustomerId")
        private int customerid;

        public int getVersionnumber() {
            return versionnumber;
        }

        public void setVersionnumber(int versionnumber) {
            this.versionnumber = versionnumber;
        }

        public String getSubprocessname() {
            return subprocessname;
        }

        public void setSubprocessname(String subprocessname) {
            this.subprocessname = subprocessname;
        }

        public int getSubprocessid() {
            return subprocessid;
        }

        public void setSubprocessid(int subprocessid) {
            this.subprocessid = subprocessid;
        }

        public String getProcessname() {
            return processname;
        }

        public void setProcessname(String processname) {
            this.processname = processname;
        }

        public int getProcessid() {
            return processid;
        }

        public void setProcessid(int processid) {
            this.processid = processid;
        }

        public int getCustomerid() {
            return customerid;
        }

        public void setCustomerid(int customerid) {
            this.customerid = customerid;
        }
    }

    public static class VersionManagementsBean {
        @Expose
        @SerializedName("VersionNumber")
        private int versionnumber;
        @Expose
        @SerializedName("VersionName")
        private String versionname;
        @Expose
        @SerializedName("CustomerId")
        private int customerid;

        public int getVersionnumber() {
            return versionnumber;
        }

        public void setVersionnumber(int versionnumber) {
            this.versionnumber = versionnumber;
        }

        public String getVersionname() {
            return versionname;
        }

        public void setVersionname(String versionname) {
            this.versionname = versionname;
        }

        public int getCustomerid() {
            return customerid;
        }

        public void setCustomerid(int customerid) {
            this.customerid = customerid;
        }
    }

    public static class UserAccessRightsBean {
        @Expose
        @SerializedName("SubProcessName")
        private String subprocessname;
        @Expose
        @SerializedName("SubProcessId")
        private String subprocessid;
        @Expose
        @SerializedName("ProcessName")
        private String processname;
        @Expose
        @SerializedName("ProcessId")
        private String processid;
        @Expose
        @SerializedName("GroupName")
        private String groupname;
        @Expose
        @SerializedName("UserName")
        private String username;
        @Expose
        @SerializedName("UserId")
        private int userid;

        public String getSubprocessname() {
            return subprocessname;
        }

        public void setSubprocessname(String subprocessname) {
            this.subprocessname = subprocessname;
        }

        public String getSubprocessid() {
            return subprocessid;
        }

        public void setSubprocessid(String subprocessid) {
            this.subprocessid = subprocessid;
        }

        public String getProcessname() {
            return processname;
        }

        public void setProcessname(String processname) {
            this.processname = processname;
        }

        public String getProcessid() {
            return processid;
        }

        public void setProcessid(String processid) {
            this.processid = processid;
        }

        public String getGroupname() {
            return groupname;
        }

        public void setGroupname(String groupname) {
            this.groupname = groupname;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getUserid() {
            return userid;
        }

        public void setUserid(int userid) {
            this.userid = userid;
        }
    }

    public static class UseraccessrightsformattedBean {
        @Expose
        @SerializedName("Processes")
        private List<ProcessesBean> processes;

        public List<ProcessesBean> getProcesses() {
            return processes;
        }

        public void setProcesses(List<ProcessesBean> processes) {
            this.processes = processes;
        }
    }

    public static class ProcessesBean {
        @Expose
        @SerializedName("SubProcesses")
        private List<SubProcessesBean> subprocesses;
        @Expose
        @SerializedName("ProcessLogoUrl")
        private String processlogourl;
        @Expose
        @SerializedName("CustomerId")
        private int customerid;
        @Expose
        @SerializedName("ProcessName")
        private String processname;
        @Expose
        @SerializedName("ProcessId")
        private String processid;

        public List<SubProcessesBean> getSubprocesses() {
            return subprocesses;
        }

        public void setSubprocesses(List<SubProcessesBean> subprocesses) {
            this.subprocesses = subprocesses;
        }

        public String getProcesslogourl() {
            return processlogourl;
        }

        public void setProcesslogourl(String processlogourl) {
            this.processlogourl = processlogourl;
        }

        public int getCustomerid() {
            return customerid;
        }

        public void setCustomerid(int customerid) {
            this.customerid = customerid;
        }

        public String getProcessname() {
            return processname;
        }

        public void setProcessname(String processname) {
            this.processname = processname;
        }

        public String getProcessid() {
            return processid;
        }

        public void setProcessid(String processid) {
            this.processid = processid;
        }
    }

    public static class SubProcessesBean {
        @Expose
        @SerializedName("Sections")
        private List<SectionsBean> sections;
        @Expose
        @SerializedName("SubProcessLogoUrl")
        private String subprocesslogourl;
        @Expose
        @SerializedName("SubProcessName")
        private String subprocessname;
        @Expose
        @SerializedName("SubProcessId")
        private String subprocessid;

        public List<SectionsBean> getSections() {
            return sections;
        }

        public void setSections(List<SectionsBean> sections) {
            this.sections = sections;
        }

        public String getSubprocesslogourl() {
            return subprocesslogourl;
        }

        public void setSubprocesslogourl(String subprocesslogourl) {
            this.subprocesslogourl = subprocesslogourl;
        }

        public String getSubprocessname() {
            return subprocessname;
        }

        public void setSubprocessname(String subprocessname) {
            this.subprocessname = subprocessname;
        }

        public String getSubprocessid() {
            return subprocessid;
        }

        public void setSubprocessid(String subprocessid) {
            this.subprocessid = subprocessid;
        }
    }

    public static class SectionsBean {
        @Expose
        @SerializedName("SectionName")
        private String sectionname;
        @Expose
        @SerializedName("SectionId")
        private String sectionid;

        public String getSectionname() {
            return sectionname;
        }

        public void setSectionname(String sectionname) {
            this.sectionname = sectionname;
        }

        public String getSectionid() {
            return sectionid;
        }

        public void setSectionid(String sectionid) {
            this.sectionid = sectionid;
        }
    }
}