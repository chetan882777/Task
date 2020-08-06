package com.example.task.ui;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.task.R;
import com.example.task.adapter.OnSubjectListener;
import com.example.task.adapter.SubjectsAdapter;
import com.example.task.model.Subject;
import com.example.task.viewModel.SubjectListViewModel;

import java.util.List;

public class SubjectListActivity extends AppCompatActivity implements OnSubjectListener {

    private static final String TAG = "SubjectListActivity";

    private SubjectListViewModel mSubjectListViewModel;
    private RecyclerView mSubjectRecyclerView;
    private SubjectsAdapter mAdapter;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSubjectRecyclerView = findViewById(R.id.recycler_view_subjects);
        progressBar = findViewById(R.id.progressBar);
        progressBar.setVisibility(View.VISIBLE);

        mSubjectListViewModel = ViewModelProviders.of(this).get(SubjectListViewModel.class);
        initRecyclerView();
        subscribeObserver();
        initSearch();
    }

    private void initRecyclerView() {
        mAdapter = new SubjectsAdapter(this);
        mSubjectRecyclerView.setAdapter(mAdapter);
        mSubjectRecyclerView.setLayoutManager(new LinearLayoutManager(this));

    }

    private void subscribeObserver() {
        mSubjectListViewModel.getSubject().observe(this, new Observer<List<Subject>>() {
            @Override
            public void onChanged(@Nullable List<Subject> subjects) {
                if (subjects != null) {
                    progressBar.setVisibility(View.GONE);
                    Log.d(TAG, "onChanged: " + subjects.size());
                    mSubjectListViewModel.setIsPerformingQuery(false);
                    mAdapter.setSubjects(subjects);
                }
            }
        });

        mSubjectListViewModel.isNetworkTimedOut().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    Toast.makeText(getApplicationContext(), "Network Time out", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void initSearch() {
        mSubjectListViewModel.subjectListApi("0", "64");
    }

    @Override
    public void onSubjectClick(int position) {
        Log.d(TAG, "onSubjectClick: position : " + position);
        Subject selectedSubject = mAdapter.getSelectedSubject(position);
        Intent intent = new Intent(this, InstructionsActivity.class);
        intent.putExtra("subject", selectedSubject);
        startActivity(intent);
    }
}