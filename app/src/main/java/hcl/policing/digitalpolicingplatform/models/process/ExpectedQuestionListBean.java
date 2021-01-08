package hcl.policing.digitalpolicingplatform.models.process;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class ExpectedQuestionListBean implements Serializable {
    @Expose
    @SerializedName("ActualQuestion")
    private String ActualQuestion;
    @Expose
    @SerializedName("Question_Id")
    private int Question_Id;
    @Expose
    @SerializedName("IsFinalQuestion")
    private boolean IsFinalQuestion;
    @Expose
    @SerializedName("SkipQuestion")
    private int SkipQuestion;
    @Expose
    @SerializedName("SpecialLogic")
    private String SpecialLogic;
    @Expose
    @SerializedName("DialogHeading")
    private String DialogHeading;
    @Expose
    @SerializedName("AnswerExpected")
    private boolean AnswerExpected;
    @Expose
    @SerializedName("Question_Visibility")
    private boolean Question_Visibility;
    @Expose
    @SerializedName("QuestionDependentId")
    private String QuestionDependentId;
    @Expose
    @SerializedName("QuestionDependent")
    private boolean QuestionDependent;
    @Expose
    @SerializedName("SectionDependent")
    private boolean SectionDependent;
    @Expose
    @SerializedName("IsAnswermultiselect")
    private boolean IsAnswermultiselect;
    @Expose
    @SerializedName("DisplayAnswerswithQuestion")
    private boolean DisplayAnswerswithQuestion;
    @Expose
    @SerializedName("SubSectionList")
    private List<SubSectionListBean> subSectionList;
    @Expose
    @SerializedName("ExpectedAnswerList")
    private List<ExpectedAnswerListBean> ExpectedAnswerList;
    @Expose
    @SerializedName("PageSection_detailList")
    private List<PageSection_detailListBean> PageSection_detailList;

    public String getActualQuestion() {
        return ActualQuestion;
    }

    public void setActualQuestion(String actualQuestion) {
        ActualQuestion = actualQuestion;
    }

    public int getQuestion_Id() {
        return Question_Id;
    }

    public void setQuestion_Id(int question_Id) {
        Question_Id = question_Id;
    }

    public boolean isFinalQuestion() {
        return IsFinalQuestion;
    }

    public void setFinalQuestion(boolean finalQuestion) {
        IsFinalQuestion = finalQuestion;
    }

    public int getSkipQuestion() {
        return SkipQuestion;
    }

    public void setSkipQuestion(int skipQuestion) {
        SkipQuestion = skipQuestion;
    }

    public String getSpecialLogic() {
        return SpecialLogic;
    }

    public void setSpecialLogic(String specialLogic) {
        SpecialLogic = specialLogic;
    }

    public String getDialogHeading() {
        return DialogHeading;
    }

    public void setDialogHeading(String dialogHeading) {
        DialogHeading = dialogHeading;
    }

    public boolean isAnswerExpected() {
        return AnswerExpected;
    }

    public void setAnswerExpected(boolean answerExpected) {
        AnswerExpected = answerExpected;
    }

    public boolean isQuestion_Visibility() {
        return Question_Visibility;
    }

    public void setQuestion_Visibility(boolean question_Visibility) {
        Question_Visibility = question_Visibility;
    }

    public String getQuestionDependentId() {
        return QuestionDependentId;
    }

    public void setQuestionDependentId(String questionDependentId) {
        QuestionDependentId = questionDependentId;
    }

    public boolean isQuestionDependent() {
        return QuestionDependent;
    }

    public void setQuestionDependent(boolean questionDependent) {
        QuestionDependent = questionDependent;
    }

    public boolean isSectionDependent() {
        return SectionDependent;
    }

    public void setSectionDependent(boolean sectionDependent) {
        SectionDependent = sectionDependent;
    }

    public boolean isAnswermultiselect() {
        return IsAnswermultiselect;
    }

    public void setAnswermultiselect(boolean answermultiselect) {
        IsAnswermultiselect = answermultiselect;
    }

    public boolean isDisplayAnswerswithQuestion() {
        return DisplayAnswerswithQuestion;
    }

    public void setDisplayAnswerswithQuestion(boolean displayAnswerswithQuestion) {
        DisplayAnswerswithQuestion = displayAnswerswithQuestion;
    }

    public List<SubSectionListBean> getSubSectionList() {
        return subSectionList;
    }

    public void setSubSectionList(List<SubSectionListBean> subSectionList) {
        this.subSectionList = subSectionList;
    }

    public List<ExpectedAnswerListBean> getExpectedAnswerList() {
        return ExpectedAnswerList;
    }

    public void setExpectedAnswerList(List<ExpectedAnswerListBean> expectedAnswerList) {
        ExpectedAnswerList = expectedAnswerList;
    }

    public List<PageSection_detailListBean> getPageSection_detailList() {
        return PageSection_detailList;
    }

    public void setPageSection_detailList(List<PageSection_detailListBean> pageSection_detailList) {
        PageSection_detailList = pageSection_detailList;
    }
}
