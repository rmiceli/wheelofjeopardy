package swmasters.woj.model;

import java.util.ArrayList;

import swmasters.woj.ui.gameboard.wheel.Sector;
import swmasters.woj.ui.gameboard.wheel.Wheel;

public class Game {

   private final int NUMBER_OF_PLAYERS = 2; /**< maximum number of players allowed */
   private int currentPlayer;               /**< player who has control of the board */
   private ArrayList<Player> players;       /**< list of players */
   private Wheel theWheel;                  /**< the wheel object */
   private ArrayList<Category> categories;  /**< the list of categories in this round */

   /**
    * @brief returns the next player in the list
    */
   private int getNextPlayer() {
      return (currentPlayer + 1) % NUMBER_OF_PLAYERS;
   }

   /**
    * @brief Handle player spinning a bankrupt
    * Ends current player's turn with no chance for a free spin
    */
   private void onSpinBankrupt() {
      players.get(currentPlayer).bankrupt();
      currentPlayer = getNextPlayer();
   }

   /**
    * @brief Handle player spinning a category
    */
   private void onSpinCategory() {

   }

   /**
    * @brief Handle player spinning a free turn
    */
   private void onSpinFreeTurn() {
      players.get(currentPlayer).addAFreeTurn();
   }

   /**
    * @brief Handle player spinning lose a turn
    */
   private void onSpinLoseTurn() {
      currentPlayer = getNextPlayer();
   }

   /**
    * @brief Handle player spinning opponent's choice
    */
   private void onSpinOpponentsChoice() {
      /** TODO launch category choice dialog with getNextPlayer() selected */
   }

   /**
    * @brief Handle player spinning player's choice
    */
   private void onSpinPlayersChoice() {
      /** TODO launch category choice dialog with currentPlayer selected */
   }

   /**
    * @brief Handle player spinning a spin again
    */
   private void onSpinSpinAgain() {
      /* this has UI side effects but no effect on the model */
   } 

   /**
    * @brief Select 6 categories from the data file
    */
   public void generateCategories() {
      categories = new ArrayList<Category>();
      /** TODO read in categories from file and
       *  randomly pick 6 of them for this game */
   }

   /**
    * @brief Select 5 questions for each of the 6 categories
    * from the questions/* data files
    */
   public void generateQuestions() {
      /** TODO read in question file for each category and
       *  randomly pick 5 questions from the list */
   }

   /**
    * @brief Handle spin wheel UI event
    */
   public void onSpinWheel() {
      Sector sector = theWheel.spin();
      switch (sector.getType()) {
         case SECTOR_TYPE_BANKRUPT:
            onSpinBankrupt();
            break;
         case SECTOR_TYPE_CATEGORY:
            /** TODO handle category spin */
            break;
         case SECTOR_TYPE_FREE_TURN:
            /** TODO handle free turn spin */
            break;
         case SECTOR_TYPE_LOSE_TURN:
            /** TODO handle lose a turn spin */
            break;
         case SECTOR_TYPE_OPPONENTS_CHOICE:
            /** TODO handle opponent's choice spin */
            break;
         case SECTOR_TYPE_PLAYERS_CHOICE:
            /** TODO handle player's choice spin */
            break;
         case SECTOR_TYPE_SPIN_AGAIN:
            /** TODO handle spin again spin */
            break;
      }
   }

   /**
    * @return 
    * @brief initialize a 2-player game
    *
    * @param[in] player1
    *    first player
    * @param[in] player2
    *    second player
    */
   private void initGame(Player player1, Player player2) {
      generateCategories();
      generateQuestions();
      this.theWheel = new Wheel(this.categories);
     this.players = new ArrayList<Player>();
     this.players.add(player1);
     this.players.add(player2);
     this.currentPlayer = 0;
   }
   
   /**
    * @brief Construct a 2-player game
    *
    * @param[in] player1
    *    first player
    * @param[in] player2
    *    second player
    */
   public Game(Player player1, Player player2) {
      initGame(player1, player2);
   }
}
