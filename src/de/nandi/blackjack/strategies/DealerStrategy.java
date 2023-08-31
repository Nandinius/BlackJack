package de.nandi.blackjack.strategies;

import de.nandi.blackjack.participants.PlayerStrategy;
import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Duo;
import de.nandi.blackjack.util.Result;

public class DealerStrategy extends PlayerStrategy {

	public DealerStrategy(CardDeck deck) {
		super(deck, "Dealer strategy");
	}

	@Override
	public Duo strategy() {
		newHand();
		bet = 100;
		while (deck.countValueBeneficial(cards) < 17) {
			if (hits() == Result.LOST)
				return new Duo(bet, Result.LOST);
		}
		return new Duo(bet, stand());
	}
}
