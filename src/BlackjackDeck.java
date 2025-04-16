import java.util.Collections;

public class BlackjackDeck extends Deck{

    public BlackjackDeck()
    {
        super();

        for(int i = 0; i < 4; i++) {
            switch(i){
                case 0: currentSuit = Suit.Clubs;
                    break;
                case 1: currentSuit = Suit.Diamonds;
                    break;
                case 2: currentSuit = Suit.Hearts;
                    break;
                case 3: currentSuit = Suit.Spades;
                    break;
            }
            for (int x = 2; x < 15; x++) {
                switch (x) {
                    case 14:
                        deck.add(new Card(currentSuit, 11, true, "Ace"));
                        break;
                    case 13:
                        deck.add(new Card(currentSuit, 10, true, "King"));
                        break;
                    case 12:
                        deck.add(new Card(currentSuit, 10, true, "Queen"));
                        break;
                    case 11:
                        deck.add(new Card(currentSuit, 10, true, "Jack"));
                        break;
                    default:
                        deck.add(new Card(currentSuit, x, true, Integer.toString(x)));
                        break;
                }

            }
        }
        Collections.shuffle(deck);
    }
}
