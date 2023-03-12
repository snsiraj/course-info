package com.simplecourse.courseinfo.cli.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PluralSightCoursesRecordsTest {

    @Test
    void durationInMinutes() {
        PluralSightCoursesRecords records=new PluralSightCoursesRecords("id","test","00:05:37","url",false);
        assertEquals(5,records.durationInMinutes());
    }
    @ParameterizedTest
    @CsvSource(textBlock = """
            00:05:37, 5
            01:05:37.8997, 65
            00:00:00.00, 0
                """)
    void durationInMinutes(String input,String Expected) {
        PluralSightCoursesRecords records=new PluralSightCoursesRecords("id","test","00:05:37","url",false);
        assertEquals(5,records.durationInMinutes());
    }
}