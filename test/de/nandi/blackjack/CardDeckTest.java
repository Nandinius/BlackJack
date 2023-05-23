package de.nandi.blackjack;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class CardDeckTest {

	@Test
	void drawCard() {
		int decks = 6;
		CardDeck deck = new CardDeck(decks);
		deck.wasShuffled();
		for (int i = 0; i < decks * 52D * 0.70; i++) {//Not shuffled in the first 70%
			deck.drawCard();
			assertFalse(deck.wasShuffled(), "Deck should not be shuffled in the first 70%");
		}
		boolean wasShuffled = false;
		for (int i = 0; i < decks * 52D * 0.10; i++) {//Shuffled in the next 10%, between 70% and 80%
			deck.drawCard();
			if (deck.wasShuffled())
				wasShuffled = true;
		}
		assertTrue(wasShuffled, "Deck should be shuffled between 70% and 80%");
	}

	@Test
	void initializeCardDeck() {
		int decks = 6;
		CardDeck deck = new CardDeck(decks);
		int[] cardValues = new int[12];
		for (int i = 0; i < 52 * decks; i++) {
			cardValues[deck.getCurrentCardDeck().get(i)]++;
		}
		assertEquals(4 * decks, cardValues[11]);//Aces
		assertEquals(4 * 4 * decks, cardValues[10]);//King, Queen, Jack, Tens
		for (int numbers = 9; numbers >= 2; numbers--)
			assertEquals(4 * decks, cardValues[numbers]);//Numbers
	}

	@Test
	void wasShuffled() {
		CardDeck deck = new CardDeck(1);
		assertTrue(deck.wasShuffled());
		assertFalse(deck.wasShuffled());
	}

	@Test
	void countValueBeneficial() {
		CardDeck deck = new CardDeck(0);
		assertEquals(15, deck.countValueBeneficial(new ArrayList<>(Arrays.asList(5, 4, 6))));
		assertEquals(16, deck.countValueBeneficial(new ArrayList<>(Arrays.asList(10, 5, 11))));
		assertEquals(17, deck.countValueBeneficial(new ArrayList<>(Arrays.asList(10, 5, 11, 11))));
		assertEquals(22, deck.countValueBeneficial(new ArrayList<>(Arrays.asList(10, 5, 6, 11))));
	}
}