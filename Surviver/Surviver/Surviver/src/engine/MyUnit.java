package engine;

import bullet.Bullet;
import core.GHQ;
import core.Standpoint;
import geom.Square;
import paint.DotPaint;
import paint.HasDotPaint;
import paint.ImageFrame;
import physics.Dynam;
import physics.HasAnglePoint;
import unit.BulletLibrary;
import unit.Unit;
import weapon.Weapon;
import weapon.WeaponInfo;

public abstract class MyUnit extends Unit implements HasDotPaint{
	private static final long serialVersionUID = 8308964807929123877L;

	static final int FRIEND = 0, ENEMY = 1;
	
	final String NAME;
	int maxHP, hp, def, atk, cd;

	double atkRange;
	double spd;
	final DotPaint dotPaint;
	
	public Weapon mainWeapon = setDegaultWeapon();

	protected MyUnit(String name, String image, int standpoint) {
		super(new Square(25), standpoint);
		NAME = name;
		dotPaint = new ImageFrame(image);
	}
	protected MyUnit(String name, DotPaint dotPaint, int standpoint) {
		super(new Square(25), standpoint);
		NAME = name;
		this.dotPaint = dotPaint;
	}
	@Override
	public void respawn(int x, int y) {
		dynam.setXY(x, y);
		baseAngle.set(0.0);
		mainWeapon.reset();
	}
	@Override
	public boolean isAlive() {
		return hp > 0;
	}
	@Override
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
		WeaponInfo.clear();
		WeaponInfo.coolTime = cd;
		final MyUnit unit = this;
		return new Weapon() {
			private static final long serialVersionUID = -1692674505068462831L;
			/*@Override
			public void trigger() {
				
			}*/
			@Override
			public void setBullets(HasAnglePoint shooter, Standpoint standpoint) {
				final Bullet BULLET = GHQ.stage().addBullet(new BulletLibrary.ACCAR(this, shooter, new Standpoint((unit instanceof Knowledge) ^ atk < 0 ? FRIEND : ENEMY)));
				BULLET.damage = atk;
				BULLET.limitRange = (int)atkRange;
				final Dynam BULLET_DYNAM = BULLET.dynam;
				BULLET_DYNAM.setSpeed(10);
				BULLET_DYNAM.addXY_allowsMoveAngle(0, 18);
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
		dotPaint.dotPaint_capSize(dynam, 100);
		GHQ.paintHPArc(dynam, 70, hp, maxHP);
		////////////
		//dynam
		////////////
		dynam.moveIfNoObstacles(this);
		dynam.accelerate_MUL(0.9);
	}
	@Override
	public DotPaint getPaintScript() {
		return dotPaint;
	}
}
