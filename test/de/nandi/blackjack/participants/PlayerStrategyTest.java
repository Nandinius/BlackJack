package de.nandi.blackjack.participants;

import de.nandi.blackjack.strategies.DealerStrategy;
import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Result;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PlayerStrategyTest {

	@Test
	void hits() {
		PlayerStrategy playerStrategy = new DealerStrategy(new CardDeck(1));
		playerStrategy.cards.clear();
		playerStrategy.cards.addAll(Arrays.asList(10, 10, 11));
		assertEquals(Result.LOST, playerStrategy.hit());
		playerStrategy.cards.clear();
		playerStrategy.cards.add(10);
		assertEquals(Result.DRAW, playerStrategy.hit());
	}
}