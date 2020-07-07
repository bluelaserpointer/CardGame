package ui;

import java.awt.Font;
import java.awt.event.MouseEvent;

import core.GHQ;
import engine.Engine_Surviver;
import engine.Subject;
import gui.GUIParts;

public class BetButton extends GUIParts {
	private final Subject subject;
	private final int betValue;
	public BetButton(Subject subject, int betValue) {
		this.subject = subject;
		this.betValue = betValue;
		this.setName("add" + betValue + subject.toCHIString());
		this.setBGImage(subject.imageURL());
	}
	@Override
	public void idle() {
		super.idle();
		GHQ.getG2D(subject.color());
		GHQ.drawStringGHQ("BET+" + betValue, point().intX(), point().intY() + 90, GHQ.basicFont.deriveFont(Font.BOLD));
	}
	@Override
	public boolean clicked(MouseEvent e) {
		if(Engine_Surviver.resourceAmount[subject.id()] >= betValue && Engine_Surviver.getTotalBet() + betValue <= 100) {
			Engine_Surviver.lotteAdded[subject.id()] += betValue;
			Engine_Surviver.resourceAmount[subject.id()] -= betValue;
		}
		return super.clicked(e);
	}
}
