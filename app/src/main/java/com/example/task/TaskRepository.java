package com.example.task;

import android.util.Log;

import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Observer;

import com.example.task.model.Question;
import com.example.task.model.Subject;
import com.example.task.requests.TaskApiClient;

import java.util.List;

public class TaskRepository {

    private static final String TAG = "TaskRepository";

    private static TaskRepository instance;
    private TaskApiClient mTaskApiClient;
    private MediatorLiveData<List<Subject>> mSubjects = new MediatorLiveData<>();
    private MediatorLiveData<List<Question>> mQuestions = new MediatorLiveData<>();

    public static TaskRepository getInstance(){
        if(instance == null){
            instance = new TaskRepository();
        }
        return instance;
    }

    private TaskRepository(){
        mTaskApiClient = TaskApiClient.getInstance();
    }

    public void fetchSubjects(){
        LiveData<List<Subject>> subjectListApiSource = mTaskApiClient.getSubjects();
        mSubjects.addSource(subjectListApiSource, new Observer<List<Subject>>() {
            @Override
            public void onChanged(@Nullable List<Subject> subjects) {
                if(subjects != null){
                    mSubjects.setValue(subjects);
                }else{
                    Log.e(TAG, "onChanged: subjects empty " );
                }
            }
        });
    }

    public void fetchQuestions(){
        Log.d(TAG, "fetchQuestions: CALLED");
        LiveData<List<Question>> questionListApiSource = mTaskApiClient.getQuestions();
        mQuestions.addSource(questionListApiSource, new Observer<List<Question>>() {
            @Override
            public void onChanged(@Nullable List<Question> questions) {
                if(questions != null){
                    mQuestions.setValue(questions);
                }else{
                    Log.e(TAG, "onChanged: questions empty " );
                }
            }
        });
    }
    public void removeQuestionSource(){
        mQuestions.removeSource(mTaskApiClient.getQuestions());
    }

    public LiveData<List<Subject>> getSubjects(){return mSubjects;}

    public LiveData<List<Question>> getQuestions() {return mQuestions;}

    public LiveData<Boolean> isNetworkTimedOut(){return mTaskApiClient.isNetworkTimedOut();}

    public void subjectListApi(String studentId, String courseId){
        mTaskApiClient.subjectListApi(studentId , courseId);
    }

    public void questionListApi(String subjectId){
        mTaskApiClient.questionListApi(subjectId);
    }

    public void cancelRequest(){
        mTaskApiClient.cancelRequest();
    }
}

