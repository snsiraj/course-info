package com.simplecourse.courseinfo.server;

import com.simplecourse.courseinfo.domain.Course;
import com.simplecourse.courseinfo.repo.CourseRepo;
import com.simplecourse.courseinfo.repo.RepoException;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Comparator;
import java.util.List;

@Path("/courses")
public class CourseResource {
    private static final Logger LOG = LoggerFactory.getLogger(CourseResource.class);
    private final CourseRepo repo;

    public CourseResource(CourseRepo repo) {
        this.repo = repo;
    }
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Course> getCourses(){
        try{
            return repo.getAllCourses()
                    .stream()
                    .sorted(Comparator.comparing(Course::id))
                    .toList();
        }
        catch(RepoException e){
            LOG.error("Course Not found",e);
            throw new NotFoundException();
        }
    }
    @POST
    @Path(("/{id}/notes"))
    @Consumes(MediaType.TEXT_PLAIN)
    public void addNotes(@PathParam("id") String id,String notes){
        LOG.info("Updating notes for {}",id);
        repo.addNotes(id,notes);

    }
}
