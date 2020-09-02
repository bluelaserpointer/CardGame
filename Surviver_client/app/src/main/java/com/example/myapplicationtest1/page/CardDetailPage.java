package com.example.myapplicationtest1.page;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.example.myapplicationtest1.CardStatusEnum;
import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.utils.Cache;
import com.example.myapplicationtest1.utils.Urls;

import org.json.JSONException;
import org.json.JSONObject;

@RequiresApi(api = Build.VERSION_CODES.N)
public class CardDetailPage extends Page {
    public static Cache.OwnCard selectingOwnCard;
    private int sp;
    private int[] enhancedBackUp;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_detail);
        super.setTouchEvent(R.id.return_button, () -> {
            //返回到其它页面前，把强化点数分配上传至后端
            boolean hasChange = false;
            JSONObject jsonObject = new JSONObject();
            try {
                for (CardStatusEnum status : CardStatusEnum.values()) {
                    final int statusID = status.ordinal();
                    if (enhancedBackUp[statusID] != selectingOwnCard.enhanced[statusID]) {
                        jsonObject.put("enhance" + status.str, selectingOwnCard.enhanced[statusID]);
                        hasChange = true;
                    }
                }
                if(hasChange) {
                    jsonObject.put("ownCardId", selectingOwnCard.ownCardId);
                    System.out.println("CardDetailPage: redistribution is " + jsonObject.toString());
                    HttpClient.doPostShort(this, Urls.redistributeUpgrades(), jsonObject.toString());
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            selectingOwnCard.leftPoints = sp;
            Page.jump(this, CardStoragePage.class);
        });
        //BackUpEnhancedPoint
        enhancedBackUp = new int[selectingOwnCard.enhanced.length];
        System.arraycopy(selectingOwnCard.enhanced, 0, this.enhancedBackUp, 0, selectingOwnCard.enhanced.length);
        //setCardStatus
        super.setImage_ImageView(R.id.cardIcon, selectingOwnCard.card.drawableId);
        super.setText_TextView(R.id.cardRarity, selectingOwnCard.card.rarity + " ");
        super.setText_TextView(R.id.cardName, selectingOwnCard.card.cardName);
        super.setText_TextView(R.id.cardLevel, selectingOwnCard.cardLevel + "/" + selectingOwnCard.cardLevelLimit);
        super.setText_TextView(R.id.cardExp, selectingOwnCard.cardCurExp + "/" + 100);
        sp = selectingOwnCard.leftPoints;
        //update texts
        for(CardStatusEnum status : CardStatusEnum.values()) {
            updateStatusText(status);
        }
        updateSPText();
        //upgrades
        super.setTouchEvent(R.id.addHP, () -> editUpgPt(CardStatusEnum.HP, +1));
        super.setTouchEvent(R.id.addATK, () -> editUpgPt(CardStatusEnum.ATK, +1));
        super.setTouchEvent(R.id.addDEF, () -> editUpgPt(CardStatusEnum.DEF, +1));
        super.setTouchEvent(R.id.addATKRange, () -> editUpgPt(CardStatusEnum.ATKRange, +1));
        super.setTouchEvent(R.id.addCD, () -> editUpgPt(CardStatusEnum.CD, +1));
        super.setTouchEvent(R.id.addSpeed, () -> editUpgPt(CardStatusEnum.SPD, +1));
        //downgrades
        super.setTouchEvent(R.id.subHP, () -> editUpgPt(CardStatusEnum.HP, -1));
        super.setTouchEvent(R.id.subATK, () -> editUpgPt(CardStatusEnum.ATK, -1));
        super.setTouchEvent(R.id.subDEF, () -> editUpgPt(CardStatusEnum.DEF, -1));
        super.setTouchEvent(R.id.subATKRange, () -> editUpgPt(CardStatusEnum.ATKRange, -1));
        super.setTouchEvent(R.id.subCD, () -> editUpgPt(CardStatusEnum.CD, -1));
        super.setTouchEvent(R.id.subSpeed, () -> editUpgPt(CardStatusEnum.SPD, -1));
    }
    private void editUpgPt(CardStatusEnum status, int change) {
        if(change == 0)
            return;
        final int statusID = status.ordinal();
        if(change > 0) { //upgrade
            if(sp > 0) {
                ++selectingOwnCard.enhanced[statusID];
                --sp;
            }
        } else { //downgrade
            if(selectingOwnCard.enhanced[statusID] > 0) {
                --selectingOwnCard.enhanced[statusID];
                ++sp;
            }
        }
        this.updateStatusText(status);
        this.updateSPText();
    }
    private void updateSPText() {
        super.setText_TextView(R.id.cardSP, sp + "/" + selectingOwnCard.enhancePoint);
    }
    private void updateStatusText(CardStatusEnum status) {
        final int statusID = status.ordinal();
        final int newUpgPt = selectingOwnCard.enhanced[statusID];
        //change upgPt text color if new value changed from original value.
        final int textColor;
        if(newUpgPt > enhancedBackUp[statusID]) { //plus is green
            textColor = Color.GREEN;
        } else if(newUpgPt < enhancedBackUp[statusID]) { //minus is red
            textColor = Color.RED;
        } else { //unchanged is white
            textColor = Color.WHITE;
        }
        ((TextView)findViewById(status.upgPtTextViewId)).setTextColor(textColor);
        ((TextView)findViewById(status.valTextViewId)).setTextColor(textColor);
        //update this status's texts
        super.setText_TextView(status.upgPtTextViewId, String.valueOf(newUpgPt));
        super.setText_TextView(status.valTextViewId, String.valueOf(selectingOwnCard.strVal(status)));
    }
    private void discardUpgChanges() {
        System.arraycopy(this.enhancedBackUp, 0, selectingOwnCard.enhanced, 0, selectingOwnCard.enhanced.length);
    }
}
