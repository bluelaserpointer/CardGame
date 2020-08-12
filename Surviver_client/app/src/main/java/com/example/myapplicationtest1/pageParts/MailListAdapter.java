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
import com.example.myapplicationtest1.page.MailPage;
import com.example.myapplicationtest1.utils.Cache;

public class MailListAdapter extends RecyclerView.Adapter<MailListAdapter.MailListViewHolder> {
    private final MailPage context;
    public static int selectedMailId = -1;
    public MailListAdapter(MailPage context) {
        this.context = context;
    }

    @NonNull
    @Override
    public MailListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MailListAdapter.MailListViewHolder(LayoutInflater.from(context).inflate(R.layout.card_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull MailListViewHolder holder, int position) {
        final Cache.Mail mail = Cache.mails.get(position + 1);
        holder.cardNameTextView.setText(mail.title);
        holder.cardRarityTextView.setText(mail.time);
        holder.cardExpTextView.setText("");
        holder.itemView.setOnClickListener(v -> {}); //???I don't know why, but it helps itself receive more kinds of motionEvent.
        holder.itemView.setOnTouchListener((v, motionEvent) -> {
            v.performClick();
            if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                selectedMailId = position + 1;
                context.setMailInfo(selectedMailId);
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return Cache.mails.size();
    }

    static class MailListViewHolder extends RecyclerView.ViewHolder {
        final ImageView cardImageView;
        final TextView cardNameTextView;
        final TextView cardRarityTextView;
        final TextView cardLevelTextView;
        final TextView cardExpTextView;
        public MailListViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImageView = itemView.findViewById(R.id.cardIcon);
            cardNameTextView = itemView.findViewById(R.id.cardName);
            cardRarityTextView = itemView.findViewById(R.id.cardRarity);
            cardLevelTextView = itemView.findViewById(R.id.cardLevel);
            cardExpTextView = itemView.findViewById(R.id.cardExp);
        }
    }
}

