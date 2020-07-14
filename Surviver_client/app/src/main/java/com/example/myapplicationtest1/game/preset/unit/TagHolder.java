package com.example.myapplicationtest1.game.preset.unit;

import java.io.Serializable;
import java.util.ArrayList;

public abstract class TagHolder implements Serializable{
	private static final long serialVersionUID = 5735269528773492135L;

	public abstract ArrayList<Tag> getTags();
}

class Tag{
	public String getName() {
		return "NOT_NAMED";
	}
}
class PassiveTag extends Tag{
	
}