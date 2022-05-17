package krawczyk.Examee.Client;

import krawczyk.Examee.Exam;
import krawczyk.Examee.Question;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
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
		System.out.println("Starting server");
		s = new Socket(host.getHostName(), port);
		System.out.println("Server started.");
		System.out.println("Waiting for data...");
		Thread.sleep(100);
		oos = new ObjectOutputStream(s.getOutputStream());
		System.out.println("Data received...");
		oos.writeObject(studentName);
		Thread.sleep(100);
		ois = new ObjectInputStream(s.getInputStream());
		Exam exam = (Exam) ois.readObject();

		do {
			System.out.println("Are you ready to start an exam? (Y/N)");
		} while (!scanner.nextLine().equalsIgnoreCase("y"));

		oos.writeObject(startExam(exam));

		ois.close();
		oos.close();
		s.close();
	}

	private static ArrayList<Integer> startExam(Exam exam) {
		ArrayList<Integer> answers = new ArrayList<>();
		System.out.println("Exam: " + exam.getTitle());
		int questionCounter = 1;
		for(Question question : exam.getQuestions()) {
			System.out.print("Question " + questionCounter++ + ": ");
			System.out.println(question.getMainQuestion());
			int answerCounter = 1;
			for(String answer : question.getAnswers()) {
				System.out.print("\t" + answerCounter++ + ". ");
				System.out.println(answer);
			}
			System.out.println("Which answer is correct? (1-" + (answerCounter-1) +"): ");
			Scanner scanner = new Scanner(System.in);
			answers.add(scanner.nextInt());
		}
		return answers;
	}
}

