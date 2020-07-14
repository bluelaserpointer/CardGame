package com.example.myapplicationtest1.game.preset.structure;

import com.example.myapplicationtest1.game.core.GHQObject;
import com.example.myapplicationtest1.game.physics.HitInteractable;

/**
 * A primal class for managing structure.
 * @author bluelaserpointer
 * @since alpha1.0
 */
public abstract class Structure extends GHQObject implements HitInteractable, HasVisibility {
	@Override
	public double visibility() {
		return Double.NEGATIVE_INFINITY;
	}
}