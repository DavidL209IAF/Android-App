package com.example.c196.c196;

public class Term {

    private String termId;
    private String termTitle;
    private String termStartDate;
    private String termEndDate;


    // constructors
    public Term() {
    }

    public Term(String termId, String termTitle, String termStartDate, String termEndDate) {
        this.termId = termId;
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    public Term(String termTitle, String termStartDate, String termEndDate) {
        this.termTitle = termTitle;
        this.termStartDate = termStartDate;
        this.termEndDate = termEndDate;
    }

    // setters
    public void setTermId(String termId) {
        this.termId = termId;
    }

    public void setTermTitle(String termTitle) {
        this.termTitle = termTitle;
    }

    public void setTermStartDate(String termStartDate) {
        this.termStartDate = termStartDate;
    }

    public void setTermEndDate(String termEndDate) {
        this.termEndDate = termEndDate;
    }

    // getters
    public String getTermId() {
        return this.termId;
    }

    public String getTermTitle() {
        return this.termTitle;
    }

    public String getTermStartDate() {
        return this.termStartDate;
    }

    public String getTermEndDate() {
        return this.termEndDate;
    }
}




