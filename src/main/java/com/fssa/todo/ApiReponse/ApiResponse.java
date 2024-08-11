package com.fssa.todo.ApiReponse;

/**
 * This is file send the data only as the
 * object to the user
 */
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponse<T> {
    private String message;
    private T data;
    private String token;

    // Constructor with 2 parameter
    public ApiResponse(String message, T data) {
        this.message = message;
        this.data = data;
    }

    // constructor with 3 parameter
    public ApiResponse(String message, T data, String token) {
        this.message = message;
        this.data = data;
        this.token = token;
    }


}



