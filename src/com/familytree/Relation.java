package com.familytree;

public class Relation {
    private TreeRelationType type;
    private Person person;

    Relation(TreeRelationType type, Person person) {
        this.type = type;
        this.person = person;
    }

    public TreeRelationType getType() {
        return type;
    }

    public Person getPerson() {
        return person;
    }
}
