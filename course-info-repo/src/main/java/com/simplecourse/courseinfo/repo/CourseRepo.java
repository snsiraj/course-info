package com.simplecourse.courseinfo.repo;

import com.simplecourse.courseinfo.domain.Course;

import java.util.List;

public interface CourseRepo {
    void saveCourse(Course course);
    List<Course> getAllCourses();

    static CourseRepo getCourseRepo(String dbFileName){
        return new CourseJdbcRepo(dbFileName);
    }
    void addNotes(String id,String notes);
}
