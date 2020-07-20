package com.example.myapplicationtest1.game.preset.unit;

import android.graphics.Color;
import android.graphics.Paint;

import com.example.myapplicationtest1.game.calculate.Damage;
import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.core.GHQObject;
import com.example.myapplicationtest1.game.paint.dot.DotPaint;
import com.example.myapplicationtest1.game.paint.dot.HasDotPaint;
import com.example.myapplicationtest1.game.physics.HasAnglePoint;
import com.example.myapplicationtest1.game.physics.HitIntractable;
import com.example.myapplicationtest1.game.physics.Point;
import com.example.myapplicationtest1.game.preset.item.ItemData;

/**
 * A primal class for managing unit.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public abstract class Unit extends GHQObject implements HasAnglePoint, HasDotPaint {
	public static final Unit NULL_UNIT = new Unit() {
		final Paint paint = GHQ.generatePaint(Color.BLACK);
		@Override
		public void paint() {
			super.paint();
			GHQ.fillCircle(point(), 5, paint);
		}
		@Override
		public Unit respawn(int spawnX, int spawnY) {
			return this;
		}
		@Override
		public boolean isAlive() {
			return true;
		}
		@Override
		public String name() {
			return "DummyUnit";
		}
		@Override
		public DotPaint getDotPaint() {
			return DotPaint.BLANK_SCRIPT;
		}
	};
	public String originalName = "";
	
	/////////////
	//Initialization
	/////////////
	public static final <T extends Unit>T initialSpawn(T unit, int spawnX, int spawnY) {
		unit.loadImageData();
		unit.respawn(spawnX,spawnY);
		return unit;
	}
	public static final <T extends Unit>T initialSpawn(T unit, Point point) {
		return initialSpawn(unit, point.intX(), point.intY());
	}
	public abstract Unit respawn(int spawnX, int spawnY);
	public final Unit respawn(Point point) {
		return respawn(point.intX(), point.intY());
	}
	public void loadImageData(){}
	
	@Override
	public void idle() {
		super.idle();
		if(!isAlive()) {
			claimDeleteFromStage();
		}
	}
	
	/////////////
	//control
	/////////////
	public void damagedTarget(Unit targetUnit, Damage damage) {}

	/////////////
	//event
	/////////////
	public void removedItem(ItemData item) {}
	
	/////////////
	//information
	/////////////
	public String name() {
		return "[Unit]" + "NOT_NAMED";
	}
	@Override
	public boolean intersects(HitIntractable object) {
		return isAlive() && super.intersects(object);
	}
	public abstract boolean isAlive();
}