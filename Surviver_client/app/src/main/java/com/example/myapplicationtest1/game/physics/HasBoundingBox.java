package com.example.myapplicationtest1.game.physics;

import android.graphics.Paint;
import android.graphics.Rect;

import androidx.constraintlayout.solver.widgets.Rectangle;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.physics.hitShape.HasArea;

public interface HasBoundingBox extends HasPoint, HasArea {
	public default int centerX() {
		return point().intX();
	}
	public default int centerY() {
		return point().intY();
	}
	public default int leftX() {
		return point().intX() - width()/2;
	}
	public default int topY() {
		return point().intY() - height()/2;
	}
	public default boolean boundingBoxIntersectsDot(int x, int y) {
		return point().inRangeXY(centerX(), centerY(), width()/2, height()/2);
	}
	public default boolean boundingBoxIntersectsDot(Point point) {
		return boundingBoxIntersectsDot(point.intX(), point.intY());
	}
	public default boolean boundingBoxIntersectsLine(int x1, int y1, int x2, int y2) {
		//TODO: rectangle & line intersects
		return isLineIntersectRectangle(x1, y1, x2, y2, leftX(), topY(), leftX() + width(), topY() + height());
	}
	public default boolean boundingBoxIntersectsLine(Point p1, Point p2) {
		return boundingBoxIntersectsLine(p1.intX(), p1.intY(), p2.intX(), p2.intY());
	}
	public default boolean boundingBoxIntersects(HasBoundingBox target) {
		return Math.abs(centerX() - target.centerX()) < (width() + target.width())/2
				&& Math.abs(centerY() - target.centerY()) < (height() + target.height())/2;
	}
	public default void drawBoundingBox(Paint paint) {
		GHQ.fillRect(leftX(), topY(), width(), height(), paint);
	}
	public default void drawBiggerBoundingBox(Paint paint, int sizeAdd) {
		GHQ.fillRect(leftX() - sizeAdd/2, topY() - sizeAdd/2, width() + sizeAdd, height() + sizeAdd, paint);
	}
	/**
	 * <p> 判断线段是否在矩形内
	 * <p>
	 * 先看线段所在直线是否与矩形相交，
	 * 如果不相交则返回false，
	 * 如果相交，
	 * 则看线段的两个点是否在矩形的同一边（即两点的x(y)坐标都比矩形的小x(y)坐标小，或者大）,
	 * 若在同一边则返回false，
	 * 否则就是相交的情况。
	 * </p>
	 *
	 * @param linePointX1 线段起始点x坐标
	 * @param linePointY1 线段起始点y坐标
	 * @param linePointX2 线段结束点x坐标
	 * @param linePointY2 线段结束点y坐标
	 * @param rectangleLeftTopX 矩形左上点x坐标
	 * @param rectangleLeftTopY 矩形左上点y坐标
	 * @param rectangleRightBottomX 矩形右下点x坐标
	 * @param rectangleRightBottomY 矩形右下点y坐标
	 * @return 是否相交
	 */
	public static boolean isLineIntersectRectangle(int linePointX1,int linePointY1,int linePointX2,int linePointY2,int rectangleLeftTopX,int rectangleLeftTopY,int rectangleRightBottomX,int rectangleRightBottomY) {

		int lineHeight = linePointY1 - linePointY2;
		int lineWidth = linePointX2 - linePointX1;
		// 计算叉乘
		int c = linePointX1 * linePointY2 - linePointX2 * linePointY1;

		if ((lineHeight * rectangleLeftTopX + lineWidth * rectangleLeftTopY + c >= 0 && lineHeight * rectangleRightBottomX + lineWidth * rectangleRightBottomY + c <= 0)
				|| (lineHeight * rectangleLeftTopX + lineWidth * rectangleLeftTopY + c <= 0 && lineHeight * rectangleRightBottomX + lineWidth * rectangleRightBottomY + c >= 0)
				|| (lineHeight * rectangleLeftTopX + lineWidth * rectangleRightBottomY + c >= 0 && lineHeight * rectangleRightBottomX + lineWidth * rectangleLeftTopY + c <= 0)
				|| (lineHeight * rectangleLeftTopX + lineWidth * rectangleRightBottomY + c <= 0 && lineHeight * rectangleRightBottomX + lineWidth * rectangleLeftTopY + c >= 0)) {
			if (rectangleLeftTopX > rectangleRightBottomX) {
				int temp = rectangleLeftTopX;
				rectangleLeftTopX = rectangleRightBottomX;
				rectangleRightBottomX = temp;
			}
			if (rectangleLeftTopY < rectangleRightBottomY) {
				int temp = rectangleLeftTopY;
				rectangleLeftTopY = rectangleRightBottomY;
				rectangleRightBottomY = temp;
			}
			if ((linePointX1 < rectangleLeftTopX && linePointX2 < rectangleLeftTopX)
					|| (linePointX1 > rectangleRightBottomX && linePointX2 > rectangleRightBottomX)
					|| (linePointY1 > rectangleLeftTopY && linePointY2 > rectangleLeftTopY)
					|| (linePointY1 < rectangleRightBottomY && linePointY2 < rectangleRightBottomY)) {
				return false;
			} else {
				return true;
			}
		} else {
			return false;
		}
	}
}