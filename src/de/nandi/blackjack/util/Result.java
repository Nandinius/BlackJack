package de.nandi.blackjack.util;

public enum Result {
	WIN(1),
	BJ_WIN(1.5),
	DRAW(0),
	LOST(-1),
	UNDECIDED(Double.NaN);


	private final double winningsMultiplier;

	Result(double winningsMultiplier) {
		this.winningsMultiplier = winningsMultiplier;
	}

	public double getWinningsMultiplier() {
		return winningsMultiplier;
	}
}
