package bullet;

import core.GHQ;
import core.GHQObject;
import effect.Effects;
import paint.ImageFrame;
import physics.HitRule;
import physics.hitShape.Circle;
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
		public Laser(Weapon originWeapon, GHQObject shooter, HitRule hitGroup) {
			super(shooter);
			this.originWeapon = originWeapon;
			physics().setHitRule(hitGroup);
			physics().setHitShape(new Circle(this, 3));
			point().setSpeed(2);
			point().setMoveAngle(point().moveAngle() - 0.05 + Math.random()*0.1);
			name = "Laser";
			limitFrame = 50;
			paintScript = ImageFrame.create(this, "image/animations/1_fire.png");
		}
		public Laser(Weapon originWeapon, GHQObject shooter, HitRule hitGroup, Laser sample) {
			super(shooter);
			this.originWeapon = originWeapon;
			physics().setHitRule(hitGroup);
			physics().setHitShape(new Circle(this, 3));
			point().setSpeed(2);
			point().setMoveAngle(point().moveAngle() - 0.05 + Math.random()*0.1);
			setDamage(sample.damage);
			name = "ACCAR";
			limitFrame = 50;
			paintScript = ImageFrame.create(this, "image/animations/1_fire.png");
		}
		public Laser getOriginal() {
			return new Laser(originWeapon, shooter, hitGroup(), this);
		}
		@Override
		public void idle() {
			if(checkIsOutofLifeSpan()) {
				claimDeleteFromStage();
				return;
			}
			boolean alive;
			int loops = 50;
			while(point().inStage() && --loops > 0) {
				alive = dynamIdle();
//				if(loops > 5) {
//					GHQ.getG2D(Color.RED).fillRect(point().intX(), point().intY(), 1, 1);
//				} else {
//					paint();
//				}
				if(!alive)
					return;
			}
			paint();
		}
		@Override
		public final void hitObject(GHQObject object) {
			super.hitObject(object);
			GHQ.stage().addEffect(new Effects.SparkHitEF(this));
		}
	}
	
}
