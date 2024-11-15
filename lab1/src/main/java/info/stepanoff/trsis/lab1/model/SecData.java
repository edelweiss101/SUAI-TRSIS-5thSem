package info.stepanoff.trsis.lab1.model;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

public class SecData {
    public static HashMap<Integer, Employee> employees = new HashMap<Integer, Employee>();
    public static HashMap<Integer, Room> rooms = new HashMap<Integer, Room>();
    public static HashMap<Integer, HashSet<Integer>> access = new HashMap<Integer, HashSet<Integer>>();
    public static final char[] ALPHABET = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

    public static void initTestData() {
        employees.clear();
        rooms.clear();
        access.clear();
        for (int i = 0; i < 10; ++i){
            employees.put(i, new Employee(
                    i,
                    "Name" + ALPHABET[i],
                    "Dep" + ALPHABET[i],
                    "Pos" + ALPHABET[i]));
            rooms.put(i, new Room(
                    i,
                    "Room" + Integer.toString(i),
                    "Dep" + Integer.toString(i),
                    i
            ));
        }
    }

    // Writing .html file to String
    public static String readHTML(String path) {
        StringBuilder contentBuilder = new StringBuilder();
        try {
            BufferedReader in = new BufferedReader(new FileReader(path));
            String str;
            while ((str = in.readLine()) != null) {
                contentBuilder.append(str);
            }
            in.close();
        } catch (IOException e) {
            return "";
        }
        return contentBuilder.toString();
    }
}
