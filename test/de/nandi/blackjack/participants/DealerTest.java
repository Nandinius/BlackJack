package de.nandi.blackjack.participants;

import de.nandi.blackjack.CardDeck;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DealerTest {

    @Test
    void stand() {
        Player player = new Player(new CardDeck(1));
        Dealer dealer = player.dealer;
        player.cards.clear();
        player.cards.addAll(Arrays.asList(10, 11));
        dealer.cards.clear();
        dealer.cards.addAll(Arrays.asList(10, 11));
        assertEquals(1, dealer.stand());
        player.cards.clear();
        player.cards.addAll(Arrays.asList(10, 8, 11));
        dealer.cards.clear();
        dealer.cards.addAll(Arrays.asList(10, 7, 11));
        assertEquals(2, dealer.stand());
        player.cards.clear();
        player.cards.addAll(Arrays.asList(10, 11));
        dealer.cards.clear();
        dealer.cards.addAll(Arrays.asList(10, 8));
        assertEquals(2.5, dealer.stand());
        player.cards.clear();
        player.cards.addAll(Arrays.asList(10, 8));
        dealer.cards.clear();
        dealer.cards.addAll(Arrays.asList(10, 11));
        assertEquals(-1, dealer.stand());
    }

    @Test
    void hits() {
        Player player = new Player(new CardDeck(1));
        Dealer dealer = player.dealer;
        player.cards.clear();
        player.cards.addAll(Arrays.asList(10, 11));
        dealer.cards.clear();
        dealer.cards.addAll(Arrays.asList(10, 10));
        assertEquals(2.5, dealer.hits());
        player.cards.clear();
        player.cards.addAll(Arrays.asList(10, 8, 11));
        dealer.cards.clear();
        dealer.cards.addAll(Arrays.asList(10, 10, 11));
        assertEquals(2, dealer.hits());
        dealer.cards.clear();
        dealer.cards.add(10);
        assertEquals(1, dealer.hits());
    }
}