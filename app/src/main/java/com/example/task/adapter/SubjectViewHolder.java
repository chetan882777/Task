package com.example.task.adapter;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.task.R;

public class SubjectViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    TextView subjectName, subjectInfo;
    OnSubjectListener mOnSubjectListener;

    public SubjectViewHolder(View view, OnSubjectListener onSubjectListener) {
        super(view);
        mOnSubjectListener = onSubjectListener;
        subjectInfo = view.findViewById(R.id.textView_list_subjectInfo);
        subjectName = view.findViewById(R.id.textView_list_subjectName);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        mOnSubjectListener.onSubjectClick(getAdapterPosition());
    }
}
