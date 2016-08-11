package swmasters.woj.core;

public class Question {
  private String question; /**< The text of the question */
  private String answer;   /**< The text of the answer to the question */
  private Integer pointValue; /**< The integer value of the points for the question */
  private boolean answered; /**< The boolean for whether the question has been answered or not */

   /**
    * @brief Construct a Question
    */
  public Question() {

  }

   /**
    * @brief Construct a question from relevant information
    *
    * @param[in] question
    *    The string of the question itself
    * @param[in] answer
    *    The correct answer to the question
    * @param[in] pointValue
    *    The value of the question's points
    * @param[in] answered
    *    Whether the question has been answered or not
    */
  public Question(String question, String answer, Integer pointValue, boolean answered) {
    setQuestion(question);
    setAnswer(answer);
    setPointValue(pointValue);
    setAnswered(answered);
  }

   /**
    * @brief Get the question answer
    *
    * @return answer
    *    The string of the answer to a given question
    */
  public String getAnswer() {
    return answer;
  }

   /**
    * @brief Get whether the question has been answered or not
    *
    * @return answered
    *    The bool value for whether a question has been answered already
    */
  public boolean getAnswered() {
    return answered;
  }

   /**
    * @brief Get the question point value
    *
    * @return answer
    *    The integer of a question's point value
    */
  public Integer getpointValue() {
    return pointValue;
  }

   /**
    * @brief Get the question
    *
    * @return answer
    *    The string of the question
    */
  public String getQuestion() {
    return question;
  }

   /**
    * @brief Set the question answer
    *
    * @param[in] answer
    *    The string of the answer to a given question
    */
  public void setAnswer(String answer) {
    this.answer = answer;
  }

   /**
    * @brief Set whether the question has been answered or not
    *
    * @param[in] answered
    *    The bool value for whether a question has been answered already
    */
  public void setAnswered(boolean answered) {
    this.answered = answered;
  }

   /**
    * @brief Set the question point value
    *
    * @param[in] answer
    *    The integer of a question's point value
    */
  public void setPointValue(Integer pointValue) {
    this.pointValue = pointValue;
  }

   /**
    * @brief Set the question
    *
    * @param[in] answer
    *    The string of the question
    */
  public void setQuestion(String question) {
    this.question = question;
  }

   /**
    * @brief Verifies whether the passed answer is correct
    *
    * @return correct
    *    The boolean value for whether the answer was correct
    */
  public boolean verifyAnswer(String stringToVerify) {
    if(stringToVerify.equals(this.answer)) {
      return true;
    }
    return false;
  }

}
