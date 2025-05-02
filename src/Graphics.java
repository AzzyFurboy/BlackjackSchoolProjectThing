import javax.swing.*;
import java.awt.*;

public class Graphics {
    JFrame frame;

    JMenuBar menuBar;
    JMenu fileMenu;

    JMenuItem newGame;
    JMenuItem menu;
    JMenuItem quit;
    JMenu help;
    JMenuItem about;

    JLabel potLabel = new JLabel("Pot: 0");
    JLabel statusLabel = new JLabel("New Game");

    String gameName;

    Color bgc = new Color(0,120,0);
    Image icon = Toolkit.getDefaultToolkit().getImage("fav.jpg");


    Player player;
    Player dealer;

    JTextField betBox;
    JButton betButton;

    public Graphics(String gameName, Player player, Player dealer) {
        this.gameName = gameName;
        this.player = player;
        this.dealer = dealer;

        makeFrame();
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }

    private void makeFrame() {
        frame = new JFrame(gameName);
        frame.setIconImage(icon);
        frame.setBackground(bgc);
        frame.setLayout(new BorderLayout());
        makeMenus();
        center();
        north();
        east();
        south();
        west();
    }

    private void makeMenus(){
        menuBar = new JMenuBar();
        menuBar.add(fileMenu = new JMenu("File"));
        fileMenu.add(newGame = new JMenuItem("New Game"));
        fileMenu.add(menu = new JMenuItem("Menu"));
        fileMenu.add(quit = new JMenuItem("Quit"));
        menuBar.add(help = new JMenu("Help"));
        help.add(about = new JMenuItem("About " + gameName));

        newGame.addActionListener(e -> {if(gameName.equals("Blackjack")){
            frame.dispose();
            new BlackjackTable();
        } else if (gameName.equals("Poker")){
            frame.dispose();
            new PokerTable();
        }
        });
        menu.addActionListener(e -> {frame.dispose(); new MainMenu();});

        frame.setJMenuBar(menuBar);
    }

    private void north(){
        JPanel north = new JPanel();
        north.setLayout(new BoxLayout(north, BoxLayout.X_AXIS));

        //adding
        north.add(statusLabel);
        north.add(Box.createRigidArea(new Dimension(20,1)));
        north.add(potLabel);

        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        potLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        //color
        statusLabel.setForeground(Color.white);
        potLabel.setForeground(Color.white);
        north.setBackground(Color.darkGray);

        //put it all together
        frame.add(north,BorderLayout.NORTH);
    }

    private void center(){
        JPanel center = new JPanel();
        center.setLayout(new GridLayout(2,1));

        center.add(dealer.getCardPanel());
        center.add(player.getCardPanel());

        center.setBackground(bgc);

        frame.add(center, BorderLayout.CENTER);
    }

    private void east(){
        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east, BoxLayout.Y_AXIS));

        east.add(dealer.getNameLabel());
        east.add(dealer.getStashLabel());
        east.add(dealer.getScoreLabel());

        east.setBackground(bgc);
        frame.add(east, BorderLayout.EAST);
    }

    private void west(){
        JPanel west = new JPanel();
        west.setLayout(new BoxLayout(west, BoxLayout.Y_AXIS));

        west.add(player.getNameLabel());
        west.add(player.getStashLabel());
        west.add(player.getScoreLabel());

        west.setBackground(bgc);
        frame.add(west, BorderLayout.WEST);
    }

    private void south(){
        JPanel south = new JPanel();
        south.setLayout(new GridBagLayout());
        south.setBackground(Color.LIGHT_GRAY);

        south.add(betBox = new JTextField(20));
        south.add(betButton = new JButton("Bet"));

        frame.add(south, BorderLayout.SOUTH);
    }

    public void setPotLabel(int pot) {
        potLabel.setText(String.valueOf(pot));
    }

    public void setStatusLabel(String status) {
        statusLabel.setText(status);
    }

}
