package com.simplecourse.courseinfo.repo;

import java.sql.SQLException;

public class RepoException extends RuntimeException {
    public RepoException(String s, SQLException e) {
        super(s,e);
    }
}
