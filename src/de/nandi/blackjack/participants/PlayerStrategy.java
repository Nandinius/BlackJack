package de.nandi.blackjack.participants;

import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Duo;
import de.nandi.blackjack.util.Result;

public abstract class PlayerStrategy extends Participant {
	final Dealer dealer;
	protected int bet;
	private final String name;

	public PlayerStrategy(CardDeck deck, String name) {
		super(deck);
		this.name = name;
		deck.wasShuffled();
		dealer = new Dealer(deck, this);
		newHand();
	}

	protected void newHand() {
		cards.clear();
		cards.add(deck.drawCard());
		cards.add(deck.drawCard());
		dealer.cards.clear();
		dealer.cards.add(deck.drawCard());
		dealer.cards.add(deck.drawCard());
	}

	public abstract Duo strategy();


	/**
	 * Player doesn't take another card and ends their turn.
	 * It's now the dealers turn.
	 */
	@Override
	public Result stand() {
		return dealer.start();
	}

	@Override
	public Result hits() {
		cards.add(deck.drawCard());
		if (deck.countValueBeneficial(cards) > 21)
			return Result.LOST;
		return Result.UNDECIDED;
	}

	public String getName() {
		return name;
	}
}
