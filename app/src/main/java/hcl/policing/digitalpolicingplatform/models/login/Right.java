package hcl.policing.digitalpolicingplatform.models.login;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Right implements Serializable {

    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("ObjectId")
    @Expose
    private String objectId;
    @SerializedName("ObjectType")
    @Expose
    private String objectType;
    @SerializedName("ParentId")
    @Expose
    private Integer parentId;
    @SerializedName("SubProcesses")
    @Expose
    private List<SubProcess> subProcesses = null;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public List<SubProcess> getSubProcesses() {
        return subProcesses;
    }

    public void setSubProcesses(List<SubProcess> subProcesses) {
        this.subProcesses = subProcesses;
    }

    public class SubProcess {

        @SerializedName("Name")
        @Expose
        private String name;
        @SerializedName("ObjectId")
        @Expose
        private String objectId;
        @SerializedName("ObjectType")
        @Expose
        private String objectType;
        @SerializedName("ParentId")
        @Expose
        private Integer parentId;
        @SerializedName("Sections")
        @Expose
        private List<Section> sections = null;
        @SerializedName("Url")
        @Expose
        private String url;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getObjectId() {
            return objectId;
        }

        public void setObjectId(String objectId) {
            this.objectId = objectId;
        }

        public String getObjectType() {
            return objectType;
        }

        public void setObjectType(String objectType) {
            this.objectType = objectType;
        }

        public Integer getParentId() {
            return parentId;
        }

        public void setParentId(Integer parentId) {
            this.parentId = parentId;
        }

        public List<Section> getSections() {
            return sections;
        }

        public void setSections(List<Section> sections) {
            this.sections = sections;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public class Section {

            @SerializedName("Functions")
            @Expose
            private List<Function> functions = null;
            @SerializedName("IsSearchSection")
            @Expose
            private Boolean isSearchSection;
            @SerializedName("Name")
            @Expose
            private String name;
            @SerializedName("ObjectId")
            @Expose
            private String objectId;
            @SerializedName("ObjectType")
            @Expose
            private Object objectType;
            @SerializedName("ParentId")
            @Expose
            private Integer parentId;
            @SerializedName("ProcessId")
            @Expose
            private Integer processId;

            public List<Function> getFunctions() {
                return functions;
            }

            public void setFunctions(List<Function> functions) {
                this.functions = functions;
            }

            public Boolean getIsSearchSection() {
                return isSearchSection;
            }

            public void setIsSearchSection(Boolean isSearchSection) {
                this.isSearchSection = isSearchSection;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getObjectId() {
                return objectId;
            }

            public void setObjectId(String objectId) {
                this.objectId = objectId;
            }

            public Object getObjectType() {
                return objectType;
            }

            public void setObjectType(Object objectType) {
                this.objectType = objectType;
            }

            public Integer getParentId() {
                return parentId;
            }

            public void setParentId(Integer parentId) {
                this.parentId = parentId;
            }

            public Integer getProcessId() {
                return processId;
            }

            public void setProcessId(Integer processId) {
                this.processId = processId;
            }

            public class Function {

                @SerializedName("Name")
                @Expose
                private String name;
                @SerializedName("ObjectId")
                @Expose
                private String objectId;
                @SerializedName("ObjectType")
                @Expose
                private Object objectType;
                @SerializedName("ParentId")
                @Expose
                private Integer parentId;
                @SerializedName("ProcessId")
                @Expose
                private Integer processId;
                @SerializedName("SubProcessId")
                @Expose
                private Integer subProcessId;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getObjectId() {
                    return objectId;
                }

                public void setObjectId(String objectId) {
                    this.objectId = objectId;
                }

                public Object getObjectType() {
                    return objectType;
                }

                public void setObjectType(Object objectType) {
                    this.objectType = objectType;
                }

                public Integer getParentId() {
                    return parentId;
                }

                public void setParentId(Integer parentId) {
                    this.parentId = parentId;
                }

                public Integer getProcessId() {
                    return processId;
                }

                public void setProcessId(Integer processId) {
                    this.processId = processId;
                }

                public Integer getSubProcessId() {
                    return subProcessId;
                }

                public void setSubProcessId(Integer subProcessId) {
                    this.subProcessId = subProcessId;
                }

            }

        }

    }
}