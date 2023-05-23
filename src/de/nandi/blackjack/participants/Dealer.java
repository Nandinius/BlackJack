package de.nandi.blackjack.participants;

import de.nandi.blackjack.CardDeck;

public class Dealer extends Participant {
	private final Player player;

	public Dealer(CardDeck deck, Player player) {
		super(deck);
		this.player = player;
	}

	public double start() {
		while (deck.countValueBeneficial(cards) < 17) {
			double multiplier = hits();
			if (multiplier != 1)
				return multiplier;
		}
		return stand();
	}

	@Override
	public double stand() {
		int sumPlayer = deck.countValueBeneficial(player.cards);
		int sumDealer = deck.countValueBeneficial(cards);
		if(sumPlayer > sumDealer)
			return sumPlayer == 21 ? 2.5 : 2;
		else if(sumPlayer<sumDealer)
			return -1;
		else
			return 1;
	}

	@Override
	public double hits() {
		cards.add(deck.drawCard());
		if (deck.countValueBeneficial(cards) > 21)
			return deck.countValueBeneficial(player.cards) == 21 ? 2.5 : 2;
		return 1;
	}
}
