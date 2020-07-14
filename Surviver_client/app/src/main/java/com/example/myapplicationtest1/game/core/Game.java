package com.example.myapplicationtest1.game.core;

import android.graphics.Canvas;

import com.example.myapplicationtest1.game.physics.stage.GHQStage;

/**
 * A important class which is a frame of game main system.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public abstract class Game {
	
	public void loadResource() {}
	public abstract GHQStage loadStage();
	
	//idle
	public abstract void idle(Canvas canvas, int stopEventKind);
	
	//information
	public String getTitleName(){
		return GHQ.NOT_NAMED;
	}
	public String getVersion(){
		return GHQ.NOT_NAMED;
	}
}
