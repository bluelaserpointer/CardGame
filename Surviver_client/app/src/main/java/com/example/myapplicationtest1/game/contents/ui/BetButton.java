package com.example.myapplicationtest1.game.contents.ui;

import com.example.myapplicationtest1.game.contents.engine.Engine_Survivor;
import com.example.myapplicationtest1.game.contents.engine.Subject;

public class BetButton {
	private final Subject subject;
	private final int betValue;
	public BetButton(Subject subject, int betValue) {
		this.subject = subject;
		this.betValue = betValue;
	}
	public void idle() {
		//GHQ.drawStringGHQ("BET+" + betValue, point().intX(), point().intY() + 90, GHQ.basicFont.deriveFont(Font.BOLD));
	}
	public void clicked() {
		if(Engine_Survivor.resourceAmount[subject.ordinal()] >= betValue && Engine_Survivor.getTotalBet() + betValue <= 100) {
			Engine_Survivor.lotteAdded[subject.ordinal()] += betValue;
			Engine_Survivor.resourceAmount[subject.ordinal()] -= betValue;
		}
	}
}
