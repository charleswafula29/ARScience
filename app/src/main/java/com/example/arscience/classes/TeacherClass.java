package com.example.arscience.classes;

public class TeacherClass {

    private String Classname, description, classcode;

    public TeacherClass(String classname, String description, String classcode) {
        Classname = classname;
        this.description = description;
        this.classcode = classcode;
    }

    public String getClassname() {
        return Classname;
    }

    public String getDescription() {
        return description;
    }

    public String getClasscode() {
        return classcode;
    }
}
