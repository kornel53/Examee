package krawczyk.Examee.Server;

import java.io.Serializable;
import java.util.ArrayList;

public class Question implements Serializable {
	String mainQuestion;
	ArrayList<String> answers;
	transient int correctAnswer;
	double points;

	public Question(String mainQuestion, ArrayList<String> answers, int correctAnswer, double points) {
		this.mainQuestion = mainQuestion;
		this.answers = answers;
		this.correctAnswer = correctAnswer;
		this.points = points;
	}

	public String getMainQuestion() {
		return mainQuestion;
	}

	public ArrayList<String> getAnswers() {
		return answers;
	}

	public int getCorrectAnswer() {
		return correctAnswer;
	}

	public double getPoints() {
		return points;
	}
}
