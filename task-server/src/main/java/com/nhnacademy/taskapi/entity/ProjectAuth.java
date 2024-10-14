package com.nhnacademy.taskapi.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProjectAuth {
    ADMIN, MEMBER;

    @JsonCreator
    public static ProjectAuth fromString(String str){
        for (ProjectAuth value : ProjectAuth.values()) {
            if (value.name().equalsIgnoreCase(str)) {
                return value;
            }
        }
        //default
        return MEMBER;
    }

    @JsonValue
    public String toJson(){
        return this.name().toLowerCase();
    }

}
