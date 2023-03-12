package com.simplecourse.courseinfo.cli;


import com.simplecourse.courseinfo.cli.service.CourseRetreiverService;
import com.simplecourse.courseinfo.cli.service.CourseStorageService;
import com.simplecourse.courseinfo.cli.service.PluralSightCoursesRecords;
import com.simplecourse.courseinfo.repo.CourseRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.function.Predicate;

public class CourseRetriever {
    private static final Logger LOG =LoggerFactory.getLogger(CourseRetriever.class);
    public static void main(String... arg){
        LOG.info("CourseRetriever ...Started!");
        if(arg.length==0){
            LOG.error("Enter the valid arguments...");
        }
        try{
            retrieveCourses(arg[0]);
        }
        catch (Exception e){
            LOG.error("Unexpected Exception",e);

        }
        LOG.info("CourseRetriever ...End");
    }

    private static void retrieveCourses(String authorId) {
        CourseRetreiverService courserRetreiverService =new CourseRetreiverService();
        LOG.info("Fetching the course records for Author: {}",authorId);
        List<PluralSightCoursesRecords> coursesFor = courserRetreiverService.getCoursesFor(authorId)
                .stream()
                //.filter(c->!c.isRetired())
                .filter(Predicate.not(PluralSightCoursesRecords::isRetired))
                .toList();
        LOG.info("Fetched {} valid courses: {}",coursesFor.size(),coursesFor);
        CourseRepo courseRepo=CourseRepo.getCourseRepo("./courses.db");
        CourseStorageService courseStorageService=new CourseStorageService(courseRepo);
        courseStorageService.savePluralsightCourses(coursesFor);
        LOG.info("Stored Courses in DB");
    }
}
