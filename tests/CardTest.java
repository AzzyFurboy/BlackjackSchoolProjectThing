import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class CardTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class CardTest
{
    /**
     * Default constructor for test class CardTest
     */
    public CardTest()
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
    public void cardConstructorTest()
    {
        // Arrange
        Card c1;
        
        // Act
        c1 = new Card(Suit.Clubs, 10, true, "Queen");
        
        // Assert
        assertEquals(c1.getSuit(), Suit.Clubs);
        assertEquals(c1.getValue(), 10);
        assertEquals(c1.getName(), "Queen");
        
    }
    
    @Test
    public void cardShowHideTest()
    {
        // Arrange
        Card c1 = new Card(Suit.Clubs, 10, true, "Jack");
        Card c2 = new Card(Suit.Clubs, 10, false, "Jack");
        
        // Act
        c1.hide();
        c2.show();
        
        // Assert
        assertFalse(c1.isVisible());
        assertTrue(c2.isVisible());
    }
}