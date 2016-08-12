package swmasters.woj.core;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;

import swmasters.woj.ui.gameboard.questionboard.QuestionPanel;
import swmasters.woj.ui.gameboard.wheel.FreeSpinDialog;
import swmasters.woj.ui.gameboard.wheel.Sector;
import swmasters.woj.ui.gameboard.wheel.Wheel;

public class Game {

   private final int NUMBER_OF_PLAYERS = 2; /**< maximum number of players allowed */
   private int currentPlayer;               /**< player who has control of the board */
   private ArrayList<Player> players;       /**< list of players */
   private Wheel theWheel;                  /**< the wheel object */
   private ArrayList<Category> categories = new ArrayList<Category>();  /**< the list of categories in this round */
   private Stage stage;						/**< the stage corresponding to the game GUI */

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
	   Question currentQuestion = currentCategory.getNextQuestion();
	   QuestionPanel questionPanel = new QuestionPanel(currentQuestion);
	   questionPanel.show(stage);
	   
	   // Add or subtract points from the player's score based on answer
	   if(currentQuestion.answeredCorrectly() == true){
		   players.get(currentPlayer).increaseScoreBy(currentQuestion.getpointValue());
	   }
	   else{
		   players.get(currentPlayer).decreaseScoreBy(currentQuestion.getpointValue());
	   }
	   
	   // Next player's turn begins
	   currentPlayer = getNextPlayer();
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
	   // Get the current player object and whether they have free spin tokens or not
	   Player player = players.get(currentPlayer);
	   boolean playerSpinCount = false;
	   if(player.getfreeTurnCount() > 0){
		   playerSpinCount = true;
	   };
	   
	   // If the player has free spin tokens, open dialog asking to use token
	   if(playerSpinCount){
		   FreeSpinDialog freeSpin = new FreeSpinDialog(player);
		   freeSpin.show(stage);
		   // If the player chooses Yes, call use token method and spin again method
		   if(freeSpin.returnResult() == true && player.useAFreeTurn()){
			   onSpinSpinAgain();
		   }
		   // If the player chose no and/or has no free tokens, get next player
		   else{
			   currentPlayer = getNextPlayer();
		   }
	   }
	   // If the player has no free tokens, get next player
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
	   
	   // Dummy variable until category widgets implemented
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

	   // Dummy variable until category widgets implemented
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
   private void initGame(Player player1, Player player2, Stage stage) {
      generateQuestionsAndCategories();
      this.theWheel = new Wheel(this.categories);
     this.players = new ArrayList<Player>();
     this.players.add(player1);
     this.players.add(player2);
     this.currentPlayer = 0;
     this.stage = stage;
   }

   /**
    * @brief Construct a 2-player game
    *
    * @param[in] player1
    *    first player
    * @param[in] player2
    *    second player
    */
   public Game(Player player1, Player player2, Stage stage) {
      initGame(player1, player2, stage);
   }
}
