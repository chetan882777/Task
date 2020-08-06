package com.example.task.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Question implements Parcelable {

    @SerializedName("ques_id")
    @Expose()
    private String quesId;

    @SerializedName("subject_id")
    @Expose()
    private String subjectId;

    @SerializedName("question")
    @Expose()
    private String question;

    @SerializedName("ans_1")
    @Expose()
    private String ans1;

    @SerializedName("ans_2")
    @Expose()
    private String ans2;

    @SerializedName("ans_3")
    @Expose()
    private String ans3;

    @SerializedName("ans_4")
    @Expose()
    private String ans4;

    @SerializedName("ans_5")
    @Expose()
    private String ans5;

    @SerializedName("ans_6")
    @Expose()
    private String ans6;

    @SerializedName("total_ans")
    @Expose()
    private String totalAns;

    @SerializedName("r_ans")
    @Expose()
    private String rAns;

    public String getQuesId() {
        return quesId;
    }

    public void setQuesId(String quesId) {
        this.quesId = quesId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAns1() {
        return ans1;
    }

    public void setAns1(String ans1) {
        this.ans1 = ans1;
    }

    public String getAns2() {
        return ans2;
    }

    public void setAns2(String ans2) {
        this.ans2 = ans2;
    }

    public String getAns3() {
        return ans3;
    }

    public void setAns3(String ans3) {
        this.ans3 = ans3;
    }

    public String getAns4() {
        return ans4;
    }

    public void setAns4(String ans4) {
        this.ans4 = ans4;
    }

    public String getAns5() {
        return ans5;
    }

    public void setAns5(String ans5) {
        this.ans5 = ans5;
    }

    public String getAns6() {
        return ans6;
    }

    public void setAns6(String ans6) {
        this.ans6 = ans6;
    }

    public String getTotalAns() {
        return totalAns;
    }

    public void setTotalAns(String totalAns) {
        this.totalAns = totalAns;
    }

    public String getrAns() {
        return rAns;
    }

    public void setrAns(String rAns) {
        this.rAns = rAns;
    }

    @Override
    public String toString() {
        return "Question{" +
                "quesId='" + quesId + '\'' +
                ", subjectId='" + subjectId + '\'' +
                ", question='" + question + '\'' +
                ", ans1='" + ans1 + '\'' +
                ", ans2='" + ans2 + '\'' +
                ", ans3='" + ans3 + '\'' +
                ", ans4='" + ans4 + '\'' +
                ", ans5='" + ans5 + '\'' +
                ", ans6='" + ans6 + '\'' +
                ", totalAns='" + totalAns + '\'' +
                ", rAns='" + rAns + '\'' +
                '}';
    }

    public Question(Parcel in) {
        quesId = in.readString();
        subjectId = in.readString();
        question = in.readString();
        ans1 = in.readString();
        ans2 = in.readString();
        ans3 = in.readString();
        ans4 = in.readString();
        ans5 = in.readString();
        ans6 = in.readString();
        totalAns = in.readString();
        rAns = in.readString();
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(quesId);
        parcel.writeString(subjectId);
        parcel.writeString(question);
        parcel.writeString(ans1);
        parcel.writeString(ans2);
        parcel.writeString(ans3);
        parcel.writeString(ans4);
        parcel.writeString(ans5);
        parcel.writeString(ans6);
        parcel.writeString(totalAns);
        parcel.writeString(rAns);
    }

    @Override
    public int describeContents() {
        return 0;
    }

}
