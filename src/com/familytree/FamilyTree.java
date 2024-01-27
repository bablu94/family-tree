package com.familytree;

import com.familytree.exception.InvalidInputException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FamilyTree {
    private Person root;
    private Map<String, Boolean> visited = new HashMap<>();

    public Person getRoot() {
        return root;
    }

    public void setRoot(Person root) {
        this.root = root;
    }

    public void addPerson(String name) {
        if (this.root == null) {
            this.root = new Person(name, Gender.FEMALE);
        } else {
            System.out.println("Family tree already has a root!");
        }
    }

    public void addRelationship(String name, RelationType relationType) throws InvalidInputException {
        Person person = findPerson(this.root, name);
        if (person == null) {
            throw new InvalidInputException("Invalid Input");
        }

        TreeRelationType treeRelationType = convertToTreeRelationType(relationType);
        Gender gender = fetchGender(relationType);

        Person newPerson = new Person(name, gender);
        addRelation(treeRelationType, person, TreeRelationType.CHILD, newPerson);

        if (treeRelationType == TreeRelationType.CHILD) {
            for (Relation relation : person.getRelations()) {
                if (TreeRelationType.SPOUSE.equals(relation.getType())) {
                    person = relation.getPerson();
                    break;
                }
            }
            addRelation(treeRelationType, person, TreeRelationType.CHILD, newPerson);
        }
    }


    public void connectPeople(String name1, RelationType relationType1, String name2, RelationType relationType2)
            throws InvalidInputException {
        TreeRelationType treeRelationType1 = convertToTreeRelationType(relationType1);
        TreeRelationType treeRelationType2 = convertToTreeRelationType(relationType2);
        Gender gender1 = fetchGender(relationType1);
        Gender gender2 = fetchGender(relationType2);

        if (this.root == null) {
            Person person1 = new Person(name1, gender1);
            Person person2 = new Person(name2, gender2);
            this.root = person1;
            addRelation(treeRelationType1, person1, treeRelationType2, person2);
        } else {
            Person person1 = findPerson(this.root, name1);
            if (person1 == null) {
                throw new InvalidInputException("Invalid Input");
            }
            Person person2 = new Person(name2, gender2);
            addRelation(treeRelationType1, person1, treeRelationType2, person2);
            if (treeRelationType1 == TreeRelationType.CHILD) {
                for (Relation relation : person1.getRelations()) {
                    if (TreeRelationType.SPOUSE.equals(relation.getType())) {
                        person1 = relation.getPerson();
                        break;
                    }
                }
                addRelation(treeRelationType1, person1, treeRelationType2, person2);
            }
        }
    }

    private Person findPerson(Person cur, String name) {
        this.visited.put(cur.getName(), Boolean.TRUE);
        if (cur.getName().equals(name)) {
            this.visited.clear();
            return cur;
        } else {
            for (Relation relation : cur.getRelations()) {
                Person person2 = relation.getPerson();
                if (!visited.containsKey(person2.getName())) {
                    Person person = findPerson(person2, name);
                    if (person != null) {
                        return person;
                    }
                }
            }
        }
        return null;
    }

    public void addRelation(TreeRelationType type1, Person person1, TreeRelationType type2, Person person2) {
        Relation relation1 = new Relation(type1, person1);
        person1.addRelation(relation1);

        Relation relation2 = new Relation(type2, person2);
        person2.addRelation(relation2);
    }


    private TreeRelationType convertToTreeRelationType(RelationType type) {
        if (RelationType.MOTHER.equals(type) || RelationType.FATHER.equals(type))
            return TreeRelationType.CHILD;
        else if (RelationType.HUSBAND.equals(type) || RelationType.WIFE.equals(type))
            return TreeRelationType.SPOUSE;
        else
            return TreeRelationType.PARENT;
    }

    private Gender fetchGender(RelationType type) {
        if (RelationType.MOTHER.equals(type) || RelationType.DAUGHTER.equals(type) || RelationType.WIFE.equals(type))
            return Gender.FEMALE;
        else
            return Gender.MALE;
    }

    public int countSons(String name) throws InvalidInputException {
        List<Person> children = fetchChildren(name);
        int count = 0;
        for (Person child : children) {
            if (Gender.MALE.equals(child.getGender())) {
                count++;
            }
        }
        return count;
    }

    public int countDaughters(String name) throws InvalidInputException {
        List<Person> children = fetchChildren(name);
        int count = 0;
        for (Person child : children) {
            if (Gender.FEMALE.equals(child.getGender())) {
                count++;
            }
        }
        return count;
    }

    public int countWives(String name) throws InvalidInputException {
        List<Person> spouses = fetchSpouses(name);
        int count = 0;
        for (Person spouse : spouses) {
            if (Gender.FEMALE.equals(spouse.getGender())) {
                count++;
            }
        }
        return count;
    }

    public String getFather(String name) throws InvalidInputException {
        Person father = fetchFather(name);
        if (father != null) {
            return father.getName();
        } else {
            return "Not found";
        }
    }

    private List<Person> fetchChildren(String name) throws InvalidInputException {
        Person person = findPerson(this.root, name);
        if (person == null) {
            throw new InvalidInputException("Invalid Input");
        }
        List<Person> children = new ArrayList<>();
        for (Relation relation : person.getRelations()) {
            if (TreeRelationType.CHILD.equals(relation.getType())) {
                children.add(relation.getPerson());
            }
        }
        return children;
    }

    private List<Person> fetchSpouses(String name) throws InvalidInputException {
        Person person = findPerson(this.root, name);
        if (person == null) {
            throw new InvalidInputException("Invalid Input");
        }
        List<Person> spouses = new ArrayList<>();
        for (Relation relation : person.getRelations()) {
            if (TreeRelationType.SPOUSE.equals(relation.getType())) {
                spouses.add(relation.getPerson());
            }
        }
        return spouses;
    }

    private Person fetchFather(String name) throws InvalidInputException {
        List<Person> parents = fetchParents(name);
        for (Person person : parents) {
            if (Gender.MALE.equals(person.getGender())) {
                return person;
            }
        }
        return null;
    }

    private List<Person> fetchParents(String name) throws InvalidInputException {
        Person person = findPerson(this.root, name);
        if (person == null) {
            throw new InvalidInputException("Invalid Input");
        }
        List<Person> parents = new ArrayList<>();
        for (Relation relation : person.getRelations()) {
            if (TreeRelationType.PARENT.equals(relation.getType())) {
                parents.add(relation.getPerson());
            }
        }
        return parents;
    }
}
