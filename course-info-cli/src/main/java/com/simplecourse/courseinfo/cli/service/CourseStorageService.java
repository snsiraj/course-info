package com.simplecourse.courseinfo.cli.service;

import com.simplecourse.courseinfo.domain.Course;
import com.simplecourse.courseinfo.repo.CourseRepo;

import java.util.List;
import java.util.Optional;

public class CourseStorageService {
  private final CourseRepo courseRepo;
  private static final String PS_BASE_URL="https://app.pluralsight.com";
  public CourseStorageService(CourseRepo repo){
      courseRepo=repo;
  }
  public void savePluralsightCourses(List<PluralSightCoursesRecords> pluralSightCoursesRecords){
      for(PluralSightCoursesRecords course:pluralSightCoursesRecords) {
          Course courseRec=new Course(course.id(),
                                        course.title(),
                                        course.durationInMinutes(),
                  PS_BASE_URL+course.contentUrl(),
                  Optional.empty());
          courseRepo.saveCourse(courseRec);
      }

  }
}
