package com.example.myapplicationtest1.game.core;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.fonts.Font;
import android.view.View;

import static java.lang.Math.*;

import java.io.*;
import java.net.*;
import java.text.DecimalFormat;
import java.util.*;

import com.example.myapplicationtest1.game.calculate.Consumables;
import com.example.myapplicationtest1.game.physics.*;
import com.example.myapplicationtest1.game.physics.stage.GHQStage;

/**
 * The core class for engine "THH"
 * @author bluelaserpointer
 * @version alpha1.0
 */

public final class GHQ implements Runnable{
	private static long serialVersionUID = 123412351L;

	/**
	 * A static instance of GHQ for enabling static methods accessing non-static objects.
	 * @since alpha1.0
	 */
	public static GHQ hq;
	
	public static String
		GHQ_VERSION = "Ver alpha1.0.0";
	/**
	 * A major constant which describe various meaning related to "nothing".
	 * @since alpha1.0
	 */
	public static int NONE = -999999999;
	public static int  
		MAX = Integer.MAX_VALUE,
		MIN = Integer.MIN_VALUE;
	public static String
		NOT_NAMED = "<Not Named>";

	//debug
	private static boolean freezeScreen;
	private boolean debugMode;
	private static int loadTime_total;
	public static String errorPoint = "NONE";

	//stopEvent
	private static int stopEventKind = NONE;
	public static int STOP = 0;

	//screen
	private static int screenW, screenH;
	private static double viewX, viewY, viewDstX, viewDstY;
	private static double stageZoomRate = 1.0;

	//graphic
	public static Canvas targetCanvas;
	public static Resources resources;

	//frame
	private static int systemFrame;
	private static int gameFrame;

	//stage
	private static Game engine;

	//stage object data
	private static GHQStage stage;

	//Initialize methods/////////////
	public GHQ(Game engine){
		GHQ.engine = engine;
		hq = this;
		//load assets
		//setup
		resetStage();
		//font
		engine.loadResource();
		//basicFont = createFont("font/upcibi.ttf").deriveFont(25.0f);
		//commentFont = createFont("font/HGRGM.TTC").deriveFont(Font.PLAIN, 15.0f);
		new Thread(this).start();
	}

	//mainLoop///////////////
	private static long mili1000Stamp;
	private static int framesCount;
	private static double nowFPS;

    public static void setResource(Resources resources) {
    	GHQ.resources = resources;
    }

    public final void run(){
		mili1000Stamp = GHQ.nowTime();
		while(true){
			//fps
			final int PASSED_TIME = GHQ.passedTime(mili1000Stamp);
			++framesCount;
			if(PASSED_TIME >= 1000) {
				nowFPS = (double)framesCount / (double)PASSED_TIME * 1000.0;
				mili1000Stamp = GHQ.nowTime();
				framesCount = 0;
			}
			//sleep
			try{
				Thread.sleep(25L);
			}catch(InterruptedException ignored){}
			//main process
			if(!freezeScreen) {
				repaint();
			}
		}
	}

	public static Font initialFont, basicFont, commentFont;
	private static Paint whitePaint = new Paint(Color.WHITE), debugTextPaint = new Paint(Color.LTGRAY);
	public static DecimalFormat DF0_0 = new DecimalFormat("0.0"), DF0_00 = new DecimalFormat("0.00"), DF00_00 = new DecimalFormat("00.00");

	public void repaint() {
		final long LOAD_TIME_PAINT_COMPONENT = System.currentTimeMillis();
		targetCanvas.drawRect(0, 0, screenW(), screenH(), whitePaint);
		final int TRANSLATE_X = (int)viewX,TRANSLATE_Y = (int)viewY;
		targetCanvas.translate(TRANSLATE_X, TRANSLATE_Y);
		////////////////////////////////////////////////////////////////////////
		//gameIdle
		final int tx = (int)GHQ.getScreenCenterStageX(), ty = (int)GHQ.getScreenCenterStageY();
		final float zoomRate = (float)stageZoomRate;
		if(zoomRate != 1.0) {
			targetCanvas.translate(tx, ty);
			targetCanvas.scale(zoomRate, zoomRate);
			targetCanvas.translate(-tx, -ty);
			engine.idle(stopEventKind);
			targetCanvas.translate(tx, ty);
			final float _zoomRate = 1F/zoomRate;
			targetCanvas.scale(_zoomRate, _zoomRate);
			targetCanvas.translate(-tx, -ty);
		} else
			engine.idle(stopEventKind);
		////////////////////////////////////////////////////////////////////////
		//GUIIdle
		targetCanvas.translate(-TRANSLATE_X, -TRANSLATE_Y);
		//debug ////////////////////////
		if(debugMode){
			//grid
			for(int i = 100;i < screenW;i += 100)
				targetCanvas.drawLine(i, 0, i, screenH, debugTextPaint);
			for(int i = 100;i < screenH;i += 100)
				targetCanvas.drawLine(0, i, screenW, i, debugTextPaint);
			GHQ.translateForGUI(false);
			//Origin
			GHQ.fillCircle(0, 0, 10, debugTextPaint);
			GHQ.fillCircle(0, 0, 25, debugTextPaint);
			GHQ.fillCircle(0, 0, 50, debugTextPaint);
			//stageEdge
			final int STAGE_W = stage.width(), STAGE_H = stage.height();
			{ //LXRX lines
				final int LX = 0, RX = STAGE_W;
				for(int i = 0;i < 50;i++) {
					final int Y = STAGE_H*i/50;
					targetCanvas.drawLine(LX - 20, Y - 20, LX + 20, Y + 20, debugTextPaint);
					targetCanvas.drawLine(RX - 20, Y - 20, RX + 20, Y + 20, debugTextPaint);
				}
			}
			{ //LYRY lines
				final int LY = 0, RY = STAGE_H;
				for(int i = 0;i < 50;i++) {
					final int X = STAGE_W*i/50;
					targetCanvas.drawLine(X - 20, LY - 20, X + 20, LY + 20, debugTextPaint);
					targetCanvas.drawLine(X - 20, RY - 20, X + 20, RY + 20, debugTextPaint);
				}
			}
			GHQ.translateForGUI(true);
			//entityInfo
			targetCanvas.drawText(stage.entityAmountInfo(), 30, 100, debugTextPaint);
			targetCanvas.drawText("LoadTime(ms):" + loadTime_total, 30, 120, debugTextPaint);
			targetCanvas.drawText("FPS:" + DF00_00.format(nowFPS), 30, 140, debugTextPaint);
			targetCanvas.drawText("GameTime(ms):" + gameFrame, 30, 160, debugTextPaint);
			//unitInfo
			stage.unitDebugPaint();
		}
		//increase frame count
		systemFrame++;
		if(stopEventKind == NONE)
			gameFrame++;
		//calculate total paintComponent time
		loadTime_total = (int)(System.currentTimeMillis() - LOAD_TIME_PAINT_COMPONENT);
	}

	//information-stage
	public static GHQStage stage() {
		return stage;
	}
	public static Game getEngine() {
		return engine;
	}
	//information-GUI
	public static int screenW(){
		return targetCanvas.getWidth();
	}
	public static int screenH(){
		return targetCanvas.getHeight();
	}
	public static Canvas getTargetCanvas() {
		return targetCanvas;
	}
	//information-paint
	public static boolean inScreen(int x, int y) {
		return abs(viewX - x) < screenW && abs(viewY - y) < screenH;
	}

	//control
	//control-stage
	public static GHQStage setStage(GHQStage stage) {
		return GHQ.stage = stage;
	}
	public static void setStageZoomRate(double value) {
		stageZoomRate = value;
	}
	//control-viewPoint
	public static void viewMove(double dx, double dy) {
		viewX -= dx;viewY -= dy;
		viewDstX -= dx;viewDstY -= dy;
	}
	public static void viewTo(double x, double y) {
		viewDstX = viewX = -x + screenW/2;
		viewDstY = viewY = -y + screenH/2;
	}
	public static void viewTo(Point point) {
		viewTo(point.intX(), point.intY());
	}
	public static void viewTo(HasPoint target) {
		viewTo(target.point());
	}
	public static void pureViewMove(double dx, double dy) {
		viewX -= dx;viewY -= dy;
	}
	public static void pureViewTo(double x, double y) {
		viewX = x;viewY = y;
	}
	public static void viewTargetTo(double x, double y) {
		viewDstX = -x + screenW/2;viewDstY = -y + screenH/2;
	}
	public static void viewTargetMove(double x, double y) {
		viewDstX -= x;viewDstY -= y;
	}
	public static void viewApproach_speed(int speed) {
		if(abs(viewDstX - viewX) < speed)
			viewX = viewDstX;
		else
			viewX += viewX < viewDstX ? speed : -speed;
		if(abs(viewDstY - viewY) < speed)
			viewY = viewDstY;
		else
			viewY += viewY < viewDstY ? speed : -speed;
	}
	public static void viewApproach_rate(double rate) {
		final double DX = (double)(viewDstX - viewX)/rate,DY = (double)(viewDstY - viewY)/rate;
		if(-0.1 < DX && DX < 0.1)
			viewX = viewDstX;
		else
			viewX += DX;
		if(-0.1 < DY && DY < 0.1)
			viewY = viewDstY;
		else
			viewY += DY;
	}
	public static double getViewX() {
		return viewX;
	}
	public static double getViewY() {
		return viewY;
	}
	public static int getScreenCenterStageX() {
		return toStageCoordinate_x(GHQ.screenW/2);
	}
	public static int getScreenCenterStageY() {
		return toStageCoordinate_y(GHQ.screenH/2);
	}
	public static int getScreenLeftX_stageCod() {
		return toStageCoordinate_x(0);
	}
	public static int getScreenTopY_stageCod() {
		return toStageCoordinate_y(0);
	}
	public static int getScreenW_stageCod() {
		return (int)(screenW/stageZoomRate);
	}
	public static int getScreenH_stageCod() {
		return (int)(screenH/stageZoomRate);
	}
	//control-add

	//control-delete
	public static void paintHPArc(int x, int y, int radius, int hp, int maxHP) {
		int color;
		if((double)hp/(double)maxHP > 0.75)
			color = Color.CYAN;
		else if((double)hp/(double)maxHP > 0.50)
			color = Color.GREEN;
		else if((double)hp/(double)maxHP > 0.25)
			color = Color.YELLOW;
		else
			color = Color.RED;
		final Paint paint = new Paint();
		paint.setColor(color);
		paint.setStrokeWidth(5);
		paint.setStyle(Paint.Style.STROKE);
		targetCanvas.drawText(String.valueOf(hp), x + (int)(radius*1.1) + (hp >= 10 ? 0 : 6), y + (int)(radius*1.1), paint);
		//TODO: Antialiasing
		//TODO: arcPaint

		//开始画
		targetCanvas.drawArc(new RectF(x - radius, y - radius, x + radius, y + radius), -90, (int)((double)hp/(double)maxHP*360), true, paint);
	}
	public static void paintHPArc(int x, int y, int radius, Consumables hp) {
		paintHPArc(x, y, radius, hp.intValue(), hp.max().intValue());
	}
	public static void paintHPArc(Point point, int radius, int hp, int maxHP) {
		paintHPArc(point.intX(), point.intY(), radius, hp, maxHP);
	}
	public static void paintHPArc(Point point, int radius, Consumables hp) {
		paintHPArc(point.intX(), point.intY(), radius, hp);
	}
	public static int toStageCoordinate_x(int x) {
		//return (int)(x/stageZoomRate + screenW/2*(1 - 1/stageZoomRate)) - (int)viewX;
		return (int)((x - screenW/2)/stageZoomRate) + screenW/2 - (int)viewX;
	}
	public static int toStageCoordinate_y(int y) {
		//return (int)(y/stageZoomRate + screenH/2*(1 - 1/stageZoomRate)) - (int)viewY;
		return (int)((y - screenH/2)/stageZoomRate) + screenH/2 - (int)viewY;
	}
	public static int toScreenCoordinate_x(int x) {
		return (int)(x*stageZoomRate - screenW/2*(stageZoomRate - 1)) + (int)viewX;
	}
	public static int toScreenCoordinate_y(int y) {
		return (int)(y*stageZoomRate - screenH/2*(stageZoomRate - 1)) + (int)viewY;
	}
	public static boolean intersectsScreen_screenCod(int x, int y, int w, int h) {
		return Math.abs(x - screenW/2) < (screenW + w)/2 && Math.abs(y - screenH/2) < (screenH + h)/2;
	}
	public static boolean intersectsScreen_stageCod(int x, int y, int w, int h) {
		return intersectsScreen_screenCod(toScreenCoordinate_x(x), toScreenCoordinate_y(y), (int)(w/stageZoomRate), (int)(h/stageZoomRate));
	}
	
	//control
	/**
	 * Freeze screen manually.
	 * @since alpha1.0
	 */
	public static void freezeScreen() {
		freezeScreen = true;
	}
	public static void stopScreen() {
		stopEventKind = STOP;
	}
	/**
	 * Clear freeze screen and other stopEvents.
	 * @since alpha1.0
	 */
	public static void clearStopEvent() {
		stopEventKind = NONE;
	}

	//information-frame&time
	/**
	 * Returns gameFrame.
	 * @return now gameFrame
	 * @since 1.0
	 */
	public static int nowFrame() {
		return gameFrame;
	}
	public static void progressGameFrame() {
		++gameFrame;
	}
	/**
	 * Returns passed frame that independents from game pause.
	 * @return now gameFrame
	 * @since 1.0
	 */
	public static int systemFrame() {
		return systemFrame;
	}
	/**
	 * Returns frames passed from the indicated frame.
	 * @param frame
	 * @return passed frames
	 * @since 1.0
	 */
	public static int passedFrame(int frame) {
		return frame == NONE ? MAX : gameFrame - frame;
	}
	/**
	 * Check if passed frames from the indicated frame is much than the limit.
	 * @return if expired
	 * @since 1.0
	 */
	public static boolean isExpired_frame(int initialFrame, int limitFrame) {
		return initialFrame == NONE || (gameFrame - initialFrame) >= limitFrame;
	}
	/**
	 * Returns systemTime. {@link System#currentTimeMillis()}
	 * @return now systemTime
	 * @since 1.0
	 */
	public static long nowTime() {
		return System.currentTimeMillis();
	}
	/**
	 * Returns time passed from the indicated time. {@link System#currentTimeMillis()}
	 * @param time
	 * @return passed time
	 * @since 1.0
	 */
	public static int passedTime(long time) {
		return time == NONE ? MAX : (int)(System.currentTimeMillis() - time);
	}
	/**
	 * Check if passed time from the indicated time is much than the limit.
	 * @param initialFrame
	 * @param limitTime
	 * @return if expired
	 * @since 1.0
	 */
	public static boolean isExpired_time(long initialFrame,long limitTime) {
		return initialFrame == NONE || (System.currentTimeMillis() - initialFrame) >= limitTime;
	}
	public static boolean isExpired_dynamicSeconds(int frame, double seconds) {
		return frame == NONE || passedFrame(frame) > nowFPS*seconds;
	}
	public static boolean checkSpan_dynamicSeconds(double seconds) {
		final int SPAN = (int)(nowFPS * seconds);
		return SPAN == 0 ? true : gameFrame % SPAN == 0;
	}
	public static double getFPS() {
		return nowFPS;
	}
	public static double getSPF() {
		return 1.0/nowFPS;
	}
	public static double mulSPF(double value) {
		return getSPF()*value;
	}
	/**
	 * Check if there is a stopEvent.
	 * @return boolean
	 * @since 1.0
	 */
	public static boolean isNoStopEvent() {
		return !freezeScreen && stopEventKind == NONE;
	}
	/**
	 * Check if there is a "freezeScreen" stop Event.
	 * @return boolean
	 * @since 1.0
	 */
	public static boolean isFreezeScreen() {
		return freezeScreen;
	}
	
	//stage test area
	final private void resetStage(){
		if(stage != null)
			stage.clear();
		ErrorCounter.clear();
		gameFrame = 0;
		System.gc();
		//System.out.println("stage reset done");
		stage = engine.loadStage();
	}

	//ResourceLoad
	/**
	* Load the font file.
	* @param filename
	* @return Font
	* @since alpha1.0
	*/
	public final Font createFont(String filename){
//		try{
//			return Font.createFont(Font.TRUETYPE_FONT,getClass().getResourceAsStream("/" + filename));
//		}catch (IOException | FontFormatException e){
//			e.printStackTrace();
//			return null;
//		}
		//TODO: create font
		return null;
	}
	
	//PaintTool
	/**
	 * Change paint coordinate for GUI/stageObjects.
	 * Note that if you don't reset changed paint coordinate, it will collapse coordinates of following paints.(like Graphics2D.rotate method)
	 * @param forGUI true - change for GUI / false - change for objects in its stage;
	 * @since alpha1.0
	 */
	public static void translateForGUI(boolean forGUI) {
		final int tx = (int)GHQ.getScreenCenterStageX(), ty = (int)GHQ.getScreenCenterStageY();
		final float zoomRate = (float)stageZoomRate;
		if(forGUI) {
			if(zoomRate != 1F) {
				targetCanvas.translate(tx, ty);
				final float _zoomRate = 1F/zoomRate;
				targetCanvas.scale(_zoomRate, _zoomRate);
				targetCanvas.translate(-tx, -ty);
			}
			targetCanvas.translate(-(float)viewX, -(float)viewY);
		}else {
			targetCanvas.translate((float)viewX, (float)viewY);
			if(zoomRate != 1F) {
				targetCanvas.translate(tx, ty);
				targetCanvas.scale(zoomRate, zoomRate);
				targetCanvas.translate(-tx, -ty);
			}
		}
	}

	public static float getFadingAlpha(int initialFrame, int limitFrame) {
		final float f = (float)(1.0 - (double)GHQ.passedFrame(initialFrame)/limitFrame);
		return f < 0 ? 0 : (f > 1 ? 1 : f);
	}
	//Paint
	static boolean doXFlip, doYFlip;
	/**
	 * quickPaintGenerate
	 */
	public static Paint generatePaint(int color) {
		final Paint paint = new Paint();
		paint.setColor(color);
		return paint;
	}
	public static Paint generatePaint(int color, float width, Paint.Style style) {
		final Paint paint = new Paint();
		paint.setColor(color);
		paint.setStrokeWidth(width);
		paint.setStyle(style);
		return paint;
	}
	/**
	 * easyShapeFill
	 */
	public static void fillRect(int left, int top, int width, int height, Paint paint) {
		targetCanvas.drawRect(left, top, width, height, paint);
	}
	public static void fillCircle(int cx, int cy, int radius, Paint paint) {
		targetCanvas.drawCircle(cx, cy, radius, paint);
	}
	public static void fillRectCenter(int x, int y, int width, int height, Paint paint) {
		fillRect(x - width/2, y - height/2, width, height, paint);
	}
	public static void fillRectCenter(Point cPoint, int width, int height, Paint paint) {
		fillRectCenter(cPoint.intX(), cPoint.intY(), width, height, paint);
	}
	public static void fillCircle(Point cPoint, int radius, Paint paint) {
		fillCircle(cPoint.intX(), cPoint.intY(), radius, paint);
	}
	public static void drawLine(int x1, int y1, int x2, int y2) {
		targetCanvas.drawLine(x1, y1, x2, y2, new Paint());
	}
	public static void drawImageGHQ(Bitmap bm, int x, int y, int w, int h) {
		if(bm == null)
			return;
		final int tx = x + w/2, ty = y + h/2;
		final Rect bmRect = new Rect(0, 0, bm.getWidth(), bm.getHeight());
		targetCanvas.translate(tx, ty);
		if(doXFlip || doYFlip) {
			final float xs = doXFlip ? -1 : 1;
			final float ys = doYFlip ? -1 : 1;
			targetCanvas.scale(xs, ys);
			targetCanvas.drawBitmap(bm, bmRect, new Rect(-w/2, -h/2, w/2, h/2), new Paint());
			targetCanvas.scale(xs, ys);
		} else {
			targetCanvas.drawBitmap(bm, bmRect, new Rect(-w/2, -h/2, w/2, h/2), new Paint());
		}
		targetCanvas.translate(-tx, -ty);
	}
	public static void drawImageGHQ(Bitmap bm, int x, int y, int w, int h, double angle) {
		if(angle != 0.0) {
			final float OX = x + w/2F, OY = y + h/2F;
			targetCanvas.rotate((float)angle, OX, OY);
			drawImageGHQ(bm, x, y, w, h);
			targetCanvas.rotate(-(float)angle, OX, OY);
		}else
			drawImageGHQ(bm, x, y, w, h);
	}
	public static void drawImageGHQ_center(Bitmap bm, int x, int y, double angle){
		if(bm == null)
			return;
		final int w = bm.getWidth(), h = bm.getHeight();
		if(angle != 0F) {
			targetCanvas.rotate((float)angle, x, y);
			targetCanvas.drawBitmap(bm, x - w/2, y - h/2, new Paint());
			targetCanvas.rotate(-(float)angle, x, y);
		} else
			targetCanvas.drawBitmap(bm, x - w/2, y - h/2, new Paint());
	}
	public static void drawImageGHQ_center(Bitmap bm, int x, int y){
		drawImageGHQ_center(bm, x, y, 0.0);
	}
	public static void drawImageGHQ_center(Bitmap bm, Point cPoint, float angle){
		if(bm == null)
			return;
		drawImageGHQ_center(bm, cPoint.intX(), cPoint.intY(), angle);
	}
	public static void drawStringGHQ(String string, int x, int y, Paint paint) {
		targetCanvas.drawText(string, x, y, paint);
	}
	public static void setFlip(boolean doXFlip, boolean doYFlip) {
		GHQ.doXFlip = doXFlip ^ GHQ.doXFlip;
		GHQ.doYFlip = doYFlip ^ GHQ.doYFlip;
	}
	public static void setTargetCanvas(Canvas canvas) {
		targetCanvas = canvas;
	}
	
	//math & string
	public static int toStageX(int screenX) {
		return screenX - (int)viewX;
	}
	public static int toStageY(int screenY) {
		return screenY - (int)viewY;
	}
	public static double angleFormat(double radian){ //ラジアン整理メソッド -PI~+PIに直す
		radian %= PI*2;
		if(radian > PI)
			radian -= PI*2;
		else if(radian <= -PI)
			radian += PI*2;
		return radian;
	}
	public static Random random = new Random();
	public static int random2(int value1, int value2) {
		if(value1 == value2)
			return value1;
		else if(value1 > value2)
			return new Random().nextInt(abs(value1 - value2) + 1) + value2;
		else
			return new Random().nextInt(abs(value2 - value1) + 1) + value1;
	}
	public static double random2(double value1, double value2) {
		if(value1 == value2)
			return value1;
		else if(value1 > value2)
			return Math.random()*(value1 - value2) + value2;
		else
			return Math.random()*(value2 - value1) + value1;
	}
	public static double random2(double value) {
		if(value == 0.0)
			return 0.0;
		else
			return Math.random()*value*2 - value;
	}
	public static int arrangeIn(int value, int min, int max) {
		if(value < min)
			return min;
		else if(value > max)
			return max;
		return value;
	}
}