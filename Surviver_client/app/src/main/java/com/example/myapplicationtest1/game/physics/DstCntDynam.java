package com.example.myapplicationtest1.game.physics;

import com.example.myapplicationtest1.game.core.GHQ;

import static java.lang.Math.sqrt;

/**
 * A subclass of {@link Dynam} which is able to count its physical distance.<br>
 * Such as {@link DstCntDynam#setXY(double, double)} don't affect the distance count.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class DstCntDynam extends Dynam{
	private static final long serialVersionUID = -617770729132898247L;
	
	protected double movedDistance;
	
	//init
	public DstCntDynam() {
		super();
	}
	public DstCntDynam(Point sample) {
		super(sample);
	}
	public DstCntDynam(HasAnglePoint sample) {
		super(sample);
	}
	
	//control
	@Override
	public DstCntDynam setAll(Point sample) {
		super.setAll(sample);
		if(sample instanceof DstCntDynam)
			movedDistance = ((DstCntDynam) sample).movedDistance;
		return this;
	}
	public void setAllBySampleAndInitDistCnt(Dynam sample) {
		super.setAll(sample);
		movedDistance = 0;
	}
	@Override
	public void clear() {
		super.clear();
		movedDistance = 0.0;
	}
	public void resetMovedDistanceCount() {
		movedDistance = 0;
	}
	@Override
	public void moveBySpeed() {
		if(xSpd == 0 && ySpd == 0)
			return;
		x += xSpd;
		y += ySpd;
		movedDistance += sqrt(xSpd*xSpd + ySpd*ySpd);
	}
	@Override
	public double move(double lengthCap) {
		if(speed() < lengthCap) {
			final double leftDistance = super.move(lengthCap);
			movedDistance += speed();
			return leftDistance;
		}else {
			super.move(lengthCap);
			movedDistance += lengthCap;
			return 0;
		}
	}
	@Override
	public void moveIfNoObstacles(HitIntractable source) {
		if(xSpd == 0 && ySpd == 0 || GHQ.stage().hitObstacle_atNewPoint(source, (int)xSpd, (int)ySpd))
			return;
		x += xSpd;
		y += ySpd;
		movedDistance += sqrt(xSpd*xSpd + ySpd*ySpd);
	}
	@Override
	public void approach(double dstX,double dstY,double speed) {
		final double DX = dstX - x,DY = dstY - y;
		final double DISTANCE = sqrt(DX*DX + DY*DY);
		if(DISTANCE <= speed) {
			x = dstX;
			y = dstY;
			movedDistance += DISTANCE;
		}else {
			final double RATE = speed/DISTANCE;
			x += DX*RATE;
			y += DY*RATE;
			movedDistance += speed;
		}
	}
	@Override
	public void approachIfNoObstacles(HitIntractable source, double dstX, double dstY, double speed) {
		final double DX = dstX - x,DY = dstY - y;
		final double DISTANCE = sqrt(DX*DX + DY*DY);
		if(DISTANCE > speed) {
			final double RATE = speed/DISTANCE;
			dstX = x + DX*RATE;
			dstY = y + DY*RATE;
		}
		if(!GHQ.stage().hitObstacle_atNewPoint(source, (int)dstX, (int)dstY)) {
			x = dstX;
			y = dstY;
			movedDistance += DISTANCE < speed ? DISTANCE : speed;
		}
	}
	
	//information
	public double getMovedDistance() {
		return movedDistance;
	}
}
