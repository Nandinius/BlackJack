package de.nandi.blackjack.strategies;

import de.nandi.blackjack.participants.PlayerStrategy;
import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Result;
import de.nandi.blackjack.util.Trio;

public class DealerStrategyNoCounting extends PlayerStrategy {

	public DealerStrategyNoCounting(CardDeck deck) {
		super(deck, "DealerStrategy no CC", true);
	}

	@Override
	public Trio[] strategy(int ignored) {
		int trueCount = deck.getBetCount();
		bet = 100 ;
		while (deck.countValueBeneficial(cards) < 17) {
			if (hit() == Result.BUST)
				return new Trio[]{new Trio(bet, Result.BUST, trueCount)};
		}
		return new Trio[]{new Trio(bet, stand(), trueCount)};
	}
}
