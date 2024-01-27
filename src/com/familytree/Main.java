package com.familytree;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        FamilyTree tree = new FamilyTree();
        FamilyTreeCliProcessor commandProcessor = new FamilyTreeCliProcessor(tree);

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            try {
                sc.skip("(\r\n|[\n\r\u2028\u2029\u0085])?");
                String inputString = sc.nextLine();
                if (!inputString.isEmpty()) {
                    String[] input = inputString.split(" ");
                    if (input[0].equals("exit")) {
                        sc.close();
                        System.exit(0);
                    }
                    commandProcessor.processCommand(input);
                }

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}

