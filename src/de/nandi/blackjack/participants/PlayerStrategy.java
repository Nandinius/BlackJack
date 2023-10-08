package de.nandi.blackjack.participants;

import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Result;
import de.nandi.blackjack.util.Trio;

import java.util.List;

public abstract class PlayerStrategy extends Participant {
	protected final Dealer dealer;
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

	public Trio[] newGame() {
		newHand();
		return strategy(-1);
	}


	/**
	 * @param bet Only used if last action was split.
	 */
	protected abstract Trio[] strategy(int bet);


	/**
	 * Player doesn't take another card and ends their turn.
	 * It's now the dealers turn.
	 */
	@Override
	public Result stand() {
		return dealer.start();
	}

	@Override
	public Result hit() {
		cards.add(deck.drawCard());
		if (deck.countValueBeneficial(cards) > 21)
			return Result.LOST;
		return Result.UNDECIDED;
	}

	public Trio[] split() {
		int playerCard = cards.remove(0);
		cards.add(deck.drawCard());
		Integer[] dealerCards = dealer.cards.toArray(new Integer[0]);
		Trio[] result = strategy(bet);
		cards.clear();
		cards.add(playerCard);
		cards.add(deck.drawCard());
		dealer.cards.clear();
		dealer.cards.addAll(List.of(dealerCards));
		return result;
	}

	public Result doubleDown() {
		bet *= 2;
		if (hit() == Result.LOST)
			return Result.LOST;
		return stand();
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
