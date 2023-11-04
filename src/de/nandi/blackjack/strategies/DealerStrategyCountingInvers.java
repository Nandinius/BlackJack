package de.nandi.blackjack.strategies;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import de.nandi.blackjack.participants.PlayerStrategy;
import de.nandi.blackjack.util.CardDeck;
import de.nandi.blackjack.util.Result;
import de.nandi.blackjack.util.Trio;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DealerStrategyCountingInvers extends PlayerStrategy {

	public DealerStrategyCountingInvers(CardDeck deck) {
		super(deck, "DealerStrategy Invers", true);
	}

	@Override
	public Trio[] strategy(int ignored) {
		int betCount = -deck.getBetCount();
		if (betCount < 1)
			betCount = 1;
		bet = 100 * betCount;
		while (deck.countValueBeneficial(cards) < 17) {
			if (hit() == Result.BUST)
				return new Trio[]{new Trio(bet, Result.BUST, betCount)};
		}
		return new Trio[]{new Trio(bet, stand(), betCount)};
	}
}
