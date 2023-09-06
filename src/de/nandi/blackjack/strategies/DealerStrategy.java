package de.nandi.blackjack.strategies;

import de.nandi.blackjack.participants.PlayerStrategy;
import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Trio;
import de.nandi.blackjack.util.Result;

public class DealerStrategy extends PlayerStrategy {

	public DealerStrategy(CardDeck deck) {
		super(deck, "Dealer strategy", false);
	}

	@Override
	public Trio strategy() {
		newHand();
		bet = 100;
		while (deck.countValueBeneficial(cards) < 17) {
			if (hits() == Result.LOST)
				return new Trio(bet, Result.LOST, 0);
		}
		return new Trio(bet, stand(), 0);
	}
}
