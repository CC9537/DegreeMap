package com.chancellor.degreemap.database;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.chancellor.degreemap.dao.AssessmentDao;
import com.chancellor.degreemap.dao.CourseDao;
import com.chancellor.degreemap.dao.MentorDao;
import com.chancellor.degreemap.dao.TermDao;
import com.chancellor.degreemap.models.Assessment;
import com.chancellor.degreemap.models.Course;
import com.chancellor.degreemap.models.Mentor;
import com.chancellor.degreemap.models.Term;
import com.chancellor.degreemap.utilities.DateTypeConverter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Term.class, Course.class, Assessment.class, Mentor.class}, version = 1, exportSchema = false)
public abstract class DegreeMapDatabase extends RoomDatabase {

    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);
    private static volatile DegreeMapDatabase INSTANCE;

    private static DegreeMapDatabase.Callback populateDatabaseCallback = new DegreeMapDatabase.Callback() {
        @Override
        public void onOpen(@NonNull SupportSQLiteDatabase db) {
            super.onOpen(db);

            databaseWriteExecutor.execute(() -> {
                TermDao termDao = INSTANCE.termDao();
                CourseDao courseDao = INSTANCE.courseDao();
                MentorDao mentorDao = INSTANCE.mentorDao();
                AssessmentDao assessmentDao = INSTANCE.assessmentDao();


                termDao.deleteAllTerms();

                Term term1 = new Term();
                term1.setTermId(1);
                term1.setTermName("December 2018");
                term1.setTermStart(DateTypeConverter.toDate("2018-12-31"));
                term1.setTermEnd(DateTypeConverter.toDate("2019-05-31"));
                termDao.createTerm(term1);

                Course course1 = new Course();
                course1.setCourseId(1);
                course1.setCourseName("Orientation – ORA1");
                course1.setCourseStatus("In Progress");
                course1.setCourseNotes("This course will use an interactive, self-paced learning resource to help guide you through the basics of succeeding at WGU.");
                course1.setCourseStart(DateTypeConverter.toDate("2018-12-01"));
                course1.setCourseEnd(DateTypeConverter.toDate("2019-02-30"));
                course1.setTermIdFk(1);
                courseDao.createCourse(course1);

                Course course2 = new Course();
                course2.setCourseId(2);
                course2.setCourseName("IT Foundations – C393");
                course2.setCourseStatus("Pending");
                course2.setCourseNotes("This course prepares you for one assessment, CompTIA A+ Exam 220-901.");
                course2.setCourseStart(DateTypeConverter.toDate("2019-03-01"));
                course2.setCourseEnd(DateTypeConverter.toDate("2019-05-31"));
                course2.setTermIdFk(1);
                courseDao.createCourse(course2);

                Assessment assessment1 = new Assessment();
                assessment1.setAssessmentId(1);
                assessment1.setAssessmentName("Assessment 1");
                assessment1.setAssessmentInfo("Assessment 1 Info");
                assessment1.setAssessmentType("OA");
                assessment1.setCourseIdFk(1);
                assessmentDao.createAssessment(assessment1);

                Assessment assessment2 = new Assessment();
                assessment2.setAssessmentId(2);
                assessment2.setAssessmentName("Assessment 2");
                assessment2.setAssessmentInfo("Assessment 2 Info");
                assessment2.setAssessmentType("OA");
                assessment2.setCourseIdFk(2);
                assessmentDao.createAssessment(assessment2);

                Mentor mentor1 = new Mentor();
                mentor1.setMentorId(1);
                mentor1.setMentorName("Mentor One");
                mentor1.setMentorEmail("mentor.one@my.wgu.edu");
                mentor1.setMentorPhone("801-555-1212");
                mentor1.setCourseIdFk(1);
                mentorDao.createMentor(mentor1);

                Mentor mentor2 = new Mentor();
                mentor2.setMentorId(2);
                mentor2.setMentorName("Mentor two");
                mentor2.setMentorEmail("mentor.two@my.wgu.edu");
                mentor2.setMentorPhone("801-555-2121");
                mentor2.setCourseIdFk(2);
                mentorDao.createMentor(mentor2);

                Term term2 = new Term();
                term2.setTermName("June 2019");
                term2.setTermStart(DateTypeConverter.toDate("2019-06-01"));
                term2.setTermEnd(DateTypeConverter.toDate("2019-11-31"));
                termDao.createTerm(term2);
                Term term3 = new Term();
                term3.setTermName("December 2019");
                term3.setTermStart(DateTypeConverter.toDate("2019-12-01"));
                term3.setTermEnd(DateTypeConverter.toDate("2020-05-31"));
                termDao.createTerm(term3);
            });
        }
    };

    static DegreeMapDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (DegreeMapDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            DegreeMapDatabase.class, "degree_map_database")
                            .addCallback(populateDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    public abstract TermDao termDao();

    public abstract CourseDao courseDao();

    public abstract AssessmentDao assessmentDao();

    public abstract MentorDao mentorDao();
}
