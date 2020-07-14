package com.example.myapplicationtest1;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.CardViewHolder>{
    private Context mcontext;
    private ArrayList<Card> mCardList;

    public CardAdapter(Context context, ArrayList<Card> cardList){
        mcontext = context;
        mCardList = cardList;
    }

    @NonNull
    @Override
    public CardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mcontext).inflate(R.layout.selectcards,parent,false);
        return new CardViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull CardViewHolder holder, int position) {
    Card currentItem = mCardList.get(position);

    String imageUrl = currentItem.getImageUrl();
    String cardName = currentItem.getCardName();

    holder.mTextViewCardName.setText(cardName);
    Picasso.with(mcontext).load(imageUrl).fit().centerInside().into(holder.mImageView);

    }

    @Override
    public int getItemCount() {
        return mCardList.size();
    }

    public class CardViewHolder extends RecyclerView.ViewHolder{
        public ImageView mImageView;
        public TextView mTextViewCardName;
        public CardViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.selectCard_Image);
            mTextViewCardName = itemView.findViewById(R.id.selectCardName);
        }
    }
}
