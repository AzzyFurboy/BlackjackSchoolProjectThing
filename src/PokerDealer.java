/**
 * Class for a Poker Dealer, or at least a 3 card draw one.
 * @author Asriel
 * @version 2025.04.30
 */
public class PokerDealer extends PokerPlayer
{
    // Create a private property of a Deck
    private Deck deck;

    /**
     * Constructor for objects of class PokerDealer
     */
    public PokerDealer(int stash)
    {
        // Call the parent class' constructor with the name "Dealer" and the stash provided as a parameter
        // Initialize the Deck property by calling its constructor
        super("Dealer", stash);
        deck = new PokerDeck();
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
        deck = new PokerDeck();
    }

    /**
     * Checks if dealer qualifies for a W
     */
    public boolean qualify(){
        showAllCards();
        for(Card card : hand)
        {
            if(card.getValue() >= 12)
            {
                return true;
            }
        }
        return false;
    }

    /**
     * Had to remake the toString command due to the dealers points always being visible without
     * @return
     */
    public String toString(boolean shown)
    {
        String player = name + " has $" + stash + "\n";
        if(shown)
        {
            player += "Current points: " + scoreHand() + "\n";
        }
        else{
            player += "Current points: ??? \n";
        }
        for(Card c : hand)
        {
            player += c.toString() + "\n";
        }
        return player;
    }
}
