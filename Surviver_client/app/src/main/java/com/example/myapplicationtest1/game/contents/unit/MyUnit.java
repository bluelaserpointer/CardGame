package com.example.myapplicationtest1.game.contents.unit;

import android.content.Context;

import com.example.myapplicationtest1.HttpClient;
import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.contents.bullet.Bullets;
import com.example.myapplicationtest1.game.contents.engine.SurDamage;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.core.GHQObject;
import com.example.myapplicationtest1.game.paint.ImageFrame;
import com.example.myapplicationtest1.game.paint.dot.DotPaint;
import com.example.myapplicationtest1.game.paint.dot.HasDotPaint;
import com.example.myapplicationtest1.game.physics.HitGroup;
import com.example.myapplicationtest1.game.physics.hitShape.Square;
import com.example.myapplicationtest1.game.preset.bullet.Bullet;
import com.example.myapplicationtest1.game.preset.unit.Unit;
import com.example.myapplicationtest1.game.weapon.Weapon;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedList;
import java.util.List;

public abstract class MyUnit extends Unit implements HasDotPaint {

	public static final int FRIEND = 0, ENEMY = 1;
	
	final String NAME;
	int maxHP, hp, def, atk;
	double cd;

	double atkRange;
	double spd;
	final DotPaint dotPaint;
	
	public Weapon mainWeapon = setDegaultWeapon();

	protected MyUnit(String name, int image, int hitRule) {
		super.physics().setHitRule(new HitGroup(hitRule));
		super.physics().setHitShape(new Square(this, 75));
		NAME = name;
		dotPaint = ImageFrame.create(image);
	}
	protected MyUnit(String name, DotPaint dotPaint, int hitRule) {
		super.physics().setHitRule(new HitGroup(hitRule));
		super.physics().setHitShape(new Square(this, 75));
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
				this.setCoolTime((int)cd);
			}
			@Override
			public List<Bullet> setBullets(GHQObject shooter, HitGroup standpoint) {
				final Bullet bullet = GHQ.stage().addBullet(new Bullets.Laser(this, shooter, new HitGroup((shooter instanceof Knowledge) == atk > 0 ? FRIEND : ENEMY)));
				bullet.setDamage(new SurDamage(atk));
				bullet.limitRange = atkRange;
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
		//dynam
		////////////
		point().moveIfNoObstacles(this);
		point().mulSpeed(0.9);
		dotPaint.dotPaint(point());
		GHQ.paintHPArc(point(), 70, hp, maxHP);
	}
	@Override
	public DotPaint getDotPaint() {
		return dotPaint;
	}
	public static Enemy.EnemyParameter loadAsEnemy(Context context, String fetchURL) {
		Enemy.EnemyParameter enemyParameter = null;
		try {
			final JSONObject enemyInfo = new JSONObject(HttpClient.doGetShort(context, fetchURL));
			enemyParameter = new Enemy.EnemyParameter(
					enemyInfo.getString("cardName"),
					ImageFrame.create(R.drawable.tongyongc),
					enemyInfo.getInt("healthPoint"),
					enemyInfo.getInt("attack"),
					enemyInfo.getInt("defense"),
					enemyInfo.getInt("attackRange"),
					enemyInfo.getInt("cd"),
					enemyInfo.getInt("speed"),
					enemyInfo.getString("cardDetails")
			);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return enemyParameter;
	}
}
