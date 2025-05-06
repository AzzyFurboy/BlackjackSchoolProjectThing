import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * The Player class represents a player
 * @author Gage Roush
 * @version 2025.03.26
 */
public abstract class Player
{
	// Private instance variables
    String name;
    int stash;
    ArrayList<Card> hand;

    JPanel cardPanel;
    JLabel nameLabel;
    JLabel stashLabel;
    JLabel scoreLabel;

    int startingMoney;

    Logging log = new Logging();

    /**
     * Constructor for objects of class Player
     */
    public Player()
    {
        name = "Player";
        stash = 500;
        hand = new ArrayList<>();
    }

    /**
     * Overloaded constructor for custom name and amount of money to bet with
     */
    public Player(String name, int stash)
    {
        this.name = name;
        try{ setStash(stash); }
        catch(Exception e)
        {
            System.out.println("Error invalid stash amount");
            log.logWarningMessage(e.getMessage());
        }
        hand = new ArrayList<>();
        startingMoney = getStash();

        nameLabel = new JLabel(name);
        stashLabel = new JLabel(String.valueOf(stash));
        cardPanel = new JPanel();
        scoreLabel = new JLabel(String.valueOf(scoreHand()));
        cardPanel.setBackground(Color.CYAN);
    }

    public void updateLabels(){
        stashLabel.setText(String.valueOf(stash));
        scoreLabel.setText(String.valueOf(scoreHand()));
    }

    /**
     * Get a card from the dealer
     * @param card A card from the deck of cards
     * @param visibility Set the card face-up (true) or face-down (false)
     */
    public void receiveCard(Card card, boolean visibility)
    {
	// If the visibility is true, call the card's show method and then add it to the hand
	// Otherwise, call the card's hide method and then add it to the hand
        if(visibility)
        {
            card.show();
            hand.add(card);
        }
        else
        {
            card.hide();
            hand.add(card);
        }
        cardPanel.add(card.getCardLabel());
    }

    /**
     * Set the amount of money the player has available to bet, must be >= 0
     */
    public void setStash(int stash)
    {
	// Validate that the stash is greater than or equal to zero before assigning it.
        if(stash >= 0)
        {
            this.stash = stash;
        }
    }

    /**
     * Get the amount of money the player has available to bet
     * @return The amount of money the player has to bet
     */
    public int getStash()
    {
        return stash;
    }

    /**
     * Clear the player's hand
     */
    public void clearHand()
    {
        hand.clear();
    }

    int scoreHand() {
        return 0;
    }

    /**
     * Flips all cards face-up
     */
    public void showAllCards()
    {
        for(Card card : hand)
        {
            card.show();
        }
    }


    /**
     * Return a string representing the player and the amount of money they have available
     */
    @Override
    public String toString()
    {
        String player = name + " has $" + stash + "\n";
        player += "Current points: " + scoreHand() + "\n";
        for(Card c : hand)
        {
            player += c.toString() + "\n";
        }
        return player;
    }

    public JLabel getScoreLabel() {
        return scoreLabel;
    }

    public void setScoreLabel(JLabel scoreLabel) {
        this.scoreLabel = scoreLabel;
    }

    public JLabel getStashLabel() {
        return stashLabel;
    }

    public void setStashLabel(JLabel stashLabel) {
        this.stashLabel = stashLabel;
    }

    public JLabel getNameLabel() {
        return nameLabel;
    }

    public void setNameLabel(JLabel nameLabel) {
        this.nameLabel = nameLabel;
    }

    public JPanel getCardPanel() {
        return cardPanel;
    }

    public void setCardPanel(JPanel cardPanel) {
        this.cardPanel = cardPanel;
    }
}