package com.example.myapplicationtest1.game.preset.item;

import com.example.myapplicationtest1.game.paint.dot.DotPaint;
import com.example.myapplicationtest1.game.paint.dot.HasDotPaint;

public interface Usable extends HasDotPaint {
	public static final Usable NULL_USABLE = new Usable() {
		@Override
		public void use() {}
		@Override
		public DotPaint getDotPaint() {
			return DotPaint.BLANK_SCRIPT;
		}
	};
	public abstract void use();
}
