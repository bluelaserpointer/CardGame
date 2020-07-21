package com.example.myapplicationtest1.pageParts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.contents.unit.Knowledge;
import com.example.myapplicationtest1.game.contents.unit.MyUnit;
import com.example.myapplicationtest1.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class CardListAdapter extends RecyclerView.Adapter<CardListAdapter.CardListViewHolder> {
    private final Context context;
    private final LinkedList<Knowledge.KnowledgeParameter> knowledgeParameters = new LinkedList<>();

    public CardListAdapter(Context context) {
        this.context = context;
        fetch();
    }

    public void fetch() {
        knowledgeParameters.clear();
        System.out.println("!!!fetch " + "ownCard/getAllOwnCardsByUserId?userId=" + Utils.getUserId());
        if(Utils.getUserName().equals("NOT_LOGGED")) {
            System.out.println("!!!CardListAdapter: not login");
            return;
        }
        try {
            final JSONArray arr = new JSONArray(HttpClient.doGetShort("ownCard/getAllOwnCardsByUserId?userId=" + Utils.getUserId()));
            for(int i = 0; i < arr.length(); ++i) {
                final JSONObject object = arr.getJSONObject(i);
                final int cardId = object.getInt("cardId");
                knowledgeParameters.add(MyUnit.loadAsKnowledge("card/getCard?cardId=" + cardId));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    @NonNull
    @Override
    public CardListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new CardListViewHolder(LayoutInflater.from(context).inflate(R.layout.card_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull CardListViewHolder holder, int position) {
        final Knowledge.KnowledgeParameter params = knowledgeParameters.get(position);
        holder.cardImageView.setImageResource(R.drawable.greentmp);
        holder.cardNameTextView.setText(params.NAME);
        holder.cardRarityTextView.setText("SSR");
        holder.cardLevelTextView.setText("Lv: " + 12 + "/" + 50);
        holder.cardExpTextView.setText("exp: " + 12 + "/" + 100);
    }

    @Override
    public int getItemCount() {
        return knowledgeParameters.size();
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
