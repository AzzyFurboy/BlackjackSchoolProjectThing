/**
 * A class for a player in a blackjack game.
 *
 * @author Gage Roush
 * @version 2025.03.26
 */

public class BlackjackPlayer extends Player{

    /**
     * Default constructor for the BlackjackPlayer class
     */
    public BlackjackPlayer()
    {
        super();
    }

    /**
     * Overloaded constructor for the BlackjackPlayer class
     */
    public BlackjackPlayer(String name, int stash)
    {
        super(name, stash);
    }

    /**
     * Score the cards in the player's hand for Blackjack
     * If the score is more than 21, the method reduces the score by 10 for each Ace present
     * until the score is less than or equal to 21 or all cards are examined
     * @return The score of all cards in the player's hand
     */
    public int scoreHand()
    {
        int score = 0;
        for(Card card: hand)
        {
            if(card.isVisible())
            {
                score += card.getValue();
                if(score > 21)
                {
                    for(Card isAces : hand) //checks for aces to reduce score
                    {
                        if(isAces.isVisible() && isAces.getName().equals("Ace") && isAces.getValue() == 11)
                        {
                            isAces.setValue(1);
                            score-=10;
                        }
                        if(score <= 21)
                        {
                            break;
                        }
                    }
                }
            }
        }
        return score;
    }

}
