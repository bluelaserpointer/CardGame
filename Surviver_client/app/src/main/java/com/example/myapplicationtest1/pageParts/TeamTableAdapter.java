package com.example.myapplicationtest1.pageParts;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.contents.unit.Knowledge;
import com.example.myapplicationtest1.page.CardStoragePage;
import com.example.myapplicationtest1.page.Page;
import com.example.myapplicationtest1.page.TeamPage;
import com.example.myapplicationtest1.utils.Cache;
import com.example.myapplicationtest1.utils.Utils;

public class TeamTableAdapter extends RecyclerView.Adapter<TeamTableAdapter.TeamTableLine> {
    private final Activity activity;

    public TeamTableAdapter(Activity activity) {
        this.activity = activity;
    }
    @NonNull
    @Override
    public TeamTableAdapter.TeamTableLine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeamTableAdapter.TeamTableLine(LayoutInflater.from(activity).inflate(R.layout.team_table_line, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamTableAdapter.TeamTableLine holder, int position) {
        //TODO: make it square
        //final int itemHeight = activity.findViewById(R.id.teamTableContent).getMinimumWidth()/this.getItemCount();
        for(int i = 0; i < 5; ++i) {
            ViewGroup.LayoutParams params = holder.itemView.getLayoutParams();
            params.height = 130;
            holder.itemView.setLayoutParams(params);
            final int cardSetPos = position * this.getItemCount() + i;
            if(TeamPage.formation.containsKey(cardSetPos)) { //set button bg to card icon
                System.out.println("TeamTableAdapter: found card at " + cardSetPos);
                final Cache.OwnCard ownCard = Cache.ownCards.get(TeamPage.formation.get(cardSetPos));
                if(ownCard != null)
                    holder.buttons[i].setBackground(ContextCompat.getDrawable(activity, ownCard.card.drawableId));
                else
                    System.out.println("TeamTableAdapter: Not found card param at " + TeamPage.formation.get(cardSetPos));
            } else {
                System.out.println("TeamTableAdapter: not found any card at " + cardSetPos);
            }
            final int finalI = i; //set button's touch event to jump to CardStoragePage
            holder.buttons[i].setOnTouchListener((v, motionEvent) -> {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    TeamPage.setOnEditPos(position*getItemCount() + finalI);
                    System.out.println("TeamTableAdapter: " + (position*getItemCount() + finalI));
                    Page.jump(activity, CardStoragePage.class);
                }
                return false;
            });
        }
    }

    @Override
    public int getItemCount() {
        return 5; //5*5 table
    }

    static class TeamTableLine extends RecyclerView.ViewHolder {
        final Button[] buttons = new Button[5];
        public TeamTableLine(@NonNull View itemView) {
            super(itemView);
            buttons[0] = itemView.findViewById(R.id.team_line_0);
            buttons[1] = itemView.findViewById(R.id.team_line_1);
            buttons[2] = itemView.findViewById(R.id.team_line_2);
            buttons[3] = itemView.findViewById(R.id.team_line_3);
            buttons[4] = itemView.findViewById(R.id.team_line_4);
        }
    }
}
