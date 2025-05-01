import java.util.Collections;
import java.util.Comparator;

/**
 * A poker player.
 *
 * @author Gage Roush
 * @version 2025.04.03
 */
public class PokerPlayer extends Player{

    /**
     * Default constructor for a poker player.
     */
    public PokerPlayer()
    {
        super();
    }

    /**
     * Overloaded constructor for a poker player.
     * @param name name of the player
     * @param stash amount of money/chips in stash
     */
    public PokerPlayer(String name, int stash)
    {
        super(name, stash);
    }

    /**
     * Scores if a player has 3 of a kind.
     * @return whether player has a 3 of a kind or not.
     */
    private boolean is3ofKind()
    {
        Collections.sort(hand);
        return hand.get(0).getValue() == hand.get(2).getValue();
    }

    /**
     * Scores if a player has a straight.
     * Is this the best code ever? Probably. Is it efficient? Nah.
     * @return whether a player has a straight or not.
     */
    private boolean isStraight()
    {
        Collections.sort(hand);
        Card previousCard = null;

        // checks if the last card in the hand is an ace and then checks the order from there
        if(hand.get(2).getValue() == 14)
        {
            // checks if the first card is 2 and then checks if the second card is either a king or a 3
            // since those are the only cards that low ace can apply with
            if(hand.get(0).getValue() == 2 && (hand.get(1).getValue() == 3 || hand.get(1).getValue() == 13))
            {
                return true;
            }
            else{ return false; }
        }
        else{
            // checks if the hand is a straight if there is no ace
            for (Card card : hand)
            {
                if (previousCard != null)
                {
                    // checks if the current card is 1 more than the previous card
                    if (card.getValue() != previousCard.getValue() + 1)
                    {
                        return false;
                    }
                    previousCard = card;
                } else
                {
                    previousCard = card;
                }
            }
            return true;
        }
    }

    /**
     * Scores if a player has a flush (all cards are of same house/suit).
     * @return whether a player has a flush or not.
     */
    private boolean isFlush()
    {
        Collections.sort(hand, Card.suitComparator());
        return hand.get(0).getSuit() == hand.get(2).getSuit();
    }

    /**
     * Scores if a player has a pair.
     * @return whether a player has a pair or not.
     */
    private boolean isPair()
    {
        if (hand.get(0).getValue() == hand.get(1).getValue()
                || hand.get(1).getValue() == hand.get(2).getValue()
                || hand.get(0).getValue() == hand.get(2).getValue())
        {
            return true;
        } else
        {
            return false;
        }
    }

    /**
     * Checks if a player has a straight flush using the isStraight method and isFlush method.
     * @return whether a player has a straight flush or not
     */
    private boolean isStraightFlush()
    {
        if (isFlush() && isStraight())
        {
            return true;
        }
        return false;
    }

    /**
     * Calculates the score of the cards
     * @return the score of all cards combined
     */
    private int calculateCardSum()
    {
        int sum = 0;
        for(Card card : hand)
        {
            sum += card.getValue();
        }
        return sum;
    }

    /**
     * Calculates the total score of the hand.
     * @return the score of the current hand
     */
    @Override
    public int scoreHand()
    {
        if(isStraightFlush())
        {
            return 1000 + calculateCardSum();
        }
        else if (is3ofKind())
        {
            return 800 + calculateCardSum();
        }
        else if (isStraight())
        {
            return 600 + calculateCardSum();
        }
        else if (isFlush())
        {
            return 400 + calculateCardSum();
        }
        else if (isPair())
        {
            return 200 + calculateCardSum();
        }
        else
        {
            return 100 + calculateCardSum();
        }
    }
}
