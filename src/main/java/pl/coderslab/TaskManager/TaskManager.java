package pl.coderslab.TaskManager;

import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
        taskManagerCore();
    }

    public static void taskManagerCore() {
        String welcome = "Please select an option";
        String input = "";
        boolean isTrue = true;
        while (isTrue) {
            System.out.println(welcome);
            for (KeyWord word : KeyWord.values()) {
                System.out.println(word);
            }
            Scanner scanner = new Scanner(System.in);
            input = scanner.nextLine();
            KeyWord keyWord;

            try {
                keyWord = KeyWord.valueOf(input.toLowerCase());
            } catch (RuntimeException e) {
                System.out.println("Invalid option enter one of the provided options Setting default to  task list");
                keyWord = KeyWord.list;
            }
            switch (keyWord) {
                case add:
                    System.out.println("add");
                    addTask();
                    break;
                case remove:
                    System.out.println("remove");
                    removeTask();
                    break;
                case list:
                    System.out.println("list:");
                    printTaskList();
                    break;
                case exit:
                    System.out.println("exit");
                    isTrue = false;
                    scanner.close();

            }
        }
    }


    public static void addTask() {
        StringBuilder chain = new StringBuilder();
        System.out.println("Please add task description");
        Scanner scanner1 = new Scanner(System.in);
        chain.append(scanner1.nextLine()).append(" ");
        System.out.println("Please add task due date");
        chain.append(scanner1.nextLine()).append(" ");
        System.out.println("Is your task is important: true/false");
        while (!scanner1.hasNextBoolean()) {
            scanner1.next();
            System.out.println("Enter true or false");

        }
        chain.append(scanner1.nextBoolean()).append("\n");

        Path path = Paths.get("./src/main/resources/tasks.csv");
        try {
            Files.writeString(path, chain, StandardOpenOption.APPEND);
        } catch (IOException ex) {
            System.out.println("The file could not be saved");

        }
        //scanner1.close();

    }

    public static void removeTask() {
        int counter = 0;
        int index;
        StringBuilder chain1 = new StringBuilder();
        String forReplace = "";
        String[] tab = new String[0];
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Please select number to remove");
        String ifIsParsable = scanner2.nextLine();
        if (!NumberUtils.isParsable(ifIsParsable) && !(Integer.parseInt(ifIsParsable) >= 0)) {
            System.out.println("Incorrect argument passed. Please give number greater or equal 0");
        }
        index = Integer.parseInt(ifIsParsable);
        System.out.println(index);
        Path path = Paths.get("./src/main/resources/tasks.csv");
        try {
            counter = Files.readAllLines(path).size();
            System.out.println(counter);
            if (index < counter) {
                tab = Files.readAllLines(path).toArray(new String[0]);
                System.out.println(tab.length);
            }
        } catch (IOException e) {
            System.out.println(e);

        }
        String[] forCopyToFile = ArrayUtils.remove(tab, index);
        for (String copy : forCopyToFile) {
            chain1.append(copy).append("\n");
        }
        forReplace = chain1.toString();
        System.out.println(forReplace);
        Path path1 = Paths.get("./src/main/resources/tasks.csv");

        try {
            Files.writeString(path1, forReplace);
            if (tab.length > forCopyToFile.length) {
                System.out.println(index + "\n" + "Value was successfully deleted");

            }
        } catch (IOException ex) {
            System.out.println("The file could not be saved");
        }

    }

    public static void printTaskList() {
        Path path = Paths.get("./src/main/resources/tasks.csv");
        try {
            for (String line : Files.readAllLines(path))
                System.out.println(line);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
