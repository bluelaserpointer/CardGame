package com.example.myapplicationtest1.game.core;


public interface T_Verifier<T> {
	abstract T objectToT(Object object);
	default boolean objectToTAccepts(Object object) {
		return objectToT(object) != null;
	}
}
