package de.nandi.blackjack.participants;

import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Trio;
import de.nandi.blackjack.util.Result;

public abstract class PlayerStrategy extends Participant {
	final Dealer dealer;
	protected int bet;
	private final String name;
	private final boolean cardCounting;

	public PlayerStrategy(CardDeck deck, String name, boolean cardCounting) {
		super(deck);
		this.name = name;
		this.cardCounting = cardCounting;
		deck.wasShuffled();
		dealer = new Dealer(deck, this);
	}

	protected void newHand() {
		cards.clear();
		cards.add(deck.drawCard());
		cards.add(deck.drawCard());
		dealer.cards.clear();
		dealer.cards.add(deck.drawCard());
		dealer.cards.add(deck.drawCard());
	}

	public abstract Trio strategy();


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

	public int getDecks() {
		return deck.getDecks();
	}

	public boolean isCardCounting() {
		return cardCounting;
	}
}
