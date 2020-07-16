package com.example.myapplicationtest1.game.contents.engine;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.paint.ImageFrame;

public class GameGuide {
    private static int step = 0;
    private final ImageFrame[] introIFs = new ImageFrame[] {
            ImageFrame.create(R.drawable.guide0),
            ImageFrame.create(R.drawable.guide1),
            ImageFrame.create(R.drawable.guide2),
            ImageFrame.create(R.drawable.guide3),
            ImageFrame.create(R.drawable.guide4),
            ImageFrame.create(R.drawable.guide5),
            ImageFrame.create(R.drawable.guide6),
            ImageFrame.create(R.drawable.guide7),
    };
    public void idle() {
        switch(step) {
            case 0:
            case 1:
            case 2:
            case 3:
                introIFs[step].dotPaint(200, 200);
                break;
            case 4:
                introIFs[step].dotPaint(240, 125);
                break;
            case 5:
                //TODO: datafetch
//                if(Engine_Survivor.garrageCount > 0) {
//                    ++step;
//                    introIFs[step].dotPaint(450, 230);
//                }else
//                    introIFs[step].dotPaint(400, 200);
                break;
            case 6:
                //TODO: datafetch
//                if(Engine_Survivor.formationCount > 0) {
//                    ++step;
//                    introIFs[step].dotPaint(450, 275);
//                }else
//                    introIFs[step].dotPaint(450, 230);
                break;
            case 7:
                introIFs[step].dotPaint(450, 275);
                break;
        }
    }
    public void clicked() {
        switch(step) {
            case 0:
            case 1:
            case 2:
            case 3:
                ++step;
                break;
            case 4:
                ++step;
                break;
            case 5:
            case 6:
                break;
            case 7:
                ++step;
                break;
        };
    }
}
