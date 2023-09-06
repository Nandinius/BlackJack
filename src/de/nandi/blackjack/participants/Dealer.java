package de.nandi.blackjack.participants;

import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Result;

public class Dealer extends Participant {
	private final PlayerStrategy playerStrategy;

	public Dealer(CardDeck deck, PlayerStrategy playerStrategy) {
		super(deck);
		this.playerStrategy = playerStrategy;
	}

	/**
	 * It's now the dealers turn.
	 * Starts with the standard strategy for the dealer with standing on a soft 17.
	 *
	 * @return result for Player
	 * @see Result
	 */
	public Result start() {
		while (deck.countValueBeneficial(cards) < 17) {
			Result result = hits();
			if (result != Result.UNDECIDED)
				return result;
		}
		return stand();
	}

	/**
	 * Dealer doesn't take another card and ends their turn.
	 * Method calculates who wins and how much.
	 *
	 * @return result for Player
	 * @see Result
	 */
	@Override
	public Result stand() {
		int sumPlayer = deck.countValueBeneficial(playerStrategy.cards);
		int sumDealer = deck.countValueBeneficial(cards);
		if (sumPlayer > sumDealer)
			return sumPlayer == 21 ? Result.BJ_WIN : Result.WIN;
		else if (sumPlayer < sumDealer)
			return Result.LOST;
		else
			return Result.DRAW;
	}

	/**
	 * Dealer hits and may have lost.
	 *
	 * @return Result for Player
	 * @see Result
	 */
	@Override
	public Result hits() {
		cards.add(deck.drawCard());
		if (deck.countValueBeneficial(cards) > 21)
			return deck.countValueBeneficial(playerStrategy.cards) == 21 ? Result.BJ_WIN : Result.WIN;
		return Result.UNDECIDED;
	}

	public int openCard(){
		return cards.get(0);
	}
}
