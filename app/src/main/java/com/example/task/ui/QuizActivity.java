package com.example.task.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.task.R;
import com.example.task.model.Question;
import com.example.task.model.Subject;
import com.example.task.viewModel.QuizViewModel;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SubjectListActivity";

    private QuizViewModel mQuizViewModel;
    private Subject mSubject;
    private List<Question> mQuestions;
    private int currentQuestionNUmber = -1;
    private TextView attemptedQuestion, quizTime, question;
    private Button submitButton, nextButton, prevButton, optionA, optionB, optionC, optionD, optionE, optionF;
    private boolean selectOptionCalled = false;
    private int attempted = 0;
    private boolean submitted = false;
    private ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        mSubject = (Subject) getIntent().getParcelableExtra("subject");

        attemptedQuestion = findViewById(R.id.textView_quiz_attempted);
        quizTime = findViewById(R.id.textView_quiz_timeLeft);
        question = findViewById(R.id.textView_quiz_question);

        submitButton = findViewById(R.id.button_quiz_submit);
        prevButton = findViewById(R.id.button_quiz_next);
        nextButton = findViewById(R.id.button_quiz_prev);
        optionA = findViewById(R.id.button_option_1);
        optionB = findViewById(R.id.button_option_2);
        optionC = findViewById(R.id.button_option_3);
        optionD = findViewById(R.id.button_option_4);
        optionE = findViewById(R.id.button_option_5);
        optionF = findViewById(R.id.button_option_6);

        optionA.setVisibility(View.GONE);
        optionB.setVisibility(View.GONE);
        optionC.setVisibility(View.GONE);
        optionD.setVisibility(View.GONE);
        optionE.setVisibility(View.GONE);
        optionF.setVisibility(View.GONE);
        question.setVisibility(View.GONE);

        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        mQuizViewModel = ViewModelProviders.of(this).get(QuizViewModel.class);
        subscribeObserver();
        initSearch();

        optionA.setOnClickListener(this);
        optionB.setOnClickListener(this);
        optionC.setOnClickListener(this);
        optionD.setOnClickListener(this);
        optionE.setOnClickListener(this);
        optionF.setOnClickListener(this);
        submitButton.setOnClickListener(this);
        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
    }

    private void subscribeObserver() {
        mQuizViewModel.getQuestions().observe(this, new Observer<List<Question>>() {
            @Override
            public void onChanged(@Nullable List<Question> questions) {
                if (questions != null) {
                    progressBar.setVisibility(View.GONE);
                    Log.d(TAG, "onChanged: " + questions.size());
                    mQuizViewModel.setIsPerformingQuery(false);
                    mQuestions = questions;
                    setQuestion(0);
                    startTimer();

                }
            }
        });


        mQuizViewModel.isNetworkTimedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(getApplicationContext(), "Network Time out", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void startTimer() {
        final String[] time = mSubject.getQuizTime().split(":");
        Runnable r = new Runnable() {
            @Override
            public void run() {
                quizTime.setText("Time Left " + time[0] + ":" + time[1]);
                int newSec = Integer.parseInt(time[1]) - 1;
                int min = Integer.parseInt(time[0]);
                if(newSec == -1 ){
                    newSec = 59;
                    min = min -1;
                    if(min == -1){
                        timeUp();
                        return;
                    }
                }
                time[0] = String.valueOf(min);
                time[1] = String.valueOf(newSec);
                Handler handler = new Handler();
                if(!submitted) {
                    handler.postDelayed(this, 1000);
                }
            }
        };
        r.run();
    }

    private void timeUp() {
        Log.d(TAG, "timeUp: CALLED");
        submitTest();
    }


    private void setQuestion(int questionNumber) {
        Log.d(TAG, "setQuestion: " + currentQuestionNUmber);
        currentQuestionNUmber = questionNumber;

        if(currentQuestionNUmber >= mQuestions.size()){
            currentQuestionNUmber = 0;
            questionNumber = currentQuestionNUmber;
        }else if(currentQuestionNUmber < 0){
            currentQuestionNUmber = mQuestions.size() - 1;
            questionNumber = currentQuestionNUmber;
        }
        optionA.setVisibility(View.VISIBLE);
        optionB.setVisibility(View.VISIBLE);
        optionC.setVisibility(View.VISIBLE);
        optionD.setVisibility(View.VISIBLE);
        optionE.setVisibility(View.VISIBLE);
        optionF.setVisibility(View.VISIBLE);
        question.setVisibility(View.VISIBLE);

        selectOptionCalled = false;

        question.setText("Q" + (currentQuestionNUmber + 1) + " :  " + mQuestions.get(questionNumber).getQuestion());
        if(!mQuestions.get(questionNumber).getAns1().isEmpty()) {
            optionA.setText(mQuestions.get(questionNumber).getAns1());
        }else{
            optionA.setVisibility(View.GONE);
        }
        if(!mQuestions.get(questionNumber).getAns2().isEmpty()) {
            optionB.setText(mQuestions.get(questionNumber).getAns2());
        }else{
            optionB.setVisibility(View.GONE);
        }
        if(!mQuestions.get(questionNumber).getAns3().isEmpty()) {
            optionC.setText(mQuestions.get(questionNumber).getAns3());
        }else{
            optionC.setVisibility(View.GONE);
        }
        if(!mQuestions.get(questionNumber).getAns4().isEmpty()) {
            optionD.setText(mQuestions.get(questionNumber).getAns4());
        }else{
            optionD.setVisibility(View.GONE);
        }
        if(!mQuestions.get(questionNumber).getAns5().isEmpty()) {
            optionE.setText(mQuestions.get(questionNumber).getAns5());
        }else{
            optionE.setVisibility(View.GONE);
        }
        if(!mQuestions.get(questionNumber).getAns6().isEmpty()) {
            optionF.setText(mQuestions.get(questionNumber).getAns6());
        }else{
            optionF.setVisibility(View.GONE);
        }

        optionB.setText(mQuestions.get(questionNumber).getAns2());
        optionC.setText(mQuestions.get(questionNumber).getAns3());
        optionD.setText(mQuestions.get(questionNumber).getAns4());
        optionE.setText(mQuestions.get(questionNumber).getAns5());
        optionF.setText(mQuestions.get(questionNumber).getAns6());

        optionA.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        optionB.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        optionC.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        optionD.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        optionE.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        optionF.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));

        String totalAns = mQuestions.get(questionNumber).getTotalAns();
        if(!totalAns.isEmpty()){
            String[] split = totalAns.split(",");
            for(String s: split){
                if(!s.isEmpty()){
                    int i = Integer.parseInt(s);
                    switch (i){
                        case 1:optionA.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark)); break;
                        case 2:optionB.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));break;
                        case 3:optionC.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));break;
                        case 4:optionD.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));break;
                        case 5:optionE.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));break;
                        case 6:optionF.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));break;
                    }
                }
            }
        }
    }

    private void initSearch() {
        mQuizViewModel.questionListApi(mSubject.getSubjectId());
    }

    @Override
    public void onClick(View view) {
        if(mQuestions != null && mQuestions.size() > 0){
            switch (view.getId()){
                case R.id.button_quiz_submit: {
                    if(attempted < mQuestions.size()){
                        attempted = attempted + 1;
                    }
                    submitTest();
                    break;
                }
                case R.id.button_quiz_next: {
                    nextQuestion();
                    break;
                }
                case R.id.button_quiz_prev: {
                    prevQuestion();
                    break;
                }
                case R.id.button_option_1: {optionA.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    selectedOption(1);
                    break;
                }
                case R.id.button_option_2: {optionB.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    selectedOption(2);
                    break;
                }
                case R.id.button_option_3: {optionC.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    selectedOption(3);
                    break;
                }
                case R.id.button_option_4: {optionD.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    selectedOption(4);
                    break;
                }
                case R.id.button_option_5: {optionE.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    selectedOption(5);
                    break;
                }
                case R.id.button_option_6: {optionF.setBackgroundColor(getResources().getColor(R.color.colorPrimaryDark));
                    selectedOption(6);
                    break;
                }
            }
        }

    }

    private void selectedOption(int i) {
        selectOptionCalled = true;
        Log.d(TAG, "selectedOption: CALLED");
        Question question = mQuestions.get(currentQuestionNUmber);
        if(question.getTotalAns().contains("" + i)){
            String s = "";
            String[] split = question.getTotalAns().split(",");
            for(String t: split){
                Log.d(TAG, "selectedOption: " + t + " - " + i);
                if(!(Integer.parseInt(t) == i) && !t.isEmpty()){
                    Log.d(TAG, "selectedOption: in if condition");
                    s = s + t + ",";
                }
            }
            question.setTotalAns(s);
            switch (i){
                case 1:optionA.setBackgroundColor(getResources().getColor(android.R.color.darker_gray)); break;
                case 2:optionB.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));break;
                case 3:optionC.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));break;
                case 4:optionD.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));break;
                case 5:optionE.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));break;
                case 6:optionF.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));break;
            }
            return;
        }
        question.setTotalAns(question.getTotalAns() + i + ",");
    }

    private void prevQuestion() {
        if(selectOptionCalled && attempted <= mQuestions.size()){
            attempted = attempted + 1;
            attemptedQuestion.setText("Attempted: " + attempted + "/" + mQuestions.size());
        }
        setQuestion(--currentQuestionNUmber);
    }

    private void nextQuestion() {
        if(selectOptionCalled && attempted <= mQuestions.size()){
            attempted = attempted + 1;
            attemptedQuestion.setText("Attempted: " + attempted + "/" + mQuestions.size());
        }
        setQuestion(++currentQuestionNUmber);
    }

    private void submitTest() {
        submitted = true;

        mQuizViewModel.removeSources();

        Intent intent = new Intent(this, ResultActivity.class);
        intent.putParcelableArrayListExtra("questions", (ArrayList<? extends Parcelable>) mQuestions);
        intent.putExtra("attempted", attempted);

        startActivity(intent);
        finish();
    }
}