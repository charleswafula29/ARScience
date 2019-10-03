package com.example.arscience.classes;

public class BaseURL {

    private static String url= "http://nyangenahospital.com/APIS/";

    public static String  getLogin(String email, String password){
        return url+"login.php?email="+email+"&password="+password;
    }

    //Returns String response => inserted or not inserted
    public static String getRegister(String email,String names, String password){
        return url+"register.php?email="+email+"&names="+names+"&password="+password;
    }

    //Returns String response => created, not created or name already in use
    public static String getNewClass(String code, String name,String desc, String teacher_id){
        return url+"newclass.php?code="+code+"&name="+name+"&desc="+desc+"&teacher_id="+teacher_id;
    }

    //return json array 'Classes' with teacher's classes
    public static String getTeacherClasses(String teacher_id){
        return url+"teacherclasses.php?teacher_id="+teacher_id;
    }

    //returns json array 'Students' of all students in a single class
    public static String getAllStudents(String classname){
        return url+"studentslist.php?classname="+classname;
    }

    //Returns String response => Class deleted or Class not deleted
    public static String getDeleteClass(String codename, String teacher_id){
        return url+"deleteclass.php?codename="+codename+"&teacher_id="+teacher_id;
    }

    //Returns String response => student created, student not created or student already exists
    public static String getNewStudent(String Snumber, String Snames){
        //Snames should be concatinated using underscore for creating db
        return url+"newstudent.php?Snumber="+Snumber+"&Snames="+Snames;
    }

    //Returns String response => joined class, class not joined or class does not exist
    public static String getJoinClass(String classcode, String Snumber, String Snames){
        return url+"joinclass.php?code="+classcode+"&Snumber="+Snumber+"&Snames="+Snames;
    }

    //Returns json array 'Classes' of all the student's classes
    public static String getStudentClasses(String Snumber, String Snames){
        return url+"studentclasses.php?Snumber="+Snumber+"&Snames="+Snames;
    }


}