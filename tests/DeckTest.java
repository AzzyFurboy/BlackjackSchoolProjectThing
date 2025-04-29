import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class DeckTest.
 *
 * @author  Charles Almond
 * @version 2020.06.15.01
 */
public class DeckTest
{
    /**
     * Default constructor for test class DeckTest
     */
    public DeckTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }
    
    @Test
    public void deckConstructorTest()
    {
        // Arrange
        BlackjackDeck deck = new BlackjackDeck();

        // Act
        
        // Assert
        assertTrue(deck.cardsLeftInDeck() == 52);
    }
    
    @Test
    public void deckDealTest()
    {
        // Arrange
        BlackjackDeck deck = new BlackjackDeck();

        // Act
        Card c1 = deck.deal();
        // Assert
        assertTrue(c1 instanceof Card);
    }
    
    @Test
    public void deckShuffleTest()
    {
        // Arrange
        BlackjackDeck deck1 = new BlackjackDeck();
        BlackjackDeck deck2 = new BlackjackDeck();
        boolean cardsEqual = true;
        
        // Act
        for (int i = 0; i < 52; i++)
        {
            Card c1 = deck1.deal();
            Card c2 = deck2.deal();
            // Use the getters of c1 and c2 to compare fields.  If one field isn't the same, set cardsEqual to false
            if (c1.getValue() != c2.getValue() 
             || c1.getSuit() != c2.getSuit()
             || !c1.getName().equals(c2.getName()))
            {
                cardsEqual = false;
            }
            
            if (cardsEqual == false)
            {
                break;
            }
        }
        
        // Assert
        assertFalse(cardsEqual);
    }
}