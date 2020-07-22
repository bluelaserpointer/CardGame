package com.example.myapplicationtest1.pageParts;

import android.content.Context;
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
import com.example.myapplicationtest1.utils.Utils;

public class TeamTableAdapter extends RecyclerView.Adapter<TeamTableAdapter.TeamTableLine> {
    private final Context context;

    public TeamTableAdapter(Context context) {
        this.context = context;
    }
    @NonNull
    @Override
    public TeamTableAdapter.TeamTableLine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeamTableAdapter.TeamTableLine(LayoutInflater.from(context).inflate(R.layout.team_line, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamTableAdapter.TeamTableLine holder, int position) {
        for(int i = 0; i < 5; ++i) {
            final int cardSetPos = position * this.getItemCount() + i;
            if(TeamPage.formation.containsKey(cardSetPos)) { //set button bg to card icon
                System.out.println("TeamTableAdapter: found card at " + cardSetPos);
                final Knowledge.KnowledgeParameter param = Utils.getKnowledgeParameter(TeamPage.formation.get(cardSetPos));
                holder.buttons[i].setBackground(ContextCompat.getDrawable(context, param.drawableId));
            } else {
                System.out.println("TeamTableAdapter: not found any card at " + cardSetPos);
            }
            final int finalI = i; //set button's touch event to jump to CardStoragePage
            holder.buttons[i].setOnTouchListener((v, motionEvent) -> {
                if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                    TeamPage.setOnEditPos(position*getItemCount() + finalI);
                    System.out.println("TeamTableAdapter: " + (position*getItemCount() + finalI));
                    Page.jump(context, CardStoragePage.class);
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
