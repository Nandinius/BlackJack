package de.nandi.blackjack.probabilities;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BestEasyBasicStrategyRemovedTens {

	private final Map<String, ActionsDouble> map;
	private final Map<String, ActionsDoubleDebug> mapDebug = new HashMap<>();
	private final double probabilityDraw = 4D / 52D;
	private final int removedTens;

//    private final Map<String, Integer> performance = Stream.of(new Object[][]{
//            {"16", 16},
//            {"17..", 17},
//            {"16", 16},
//    }).collect(Collectors.toMap(data -> (String) data[0], data -> (Integer) data[1]));


	public BestEasyBasicStrategyRemovedTens(int removedTens) {
		this.removedTens = removedTens;
		this.map = new TreeMap<>((o1, o2) -> {
			//if return = -1 dann o1 vor o2
			if (o1.startsWith("A") || o2.startsWith("A")) {
				if (o1.startsWith("A") && o2.startsWith("A"))
					return Integer.compare(Integer.parseInt(o1.substring(1)), Integer.parseInt(o2.substring(1)));
				if (o1.startsWith("A"))
					if (o2.contains(" "))
						return -1;
					else
						return 1;
				else {
					if (o1.contains(" "))
						return 1;
					else
						return -1;
				}
			}
			if (o1.contains(" ") || o2.contains(" ")) {
				if (o1.contains(" ") && o2.contains(" "))
					return Integer.compare(Integer.parseInt(o1.split(" ")[0]), Integer.parseInt(o2.split(" ")[0]));
				if (o1.contains(" "))
					return 1;
				return -1;
			}
			return Integer.compare(Integer.parseInt(o1), Integer.parseInt(o2));
		});
//		this.map = new HashMap<>();
		for (int i = 5; i <= 21; i++) {
			map.put(String.valueOf(i), new ActionsDouble());
		}
		for (int b = 10; b >= 2; b--) {
			map.put("A" + b, new ActionsDouble());
			for (int j = 2; j <= 11; j++)
				prob(new ArrayList<>(List.of(11, b)), j);
		}
		for (int a = 11; a >= 2; a--)
			for (int b = 11; b >= 2; b--) {
				if (a + b <= 7)
					continue;
				if (a == b)
					continue;
				map.put(String.valueOf(a + b), new ActionsDouble());
				for (int j = 2; j <= 11; j++)
					prob(new ArrayList<>(List.of(a, b)), j);
			}
		for (int i = 2; i <= 11; i++) {
			String mapKey = i + " " + i;
			map.put(mapKey, new ActionsDouble());
			for (int j = 2; j <= 11; j++)
				prob(new ArrayList<>(List.of(i, i)), j);
		}
		System.out.println(mapToStringCopy());
		System.out.println(erwartungsWert());
		File result = new File("D:\\nandi\\Desktop\\Programieren\\Workspace\\Blackjack\\src\\de\\nandi\\blackjack\\probabilities\\" +
//				"resultsPC\\" +
				"BestEasyBasicStrategy RemovedTens PC" + removedTens + ".txt");
		try {
			if (!result.createNewFile()) {
				System.out.println("Could not save File because it already exists.");
				return;
			}
		} catch (IOException e) {
			System.out.println("Could not save File.");
			e.printStackTrace();
			return;
		}
		try (FileWriter writer = new FileWriter(result)) {
			writer.write(mapToString()+"\n");
			writer.write(String.format(Locale.ENGLISH, "%." + 5 + "f" + "%% μ \n", erwartungsWert() * 100));
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	private double erwartungsWert() {
		double value = 0;
		for (int a = 11; a >= 2; a--)
			for (int b = 11; b >= 2; b--) {
				double value2 = 0;
				for (int c = 11; c >= 2; c--) {
					value2 += map.get(cardsToString(new ArrayList<>(List.of(a, b)))).getExpectedValue()[c - 2]
							* probabilityDraw(c);
				}
				value += value2 * probabilityDraw(a) * probabilityDraw(b);
			}
		return value;
	}


	/**
	 * @return Erwartungswert für player
	 */
	private double prob(ArrayList<Integer> playerCards, int dealerCard) {
		int playerValue = countValueBeneficial(playerCards);
		String cardString = cardsToString(playerCards);
		if (!cardString.startsWith("A") && !cardString.contains(" ")) {
			if (playerValue > 21)
				return -1;
//			if (playerValue > 17)
//				return dealer(playerValue, new ArrayList<>(List.of(dealerCard)));
		}
		ActionsDouble actionsDouble = map.get(cardString);
		if (actionsDouble.isSet(dealerCard - 2))
			return actionsDouble.getExpectedValue()[dealerCard - 2] / (
					Objects.equals(actionsDouble.getActions()[dealerCard - 2], "d") && playerCards.size() != 2 ? 2D : 1D
			);
		double stand = dealer(playerValue, new ArrayList<>(List.of(dealerCard)));
		double hit = 0;
		for (int i = 2; i <= 11; i++) {
			playerCards.add(i);
			hit += prob(playerCards, dealerCard) * probabilityDraw(i);
			playerCards.remove(playerCards.size() - 1);
		}
		double dd = 0;
		for (int i = 2; i <= 11; i++) {
			playerCards.add(i);
			dd += dd(countValueBeneficial(playerCards), new ArrayList<>(List.of(dealerCard)))
					* probabilityDraw(i) * 2;
			playerCards.remove(playerCards.size() - 1);
		}
		double split = -1000;
		if (!splited && cardString.contains(" ")) {
			split = 0;
			splited = true;
			int playerCard = playerCards.remove(0);
			for (int i = 2; i <= 11; i++) {
				playerCards.add(i);
				double prob = prob(playerCards, dealerCard);
				split += prob * probabilityDraw(i) * 2
						+ prob * (2 * (probabilityDraw(i)));
				playerCards.remove(playerCards.size() - 1);
			}
			playerCards.add(playerCard);
			splited = false;
		}

		double expectedValue = Math.max(Math.max(stand, hit), Math.max(dd, split));
		if (expectedValue == stand)
			actionsDouble.getActions()[dealerCard - 2] = "s";
		else if (expectedValue == hit)
			actionsDouble.getActions()[dealerCard - 2] = "h";
		else if (expectedValue == dd)
			actionsDouble.getActions()[dealerCard - 2] = "d";
		else
			actionsDouble.getActions()[dealerCard - 2] = "p";//split
		actionsDouble.getExpectedValue()[dealerCard - 2] = expectedValue;
		return expectedValue;
	}

	boolean splited = false;


	private double dd(int playerValue, ArrayList<Integer> dealerCards) {
		if (playerValue > 21)
			return -1;
		ActionsDouble actionsDouble = map.get(String.valueOf(playerValue));
		if (Objects.equals(actionsDouble.getActions()[dealerCards.get(0) - 2], "s"))
			return actionsDouble.getExpectedValue()[dealerCards.get(0) - 2];
		return dealer(playerValue, dealerCards);
	}

	/**
	 * @return Erwartungswert für player
	 */
	private double dealer(int playerValue, ArrayList<Integer> dealerCards) {
		int dealerValue = countValueBeneficial(dealerCards);
		if (dealerValue > 21)
			return 1;
		if (dealerValue > 16)
			return Integer.compare(playerValue, dealerValue);//Natruals?
		double bust = 0;
		for (int i = 2; i <= 11; i++) {
			dealerCards.add(i);
			bust += dealer(playerValue, dealerCards) * probabilityDraw(i);
			dealerCards.remove(dealerCards.size() - 1);
		}
		return bust;
	}

	private double probabilityDraw(int card) {
		if (removedTens >= 0)
			if (card == 10)
				return 1D / 52D * (16 - removedTens);
//		if(removedTens < 0)
//
		if(card == -removedTens)
			return 3D / 52D;
		return probabilityDraw * (card == 10 ? 4 : 1);
	}

	private String mapToString() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String key : map.keySet()) {
			stringBuilder.append(String.format("%-4s", key)).append('=').append(' ').
					append(map.get(key).toStringOnlyLetters()).append("\n");
		}
		return stringBuilder.toString();
	}

	private String mapToStringCopy() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String key : map.keySet()) {
			stringBuilder.append("{{")
					.append(String.format("%-8s", '"' + key + '"')).append("},{");
			for (String value : map.get(key).getActions()) {
				stringBuilder.append('"').append(value).append('"').append(',').append(' ');
			}
			stringBuilder.append("}},\n");
		}
		return stringBuilder.toString();
	}

	private String mapToStringPC() {
		StringBuilder stringBuilder = new StringBuilder();
		for (String key : map.keySet()) {
			stringBuilder
					.append(key).append(";");
			for (String value : map.get(key).getActions()) {
				stringBuilder.append(value).append(',');
			}
			stringBuilder.append('\n');
		}
		return stringBuilder.toString();
	}

	private String cardsToString(ArrayList<Integer> cards) {
		if (Objects.equals(cards.get(0), cards.get(1)) && cards.size() == 2)
			return cards.get(0) + " " + cards.get(0);
		else if (cards.contains(11)) {
			ArrayList<Integer> cardsCopy = new ArrayList<>(cards);
			cardsCopy.remove(Integer.valueOf(11));
			int additionalValue;
			if (cardsCopy.contains(11))
				additionalValue = cardsCopy.stream().mapToInt(Integer::intValue).map(oldValue -> oldValue == 11 ? 1 : oldValue).sum();
			else
				additionalValue = cardsCopy.stream().mapToInt(Integer::intValue).sum();
			if (additionalValue < 11 && additionalValue > 1)
				return "A" + additionalValue;
		}
		return String.valueOf(countValueBeneficial(cards));
	}

	public int countValueBeneficial(ArrayList<Integer> cards) {
		if (cards.contains(11)) {
			ArrayList<Integer> cardsCopy = new ArrayList<>(cards);
			int sum = cardsCopy.stream().mapToInt(Integer::intValue).sum();
			while (sum > 21)
				if (cardsCopy.remove(Integer.valueOf(11))) {
					sum -= 10;
				} else
					break;
			return sum;
		}
		return cards.stream().mapToInt(Integer::intValue).sum();
	}


	private double probDebug(ArrayList<Integer> playerCards, int dealerCard) {
		int playerValue = countValueBeneficial(playerCards);
		if (playerValue > 21)
			return -1;
		if (playerValue > 16)
			return dealer(playerValue, new ArrayList<>(List.of(dealerCard)));
		String cards = cardsToString(playerCards);
		ActionsDoubleDebug actionsDouble = mapDebug.get(cards);
		if (actionsDouble.isSet(dealerCard - 2))
			return actionsDouble.getExpectedValue()[dealerCard - 2];
		double stand = dealer(playerValue, new ArrayList<>(List.of(dealerCard)));
		double hit = 0;
		for (int i = 2; i <= 11; i++) {
			playerCards.add(i);
			hit += probDebug(playerCards, dealerCard);
			playerCards.remove(playerCards.size() - 1);
		}
		if (stand > hit)
			actionsDouble.getActions()[dealerCard - 2] = "s";
		else
			actionsDouble.getActions()[dealerCard - 2] = "h";
		double expectedValue = Math.max(stand, hit);
		actionsDouble.getExpectedValue()[dealerCard - 2] = expectedValue;
		actionsDouble.getExpectedValue2()[dealerCard - 2] = Math.min(stand, hit);
		return expectedValue;
	}

}
