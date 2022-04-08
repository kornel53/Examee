package krawczyk.Examee;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Exam implements Serializable {
	private static final long serialVersionUID = 1L;
	private String title;
	private int durationInMinutes;
	private ArrayList<Question> questions;

	public String getTitle() {
		return title;
	}

	public void createNewAndSaveToFile() throws IOException {
		// Exam's data
		Scanner sc = new Scanner(System.in);
		System.out.println("Enter exam's title: ");
		title = sc.nextLine();
		while(true) {
			System.out.println("Enter exam duration in minutes (as natural number): ");
			try {
				durationInMinutes = sc.nextInt();
				sc.nextLine(); //clear buffer
				if(durationInMinutes < 1) {
					System.out.println("Duration must be greater or equal 1! Try again...");
				}
				else break;
			} catch(InputMismatchException e) {
				System.out.println("Invalid input format! Try again...");
			}
		}

		// Questions
		questions = new ArrayList<>();
		int questionQuantity = 1;
		String userAnswer = "";
		System.out.println("Enter the questions and answers (if you want to finish, type q): ");

		while (true) {
			System.out.print("Question " + questionQuantity + ": ");
			userAnswer = sc.nextLine();
			if(userAnswer.equalsIgnoreCase("q") && questionQuantity == 1) {
				System.out.println("The exam needs to have at least one question!");
				continue;
			}
			else if (userAnswer.equalsIgnoreCase("q")) break;
			questions.add(new Question(userAnswer));
			//questionQuantity++;

			// Answers
			int answerQuantity = 1;
			while(true) {
				System.out.print("Answer " + answerQuantity + ": ");
				userAnswer = sc.nextLine();
				if(userAnswer.equalsIgnoreCase("q") && answerQuantity == 1) {
					System.out.println("The question needs to have at least one answer!");
					continue;
				}
				else if (userAnswer.equalsIgnoreCase("q")) {
					while(true) {
						try {
							System.out.println("Which answer is correct (1-" + (answerQuantity-1) + "): ");
							int correctAnswer = Integer.parseInt(sc.nextLine());
							//sc.nextLine(); //clear buffer
							if(correctAnswer > answerQuantity - 1  && correctAnswer < 1) {
								System.out.println("Correct answer number must be between 1 and " + (answerQuantity - 1) + "! Try again...");
							}
							else {
								questions.get(questionQuantity - 1).setCorrectAnswer(correctAnswer);
								break;
							}
						} catch(NumberFormatException e) {
							System.out.println("Invalid input format! Try again...");
						}
					}
					break;
				}

				questions.get(questionQuantity - 1).addAnswer(userAnswer);
				answerQuantity++;
			}
			questionQuantity++;
			//if(userAnswer.equalsIgnoreCase("q")) break;
		}

		System.out.println("Save to file? (Y/N)");
		userAnswer = sc.nextLine();
		if(userAnswer.equalsIgnoreCase("y")) {
			File quizDirectory = new File("quiz");
			quizDirectory.mkdir();
			File targetFile = new File(quizDirectory, formatExamTitleToFileName());
			try (ObjectOutputStream examFile = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(targetFile)))) {
				examFile.writeObject(this);
			}
		} else System.out.println("Not saving created exam to the file.");

	}

	private String formatExamTitleToFileName() {
		String filename = title.trim() + "---";
		filename = filename.replaceAll("\\s", "_");
		LocalDateTime currentData = LocalDateTime.now();
		DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy-HH-MM-SS");
		String formattedData = currentData.format(myFormatObj);
		filename += formattedData;
		filename += ".dat";
		return filename;
	}

	public static String formatFileNameToExamTitle(String filename) {
		filename = filename.substring(0, filename.indexOf("---"));
		filename = filename.replaceAll("_", "\\s");
		return filename;
	}

}
