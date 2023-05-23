package de.nandi.blackjack;

import de.nandi.blackjack.participants.Player;

import java.util.Locale;

public class Main {
	public static void main(String[] args) {
		new Main();
	}


	public Main() {
		dealerStrategy();
	}

	public void dealerStrategy() {
		Player player = new Player(new CardDeck(1));
		double bJWins = 0;
		double wins = 0;
		double draws = 0;
		double losses = 0;
		int games = 10000000;
		for (int i = 0; i < games; i++) {
			double result = player.dealerStrategy();
			if (result == 2)
				wins++;
			else if (result == 2.5)
				bJWins++;
			else if (result == 1)
				draws++;
			else
				losses++;
		}
		formatPercentage(wins / games, bJWins / games, draws / games, losses / games, 1);
	}

	public void formatPercentage(double winPercent, double bjWinPercent, double drawPercent, double lossPercent, int precision) {
		String format = "%." + precision + "f";
		System.out.printf(Locale.ENGLISH, format + "%% win percentage\n", winPercent * 100);
		System.out.printf(Locale.ENGLISH, format + "%% Blackjack win percentage\n", bjWinPercent * 100);
		System.out.printf(Locale.ENGLISH, format + "%% draw percentage\n", drawPercent * 100);
		System.out.printf(Locale.ENGLISH, format + "%% loss percentage\n", lossPercent * 100);
		double averageGain = winPercent + 1.5 * bjWinPercent - lossPercent;
		System.out.printf(Locale.ENGLISH, "Average gain with betting 100: " + format + "\n", averageGain * 100);
	}
}