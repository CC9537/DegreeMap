package com.chancellor.degreemap.viewadapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.chancellor.degreemap.R;
import com.chancellor.degreemap.models.Course;

import java.util.List;

public class CourseListAdapter extends RecyclerView.Adapter<CourseListAdapter.CourseViewHolder> {
    private final LayoutInflater layoutInflater;
    private final Context context;
    private List<Course> courses;

    public CourseListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public CourseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.content_recyclerview_item, parent, false);
        return new CourseViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CourseViewHolder holder, int position) {
        if (courses != null) {
            Course current = courses.get(position);
            holder.courseItemView.setText(current.getCourseName());
        } else
            holder.courseItemView.setText("No Courses associated with this term. (Create one)");
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (courses != null)
            return courses.size();
        else
            return 0;
    }

    class CourseViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseItemView;

        public CourseViewHolder(@NonNull View itemView) {
            super(itemView);
            courseItemView = itemView.findViewById(R.id.itemTextView);
//            itemView.setOnClickListener((v) -> {
//                int position = getAdapterPosition();
//                final Course current = courses.get(position);
//                Intent intent = new Intent(context, TermDetailsActivity.class);
//                intent.putExtra("termID", current.getTermId());
//                intent.putExtra("termName", current.getTermName());
//                intent.putExtra("termStart", current.getTermStart().toString());
//                intent.putExtra("termEnd", current.getTermEnd().toString());
//                context.startActivity(intent);
//            });
        }
    }
}
