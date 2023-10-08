package de.nandi.blackjack.strategies;

import de.nandi.blackjack.participants.PlayerStrategy;
import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Result;
import de.nandi.blackjack.util.Trio;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EvolvedBasicStrategyCountingExceptions extends PlayerStrategy {
	public EvolvedBasicStrategyCountingExceptions(CardDeck deck) {
		super(deck, "EvolvedBasicStrategy with Exceptions", true);

	}

	//@formatter:off
    Map<String, String[]> map = Stream.of(new Object[][][]{
            {{"8"},     {"h", "h", "h", "d", "d", "h", "h", "h", "h", "h"}},
            {{"9"},     {"d", "d", "d", "d", "d", "h", "h", "h", "h", "h"}},
            {{"10"},    {"d", "d", "d", "d", "d", "d", "d", "d", "h", "h"}},
            {{"11"},    {"d", "d", "d", "d", "d", "d", "d", "d", "d", "d"}},
            {{"12"},    {"h", "h", "s", "s", "s", "h", "h", "h", "h", "h"}},
            {{"13"},    {"s", "s", "s", "s", "s", "h", "h", "h", "h", "h"}},
            {{"14"},    {"s", "s", "s", "s", "s", "h", "h", "h", "h", "h"}},
            {{"15"},    {"s", "s", "s", "s", "s", "h", "h", "h", "h", "h"}},
            {{"16"},    {"s", "s", "s", "s", "s", "h", "h", "h", "h", "h"}},
            {{"A2"},    {"h", "h", "d", "d", "d", "h", "h", "h", "h", "h"}},
            {{"A3"},    {"h", "h", "d", "d", "d", "h", "h", "h", "h", "h"}},
            {{"A4"},    {"h", "h", "d", "d", "d", "h", "h", "h", "h", "h"}},
            {{"A5"},    {"h", "h", "d", "d", "d", "h", "h", "h", "h", "h"}},
            {{"A6"},    {"d", "d", "d", "d", "d", "h", "h", "h", "h", "h"}},
            {{"A7"},    {"s", "d", "d", "d", "d", "s", "s", "h", "h", "s"}},
            {{"A8"},    {"s", "s", "s", "d", "d", "s", "s", "s", "s", "s"}},
            {{"A9"},    {"s", "s", "s", "s", "s", "s", "s", "s", "s", "s"}},
            {{"A10"},   {"s", "s", "s", "s", "s", "s", "s", "s", "s", "s"}},
            {{"2 2"},   {"p", "p", "p", "p", "p", "p", "h", "h", "h", "h"}},
            {{"3 3"},   {"h", "h", "p", "p", "p", "p", "h", "h", "h", "h"}},
            {{"4 4"},   {"h", "h", "h", "d", "d", "h", "h", "h", "h", "h"}},
            {{"5 5"},   {"d", "d", "d", "d", "d", "d", "d", "d", "h", "h"}},
            {{"6 6"},   {"p", "p", "p", "p", "p", "h", "h", "h", "h", "h"}},
            {{"7 7"},   {"p", "p", "p", "p", "p", "p", "h", "h", "s", "h"}},
            {{"8 8"},   {"p", "p", "p", "p", "p", "p", "p", "p", "p", "p"}},
            {{"9 9"},   {"p", "p", "p", "p", "p", "p", "s", "p", "s", "s"}},
            {{"10 10"}, {"s", "s", "s", "s", "s", "s", "s", "s", "s", "s"}},
            {{"11 11"}, {"p", "p", "p", "p", "p", "p", "p", "p", "p", "p"}}
    }).collect(Collectors.toMap(data -> (String) data[0][0], data ->
            Arrays.stream(data[1]).map(Object::toString).toArray(String[]::new)));
    Map<String, String[]> mapCounts = Stream.of(new Object[][][]{
            {{"8 5"},      {"2",  "d", "h"}},
            {{"8 6"},      {"2",  "d", "h"}},
            {{"9 2"},      {"1",  "d", "h"}},
            {{"9 3"},      {"0",  "d", "h"}},
            {{"9 4"},      {"-1", "d", "h"}},
            {{"10 9"},     {"1",  "d", "h"}},
            {{"10 10"},    {"3",  "d", "h"}},
            {{"10 11"},    {"3",  "d", "h"}},
            {{"11 11"},    {"-1", "d", "h"}},
            {{"12 2"},     {"3",  "s", "h"}},
            {{"12 3"},     {"2",  "s", "h"}},
            {{"12 4"},     {"2",  "s", "h"}},
            {{"12 5"},     {"0",  "s", "h"}},
            {{"12 6"},     {"-1", "s", "h"}},
            {{"13 2"},     {"0",  "s", "h"}},
            {{"13 3"},     {"0",  "s", "h"}},
            {{"13 4"},     {"-2", "s", "h"}},
            {{"14 2"},     {"-2", "s", "h"}},
            {{"15 10"},    {"4",  "s", "h"}},
            {{"16 9"},     {"3",  "s", "h"}},
            {{"16 10"},    {"1",  "s", "h"}},
            {{"A2 4"},     {"-1", "d", "h"}},
            {{"A3 4"},     {"-1", "d", "h"}},
            {{"A4 4"},     {"-1", "d", "h"}},
            {{"A5 4"},     {"-1", "d", "h"}},
            {{"A7 11"},    {"0",  "s", "h"}},
            {{"A8 5"},     {"0",  "d", "s"}},
            {{"A8 6"},     {"0",  "d", "s"}},
            {{"44 5"},     {"2",  "d", "h"}},
            {{"44 6"},     {"2",  "d", "h"}},
            {{"77 10"},    {"0",  "s", "h"}},
            {{"99 2"},     {"-2", "p", "s"}}
    }).collect(Collectors.toMap(data -> (String) data[0][0], data ->
            Arrays.stream(data[1]).map(Object::toString).toArray(String[]::new)));
    //@formatter:on

	@Override
	protected Trio[] strategy(int splitBet) {
		ArrayList<Trio> results = new ArrayList<>();
		if (splitBet == -1) {
			int betCount = deck.getTrueCount();
			if (betCount < 1)
				betCount = 1;
			bet = 100 * betCount;
		} else
			bet = splitBet;

		String action = null;
		if (Objects.equals(cards.get(0), cards.get(1))) {
			String[] conditionalAction = mapCounts.get(cards.get(0) + cards.get(0) + " " + dealer.openCard());
			if (conditionalAction != null)
				if (deck.getTrueCount() >= Integer.parseInt(conditionalAction[0]))
					action = conditionalAction[1];
				else action = conditionalAction[2];
			else
				action = map.get(cards.get(0) + " " + cards.get(0))[dealer.openCard() - 2];
		} else if (cards.remove(Integer.valueOf(11))) {
			String[] conditionalAction = mapCounts.get("A" + cards.get(0) + " " + dealer.openCard());
			if (conditionalAction != null)
				if (deck.getTrueCount() >= Integer.parseInt(conditionalAction[0]))
					action = conditionalAction[1];
				else action = conditionalAction[2];
			else
				action = map.get("A" + cards.get(0))[dealer.openCard() - 2];
			cards.add(11);
		}
		int value;
		while ((value = deck.countValueBeneficial(cards)) < 17) {
			if (action == null) {
				if (value <= 7) {
					action = "h";
					continue;
				}
				String[] conditionalAction = mapCounts.get(value + " " + dealer.openCard());
				if (conditionalAction != null)
					if (deck.getTrueCount() >= Integer.parseInt(conditionalAction[0]))
						action = conditionalAction[1];
					else action = conditionalAction[2];
				else
					action = map.get(Integer.toString(value))[dealer.openCard() - 2];
			}
			Result result = Result.UNDECIDED;
			switch (action) {
				case "h" -> result = hit();
				case "s" -> result = stand();
				case "d" -> {
					if (cards.size() == 2)
						result = doubleDown();
					else
						result = hit();
				}
				case "p" -> results.addAll(List.of(split()));
			}
			action = null;
			if (result != Result.UNDECIDED) {
				results.add(new Trio(bet, result, deck.getTrueCount()));
				return results.toArray(new Trio[0]);
			}
		}
		results.add(new Trio(bet, stand(), deck.getTrueCount()));
		return results.toArray(new Trio[0]);
	}
}
