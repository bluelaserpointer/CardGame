package com.example.myapplicationtest1.game.preset.effect.debugEffect;

import android.graphics.Paint;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.core.GHQObject;
import com.example.myapplicationtest1.game.paint.ColorLine;
import com.example.myapplicationtest1.game.physics.Angle;
import com.example.myapplicationtest1.game.physics.Point;
import com.example.myapplicationtest1.game.preset.effect.Effect;

public class DebugEffect extends Effect {
	public static int lifeSpan = 5;
	public static Effect setLine(int color, float width, int x1, int y1, int x2, int y2) {
		final Effect effect = new Effect();
		effect.point().setXY(x1, y1);
		effect.paintScript = new ColorLine(color, width).convertToDotPaint(GHQObject.NULL_GHQ_OBJECT, x2, y2);
		effect.limitFrame = lifeSpan;
		return GHQ.stage().addEffect(effect);
	}
	public static Effect setLine(int color, float width, Point p1, Point p2) {
		return setLine(color, width, p1.intX(), p1.intY(), p2.intX(), p2.intY());
	}
	public static Effect setLine(int color, int x1, int y1, int x2, int y2) {
		return setLine(color, 1F, x1, y1, x2, y2);
	}
	public static Effect setLine(int color, Point p1, Point p2) {
		return setLine(color, p1.intX(), p1.intY(), p2.intX(), p2.intY());
	}
	public static Effect setDot(int color, Point point) {
		return GHQ.stage().addEffect(new Effect() {
			final Paint paint = new Paint();
			{
				paint.setColor(color);
				point().setXY(point);
				limitFrame = lifeSpan;
			}
			@Override
			public void idle() {
				super.idle();
				GHQ.stage().addEffect(new Effect() {
					{
						point().setXY(point);
						limitFrame = 10;
						point().addSpeed_DA(15, Angle.random());
					}
					@Override
					public void paint() {
						GHQ.fillCircle(point(), 4, paint);
					}
				});
			}
		});
	}
}
