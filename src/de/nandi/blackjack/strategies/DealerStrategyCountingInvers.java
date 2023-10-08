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

	private final ArrayList<Integer> bets = new ArrayList<>();
	private final ArrayList<Integer> count = new ArrayList<>();

	public DealerStrategyCountingInvers(CardDeck deck) {
		super(deck, "Dealer Strategy Invers", true);
	}

	@Override
	public Trio[] strategy(int ignored) {
		int betCount = -deck.getTrueCount();
		count.add(betCount);
		if (betCount < 1)
			betCount = 1;
		bet = 100 * betCount;
		bets.add(bet);
		while (deck.countValueBeneficial(cards) < 17) {
			if (hit() == Result.LOST)
				return new Trio[]{new Trio(bet, Result.LOST, betCount)};
		}
		return new Trio[]{new Trio(bet, stand(), betCount)};
	}

	public void saveExcel() throws Exception {
		Workbook workbook = new Workbook();
		Worksheet sheet = workbook.getWorksheets().get(0);
		sheet.getCells().importArrayList(count, 0, 0, true);
		sheet.getCells().importArrayList(bets, 0, 1, true);
		File file = new File("D:\\nandi\\Desktop\\Programieren\\Workspace\\BlackJack\\src\\de\\nandi\\" +
				"blackjack\\results\\excel", getName() + (isCardCounting() ? " with Card Counting " : " ")
				+ " Excel " + deck.getDecks() + " Decks " +
				LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd-HH_mm_ss")) + ".xlsx");

		workbook.save(file.getAbsolutePath());
	}
}
