package com.example.myapplicationtest1.game.physics;

import com.example.myapplicationtest1.game.physics.direction.DirectionLR;

public class Angle {
	protected double angle;
	public static final Angle NULL_ANGLE = new Angle() {
		public double get() {
			return 0.0;
		}
	};
	
	//initialization
	public Angle() {
		this.angle = 0.0;
	}
	public Angle(double angle) {
		this.angle = angle;
	}
	//control
	public void set(double angle) {
		this.angle = angle;
	}
	public void set(Angle sample) {
		set(sample.angle);
	}
	public void set(HasAngle sample) {
		set(sample.angle());
	}
	public void set(double dx, double dy) {
		set(Math.atan2(dy, dx));
	}
	public void spin(double angle) {
		set(get() + angle);
	}
	public static double spinTo_ConstSpd(double nowAngle, double targetAngle, double angularSpeed) {
		final double D_ANGLE = targetAngle - nowAngle;
		if(D_ANGLE < 0) {
			if(D_ANGLE < -angularSpeed) {
				return nowAngle - angularSpeed;
			} else {
				return targetAngle;
			}
		}else if(D_ANGLE > 0){
			if(D_ANGLE > angularSpeed) {
				return nowAngle + angularSpeed;
			} else {
				return targetAngle;
			}
		}else
			return nowAngle;
	}
	public static double spinTo_Suddenly(double nowAngle, double targetAngle, double turnFrame) {
		return nowAngle + (targetAngle - nowAngle)/turnFrame;
	}
	//tool
	public static double random() {
		return Math.random()*Math.PI*2;
	}
	//information
	public double get() {
		return angle;
	}
	public double sin() {
		return Math.sin(get());
	}
	public double cos() {
		return Math.cos(get());
	}
	public double diff(double angle) {
		return angle - get();
	}
	public double absDist(double angle) {
		final double rawAngle = Math.abs(diff(angle));
		return rawAngle < Math.PI ? rawAngle : Math.PI*2 - rawAngle;
	}
	public boolean isDiffSmaller(double angle, double range) {
		return absDist(angle) < range;
	}
}
