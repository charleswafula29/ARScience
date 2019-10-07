package com.example.arscience.classes;

public class StudentClass {

    private String classname,classdesc,classcode;

    public StudentClass(String classname, String classdesc, String classcode) {
        this.classname = classname;
        this.classdesc = classdesc;
        this.classcode = classcode;
    }

    public String getClassname() {
        return classname;
    }

    public String getClassdesc() {
        return classdesc;
    }

    public String getClasscode() {
        return classcode;
    }
}
