package com.jukes.model;

import lombok.*;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
@Setter
@Builder
@NoArgsConstructor
public class ApiError {

    private HttpStatus status;
    private String message;
    private String timestamp;


}