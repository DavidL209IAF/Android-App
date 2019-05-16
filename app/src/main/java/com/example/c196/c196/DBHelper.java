package com.example.c196.c196;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.Cursor;
import java.util.List;
import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "terms.db";

    // Table Names
    private static final String TABLE_TERMS = "terms";
    private static final String TABLE_COURSES = "courses";
    private static final String TABLE_MENTORS = "mentors";
    private static final String TABLE_ASSESSMENTS = "assessments";


    // used by multiple tables
    private static final String TERM_ID = "term_id";
    private static final String COURSE_ID = "course_id";

    // Terms Table - column names
    private static final String TERM_TITLE = "term_title";
    private static final String TERM_STARTDATE = "term_startdate";
    private static final String TERM_ENDDATE = "term_enddate";

    // Courses Table - column names
    private static final String COURSE_TITLE = "course_title";
    private static final String COURSE_STARTDATE = "course_startdate";
    private static final String COURSE_STARTDATEALERT = "course_startdatealter";
    private static final String COURSE_ENDDATEALERT = "course_enddatealert";
    private static final String COURSE_STATUS = "course_status";
    private static final String COURSE_ENDDATE = "course_enddate";
    private static final String COURSE_NOTES = "course_notes";

    //Mentors Table -column names
    private static final String COURSE_MENTORID = "course_mentorid";
    private static final String COURSE_MENTORNAME = "course_mentorname";
    private static final String COURSE_MENTORNUMBER= "course_mentornumber";
    private static final String COURSE_MENTOREMAIL = "course_mentoremail";


    //Assessments Table -column names
    private static final String COURSE_ASSESSMENTTITLE = "course_assessmenttitle";
    private static final String COURSE_ASSESSMENT = "course_assessment";
    private static final String COURSE_ASSESSMENTID = "course_assessmentid";
    private static final String COURSE_ASSESSMENTDUEDATE = "course_assessmentduedate";
    private static final String COURSE_ASSESSMENTGOALDATE = "course_assessmentgoaldate";
    private static final String COURSE_ASSESSMENTGOALDATEALERT = "course_assessmentgoaldatealert";

    // Table Create Statements
    // Term table create statement
    private static final String CREATE_TABLE_TERM = "CREATE TABLE "
            + TABLE_TERMS + "(" + TERM_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TERM_TITLE
            + " TEXT," + TERM_STARTDATE + " DATE," + TERM_ENDDATE
            + " DATE" + ")";

    // Courses table create statement
    private static final String CREATE_TABLE_COURSE = "CREATE TABLE " + TABLE_COURSES
            + "(" + COURSE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + TERM_ID + " INTEGER," + COURSE_TITLE + " TEXT,"
            + COURSE_STARTDATE + " DATE," + COURSE_STATUS + " TEXT," + COURSE_ENDDATE + " DATE," +  COURSE_NOTES + " TEXT," + COURSE_STARTDATEALERT + " INTEGER," + COURSE_ENDDATEALERT + " INTEGER" + ")";

    // Mentors table create statement
    private static final String CREATE_TABLE_MENTORS = "CREATE TABLE " + TABLE_MENTORS
            + "(" + COURSE_MENTORID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COURSE_ID + " INTEGER," + COURSE_MENTORNAME + " TEXT,"
            + COURSE_MENTORNUMBER + " TEXT," + COURSE_MENTOREMAIL + " TEXT"  + ")";

    // Mentors table create statement
    private static final String CREATE_TABLE_ASSESSMENTS = "CREATE TABLE " + TABLE_ASSESSMENTS
            + "(" + COURSE_ASSESSMENTID + " INTEGER PRIMARY KEY AUTOINCREMENT," + COURSE_ID + " INTEGER," + COURSE_ASSESSMENTTITLE + " TEXT," + COURSE_ASSESSMENT + " TEXT,"
            + COURSE_ASSESSMENTDUEDATE + " DATE," + COURSE_ASSESSMENTGOALDATE + " DATE,"  + COURSE_ASSESSMENTGOALDATEALERT + " INTEGER"  + ")";





    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_TERM);
        db.execSQL(CREATE_TABLE_COURSE);
        db.execSQL(CREATE_TABLE_MENTORS);
        db.execSQL(CREATE_TABLE_ASSESSMENTS);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TERMS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COURSES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MENTORS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ASSESSMENTS);

        onCreate(db);

    }

    public long createAssessment(Assessments assessments) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COURSE_ASSESSMENTTITLE, assessments.getAssessmentTitle());
        values.put(COURSE_ASSESSMENT, assessments.getAssessment());
        values.put(COURSE_ASSESSMENTDUEDATE, assessments.getAssessmentDueDate());
        values.put(COURSE_ASSESSMENTGOALDATE, assessments.getAssessmentGoalDate());
        values.put(COURSE_ASSESSMENTGOALDATEALERT, assessments.getAssessmentGoalDateAlert());


        // insert row
        long assessment_id = db.insert(TABLE_ASSESSMENTS, null, values);
        return assessment_id;
    }

    /*
     * get single term
     */
    public Assessments getAssessment(long assessment_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_ASSESSMENTS + " WHERE "
                + COURSE_ASSESSMENTID + " = " + assessment_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Assessments assessment = new Assessments();
        assessment.setAssessmentId(c.getString(c.getColumnIndex(COURSE_ASSESSMENTID)));
        assessment.setCourseId((c.getString(c.getColumnIndex(COURSE_ID))));
        assessment.setAssessmentTitle(c.getString(c.getColumnIndex(COURSE_ASSESSMENTTITLE)));
        assessment.setAssessment(c.getString(c.getColumnIndex(COURSE_ASSESSMENT)));
        assessment.setAssessmentDueDate(c.getString(c.getColumnIndex(COURSE_ASSESSMENTDUEDATE)));
        assessment.setAssessmentGoalDate(c.getString(c.getColumnIndex(COURSE_ASSESSMENTGOALDATE)));
        assessment.setAssessmentGoalDateAlert(c.getString(c.getColumnIndex(COURSE_ASSESSMENTGOALDATEALERT)));

        return assessment;
    }

    /*
     * getting all terms
     * */
    public List<Assessments> getAllAssessments() {
        List<Assessments> assessments = new ArrayList<Assessments>();
        String selectQuery = "SELECT  * FROM " + TABLE_ASSESSMENTS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Assessments assessment = new Assessments();
                assessment.setAssessmentId(c.getString(c.getColumnIndex(COURSE_ASSESSMENTID)));
                assessment.setCourseId((c.getString(c.getColumnIndex(COURSE_ID))));
                assessment.setAssessmentTitle(c.getString(c.getColumnIndex(COURSE_ASSESSMENTTITLE)));
                assessment.setAssessment(c.getString(c.getColumnIndex(COURSE_ASSESSMENT)));
                assessment.setAssessmentDueDate(c.getString(c.getColumnIndex(COURSE_ASSESSMENTDUEDATE)));
                assessment.setAssessmentGoalDate(c.getString(c.getColumnIndex(COURSE_ASSESSMENTGOALDATE)));
                assessment.setAssessmentGoalDateAlert(c.getString(c.getColumnIndex(COURSE_ASSESSMENTGOALDATEALERT)));


                // adding to term list
                assessments.add(assessment);
            } while (c.moveToNext());
        }

        return assessments;
    }

    /*
     * Updating a term
     */
    public int updateAssessment(Assessments assessment) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COURSE_ID, assessment.getCourseId());
        values.put(COURSE_ASSESSMENTTITLE, assessment.getAssessmentTitle());
        values.put(COURSE_ASSESSMENT, assessment.getAssessment());
        values.put(COURSE_ASSESSMENTDUEDATE, assessment.getAssessmentDueDate());
        values.put(COURSE_ASSESSMENTGOALDATE, assessment.getAssessmentGoalDate());
        values.put(COURSE_ASSESSMENTGOALDATEALERT, assessment.getAssessmentGoalDateAlert());

        // updating row
        return db.update(TABLE_ASSESSMENTS, values, COURSE_ASSESSMENTID + " = ?",
                new String[] { String.valueOf(assessment.getAssessmentId()) });
    }

    /*
     * Deleting a term
     */
    public void deleteAssessment(long assessment_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ASSESSMENTS, COURSE_ASSESSMENTID + " = ?",
                new String[] { String.valueOf(assessment_id) });
    }







    //CRUD Operations

    public long createMentors(Mentors mentors) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COURSE_MENTORNAME, mentors.getMentorName());
        values.put(COURSE_MENTORNUMBER, mentors.getMentorPhone());
        values.put(COURSE_MENTOREMAIL, mentors.getMentorEmail());

        // insert row
        long mentors_id = db.insert(TABLE_MENTORS, null, values);
        return mentors_id;
    }

    /*
     * get single term
     */
    public Mentors getMentors(long mentors_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_MENTORS + " WHERE "
                + COURSE_MENTORID + " = " + mentors_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Mentors mentors = new Mentors();
        mentors.setMentorId(c.getString(c.getColumnIndex(COURSE_MENTORID)));
        mentors.setCourseId((c.getString(c.getColumnIndex(COURSE_ID))));
        mentors.setMentorName(c.getString(c.getColumnIndex(COURSE_MENTORNAME)));
        mentors.setMentorPhone(c.getString(c.getColumnIndex(COURSE_MENTORNUMBER)));
        mentors.setMentorEmail(c.getString(c.getColumnIndex(COURSE_MENTOREMAIL)));

        return mentors;
    }

    /*
     * getting all terms
     * */
    public List<Mentors> getAllMentors() {
        List<Mentors> mentors = new ArrayList<Mentors>();
        String selectQuery = "SELECT  * FROM " + TABLE_MENTORS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Mentors mentor = new Mentors();
                mentor.setMentorId(c.getString(c.getColumnIndex(COURSE_MENTORID)));
                mentor.setCourseId((c.getString(c.getColumnIndex(COURSE_ID))));
                mentor.setMentorName(c.getString(c.getColumnIndex(COURSE_MENTORNAME)));
                mentor.setMentorPhone(c.getString(c.getColumnIndex(COURSE_MENTORNUMBER)));
                mentor.setMentorEmail(c.getString(c.getColumnIndex(COURSE_MENTOREMAIL)));


                // adding to term list
                mentors.add(mentor);
            } while (c.moveToNext());
        }

        return mentors;
    }

    /*
     * Updating a term
     */
    public int updateMentors(Mentors mentors) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COURSE_MENTORID, mentors.getMentorId());
        values.put(COURSE_ID, mentors.getCourseId());
        values.put(COURSE_MENTORNAME, mentors.getMentorName());
        values.put(COURSE_MENTORNUMBER, mentors.getMentorPhone());
        values.put(COURSE_MENTOREMAIL, mentors.getMentorEmail());

        // updating row
        return db.update(TABLE_MENTORS, values, COURSE_MENTORID + " = ?",
                new String[] { String.valueOf(mentors.getMentorId()) });
    }

    /*
     * Deleting a term
     */
    public void deleteMentors(long mentors_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MENTORS, COURSE_MENTORID + " = ?",
                new String[] { String.valueOf(mentors_id) });
    }





    public long createTerm(Term term) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TERM_TITLE, term.getTermTitle());
        values.put(TERM_STARTDATE, term.getTermStartDate());
        values.put(TERM_ENDDATE, term.getTermEndDate());

        // insert row
        long term_id = db.insert(TABLE_TERMS, null, values);
        return term_id;
    }

    /*
     * get single term
     */
    public Term getTerm(long term_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TERMS + " WHERE "
                + TERM_ID + " = " + term_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Term term = new Term();
        term.setTermId(c.getString(c.getColumnIndex(TERM_ID)));
        term.setTermTitle((c.getString(c.getColumnIndex(TERM_TITLE))));
        term.setTermStartDate(c.getString(c.getColumnIndex(TERM_STARTDATE)));
        term.setTermEndDate(c.getString(c.getColumnIndex(TERM_ENDDATE)));

        return term;
    }

    /*
     * getting all terms
     * */
    public List<Term> getAllTerms() {
        List<Term> terms = new ArrayList<Term>();
        String selectQuery = "SELECT  * FROM " + TABLE_TERMS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Term term = new Term();
                term.setTermId(c.getString(c.getColumnIndex(TERM_ID)));
                term.setTermTitle((c.getString(c.getColumnIndex(TERM_TITLE))));
                term.setTermStartDate(c.getString(c.getColumnIndex(TERM_STARTDATE)));
                term.setTermEndDate(c.getString(c.getColumnIndex(TERM_ENDDATE)));

                // adding to term list
                terms.add(term);
            } while (c.moveToNext());
        }

        return terms;
    }

    /*
     * Updating a term
     */
    public int updateTerm(Term term) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TERM_TITLE, term.getTermTitle());
        values.put(TERM_STARTDATE, term.getTermStartDate());
        values.put(TERM_ENDDATE, term.getTermEndDate());

        // updating row
        return db.update(TABLE_TERMS, values, TERM_ID + " = ?",
                new String[] { String.valueOf(term.getTermId()) });
    }

    /*
     * Deleting a term
     */
    public void deleteTerm(long term_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_TERMS, TERM_ID + " = ?",
                new String[] { String.valueOf(term_id) });
    }

    //CRUD for courses

    public long createCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COURSE_TITLE, course.getCourseTitle());
        values.put(COURSE_STARTDATE, course.getCourseStartDate());
        values.put(COURSE_STATUS, course.getCourseStatus());
        values.put(COURSE_ENDDATE, course.getCourseEndDate());
       // if (course.getCourseNotes().toString().length() == 0) {
        values.put(COURSE_NOTES, course.getCourseNotes());
        values.put(COURSE_STARTDATEALERT, course.getCourseStartDateAlert());
        values.put(COURSE_ENDDATEALERT, course.getCourseEndDateAlert());

            long course_id = db.insert(TABLE_COURSES, null, values);
            return course_id;
      //  } else {

         //   long course_id = db.insert(TABLE_COURSES, null, values);
        //    return course_id;
       // }


        // insert row

    }

    public Course getCourseByCourseId(long course_id) {
        SQLiteDatabase db = this.getReadableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_COURSES + " WHERE "
                + COURSE_ID + " = " + course_id;

        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null)
            c.moveToFirst();

        Course course = new Course();
        course.setCourseId(c.getString(c.getColumnIndex(COURSE_ID)));
        course.setTermId(c.getString(c.getColumnIndex(TERM_ID)));
        course.setCourseTitle((c.getString(c.getColumnIndex(COURSE_TITLE))));
        course.setCourseStartDate(c.getString(c.getColumnIndex(COURSE_STARTDATE)));
        course.setCourseStatus(c.getString(c.getColumnIndex(COURSE_STATUS)));
        course.setCourseEndDate(c.getString(c.getColumnIndex(COURSE_ENDDATE)));
        course.setCourseNotes(c.getString(c.getColumnIndex(COURSE_NOTES)));
        course.setCourseStartDateAlert(c.getString(c.getColumnIndex(COURSE_STARTDATEALERT)));
        course.setCourseEndDateAlert(c.getString(c.getColumnIndex(COURSE_ENDDATEALERT)));


        return course;
    }


    /**
     * getting all courses
     * */
    public List<Course> getAllCourses() {
        List<Course> courses = new ArrayList<Course>();
        String selectQuery = "SELECT  * FROM " + TABLE_COURSES;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Course course = new Course();
                course.setTermId(c.getString(c.getColumnIndex((TERM_ID))));
                course.setCourseId(c.getString(c.getColumnIndex(COURSE_ID)));
                course.setCourseTitle((c.getString(c.getColumnIndex(COURSE_TITLE))));
                course.setCourseStartDate(c.getString(c.getColumnIndex(COURSE_STARTDATE)));
                course.setCourseStatus(c.getString(c.getColumnIndex(COURSE_STATUS)));
                course.setCourseEndDate(c.getString(c.getColumnIndex(COURSE_ENDDATE)));
                course.setCourseNotes(c.getString(c.getColumnIndex(COURSE_NOTES)));
                course.setCourseStartDateAlert(c.getString(c.getColumnIndex(COURSE_STARTDATEALERT)));
                course.setCourseEndDateAlert(c.getString(c.getColumnIndex(COURSE_ENDDATEALERT)));

                // adding to tags list
                courses.add(course);
            } while (c.moveToNext());
        }
        return courses;
    }

    public List<Course> getAllCoursesByTermId(long term_id) {
        List<Course> courses = new ArrayList<Course>();
        String selectQuery = "SELECT  * FROM " + TABLE_COURSES + " WHERE "
                + TERM_ID + " = " + term_id;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (c.moveToFirst()) {
            do {
                Course course = new Course();
                course.setCourseId(c.getString(c.getColumnIndex(COURSE_ID)));
                course.setTermId(c.getString(c.getColumnIndex(TERM_ID)));
                course.setCourseTitle((c.getString(c.getColumnIndex(COURSE_TITLE))));
                course.setCourseStartDate(c.getString(c.getColumnIndex(COURSE_STARTDATE)));
                course.setCourseStatus(c.getString(c.getColumnIndex(COURSE_STATUS)));
                course.setCourseEndDate(c.getString(c.getColumnIndex(COURSE_ENDDATE)));
                course.setCourseNotes(c.getString(c.getColumnIndex(COURSE_NOTES)));
                course.setCourseStartDateAlert(c.getString(c.getColumnIndex(COURSE_STARTDATEALERT)));
                course.setCourseEndDateAlert(c.getString(c.getColumnIndex(COURSE_ENDDATEALERT)));

                // adding to tags list
                courses.add(course);
            } while (c.moveToNext());
        }
        return courses;
    }

    /*
     * Updating a course
     */
    public int updateCourse(Course course) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(TERM_ID, course.getTermId());
        values.put(COURSE_TITLE, course.getCourseTitle());
        values.put(COURSE_STARTDATE, course.getCourseStartDate());
        values.put(COURSE_STATUS, course.getCourseStatus());
        values.put(COURSE_ENDDATE, course.getCourseEndDate());
        values.put(COURSE_NOTES, course.getCourseNotes());
        values.put(COURSE_STARTDATEALERT, course.getCourseStartDateAlert());
        values.put(COURSE_ENDDATEALERT, course.getCourseEndDateAlert());

        // updating row
        return db.update(TABLE_COURSES, values, COURSE_ID + " = ?",
                new String[] { String.valueOf(course.getCourseId()) });
    }

    /*
     * Deleting a course
     */
    public void deleteCourse(long course_id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_COURSES, COURSE_ID + " = ?",
                new String[] { String.valueOf(course_id) });
    }


    // closing database
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }

}
