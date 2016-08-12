package swmasters.woj.core;

public class Player {
   private String name;          /**< player's name */
   private int score;            /**< player's score */
   private int freeTurnCount;    /**< number of free turns available */
   
   /**
    * @brief Contruct a player with name
    */
   public Player(String name) {
      this.name = name;
   }
   
   /**
    * @brief Return player's score
    *
    * @returns Player's score
    */
   public int getScore() {
      return this.score;
   }

   /**
    * @brief Increase player's score by points
    *
    * @param[in] points
    *    Number of points to increase score by
    */
   public void increaseScoreBy(int points) {
      if (points > 0) {
         this.score += points;
      }
   }

   /**
    * @brief Decrease player's score by points
    *
    * @param[in] points
    *    Number of points to decrease score by
    */
   public void decreaseScoreBy(int points) {
      if (points > 0) {
         this.score -= points;
      }
   }

   /**
    * @brief Zeros out player's score
    */
   public void bankrupt() {
      this.score = 0;
   }

   /**
    * @brief add a free turn
    * Free turn count increases by one
    */
   public void addAFreeTurn() {
      this.freeTurnCount++;
   }

   /**
    * @brief use a free turn
    * Free turn count decreases by one
    */
   public boolean useAFreeTurn() {
      if (this.freeTurnCount <= 0) {
         return false;
      }
      this.freeTurnCount--;
      return true;
   }
   
   /**
    * @brief Return player's name
    *
    * @returns player's name
    */
   public String getPlayerName() {
      return this.name;
   }
   
    /**
    * @brief Return player's free turn count
    *
    * @returns player's free turn count
    */
   public int getfreeTurnCount(){
	   return freeTurnCount;
   }
}
