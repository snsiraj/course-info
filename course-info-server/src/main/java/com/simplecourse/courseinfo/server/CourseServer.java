package com.simplecourse.courseinfo.server;

import com.simplecourse.courseinfo.repo.CourseRepo;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.util.Properties;
import java.util.logging.LogManager;

public class CourseServer {
    static {
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
    }
    private static final Logger LOG= LoggerFactory.getLogger(CourseServer.class);
    private static final String BASE_URI="http://localhost:7070/";
    public static void main(String... args){
        LOG.info("Starting HTTP Server...");
        String databaseFileName=loadDatabaseFileName();
        CourseRepo repo=CourseRepo.getCourseRepo(databaseFileName);
        ResourceConfig config=new ResourceConfig().register(new CourseResource(repo));
        GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI),config);
    }

    private static String loadDatabaseFileName() {
        try(InputStream propertiesStream=CourseServer.class.getResourceAsStream("/server.properties")){
            Properties properties=new Properties();
            properties.load(propertiesStream);
            return properties.getProperty("course-info.database");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}
