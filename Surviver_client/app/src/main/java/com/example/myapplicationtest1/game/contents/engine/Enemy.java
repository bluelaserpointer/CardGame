package com.example.myapplicationtest1.game.contents.engine;

import com.example.myapplicationtest1.R;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.paint.animation.SerialImageFrame;
import com.example.myapplicationtest1.game.paint.dot.DotPaint;
import com.example.myapplicationtest1.game.preset.unit.Unit;

public class Enemy extends MyUnit {

	final EnemyParameter PARAM;

	static int chiCount, matCount, engCount;
	
	private Enemy(EnemyParameter param) {
		super(param.NAME, param.PAINT, ENEMY);
		PARAM = param;
	}
	@Override
	public Enemy respawn(int x, int y) {
		super.respawn(x, y);
		hp = PARAM.iniHP;
		atk = PARAM.iniATK;
		def = PARAM.iniDEF;
		atkRange = PARAM.iniATKRange;
		cd = PARAM.iniCD;
		spd = PARAM.iniSPD;
		for(int i = 0; i < Engine_Survivor.stage; ++i) {
			hp += hp*0.5;
			atk += atk*0.5;
			def += def*0.5;
		}
		maxHP = hp;
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
		if(RAND < chiCount) {
			Engine_Survivor.resourceAmount[0] += drops[Engine_Survivor.stage];
		}else if(RAND < chiCount + matCount) {
			Engine_Survivor.resourceAmount[1] += drops[Engine_Survivor.stage];
		}else
			Engine_Survivor.resourceAmount[2] += drops[Engine_Survivor.stage];
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
	enum EnemyParameter{
		//name , paint , hp , atk , def , range, CD , speed, description
		CHI_QZ("CHI_QZ", new SerialImageFrame(5, R.drawable.chineseqz1_1, R.drawable.chineseqz1_2), 400, 20, 40, 100.0, 60, 3.3, ""),
		MAT_QZ("MAT_QZ", new SerialImageFrame(5, R.drawable.mathqz1_1, R.drawable.mathqz1_2), 200, 60, 15, 80, 20, 3.3, ""),
		ENG_QZ("ENG_QZ", new SerialImageFrame(5, R.drawable.englishqz1_1, R.drawable.englishqz1_2), 200, -40, 15, 300.0, 30, 3.3, "");
		
		final String NAME;
		final DotPaint PAINT;
		final int iniHP, iniATK, iniDEF, iniCD;
		final double iniATKRange, iniSPD;
		final String description;
		
		private EnemyParameter(String name, DotPaint paint, int hp, int atk, int def, double atkRange, int cd, double spd, String description) {
			NAME = name;
			PAINT = paint;
			iniHP = hp;
			iniATK = atk;
			iniDEF = def;
			iniATKRange = atkRange;
			iniCD = cd;
			iniSPD = spd;
			this.description = description;
		}
		Enemy generate() {
			return new Enemy(this);
		}
	}
}