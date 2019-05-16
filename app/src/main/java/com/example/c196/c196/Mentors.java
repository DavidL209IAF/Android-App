package com.example.c196.c196;

public class Mentors {

    private String mentorId;
    private String courseId;
    private String mentorName;
    private String mentorPhone;
    private String mentorEmail;


    // constructors
    public Mentors() {
    }

    public Mentors(String mentorId, String mentorName, String mentorPhone, String mentorEmail) {
        this.mentorId = mentorId;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
    }
    public Mentors(String mentorName, String mentorPhone, String mentorEmail) {
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
    }

    public Mentors(String courseId, String mentorId, String mentorName, String mentorPhone, String mentorEmail) {
        this.courseId = courseId;
        this.mentorId = mentorId;
        this.mentorName = mentorName;
        this.mentorPhone = mentorPhone;
        this.mentorEmail = mentorEmail;
    }

    // setters
    public void setMentorId(String mentorId) {
        this.mentorId = mentorId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setMentorName(String mentorName) {
        this.mentorName = mentorName;
    }

    public void setMentorPhone(String mentorPhone) {
        this.mentorPhone = mentorPhone;
    }

    public void setMentorEmail(String mentorEmail) {
        this.mentorEmail = mentorEmail;
    }

    // getters
    public String getMentorId() {
        return this.mentorId;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public String getMentorName() {
        return this.mentorName;
    }

    public String getMentorPhone() {
        return this.mentorPhone;
    }

    public String getMentorEmail() {
        return this.mentorEmail;
    }
}

