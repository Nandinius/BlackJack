package de.nandi.blackjack.participants;

import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Result;

import java.util.ArrayList;

public abstract class Participant {
	protected final ArrayList<Integer> cards;
	protected final CardDeck deck;

	protected Participant(CardDeck deck) {
		this.deck = deck;
		cards = new ArrayList<>();
	}

	/**
	 * Participant doesn't take another card and ends their turn.
	 *
	 * @return result for Player
	 * @see Result
	 */
	public abstract Result stand();

	/**
	 * Participant takes another card.
	 *
	 * @return result for Player
	 * @see Result
	 */
	public abstract Result hit();
}
