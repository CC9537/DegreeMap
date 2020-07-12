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
import com.chancellor.degreemap.models.Mentor;
import com.chancellor.degreemap.views.MentorActivity.MentorDetailsActivity;

import java.util.List;

public class MentorListAdapter extends RecyclerView.Adapter<MentorListAdapter.MentorViewHolder> {
    private final LayoutInflater layoutInflater;
    private final Context context;
    private List<Mentor> mentors;

    public MentorListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public MentorViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.content_recyclerview_item, parent, false);
        return new MentorListAdapter.MentorViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MentorViewHolder holder, int position) {
        if (mentors != null) {
            Mentor current = mentors.get(position);
            holder.mentorItemView.setText(current.getMentorName());
        } else
            holder.mentorItemView.setText("No Mentors associated with this term. (Create one)");
    }

    public void setMentors(List<Mentor> mentors) {
        this.mentors = mentors;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (mentors != null)
            return mentors.size();
        else
            return 0;
    }

    class MentorViewHolder extends RecyclerView.ViewHolder {
        private final TextView mentorItemView;

        public MentorViewHolder(@NonNull View itemView) {
            super(itemView);
            mentorItemView = itemView.findViewById(R.id.itemTextView);
            itemView.setOnClickListener((v) -> {
                int position = getAdapterPosition();
                final Mentor current = mentors.get(position);
                Intent intent = new Intent(context, MentorDetailsActivity.class);
                intent.putExtra("Mentor", current);
                context.startActivity(intent);
            });
        }
    }
}