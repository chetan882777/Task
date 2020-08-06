package com.example.task.requests;

import com.example.task.model.Question;
import com.example.task.model.Subject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class QuestionListResponse {

    @SerializedName("responce")
    @Expose()
    private String response;

    @SerializedName("data")
    @Expose()
    private List<Question> questions;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @Override
    public String toString() {
        return "SubjectList{" +
                "response='" + response + '\'' +
                ", subjects=" + questions +
                '}';
    }

}
