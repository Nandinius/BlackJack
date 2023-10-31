package de.nandi.blackjack.strategies;

import de.nandi.blackjack.participants.PlayerStrategy;
import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Result;
import de.nandi.blackjack.util.Trio;

public class DealerStrategy extends PlayerStrategy {

	public DealerStrategy(CardDeck deck) {
		super(deck, "Dealer strategy", false);
	}

	@Override
	public Trio[] strategy(int ignored) {
		bet = 100;
		while (deck.countValueBeneficial(cards) < 17) {
			if (hit() == Result.BUST)
				return new Trio[]{new Trio(bet, Result.BUST, 0)};
		}
		return new Trio[]{new Trio(bet, stand(), 0)};
	}
}
