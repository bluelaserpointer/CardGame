package bullet;

import java.awt.Color;

import core.GHQ;
import core.GHQObject;
import effect.Effects;
import engine.MyUnit;
import engine.SurDamage;
import paint.ImageFrame;
import physics.HitGroup;
import physics.hitShape.Circle;
import physics.hitShape.Square;
import preset.bullet.Bullet;
import weapon.Weapon;

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
			paintScript = ImageFrame.create(this, "image/animations/1_fire.png");
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
			paintScript = ImageFrame.create(this, "image/animations/1_fire.png");
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
			Color color;
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
			this.hitShape().draw(color, GHQ.stroke1);
		}
	}
}
