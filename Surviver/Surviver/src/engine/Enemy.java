package engine;

import core.GHQ;
import paint.dot.DotPaint;
import paint.animation.SerialImageFrame;
import preset.unit.Unit;

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
		for(int i = 0;i < Engine_Surviver.stage;++i) {
			hp += hp*0.5;
			atk += atk*0.5;
			def += def*0.5;
		}
		maxHP = hp;
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
			Engine_Surviver.resourceAmount[0] += drops[Engine_Surviver.stage];
		}else if(RAND < chiCount + matCount) {
			Engine_Surviver.resourceAmount[1] += drops[Engine_Surviver.stage];
		}else
			Engine_Surviver.resourceAmount[2] += drops[Engine_Surviver.stage];
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
		CHI_QZ("语文期中考试", new SerialImageFrame(5, "image/ChineseQZ1_1.png", "image/ChineseQZ1_2.png"), 400, 20, 40, 100.0, 60, 3.3, ""),
		MAT_QZ("数学期中考试", new SerialImageFrame(5, "image/MathQZ1_1.png", "image/MathQZ1_2.png"), 200, 60, 15, 80, 20, 3.3, ""),
		ENG_QZ("英语期中考试", new SerialImageFrame(5, "image/EnglishQZ1_1.png", "image/EnglishQZ1_2.png"), 200, -40, 15, 300.0, 30, 3.3, "");
		
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