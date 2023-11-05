package de.nandi.blackjack.strategies;

import de.nandi.blackjack.participants.PlayerStrategy;
import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Result;
import de.nandi.blackjack.util.Trio;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ThorpBasicStrategy extends PlayerStrategy {
	public ThorpBasicStrategy(CardDeck deck) {
		super(deck, "ThorpBasicStrategy", false);

	}

	//@formatter:off
    Map<String, String[]> map = Stream.of(new Object[][][]{
			//			  2    3    4    5    6    7	8	 9	  10   A
            {{"8"},     {"h", "h", "h", "d", "d", "h", "h", "h", "h", "h",}},
            {{"9"},     {"d", "d", "d", "d", "d", "h", "h", "h", "h", "h",}},
            {{"10"},    {"d", "d", "d", "d", "d", "d", "d", "d", "h", "h",}},
            {{"11"},    {"d", "d", "d", "d", "d", "d", "d", "d", "d", "d",}},
            {{"12"},    {"h", "h", "s", "s", "s", "h", "h", "h", "h", "h",}},
            {{"13"},    {"s", "s", "s", "s", "s", "h", "h", "h", "h", "h",}},
            {{"14"},    {"s", "s", "s", "s", "s", "h", "h", "h", "h", "h",}},
            {{"15"},    {"s", "s", "s", "s", "s", "h", "h", "h", "h", "h",}},
            {{"16"},    {"s", "s", "s", "s", "s", "h", "h", "h", "h", "h",}},
            {{"A2"},    {"h", "h", "d", "d", "d", "h", "h", "h", "h", "h",}},
            {{"A3"},    {"h", "h", "d", "d", "d", "h", "h", "h", "h", "h",}},
            {{"A4"},    {"h", "h", "d", "d", "d", "h", "h", "h", "h", "h",}},
            {{"A5"},    {"h", "h", "d", "d", "d", "h", "h", "h", "h", "h",}},
            {{"A6"},    {"d", "d", "d", "d", "d", "h", "h", "h", "h", "h",}},
            {{"A7"},    {"s", "d", "d", "d", "d", "s", "s", "h", "h", "s",}},
            {{"A8"},    {"s", "s", "s", "s", "s", "s", "s", "s", "s", "s",}},
            {{"A9"},    {"s", "s", "s", "s", "s", "s", "s", "s", "s", "s",}},
            {{"A10"},   {"s", "s", "s", "s", "s", "s", "s", "s", "s", "s",}},
            {{"2 2"},   {"p", "p", "p", "p", "p", "p", "h", "h", "h", "h",}},
            {{"3 3"},   {"p", "p", "p", "p", "p", "p", "h", "h", "h", "h",}},
            {{"4 4"},   {"h", "h", "h", "p", "d", "h", "h", "h", "h", "h",}},
            {{"5 5"},   {"d", "d", "d", "d", "d", "d", "d", "d", "h", "h",}},
            {{"6 6"},   {"p", "p", "p", "p", "p", "p", "h", "h", "h", "h",}},
            {{"7 7"},   {"p", "p", "p", "p", "p", "p", "p", "h", "s", "h",}},
            {{"8 8"},   {"p", "p", "p", "p", "p", "p", "p", "p", "p", "p",}},
            {{"9 9"},   {"p", "p", "p", "p", "p", "s", "p", "p", "s", "s",}},
            {{"10 10"}, {"s", "s", "s", "s", "s", "s", "s", "s", "s", "s",}},
            {{"11 11"}, {"p", "p", "p", "p", "p", "p", "p", "p", "p", "p",}},
    }).collect(Collectors.toMap(data -> (String) data[0][0], data ->
            Arrays.stream(data[1]).map(Object::toString).toArray(String[]::new)));
    //@formatter:on

	@Override
	protected Trio[] strategy(int ignored) {
		ArrayList<Trio> results = new ArrayList<>();
		bet = 100;

		String action = null;
		if (Objects.equals(cards.get(0), cards.get(1)))
			action = map.get(cards.get(0) + " " + cards.get(0))[dealer.openCard() - 2];
		else if (cards.remove(Integer.valueOf(11))) {
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
				results.add(new Trio(bet, result, 0));
				return results.toArray(new Trio[0]);
			}
			if (cards.remove(Integer.valueOf(11))) {
				int additionalValue = deck.countValueBeneficial(cards);
				if (cards.contains(11))
					additionalValue = cards.stream().mapToInt(Integer::intValue).map(oldValue -> oldValue == 11 ? 1 : oldValue).sum();
				if (additionalValue < 11 && additionalValue > 1)
					action = map.get("A" + additionalValue)[dealer.openCard() - 2];
				cards.add(11);
			}
		}
		results.add(new Trio(bet, stand(), 0));
		return results.toArray(new Trio[0]);
	}
}
