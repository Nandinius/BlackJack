package de.nandi.blackjack;

import de.nandi.blackjack.participants.PlayerStrategy;
import de.nandi.blackjack.strategies.DealerStrategy;
import de.nandi.blackjack.strategies.DealerStrategyCounting;
import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Duo;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Main {
	public static void main(String[] args) {
		new Main();
	}


	public Main() {
		testStrategy(new DealerStrategy(new CardDeck(6)));
		testStrategy(new DealerStrategyCounting(new CardDeck(6)));
		//dealerStrategy();
		//formatPercentage(0.298, 0.110, 0.104, 0.488, 1, 10000000, "dealer");
	}


	public void testStrategy(PlayerStrategy playerStrategy) {
		double bJWins = 0;
		double wins = 0;
		double draws = 0;
		double losses = 0;
		double gain = 0;
		int games = 10000000;
		for (int i = 0; i < games; i++) {
			Duo result = playerStrategy.strategy();
			switch (result.getResult()) {
				case BJ_WIN -> bJWins++;
				case WIN -> wins++;
				case DRAW -> draws++;
				case LOST -> losses++;
			}
			gain += result.getGain();
		}
		formatPercentage(wins / games, bJWins / games, draws / games, losses / games,
				gain / games, 1, games, playerStrategy.getName());
	}

	public void formatPercentage(double winPercent, double bjWinPercent, double drawPercent, double lossPercent, double averageGain,
								 int precision, int games, String strategy) {
		File result = new File("D:\\nandi\\Desktop\\Programieren\\Workspace\\BlackJack\\src\\de\\nandi\\blackjack\\results",
				strategy + " result " +
						LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH_mm_ss")) + ".txt");
		String format = "%." + precision + "f";
		String win = String.format(Locale.ENGLISH, format + "%% win percentage\n", winPercent * 100);
		String bj = String.format(Locale.ENGLISH, format + "%% Blackjack win percentage\n", bjWinPercent * 100);
		String draw = String.format(Locale.ENGLISH, format + "%% draw percentage\n", drawPercent * 100);
		String loss = String.format(Locale.ENGLISH, format + "%% loss percentage\n", lossPercent * 100);
		//if(averageGain == Double.)
		//double averageGain = winPercent + 1.5 * bjWinPercent - lossPercent;
		String gain = String.format(Locale.ENGLISH, "Average gain (μ): " + format + "\n", averageGain);
		System.out.println("--- " + strategy + " ---");
		System.out.println(games + " simulated Games");
		System.out.println(win + bj + draw + loss + gain);
		try {
			if (!result.createNewFile()) {
				System.out.println("Could not save File because it already exists.");
				return;
			}
		} catch (IOException e) {
			System.out.println("Could not save File.");
			e.printStackTrace();
			return;
		}
		try (FileWriter writer = new FileWriter(result)) {
			writer.write(strategy + " strategy");
			writer.write("\n" + games + " simulated Games");
			writer.write("\n" + win + bj + draw + loss + gain);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}