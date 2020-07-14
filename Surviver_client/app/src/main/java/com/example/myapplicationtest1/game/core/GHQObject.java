package com.example.myapplicationtest1.game.core;

import com.example.myapplicationtest1.game.calculate.Damage;
import com.example.myapplicationtest1.game.paint.HasPaint;
import com.example.myapplicationtest1.game.physics.Angle;
import com.example.myapplicationtest1.game.physics.Dynam;
import com.example.myapplicationtest1.game.physics.HasPhysics;
import com.example.myapplicationtest1.game.physics.HitGroup;
import com.example.myapplicationtest1.game.physics.Physics;
import com.example.myapplicationtest1.game.physics.Point;
import com.example.myapplicationtest1.game.physics.StdPhysics;
import com.example.myapplicationtest1.game.physics.hitShape.Square;
import com.example.myapplicationtest1.game.physics.stage.GHQStage;
import com.example.myapplicationtest1.game.preset.bullet.Bullet;
import com.example.myapplicationtest1.game.preset.effect.Effect;
import com.example.myapplicationtest1.game.preset.item.ItemData;
import com.example.myapplicationtest1.game.preset.structure.Structure;
import com.example.myapplicationtest1.game.preset.unit.Unit;
import com.example.myapplicationtest1.game.preset.vegetation.Vegetation;

import java.util.Iterator;

public class GHQObject implements HasName, HasPaint, HasPhysics {
	public static final GHQObject NULL_GHQ_OBJECT = new GHQObject();
	
	public int initialFrame;
	private static int maxUniqueID = -1;
	public final int uniqueID;
	private boolean isDeleted;
	private int paintOrder = -1;
	public static int nowPaintOrder = -1;
	
	protected String name = this.getClass().getName() + GHQ.NOT_NAMED;
	
	protected Physics physics;
	
	//init
	public GHQObject() {
		initialFrame = GHQ.nowFrame();
		uniqueID = ++maxUniqueID;
		physics = predefine_physics();
	}
	//physics = new StdPhysics().setPoint(new Point.IntPoint(x + w/2, y + h/2)).setHitShape(new RectShape(physics, w, h));
	protected Physics predefine_physics() {
		return new StdPhysics(predefine_point(), predefine_angle(), new Square(this, 0), HitGroup.HIT_ALL);
	}
	protected Point predefine_point() {
		return new Dynam();
	}
	protected Angle predefine_angle() {
		return new Angle();
	}
	public GHQObject setName(String name) {
		this.name = name;
		return this;
	}
	public void claimDeleteFromStage() {
		isDeleted = true;
	}
	public void cancelDeleteFromStage() {
		isDeleted = false;
	}
	public void forceDelete() {
		if(this instanceof Unit)
			GHQ.stage().units.forceRemove((Unit)this);
		else if(this instanceof Bullet)
			GHQ.stage().bullets.forceRemove((Bullet)this);
		else if(this instanceof Effect)
			GHQ.stage().effects.forceRemove((Effect)this);
		else if(this instanceof Structure)
			GHQ.stage().structures.forceRemove((Structure)this);
		else if(this instanceof ItemData)
			GHQ.stage().items.forceRemove((ItemData)this);
		else if(this instanceof Vegetation)
			GHQ.stage().vegetations.forceRemove((Vegetation)this);
	}
	public final boolean hasDeleteClaimFromStage() {
		return isDeleted;
	}
	public void idle() {
		paintOrder = ++nowPaintOrder;
		paint();
	}
	/**
	 * This method automatically called by idle() in GHQObject.
	 */
	@Override
	public void paint() {
	}
	public int getPaintOrder() {
		return paintOrder;
	}
	public boolean upperThen(GHQObject object) {
		return object == null || paintOrder > object.paintOrder;
	}
	public void tellNewFrameStart() {
		nowPaintOrder = -1;
	}
	//control
	public final void resetInitialFrame() {
		initialFrame = GHQ.nowFrame();
	}
	public void damage(Damage damage) {
		damage.doDamage(this);
	}
	//event
	public void addedToStage(GHQStage stage) {}
	//public tool
	public static void removeDeletedFromList(Iterable<GHQObject> list) {
		final Iterator<GHQObject> iterator = list.iterator();
		while(iterator.hasNext()) {
			if(iterator.next().hasDeleteClaimFromStage())
				iterator.remove();
		}
	}
	//information
	@Override
	public String name() {
		return name;
	}
	public boolean nameIs(String name) {
		return this.name == name;
	}
	public final boolean isUnit() {
		return this instanceof Unit;
	}
	public final boolean isBullet() {
		return this instanceof Bullet;
	}
	public final boolean isEffect() {
		return this instanceof Effect;
	}
	public final boolean isStructure() {
		return this instanceof Structure;
	}
	public final boolean isItemData() {
		return this instanceof ItemData;
	}
	public boolean intersects_checkNotDeleted(GHQObject object) {
		return !object.hasDeleteClaimFromStage() && intersects(object);
	}
	public static int getTotalAmount() {
		return maxUniqueID + 1;
	}
	public static void initTotalAmount() {
		maxUniqueID = -1;
	}
	@Override
	public Physics physics() {
		return physics;
	}
}