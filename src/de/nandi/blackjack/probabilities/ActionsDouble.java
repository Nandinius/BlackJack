package de.nandi.blackjack.probabilities;

import java.util.Arrays;

public class ActionsDouble {
	private final String[] actions;
	private final double[] expectedValue;

	public ActionsDouble() {
		this.actions = new String[10];
		this.expectedValue = new double[10];
	}

	public String[] getActions() {
		return actions;
	}

	public double[] getExpectedValue() {
		return expectedValue;
	}

	public boolean isSet(int index) {
		return expectedValue[index] != 0;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{");
		for (int i = 0; i < 10; i++) {
			stringBuilder.append("[").append(actions[i]).append(", ").append(expectedValue[i]).append("]");
			if (i != 9)
				stringBuilder.append(", ");
		}
		return stringBuilder.toString();
	}

	public String toStringOnlyLetters() {
		return Arrays.toString(actions);
	}
}
