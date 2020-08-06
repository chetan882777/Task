package com.example.task.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.widget.TextView;

import com.example.task.R;
import com.example.task.model.Question;

import java.util.Arrays;
import java.util.List;

public class ResultActivity extends AppCompatActivity {

    private static final String TAG = "ResultActivity";

    private List<Question> mQuestions;
    private TextView attempted, unattempted, correct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        int attemptedV = intent.getIntExtra("attempted", 0);
        mQuestions = intent.getParcelableArrayListExtra("questions");

        attempted = findViewById(R.id.textView_attempted);
        unattempted = findViewById(R.id.textView_unattempted);
        correct = findViewById(R.id.textView_correct);

        attempted.setText("" + attemptedV);
        unattempted.setText("" + (mQuestions.size() - attemptedV));


        int correct = 0;
        for (Question question : mQuestions) {
            Log.d(TAG, "onCreate: --- " + question.getrAns());
            Log.d(TAG, "onCreate: --- " + question.getTotalAns());
            String[] ans = question.getTotalAns().split(",");
            String[] rAns = question.getrAns().split(",");
            List<String> ansL = Arrays.asList(ans);
            List<String> rAnsL = Arrays.asList(rAns);

            boolean isCorrect = true;
            if (ansL.size() > 0) {
                for (String s : ansL) {
                    if (rAnsL.contains(s)) {
                        continue;
                    } else {
                        isCorrect = false;
                        break;
                    }
                }
            }
            if(isCorrect){
                correct = correct + 1;
            }
        }
        this.correct.setText("" + correct);

    }
}