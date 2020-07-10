package engine;

import calculate.Damage;
import core.GHQObject;

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
