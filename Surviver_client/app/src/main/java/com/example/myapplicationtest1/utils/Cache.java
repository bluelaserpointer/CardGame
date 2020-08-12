package com.example.myapplicationtest1.utils;

import android.content.Context;

import com.example.myapplicationtest1.CardStatusEnum;
import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.contents.engine.Subject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Objects;

public class Cache {
    /////////////
    //authorization token
    /////////////
    public static String token;
    /////////////
    //assets version
    /////////////
    public static void checkAssetsVersion() {
        if(Utils.sp == null)
            return;
        final int ver = Integer.parseInt(Objects.requireNonNull(Utils.sp.getString("assetsVersion", "0")));

    }
    /////////////
    //user
    /////////////
    public static int chiKnowledge;
    public static int credits;
    public static int curExpPoint;
    public static int engKnowledge;
    public static String email;
    public static double grade;
    public static int level;
    public static int mathKnowledge;
    public static int money;
    public static String phoneNumber;
    public static int stamina;
    public static int userId = -1;
    public static String userName;
    public static void setUserInfo(JSONObject userInfo) {
        try {
            chiKnowledge = userInfo.getInt("chiKnowledge");
            credits = userInfo.getInt("credits");
            curExpPoint = userInfo.getInt("curExpPoint");
            engKnowledge = userInfo.getInt("engKnowledge");
            email = userInfo.getString("email");
            grade = userInfo.getDouble("grade");
            level = userInfo.getInt("level");
            mathKnowledge = userInfo.getInt("mathKnowledge");
            money = userInfo.getInt("money");
            phoneNumber = userInfo.getString("phoneNumber");
            stamina = userInfo.getInt("stamina");
            userId = userInfo.getInt("userId");
            userName = userInfo.getString("userName");
        } catch(JSONException e) {
            e.printStackTrace();
        }
    }
    /////////////
    //general
    /////////////
    public static void reloadAll(Context context) {
        Cache.loadCardsFromNet();
        Cache.loadOwnCardsFromNet();
        Cache.loadFormation(context);
        Cache.loadActivitiesFromNet();
    }
    /////////////
    //card
    /////////////
    public static class Card {
        // 卡牌名称
        public String cardName;
        // 卡牌稀有度
        public String rarity;
        // 卡牌hp属性
        public int healthPoint;
        // 卡牌攻击属性
        public int attack;
        // 卡牌防御属性
        public int defense;
        // 卡牌攻击范围
        public int attackRange;
        // 卡牌冷却时间
        public double cd;
        // 卡牌速度属性
        public int speed;
        // 基本属性通用
        public String strStatus(CardStatusEnum status) {
            switch(status) {
                case HP:
                    return String.valueOf(healthPoint);
                case ATK:
                    return String.valueOf(attack);
                case DEF:
                    return String.valueOf(defense);
                case ATKRange:
                    return String.valueOf(attackRange);
                case CD:
                    return String.valueOf(cd);
                case SPD:
                    return String.valueOf(speed);
            }
            return null;
        }
        // 卡牌派系(1 chi 2 mat 3 eng)
        public int type;
        // 新获得时台词
        public String talkNewcomer;
        // 说明文
        public String description;
        // 图片
        public int drawableId = R.drawable.tongyongc;
        public Subject subject() {
            return Subject.values()[type];
        }
    }
    public static HashMap<Integer, Card> cards = new HashMap<>();
    /**
     *
     * @return success: total amount of cards | fail: failed index of the card * -1
     */
    public static int loadCardsFromNet() {
        int index = 0;
        try {
            final JSONArray cardsJson = new JSONArray(HttpClient.doGetShort(Urls.getAllCards()));
            for(index = 0; index < cardsJson.length(); ++index) {
                final JSONObject cardJson = cardsJson.getJSONObject(index);
                final Card card = new Card();
                card.cardName = cardJson.getString("cardName");
                card.rarity = cardJson.getString("rarity");
                card.healthPoint = cardJson.getInt("healthPoint");
                card.attack = cardJson.getInt("attack");
                card.defense = cardJson.getInt("defense");
                card.attackRange = cardJson.getInt("attackRange");
                card.cd = cardJson.getDouble("cd");
                card.speed = cardJson.getInt("speed");
                card.type = cardJson.getInt("type");
                final int cardId = cardJson.getInt("cardId");
                System.out.println("CachedCard: " + cardId);
                cards.put(cardId, card);
            }
            return index;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return index*-1;
    }
    /////////////
    //chapter
    /////////////
    public static class Chapter {
        public final HashMap<Integer, ChapterDetail> phases = new HashMap<>();
    }
    public static class ChapterDetail {

    }
    public static final HashMap<Integer, Chapter> chapters = new HashMap<>();
    public static void loadChaptersFromNet() {

    }
    /////////////
    //ownCard
    /////////////
    public static class OwnCard {
        public int cardId;
        public int ownCardId;
        public Card card;
        // 用户拥有的某张卡片的等级
        public int cardLevel;
        // 用户拥有的某张卡牌的目前的经验值（升级清零）
        public int cardCurExp;
        // 用户目前拥有的这张卡牌的等级上限
        public int cardLevelLimit;
        // 用户目前重复拥有这张卡拍的数目
        public int repetitiveOwns;
        // 用户首次获取该卡牌的日期+时间
        public Timestamp accquireDate;
        // 强化点数
        public int enhancePoint;
        // 剩余强化点数
        public int leftPoints;
        // 各属性强化次数
        public final int[] enhanced = new int[CardStatusEnum.values().length];
        // 目前卡牌hp属性
        public int healthPoint() {
            return (int)(card.healthPoint + 1.0*enhanced[CardStatusEnum.HP.ordinal()]);
        }
        // 目前卡牌攻击属性
        public int attack() {
            return (int)(card.attack + 1.0*enhanced[CardStatusEnum.ATK.ordinal()]);
        }
        // 目前卡牌防御属性
        public int defense() {
            return (int)(card.defense + 1.0*enhanced[CardStatusEnum.DEF.ordinal()]);
        }
        // 目前卡牌攻击范围
        public int attackRange() {
            return (int)(card.attackRange + 1.0*enhanced[CardStatusEnum.ATKRange.ordinal()]);
        }
        // 目前卡牌冷却时间
        public double cd() {
            return card.cd + 1.0*enhanced[CardStatusEnum.CD.ordinal()];
        }
        // 目前卡牌速度属性
        public int speed() {
            return (int)(card.speed + 1.0*enhanced[CardStatusEnum.SPD.ordinal()]);
        }
        // 通用
        public String strVal(CardStatusEnum status) {
            switch(status) {
                case HP:
                    return String.valueOf(healthPoint());
                case ATK:
                    return String.valueOf(attack());
                case DEF:
                    return String.valueOf(defense());
                case ATKRange:
                    return String.valueOf(attackRange());
                case CD:
                    return String.valueOf(cd());
                case SPD:
                    return String.valueOf(speed());
            }
            return null;
        }
    }
    public static final HashMap<Integer, OwnCard> ownCards = new HashMap<>();
    public static int loadOwnCardsFromNet() {
        int index = 0;
        try {
            final JSONArray ownCardsJson = new JSONArray(HttpClient.doGetShort(Urls.getAllOwnCard()));
            for(index = 0; index < ownCardsJson.length(); ++index) {
                System.out.println("CachedOwnCard: " + index);
                final JSONObject ownCardJson = ownCardsJson.getJSONObject(index);
                final OwnCard ownCard = new OwnCard();
                ownCard.cardId = ownCardJson.getInt("cardId");
                ownCard.ownCardId = ownCardJson.getInt("ownCardId");
                ownCard.card = cards.get(ownCard.cardId);
                ownCard.cardLevel = ownCardJson.getInt("cardLevel");
                ownCard.cardCurExp = ownCardJson.getInt("cardCurExp");
                ownCard.cardLevelLimit = ownCardJson.getInt("cardLevelLimit");
                ownCard.repetitiveOwns = ownCardJson.getInt("repetitiveOwns");
                ownCard.accquireDate = Timestamp.valueOf(ownCardJson.getString("accquireDate"));
                ownCard.enhancePoint = ownCardJson.getInt("enhancePoint");
                ownCard.leftPoints = ownCardJson.getInt("leftPoints");
                ownCard.enhanced[CardStatusEnum.HP.ordinal()] = ownCardJson.getInt("enhanceHP");
                ownCard.enhanced[CardStatusEnum.ATK.ordinal()] = ownCardJson.getInt("enhanceAttack");
                ownCard.enhanced[CardStatusEnum.DEF.ordinal()] = ownCardJson.getInt("enhanceDefense");
                ownCard.enhanced[CardStatusEnum.ATKRange.ordinal()] = ownCardJson.getInt("enhanceAttackRange");
                ownCard.enhanced[CardStatusEnum.CD.ordinal()] = ownCardJson.getInt("enhanceCD");
                ownCard.enhanced[CardStatusEnum.SPD.ordinal()] = ownCardJson.getInt("enhanceSpeed");
                //TODO: Need change them to latest logic
                ownCards.put(ownCardJson.getInt("ownCardId"), ownCard);
            }
            return index;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return index;
    }
    /////////////
    //formation
    /////////////
    public static final HashMap<Integer, Integer> formation = new HashMap<>();
    public static void saveFormation(Context context) {
        Utils.writeFile(context, "formation.txt", MapStringUtil.mapToString(Cache.formation));
    }
    public static void loadFormation(Context context) {
        final String str = Utils.readFile(context, "formation.txt");
        if(str != null) {
            Cache.formation.clear();
            Cache.formation.putAll(MapStringUtil.stringToMap(str));
        }
    }
    /////////////
    //activity
    /////////////
    public static class Activity {
        public String title;
        public String type;
        public String description;
        public String time;
        public String imgBase64;
    }
    public static HashMap<Integer, Activity> activities = new HashMap<>();
    public static void loadActivitiesFromNet() {
        try {
            final JSONArray activitiesJson = new JSONArray(HttpClient.doGetShort(Urls.getAllActivities()));
            for(int i = 0; i < activitiesJson.length(); ++i) {
                final JSONObject activityJson = activitiesJson.getJSONObject(i);
                final Activity activity = new Activity();
                activity.title = activityJson.getString("activityName");
                activity.type = activityJson.getString("type");
                activity.time = activityJson.getString("start");
                final JSONObject detailJson = activityJson.getJSONObject("activityDetails");
                activity.description = detailJson.getString("activityDescription");
                activity.imgBase64 = detailJson.getString("activityImg");
                final int activityId = activityJson.getInt("activityId");
                System.out.println("CachedActivity: " + activityId);
                activities.put(activityId, activity);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
