package de.nandi.blackjack.probabilities;

import java.util.ArrayList;
import java.util.List;

public class Probabilities {
    public static void main(String[] args) {
        new Probabilities();
    }

    private double[] bustProbabilities;
    private final double probabilityDraw = 4D / 52D;

    public Probabilities() {
        new BestCompleteBasicStrategy();
//        System.out.println(bustEasy(new ArrayList<>(List.of())));
//        bustProbabilities = new double[18];
//        double[] bustProbabilitiesFinal = new double[12];
//        double bust;
//        for (int i = 2; i <= 11; i++) {
//            bustProbabilitiesFinal[i] = bust(new ArrayList<>(List.of(i)));
//        }
    }

    public double bust(ArrayList<Integer> cards) {
        if (countValueBeneficial(cards) > 21)
            return 1;
        if (countValueBeneficial(cards) > 16)
            return 0;
        double bust = 0;
        for (int i = 2; i <= 11; i++) {
            double probabilityDraw = probabilityDraw(cards, i);
            cards.add(i);
            if (probabilityDraw != 0)
                bust += bust(cards) * probabilityDraw;
            cards.remove(cards.size()-1);
        }
        return bust;
    }
    public double bustEasy(ArrayList<Integer> cards) {
        if (countValueBeneficial(cards) > 21)
            return 1;
        if (countValueBeneficial(cards) > 16)
            return 0;
        double bust = 0;
        for (int i = 2; i <= 11; i++) {
            cards.add(i);
            bust += bustEasy(cards) * probabilityDraw * (i==10?4:1);
            cards.remove(cards.size()-1);
        }
        return bust;
    }

    private double probabilityDraw(ArrayList<Integer> cards, int card) {
        if (card == 10)
            return (4D * 4 - cards.stream().filter(integer -> integer == card).count()) / (52D - cards.size());
        return (4D - cards.stream().filter(integer -> integer == card).count()) / (52D - cards.size());
    }

    public int countValueBeneficial(ArrayList<Integer> cards) {
        if (cards.contains(11)) {
            ArrayList<Integer> cardsCopy = new ArrayList<>(cards);
            int sum = cardsCopy.stream().mapToInt(Integer::intValue).sum();
            while (sum > 21)
                if (cardsCopy.remove(Integer.valueOf(11))) {
                    sum -= 10;
                } else
                    break;
            return sum;
        }
        return cards.stream().mapToInt(Integer::intValue).sum();
    }
}
