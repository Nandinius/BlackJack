package de.nandi.blackjack;

import de.nandi.blackjack.participants.PlayerStrategy;
import de.nandi.blackjack.strategies.DealerStrategyCounting;
import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Trio;

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
        testStrategy(new DealerStrategyCounting(new CardDeck(1)), true);
        testStrategy(new DealerStrategyCounting(new CardDeck(1)), true);
        for (int i = 10; i > 0; i--) {
            //testStrategy(new DealerStrategyCounting(new CardDeck(i)), false);
        }
        //dealerStrategy();
        //formatPercentage(0.298, 0.110, 0.104, 0.488, 1, 10000000, "dealer");
    }


    public void testStrategy(PlayerStrategy playerStrategy, boolean saveToFile) {
        int games = 10000000;
        double bJWins = 0;
        double wins = 0;
        double draws = 0;
        double losses = 0;
        double gains = 0;
        double winsWithPositiveCount = 0;
        double bJWinsWPC = 0;
        double drawsWPC = 0;
        double lossesWPC = 0;
        double gainsWPC = 0;
        int gamesWPC = 0;
        for (int i = 0; i < games; i++) {
            Trio result = playerStrategy.strategy();
            switch (result.getResult()) {
                case BJ_WIN -> {
                    if (result.getTrueCount() > 1)
                        bJWinsWPC++;
                    bJWins++;
                }
                case WIN -> {
                    if (result.getTrueCount() > 1)
                        winsWithPositiveCount++;
                    wins++;
                }
                case DRAW -> {
                    if (result.getTrueCount() > 1)
                        drawsWPC++;
                    draws++;
                }
                case LOST -> {
                    if (result.getTrueCount() > 1)
                        lossesWPC++;
                    losses++;
                }
            }
            gains += result.getGain();
            if (result.getTrueCount() > 1)
                gainsWPC += result.getGain();
            if (result.getTrueCount() > 1)
                gamesWPC++;
        }
//        File result = formatPercentage(wins / games, bJWins / games, draws / games, losses / games,
//                gain / games, 1, games,
//                playerStrategy.getDecks(), playerStrategy.getName(), playerStrategy.isCardCounting(), saveToFile);
//        if (playerStrategy.isCardCounting())
//            formatPercentageCC(result, winsWithPositiveCount, bJWinsWPC, drawsWPC,lossesWPC,gamesWPC,games,
//                    1, playerStrategy.getDecks(), playerStrategy.getName(), playerStrategy.isCardCounting(),saveToFile );

        String strategyName = playerStrategy.getName() + (playerStrategy.isCardCounting() ? " with CC" : "");
        File result = new File("D:\\nandi\\Desktop\\Programieren\\Workspace\\BlackJack\\src\\de\\nandi\\blackjack\\results",
                strategyName + " " + playerStrategy.getDecks() + " Decks " +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH_mm_ss")) + ".txt");

        StringBuilder resultText = new StringBuilder();
        resultText.append("--- ").append(strategyName).append(" ---\n").
                append(playerStrategy.getDecks()).append(" Decks\n").
                append(games).append(" simulated Games\n");
        formatPercentages(wins, bJWins, draws, losses, games, gains, resultText);
        if (playerStrategy.isCardCounting()) {
            resultText.append("\n-Statistics with Positive Count-\n");
            resultText.append(gamesWPC).append(" simulated Games\n");
            formatPercentages(winsWithPositiveCount, bJWinsWPC, drawsWPC, lossesWPC, gamesWPC, gainsWPC, resultText);
            resultText.append("-Statistics with <1 Count-\n");
            resultText.append(games - gamesWPC).append(" simulated Games\n");
            formatPercentages(wins - winsWithPositiveCount, bJWins - bJWinsWPC, draws - drawsWPC,
                    losses - lossesWPC, games - gamesWPC, gains - gainsWPC, resultText);
        }
        System.out.println(resultText);

        if (!saveToFile)
            return;
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
            writer.write(resultText.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void formatPercentages(double wins, double bJWins, double draws, double losses, double games, double gains,
                                   StringBuilder resultText) {
        String format = "%." + 2 + "f";//precision
        resultText.append(String.format(Locale.ENGLISH, format + "%% win \n", wins / games * 100));
        resultText.append(String.format(Locale.ENGLISH, format + "%% Blackjack win \n", bJWins / games * 100));
        resultText.append(String.format(Locale.ENGLISH, format + "%% draw \n", draws / games * 100));
        resultText.append(String.format(Locale.ENGLISH, format + "%% loss \n", losses / games * 100));
        resultText.append(String.format(Locale.ENGLISH, "Average gain (μ): " + format + "%%\n", gains / games));
    }


}