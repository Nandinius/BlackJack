package de.nandi.blackjack.participants;

import de.nandi.blackjack.CardDeck;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class PlayerTest {

	@Test
	void hits() {
		Player player = new Player(new CardDeck(1));
		player.cards.clear();
		player.cards.addAll(Arrays.asList(10,10,11));
		assertEquals(-1,player.hits());
		player.cards.clear();
		player.cards.add(10);
		assertEquals(1,player.hits());
	}
}