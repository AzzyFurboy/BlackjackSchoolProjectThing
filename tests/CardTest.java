import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import javax.swing.ImageIcon;
import javax.imageio.ImageIO;
import java.io.File;

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
        assertTrue(c1.getImage() instanceof BufferedImage);
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
    
    @Test // Basic logic from https://stackoverflow.com/questions/31279009/testing-image-files-with-junit 
    public void CardGetImageTest()
    {
        // Arrange
        Card c1 = new Card(Suit.Clubs, 10, true, "Queen");
        Card c2 = new Card(Suit.Clubs, 2, false, "2");
        BufferedImage face;
        BufferedImage back;
        BufferedImage actualFace = null;
        BufferedImage actualBack = null;
        byte[] faceArray;
        byte[] backArray;
        byte[] actualFaceArray;
        byte[] actualBackArray;
        
        // Act
        face = c1.getImage();
        back = c2.getImage();
        try
        {
             actualFace = ImageIO.read(new File("png/queen_of_clubs.png"));
             actualBack = ImageIO.read(new File("png/back.png"));
        }
        catch (Exception e)
        {
            // do something catchy
        }
        
        // Read the image into a byte array
        faceArray = ((DataBufferByte) face.getData().getDataBuffer()).getData();
        backArray = ((DataBufferByte) back.getData().getDataBuffer()).getData();
        actualFaceArray = ((DataBufferByte) actualFace.getData().getDataBuffer()).getData();
        actualBackArray = ((DataBufferByte) actualBack.getData().getDataBuffer()).getData();
        
        // Assert
        assertArrayEquals(faceArray, actualFaceArray);
        assertArrayEquals(backArray, actualBackArray);
    }
    
    @Test
    public void cardLabelTest()
    {
        // Arrange
        Card c1 = new Card(Suit.Clubs, 10, true, "Queen");
        Card c2 = new Card(Suit.Clubs, 2, false, "2");
        BufferedImage actualFace = null;
        BufferedImage actualBack = null;
        BufferedImage c1Image;
        BufferedImage c2Image;
        ImageIcon c1Icon;
        ImageIcon c2Icon;
        byte[] faceArray;
        byte[] backArray;
        byte[] actualFaceArray;
        byte[] actualBackArray;
        
        // Act
        try // Get the actual image files 
        {
             actualFace = ImageIO.read(new File("png/queen_of_clubs.png"));
             actualBack = ImageIO.read(new File("png/back.png"));
        }
        catch (Exception e)
        {
            // do something catchy
        }
        // Get the image out of the labels
        c1Icon = (ImageIcon) c1.getCardLabel().getIcon();
        c2Icon = (ImageIcon) c2.getCardLabel().getIcon();
        c1Image = (BufferedImage) c1Icon.getImage();
        c2Image = (BufferedImage) c2Icon.getImage();
        
        // Read the images into a byte array
        faceArray = ((DataBufferByte) c1Image.getData().getDataBuffer()).getData();
        backArray = ((DataBufferByte) c2Image.getData().getDataBuffer()).getData();
        actualFaceArray = ((DataBufferByte) actualFace.getData().getDataBuffer()).getData();
        actualBackArray = ((DataBufferByte) actualBack.getData().getDataBuffer()).getData();
        
        // Assert - compare the actual image to the expected image
        assertArrayEquals(faceArray, actualFaceArray);
        assertArrayEquals(backArray, actualBackArray);
    }
    
        @Test
    public void cardShowHideUpdatesLabelTest()
    {
        // Arrange
        Card c1 = new Card(Suit.Clubs, 10, true, "Queen");
        BufferedImage actualFace = null;
        BufferedImage actualBack = null;
        BufferedImage c1ShowImage;
        BufferedImage c1HideImage;
        ImageIcon c1ShowIcon;
        ImageIcon c1HideIcon;
        byte[] faceArray;
        byte[] backArray;
        byte[] actualFaceArray;
        byte[] actualBackArray;
        
        // Act
        try // Get the actual image files 
        {
             actualFace = ImageIO.read(new File("png/queen_of_clubs.png"));
             actualBack = ImageIO.read(new File("png/back.png"));
        }
        catch (Exception e)
        {
            // do something catchy
        }
        // Get the image out of the labels
        c1.show();
        c1ShowIcon = (ImageIcon) c1.getCardLabel().getIcon();
        c1.hide();
        c1HideIcon = (ImageIcon) c1.getCardLabel().getIcon();
        c1ShowImage = (BufferedImage) c1ShowIcon.getImage();
        c1HideImage = (BufferedImage) c1HideIcon.getImage();
        
        // Read the images into a byte array
        faceArray = ((DataBufferByte) c1ShowImage.getData().getDataBuffer()).getData();
        backArray = ((DataBufferByte) c1HideImage.getData().getDataBuffer()).getData();
        actualFaceArray = ((DataBufferByte) actualFace.getData().getDataBuffer()).getData();
        actualBackArray = ((DataBufferByte) actualBack.getData().getDataBuffer()).getData();
        
        // Assert - compare the actual image to the expected image
        assertArrayEquals(faceArray, actualFaceArray);
        assertArrayEquals(backArray, actualBackArray);
    }
}
