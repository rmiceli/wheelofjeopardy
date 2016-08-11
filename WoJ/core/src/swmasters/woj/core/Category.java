package swmasters.woj.core;

import java.util.ArrayList;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;

public class Category {
   private String name;                      /**< name of category */
   private ArrayList<Question> questions = new ArrayList<Question>();    /**< list of questions selected for this category */

   /**
    * @brief Construct a Category
    */
   public Category() {
   }

   /**
    * @brief Construct a category from a JSON string
    *
    * @param[in] jsonCategory
    *    The JSON that contains the category data
    */
   public Category(JsonValue jsonCategory) {
   }

   /**
    * @brief Change the name of the Category
    *
    * @param[in] name
    *    The name to set
    */
   public void setName(String name) {
      this.name = name;
   }

   /**
    * @brief Assign a question to the Category
    *
    * @param[in] question
    *     The question to set
    */
   public void setQuestion(Question question) {
       questions.add(question);
   }

   /**
    * @brief Used by JsonBeans to serialize this class to JSON
    *
    * @param[in] json
    *    Handle to the Json object
    */
   public void write(Json json) {
      json.writeValue(name);
   }

   /**
    * @brief Used by JsonBeans to serialize this class from JSON
    *
    * @param[in] json
    *    Handle to the Json object
    * @param[in] jsonMap
    *    The JSON to use to fill out this class
    */
   public void read(Json json, JsonValue jsonMap) {
      name = jsonMap.child().name();
   }
   
   /**
    * @brief Get the next unanswered question from this category
    * 
    * @returns current question
    */
   public Question getNextQuestion(){
	   int index = 0;
	   Question currentQuestion = questions.get(index);
	   while(currentQuestion.getAnswered() == true){
		   currentQuestion = questions.get(index + 1);
	   }
	   return currentQuestion;
   }
}
