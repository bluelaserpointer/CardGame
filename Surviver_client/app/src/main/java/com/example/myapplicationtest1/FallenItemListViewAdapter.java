package com.example.myapplicationtest1;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class FallenItemListViewAdapter extends RecyclerView.Adapter<FallenItemListViewAdapter.ViewHolder>{

    private static final String TAG = "ExpListViewAdapter";

    private ArrayList<String> mTexts = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContext;

    public FallenItemListViewAdapter(Context mContext, ArrayList<String> mTexts, ArrayList<String> mImages) {
        this.mTexts = mTexts;
        this.mImages = mImages;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exp_list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called.");
        // Binds the data to individual listViewers
        // All the data, widgets

        Glide.with(mContext)
                .asBitmap()
                .load(mImages.get(position))
                .into(holder.image);

        holder.exp.setText(mTexts.get(position));

//        holder.image.setOnClickListener(new View.OnClickListener(){
//            @Override
//            public void onClick(View view){
//                Log.d(TAG, "onClick: clicked on an image: " + mTexts.get(position));
//                Toast.makeText(mContext, mTexts, Toast.LENGTH_SHORT).show();
//            }
//        });

    }

    @Override
    public int getItemCount() {
        return mImages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView exp;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.image);
            exp = itemView.findViewById(R.id.cardExpText);
        }
    }
}
