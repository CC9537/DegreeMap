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
import com.chancellor.degreemap.models.Term;
import com.chancellor.degreemap.views.TermActivity.TermDetailsActivity;

import java.util.List;

public class TermListAdapter extends RecyclerView.Adapter<TermListAdapter.TermViewHolder> {

    private final LayoutInflater layoutInflater;
    private final Context context;
    private List<Term> terms;

    public TermListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @Override
    public TermViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = layoutInflater.inflate(R.layout.content_terms_item, parent, false);
        return new TermViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TermViewHolder holder, int position) {
        if (terms != null) {
            Term current = terms.get(position);
            holder.termItemView.setText(current.getTermName());
        } else
            holder.termItemView.setText("No Terms In Database. (Create One)");
    }

    public void setTerms(List<Term> terms) {
        this.terms = terms;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        if (terms != null)
            return terms.size();
        else
            return 0;
    }

    class TermViewHolder extends RecyclerView.ViewHolder {
        private final TextView termItemView;

        public TermViewHolder(@NonNull View itemView) {
            super(itemView);
            termItemView = itemView.findViewById(R.id.itemTextView);
            itemView.setOnClickListener((v) -> {
                int position = getAdapterPosition();
                final Term current = terms.get(position);
                Intent intent = new Intent(context, TermDetailsActivity.class);
                intent.putExtra("termID", current.getTermId());
                intent.putExtra("termName", current.getTermName());
                intent.putExtra("termStart", current.getTermStart().toString());
                intent.putExtra("termEnd", current.getTermEnd().toString());
                context.startActivity(intent);
            });
        }
    }

}
