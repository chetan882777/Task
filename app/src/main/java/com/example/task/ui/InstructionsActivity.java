package com.example.task.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.task.R;
import com.example.task.model.Subject;

public class InstructionsActivity extends AppCompatActivity {

    private static final String TAG = "InstructionsActivity";

    private Subject mSubject;

    private Button startTest;
    private TextView subjectName, testTime, share;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);

        mSubject = (Subject) getIntent().getParcelableExtra("subject");

        startTest = findViewById(R.id.button_intruction_startTest);
        startTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(InstructionsActivity.this, QuizActivity.class);
                intent.putExtra("subject", mSubject);
                startActivity(intent);
            }
        });

        subjectName = findViewById(R.id.textView_intruction_title);
        testTime = findViewById(R.id.textView_intruction_time);
        share = findViewById(R.id.textView_instruction_share);

        subjectName.setText(mSubject.getSubjectTitle());
        testTime.setText(mSubject.getQuizTime());

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Share");
            }
        });
    }
}