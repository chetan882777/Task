package com.example.task.viewModel;


import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.task.TaskRepository;
import com.example.task.model.Question;
import com.example.task.model.Subject;

import java.util.List;

public class SubjectListViewModel extends ViewModel {
    private TaskRepository mTaskRepository;
    private boolean mIsPerformingQuery;

    public SubjectListViewModel() {
        mIsPerformingQuery = false;
        mTaskRepository = TaskRepository.getInstance();
        mTaskRepository.fetchSubjects();
    }


    public LiveData<List<Subject>> getSubject() {
        return mTaskRepository.getSubjects();
    }

    public void subjectListApi(String studentId, String courseId) {
        mIsPerformingQuery = true;
        mTaskRepository.subjectListApi(studentId, courseId);
    }
    public LiveData<Boolean> isNetworkTimedOut(){return mTaskRepository.isNetworkTimedOut();}

    public boolean isPerformingQuery() {
        return mIsPerformingQuery;
    }

    public void setIsPerformingQuery(boolean isPerformingQuery) {
        mIsPerformingQuery = isPerformingQuery;
    }
}
