package krawczyk.Examee;

import java.io.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Exam implements Serializable {
	private String title;
	private int durationInMinutes;
	private ArrayList<Question> questions;


	public static void main(String[] args) throws IOException {
		Exam exam = new Exam();
		exam.readFromFile("exam1.txt");
	}
	/*examFile structure:
	title
	duration in minutes
	questions quantity
	main question
	answers quantity
	answer#1
	answer#2
	answer#n
	correct answer (1..n)
	next main question
	answers quantity
	answer#1
	...
	*/
	void readFromFile(String fileName) {
		try (DataInputStream examFile = new DataInputStream(new FileInputStream(fileName))) {

			title = examFile.readUTF();
			durationInMinutes = examFile.readInt();
			int questionQuantity = examFile.readInt();
			while(questionQuantity-- > 0) {
				String mainQuestion = examFile.readUTF();
				ArrayList<String> answersFromFile = new ArrayList<>();
				int answerQuantity = examFile.readInt();
				while(answerQuantity-- > 0){
					answersFromFile.add(examFile.readUTF());
				}
				int correctAnswer = examFile.readInt();
				questions.add(new Question(mainQuestion, answersFromFile, correctAnswer));
			}
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		System.out.println(title + ", " + durationInMinutes + " mins to complete");
		for(Question question : questions) {
			System.out.println(question.getMainQuestion());
			System.out.println(question.getAnswers());
		}
	}

	void createNewAndSaveToFile() throws IOException {
		// Exam's data
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter exam's title: ");
		title = sc.nextLine();
		while(true) {
			System.out.println("Enter exam duration in minutes (as natural number): ");
			try {
				durationInMinutes = sc.nextInt();
				if(durationInMinutes < 1) {
					System.out.println("Duration must be greater or equal 1! Try again...");
				}
				else break;
			} catch(InputMismatchException e) {
				System.out.println("Invalid input format! Try again...");
			}
		}

		// Questions
		ArrayList<Question> questions = new ArrayList<>();
		int questionQuantity = 1;
		String userAnswer = "";
		System.out.println("Enter the questions and answers (if you want to finish, enter q): ");

		while (true) {
			System.out.print("Question " + questionQuantity + ": ");
			userAnswer = sc.nextLine();
			if(userAnswer.equalsIgnoreCase("q") && questionQuantity == 1) {
				System.out.println("The exam needs to have at least one question!");
				continue;
			}
			else if (userAnswer.equalsIgnoreCase("q")) break;
			questions.add(new Question(userAnswer));
			questionQuantity++;

			// Answers
			int answerQuantity = 1;
			while(true) {
				System.out.print("Answer " + answerQuantity + ": ");
				answerQuantity = sc.nextInt();
				if(userAnswer.equalsIgnoreCase("q") && answerQuantity == 1) {
					System.out.println("The question needs to have at least one answer!");
					continue;
				}
				else if (userAnswer.equalsIgnoreCase("q")) {
					while(true) {
						try {
							System.out.println("Which answer is correct (1-" + answerQuantity + "): ");
							int correctAnswer = sc.nextInt();
							if(correctAnswer > answerQuantity && correctAnswer < 1) {
								System.out.println("Correct answer number must be between 1 and " + answerQuantity + "! Try again...");
							}
							else {
								questions.get(questionQuantity - 1).setCorrectAnswer(correctAnswer);
								break;
							}
						} catch(InputMismatchException e) {
							System.out.println("Invalid input format! Try again...");
						}
					}
					break;
				}

				questions.get(questionQuantity - 1).addAnswer(userAnswer);
				answerQuantity++;
			}
			if(userAnswer.equalsIgnoreCase("q")) break;
		}

		System.out.println("Save to file? (Y/N)");
		userAnswer = sc.nextLine();
		if(userAnswer.equalsIgnoreCase("y")) {
			ObjectOutputStream examfile = new ObjectOutputStream(new FileOutputStream(formatExamTitleToFileName()));
		}
		sc.close();
	}

	private String formatExamTitleToFileName() {
		String filename = title;
		LocalDateTime currentData = LocalDateTime.now();
		filename.replaceAll(" ", "_");
		filename += currentData.toString();
		filename += ".dat";
		return filename;
	}

	//abstract void createNewExam();
}
