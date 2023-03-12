package com.simplecourse.courseinfo.cli.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class CourseRetreiverService {
    private static final String PS_URI="https://app.pluralsight.com/profile/data/author/%s/all-content";
    private static final HttpClient CLIENT= HttpClient.newHttpClient();
    private static final ObjectMapper OBJECT_MAPPER =new ObjectMapper();
    public List<PluralSightCoursesRecords> getCoursesFor(String authorId){
        HttpRequest request = HttpRequest
                                .newBuilder(URI.create(PS_URI.formatted(authorId)))
                                .GET()
                                .build();


        try{
            HttpResponse<String> stringHttpResponse = CLIENT.send(request, HttpResponse.BodyHandlers.ofString());
            return switch (stringHttpResponse.statusCode()){
                case 200 -> toPluralSightCourses(stringHttpResponse);
                case 404 -> List.of();
                default -> throw new HttpRuntimeException("Pluralsight API call failed with status code:"+ stringHttpResponse.statusCode());
            };
        }
        catch(IOException | InterruptedException e){
            Thread.currentThread().interrupt();
            throw new HttpRuntimeException("Could not call Pluralsight API",e);
        }

    }

    private static List<PluralSightCoursesRecords> toPluralSightCourses(HttpResponse<String> stringHttpResponse) throws JsonProcessingException {
        JavaType returnType=OBJECT_MAPPER.getTypeFactory()
                .constructCollectionType(List.class, PluralSightCoursesRecords.class);
        return OBJECT_MAPPER.readValue(stringHttpResponse.body(),returnType);
    }
}
