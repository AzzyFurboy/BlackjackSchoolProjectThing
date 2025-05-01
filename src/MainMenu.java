import javax.swing.*;
import java.awt.*;

public class MainMenu {

    Frame frame;
    JLabel title1;
    JLabel title2;
    JButton bjButt;
    JButton pokeButt;
    String game;

    public MainMenu()
    {
        makeFrame();
        game = "";
        frame.setVisible(true);
    }

    private void makeFrame()
    {
        frame = new Frame("Main Menu");
        frame.setLayout(new BorderLayout());
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

        frame.add(north,BorderLayout.NORTH);
    }

    private void center()
    {
        JPanel center = new JPanel();
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
        bjButt = new JButton("Play Blackjack!");
        pokeButt = new JButton("Play 3 Card Poker!");

        bjButt.addActionListener(e -> {new Graphics("Blackjack"); frame.dispose();});
        pokeButt.addActionListener(e -> {new Graphics("Poker"); frame.dispose();});

        center.add(bjButt);
        center.add(pokeButt);

        frame.add(center, BorderLayout.CENTER);
    }

    public String game(){
        return game;
    }
}
