package com.familytree;

import com.familytree.exception.InvalidInputException;

public class FamilyTreeCliProcessor {
    private FamilyTree familyTree;

    FamilyTreeCliProcessor(FamilyTree familyTree) {
        this.familyTree = familyTree;
    }

    public void processCommand(String[] parts) throws InvalidInputException {
        String action = parts[0].toLowerCase();

        switch (action) {
            case "add":
                if (parts.length >= 4 && parts[1].equals("person")) {
                    String name = parts[2];
                    familyTree.addPerson(name);
                    System.out.println("Person added: " + name);
                } else if (parts.length >= 4 && parts[1].equals("relationship")) {
                    String name = parts[2];
                    RelationType relationType = RelationType.valueOf(parts[3].toUpperCase());
                    familyTree.addRelationship(name, relationType);
                    System.out.println("Relationship added: " + relationType + " for " + name);
                } else {
                    System.out.println("Invalid add command!");
                }
                break;
            case "connect":
                if (parts.length >= 8 && parts[2].equals("as") && parts[5].equals("of") && parts[7].equals("as")) {
                    String name1 = parts[1];
                    RelationType relationType1 = RelationType.valueOf(parts[3].toUpperCase());
                    String name2 = parts[6];
                    RelationType relationType2 = RelationType.valueOf(parts[8].toUpperCase());
                    familyTree.connectPeople(name1, relationType1, name2, relationType2);
                } else {
                    System.out.println("Invalid connect command!");
                }
                break;
            case "count":
                if (parts.length >= 5 && parts[1].equals("sons") && parts[2].equals("of")) {
                    String name = parts[3];
                    int count = familyTree.countSons(name);
                    System.out.println("Number of sons of " + name + ": " + count);
                } else if (parts.length >= 5 && parts[1].equals("daughters") && parts[2].equals("of")) {
                    String name = parts[3];
                    int count = familyTree.countDaughters(name);
                    System.out.println("Number of daughters of " + name + ": " + count);
                } else if (parts.length >= 4 && parts[1].equals("wives") && parts[2].equals("of")) {
                    String name = parts[3];
                    int count = familyTree.countWives(name);
                    System.out.println("Number of wives of " + name + ": " + count);
                } else {
                    System.out.println("Invalid count command!");
                }
                break;
            case "father":
                if (parts.length >= 3 && parts[1].equals("of")) {
                    String name = parts[2];
                    String fatherName = familyTree.getFather(name);
                    System.out.println("Father of " + name + ": " + fatherName);
                } else {
                    System.out.println("Invalid father command!");
                }
                break;
            default:
                System.out.println("Invalid command!");
        }
    }
}
