package swmasters.woj.core;

import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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
   private void onSpinCategory() {
      /** TODO handle category spin */
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
      /** TODO handle lose a turn spin */
      currentPlayer = getNextPlayer();
   }

   /**
    * @brief Handle player spinning opponent's choice
    */
   private void onSpinOpponentsChoice() {
      /** TODO handle opponent's choice spin */
      /** TODO launch category choice dialog with getNextPlayer() selected */
   }

   /**
    * @brief Handle player spinning player's choice
    */
   private void onSpinPlayersChoice() {
      /** TODO handle player's choice spin */
      /** TODO launch category choice dialog with currentPlayer selected */
   }

   /**
    * @brief Handle player spinning a spin again
    */
   private void onSpinSpinAgain() {
      /** TODO handle spin again spin */
      /** This should have UI side effects but no effect on the model */
   }

   /**
    * @brief Add category to categories arraylist
    *
    * @param[in] category
    *     The string of the category to set
    *
    * @param[in] questions
    *     The arraylist of questions to assign to the category
    */
   public void generateCategory(String category, ArrayList<Question> questions) {

       System.out.println(category);
       Category c = new Category();
       c.setName(category);
       categories.add(c);

       for (Question q : questions) {
           c.setQuestion(q);
       }
   }

   /**
    * @brief Select 5 questions for each of the 6 categories
    * from the questions data files
    */
   public void generateQuestions() {

      try {

          JSONParser parser = new JSONParser();

          // Search for all files in questions directory or combine all
          Object obj = (JSONObject) parser.parse(new FileReader(
                  "../../assets/data/questions/questions.json"));

          // Read question database as json object
          JSONObject jsonObject = (JSONObject) obj;
          JSONArray categoryList = (JSONArray) jsonObject.get("database");

          // Read categories from database
          Iterator<JSONObject> c_iterator = categoryList.iterator();
          while (c_iterator.hasNext()) {
              JSONObject cat = c_iterator.next();
              String category = (String) cat.get("Category");

              // Read questions from category
              JSONArray questionList = (JSONArray) cat.get("Questions");
              Iterator<JSONObject> q_iterator = questionList.iterator();
              ArrayList<Question> questions = new ArrayList<Question>();
              while (q_iterator.hasNext()) {
                  JSONObject qa = q_iterator.next();
                  String question = (String) qa.get("question");
                  String answer = (String) qa.get("answer");

                  // Create new question instance with zero point value
                  Question q = new Question(question, answer, 0, false);

                  // Add question to question list
                  questions.add(q);
              }

              // Generate the new category with questions
              generateCategory(category, questions);

          }

      } catch (FileNotFoundException e) {
          e.printStackTrace();
      } catch (IOException e) {
          e.printStackTrace();
      } catch (ParseException e) {
          e.printStackTrace();
      }
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
            onSpinCategory();
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
