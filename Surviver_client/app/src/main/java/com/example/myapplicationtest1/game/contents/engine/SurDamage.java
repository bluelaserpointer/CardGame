package com.example.myapplicationtest1.game.contents.engine;

import com.example.myapplicationtest1.game.calculate.Damage;
import com.example.myapplicationtest1.game.contents.unit.MyUnit;
import com.example.myapplicationtest1.game.core.GHQObject;

public class SurDamage extends Damage {
	final int damageValue;
	public SurDamage(int damageValue) {
		this.damageValue = damageValue;
	}
	@Override
	public void doDamage(GHQObject target) {
		((MyUnit)target).damage_amount(this.damageValue);
	}
	@Override
	public SurDamage clone() {
		return new SurDamage(damageValue);
	}
	public int value() {
		return damageValue;
	}
}
