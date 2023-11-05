package de.nandi.blackjack.strategies;

import de.nandi.blackjack.participants.PlayerStrategy;
import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Result;
import de.nandi.blackjack.util.Trio;

public class DealerStrategyCountingFutureTrueCountInvers extends PlayerStrategy {

	public DealerStrategyCountingFutureTrueCountInvers(CardDeck deck) {
		super(deck, "DealerStrategyFutureTrueCount Invers", true);
	}

	@Override
	public Trio[] strategy(int ignored) {
		int betCount = -deck.getTrueCount();
		if (betCount < 1)
			betCount = 1;
		bet = 100 * betCount;
		while (deck.countValueBeneficial(cards) < 17) {
			if (hit() == Result.BUST)
				return new Trio[]{new Trio(bet, Result.BUST, betCount)};
		}
		return new Trio[]{new Trio(bet, stand(), betCount)};
	}
}
