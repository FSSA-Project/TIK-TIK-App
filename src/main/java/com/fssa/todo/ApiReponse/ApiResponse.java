package com.fssa.todo.ApiReponse;

/**
 * This is file send the data only as the
 * object to the user
 */
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponse<T> {
    private String message;
    private T data;
}



