package com.simplecourse.courseinfo.cli.service;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.Duration;
import java.time.LocalTime;


@JsonIgnoreProperties(ignoreUnknown = true)
public record PluralSightCoursesRecords(String id, String title, String duration, String contentUrl, boolean isRetired
                                        ) {
    public long durationInMinutes(){
        return Duration.between(LocalTime.MIN,LocalTime.parse(duration)).toMinutes();
    }

}
