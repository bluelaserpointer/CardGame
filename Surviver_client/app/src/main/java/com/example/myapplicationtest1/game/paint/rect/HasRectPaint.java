package com.example.myapplicationtest1.game.paint.rect;

/**
 * Subclasses of this has a accessible {@link RectPaint} instance.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public interface HasRectPaint {
	/**
	 * Return the RectPaint instance of this object.
	 * @return {@link RectPaint}
	 */
	public abstract RectPaint getRectPaint();
}
