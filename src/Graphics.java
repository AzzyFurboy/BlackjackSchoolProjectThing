import javax.swing.*;
import java.awt.*;

public class Graphics {
    static JFrame frame;

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

    static JTextField betBox;
    static JButton betButton;
    static JButton hitButton;
    static JButton stayButton;

    public Graphics(String gameName) {
        this.gameName = gameName;

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
    }

    public void fillFrame(){
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

        about.addActionListener(e -> {if(gameName.equals("Blackjack")){
            JOptionPane.showMessageDialog(null,"A simple GUI Blackjack game made by Gage Roush.\nFor more information PayPal me $1000.\n\nVersion: 1.0","About Blackjack",JOptionPane.INFORMATION_MESSAGE);
        } else if (gameName.equals("Poker")) {
            JOptionPane.showMessageDialog(null,"A unfinished GUI Poker game made by Gage Roush.\nFor more information PayPal me $1000000.\n\nVersion: 0.6","About Poker",JOptionPane.INFORMATION_MESSAGE);
        }
        });

        newGame.addActionListener(e -> {if(gameName.equals("Blackjack")){
            frame.dispose();
            new BlackjackTable();
        } else if (gameName.equals("Poker")){
            frame.dispose();
            new PokerTable();
        }
        });
        menu.addActionListener(e -> {frame.dispose(); new MainMenu();});
        quit.addActionListener(e -> System.exit(0));

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
        south.add(hitButton = new JButton("Hit"));
        south.add(stayButton = new JButton("Stay"));

        frame.add(south, BorderLayout.SOUTH);
    }

    public void setPotLabel(int pot) {
        potLabel.setText(String.valueOf(pot));
    }

    public void setStatusLabel(String status) {
        statusLabel.setText(status);
    }

    public void setDealer(Player dealer){
        this.dealer = dealer;
    }

    public void setPlayer(Player player){
        this.player = player;
    }
}
