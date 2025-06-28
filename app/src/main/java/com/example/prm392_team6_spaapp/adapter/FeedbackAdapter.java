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
        Feedback feedback = feedbackList.get(position);
        
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
    }

    @Override
    public int getItemCount() {
        return feedbackList != null ? feedbackList.size() : 0;
    }

    public void updateFeedbackList(List<Feedback> newFeedbackList) {
        this.feedbackList = newFeedbackList;
        notifyDataSetChanged();
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