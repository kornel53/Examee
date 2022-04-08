package krawczyk.Examee;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ExamManager {
	public static void start() throws IOException {
		Exam exam = null;
		Scanner scanner = new Scanner(System.in);
		int userChoice = -1;
		while(true) {

			System.out.print("-- EXAMEE --\n\n");
			System.out.print("MENU\n");
			System.out.print("1) Start exam\n");
			System.out.print("2) Create a new exam\n");
			System.out.print("3) Read exam from file\n\n");
			System.out.print("0) Exit\n");
			if(exam != null ) System.out.println("-- CURRENT EXAM: " + exam.getTitle() + "--");

			try {
				userChoice = Integer.parseInt(scanner.nextLine());
			} catch(NoSuchElementException e) {
				System.out.println("Invalid input format! Try again...");
				continue;
			}


			switch (userChoice) {
				case 1:

					System.out.println("Starting exam...");
					break;
				case 2:
					System.out.println("Creating a new exam");
					exam = new Exam();
					exam.createNewAndSaveToFile();
					break;
				case 3:
					System.out.println("Reading exam from file");
					File quizDirectory = new File("quiz");
					HashMap<String, String> quizzes = new HashMap<>();
					for( File f : quizDirectory.listFiles()){
						String quizFile = f.getName();
						String quizTitle = Exam.formatFileNameToExamTitle(f.getName());
						quizzes.put(quizTitle, quizFile);
						System.out.println(quizTitle);
					}
					break;
				case 0:
					System.out.println("Exitting...");
					System.exit(0);
				default:
					System.out.println("There is no such option in menu!");
			}

			System.out.print("\033[H\033[2J"); //clears the console
			System.out.flush();
		}
	}

}
