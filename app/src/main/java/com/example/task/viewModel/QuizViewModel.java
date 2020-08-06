package com.example.task.viewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.task.TaskRepository;
import com.example.task.model.Question;
import com.example.task.model.Subject;

import java.util.List;

public class QuizViewModel extends ViewModel {
    private TaskRepository mTaskRepository;
    private boolean mIsPerformingQuery;

    public QuizViewModel() {
        mIsPerformingQuery = false;
        mTaskRepository = TaskRepository.getInstance();
        mTaskRepository.fetchQuestions();
    }

    public LiveData<List<Question>> getQuestions() {
        return mTaskRepository.getQuestions();
    }

    public void questionListApi(String subjectId) {
        mIsPerformingQuery = true;
        mTaskRepository.questionListApi(subjectId);
    }

    public void removeSources() {
        mTaskRepository.removeQuestionSource();
    }

    public LiveData<Boolean> isNetworkTimedOut() {
        return mTaskRepository.isNetworkTimedOut();
    }


    public boolean isPerformingQuery() {
        return mIsPerformingQuery;
    }

    public void setIsPerformingQuery(boolean isPerformingQuery) {
        mIsPerformingQuery = isPerformingQuery;
    }
}

