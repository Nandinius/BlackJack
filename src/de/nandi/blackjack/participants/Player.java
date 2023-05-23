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
		if(deck.countValueBeneficial(cards)==21)
			return stand();
		while (deck.countValueBeneficial(cards) < 17) {
			if(hits() == -1)
				return -1;
		}
		return stand();
	}

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
