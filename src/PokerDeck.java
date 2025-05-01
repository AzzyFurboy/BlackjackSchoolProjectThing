import java.util.Collections;

/**
 * A poker deck with values for a poker game.
 *
 * @author Gage Roush
 * @version 2025.03.27
 */

public class PokerDeck extends Deck {

    /**
     * Constructor for a poker deck. It is very good.
     */
    public PokerDeck() {
        super();

        for (int i = 0; i < 4; i++) {
            switch (i) {
                case 0:
                    currentSuit = Suit.Clubs;
                    break;
                case 1:
                    currentSuit = Suit.Diamonds;
                    break;
                case 2:
                    currentSuit = Suit.Hearts;
                    break;
                case 3:
                    currentSuit = Suit.Spades;
                    break;
            }
            for (int x = 2; x < 15; x++) {
                switch (x) {
                    case 14:
                        deck.add(new Card(currentSuit, 14, true, "Ace"));
                        break;
                    case 13:
                        deck.add(new Card(currentSuit, 13, true, "King")); //My King <3
                        break;
                    case 12:
                        deck.add(new Card(currentSuit, 12, true, "Queen"));
                        break;
                    case 11:
                        deck.add(new Card(currentSuit, 11, true, "Jack"));
                        break;
                    default:
                        deck.add(new Card(currentSuit, x, true, Integer.toString(x)));
                        break;

                }
            }
        }
        Collections.shuffle(deck);
    }
}