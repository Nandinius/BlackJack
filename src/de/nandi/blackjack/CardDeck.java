package de.nandi.blackjack;

import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {

	private final ArrayList<Integer> currentCardDeck;
	private final ArrayList<Integer> usedCardDeck;
	private final int decks;
	private boolean shuffled;

	public CardDeck(int decks) {
		this.decks = decks;
		currentCardDeck = new ArrayList<>();
		usedCardDeck = new ArrayList<>();
		initializeCardDeck();
	}

	private void initializeCardDeck(){
		for (int i = 0; i < decks; i++)
			for (int colors = 0; colors < 4; colors++) {
				currentCardDeck.add(11);//Ace
				currentCardDeck.add(10);//King
				currentCardDeck.add(10);//Queen
				currentCardDeck.add(10);//Jack
				for (int numbers = 10; numbers >= 2; numbers--)
					currentCardDeck.add(numbers);//Numbers
			}
		shuffle();
	}

	/**
	 * This method returns the first card of the deck and removes it.
	 * If there are less than 25% of the cards in the deck it will be shuffled.
	 * To test if the deck was shuffled use the method wasShuffled();
	 * @see #wasShuffled
	 * @return
	 */
	public int drawCard(){
		int card = currentCardDeck.remove(0);
		usedCardDeck.add(card);
		return card;
	}

	public boolean wasShuffled(){
		if(shuffled) {
			shuffled = false;
			return true;
		}
		return false;
		return shuffled && !(shuffled = false);
	}

	public void shuffle(){
		currentCardDeck.addAll(usedCardDeck);
		usedCardDeck.clear();
		Collections.shuffle(currentCardDeck);
		shuffled = true;
	}
}
