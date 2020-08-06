package com.example.task.requests;

import androidx.lifecycle.LiveData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface TaskApi {

    @GET("education_portal/index.php/api/get_test_list")
    Call<SubjectListResponse> getTestList(
            @Query("student_id") String studentId,
            @Query("course_id") String courseId
    );

    @GET("education_portal/index.php/api/get_question_by_subject")
    Call<QuestionListResponse> getQuestions(
            @Query("subject_id") String subjectId
    );
}
