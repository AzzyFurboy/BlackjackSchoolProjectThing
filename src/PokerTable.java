import java.util.Scanner;

/**
 * Poker game!!
 * @author Asriel
 * @version 2025.4.30
 */
public class PokerTable {

    private Scanner scan;
    private Logging log = new Logging();

    PokerPlayer player;
    PokerDealer dealer;

    private boolean betRound;
    private int pot; //the total amount of winnings available at the "table"
    private int bet; //represents the players bet
    private boolean fold; //if player folded
    private boolean roundOver; // if the round has already been won/lost to a bust
    private int winCount;
    private int lossCount;
    private int dealerBet1;
    private int dealerBet2;

    public PokerTable()
    {
        scan = new Scanner(System.in);
        pot = 0;

        player = new PokerPlayer("Player", 500);
        dealer = new PokerDealer(player.getStash()*2);
    }

    public void play(){
        String reply = "";

        do{
            betRound=true;
            // taking bets
            try
            {
                takeBets();
            } catch (IllegalBet e)
            {
                System.err.println(e.getMessage());
                log.logWarningMessage(e.getMessage() + "\n Player Betted: " + bet + "\n Player Stash: " + player.stash);
                System.exit(0);
            } catch (Exception e) {
                System.err.println("Error");
                log.logWarningMessage(e.getMessage() + "\n Error when inputting bet");
                System.exit(0);
            }
            //first deal
            startDeal();
            System.out.println(printScores(false));
            betRound=false;

            // taking bets pt2
            try
            {
                takeBets();
            } catch (IllegalBet e)
            {
                System.err.println(e.getMessage());
                log.logWarningMessage(e.getMessage() + "\n Player Betted: " + bet + "\n Player Stash: " + player.stash);
                System.exit(0);
            } catch (Exception e)
            {
                System.err.println("Error");
                log.logWarningMessage(e.getMessage() + "\n Error when inputting bet");
                System.exit(0);
            }

            System.out.println(printScores(true));
            //wieners!!!
            winningLogic();
            player.clearHand();
            dealer.clearHand();

            if(ultimateWinnerCheck())
            {
                System.exit(0);
            }

            //asks if player still wanna play
            System.out.println("Would you like to keep playing? Y/N \nWins: " + winCount + "\nLosses: " + lossCount);
            reply = scan.next();
        }while (!(reply.toLowerCase().startsWith("n")));
        System.out.println("\n \nThanks for Playing !\n\tYou went into Azzy's Casino with $" + player.startingMoney + " and left with $" + player.getStash() + "!");
        System.exit(0);
    }


    /**
     * Takes a players bet. The dealer then matches it, if the dealer cannot match then they go "all in" and use all their chips to try to match.
     */
    private void takeBets() throws IllegalBet
    {
        bet = 0;
        fold = false;

        String round = betRound ? "ante":"play";
        System.out.println("Please make your "+ round +" bet.\nIf it's the second betting round, bet -1 if you wanna fold. \n You have $" + player.getStash());

        bet = scan.nextInt();
        if (bet == -1 && !betRound) {
            fold = true;
            bet = 0;
        }
        else if (betRound && bet == 0) {
            throw new IllegalBet("Illegal Bet, you have to bet something");
        }
        else if (bet < 0)
        {
            throw new IllegalBet("Illegal Bet, less than 0");
        } else if (bet > player.getStash())
        {
            throw new IllegalBet("Illegal Bet, more than player stash");
        }
        pot+=bet;
        player.setStash(player.getStash() - bet);
        dealerLogic();
    }

    /**
     * Initial round deal after the ante has been placed
     */
    private void startDeal()
    {
        player.receiveCard(dealer.deal(), true);
        dealer.receiveCard(dealer.deal(), false);
        player.receiveCard(dealer.deal(), true);
        dealer.receiveCard(dealer.deal(), false);
        player.receiveCard(dealer.deal(), true);
        dealer.receiveCard(dealer.deal(), true);
    }

    /**
     * Dealer checks if they qualify and then makes a bet equal to player
     * If its the ante the Dealer matches the ante
     * if dealer doesnt have enough $$$ then they go all in
     */
    private void dealerLogic()
    {
        if(betRound)
        {
            dealerBet1 = 0;
            if(dealer.getStash() >= bet)
            {
                dealerBet1 = bet;
                pot += dealerBet1;
                dealer.setStash(dealer.getStash() - dealerBet1);
            }
            else {
                dealerBet1 =  dealer.getStash();
                pot += dealerBet1;
                dealer.setStash(0);
            }
        }
        else if (!betRound && dealer.qualify()) {
            dealerBet2 = 0;
            if(dealer.getStash() >= bet)
            {
                dealerBet2 = bet;
                pot += dealerBet2;
                dealer.setStash(dealer.getStash() - dealerBet2);
            }
            else {
                dealerBet2 =  dealer.getStash();
                pot += dealerBet2;
                dealer.setStash(0);
            }
        }
    }

    /**
     * Logic that determines which player wins and rewards them
     */
    private void winningLogic()
    {
        if(!dealer.qualify() && !fold)
        {
            playerWins();
        } else if (dealer.qualify() && fold) {
            dealerWins();
        }
        else if(!dealer.qualify() && fold)
        {
            System.out.println("You folded and the dealer didn't qualify, the dealer gets their money back an' yours stays on the table..\nI suggest trying to win it back.");
            dealer.setStash(dealer.getStash()+dealerBet1);
        }
        else if(dealer.qualify() && !fold)
        {
            if(player.scoreHand() > dealer.scoreHand())
            {
                playerWins();
            }
            else if (dealer.scoreHand() > player.scoreHand()) {
                dealerWins();
            }
            else{
                System.out.println("Tie! Money is returned!");
                pot = pot - (dealerBet1 + dealerBet2);
                dealer.setStash(dealer.getStash()+dealerBet1+dealerBet2);
                player.setStash(player.getStash()+pot);
                pot = 0;
            }
        }
    }


    /**
     * for when the player wins
     */
    private void playerWins()
    {
        player.setStash(player.getStash() + pot);
        System.out.println("You Win!");
        winCount++;
        pot = 0;
    }

    /**
     * for when the dealer wins
     */
    private void dealerWins()
    {
        dealer.setStash(dealer.getStash() + pot);
        System.out.println("You Lose!");
        lossCount++;
        pot = 0;
    }

    /**
     * checks if either players have 0 remaining stash
     * @return if either player has won
     */
    private boolean ultimateWinnerCheck()
    {
        if(dealer.getStash() == 0)
        {
            System.out.println(player.name + " cleans house and is the Ulitimate Winner!!");
            return true;
        }
        else if (player.getStash() == 0)
        {
            System.out.println("The house wins! \n you may leave broke now.");
            return true;
        }
        return false;
    }

    /**
     * Thing that prints both the player and dealers scores so i dont have to rewrite it all the time.
     * @return the toStrings of the dealer and player seperated by a line
     */
    private String printScores(boolean shown)
    {
        String string;
        string = (dealer.toString(shown) + "\n" + player.toString() + "\n" + "--------------------------- \n");
        return string;
    }
}
