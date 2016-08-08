package swmasters.woj.core;

public class Question {
	private String question; /**< The text of the question */
	private String answer;   /**< The text of the answer to the question */
	private Integer pointValue; /**< The integer value of the points for the question */
	private boolean answered; /**< The boolean for whether the question has been answered or not */
	
	public Question() {
		
	}
	
	public Question(String question, String answer, Integer pointValue, boolean answered) {
		setQuestion(question);
		setAnswer(answer);
		setPointValue(pointValue);
		setAnswered(answered);
	}

	public String getAnswer() {
		return answer;
	}
	
	public boolean getAnswered() {
		return answered;
	}

	public Integer getpointValue() {
		return pointValue;
	}
	
	public String getQuestion() {
		get question;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}
	
	public void setAnswered(boolean answered) {
		this.answered = answered;
	}

	public void setpointValue(Integer pointValue) {
		this.pointValue = pointValue;
	}
	
	public void setQuestion(String question) {
		this.question = question;
	}
	
	public boolean verifyAnswer(String stringToVerify) {
		/* Needs to be written */
	}

}
