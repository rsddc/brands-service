package com.weshopify.platform.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatusCode;

import java.io.Serial;
import java.time.LocalDateTime;


@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ApiException extends RuntimeException  {

    @Serial
    private static final long serialVersionUID = 7510316766190623476L;
    private String message;
    private int errorCode;
    private LocalDateTime timeStamp;

}
