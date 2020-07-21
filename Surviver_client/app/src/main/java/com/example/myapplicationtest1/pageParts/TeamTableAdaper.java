package com.example.myapplicationtest1.pageParts;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.contents.unit.Knowledge;
import com.example.myapplicationtest1.game.contents.unit.MyUnit;
import com.example.myapplicationtest1.page.Page;
import com.example.myapplicationtest1.page.TeamPage;
import com.example.myapplicationtest1.utils.Utils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;

public class TeamTableAdaper extends RecyclerView.Adapter<TeamTableAdaper.TeamTableLine> {
    private final Context context;

    public TeamTableAdaper(Context context) {
        this.context = context;
        fetch();
    }

    public void fetch() {
//        if(Utils.getUserName().equals("NOT_LOGGED")) {
//            System.out.println("!!!CardListAdapter: not login");
//            return;
//        }
//        try {
//            final JSONArray arr = new JSONArray(HttpClient.doGetShort("ownCard/getAllOwnCardsByUserId?userId=" + Utils.getUserId()));
//            for(int i = 0; i < arr.length(); ++i) {
//                final JSONObject object = arr.getJSONObject(i);
//                final int cardId = object.getInt("cardId");
//                knowledgeParameters.add(MyUnit.loadAsKnowledge(object.getInt("ownCardId"), "card/getCard?cardId=" + cardId));
//            }
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
    }
    @NonNull
    @Override
    public TeamTableAdaper.TeamTableLine onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeamTableAdaper.TeamTableLine(LayoutInflater.from(context).inflate(R.layout.team_line, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TeamTableAdaper.TeamTableLine holder, int position) {
        for(int i = 0; i < 5; ++i) {
            final int cardSetPos = position*this.getItemCount() + i;
            Knowledge.KnowledgeParameter param = Utils.getKnowledgeParameter(TeamPage.formation.get(cardSetPos));
            holder.buttons[i].setBackground(ContextCompat.getDrawable(context, param.drawableId));
        }
//        holder.itemView.setOnTouchListener((v, motionEvent) -> {
//            v.performClick();
//            if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
//                if(TeamPage.getOnEditPos() != -1) {
//                    TeamPage.setFormationToOnEditPos(knowledgeParameters.get(position).ownCardId);
//                    Page.jump(context, TeamPage.class);
//                }
//            }
//            return false;
//        });
    }

    @Override
    public int getItemCount() {
        return 5; //5*5 table
    }

    static class TeamTableLine extends RecyclerView.ViewHolder {
        final Button buttons[] = new Button[5];
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
