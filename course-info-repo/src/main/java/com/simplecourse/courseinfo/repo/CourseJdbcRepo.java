package com.simplecourse.courseinfo.repo;

import com.simplecourse.courseinfo.domain.Course;
import org.h2.jdbcx.JdbcDataSource;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

class CourseJdbcRepo implements CourseRepo{
    private static final String INSERT_COURSE_SQL = """
    MERGE INTO COURSES(id,name,length,url)
    VALUES (?,?,?,?)
""";
    private final DataSource dataSource;
    private static final String H2_DB_URL="jdbc:h2:file:%s;AUTO_SERVER=TRUE;INIT=RUNSCRIPT FROM './db_init.sql'";
    public CourseJdbcRepo(String dbFile){
        JdbcDataSource jdbcDataSource=new JdbcDataSource();
        jdbcDataSource.setURL(H2_DB_URL.formatted(dbFile));
        dataSource=jdbcDataSource;
    }
    @Override
    public void saveCourse(Course course) {
        try(PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(INSERT_COURSE_SQL)){
            preparedStatement.setString(1, course.id());
            preparedStatement.setString(2, course.name());
            preparedStatement.setLong(3, course.length());
            preparedStatement.setString(4, course.url());
            preparedStatement.execute();
        }
        catch (SQLException e){
            throw new RepoException("Failed to save:"+course,e);
        }

    }

    @Override
    public List<Course> getAllCourses() {
        try(Statement statement = dataSource.getConnection().createStatement()){
            ResultSet resultSet = statement.executeQuery("SELECT * FROM COURSES");
            List<Course> courseList =new ArrayList<>();
            while(resultSet.next()){
                Course course=new Course(resultSet.getString(1),
                        resultSet.getString(2),
                        resultSet.getLong(3),
                        resultSet.getString(4),
                        Optional.ofNullable(resultSet.getString(5))
                        );
                courseList.add(course);
            }
            return Collections.unmodifiableList(courseList);
        }
        catch (SQLException e){
            throw new RepoException("Failed to Read:",e);
        }

    }
    private static final String UPDATE_COURSE_NOTES_SQL = """
                            UPDATE COURSES SET NOTES=? WHERE ID=?
                                                            """;
    @Override
    public void addNotes(String id, String notes) {
        try(PreparedStatement preparedStatement = dataSource.getConnection().prepareStatement(UPDATE_COURSE_NOTES_SQL)){
            preparedStatement.setString(1, notes);
            preparedStatement.setString(2, id);
            preparedStatement.execute();
        }
        catch (SQLException e){
            throw new RepoException("Failed to Update the notes...",e);
        }
    }
}
