package de.nandi.blackjack.strategies;

import de.nandi.blackjack.participants.PlayerStrategy;
import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Result;
import de.nandi.blackjack.util.Trio;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class BestStrategysRemoved10s extends PlayerStrategy {

	public BestStrategysRemoved10s(CardDeck deck) {
		super(deck, "BestStrategysRemoved10s", false);
		getMap();
	}

	private Map<String, String[]> map;

	private void getMap() {
		File result = new File("D:\\nandi\\Desktop\\Programieren\\Workspace\\Blackjack\\src\\de\\nandi\\blackjack\\probabilities\\resultsPC\\" +
				"BestEasyBasicStrategy RemovedTens PC" + deck.getRemovedTens() + ".txt");
		map = new HashMap<>();
		try (Scanner reader = new Scanner(result)) {
			reader.useDelimiter("\\n");
			for (int i = 0; i < 36; i++) {
				String s = reader.next();
				map.put(s.split(";")[0],s.split(";")[1].split(","));
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

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
