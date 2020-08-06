package com.example.task.requests;

import com.example.task.model.Subject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SubjectListResponse {

    @SerializedName("responce")
    @Expose()
    private String response;

    @SerializedName("data")
    @Expose()
    private List<Subject> subjects;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Subject> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<Subject> subjects) {
        this.subjects = subjects;
    }

    @Override
    public String toString() {
        return "SubjectList{" +
                "response='" + response + '\'' +
                ", subjects=" + subjects +
                '}';
    }
}
