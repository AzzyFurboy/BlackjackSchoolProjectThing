
import javax.swing.*;;

/**
 * Represents the table where you play blackjack at.
 * Features gameplay. Most methods are private cuz isn't that how it's supposed to be.
 * I added Doubling Down for no reason, muchhh more of a pain than I had initially thought it would be.
 * It works though so thats all that really matters right.
 *
 * @author Gage Roush
 * @version 2025-04-16
 */

public class BlackjackTable extends Graphics
{
    private Logging log = new Logging();

    BlackjackPlayer player = new BlackjackPlayer();
    BlackjackDealer dealer = new BlackjackDealer();

    private int pot; //the total amount of winnings available at the "table"
    private int bet; //represents the players bet
    private int dealerBet;//dealers bet
    private int winCount; // amount of wins the player has
    private int lossCount; // amount of games the player has lost

    public BlackjackTable()
    {
        super("Blackjack");
        pot = 0;
        winCount = 0;
        lossCount = 0;

        player = new BlackjackPlayer("Player", 500);
        dealer = new BlackjackDealer(player.getStash()*5);
        super.setDealer(dealer);
        super.setPlayer(player);
        super.fillFrame();

        betButton.addActionListener(e -> {
            try
            {
                takeBets(Integer.parseInt(betBox.getText()));
            } catch (IllegalBet ex)
            {
                System.err.println(ex.getMessage());
                log.logWarningMessage(ex.getMessage() + "\n Player Betted: " + bet + "\n Player Stash: " + player.stash);
                System.exit(0);
            } catch (Exception ex)
            {
                System.err.println("Error");
                log.logWarningMessage(ex.getMessage() + "\n Error when inputting bet");
                System.exit(0);
            }});

        stayButton.addActionListener(e -> {playerLogic("Stay");});
        hitButton.addActionListener(e -> {playerLogic("Hit");});

        newRound();
    }


    /**
     * Takes a players bet. The dealer then matches it, if the dealer cannot match then they go "all in" and use all their chips to try to match.
     */
    private void takeBets(int bet) throws IllegalBet
    {
        this.bet = bet;
            if (bet < 0)
            {
                throw new IllegalBet("Illegal Bet, less than 0");
            } else if (bet > player.getStash())
            {
                throw new IllegalBet("Illegal Bet, more than player stash");
            }
             player.setStash(player.getStash() - bet);
            pot = bet;
            // if dealer has less than the bet, they go all in. Adding all their stash to the pot and setting their stash to 0
        dealerBetting();
        betButton.setEnabled(false);

        hitButton.setEnabled(true);
        stayButton.setEnabled(true);

        dealer.updateLabels();
        player.updateLabels();
        potLabel.setText("Pot: " + pot);

        dealer.resetDeck();
        startingCards();

        frame.repaint();
    }

    /**
     * for when the player wins
     */
    private void playerWins()
    {
        player.setStash(player.getStash() + pot);
        winCount++;
        if(ultimateWinnerCheck()){
            JOptionPane.showMessageDialog(null, "YOU BEAT THE HOUSE!!", "You went home with all the money.\nNow go somewhere else.", JOptionPane.OK_OPTION);
                new MainMenu();
                frame.dispose();
        }
        else {
            int dialog = JOptionPane.showConfirmDialog(null, "Play again?\n" + "Wins: " + winCount + "\nLosses: " + lossCount, "Player Wins!!", JOptionPane.YES_NO_OPTION);
            if (dialog == 1) {
                frame.dispose();
                new MainMenu();
            } else {
                newRound();
            }
        }
    }

    /**
     * for when the dealer wins
     */
    private void dealerWins()
    {
        dealer.setStash(dealer.getStash() + pot);
        lossCount++;
        if(ultimateWinnerCheck()){
            JOptionPane.showMessageDialog(null, "YOU LOST IT ALL!!", "You are now broke.\nCome back again soon broke boi :3", JOptionPane.OK_OPTION);
                new MainMenu();
                frame.dispose();
        }
        else{
        int dialog = JOptionPane.showConfirmDialog(null, "Play again?\n" + "Wins: " + winCount + "\nLosses: " + lossCount, "Dealer Wins!!",JOptionPane.YES_NO_OPTION);
        if(dialog == 1){
            frame.dispose();
            new MainMenu();
        }
        else{
            newRound();
        }
        }
    }

    /**
     * checks if either players have 0 remaining stash
     * @return if either player has won
     */
    private boolean ultimateWinnerCheck()
    {
        if(dealer.getStash() == 0)
        {
            return true;
        }
        else if (player.getStash() == 0)
        {
            return true;
        }
        return false;
    }

    /**
     * The scoring logic
     */
    private void score()
    {
        if(player.scoreHand() > dealer.scoreHand() && player.scoreHand() <= 21)
        {
            playerWins();
        }
        else if (player.scoreHand() == dealer.scoreHand() && player.scoreHand() <= 21)
        {
            JOptionPane.showMessageDialog(null,"Push!","The game was a push, both players have their money returned.",JOptionPane.INFORMATION_MESSAGE);
            player.setStash(player.getStash()+bet);
            dealer.setStash(dealer.getStash()+dealerBet);
            newRound();
        }
        else if(player.scoreHand() <= 21 && dealer.scoreHand() > 21)
        {
            playerWins();
        }else if(dealer.scoreHand() > player.scoreHand())
        {
            dealerWins();
        }
    }

    /**
     * Dealer logic
     */
    private void dealerLogic()
    {
        dealer.showAllCards();
        dealer.updateLabels();
        frame.repaint();
        if (dealer.scoreHand() < 17)
        {
            do
            {
                dealer.receiveCard(dealer.deal(), true);
                dealer.updateLabels();
                frame.repaint();
            } while (dealer.scoreHand() < 17);
        }
        score();
    }

    /**
     * What the player can do on their turn.
     *
     * Features . .
     * Hitting and Staying
     */
    private void playerLogic(String reply)
    {
            //if the player hits
            if (reply.equals("Hit"))
            {
                player.receiveCard(dealer.deal(), true);
                if(player.scoreHand() > 21){
                    dealerWins();
                    hitButton.setEnabled(false);
                    stayButton.setEnabled(false);
                    return;
                }
            }
            //if the player stays
            else if (reply.equals("Stay"))
            {
                dealerLogic();

                hitButton.setEnabled(false);
                stayButton.setEnabled(false);
            }

        dealer.updateLabels();
        player.updateLabels();

        frame.repaint();
    }

    /**
     * Resets both players hand and the deck, resetting the pot to 0.
     * Also checks if any player has completely won
     */
    private void newRound()
    {
        hitButton.setEnabled(false);
        stayButton.setEnabled(false);
        betButton.setEnabled(true);

        dealer.resetDeck();

        player.clearHand();
        dealer.clearHand();
        player.cardPanel.removeAll();
        dealer.cardPanel.removeAll();


        player.updateLabels();
        dealer.updateLabels();
        frame.repaint();
        pot = 0;
        bet = 0;
        dealerBet = 0;
    }

    /**
     * The dealer betting mechanics
     * If the player bets more than the dealer has, the dealer goes "all-in", betting all their money and setting their stash to 0
     */
    private void dealerBetting()
    {
        if(dealer.getStash() >= bet)
        {
            dealerBet = bet;
            pot += dealerBet;
            dealer.setStash(dealer.getStash() - bet);
        }
        else {
            dealerBet =  dealer.getStash();
            pot += dealerBet;
            dealer.setStash(0);
        }
    }

    /**
     * Starting cards
     */
    private void startingCards(){
        dealer.receiveCard(dealer.deal(), false);
        player.receiveCard(dealer.deal(), true);
        dealer.receiveCard(dealer.deal(), true);
        player.receiveCard(dealer.deal(), true);
    }

}
