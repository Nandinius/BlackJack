package de.nandi.blackjack.participants;

import de.nandi.blackjack.CardDeck;

public class Player extends Participant {
	final Dealer dealer;

	public Player(CardDeck deck) {
		super(deck);
		deck.wasShuffled();
		dealer = new Dealer(deck, this);
		newHand();
	}

	private void newHand() {
		cards.clear();
		cards.add(deck.drawCard());
		cards.add(deck.drawCard());
		dealer.cards.clear();
		dealer.cards.add(deck.drawCard());
		dealer.cards.add(deck.drawCard());
	}

	public double dealerStrategy() {
		newHand();
		while (deck.countValueBeneficial(cards) < 17) {
			if(hits() == -1)
				return -1;
		}
		return stand();
	}


	/**
	 * Player doesn't take another card and ends their turn.
	 * It's now the dealers turn.
	 */
	@Override
	public double stand() {
		return dealer.start();
	}

	@Override
	public double hits() {
		cards.add(deck.drawCard());
		if(deck.countValueBeneficial(cards)>21)
			return -1;
		return 1;
	}
}
