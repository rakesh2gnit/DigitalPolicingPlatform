package hcl.policing.digitalpolicingplatform.models.process.fds.person.dl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DLMenuEDListModel implements Serializable {
    @Expose
    @SerializedName("SuspendSentence")
    private String suspendsentence;
    @Expose
    @SerializedName("SentencingDate")
    private String sentencingdate;
    @Expose
    @SerializedName("SentencingCourt")
    private String sentencingcourt;
    @Expose
    @SerializedName("RehabReduction")
    private String rehabreduction;
    @Expose
    @SerializedName("Points")
    private String points;
    @Expose
    @SerializedName("OtherSentence")
    private String othersentence;
    @Expose
    @SerializedName("OffenceDate")
    private String offencedate;
    @Expose
    @SerializedName("OffenceCode")
    private String offencecode;
    @Expose
    @SerializedName("Fine")
    private String fine;
    @Expose
    @SerializedName("DisqReimposed")
    private String disqreimposed;
    @Expose
    @SerializedName("DisqPeriod")
    private String disqperiod;
    @Expose
    @SerializedName("DisqPendAppeal")
    private String disqpendappeal;
    @Expose
    @SerializedName("DateDisqRemoved")
    private String datedisqremoved;
    @Expose
    @SerializedName("DTTPorDPS")
    private String dttpordps;
    @Expose
    @SerializedName("ConvictionDate")
    private String convictiondate;
    @Expose
    @SerializedName("ConvictionCourt")
    private String convictioncourt;
    @Expose
    @SerializedName("AppealDate")
    private String appealdate;
    @Expose
    @SerializedName("AppealCourt")
    private String appealcourt;

    public String getSuspendsentence() {
        return suspendsentence;
    }

    public void setSuspendsentence(String suspendsentence) {
        this.suspendsentence = suspendsentence;
    }

    public String getSentencingdate() {
        return sentencingdate;
    }

    public void setSentencingdate(String sentencingdate) {
        this.sentencingdate = sentencingdate;
    }

    public String getSentencingcourt() {
        return sentencingcourt;
    }

    public void setSentencingcourt(String sentencingcourt) {
        this.sentencingcourt = sentencingcourt;
    }

    public String getRehabreduction() {
        return rehabreduction;
    }

    public void setRehabreduction(String rehabreduction) {
        this.rehabreduction = rehabreduction;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getOthersentence() {
        return othersentence;
    }

    public void setOthersentence(String othersentence) {
        this.othersentence = othersentence;
    }

    public String getOffencedate() {
        return offencedate;
    }

    public void setOffencedate(String offencedate) {
        this.offencedate = offencedate;
    }

    public String getOffencecode() {
        return offencecode;
    }

    public void setOffencecode(String offencecode) {
        this.offencecode = offencecode;
    }

    public String getFine() {
        return fine;
    }

    public void setFine(String fine) {
        this.fine = fine;
    }

    public String getDisqreimposed() {
        return disqreimposed;
    }

    public void setDisqreimposed(String disqreimposed) {
        this.disqreimposed = disqreimposed;
    }

    public String getDisqperiod() {
        return disqperiod;
    }

    public void setDisqperiod(String disqperiod) {
        this.disqperiod = disqperiod;
    }

    public String getDisqpendappeal() {
        return disqpendappeal;
    }

    public void setDisqpendappeal(String disqpendappeal) {
        this.disqpendappeal = disqpendappeal;
    }

    public String getDatedisqremoved() {
        return datedisqremoved;
    }

    public void setDatedisqremoved(String datedisqremoved) {
        this.datedisqremoved = datedisqremoved;
    }

    public String getDttpordps() {
        return dttpordps;
    }

    public void setDttpordps(String dttpordps) {
        this.dttpordps = dttpordps;
    }

    public String getConvictiondate() {
        return convictiondate;
    }

    public void setConvictiondate(String convictiondate) {
        this.convictiondate = convictiondate;
    }

    public String getConvictioncourt() {
        return convictioncourt;
    }

    public void setConvictioncourt(String convictioncourt) {
        this.convictioncourt = convictioncourt;
    }

    public String getAppealdate() {
        return appealdate;
    }

    public void setAppealdate(String appealdate) {
        this.appealdate = appealdate;
    }

    public String getAppealcourt() {
        return appealcourt;
    }

    public void setAppealcourt(String appealcourt) {
        this.appealcourt = appealcourt;
    }
}
