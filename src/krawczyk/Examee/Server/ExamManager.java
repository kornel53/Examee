package krawczyk.Examee.Server;

import krawczyk.Examee.Exam;
import krawczyk.Examee.Question;

import java.io.*;
import java.net.ServerSocket;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;

public class ExamManager {
	Exam exam = null;
	public void start() throws IOException, ClassNotFoundException {

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
					try {
						System.out.println("Starting exam..." + exam.getTitle());
						startExam();
					}
					catch (NullPointerException | InterruptedException e){
						System.out.println("Ain't exam is loaded. Choose an exam and try again");
						continue;
					}
					break;
				case 2:
					System.out.println("Creating a new exam");
					exam = new Exam();
					exam.createNewAndSaveToFile();
					break;
				case 3:
					System.out.println("Existing exams:");
					File quizDirectory = new File("quiz");
					quizDirectory.mkdir();
					HashMap<String, String> quizzes = new HashMap<>();
					boolean examsExist = false;
					for( File f : quizDirectory.listFiles()){
						String quizFile = f.getName();
						String quizTitle = Exam.formatFileNameToExamTitle(f.getName());
						quizzes.put(quizTitle, quizFile);
						System.out.println("\t" + quizTitle);
						examsExist = true;
					}
					boolean success = false;
					while(!success && examsExist) {
						System.out.println("Enter a title: ");
						String chosenExamTitle = scanner.nextLine();
						for(Map.Entry<String, String> quiz : quizzes.entrySet()) {
							if(quiz.getKey().equalsIgnoreCase(chosenExamTitle)) {
								readExamFromFile(quiz.getValue());
								System.out.println(quiz.getValue());
								success = true;
								break;
							}
						}
					}
					if(!success) {
						System.out.println("Exam not found.");
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

	private void startExam() throws IOException, ClassNotFoundException, InterruptedException {
		ExamServer examServer = new ExamServer(exam);
		examServer.runServer();
	}

	private void readExamFromFile(String chosenExam) {
		try(ObjectInputStream readExam = new ObjectInputStream(new BufferedInputStream(new FileInputStream("quiz/" +chosenExam)))) {
			boolean eof = false;
			while (!eof) {
				try {
					exam = (Exam) readExam.readObject();
					System.out.println("Found exam " + exam.getTitle());
					for(Question question : exam.getQuestions()) {
						System.out.println("\tQuestion: " + question.getMainQuestion());
						for(String answer : question.getAnswers()) {
							System.out.println("\t\t" + answer);
						}
					}
				} catch (EOFException e) {
					eof = true;
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

}
