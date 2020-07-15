package com.example.myapplicationtest1.game.contents.bullet;

import android.graphics.Color;
import android.graphics.Paint;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.contents.effect.Effects;
import com.example.myapplicationtest1.game.contents.engine.MyUnit;
import com.example.myapplicationtest1.game.contents.engine.SurDamage;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.core.GHQObject;
import com.example.myapplicationtest1.game.paint.ImageFrame;
import com.example.myapplicationtest1.game.physics.HitGroup;
import com.example.myapplicationtest1.game.physics.hitShape.Square;
import com.example.myapplicationtest1.game.preset.bullet.Bullet;
import com.example.myapplicationtest1.game.weapon.Weapon;

public class Bullets extends Bullet {
	public Bullets(GHQObject shooter) {
		super(shooter);
	}
	/////////////////
	//LASER
	/////////////////
	public static class Laser extends Bullets {
		public Laser(Weapon originWeapon, GHQObject shooter, HitGroup hitGroup) {
			super(shooter);
			this.originWeapon = originWeapon;
			physics().setHitRule(hitGroup);
			physics().setHitShape(new Square(this, 10));
			point().setMoveAngle(point().moveAngle()/* + Math.random()*0.1*/);
			point().setSpeed(10);
			name = "SurBullet";
			limitFrame = 50;
			paintScript = ImageFrame.create(this, R.drawable.a1_fire);
		}
		public Laser(Weapon originWeapon, GHQObject shooter, HitGroup hitGroup, Laser sample) {
			super(shooter);
			this.originWeapon = originWeapon;
			physics().setHitRule(hitGroup);
			physics().setHitShape(new Square(this, 10));
			point().setMoveAngle(point().moveAngle()/* + Math.random()*0.1*/);
			point().setSpeed(10);
			setDamage(sample.damage);
			name = "SurBullet";
			limitFrame = 50;
			paintScript = ImageFrame.create(this, R.drawable.a1_fire);
		}
		public Laser getOriginal() {
			return new Laser(originWeapon, shooter, hitGroup(), this);
		}
		@Override
		public void idle() {
			super.idle();
		}
		@Override
		public final void hitObject(GHQObject object) {
			super.hitObject(object);
			GHQ.stage().addEffect(new Effects.SparkHitEF(this));
		}
		@Override
		public final void paint() {
			int color;
			if(this.hitGroup().get() == MyUnit.ENEMY) {
				if(((SurDamage)damage).value() > 0) {
					color = Color.BLACK;
				} else {
					color = Color.GREEN;
				}
			} else {
				if(((SurDamage)damage).value() > 0) {
					color = Color.RED;
				} else {
					color = Color.WHITE;
				}
			}
			this.hitShape().draw(GHQ.generatePaint(color, 1F, Paint.Style.STROKE));
		}
	}
}
