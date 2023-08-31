package de.nandi.blackjack.strategies;

import de.nandi.blackjack.participants.PlayerStrategy;
import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Duo;
import de.nandi.blackjack.util.Result;

public class DealerStrategyCounting extends PlayerStrategy {

	public DealerStrategyCounting(CardDeck deck) {
		super(deck, "Dealer Strategy with Card Counting");
	}

	@Override
	public Duo strategy() {
		newHand();
		int betCount = deck.getRunningCount() / deck.getDecks();
		if (betCount < 1)
			betCount = 1;
		bet = 100 * betCount;
		while (deck.countValueBeneficial(cards) < 17) {
			if (hits() == Result.LOST)
				return new Duo(bet, Result.LOST);
		}
		return new Duo(bet, stand());
	}
}
