/**
 * Is the dealer for blackjack
 *
 * @author Gage Roush
 * @version 2025.03.22
 */
public class BlackjackDealer extends BlackjackPlayer
{
    // Create a private property of a Deck
    private Deck deck;

    /**
     * Constructor for objects of class Dealer
     */
    public BlackjackDealer(int stash)
    {
        // Call the parent class' constructor with the name "Dealer" and the stash provided as a parameter
        // Initialize the Deck property by calling its constructor
        super("Dealer", stash);
        deck = new BlackjackDeck();
    }

    /**
     * deal will return a card from the deck
     * @return Card a card from the deck
     */
    public Card deal()
    {
        // Deal a card from the deck and return it.
        return deck.deal();
    }
    
    /**
     * resetDeck replaces the deck with a new shuffled 52 card deck
     */
    public void resetDeck()
    {
        // Initialize the deck as a new instance of the Deck to reset it to 52 random cards again.
        deck = new BlackjackDeck();
    }
}