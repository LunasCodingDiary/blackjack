package simple21;

import java.util.Scanner;
import java.util.Random;

/**
 * This is a simplified version of a common card game, "21". 
 */
public class GameControl {
    
	/**
	 * Human player.
	 */
    HumanPlayer human;
    
    /**
     * Computer player.
     */
    ComputerPlayer player1;
    
    /**
     * Computer player.
     */
    ComputerPlayer player2;
    
    /**
     * Computer player.
     */
    ComputerPlayer player3;
    
    ComputerPlayer[] players = new ComputerPlayer[3];
    /** 
     * A random number generator to be used for returning random "cards" in a card deck.
     * */
    Random random = new Random();
      
    /**
     * The main method just creates a GameControl object and calls its run method.
     * @param args Not used.
     */
    public static void main(String args[]) {    
        new GameControl().run();
    }
    
    /**
     * Prints a welcome method, then calls methods to perform each of the following actions:
     * - Create the players (one of them a Human)
     * - Deal the initial two cards to each player
     * - Control the play of the game
     * - Print the final results
     */
    public void run() {
    	
        Scanner scanner = new Scanner(System.in);
        // Students: your code goes here.
        System.out.println("Welcome to Simple 21!");
        System.out.println("You'll play against 3 other players (computers).");
        System.out.println("Try to get as close to 21 as possible, without going over.");
        
        //get the name
        System.out.print("What is your name? ");
        String humansName = scanner.next();
        System.out.println(" ");
        //create the player
        createPlayers(humansName);
        
        //play
    	deal();
    	controlPlay(scanner);
    	printResults();   
        printWinner();
        scanner.close();
    }
    
    /**
     * Creates one human player with the given humansName, and three computer players with hard-coded names.
     * @param humansName for human player
     */
    public void createPlayers(String humansName) {
       // Students: your code goes here.
       human = new HumanPlayer(humansName);
       player1 = new ComputerPlayer("Player 1");
       player2 = new ComputerPlayer("Player 2");
       player3 = new ComputerPlayer("Player 3");
       players[0] = player1;
       players[1] = player2;
       players[2] = player3;
       // players = {player1,player2,player3} ;
        
    }
    
    /**
     * Deals two "cards" to each player, one hidden, so that only the player who gets it knows what it is, 
     * and one face up, so that everyone can see it. (Actually, what the other players see is the total 
     * of each other player's cards, not the individual cards.)
     */
    public void deal() { 
        // Students: your code goes here.
        human.takeHiddenCard(nextCard());
        human.takeVisibleCard(nextCard());
        for (ComputerPlayer player : players){
            player.takeHiddenCard(nextCard());
            player.takeVisibleCard(nextCard());
        }    	
    }
    
    /**
     * Returns a random "card", represented by an integer between 1 and 10, inclusive. 
     * The odds of returning a 10 are four times as likely as any other value (because in an actual
     * deck of cards, 10, Jack, Queen, and King all count as 10).
     * 
     * Note: The java.util package contains a Random class, which is perfect for generating random numbers.
     * @return a random integer in the range 1 - 10.
     */
    public int nextCard() { 
    	// Students: your code goes here.
        int[] cards = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 10, 10, 10};
    	int cardIndex = random.nextInt(13);
    	int nextCard = cards[cardIndex];
    	return nextCard;
    }

    /**
     * Gives each player in turn a chance to take a card, until all players have passed. Prints a message when 
     * a player passes. Once a player has passed, that player is not given another chance to take a card.
     * @param scanner to use for user input
     */
    public void controlPlay(Scanner scanner) { 
        // Students: your code goes here.
        // while all 4 players haven't passed  
        while (checkAllPlayersHavePassed()==false){
            if (!human.passed && human.getScore() < 21){
                boolean response = human.offerCard(human, player1, player2, player3, scanner);
                if (!response) System.out.println(human.name + " passes."); 
                else human.takeVisibleCard(nextCard());
            }
            for(ComputerPlayer player : players){
                if (!player.passed && player.getScore() < 21){
                    boolean take = player.offerCard(human, player1, player2, player3);
                    if (!take) System.out.println(player.name + " passes."); 
                    else player.takeVisibleCard(nextCard());
                }
            }
        }
   }
     
    /**
     * Checks if all players have passed.
     * @return true if all players have passed
     */
    public boolean checkAllPlayersHavePassed() {
    	// Students: your code goes here.
        //if a player's score exceeds 21, it is automatically passed.
        if (human.getScore()>=21) human.passed = true;
        if (player1.getScore()>=21) player1.passed = true;
        if (player2.getScore()>=21) player2.passed = true;
        if (player3.getScore()>=21) player3.passed = true;
        if (human.passed && player1.passed && player2.passed && player3.passed ) return true;
        else return false;
    }
 
    /**
     * Prints a summary at the end of the game.
     * Displays how many points each player had, and if applicable, who won.
     */
    public void printResults() { 
        // Students: your code goes here.
        System.out.println(" ");
        System.out.println("Game Over");
        System.out.println(human.name+" has " + human.getScore() + " point(s).");
        for(ComputerPlayer player : players){
            System.out.println(player.name+" has " + player.getScore() + " point(s).");
        }
    }

    /**
     * Determines who won the game, and prints the results.
     */
    public void printWinner() { 
        // Students: your code goes here.
        int[] results = {human.getScore(),player1.getScore(), player2.getScore(), player3.getScore()};
        
        // find the winning score
        int winningScore = 1 ;
        for (int result : results){
            if (result > 21) result = 0; //cannot exceed 21
            if (result > winningScore) winningScore=result; //scan the array to find the largest number
        }
        
        //counting the appearing times of winning score
        int count = 0;
        for (int result : results){
            if (result == winningScore) count+=1; 
        }
        
        if (count > 1) System.out.println("Tie, nobody wins.");
        else { //find the winner
            if (human.getScore() == winningScore) {
                HumanPlayer winner = human;
                System.out.println(winner.name+" wins with "+ winningScore +" points!"); 
            }
                
            for (ComputerPlayer player : players){
                if (player.getScore() == winningScore) {
                    ComputerPlayer winner = player;
                    System.out.println(winner.name+" wins with "+ winningScore +" points!"); 
                }
            }
        }
    }
}
