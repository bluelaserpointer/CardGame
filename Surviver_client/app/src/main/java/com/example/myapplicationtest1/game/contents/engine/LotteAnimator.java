package com.example.myapplicationtest1.game.contents.engine;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.paint.ImageFrame;

import java.util.Arrays;

public class LotteAnimator {
    private final int ANM_SIZE = 89;
    private final ImageFrame[] anime = new ImageFrame[ANM_SIZE];

    { //TODO: resource load
//				for (int i = 0; i < ANM_SIZE; ++i)
//					anime[i] = ImageFrame.create("image/lotte/" + (i + 1) + ".png");
    }

    private final ImageFrame[] cardsBack = {
            ImageFrame.create(R.drawable.card0),
            ImageFrame.create(R.drawable.card1),
            ImageFrame.create(R.drawable.card2),
            ImageFrame.create(R.drawable.card3),
            ImageFrame.create(R.drawable.card4),
    };
    private final ImageFrame CardBGIF = ImageFrame.create(R.drawable.cardbg);
    public int lottedRarity;
    private Knowledge lottedKnowledge;
    private int lotteAnimeStartFrame = GHQ.NONE;
    public void startAnimation(int lottedRarity, Knowledge lottedKnowledge) {
        this.lottedRarity = lottedRarity;
        this.lottedKnowledge = lottedKnowledge;
        lotteAnimeStartFrame = GHQ.nowFrame();
    }
    public void skipAnimation() {
        lotteAnimeStartFrame = GHQ.NONE;
    }
    public void paintResult() {
        CardBGIF.dotPaint(GHQ.screenW() / 2, GHQ.screenH() / 2);
        lottedKnowledge.standPaint.dotPaint_rate(GHQ.screenW() / 2, GHQ.screenH() / 2, 2.0);
        //TODO: name paint & description paint
        //GHQ.drawStringGHQ("��" + lottedKnowledge.NAME + "��", 270, 70, 20F);
        //GHQ.drawStringGHQ(lottedKnowledge.PARAM.appendTalk, 270, 400, Font.BOLD, 20F);
    }
    public void idle() {
        if (lotteAnimeStartFrame != GHQ.NONE) {
            int frame = GHQ.passedFrame(lotteAnimeStartFrame);
            int span;
            if (frame - ANM_SIZE < 0) {
                anime[frame].dotPaint(GHQ.screenW() / 2, GHQ.screenH() / 2);
                return;
            }
            frame -= ANM_SIZE;
            span = 10;
            final ImageFrame IF = cardsBack[lottedRarity];
            if (frame - span < 0) {
                IF.dotPaint_turn(GHQ.screenW() / 2, GHQ.screenH() / 2, (double) frame / span * 2 * Math.PI);
                return;
            }
            frame -= span;
            span = 15;
            if (frame - span < 0) {
                final int W = (int) (cardsBack[lottedRarity].width() * Math.cos((double) frame / span * Math.PI / 2));
                final int H = cardsBack[lottedRarity].height();
                IF.rectPaint(GHQ.screenW() / 2 - W / 2, GHQ.screenH() / 2 - H / 2, W, H);
                return;
            }
            frame -= span;
            span = 15;
            if (frame - span < 0) {
                final double COS = Math.cos(((double) frame / span + 1) * Math.PI / 2);
                final int W = -(int) (cardsBack[lottedRarity].width() * COS);
                final int H = cardsBack[lottedRarity].height();
                CardBGIF.rectPaint(GHQ.screenW() / 2 - W / 2, GHQ.screenH() / 2 - H / 2, W, H);
                final int W2 = -(int) (lottedKnowledge.standPaint.width() * COS) * 2;
                final int H2 = lottedKnowledge.standPaint.height() * 2;
                lottedKnowledge.standPaint.rectPaint(GHQ.screenW() / 2 - W2 / 2, GHQ.screenH() / 2 - H2 / 2, W2, H2);
                return;
            }
        }
        paintResult();
    }
}
