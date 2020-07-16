package com.example.myapplicationtest1.game.contents.unit;

import android.graphics.Color;
import android.graphics.Paint;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.paint.dot.DotPaint;
import com.example.myapplicationtest1.game.preset.unit.Unit;

public class Enemy extends MyUnit {

	final EnemyParameter PARAM;

	public static int chiCount, matCount, engCount;
	
	public Enemy(EnemyParameter param) {
		super(param.NAME, param.PAINT, ENEMY);
		PARAM = param;
	}
	@Override
	public Enemy respawn(int x, int y) {
		super.respawn(x, y);
		maxHP = hp = PARAM.iniHP;
		atk = PARAM.iniATK;
		def = PARAM.iniDEF;
		atkRange = PARAM.iniATKRange;
		cd = PARAM.iniCD;
		spd = PARAM.iniSPD;
		//TODO: client&server-side enemy enhancement
//		for(int i = 0; i < Engine_Survivor.stage; ++i) {
//			hp += hp*0.5;
//			atk += atk*0.5;
//			def += def*0.5;
//		}
		angle().set(-Math.PI);
		return this;
	}
	private static final int drops[] = {
		2, 4, 6, 10, 15, 30, 50, 100, 150, 200
	};
	@Override
	public void forceDelete() {
	}
	@Override
	public void claimDeleteFromStage() {
		final int RAND = GHQ.random2(0, chiCount + matCount + engCount - 1);
		//TODO: server_side enemyDrop calculation
//		if(RAND < chiCount) {
//			Engine_Survivor.resourceAmount[0] += drops[Engine_Survivor.stage];
//		}else if(RAND < chiCount + matCount) {
//			Engine_Survivor.resourceAmount[1] += drops[Engine_Survivor.stage];
//		}else
//			Engine_Survivor.resourceAmount[2] += drops[Engine_Survivor.stage];
		super.claimDeleteFromStage();
	}
	@Override
	public void idle() {
		super.idle();
		Unit targetUnit = null;
		double distance = GHQ.MAX;
		final boolean IS_DMG = atk > 0;
		for(Unit unit : GHQ.stage().units) {
			if(unit != this && (IS_DMG ? unit instanceof Knowledge : unit instanceof Enemy)) {
				final double DISTANCE = unit.point().distance(this);
				if(DISTANCE < distance) {
					distance = DISTANCE;
					targetUnit = unit;
				}
			}
		}
		if(targetUnit != null) {
			angle().set(point().angleTo(targetUnit));
			if(distance > (int)(atkRange*0.8))
				point().approach(targetUnit, spd);
			else {
				mainWeapon.trigger(this);
			}
		}
	}
	public static class EnemyParameter{
		public String NAME;
		public DotPaint PAINT;
		public int iniHP, iniATK, iniDEF, iniCD;
		public double iniATKRange, iniSPD;
		public String description;

		public EnemyParameter(String name, DotPaint paint, int hp, int atk, int def, double atkRange, int cd, double spd, String description) {
			NAME = name;
			PAINT = paint;
			iniHP = hp;
			iniATK = atk;
			iniDEF = def;
			iniATKRange = atkRange;
			iniCD = cd;
			iniSPD = spd;
			if((this.description = description) == null)
				this.description = "No description data found.";
		}
		Enemy generate() {
			return new Enemy(this);
		}
	}
}