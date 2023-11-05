package de.nandi.blackjack.util;

public class Trio {
	private final Result result;
	private final int gain;
	private final int trueCount;
	private final boolean doubleDown;

	public Trio(Result result, int gain, int trueCount) {
		this.result = result;
		this.gain = gain;
		this.trueCount = trueCount;
		doubleDown = false;
	}

	public Trio(int bet, Result result, int trueCount) {
		this.result = result;
		this.trueCount = trueCount;
		this.gain = (int) (bet * result.getWinningsMultiplier());
		doubleDown = result.isDoubleDown();
		result.setDoubleDown(false);
	}

	public Result getResult() {
		return result;
	}

	public int getGain() {
		return gain;
	}

	public int getTrueCount() {
		return trueCount;
	}

	public boolean isDoubleDown() {
		return doubleDown;
	}
}
