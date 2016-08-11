package swmasters.woj.core;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.Gdx;

import swmasters.woj.ui.gameboard.questionboard.QuestionPanel;
import swmasters.woj.ui.gameboard.wheel.Sector;
import swmasters.woj.ui.gameboard.wheel.Wheel;

public class Game {

   private final int NUMBER_OF_PLAYERS = 2; /**< maximum number of players allowed */
   private int currentPlayer;               /**< player who has control of the board */
   private ArrayList<Player> players;       /**< list of players */
   private Wheel theWheel;                  /**< the wheel object */
   private ArrayList<Category> categories = new ArrayList<Category>();  /**< the list of categories in this round */

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
   private void onSpinCategory(Category currentCategory) {
      /** TODO handle category spin */
	   Question currentQuestion = currentCategory.getNextQuestion();

	   // Must display question panel
	   // Create a new dialog with question text, textbox, and submit button
	   //QuestionPanel questionPanel = new QuestionPanel(currentQuestion.getQuestion());
	   QuestionPanel questionPanel = new QuestionPanel(currentQuestion);
	   // How to get WoJ stage variable here as input?
	   //questionPanel.show(stage);
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
      /** TODO add prompt to user to use token or not */
	   Player player = players.get(currentPlayer);
	   if(player.useAFreeTurn() == true){
		   onSpinSpinAgain();
	   }
	   else{
		   currentPlayer = getNextPlayer();
	   }
   }

   /**
    * @brief Handle player spinning opponent's choice
    */
   private void onSpinOpponentsChoice() {
      /** TODO handle opponent's choice spin */
      /** TODO launch category choice dialog with getNextPlayer() selected */
	   int previousPlayer = currentPlayer;
	   currentPlayer = getNextPlayer();

	   // Next player chooses category for the current player
	   // Must display choices and retrieve their chosen category
	   Category chosenCategory = new Category();

	   // Return control to current player and go to category spin event
	   currentPlayer = previousPlayer;
	   onSpinCategory(chosenCategory);
   }

   /**
    * @brief Handle player spinning player's choice
    */
   private void onSpinPlayersChoice() {
      /** TODO handle player's choice spin */
      /** TODO launch category choice dialog with currentPlayer selected */

	   // Current player chooses their category
	   // Must display choices and retrieve their chosen category
	   Category chosenCategory = new Category();

	   // Go to category spin event
	   onSpinCategory(chosenCategory);
   }

   /**
    * @brief Handle player spinning a spin again
    */
   private void onSpinSpinAgain() {
      /** TODO handle spin again spin */
      /** This should have UI side effects but no effect on the model */
   }

   /**
    * @brief Import questions and categories from android/assets/questions.json
    * to categories ArrayList
    *
    */
   public void generateQuestionsAndCategories() {

       Json json = new Json();
       categories = json.fromJson(ArrayList.class,Gdx.files.local("../../assets/data/questions/questions.json"));
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
            onSpinCategory(sector.getCategory());
            break;
         case SECTOR_TYPE_FREE_TURN:
            onSpinFreeTurn();
            break;
         case SECTOR_TYPE_LOSE_TURN:
            onSpinLoseTurn();
            break;
         case SECTOR_TYPE_OPPONENTS_CHOICE:
            onSpinOpponentsChoice();
            break;
         case SECTOR_TYPE_PLAYERS_CHOICE:
            onSpinPlayersChoice();
            break;
         case SECTOR_TYPE_SPIN_AGAIN:
            onSpinSpinAgain();
            break;
      }
   }

   /**
    * @return
    * @brief Initialize a 2-player game
    *
    * @param[in] player1
    *    first player
    * @param[in] player2
    *    second player
    */
   private void initGame(Player player1, Player player2) {
      generateQuestionsAndCategories();
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
