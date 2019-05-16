package com.example.c196.c196;

public class Assessments {
    private String assessmentId;
    private String courseId;
    private String assessmentDueDate;
    private String assessmentGoalDate;
    private String assessment;
    private String assessmentTitle;
    private String assessmentGoalDateAlert;



    // constructors
    public Assessments() {
    }

    public Assessments(String assessmentId, String courseId, String assessmentTitle, String assessment, String assessmentDueDate, String assessmentGoalDate, String assessmentGoalDateAlert) {
        this.assessmentId = assessmentId;
        this.courseId = courseId;
        this.assessmentTitle = assessmentTitle;
        this.assessment = assessment;
        this.assessmentDueDate= assessmentDueDate;
        this.assessmentGoalDate= assessmentGoalDate;
        this.assessmentGoalDateAlert = assessmentGoalDateAlert;

    }
    public Assessments(String assessmentTitle, String assessment, String assessmentDueDate, String assessmentGoalDate) {
        this.assessmentTitle = assessmentTitle;
        this.assessment = assessment;
        this.assessmentDueDate = assessmentDueDate;
        this.assessmentGoalDate = assessmentGoalDate;
    }

    public Assessments(String assessmentId, String assessmentTitle, String assessment, String assessmentDueDate, String assessmentGoalDate) {
        this.assessmentTitle = assessmentTitle;
        this.assessmentId = assessmentId;
        this.assessment = assessment;
        this.assessmentDueDate= assessmentDueDate;
        this.assessmentGoalDate= assessmentGoalDate;
    }

    // setters
    public void setAssessmentId(String assessmentId) {
        this.assessmentId = assessmentId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public void setAssessmentTitle(String assessmentTitle) {
        this.assessmentTitle = assessmentTitle;
    }

    public void setAssessment(String assessment) {
        this.assessment = assessment;
    }

    public void setAssessmentDueDate(String assessmentDueDate) {
        this.assessmentDueDate = assessmentDueDate;
    }

    public void setAssessmentGoalDate(String assessmentGoalDate) {
        this.assessmentGoalDate = assessmentGoalDate;
    }

    public void setAssessmentGoalDateAlert(String assessmentGoalDateAlert) {
        this.assessmentGoalDateAlert = assessmentGoalDateAlert;
    }

    // getters
    public String getAssessmentId() {
        return this.assessmentId;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public String getAssessmentTitle() {
        return this.assessmentTitle;
    }

    public String getAssessment() {
        return this.assessment;
    }

    public String getAssessmentDueDate() {
        return this.assessmentDueDate;
    }

    public String getAssessmentGoalDate() {
        return this.assessmentGoalDate;
    }

    public String getAssessmentGoalDateAlert() {
        return this.assessmentGoalDateAlert;
    }
}

