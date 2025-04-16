import java.util.ArrayList;
import java.util.Collections;

/*
 * Supposed to represent a standard 52 deck of cards
 *
 * @author Gage Roush
 * @version 2025.01.23
 */
abstract class Deck
{
    Suit currentSuit;
    // private ArrayList of cards
    ArrayList<Card> deck;

    /*
     * Should make a standard deck of cards and then shuffle it.
     */
    public Deck()
    {
        this.deck = new ArrayList<Card>();
    }

    public static void sort(ArrayList<Card> hand)
    {

    }

    /*
     * returns the card and index 0 and then removes it from the deck
     */
    public Card deal()
    {
        return deck.removeFirst();
    }

    /*
     * returns the amount of cards left in the deck
     */
    public int cardsLeftInDeck()
    {
        // Return the size of the cards collection
        return deck.size();
    }
    
    /*
     * Returns 52 pick-up.
     *
     * Returns every card in the deck's toString()
     */
    @Override
    public String toString()
    {
        for(Card card: deck) {
            return card.toString();
        }
        return "";
    }
}
