package hcl.policing.digitalpolicingplatform.models.process;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ProcesslistResponse implements Serializable {

    @Expose
    @SerializedName("Processes")
    private List<ProcessesBean> processes;

    public List<ProcessesBean> getProcesses() {
        return processes;
    }

    public void setProcesses(List<ProcessesBean> processes) {
        this.processes = processes;
    }

    public class ProcessesBean {
        @Expose
        @SerializedName("SubProcesses")
        private List<SubProcessesBean> subprocesses;
        @Expose
        @SerializedName("ParentId")
        private int parentid;
        @Expose
        @SerializedName("ObjectType")
        private String objecttype;
        @Expose
        @SerializedName("ObjectId")
        private String objectid;
        @Expose
        @SerializedName("Name")
        private String name;
        @Expose
        @SerializedName("Url")
        private String url;
        @Expose
        @SerializedName("IconVersion")
        private int iconversion;

        public List<SubProcessesBean> getSubprocesses() {
            return subprocesses;
        }

        public void setSubprocesses(List<SubProcessesBean> subprocesses) {
            this.subprocesses = subprocesses;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        public String getObjecttype() {
            return objecttype;
        }

        public void setObjecttype(String objecttype) {
            this.objecttype = objecttype;
        }

        public String getObjectid() {
            return objectid;
        }

        public void setObjectid(String objectid) {
            this.objectid = objectid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getIconversion() {
            return iconversion;
        }

        public void setIconversion(int iconversion) {
            this.iconversion = iconversion;
        }

    }

    public class SubProcessesBean {
        @Expose
        @SerializedName("Url")
        private String url;
        @Expose
        @SerializedName("IconVersion")
        private int iconversion;
        @Expose
        @SerializedName("Sections")
        private List<SectionsBean> sections;
        @Expose
        @SerializedName("ParentId")
        private int parentid;
        @Expose
        @SerializedName("ObjectType")
        private String objecttype;
        @Expose
        @SerializedName("ObjectId")
        private String objectid;
        @Expose
        @SerializedName("Name")
        private String name;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public List<SectionsBean> getSections() {
            return sections;
        }

        public void setSections(List<SectionsBean> sections) {
            this.sections = sections;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        public String getObjecttype() {
            return objecttype;
        }

        public void setObjecttype(String objecttype) {
            this.objecttype = objecttype;
        }

        public String getObjectid() {
            return objectid;
        }

        public void setObjectid(String objectid) {
            this.objectid = objectid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
        public int getIconversion() {
            return iconversion;
        }

        public void setIconversion(int iconversion) {
            this.iconversion = iconversion;
        }

    }

    public class SectionsBean {
        @Expose
        @SerializedName("ProcessId")
        private int processid;
        @Expose
        @SerializedName("ParentId")
        private int parentid;
        @Expose
        @SerializedName("ObjectType")
        private String objecttype;
        @Expose
        @SerializedName("IsSearchSection")
        private boolean issearchsection;
        @Expose
        @SerializedName("Functions")
        private List<String> functions;
        @Expose
        @SerializedName("ObjectId")
        private String objectid;
        @Expose
        @SerializedName("Name")
        private String name;

        public int getProcessid() {
            return processid;
        }

        public void setProcessid(int processid) {
            this.processid = processid;
        }

        public int getParentid() {
            return parentid;
        }

        public void setParentid(int parentid) {
            this.parentid = parentid;
        }

        public String getObjecttype() {
            return objecttype;
        }

        public void setObjecttype(String objecttype) {
            this.objecttype = objecttype;
        }

        public boolean getIssearchsection() {
            return issearchsection;
        }

        public void setIssearchsection(boolean issearchsection) {
            this.issearchsection = issearchsection;
        }

        public List<String> getFunctions() {
            return functions;
        }

        public void setFunctions(List<String> functions) {
            this.functions = functions;
        }

        public String getObjectid() {
            return objectid;
        }

        public void setObjectid(String objectid) {
            this.objectid = objectid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
