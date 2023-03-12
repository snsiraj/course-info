package com.simplecourse.courseinfo.cli.service;

import com.simplecourse.courseinfo.domain.Course;
import com.simplecourse.courseinfo.repo.CourseRepo;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourseStorageServiceTest {

    @Test
    void savePluralsightCourses() {
        CourseRepo courseRepo = new InMemoryRepoMock();
        CourseStorageService courseStorageService=new CourseStorageService(courseRepo);
        PluralSightCoursesRecords pluralSightCourses=new PluralSightCoursesRecords("test","test","00:05:37","/testurl",false  );
        courseStorageService.savePluralsightCourses(List.of(pluralSightCourses));
        Course expectedCourse = new Course("test","test",5,"https://app.pluralsight.com/testurl", Optional.ofNullable(null));
        assertEquals(List.of(expectedCourse),courseRepo.getAllCourses());
    }
    static class InMemoryRepoMock implements CourseRepo{
        private final List<Course> courseList =new ArrayList<>();
        @Override
        public void saveCourse(Course course) {
            courseList.add(course);

        }

        @Override
        public List<Course> getAllCourses() {
            return courseList;
        }

        @Override
        public void addNotes(String id, String notes) {
            return;
        }
    }
}