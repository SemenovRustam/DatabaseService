package com.semenov.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.semenov.dto.ExceptionDto;
import com.semenov.operation.Type;
import lombok.AllArgsConstructor;

import java.io.File;
import java.io.IOException;

@AllArgsConstructor
public class Utils {

    public static ObjectMapper objectMapper = new ObjectMapper();

    public static void writeExceptionInJsonFile(String message, String pathName) {
        ExceptionDto exceptionJson = ExceptionDto.builder()
                .type(String.valueOf(Type.ERROR.getType()))
                .message(message)
                .build();
        try {
            objectMapper.writeValue(new File(pathName), exceptionJson);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
