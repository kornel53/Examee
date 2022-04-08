package krawczyk.Examee;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
	private String mainQuestion;
	private ArrayList<String> answers;
	private transient int correctAnswer; // no point to serialize that

	public Question(String mainQuestion) {
		this.mainQuestion = mainQuestion;
		this.answers = new ArrayList<>();
	}
	public Question(String mainQuestion, ArrayList<String> answers, int correctAnswer) {
		this.mainQuestion = mainQuestion;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
	}

	public String getMainQuestion() {
		return mainQuestion;
	}
	public void addAnswer(String answer) {
		this.answers.add(answer);
	}
	public void setMainQuestion(String mainQuestion) {
		this.mainQuestion = mainQuestion;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}

	public void setAnswers(ArrayList<String> answers) {
		this.answers = answers;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public void setCorrectAnswer(int correctAnswer) {
		this.correctAnswer = correctAnswer;
	}
}
