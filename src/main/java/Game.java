import java.security.SecureRandom;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

public class Game {
    public static void main(String[] args) {
        SecureRandom secureRandom = new SecureRandom();
        KeyAndHMACGenerator keyAndHMACGenerator = new KeyAndHMACGenerator(secureRandom);
        byte[] bytes = new byte[16];
        if (checkArgsOK(args)) {
            while (true) {
                String key = keyAndHMACGenerator.getKey(bytes);
                int computerMove = secureRandom.nextInt(args.length) + 1;
                String hmac = keyAndHMACGenerator.getHMAC(key.getBytes(), args[computerMove - 1]);
                System.out.println("HMAC: " + hmac);
                String playerMove = showMoves(args);
                if (playerMove.equals("0")) {
                    break;
                }
                if (playerMove.equals("?")) {
                    AsciiTable asciiTable = new AsciiTable(args);
                    System.out.println(asciiTable);
                    continue;
                }
                showResults(args, key, args[computerMove - 1], playerMove);
            }
        }
    }

    private static void showResults(String[] args, String key, String arg, String playerMove) {
        try {
            int playerChoice = Integer.parseInt(playerMove);
            if (playerChoice < 0 || playerChoice > args.length) {
                System.out.println("Please, enter correct number!");
            } else if (playerChoice != 0) {
                System.out.println("Your move: " + args[playerChoice - 1]);
                System.out.println("Computer move: " + (arg));
                Rules rules = new Rules(args[playerChoice - 1], arg, args);
                System.out.println(rules.getResult().equals("DRAW") ? "Result: DRAW" : "You: " + rules.getResult());
                System.out.println("HMAC key: " + key);
                System.out.println("----------------------------------");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter number!");
        }
    }

    private static boolean checkArgsOK(String[] args) {
        boolean isArgsOK = true;
        if (args.length < 3 || args.length % 2 == 0) {
            System.out.println("Amount of arguments should be >= 3 and odd!");
            isArgsOK = false;
        }
        if (args.length == 0) {
            System.out.println("No parameters! Please, enter odd amount of arguments.");
            isArgsOK = false;
        }
        Set<String> set = new HashSet<String>(Arrays.asList(args));
        if (set.size() < args.length) {
            System.out.println("Parameters should be unique. You have duplicates");
            isArgsOK = false;
        }
        return isArgsOK;
    }

    private static String showMoves(String[] args) {
        System.out.println("Available moves:");
        int i = 1;
        for (String str : args) {
            System.out.println(i + " - " + str);
            i++;
        }
        System.out.println("0 - exit");
        System.out.println("? - help");
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your move: ");
        return scanner.nextLine();
    }
}
