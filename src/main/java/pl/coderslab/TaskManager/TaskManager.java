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
            System.out.println(ConsoleColors.BLUE + welcome);
            for (KeyWord word : KeyWord.values()) {
                System.out.println(ConsoleColors.RESET + word);
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
                    addTask();
                    break;
                case remove:
                    removeTask();
                    break;
                case list:
                    printTaskList();
                    break;
                case exit:
                    isTrue = false;
                    scanner.close();
                    System.out.println(ConsoleColors.RED + "Bye, bye");

            }
        }
    }

    public static void addTask() {
        StringBuilder chain = new StringBuilder();
        System.out.println("Please add task description");
        Scanner scanner1 = new Scanner(System.in);
        chain.append(scanner1.nextLine()).append(", ");
        System.out.println("Please add task due date");
        chain.append(scanner1.nextLine()).append(", ");
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

    }

    public static void removeTask() {
        int counter = 0;
        int index;
        StringBuilder chain1 = new StringBuilder();
        String forReplace;
        String[] tab = new String[0];
        Scanner scanner2 = new Scanner(System.in);
        System.out.println("Please select number to remove");
        String ifIsParsable = scanner2.nextLine();
        if (NumberUtils.isParsable(ifIsParsable) && Integer.parseInt(ifIsParsable) >= 0) {
            index = Integer.parseInt(ifIsParsable);
            Path path = Paths.get("./src/main/resources/tasks.csv");
            try {
                counter = Files.readAllLines(path).size();
                if (index < counter) {
                    tab = Files.readAllLines(path).toArray(new String[0]);
                    System.out.println(tab.length);
                }
            } catch (IOException | ArrayIndexOutOfBoundsException e) {
                System.out.println(e);
            }

            String[] forCopyToFile = ArrayUtils.remove(tab, index);
            for (String copy : forCopyToFile) {
                chain1.append(copy).append("\n");
            }

            forReplace = chain1.toString();
            Path path1 = Paths.get("./src/main/resources/tasks.csv");

            try {
                Files.writeString(path1, forReplace);
                if (tab.length > forCopyToFile.length) {
                    System.out.println(index + "\n" + "Value was successfully deleted");
                }
            } catch (IOException ex) {
                System.out.println("The file could not be saved");
            }
        } else
            System.out.println("Incorrect argument passed. Please give number greater or equal 0");

    }
    public static void printTaskList() {
        Path path = Paths.get("./src/main/resources/tasks.csv");
        try {
            int numIndex = 0;
            for (String line : Files.readAllLines(path)) {
                System.out.println(numIndex + " : " + line);
                numIndex++;
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
