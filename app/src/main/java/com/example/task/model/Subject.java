package com.example.task.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Subject implements Parcelable {

    @SerializedName("subject_id")
    @Expose()
    private String subjectId;

    @SerializedName("school_id")
    @Expose()
    private String schoolId;

    @SerializedName("subject_standard")
    @Expose()
    private String subjectStandard;

    @SerializedName("subject_title")
    @Expose()
    private String subjectTitle;

    @SerializedName("subject_total_ques")
    @Expose()
    private String subjectTotalQues;

    @SerializedName("quiz_time")
    @Expose()
    private String quizTime;

    @SerializedName("standard_title")
    @Expose()
    private String standardTitle;

    @SerializedName("quiz_total_right_ans")
    @Expose()
    private String quizTotalRightAns;

    @SerializedName("total_qes")
    @Expose()
    private String totalQues;

    public Subject(Parcel in) {
        subjectId = in.readString();
        schoolId = in.readString();
        subjectStandard = in.readString();
        subjectTitle = in.readString();
        subjectTotalQues = in.readString();
        quizTime = in.readString();
        standardTitle = in.readString();
        quizTotalRightAns = in.readString();
        totalQues = in.readString();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(subjectId);
        parcel.writeString(schoolId);
        parcel.writeString(subjectStandard);
        parcel.writeString(subjectTitle);
        parcel.writeString(subjectTotalQues);
        parcel.writeString(quizTime);
        parcel.writeString(standardTitle);
        parcel.writeString(quizTotalRightAns);
        parcel.writeString(totalQues);

    }
    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(String schoolId) {
        this.schoolId = schoolId;
    }

    public String getSubjectStandard() {
        return subjectStandard;
    }

    public void setSubjectStandard(String subjectStandard) {
        this.subjectStandard = subjectStandard;
    }

    public String getSubjectTitle() {
        return subjectTitle;
    }

    public void setSubjectTitle(String subjectTitle) {
        this.subjectTitle = subjectTitle;
    }

    public String getSubjectTotalQues() {
        return subjectTotalQues;
    }

    public void setSubjectTotalQues(String subjectTotalQues) {
        this.subjectTotalQues = subjectTotalQues;
    }

    public String getQuizTime() {
        return quizTime;
    }

    public void setQuizTime(String quizTime) {
        this.quizTime = quizTime;
    }

    public String getStandardTitle() {
        return standardTitle;
    }

    public void setStandardTitle(String standardTitle) {
        this.standardTitle = standardTitle;
    }

    public String getQuizTotalRightAns() {
        return quizTotalRightAns;
    }

    public void setQuizTotalRightAns(String quizTotalRightAns) {
        this.quizTotalRightAns = quizTotalRightAns;
    }

    public String getTotalQues() {
        return totalQues;
    }

    public void setTotalQues(String totalQues) {
        this.totalQues = totalQues;
    }

    @Override
    public String toString() {
        return "Subject{" +
                "subjectId='" + subjectId + '\'' +
                ", schoolId='" + schoolId + '\'' +
                ", subjectStandard='" + subjectStandard + '\'' +
                ", subjectTitle='" + subjectTitle + '\'' +
                ", subjectTotalQues='" + subjectTotalQues + '\'' +
                ", quizTime='" + quizTime + '\'' +
                ", standardTitle='" + standardTitle + '\'' +
                ", quizTotalRightAns='" + quizTotalRightAns + '\'' +
                ", totalQues='" + totalQues + '\'' +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Subject> CREATOR = new Creator<Subject>() {
        @Override
        public Subject createFromParcel(Parcel in) {
            return new Subject(in);
        }

        @Override
        public Subject[] newArray(int size) {
            return new Subject[size];
        }
    };

}
