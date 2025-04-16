import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class PlayerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class BlackjackPlayerTest
{
    /**
     * Default constructor for test class PlayerTest
     */
    public BlackjackPlayerTest()
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
    public void blackjackPlayerConstructorTest()
    {
        // Arrange
        Player p;
        
        // Act
        p  = new BlackjackPlayer();
        
        // Assert
        assertEquals(p.getStash(), 500);
    }
    
    // receiveCard test
    @Test
    public void playerReceiveCardTest()
    {
        // Arrange
        Player p1 = new BlackjackPlayer();
        Player p2 = new BlackjackPlayer();
        
        // Act
        p1.receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), true);
        p2.receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), false);
        p2.receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), true);
        
        // Assert - scoreHand is a subtle way to see if there is a card in the hand
        assertEquals(p1.scoreHand(), 10);
        assertEquals(p2.scoreHand(), 10); // Only scores cards that are not hidden
    }
    

    
    // get/set stash test
    @Test
    public void playerManageStashTest()
    {
        // Arrange
        Player p = new BlackjackPlayer();
        
        // Act
        p.setStash(1000);
        
        // Assert
        assertEquals(p.getStash(), 1000);
    }
    
    // scoreBlackjack test
    @Test
    public void blackjackPlayerScoreBlackjackTest()
    {
        // Arrange
        Player p = new BlackjackPlayer();
        
        // Act - Dealer's first card will be face down, but scoreHand needs to look at it for this special situation
        p.receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), false);
        p.receiveCard(new Card(Suit.Clubs, 11, true, "Ace"), true);
        
        // Assert
        assertEquals(p.scoreHand(), 21);
    }
    
    // scoreHand test
    @Test
    public void blackjackPlayerScoreHandTest()
    {
        // Arrange
        Player p = new BlackjackPlayer();
        
        // Act
        p.receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), false); // will be hidden, so won't be counted in the score
        p.receiveCard(new Card(Suit.Clubs, 2, true, "two"), true);        
        p.receiveCard(new Card(Suit.Clubs, 3, true, "three"), true);        
        
        // Assert
        assertEquals(p.scoreHand(), 5);
    }

    // clear hand test
    @Test
    public void blackjackPlayerClearHandTest()
    {
        // Arrange
        Player p1 = new BlackjackPlayer();
        Player p2 = new BlackjackPlayer();
        
        // Act - both receive the same cards.  p2 will be empty after clear hand, while p1 will not
        p1.receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), true);
        p1.receiveCard(new Card(Suit.Clubs, 11, true, "Ace"), true);
        
        p2.receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), true);
        p2.receiveCard(new Card(Suit.Clubs, 11, true, "Ace"), true);
        
        p2.clearHand();
        // Assert
        assertEquals(p1.scoreHand(), 21);
        assertEquals(p2.scoreHand(), 0);
    }
    
    // show all cards test
    @Test
    public void blackjackPlayerShowAllCardsTest()
    {
        // Arrange
        Player p1 = new BlackjackPlayer();
        
        // Act - both receive the same cards.  p2 will be empty after clear hand, while p1 will not
        p1.receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), false);
        
        boolean cardIsHidden = (p1.scoreHand() == 0); // Score of the Queen is skipped because it is hidden 

        p1.showAllCards();

        boolean cardIsVisible = (p1.scoreHand() == 10); // Score of Queen is now returned because all cards are visible       
        
        // Assert
        assertTrue(cardIsHidden);
        assertTrue(cardIsVisible);
    }
}