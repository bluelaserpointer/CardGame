package com.example.myapplicationtest1.game.paint;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;

import com.example.myapplicationtest1.game.core.GHQ;
import com.example.myapplicationtest1.game.core.GHQObject;
import com.example.myapplicationtest1.game.paint.dot.DotPaint;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;

/**
 * A popular PaintScript subclass which loads image resource and display it.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public class ImageFrame extends DotPaint {
	private transient Bitmap IMAGE;
	private static final HashMap<Integer, Bitmap> preloadedImageMap = new HashMap<>();
	
	/////////////
	//init
	/////////////
	private ImageFrame(GHQObject owner, int drawableId) {
		super(owner);
		IMAGE = BitmapFactory.decodeResource(GHQ.resources, drawableId);
	}
	private ImageFrame(GHQObject owner) {
		super(owner);
	}
	/**
	* Load the image file.
	* @param drawableId
	* @return ImageFrame
	* @since alpha1.0
	*/
	public static Bitmap loadImage(int drawableId) {
		if(preloadedImageMap.containsKey(drawableId))
			return preloadedImageMap.get(drawableId);
		return BitmapFactory.decodeResource(GHQ.resources, drawableId);
	}
	public static ImageFrame create(int drawableId) {
		return create(GHQObject.NULL_GHQ_OBJECT, drawableId);
	}
	public static ImageFrame create(GHQObject owner, int drawableId) {
		return new ImageFrame(owner, drawableId);
	}
	//TODO: preloadImageFolder in Android
//	public static final void preloadImageFolder(File folder) {
//		for(File imageFile : folder.listFiles()) {
//			final String FILE_NAME = imageFile.getName();
//			if(FILE_NAME.endsWith(".png") || FILE_NAME.endsWith(".jpeg") || FILE_NAME.endsWith(".jpg") || FILE_NAME.endsWith(".gif")) {
//				try {
//					loadImage(imageFile.toURI().toURL());
//				} catch (MalformedURLException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//	}
	
	//main role
	@Override
	public void dotPaint(int x, int y) {
		GHQ.drawImageGHQ_center(IMAGE, x, y);
	}
	@Override
	public void rectPaint(int x,int y,int w,int h) {
		GHQ.drawImageGHQ(IMAGE, x, y, w, h);
	}
	
	//information
	@Override
	public int width() {
		return IMAGE.getWidth();
	}
	@Override
	public int height() {
		return IMAGE.getHeight();
	}
}
