package de.nandi.blackjack.util;

public enum Result {
	WIN(1),
	BJ_WIN(1.5),
	DRAW(0),
	LOST(-1),
	BUST(-1),
	UNDECIDED(Double.NaN);


	private final double winningsMultiplier;
	private boolean doubleDown;

	Result(double winningsMultiplier) {
		this.winningsMultiplier = winningsMultiplier;
		doubleDown = false;
	}

	public double getWinningsMultiplier() {
		return winningsMultiplier;
	}

	public Result setDoubleDown(boolean doubleDown) {
		this.doubleDown = doubleDown;
		return this;
	}

	public boolean isDoubleDown() {
		return doubleDown;
	}
}
