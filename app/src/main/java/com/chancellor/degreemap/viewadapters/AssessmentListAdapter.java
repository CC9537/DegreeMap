package com.chancellor.degreemap.viewadapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.models.Assessment;
import com.chancellor.degreemap.views.AssessmentActivity.AssessmentDetailsActivity;

import java.util.List;

public class AssessmentListAdapter extends RecyclerView.Adapter<AssessmentListAdapter.AssessmentViewHolder> {
    private final LayoutInflater layoutInflater;
    private final Context context;
    private List<Assessment> assessments;

    public AssessmentListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public AssessmentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.content_recyclerview_item, parent, false);
        return new AssessmentListAdapter.AssessmentViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull AssessmentViewHolder holder, int position) {
        if (assessments != null) {
            Assessment current = assessments.get(position);
            holder.assessmentItemView.setText(current.getAssessmentName());
        } else
            holder.assessmentItemView.setText("No Assessments associated with this term. (Create one)");
    }

    public void setAssessments(List<Assessment> assessments) {
        this.assessments = assessments;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (assessments != null)
            return assessments.size();
        else
            return 0;
    }

    class AssessmentViewHolder extends RecyclerView.ViewHolder {
        private final TextView assessmentItemView;

        public AssessmentViewHolder(@NonNull View itemView) {
            super(itemView);
            assessmentItemView = itemView.findViewById(R.id.itemTextView);
            itemView.setOnClickListener((v) -> {
                int position = getAdapterPosition();
                final Assessment current = assessments.get(position);
                Intent intent = new Intent(context, AssessmentDetailsActivity.class);
                intent.putExtra("Assessment", current);
                context.startActivity(intent);
            });
        }
    }
}