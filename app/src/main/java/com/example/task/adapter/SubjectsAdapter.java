package com.example.task.adapter;

import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.task.R;
import com.example.task.model.Subject;

import java.util.ArrayList;
import java.util.List;

public class SubjectsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Subject> mSubjects;
    private OnSubjectListener onSubjectListener;

    public SubjectsAdapter(OnSubjectListener onRecipeListener) {
        onSubjectListener = onRecipeListener;
        mSubjects = new ArrayList<>();
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.subject_list_item, viewGroup, false);
        return new SubjectViewHolder(view, onSubjectListener);

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

        ((SubjectViewHolder) viewHolder).subjectName.setText(mSubjects.get(i).getSubjectTitle());

        String subjectInfo = mSubjects.get(i).getTotalQues() + " Questions | " + mSubjects.get(i).getQuizTime();

        ((SubjectViewHolder) viewHolder).subjectInfo.setText(subjectInfo);
    }

    @Override
    public int getItemCount() {
        return mSubjects.size();
    }

    public void setSubjects(List<Subject> subjects) {
        mSubjects = subjects;
        notifyDataSetChanged();
    }


    public Subject getSelectedSubject(int position) {
        if (mSubjects != null) {
            if (mSubjects.size() > 0) {
                return mSubjects.get(position);
            }
        }
        return null;
    }
}
