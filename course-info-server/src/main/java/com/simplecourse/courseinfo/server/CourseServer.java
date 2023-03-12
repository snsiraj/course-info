package com.simplecourse.courseinfo.server;

import com.simplecourse.courseinfo.repo.CourseRepo;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;

public class CourseServer {
    private static final Logger LOG= LoggerFactory.getLogger(CourseServer.class);
    private static final String BASE_URI="http://localhost:7070/";
    public static void main(String... args){
        LOG.info("Starting HTTP Server...");
        CourseRepo repo=CourseRepo.getCourseRepo("./courses.db");
        ResourceConfig config=new ResourceConfig().register(new CourseResource(repo));
        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI),config);
    }
}
