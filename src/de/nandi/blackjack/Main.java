package de.nandi.blackjack;

import de.nandi.blackjack.participants.Player;

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
        dealerStrategy();
    }

    public void dealerStrategy() {
        Player player = new Player(new CardDeck(6));
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
        formatPercentage(wins / games, bJWins / games, draws / games, losses / games, 1,
                games, "dealer");
    }

    public void formatPercentage(double winPercent, double bjWinPercent, double drawPercent, double lossPercent, int precision,
                                 int games, String strategy) {
        File result = new File("D:\\nandi\\Desktop\\Programieren\\Workspace\\BlackJack\\src\\de\\nandi\\blackjack\\results",
                strategy + " strategy result " +
                        LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH_mm_ss")) + ".txt");
        String format = "%." + precision + "f";
        String win = String.format(Locale.ENGLISH, format + "%% win percentage\n", winPercent * 100);
        String bj = String.format(Locale.ENGLISH, format + "%% Blackjack win percentage\n", bjWinPercent * 100);
        String draw = String.format(Locale.ENGLISH, format + "%% draw percentage\n", drawPercent * 100);
        String loss = String.format(Locale.ENGLISH, format + "%% loss percentage\n", lossPercent * 100);
        double averageGain = winPercent + 1.5 * bjWinPercent - lossPercent;
        String gain = String.format(Locale.ENGLISH, "Average gain with betting 100: " + format + "\n", averageGain * 100);
        System.out.println(strategy + " strategy");
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