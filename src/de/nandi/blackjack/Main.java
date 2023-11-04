package de.nandi.blackjack;

import de.nandi.blackjack.participants.PlayerStrategy;
import de.nandi.blackjack.strategies.DealerStrategy;
import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Trio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Main {
	public static void main(String[] args) {
		new Main();
	}


	public Main() {
		for (int i = 1; i <= 1; i++) {
			if (i != 1 && i != 4 && i != 8 && i != 1000) continue;
			testStrategy(new DealerStrategy(new CardDeck(i)), true);
//			testStrategy(new DealerStrategyCounting(new CardDeck(i)), true);
//			testStrategy(new DealerStrategyCountingInvers(new CardDeck(i)), true);
//			testStrategy(new MathematicalEasyBasicStrategy(new CardDeck(i)), true);
//			testStrategy(new MathematicalEasyBasicStrategyCounting(new CardDeck(i)), true);
//			testStrategy(new MathematicalEasyBasicStrategyCountingInvers(new CardDeck(i)), true);
//			testStrategy(new MathematicalEasyBasicStrategyCountingExceptions(new CardDeck(i)), true);
//			testStrategy(new ThorpBasicStrategy(new CardDeck(i)), true);
//			testStrategy(new ThorpBasicStrategyCounting(new CardDeck(i)), true);
//			testStrategy(new ThorpBasicStrategyCountingInverse(new CardDeck(i)), true);
//			testStrategy(new EvolvedBasicStrategy(new CardDeck(i)), true);
//			testStrategy(new EvolvedBasicStrategyCounting(new CardDeck(i)), true);
//			testStrategy(new EvolvedBasicStrategyCountingInvers(new CardDeck(i)), true);
//			testStrategy(new EvolvedBasicStrategyCountingExceptions(new CardDeck(i)), true);
//			testStrategy(new EvolvedBasicStrategyCountingExceptionsInvers(new CardDeck(i)), true);
//			testStrategy(new EvolvedBasicStrategyCountingInversExceptions(new CardDeck(i)), true);
		}
//		for (int i = 16; i > 0; i--) {
//			testStrategy(new BestStrategysRemoved10s(new CardDeck(1, i)), true);
//		}
//		for (int i = 1; i <= 20; i++) {
//			testStrategy(new DealerStrategy(new CardDeck(1, -i)), true);
//		}
//		for (int i = 2; i <= 11; i++) {
//			testStrategy(new MathematicalEasyBasicStrategy(new CardDeck(1,-i)), true);
//		}
		//dealerStrategy();
		//formatPercentage(0.298, 0.110, 0.104, 0.488, 1, 10000000, "dealer");
	}


	public void testStrategy(PlayerStrategy playerStrategy, boolean saveToFile) {
		int newGames = 1000000 * 100;
		double bJWins = 0;
		double wins = 0;
		double draws = 0;
		double losses = 0;
		double busts = 0;
		double gains = 0;
		int games = 0;
		double winsWithPositiveCount = 0;
		double bJWinsWPC = 0;
		double drawsWPC = 0;
		double lossesWPC = 0;
		double bustsWPC = 0;
		double gainsWPC = 0;
		int gamesWPC = 0;
		int positivCount = 1;
		for (int i = 0; i < newGames; i++)
			for (Trio result : playerStrategy.newGame()) {
				switch (result.getResult()) {
					case BJ_WIN -> {
						if (result.getTrueCount() > positivCount)
							bJWinsWPC++;
						bJWins++;
					}
					case WIN -> {
						if (result.getTrueCount() > positivCount)
							winsWithPositiveCount++;
						wins++;
					}
					case DRAW -> {
						if (result.getTrueCount() > positivCount)
							drawsWPC++;
						draws++;
					}
					case LOST -> {
						if (result.getTrueCount() > positivCount)
							lossesWPC++;
						losses++;
					}
					case BUST -> {
						if (result.getTrueCount() > positivCount)
							lossesWPC++;
						losses++;
						if (result.getTrueCount() > positivCount)
							bustsWPC++;
						busts++;
					}
				}
				gains += result.getGain();
				if (result.getTrueCount() > positivCount)
					gainsWPC += result.getGain();
				if (result.getTrueCount() > positivCount)
					gamesWPC++;
				games++;
			}
//        File result = formatPercentage(wins / newGames, bJWins / newGames, draws / newGames, losses / newGames,
//                gain / newGames, 1, newGames,
//                playerStrategy.getDecks(), playerStrategy.getName(), playerStrategy.isCardCounting(), saveToFile);
//        if (playerStrategy.isCardCounting())
//            formatPercentageCC(result, winsWithPositiveCount, bJWinsWPC, drawsWPC,lossesWPC,gamesWPC,newGames,
//                    1, playerStrategy.getDecks(), playerStrategy.getName(), playerStrategy.isCardCounting(),saveToFile );

		String strategyName = playerStrategy.getName() + (playerStrategy.isCardCounting() ? " with CC" : "");
		File result = new File("D:\\nandi\\Desktop\\Programieren\\Workspace\\BlackJack\\src\\de\\nandi\\blackjack" +
				"\\results\\strategies\\" + strategyName, playerStrategy.getDecks() + " Decks " +
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH_mm_ss")) + ".txt");

		StringBuilder resultText = new StringBuilder();
		resultText.append("--- ").append(strategyName).append(" ---\n").
				append(playerStrategy.getDecks()).append(" Decks\n").
				append(newGames).append(" started Games\n").
				append(games).append(" simulated Games\n");
		formatPercentages(wins, bJWins, draws, losses, busts, games, gains, resultText);
		if (playerStrategy.getRemovedTens() != 0)
			resultText.append("missing 10s: ").append(playerStrategy.getRemovedTens()).append("\n");
		if (playerStrategy.isCardCounting()) {
			resultText.append("\n-Statistics with Positive Count-\n");
			resultText.append(gamesWPC).append(" simulated Games\n");
			formatPercentages(winsWithPositiveCount, bJWinsWPC, drawsWPC, lossesWPC, bustsWPC,
					gamesWPC, gainsWPC, resultText);
			resultText.append("-Statistics with <1 Count-\n");
			resultText.append(games - gamesWPC).append(" simulated Games\n");
			formatPercentages(wins - winsWithPositiveCount, bJWins - bJWinsWPC, draws - drawsWPC,
					losses - lossesWPC, busts - bustsWPC, games - gamesWPC, gains - gainsWPC, resultText);
		}
		System.out.println(resultText);

		if (!saveToFile)
			return;
		try {
			new File(result.getParent()).mkdirs();
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
			writer.write(resultText.toString());
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private void formatPercentages(double wins, double bJWins, double draws, double losses,
								   double busts, double games, double gains,
								   StringBuilder resultText) {
		String format = "%." + 2 + "f";//precision 2 best
		resultText.append(String.format(Locale.ENGLISH, format + "%% win \n", wins / games * 100));
		resultText.append(String.format(Locale.ENGLISH, format + "%% Blackjack win \n", bJWins / games * 100));
		resultText.append(String.format(Locale.ENGLISH, format + "%% draw \n", draws / games * 100));
		resultText.append(String.format(Locale.ENGLISH, format + "%% loss \n", losses / games * 100));
		resultText.append(String.format(Locale.ENGLISH, format + "%% bust \n", busts / games * 100));

		format = "%." + 3 + "f";//precision 3 best
		resultText.append(String.format(Locale.ENGLISH, "Average gain (μ): " + format, gains / games));
		resultText.append("% (");
		BigDecimal gain = new BigDecimal(gains).divide(new BigDecimal(games), MathContext.UNLIMITED)
				.divide(new BigDecimal(100), MathContext.UNLIMITED);
		BigDecimal deviationBig = new BigDecimal(0)
				.add(new BigDecimal(-1).subtract(gain).pow(2, MathContext.UNLIMITED)
						.multiply(new BigDecimal(losses), MathContext.UNLIMITED))
				.add(new BigDecimal(1).subtract(gain).pow(2, MathContext.UNLIMITED)
						.multiply(new BigDecimal(wins), MathContext.UNLIMITED))
				.add(new BigDecimal("1.5").subtract(gain).pow(2, MathContext.UNLIMITED)
						.multiply(new BigDecimal(bJWins), MathContext.UNLIMITED))
				.divide(new BigDecimal(games).pow(2, MathContext.UNLIMITED), MathContext.UNLIMITED)
				.sqrt(MathContext.DECIMAL128);

		resultText.append(gain.subtract(deviationBig.multiply(new BigDecimal(2))).movePointRight(2)
				.setScale(3, RoundingMode.HALF_UP).toPlainString()).append("%; ");
		resultText.append(gain.add(deviationBig.multiply(new BigDecimal(2))).movePointRight(2)
				.setScale(3, RoundingMode.HALF_UP).toPlainString()).append("%)\n");
//		resultText.append("Standard deviation (σ): ").append(deviationBig.toPlainString()).append("\n");
	}


}