package com.familytree;

import java.util.ArrayList;
import java.util.List;

public class Person {
        private String name;
        private Gender gender;
        private List<Relation> relations = new ArrayList<>();

        Person(String name, Gender gender) {
            this.name = name;
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public Gender getGender() {
            return gender;
        }

        public List<Relation> getRelations() {
            return relations;
        }

        public void addRelation(Relation relation) {
            relations.add(relation);
        }
}
