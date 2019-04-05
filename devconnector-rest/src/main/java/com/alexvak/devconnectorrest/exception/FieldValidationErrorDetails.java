package com.alexvak.devconnectorrest.exception;

import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class FieldValidationErrorDetails {

    private String title;
    private int status;
    private String detail;
    private long timeStamp;
    private String path;
    private Map<String, List<FieldValidationError>> errors = new HashMap<>();
}
