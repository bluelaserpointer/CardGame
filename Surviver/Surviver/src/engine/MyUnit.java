package engine;

import java.util.LinkedList;
import java.util.List;

import preset.bullet.Bullet;
import core.GHQ;
import core.GHQObject;
import physics.HitRule;
import physics.hitShape.Square;
import paint.dot.DotPaint;
import paint.dot.HasDotPaint;
import paint.ImageFrame;
import bullet.Bullets;
import calculate.IntDamage;
import preset.unit.Unit;
import weapon.Weapon;

public abstract class MyUnit extends Unit implements HasDotPaint {

	static final int FRIEND = 0, ENEMY = 1;
	
	final String NAME;
	int maxHP, hp, def, atk, cd;

	double atkRange;
	double spd;
	final DotPaint dotPaint;
	
	public Weapon mainWeapon = setDegaultWeapon();

	protected MyUnit(String name, String image, int hitRule) {
		super.physics().setHitRule(new HitRule(hitRule));
		super.physics().setHitShape(new Square(25));
		NAME = name;
		dotPaint = ImageFrame.create(image);
	}
	protected MyUnit(String name, DotPaint dotPaint, int hitRule) {
		super.physics().setHitRule(new HitRule(hitRule));
		super.physics().setHitShape(new Square(25));
		NAME = name;
		this.dotPaint = dotPaint;
	}
	@Override
	public MyUnit respawn(int x, int y) {
		point().setXY(x, y);
		angle().set(0.0);
		mainWeapon.reset();
		return this;
	}
	@Override
	public boolean isAlive() {
		return hp > 0;
	}
	public int damage_amount(int damage) {
		final boolean IS_DMG = damage > 0;
		damage -= def;
		if(!IS_DMG) {
			hp -= damage;
			if(hp > maxHP) {
				final int RESULT = damage + (hp - maxHP);
				hp = maxHP;
				return RESULT;
			}else
				return damage;
		}else if(damage > 0) {
			hp -= damage;
			return damage;
		}else
			return 0;
	}
	public int drain_maxHP(int damage) {
		final int DRAIN = Math.min(damage, maxHP);
		maxHP -= damage;
		if(hp < maxHP)
			hp = maxHP;
		return DRAIN;
	}
	protected final Weapon setDegaultWeapon() {
		return new Weapon() {
			{
				this.setCoolTime(cd);
			}
			@Override
			public List<Bullet> setBullets(GHQObject shooter, HitRule standpoint) {
				final Bullet bullet = GHQ.stage().addBullet(new Bullets.Laser(this, shooter, new HitRule((shooter instanceof Knowledge) ^ atk < 0 ? FRIEND : ENEMY)));
				bullet.setDamage(new IntDamage(atk) {
					@Override
					public void doDamage(GHQObject target) {
						((MyUnit)target).damage_amount(this.damageValue);
					}
					@Override
					public IntDamage clone() {
						return this;
					}
				});
				bullet.limitRange = atkRange;
				bullet.point().setSpeed(10);
				bullet.point().addXY_allowsMoveAngle(0, 18);
				final LinkedList<Bullet> bulletList = new LinkedList<>();
				bulletList.add(bullet);
				return bulletList;
			}
		};
	}
	@Override
	public void idle() {
		super.idle();
		////////////
		//skill&attack
		////////////
		mainWeapon.idle();
		////////////
		//paint
		////////////
		dotPaint.dotPaint_capSize(point(), 100);
		GHQ.paintHPArc(point(), 70, hp, maxHP);
		////////////
		//dynam
		////////////
		point().moveIfNoObstacles(this);
		point().mulSpeed(0.9);
	}
	@Override
	public DotPaint getDotPaint() {
		return dotPaint;
	}
}
