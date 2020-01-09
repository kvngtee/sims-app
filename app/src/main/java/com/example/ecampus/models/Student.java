package com.example.ecampus.models;

public class Student {
    String firstName;
    String lastName;
    String born;
    String department;
    String email;
    String image;
    String level;
    String nationality;
    String phone;
    String studentID;

    public Student(String firstName, String lastName, String born, String department, String email, String image, String level, String nationality, String phone, String studentID) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.born = born;
        this.department = department;
        this.email = email;
        this.image = image;
        this.level = level;
        this.nationality = nationality;
        this.phone = phone;
        this.studentID = studentID;
    }

    public Student() {
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBorn() {
        return born;
    }

    public void setBorn(String born) {
        this.born = born;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getlevel() {
        return level;
    }

    public void setlevel(String level) {
        this.level = level;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStudentID() {
        return studentID;
    }

    public void setStudentID(String studentID) {
        this.studentID = studentID;
    }


}
