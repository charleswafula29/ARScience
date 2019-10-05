package com.example.arscience.classes;

public class Singlestudent {

    private String Studentname,admission,status;

    public Singlestudent(String studentname, String admission, String status) {
        Studentname = studentname;
        this.admission = admission;
        this.status = status;
    }

    public String getStudentname() {
        return Studentname;
    }

    public String getAdmission() {
        return admission;
    }

    public String getStatus() {
        return status;
    }
}
