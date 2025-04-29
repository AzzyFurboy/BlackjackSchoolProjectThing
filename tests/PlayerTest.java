import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import javax.swing.*;

/**
 * The test class PlayerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class PlayerTest
{
    /**
     * Default constructor for test class PlayerTest
     */
    public PlayerTest()
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
    public void playerConstructorTest()
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
        Player p = new BlackjackPlayer();
        
        // Act
        p.receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), true);
        
        // Assert - scoreHand is a subtle way to see if there is a card in the hand
        assertEquals(p.scoreHand(), 10);
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
    
    // scoreHand test
    @Test
    public void playerScoreHandTest()
    {
        // Arrange
        Player p = new BlackjackPlayer();
        
        // Act
        p.receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), true);
        p.receiveCard(new Card(Suit.Clubs, 11, true, "Ace"), true);
        
        // Assert
        assertEquals(p.scoreHand(), 21);
    }

    // clear hand test
    @Test
    public void playerClearHandTest()
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
    public void playerShowAllCardsTest()
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
    
    @Test
    public void playerCardPanelUsesFlowLayoutTest()
    {
        // Arrange
        Player p = new BlackjackPlayer();
        // Act
        JPanel cardPanel = p.getCardPanel();
        
        // Assert
        assertTrue(cardPanel.getLayout() instanceof FlowLayout);
    }
    
    @Test
    public void playerReceiveCardUpdatesCardPanelTest()
    {
        // Arrange
        Player p = new BlackjackPlayer();
        int cardsInPanelStart;
        int cardsInPanelEnd;
        // Act
        cardsInPanelStart = p.getCardPanel().getComponentCount();
        p.receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), true);
        cardsInPanelEnd = p.getCardPanel().getComponentCount();
        
        // Assert
        assertTrue(cardsInPanelStart < cardsInPanelEnd);
    }
    
    @Test
    public void playerClearHandUpdatesCardPanelTest()
    {
        // Arrange
        Player p = new BlackjackPlayer();
        int cardsInPanelStart;
        int cardsInPanelEnd;
        // Act
        p.receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), true);
        cardsInPanelStart = p.getCardPanel().getComponentCount();
        p.clearHand();
        cardsInPanelEnd = p.getCardPanel().getComponentCount();
        
        // Assert
        assertTrue(cardsInPanelStart > cardsInPanelEnd);
    }
    
    @Test
    public void playerNameLabelTest()
    {
        // Arrange
        Player p = new BlackjackPlayer();
        // Act
        
        // Assert
        assertTrue(p.getNameLabel().getText().length() > 0);    
    }
    
    @Test
    public void playerStashLabelTest()
    {
        // Arrange
        Player p = new BlackjackPlayer();
        // Act
        
        // Assert
        assertTrue(p.getStashLabel().getText().length() > 0);    
    }
    
    
    @Test
    public void playerScoreLabelTest()
    {
        // Arrange
        Player p = new BlackjackPlayer();
        
        // Act
        p.receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), true);
        
        // Assert 
        assertTrue(p.getNameLabel().getText().length() > 0);
    }
    
    @Test
    public void playerStashLabelUpdateTest()
    {
        // Arrange
        Player p = new BlackjackPlayer();
        String stashStartString;
        String stashEndString;
        // Act
        stashStartString = p.getStashLabel().getText();
        p.setStash(p.getStash() + 50);
        stashEndString = p.getStashLabel().getText();
        
        // Assert
        assertFalse(stashStartString.equals(stashEndString));
    }
    
    @Test
    public void playerScoreUpdateLabelTest()
    {
        // Arrange
        Player p = new BlackjackPlayer();
        String scoreStartString;
        String scoreEndString;
        
        // Act
        scoreStartString = p.getScoreLabel().getText();
        p.receiveCard(new Card(Suit.Clubs, 10, true, "Queen"), true);
        p.receiveCard(new Card(Suit.Diamonds, 10, true, "Queen"), true);
        scoreEndString = p.getScoreLabel().getText();
        
        // Assert
        assertFalse(scoreStartString.equals(scoreEndString));
    }
}
