package com.example.prm392_team6_spaapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.prm392_team6_spaapp.R;
import com.example.prm392_team6_spaapp.model.Feedback;

import java.util.List;

public class FeedbackAdapter extends RecyclerView.Adapter<FeedbackAdapter.FeedbackViewHolder> {
    
    private Context context;
    private List<Feedback> feedbackList;
    private int[] avatars = {R.drawable.avatar_1, R.drawable.avatar_2, R.drawable.avatar_3, R.drawable.avatar_4};

    public FeedbackAdapter(Context context, List<Feedback> feedbackList) {
        this.context = context;
        this.feedbackList = feedbackList;
    }

    @NonNull
    @Override
    public FeedbackViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_comment, parent, false);
        return new FeedbackViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FeedbackViewHolder holder, int position) {
        try {
            Feedback feedback = feedbackList.get(position);
            android.util.Log.d("FeedbackAdapter", "Binding feedback at position " + position + ": " + feedback.getUsername());
            
            // Set tên người dùng
            holder.customerNameTextView.setText(feedback.getUsername());
            
            // Set ngày
            holder.dateTextView.setText(feedback.getDateCreated());
            
            // Set rating
            holder.ratingBarDisplay.setRating(feedback.getRating());
            
            // Set comment
            holder.commentTextView.setText(feedback.getComment());
            
            // Set avatar ngẫu nhiên
            int avatarIndex = Math.abs(feedback.getUsername().hashCode()) % avatars.length;
            holder.avatarImageView.setImageResource(avatars[avatarIndex]);
            
            android.util.Log.d("FeedbackAdapter", "Successfully bound feedback: " + feedback.getUsername());
        } catch (Exception e) {
            android.util.Log.e("FeedbackAdapter", "Error binding feedback at position " + position + ": " + e.getMessage(), e);
        }
    }

    @Override
    public int getItemCount() {
        int count = feedbackList != null ? feedbackList.size() : 0;
        android.util.Log.d("FeedbackAdapter", "Item count: " + count);
        return count;
    }

    public void updateFeedbackList(List<Feedback> newFeedbackList) {
        try {
            this.feedbackList = newFeedbackList;
            android.util.Log.d("FeedbackAdapter", "Updating feedback list with " + (newFeedbackList != null ? newFeedbackList.size() : 0) + " items");
            notifyDataSetChanged();
        } catch (Exception e) {
            android.util.Log.e("FeedbackAdapter", "Error updating feedback list: " + e.getMessage(), e);
        }
    }

    public static class FeedbackViewHolder extends RecyclerView.ViewHolder {
        TextView customerNameTextView;
        TextView dateTextView;
        TextView commentTextView;
        RatingBar ratingBarDisplay;
        ImageView avatarImageView;

        public FeedbackViewHolder(@NonNull View itemView) {
            super(itemView);
            customerNameTextView = itemView.findViewById(R.id.customerNameTextView);
            dateTextView = itemView.findViewById(R.id.dateTextView);
            commentTextView = itemView.findViewById(R.id.commentTextView);
            ratingBarDisplay = itemView.findViewById(R.id.ratingBarDisplay);
            avatarImageView = itemView.findViewById(R.id.avatarImageView);
        }
    }
} 