package krawczyk.Examee.Client;

import krawczyk.Examee.Exam;
import krawczyk.Examee.Question;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class ExamClient {

	public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
		int port = 1200;
		InetAddress host = InetAddress.getLocalHost();
		Socket s = null;
		ObjectOutputStream oos = null;
		ObjectInputStream ois = null;


		System.out.println("What's your name?");
		Scanner scanner = new Scanner(System.in);
		String studentName = scanner.nextLine();
		System.out.println("Searching for exam server...");
		final int MAX_RETRIES = 5;
		for(int retry=1; retry <= MAX_RETRIES; retry++){
			try {
				s = new Socket(host.getHostName(), port);
				break;
			} catch (ConnectException e) {
				System.out.println("Server is not responding. Trying again... (" + retry + "/" + MAX_RETRIES + ")");
				Thread.sleep(3000);
			}
		}
		if(s == null) {
			System.out.println("Connection failed. Exitting...");
			System.exit(0);
		}

		System.out.println("Connected. Waiting for data...");
		Thread.sleep(100);
		oos = new ObjectOutputStream(s.getOutputStream());
		oos.writeObject(studentName);
		Thread.sleep(100);
		ois = new ObjectInputStream(s.getInputStream());
		Exam exam = (Exam) ois.readObject();
		System.out.println("Data received.");

		do {
			System.out.println("Are you ready to start an exam? (Y/N)");
		} while (!scanner.nextLine().equalsIgnoreCase("y"));

		System.out.print("\033[H\033[2J"); //clears the console
		System.out.flush();

		oos.writeObject(startExam(exam));

		ois.close();
		oos.close();
		s.close();
		System.out.println("Exam " + exam.getTitle() + " finished. Your mark will be published by the teacher. Thank you!");
	}

	private static ArrayList<Integer> startExam(Exam exam) {
		ArrayList<Integer> answers = new ArrayList<>();
		int questionCounter = 1;
		for(Question question : exam.getQuestions()) {
			System.out.println("Exam: " + exam.getTitle());
			System.out.print("Question " + questionCounter++ + ": ");
			System.out.println(question.getMainQuestion());
			int answerCounter = 1;
			for(String answer : question.getAnswers()) {
				System.out.print("\t" + answerCounter++ + ". ");
				System.out.println(answer);
			}
			Scanner scanner = new Scanner(System.in);
			while(true) {
				try {
					System.out.println("Which answer is correct (1-" + (answerCounter-1) + "): ");
					int correctAnswer = Integer.parseInt(scanner.nextLine());
					if(correctAnswer > answerCounter - 1  && correctAnswer < 1) {
						System.out.println("Correct answer number must be between 1 and " + (answerCounter - 1) + "! Try again...");
					}
					else {
						answers.add(correctAnswer);
						break;
					}
				} catch(NumberFormatException e) {
					System.out.println("Invalid input format! Try again...");
				}
			}
			System.out.print("\033[H\033[2J"); //clears the console
			System.out.flush();
		}
		return answers;
	}
}
