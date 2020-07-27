package com.example.myapplicationtest1.pageParts;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.page.GamePage;
import com.example.myapplicationtest1.page.Page;
import com.example.myapplicationtest1.utils.Urls;

import org.json.JSONArray;
import org.json.JSONException;

public class ChapterListAdapter extends RecyclerView.Adapter <ChapterListAdapter.ChapterListViewHolder> {
    private final Context context;
    static class ChapterInfo {
        int phaseAmount;
        int phaseType;
        public ChapterInfo(int phaseAmount, int phaseType) {
            this.phaseAmount = phaseAmount;
            this.phaseType = phaseType;
        }
    }
    private ChapterInfo[] chapters;
    public static int selectedPhase;

    public ChapterListAdapter(Context context) {
        this.context = context;
        fetch();
    }
    public void fetch() {
        try {
            final JSONArray arr = new JSONArray(HttpClient.doGetShort(Urls.getAllChapters()));
            chapters = new ChapterInfo[arr.length()];
            for(int i = 0; i < chapters.length; ++i) {
                //TODO: waiting backend change its name to "phaseMaxNo"
                chapters[i] = new ChapterInfo(arr.getJSONObject(i).getInt("phaseNo"), arr.getJSONObject(i).getInt("phaseType"));
            }
        } catch (JSONException e) {
            e.printStackTrace();
            chapters = new ChapterInfo[0];
        }
    }
    private void setEnterPhaseTouchListener(View view, int phaseId) {
        view.setOnTouchListener((view2, motionEvent) -> {
            view2.performClick();
            if(motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                System.out.println("ChapterListAdapter: Joining phase " + phaseId);
                selectedPhase = phaseId;
                Page.jump(context, GamePage.class);
            }
            return false;
        });
    }
    @NonNull
    @Override
    public ChapterListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ChapterListViewHolder(LayoutInflater.from(context).inflate(R.layout.chapter_list_item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ChapterListViewHolder holder, int position) {
        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = 600;
        holder.itemView.setLayoutParams(layoutParams);
        //设置外貌
        holder.chapterNameTextView.setText("第" + (position + 1) + "章");
        //添加点击后切换地图事件
        holder.itemView.setOnClickListener(v -> {}); //???I don't know why, but it helps itself receive more kinds of motionEvent.
        holder.itemView.setOnTouchListener((view, motionEvent) -> {
            view.performClick();
            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
                System.out.println("ChapterListAdapter: clicked chapter is " + position);
                TableLayout layout;
                final FrameLayout mapContent = ((Activity)context).findViewById(R.id.mapContent);
                switch (position) { //chapter
                    case 0:
                        layout = (TableLayout)View.inflate(((Activity)context), R.layout.chapter_map_0, null);
                        mapContent.removeAllViews();
                        mapContent.addView(layout);
                        this.setEnterPhaseTouchListener(layout.findViewById(R.id.phase0), 0);
                        this.setEnterPhaseTouchListener(layout.findViewById(R.id.phase1), 1);
                        this.setEnterPhaseTouchListener(layout.findViewById(R.id.phase2), 2);
                        break;
                    case 1:
                        layout = (TableLayout)View.inflate(((Activity)context), R.layout.chapter_map_1, null);
                        mapContent.removeAllViews();
                        mapContent.addView(layout);
                        this.setEnterPhaseTouchListener(layout.findViewById(R.id.phase0), 0);
                        this.setEnterPhaseTouchListener(layout.findViewById(R.id.phase1), 1);
                        this.setEnterPhaseTouchListener(layout.findViewById(R.id.phase2), 2);
                        this.setEnterPhaseTouchListener(layout.findViewById(R.id.phase3), 3);
                        break;
                }
            }
            return false;
        });
    }

    @Override
    public int getItemCount() {
        return chapters.length;
    }

    static class ChapterListViewHolder extends RecyclerView.ViewHolder {
        final TextView chapterNameTextView;
        ChapterListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.chapterNameTextView = itemView.findViewById(R.id.chapterName);
        }
    }
}
