import javax.swing.*;
import java.awt.*;

public class MainMenu {

    JFrame frame;
    JLabel title1;
    JLabel title2;
    JButton bjButt;
    JButton pokeButt;
    String game;
    Color bgc = new Color(0,120,0);
    Image icon = Toolkit.getDefaultToolkit().getImage("fav.jpg");

    public MainMenu()
    {
        makeFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        game = "";
        frame.setVisible(true);
    }

    private void makeFrame()
    {
        frame = new JFrame("Main Menu");
        frame.setLayout(new BorderLayout());
        frame.setBackground(bgc);
        frame.setIconImage(icon);
        north();
        center();
    }

    private void north()
    {
        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.Y_AXIS));
        title1 = new JLabel("Welcome to Azzy's Casino!!");
        title2 = new JLabel("Choose one of our lovely games!");
        title1.setAlignmentX(Component.CENTER_ALIGNMENT);
        title2.setAlignmentX(Component.CENTER_ALIGNMENT);
        north.add(title1);
        north.add(title2);

        north.setBackground(bgc);
        frame.add(north,BorderLayout.NORTH);
    }

    private void center()
    {
        JPanel center = new JPanel();
        center.setLayout(new GridBagLayout());
        bjButt = new JButton("Play Blackjack!");
        pokeButt = new JButton("Play 3 Card Poker!");

        bjButt.addActionListener(e -> {new BlackjackTable(); frame.dispose();});
        pokeButt.addActionListener(e -> {new PokerTable(); frame.dispose();});

        center.add(bjButt);
        center.add(Box.createRigidArea(new Dimension(20,1)));
        center.add(pokeButt);

        center.setBackground(bgc);
        frame.add(center, BorderLayout.CENTER);
    }
}
