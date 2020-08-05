package com.example.myapplicationtest1.pageParts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.page.CardDetailPage;
import com.example.myapplicationtest1.page.Page;
import com.example.myapplicationtest1.page.TeamPage;
import com.example.myapplicationtest1.utils.Cache;

import java.util.Iterator;
import java.util.Map;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardListViewHolder> {
    private final Context context;
    private final Iterator<Map.Entry<Integer, Cache.OwnCard>> oCardEntryIterator = Cache.ownCards.entrySet().iterator();
    public CardListAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public CardListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardListViewHolder(LayoutInflater.from(context).inflate(R.layout.card_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardListViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = 500;
        holder.itemView.setLayoutParams(layoutParams);
        final Map.Entry<Integer, Cache.OwnCard> oCardEntry = this.oCardEntryIterator.next();
        final Cache.OwnCard params = oCardEntry.getValue();
        holder.cardImageView.setImageResource(R.drawable.greentmp);
        holder.cardNameTextView.setText(params.card.cardName);
        holder.cardRarityTextView.setText("SSR");
        holder.cardLevelTextView.setText("Lv: " + params.cardLevel + "/" + params.cardLevelLimit);
        holder.cardExpTextView.setText("exp: " + params.cardCurExp + "/" + 100);
        holder.itemView.setOnClickListener(v -> {}); //???I don't know why, but it helps itself receive more kinds of motionEvent.
        holder.itemView.setOnTouchListener((v, motionEvent) -> {
            v.performClick();
            System.out.println("CardListAdapter: action: " + motionEvent.getAction());
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                if(TeamPage.getOnEditPos() != -1) {
                    TeamPage.setFormationToOnEditPos(oCardEntry.getKey());
                    Page.jump(context, TeamPage.class);
                } else {
                    Page.jump(context, CardDetailPage.class);
                }
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return Cache.ownCards.size();
    }

    static class CardListViewHolder extends RecyclerView.ViewHolder {
        final ImageView cardImageView;
        final TextView cardNameTextView;
        final TextView cardRarityTextView;
        final TextView cardLevelTextView;
        final TextView cardExpTextView;
        public CardListViewHolder(@NonNull View itemView) {
            super(itemView);
            cardImageView = itemView.findViewById(R.id.cardIcon);
            cardNameTextView = itemView.findViewById(R.id.cardName);
            cardRarityTextView = itemView.findViewById(R.id.cardRarity);
            cardLevelTextView = itemView.findViewById(R.id.cardLevel);
            cardExpTextView = itemView.findViewById(R.id.cardExp);
        }
    }
}
