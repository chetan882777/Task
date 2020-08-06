package com.example.task.requests;


import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.example.task.AppExecuters;
import com.example.task.model.Question;
import com.example.task.model.Subject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.example.task.util.Constants.NETWORK_TIMEOUT;

public class TaskApiClient {
    private static TaskApiClient instance;

    private MutableLiveData<List<Subject>> mSubjects;
    private MutableLiveData<List<Question>> mQuestions;

    private RetrieveSubjectsRunnable mRetrieveSubjectsRunnable;
    private RetrieveQuestionsRunnable mRetrieveQuestionsRunnable;
    private MutableLiveData<Boolean> mNetworkTimeOut;

    private static final String TAG = "RecipeApiClient";

    public static TaskApiClient getInstance() {
        if (instance == null) {
            instance = new TaskApiClient();
        }
        return instance;
    }

    public TaskApiClient() {
        mSubjects = new MutableLiveData<>();
        mQuestions = new MutableLiveData<>();
        mNetworkTimeOut = new MutableLiveData<>();
    }

    public MutableLiveData<List<Subject>> getSubjects() {
        return mSubjects;
    }

    public MutableLiveData<List<Question>> getQuestions() {
        return mQuestions;
    }

    public MutableLiveData<Boolean> isNetworkTimedOut(){
        return mNetworkTimeOut;
    }

    public void questionListApi(String subjectId) {
        Log.d(TAG, "subjectListApi: : fetch subject list");

        if (mRetrieveQuestionsRunnable != null) {
            mRetrieveQuestionsRunnable = null;
        }
        mNetworkTimeOut.postValue(false);
        mRetrieveQuestionsRunnable = new RetrieveQuestionsRunnable(subjectId);
        final Future handler = AppExecuters.getInstance().NetworkIO().submit(mRetrieveQuestionsRunnable);

        AppExecuters.getInstance().NetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: RUN");
                // let user know its timed out
                handler.cancel(true);
                mNetworkTimeOut.postValue(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    public void subjectListApi(String studentId, String courseId) {

        Log.d(TAG, "subjectListApi: : fetch subject list");

        if (mRetrieveSubjectsRunnable != null) {
            mRetrieveSubjectsRunnable = null;
        }
        mNetworkTimeOut.postValue(false);
        mRetrieveSubjectsRunnable = new RetrieveSubjectsRunnable(studentId, courseId);
        final Future handler = AppExecuters.getInstance().NetworkIO().submit(mRetrieveSubjectsRunnable);

        AppExecuters.getInstance().NetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "run: RUN");
                // let user know its timed out
                handler.cancel(true);
                mNetworkTimeOut.postValue(true);
            }
        }, NETWORK_TIMEOUT, TimeUnit.MILLISECONDS);
    }

    private class RetrieveSubjectsRunnable implements Runnable {
        private String query;
        private String courseId;
        private boolean cancelRequest;


        public RetrieveSubjectsRunnable(String query, String courseId) {
            this.query = query;
            this.courseId = courseId;
        }

        @Override
        public void run() {
            try {
                Response response = getSubjects(query, courseId).execute();
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    Log.d(TAG, "run: SUCCESS");
                    List<Subject> list = new ArrayList<>(((SubjectListResponse) response.body()).getSubjects());
                    Log.d(TAG, "run: " + list.size());
                        mSubjects.postValue(list);
                } else {
                    Log.d(TAG, "run: ERROR");
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mSubjects.postValue(null);
                }
            } catch (IOException e) {
                Log.d(TAG, "run: EXCEPTION");
                e.printStackTrace();
                mSubjects.postValue(null);
            }
        }


        private Call<SubjectListResponse> getSubjects(String studentId, String courseId) {
            return ServiceGenerator.getTaskApi().getTestList(
                    studentId,
                    courseId
            );
        }

        private void cancelRequest() {
            cancelRequest = true;
        }
    }

    private class RetrieveQuestionsRunnable implements Runnable {
        private String subjectId;
        private boolean cancelRequest;


        public RetrieveQuestionsRunnable(String subjectId) {
            this.subjectId = subjectId;
        }

        @Override
        public void run() {
            try {
                Response response = getQuestions(subjectId).execute();
                Log.d(TAG, "run: " + response.body());
                if (cancelRequest) {
                    return;
                }
                if (response.code() == 200) {
                    Log.d(TAG, "run: SUCCESS");
                    List<Question> list = new ArrayList<>(((QuestionListResponse) response.body()).getQuestions());
                    Log.d(TAG, "run: " + list.size());
                    mQuestions.postValue(list);
                } else {
                    Log.d(TAG, "run: ERROR");
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error);
                    mQuestions.postValue(null);
                }
            } catch (IOException e) {
                Log.d(TAG, "run: EXCEPTION");
                e.printStackTrace();
                mSubjects.postValue(null);
            }
        }


        private Call<QuestionListResponse> getQuestions(String subjectId) {
            return ServiceGenerator.getTaskApi().getQuestions(
                    subjectId
            );
        }

        private void cancelRequest() {
            cancelRequest = true;
        }
    }


    public void cancelRequest() {
        if (mRetrieveSubjectsRunnable != null) {
            mRetrieveSubjectsRunnable.cancelRequest();
        }else if(mRetrieveQuestionsRunnable != null){
            mRetrieveQuestionsRunnable.cancelRequest();
        }
    }
}
