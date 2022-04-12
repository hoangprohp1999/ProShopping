package com.example.project_final_2.exception;

import java.util.Map;

public class DuplicateDataException extends Exception{

    private Map<String,String> mapError;

    /**
     * @param mapError : Map Error Field with Error Message: Ex mapError.put("email","Already exist")
     */
    public DuplicateDataException(Map<String,String> mapError){
        this.mapError = mapError;
    }

    public Map<String,String> getMapError(){
        return this.mapError;
    }
}
