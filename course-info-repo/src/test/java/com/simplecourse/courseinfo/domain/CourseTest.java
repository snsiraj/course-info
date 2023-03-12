package com.simplecourse.courseinfo.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class CourseTest {

    @ParameterizedTest
    @CsvSource(textBlock = """
            1,test,5,,Test Notes
                """)
    void id(String id, String name, Long duration, String url, String notes) {
        try{
            Course c=new Course(id, name, duration, url, Optional.of(notes));

        }
        catch(IllegalArgumentException e){
           assertEquals("No Value Present",e.getMessage());
        }

    };


}