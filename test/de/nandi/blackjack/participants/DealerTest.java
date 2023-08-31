package de.nandi.blackjack.participants;

import de.nandi.blackjack.strategies.DealerStrategy;
import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Result;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DealerTest {

	@Test
	void stand() {
		PlayerStrategy playerStrategy = new DealerStrategy(new CardDeck(1));
		Dealer dealer = playerStrategy.dealer;
		playerStrategy.cards.clear();
		playerStrategy.cards.addAll(Arrays.asList(10, 11));
		dealer.cards.clear();
		dealer.cards.addAll(Arrays.asList(10, 11));
		assertEquals(Result.DRAW, dealer.stand()); //Draw because of same number
		playerStrategy.cards.clear();
		playerStrategy.cards.addAll(Arrays.asList(10, 8, 11));
		dealer.cards.clear();
		dealer.cards.addAll(Arrays.asList(10, 7, 11));
		assertEquals(Result.WIN, dealer.stand());//Player wins because he has a higher number
		playerStrategy.cards.clear();
		playerStrategy.cards.addAll(Arrays.asList(10, 11));
		dealer.cards.clear();
		dealer.cards.addAll(Arrays.asList(10, 8));
		assertEquals(Result.BJ_WIN, dealer.stand());//Player wins with blackjack
		playerStrategy.cards.clear();
		playerStrategy.cards.addAll(Arrays.asList(10, 8));
		dealer.cards.clear();
		dealer.cards.addAll(Arrays.asList(10, 11));
		assertEquals(Result.LOST, dealer.stand());//Player looses because of lower number
	}

	@Test
	void hits() {
		PlayerStrategy playerStrategy = new DealerStrategy(new CardDeck(1));
		Dealer dealer = playerStrategy.dealer;
		playerStrategy.cards.clear();
		playerStrategy.cards.addAll(Arrays.asList(10, 11));
		dealer.cards.clear();
		dealer.cards.addAll(Arrays.asList(10, 10));
		assertEquals(Result.BJ_WIN, dealer.hits());
		playerStrategy.cards.clear();
		playerStrategy.cards.addAll(Arrays.asList(10, 8, 11));
		dealer.cards.clear();
		dealer.cards.addAll(Arrays.asList(10, 10, 11));
		assertEquals(Result.WIN, dealer.hits());
		dealer.cards.clear();
		dealer.cards.add(10);
		assertEquals(Result.DRAW, dealer.hits());
	}
}