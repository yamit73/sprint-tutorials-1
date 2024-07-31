package com.tutorials2.test.apiresponse;

import lombok.Data;
import java.util.List;

@Data
public class ApiResponse<T> {
    private String error;
    private List<T> data;
    private boolean success;

    public ApiResponse(boolean success, List<T> data, String error){
        this.success = success;
        this.data = data;
        this.error = error;
    }
}
