import java.util.Scanner;

/**
 * Represents the table where you play blackjack at.
 * Features gameplay. Most methods are private cuz isn't that how its supposed to be.
 * I added Doubling Down for no reason, muchhh more of a pain than I had innially thought it would be.
 * It works though so thats all that really matters right.
 *
 * @author Gage Roush
 * @version 2025-04-16
 */

public class BlackjackTable
{
    private Scanner scan;
    private Logging log = new Logging();

    BlackjackPlayer player;
    BlackjackDealer dealer;

    private int pot; //the total amount of winnings available at the "table"
    private int bet; //represents the players bet
    private boolean wasPush; //if a game was a push this will be true
    private int winCount; // amount of wins the player has
    private int lossCount; // amount of games the player has lost
    private boolean roundOver; // if the round has already been won/lost to a bust

    public BlackjackTable()
    {
        scan = new Scanner(System.in);
        pot = 0;
        winCount = 0;
        lossCount = 0;

        player = new BlackjackPlayer("Player", 500);
        dealer = new BlackjackDealer(player.getStash()*5);
    }


    /**
     * Plays the game
     */
    public void play()
    {
        String reply = "";

        do
        {
            roundOver = false;
            // taking bets
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
            wasPush = false;

            //giving out the starting cards of the blackjack hand
            dealer.receiveCard(dealer.deal(), false);
            player.receiveCard(dealer.deal(), true);
            dealer.receiveCard(dealer.deal(), true);
            player.receiveCard(dealer.deal(), true);
            System.out.println(printScores());
            if (player.scoreHand() == 21)
            {
                System.out.println("You drew a blackjack ! You win !");
                playerWins();
                roundOver = true;
            }

            // rahaha game logic for the player
            if(!roundOver)
            {
                playerLogic();
            }

            //dealer turn
            if(!roundOver)
            {
                dealerLogic();
            }

            //scoring
            if(!roundOver)
            {
                score();
            }

            //checks if anyone has 0, gets ignored if there was a push
            if(ultimateWinnerCheck() && !wasPush){ break; }

            endRound();

            //if the game was a push the player doesn't get the chance to leave and another round is immediately started
            if(!wasPush)
            {
                System.out.println("Would you like to keep playing?\nWins: " + winCount + "\nLosses: " + lossCount);
                reply = scan.next();
            }
        } while (reply.toLowerCase().startsWith("y") || wasPush);
    }

    /**
     * Takes a players bet. The dealer then matches it, if the dealer cannot match then they go "all in" and use all their chips to try to match.
     */
    private void takeBets() throws IllegalBet
    {
        bet = 0;

        System.out.println("Please make your bet.");

            bet = scan.nextInt();
            if (bet < 0 && !wasPush)
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
            pot += bet * 2;
            dealer.setStash(dealer.getStash() - bet);
        }
        else {
            pot += bet + dealer.getStash();
            dealer.setStash(0);
        }
        System.out.println("The current pot is -> " + pot);
    }

    /**
     * Thing that prints both the player and dealers scores so i dont have to rewrite it all the time.
     * @return the toStrings of the dealer and player seperated by a line
     */
    private String printScores()
    {
        String string;
        string = (dealer.toString() + "\n" + player.toString() + "\n" + "--------------------------- \n");
        return string;
    }

    /**
     * for when the player wins
     */
    private void playerWins()
    {
        player.setStash(player.getStash() + pot);
        winCount++;
    }

    /**
     * for when the dealer wins
     */
    private void dealerWins()
    {
        dealer.setStash(dealer.getStash() + pot);
        lossCount++;
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
     * The scoring logic
     */
    private void score()
    {
        if(player.scoreHand() > dealer.scoreHand())
        {
            System.out.println("You WIN !");
            playerWins();
        }
        else if (player.scoreHand() == dealer.scoreHand())
        {
            wasPush = true;
            System.out.println("The game is a push! The pot keeps its value and you need to play again!");
        }
        else
        {
            System.out.println("You LOSE !");
            dealerWins();
        }
    }

    /**
     * Dealer logic
     */
    private void dealerLogic()
    {
        dealer.showAllCards();
        System.out.println("Dealer flips his cards..");
        System.out.println(printScores());
        if (dealer.scoreHand() < 17)
        {
            do
            {
                dealer.receiveCard(dealer.deal(), true);
                System.out.println("The dealer draws..");
                System.out.println(printScores());

                if (dealer.scoreHand() > 21)
                {
                    System.out.println("The dealer busts ! \n You win !");
                    playerWins();
                    roundOver = true;
                }
            } while (dealer.scoreHand() < 17);
        }
    }

    private void playerLogic()
    {
        String reply = "";
        boolean exit;
        do
        {
            if(player.hand.size() == 2){ System.out.println("Hit, Stay, or Double Down?"); }
            else{ System.out.println("Hit or Stay?"); }

            reply = scan.next();
            //if the player hits
            if (reply.equalsIgnoreCase("hit"))
            {
                player.receiveCard(dealer.deal(), true);
                System.out.println(printScores());
                if (player.scoreHand() > 21)
                {
                    System.out.println("BUST ! \n You LOSE!");
                    dealerWins();
                    roundOver = true;
                    break;
                }
            }
            //if the player stays
            else if (reply.equalsIgnoreCase("stay"))
            {
                System.out.println("You Stay\n");
            }
            //if the player says double
            else if (reply.equalsIgnoreCase("double"))
            {
                if(player.hand.size() == 2 && player.getStash() >= bet)
                {
                    System.out.println("You Double Down");
                    player.setStash(player.getStash() - bet);
                    dealerBetting();

                    player.receiveCard(dealer.deal(), true);
                    System.out.println(printScores());
                    if (player.scoreHand() > 21)
                    {
                        System.out.println("BUST ! \n You LOSE!");
                        dealerWins();
                        roundOver = true;
                        break;
                    }
                }
                else if (player.hand.size() > 2)
                {
                    System.out.println("You can only double down on your first move");
                }
                else if (player.getStash() < bet*2)
                {
                    System.out.println("You don't have enough money.");
                }
            }
            //if the player says anything invalid
            else
            {
                System.out.println("Sorry don't understand. Either you hit or stay or double. \n" +
                        " Hit means I give you another card. \n" +
                        " Stay means you don't want anymore cards. \n" +
                        " Double means I give you only one more card and you double your bet. ");
            }

            exit = false;
            if(reply.equalsIgnoreCase("double") || reply.equalsIgnoreCase("stay"))
            {
                exit = true;}
        } while (!exit);
    }

    /**
     * Resets both players hand and the deck, resetting the pot to 0
     */
    private void endRound()
    {
        dealer.resetDeck();
        player.clearHand();
        dealer.clearHand();
        if (!wasPush)
        {
            pot = 0;
        }
    }

    private void dealerBetting()
    {
        if(dealer.getStash() >= bet)
        {
            pot += bet + bet;
            dealer.setStash(dealer.getStash() - bet);
        }
        else {
            pot += bet + dealer.getStash();
            dealer.setStash(0);
        }
    }
}
