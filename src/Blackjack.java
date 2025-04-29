import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Supposed to be a Blackjack game, tho i cant seem to get the graphical cards to work. Sure ill figure it out eventually, just not before I have to sumbit it lol
 * Im sure ill fix it eventually.
 * @author Gage R
 * @version 2025.04.29
 */
public class Blackjack implements ActionListener
{
    int bet;
    int dealerBet;
    int pot;
    BlackjackPlayer player;
    BlackjackDealer dealer;

    JFrame frame;
    JLabel potLabel = new JLabel("Pot: ");
    JLabel statusLabel = new JLabel("Status: ");
    JTextField betInput = new JTextField();
    JButton betButton = new JButton("Bet");
    JButton hitButton = new JButton("Hit");
    JButton stayButton = new JButton("Stay");

    public Blackjack(){
        makeFrame();
        start();
    }

    /**
     * takes bets and throws IllegalBet if incorrect bet is shown
     * @param betInput
     * @throws IllegalBet
     */
    public void takeBet(String betInput) throws IllegalBet
    {
        bet = 0;
        bet = Integer.parseInt(betInput);

        if (bet < 0)
        {
            throw new IllegalBet("Illegal Bet, less than 0");
        } else if (bet > player.getStash())
        {
            throw new IllegalBet("Illegal Bet, more than player stash");
        }
        player.setStash(player.getStash() - bet);
        // if dealer has less than the bet, they go all in. Adding all their stash to the pot and setting their stash to 0
        if(dealer.getStash() >= bet)
        {
            dealerBet = bet;
            pot += dealerBet;
            dealer.setStash(dealer.getStash() - bet);
        }
        else {
            dealerBet = dealer.getStash();
            dealer.setStash(0);
        }
        startGame();
    }

    /**
     * Checks both players starting cards for blackjack
     * @return
     */
    public Player checkForBlackjack()
    {
        if (player.hand.size() == 2 && player.scoreHand() == 21)
        {
            return player;
        } else if (dealer.hand.size() == 2 && dealer.scoreHand() == 21)
        {
            dealer.showAllCards();
            frame.repaint();
            return dealer;
        }
        return null;
    }

    /**
     * Declares the winner
     * @return
     */
    public Player declareWinner(){
        if(player.scoreHand() > 21){
            return dealer;
        } else if (dealer.scoreHand() > 21)
        {
            return player;
        } else if (player.scoreHand() > dealer.scoreHand())
        {
            return player;
        } else if (dealer.scoreHand() > player.scoreHand())
        {
            return dealer;
        }
        return null;
    }

    /**
     * Supposed to give the winner their money
     * @param winner
     */
    public void awardPotToWinner(Player winner)
    {
        if(winner == null)
        {
            player.setStash(player.getStash()+bet);
            dealer.setStash(dealer.getStash()+dealerBet);
        }else if(winner == player){
            player.setStash(player.getStash() + pot);
        }
        else{
            dealer.setStash(dealer.getStash() + pot);
        }
        pot = 0;
        potLabel.repaint();
        playAgain(winner);
    }

    /**
     * Resets the game
     */
    public void startOver()
    {
        pot = 0;
        bet = 0;
        dealerBet = 0;
        dealer.clearHand();
        player.clearHand();
        dealer.resetDeck();

        betButton.setEnabled(true);
        hitButton.disable();
        stayButton.disable();
        frame.repaint();
    }

    /**
     * Has a pop up that says who won and asks if the player wants to play again
     * Also prints if you ran out of money or if the dealer somehow ran out of money
     * @param winner
     */
    public void playAgain(Player winner)
    {
        if(player.getStash() > 0 && dealer.getStash() > 0)
        {
            String whoWon = winner == null ? "You Draw": (winner.name + " Wins!");
            int choice = JOptionPane.showConfirmDialog(null, "Play Again?", whoWon , JOptionPane.YES_NO_OPTION);
            if (choice == JOptionPane.YES_OPTION)
            {
                startOver();
            } else if (choice == JOptionPane.NO_OPTION)
            {
                System.exit(0);
            }
        } else if (player.getStash() == 0)
        {
            JOptionPane.showMessageDialog(null,"You LOSE !!");
        } else if (dealer.getStash() == 0)
        {
            JOptionPane.showMessageDialog(null, "You beat the house! Congrats lucky boy");
        }
    }

    /**
     * Start of the blackjack game
     */
    public void startGame()
    {
        dealer.receiveCard(dealer.deal(), false);
        player.receiveCard(dealer.deal(), true);
        dealer.receiveCard(dealer.deal(), true);
        player.receiveCard(dealer.deal(), true);
        frame.repaint();
        if(checkForBlackjack() != null)
        {
            awardPotToWinner(checkForBlackjack());
        }
        betButton.setEnabled(false);
        hitButton.setEnabled(true);
        stayButton.setEnabled(true);
        playersTurn();
    }

    /**
     * The players turn
     */
    public void playersTurn()
    {
        statusLabel.setText("Status: "+player.name+"'s Turn");
        hitButton.addActionListener(e -> {
            player.receiveCard(dealer.deal(), true);
            frame.repaint();
            if(player.scoreHand() > 21) {
                awardPotToWinner(dealer);
                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
            }
            });
        stayButton.addActionListener(e -> dealersTurn());
    }

    /**
     * the dealers turn
     */
    public void dealersTurn()
    {
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);

        statusLabel.setText("Status: "+dealer.name+"'s Turn");
        dealer.showAllCards();
        while(dealer.scoreHand() < 17)
        {
            dealer.receiveCard(dealer.deal(), true);
            frame.repaint();
        }
        awardPotToWinner(declareWinner());
    }

    /**
     * Puts all the gui together pretty much.
     * North, South, Center, East, and West are all the regions that get thrown together
     */
    public void makeFrame(){
        //making the players here
        player = new BlackjackPlayer("Alpha Sigma", 500);
        dealer = new BlackjackDealer(player.getStash()*5);

        //frame stuff
        frame = new JFrame("Blackjack");
        frame.setSize(640, 480);
        Container c = frame.getContentPane();

        JMenuBar menuBar = new JMenuBar(); frame.setJMenuBar(menuBar);
            JMenu file = new JMenu("File"); menuBar.add(file);
                JMenuItem newGame = new JMenuItem("New Game"); file.add(newGame); newGame.addActionListener(e -> makeFrame());
                JMenuItem quit = new JMenuItem("Quit"); file.add(quit); quit.addActionListener(e -> System.exit(0));
            JMenu help = new JMenu("Help"); menuBar.add(help);
                JMenuItem about = new JMenuItem("About Blackjack"); help.add(about); about.addActionListener(e -> JOptionPane.showMessageDialog(null,"Blackjack is a game where you compete with a dealer to be the closest player to 21 point. It is a classic casino game played often.\nAces are worth either 11 or 1 points, dependent on which one benifits the player more;\nall face cards are worth 10 points, Jacks, Queens, Kings;\nand all other cards are worth their numbered value.\n\tWritten by: Gage \"Azzy\" Roush\n\tCreated 4 - 29 - 2025","About Blackjack", JOptionPane.PLAIN_MESSAGE));


        potLabel = new JLabel("Pot: ");
        statusLabel = new JLabel("Status: ");
        betInput = new JTextField(9);
        betInput.setToolTipText("Bets go here");
        betButton = new JButton("Bet");
        hitButton = new JButton("Hit");
        stayButton = new JButton("Stay");

        c.setLayout(new BorderLayout());
        north();
        center();
        south();
        west();
        east();
    }

    public void north()
    {
        JPanel north = new JPanel();
        north.setLayout(new FlowLayout());
        north.add(getPotLabel());
        north.add(getStatusLabel());
        frame.add(north,BorderLayout.NORTH);
    }
    
    public void west(){
        JPanel west = new JPanel();
        west.setLayout(new BoxLayout(west,BoxLayout.Y_AXIS));
        west.add(player.getNameLabel());
        west.add(player.getStashLabel());
        west.add(player.getScoreLabel());
        frame.add(west,BorderLayout.WEST);
    }

    public void east(){
        JPanel east = new JPanel();
        east.setLayout(new BoxLayout(east,BoxLayout.Y_AXIS));
        east.add(dealer.getNameLabel());
        east.add(dealer.getStashLabel());
        east.add(dealer.getScoreLabel());
        frame.add(east,BorderLayout.EAST);
    }

    public void center(){
        JPanel center = new JPanel(new GridLayout(2,1));
        center.add(dealer.getCardPanel()).setPreferredSize(new Dimension(300, 200));  // Add this line
        center.add(player.getCardPanel()).setPreferredSize(new Dimension(300, 200));  // Add this line
        frame.add(center,BorderLayout.CENTER);
    }

    public void south(){
        JPanel south = new JPanel(new FlowLayout());
        south.add(betInput);
        south.add(betButton); betButton.addActionListener(e -> {try
                                                            {takeBet(betInput.getText());}
                                                                catch (IllegalBet ex){
                                                                new JOptionPane(ex.getMessage(),JOptionPane.ERROR_MESSAGE);
                                                                }});
        south.add(hitButton); hitButton.setEnabled(false);
        south.add(stayButton); stayButton.setEnabled(false);
        frame.add(south);
    }

    @Override
    public void actionPerformed(ActionEvent e){
    }

    public void start()
    {
        frame.pack();
        frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        frame.setVisible(true);
    }






    public BlackjackPlayer getPlayer()
    {
        return player;
    }

    public BlackjackDealer getDealer()
    {
        return dealer;
    }

    public JFrame getFrame()
    {
        return frame;
    }

    public JLabel getPotLabel()
    {
        return potLabel;
    }

    public JLabel getStatusLabel()
    {
        return statusLabel;
    }

    public JTextField getBetInput()
    {
        return betInput;
    }

    public JButton getBetButton()
    {
        return betButton;
    }

    public JButton getHitButton()
    {
        return hitButton;
    }

    public JButton getStayButton()
    {
        return stayButton;
    }

    public int getPot()
    {
        return pot;
    }

    public int getBet()
    {
        return bet;
    }
}
