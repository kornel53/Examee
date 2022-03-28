package krawczyk.Examee.Server;

import java.io.Serializable;
import java.util.ArrayList;

public class Exam implements Serializable {
	String author;
	int durationInMinutes;
	ArrayList<Question> questions;

}
