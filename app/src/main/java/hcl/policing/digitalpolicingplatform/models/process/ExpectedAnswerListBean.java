package hcl.policing.digitalpolicingplatform.models.process;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExpectedAnswerListBean implements Serializable {
    @Expose
    @SerializedName("PageSection_detailList")
    private List<PageSection_detailListBean> PageSection_detailList;
    @Expose
    @SerializedName("ProcessingDetails")
    private String ProcessingDetails;
    @Expose
    @SerializedName("IsBackendProcessingRequired")
    private boolean IsBackendProcessingRequired;
    @Expose
    @SerializedName("SpecialLogic")
    private String SpecialLogic;
    @Expose
    @SerializedName("HideSectionId")
    private String HideSectionId;
    @Expose
    @SerializedName("ShowSectionId")
    private String ShowSectionId;
    @Expose
    @SerializedName("HideQuestionId")
    private String HideQuestionId;
    @Expose
    @SerializedName("ShowQuestionId")
    private String ShowQuestionId;
    @Expose
    @SerializedName("HideFieldId")
    private String HideFieldId;
    @Expose
    @SerializedName("ShowFieldId")
    private String ShowFieldId;
    @Expose
    @SerializedName("SkipQuestion")
    private int SkipQuestion;
    @Expose
    @SerializedName("MatchAnswer")
    private boolean MatchAnswer;
    @Expose
    @SerializedName("IsFinalAnswer")
    private boolean IsFinalAnswer;
    @Expose
    @SerializedName("Button_Id")
    private int Button_Id;
    @Expose
    @SerializedName("Answer")
    private String Answer;

    public List<PageSection_detailListBean> getPageSection_detailList() {
        return PageSection_detailList;
    }

    public void setPageSection_detailList(List<PageSection_detailListBean> PageSection_detailList) {
        this.PageSection_detailList = PageSection_detailList;
    }

    public String getProcessingDetails() {
        return ProcessingDetails;
    }

    public void setProcessingDetails(String ProcessingDetails) {
        this.ProcessingDetails = ProcessingDetails;
    }

    public boolean getIsBackendProcessingRequired() {
        return IsBackendProcessingRequired;
    }

    public void setIsBackendProcessingRequired(boolean IsBackendProcessingRequired) {
        this.IsBackendProcessingRequired = IsBackendProcessingRequired;
    }

    public String getSpecialLogic() {
        return SpecialLogic;
    }

    public void setSpecialLogic(String SpecialLogic) {
        this.SpecialLogic = SpecialLogic;
    }

    public String getHideSectionId() {
        return HideSectionId;
    }

    public void setHideSectionId(String hideSectionId) {
        HideSectionId = hideSectionId;
    }

    public String getShowSectionId() {
        return ShowSectionId;
    }

    public void setShowSectionId(String showSectionId) {
        ShowSectionId = showSectionId;
    }

    public String getHideQuestionId() {
        return HideQuestionId;
    }

    public void setHideQuestionId(String hideQuestionId) {
        HideQuestionId = hideQuestionId;
    }

    public String getShowQuestionId() {
        return ShowQuestionId;
    }

    public void setShowQuestionId(String showQuestionId) {
        ShowQuestionId = showQuestionId;
    }

    public String getHideFieldId() {
        return HideFieldId;
    }

    public void setHideFieldId(String hideFieldId) {
        HideFieldId = hideFieldId;
    }

    public String getShowFieldId() {
        return ShowFieldId;
    }

    public void setShowFieldId(String showFieldId) {
        ShowFieldId = showFieldId;
    }

    public int getSkipQuestion() {
        return SkipQuestion;
    }

    public void setSkipQuestion(int skipQuestion) {
        SkipQuestion = skipQuestion;
    }

    public boolean getMatchAnswer() {
        return MatchAnswer;
    }

    public void setMatchAnswer(boolean MatchAnswer) {
        this.MatchAnswer = MatchAnswer;
    }

    public boolean getIsFinalAnswer() {
        return IsFinalAnswer;
    }

    public void setIsFinalAnswer(boolean IsFinalAnswer) {
        this.IsFinalAnswer = IsFinalAnswer;
    }

    public int getButton_Id() {
        return Button_Id;
    }

    public void setButton_Id(int button_Id) {
        Button_Id = button_Id;
    }

    public String getAnswer() {
        return Answer;
    }

    public void setAnswer(String Answer) {
        this.Answer = Answer;
    }
}
