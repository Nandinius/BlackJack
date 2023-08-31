package de.nandi.blackjack.util;

public class Duo {
	private final Result result;
	private final int gain;

	public Duo(Result result, int gain) {
		this.result = result;
		this.gain = gain;
	}

	public Duo(int bet, Result result) {
		this.result = result;
		this.gain = (int) (bet * result.getWinningsMultiplier());
	}

	public Result getResult() {
		return result;
	}

	public int getGain() {
		return gain;
	}
}
