package com.example.myapplicationtest1.pageParts;

import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.page.AnnouncePage;
import com.example.myapplicationtest1.utils.Cache;

public class ActivityListAdapter extends RecyclerView.Adapter<ActivityListAdapter.ActivityListViewHolder> {
    private final AnnouncePage context;
    public static int selectedActivityId = -1;
    public ActivityListAdapter(AnnouncePage context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ActivityListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ActivityListAdapter.ActivityListViewHolder(LayoutInflater.from(context).inflate(R.layout.card_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ActivityListViewHolder holder, int position) {
        final Cache.Activity activity = Cache.activities.get(position + 1);
        holder.cardNameTextView.setText(activity.title);
        holder.cardRarityTextView.setText(activity.time);
        holder.cardExpTextView.setText(activity.type);
        holder.itemView.setOnClickListener(v -> {}); //???I don't know why, but it helps itself receive more kinds of motionEvent.
        holder.itemView.setOnTouchListener((v, motionEvent) -> {
            v.performClick();
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                selectedActivityId = position + 1;
                context.setActivityInfo(selectedActivityId);
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return Cache.activities.size();
    }

    static class ActivityListViewHolder extends RecyclerView.ViewHolder {
        final ImageView cardImageView;
        final TextView cardNameTextView;
        final TextView cardRarityTextView;
        final TextView cardLevelTextView;
        final TextView cardExpTextView;
        public ActivityListViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImageView = itemView.findViewById(R.id.cardIcon);
            cardNameTextView = itemView.findViewById(R.id.cardName);
            cardRarityTextView = itemView.findViewById(R.id.cardRarity);
            cardLevelTextView = itemView.findViewById(R.id.cardLevel);
            cardExpTextView = itemView.findViewById(R.id.cardExp);
        }
    }
}
