package com.example.myapplicationtest1.page;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.utils.Cache;

public class CardDetailPage extends Page {
    private int sp;
    private int hpUpg, atkUpg, defUpg, atkRangeUpg, cdUpg;
    public static Cache.OwnCard selectingOwnCard;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail);
        super.setJump(R.id.return_button, CardStoragePage.class);
        //setCardStatus
        ((ImageView)findViewById(R.id.cardIcon)).setImageResource(selectingOwnCard.card.drawableId);
        ((TextView)findViewById(R.id.cardRarity)).setText(selectingOwnCard.card.rarity + " ");
        ((TextView)findViewById(R.id.cardName)).setText(selectingOwnCard.card.cardName);
        ((TextView)findViewById(R.id.cardLevel)).setText(selectingOwnCard.cardLevel + "/" + selectingOwnCard.cardLevelLimit);
        ((TextView)findViewById(R.id.cardExp)).setText(selectingOwnCard.cardCurExp + "/" + 100);
        ((TextView)findViewById(R.id.cardSP)).setText(selectingOwnCard.leftPoints + "/" + selectingOwnCard.enhancePoint);
        ((TextView)findViewById(R.id.cardHP)).setText(String.valueOf(selectingOwnCard.healthPoint()));
        ((TextView)findViewById(R.id.cardHPEnhanced)).setText(String.valueOf(selectingOwnCard.enhanceHP));
        ((TextView)findViewById(R.id.cardATK)).setText(String.valueOf(selectingOwnCard.attack()));
        ((TextView)findViewById(R.id.cardATKEnhanced)).setText(String.valueOf(selectingOwnCard.enhanceAttack));
        ((TextView)findViewById(R.id.cardDEF)).setText(String.valueOf(selectingOwnCard.defense()));
        ((TextView)findViewById(R.id.cardDEFEnhanced)).setText(String.valueOf(selectingOwnCard.enhanceDefense));
        ((TextView)findViewById(R.id.cardATKRange)).setText(String.valueOf(selectingOwnCard.attackRange()));
        ((TextView)findViewById(R.id.cardATKRangeEnhanced)).setText(String.valueOf(selectingOwnCard.enhanceAttackRange));
        ((TextView)findViewById(R.id.cardCD)).setText(String.valueOf(selectingOwnCard.cd()));
        ((TextView)findViewById(R.id.cardCDEnhanced)).setText(String.valueOf(selectingOwnCard.enhanceCD));
        ((TextView)findViewById(R.id.cardDescription)).setText(String.valueOf(selectingOwnCard.card.description));
        super.setTouchEvent(R.id.addHP, () -> { if(drawSp()) ++hpUpg; });
        super.setTouchEvent(R.id.addATK, () -> { if(drawSp()) ++atkUpg; });
        super.setTouchEvent(R.id.addDEF, () -> { if(drawSp()) ++defUpg; });
        super.setTouchEvent(R.id.addATKRange, () -> { if(drawSp()) ++atkRangeUpg; });
        super.setTouchEvent(R.id.addCD, () -> { if(drawSp()) ++cdUpg; });
        //TODO: check if it possible to revert skill up points.
        super.setTouchEvent(R.id.subHP, () -> {});
        super.setTouchEvent(R.id.subATK, () -> {});
        super.setTouchEvent(R.id.subDEF, () -> {});
        super.setTouchEvent(R.id.subATKRange, () -> {});
        super.setTouchEvent(R.id.subCD, () -> {});
    }
    private boolean drawSp() {
        if(sp <= 0)
            return false;
        --sp;
        return true;
    }
}
