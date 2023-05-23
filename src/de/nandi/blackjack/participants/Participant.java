package de.nandi.blackjack.participants;

import de.nandi.blackjack.CardDeck;

import java.util.ArrayList;

public abstract class Participant {
	final ArrayList<Integer> cards;
	final CardDeck deck;

	protected Participant(CardDeck deck) {
		this.deck = deck;
		cards = new ArrayList<>();
	}

	/**
	 * Participant doesn't take another card and ends their turn.
	 * @return profit multipler for player. Could be 2(won), 2.5(won with blackjack), -1(lost), 1(undecided/draw).
	 */
	public abstract double stand();

	/**
	 * Participant takes another card.
	 * @return profit multipler for player. Could be 2(won), 2.5(won with blackjack), -1(lost), 1(undecided/draw).
	 */
	public abstract double hits();
}
