package com.example.c196.c196;

public class Course {

    private String courseId;
    private String termId;
    private String courseTitle;
    private String courseStartDate;
    private String courseStatus;
    private String courseEndDate;
    private String courseNotes;
    private String courseStartDateAlert;
    private String courseEndDateAlert;

    // constructors
    public Course() {
    }

    public Course(String courseId, String termId, String courseTitle, String courseStartDate, String courseStatus, String courseEndDate,
                 String courseNotes, String courseStartDateAlert, String courseEndDateAlert) {
        this.courseId = courseId;
        this.termId = termId;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseStatus = courseStatus;
        this.courseEndDate = courseEndDate;
        this.courseNotes = courseNotes;
        this.courseStartDateAlert = courseStartDateAlert;
        this.courseEndDateAlert = courseEndDateAlert;
    }

    public Course(String courseTitle, String courseStartDate, String courseStatus, String courseEndDate) {
        this.termId = termId;
        this.courseTitle = courseTitle;
        this.courseStartDate = courseStartDate;
        this.courseStatus = courseStatus;
        this.courseEndDate = courseEndDate;


    }


    // setters
    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setTermId(String termId) {
        this.termId = termId;
    }

    public void setCourseTitle(String courseTitle) {
        this.courseTitle = courseTitle;
    }

    public void setCourseStartDate(String courseStartDate) {
        this.courseStartDate = courseStartDate;
    }

    public void setCourseStatus(String courseStatus) {
        this.courseStatus = courseStatus;
    }

    public void setCourseEndDate(String courseEndDate) {
        this.courseEndDate = courseEndDate;
    }

    public void setCourseNotes(String courseNotes) {
        this.courseNotes = courseNotes;
    }

    public void setCourseStartDateAlert(String courseStartDateAlert) {
        this.courseStartDateAlert = courseStartDateAlert;
    }

    public void setCourseEndDateAlert(String courseEndDateAlert) {
        this.courseEndDateAlert = courseEndDateAlert;
    }


    // getters
    public String getCourseId() {
        return this.courseId;
    }

    public String getTermId() {
        return this.termId;
    }

    public String getCourseTitle() {
        return this.courseTitle;
    }

    public String getCourseStartDate() {
        return this.courseStartDate;
    }

    public String getCourseStatus() {
        return this.courseStatus;
    }

    public String getCourseEndDate() {
        return this.courseEndDate;
    }

    public String getCourseNotes() {
        return this.courseNotes;
    }

    public String getCourseStartDateAlert() {
        return this.courseStartDateAlert;
    }

    public String getCourseEndDateAlert() {
        return this.courseEndDateAlert;
    }
}
