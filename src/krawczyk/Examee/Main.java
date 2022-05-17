package krawczyk.Examee;

import krawczyk.Examee.Server.ExamManager;

import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        ExamManager manager = new ExamManager();
        manager.start();
    }
}
