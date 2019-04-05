package com.alexvak.devconnectorrest.payload;

import com.alexvak.devconnectorrest.exception.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse {
    private MessageType messageType;
    private String message;
}
