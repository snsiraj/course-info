package com.simplecourse.courseinfo.domain;

import java.util.Optional;

public record Course(String id, String name, long length, String url, Optional<String> notes) {
    public Course{
        defaultValue(id);
        defaultValue(name);
        defaultValue(url);

    }
    private static void defaultValue(String s){
        if(s==null || s.isBlank())
            throw new IllegalArgumentException("No Value Present");
    }
}
