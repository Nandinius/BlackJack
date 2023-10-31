package de.nandi.blackjack.probabilities;

import java.util.Arrays;

public class ActionsDoubleDebug {
	private final String[] actions;
	private final double[] expectedValue;
	private final double[] expectedValue2;

	public ActionsDoubleDebug() {
		this.actions = new String[10];
		this.expectedValue = new double[10];
		this.expectedValue2 = new double[10];
	}

	public String[] getActions() {
		return actions;
	}

	public double[] getExpectedValue() {
		return expectedValue;
	}

	public double[] getExpectedValue2() {
		return expectedValue2;
	}

	public boolean isSet(int index) {
		return expectedValue[index] != 0;
	}

	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("{");
		for (int i = 0; i < 10; i++) {
			stringBuilder.append("[").append(actions[i]).append(", ").append(expectedValue[i])
					.append("; ").append(actions[i] == "s" ? "h" : "s").append(", ").append(expectedValue2[i])
					.append("]");
			if (i != 9)
				stringBuilder.append(", ");
		}
		return stringBuilder.toString();
	}

	public String toStringOnlyLetters() {
		return Arrays.toString(actions);
	}
}
