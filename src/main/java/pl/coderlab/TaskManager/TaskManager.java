package pl.coderlab.TaskManager;

import java.util.Scanner;

public class TaskManager {
    public static void main(String[] args) {
taskManagerCore();
    }

    public static void taskManagerCore() {
        String welcome = "Please select an option";
        StringBuilder eNum = new StringBuilder();

        boolean isTrue = true;
        while (isTrue) {
            System.out.println(welcome);
            for (KeyWord word : KeyWord.values()) {
                System.out.println(word);
                eNum.append(word).append(", ");
            }
            Scanner scanner = new Scanner(System.in);
            String input = scanner.nextLine();
            KeyWord keyWord;
            try{
                keyWord = KeyWord.valueOf(input.toLowerCase());
            }catch(IllegalArgumentException e){
                System.out.println("Invalid option enter one of the provided options "+ eNum + "Setting default to  task list");
                keyWord =KeyWord.list;
            }
            switch (keyWord){
                case add:
                    System.out.println("add");
                    break;
                case remove:
                    System.out.println("remove");
                    break;
                case list:
                    System.out.println("list");
                    break;
                case exit:
                    System.out.println("exit");
                    isTrue = false;

            }
        }

    }
}
