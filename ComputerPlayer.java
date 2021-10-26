package simple21;

/**
 * Represents a computer player in this simplified version of the "21" card game.
 */
public class ComputerPlayer {

	/** 
	 * The name of the player.
	 */
    String name;
    //String playertype = 'computer'
    
    /**
     * The player's one hidden card (a value from 1 - 10).
     */
    private int hiddenCard = 0;
    
    /** 
     * The sum of the player's cards, not counting the hidden card. 
     */
    private int sumOfVisibleCards = 0;
    
    /**
     * Flag indicating if the player has passed (asked for no more cards).
     */
    boolean passed = false;
    
    /**
     * Constructs a computer player with the given name.
     * @param name of the user.
     */
    public ComputerPlayer (String name) {
        this.name = name;
        //this.playertype = playertype
    }
    
    /**
     * Decides whether to take another card. In order to make this decision, this player considers 
     * their own total points (sum of visible cards + hidden card). 
     * This player may also consider other players' sum of visible cards, but not the value 
     * of other players' hidden cards.
     * @param human The other human player
     * @param player1 Another (computer) player
     * @param player2 Another (computer) player
     * @param player3 Another (computer) player
     * @return true if this player wants another card
     */
    public boolean offerCard(HumanPlayer human, ComputerPlayer player1, ComputerPlayer player2, ComputerPlayer player3) { 
    	// Students: your code goes here.
        boolean take = true;
        //if other players' visible cards are close to 21 enough, then it's worth trying to take another card
        if (human.getSumOfVisibleCards() >= 11) take = true ; 
        if (this!=player1 && !player1.passed && player1.getSumOfVisibleCards() >= 11) take = true ;  
        if (this!=player2 && !player2.passed && player2.getSumOfVisibleCards() >= 11) take = true ; 
        if (this!=player3 && !player3.passed && player3.getSumOfVisibleCards() >= 11) take = true ; 
        // however, it would be risky to pick another card if the sum is already larger than 16 -- there is ovver 50% chance that it wll fail with a sum larger tha 21.
    	if (this.getScore() > 16) take = false ; 
        if (!take) {
            this.passed = true;
        }
    	return take;
    }
    
    /**    
     * Puts the specified card in this player's hand as the hidden card.
     * Prints a message saying that the card is being taken, but does not print the value of the hidden card.
     * @param card being taken
     */
    public void takeHiddenCard(int card) {
    	// Students: your code goes here.
    	hiddenCard = card;
    	System.out.println(name + " takes a hidden card.");
    }
    
    /**
     * Adds the given card to the sum of the visible cards for this player.
     * Prints a message saying that the card is being taken.
     * @param card being taken
     */
    public void takeVisibleCard(int card) { 
    	// Students: your code goes here.
    	sumOfVisibleCards += card;
        System.out.println(name + " takes " + card);
    }

    /**
     * Returns the total sum of this player's cards, not counting the hidden card. 
     * @return sumOfVisibleCards
     */
    public int getSumOfVisibleCards() { 
    	// Students: your code goes here.
    	return sumOfVisibleCards;	
    }
    
    /**
     * Return this player's total score (the total of all this player's cards).
     * That is to say, the sum of the visible cards + the hidden card.
     * @return total score 
     */
    public int getScore() { 
    	// Students: your code goes here.
    	return sumOfVisibleCards + hiddenCard;
    }
}
