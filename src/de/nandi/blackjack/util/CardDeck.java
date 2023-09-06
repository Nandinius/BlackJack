package de.nandi.blackjack.util;

import java.util.ArrayList;
import java.util.Collections;

public class CardDeck {

    private final ArrayList<Integer> currentCardDeck;
    private final ArrayList<Integer> usedCardDeck;
    private final int decks;
    private boolean shuffled;

    private int runningCount = 0;

    public CardDeck(int decks) {
        this.decks = decks;
        currentCardDeck = new ArrayList<>();
        usedCardDeck = new ArrayList<>();
        initializeCardDeck();
    }

    /**
     * Adds only the value of each card to the card deck because in every case only the value matters.
     * The option of choosing the value 1 for an ace is implemented in the methods where it is needed.
     */
    private void initializeCardDeck() {
        for (int i = 0; i < decks; i++)
            for (int colors = 0; colors < 4; colors++) {
                currentCardDeck.add(11);//Ace
                currentCardDeck.add(10);//King
                currentCardDeck.add(10);//Queen
                currentCardDeck.add(10);//Jack
                for (int numbers = 10; numbers >= 2; numbers--)
                    currentCardDeck.add(numbers);//Numbers
            }
        shuffle();
    }

    /**
     * This method returns the first card of the deck and removes it.
     * If there are less than 25% of the cards in the deck it will be shuffled.
     * To test if the deck was shuffled use the method wasShuffled();
     *
     * @return Returns the first card in the deck.
     * @see #wasShuffled
     */
    public int drawCard() {
        int card = currentCardDeck.remove(0);
        usedCardDeck.add(card);
        if (usedCardDeck.size() >= currentCardDeck.size() * 3) {
            shuffle();
        }
        if (card <= 6)
            runningCount++;
        if (card >= 10)
            runningCount--;
        return card;
    }

    /**
     * @return true if the deck was shuffled since the last call to {@code wasShuffled}
     */
    public boolean wasShuffled() {//TODO vielleicht unnötig
        if (shuffled) {
            shuffled = false;
            return true;
        }
        return false;
    }

    /**
     * Puts all cards back into the current deck and shuffles them.
     */
    public void shuffle() {
        currentCardDeck.addAll(usedCardDeck);
        usedCardDeck.clear();
        Collections.shuffle(currentCardDeck);
        shuffled = true;
        runningCount = 0;
    }

    /**
     * @param cards The cards which should be summed.
     * @return the sum of all the values of the cards with choosing aces as 1 if the sum would be over 21
     */
    public int countValueBeneficial(ArrayList<Integer> cards) {
        ArrayList<Integer> cardsCopy = new ArrayList<>(cards);
        int sum = cardsCopy.stream().mapToInt(Integer::intValue).sum();
        while (sum > 21)
            if (cardsCopy.remove(Integer.valueOf(11))) {
                sum -= 10;
            } else
                break;
        return sum;
    }

    public ArrayList<Integer> getCurrentCardDeck() {
        return currentCardDeck;
    }

    public int getDecks() {
        return decks;
    }

    public int getRunningCount() {
        return runningCount;
    }

    public int getTrueCount() {
        return runningCount / (currentCardDeck.size() / 52 + 1);
    }
}
