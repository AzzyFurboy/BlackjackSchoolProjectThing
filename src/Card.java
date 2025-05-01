import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

/**
 * A class that is supposed to be a card and all the things that a card can do.
 *
 * @author Gage Roush
 * @version 2025.04.28
 */
public class Card implements Comparable<Card>
{
    Logging log = new Logging();
    Suit suit;
    int value;
    boolean visible;
    String name;
    String facePath;
    String backPath;
    JLabel cardLabel;

    /**
     * Constructor of a card that creates a card while limiting it to only be made within values of 2 - 11
     * otherwise it doesn't make the card at all
     *
     * @param suit Represents the suit of the card
     * @param value Represents the value of the card 2 - 11
     * @param visible Represents whether the card is visible or not
     * @param name Represents the name of the card
     */
    public Card(Suit suit, int value, boolean visible, String name)
    {
        if (value >= 2 && value <= 14)
        {
            this.value = value;
            this.suit = suit;
            this.visible = visible;
            this.name = name;
            cardLabel.setBackground(Color.white);
            facePath = "png/"+name.toLowerCase()+"_of_"+suit.toString().toLowerCase()+".png";
            backPath = "png/back";
        }
    }

    /**
     * returns the cards suit
     */
    public Suit getSuit()
    {
        return suit;
    }

    /**
     * returns the cards value
     */
    public int getValue()
    {
        return value;
    }

    /**
     * Sets value of card
     */
    public void setValue(int value) { this.value = value; }

    /**
     * returns the name
     */
    public String getName()
    {
        return name;
    }

    public JLabel getCardLabel() {
        return cardLabel;
    }

    /**
     * returns the status of visible
     */
    public boolean isVisible()
    {
        return visible;
    }

    /**
     * "Shows" the card by setting the visibility to true
     */
    public void show()
    {
        visible = true;
        cardLabel.setIcon(new ImageIcon(facePath));
    }

    /**
     * "Hides" the card by setting the visibility to false
     */
    public void hide()
    {
        visible = false;
        cardLabel.setIcon(new ImageIcon(backPath));
    }

    /**
     * Display the front or back of the card, based on the visible field's value
     */
    @Override
    public String toString()
    {
        // If the card is visible, display the card's name of suit (ex: Queen of Spades)
        // If the card is not visible, return the string literal "Hidden Card"
        if (visible)
        {
            return (getName() + " of " + getSuit());
        } else
        {
            return "Hidden Card";
        }
    }


    @Override
    public int compareTo(Card o)
    {
        int result = this.getValue() - o.getValue();
        if(result == 0)
        {
            result = this.getSuit().ordinal() - o.getSuit().ordinal();
        }
        return result;
    }

    public static Comparator<Card> suitComparator()
    {
        return(c1,c2) ->
                c1.suit.compareTo(c2.suit);
    }
}

