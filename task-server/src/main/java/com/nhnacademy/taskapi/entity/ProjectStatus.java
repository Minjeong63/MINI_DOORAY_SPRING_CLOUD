package com.nhnacademy.taskapi.entity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum ProjectStatus {
    ACTIVE, INACTIVE, COMPLETE;

    @JsonCreator
    public static ProjectStatus fromString(String str){
        for (ProjectStatus value : ProjectStatus.values()) {
            if (value.name().equalsIgnoreCase(str)) {
                return value;
            }
        }
        //default
        return ACTIVE;
    }

    @JsonValue
    public String toJson(){
        return this.name().toLowerCase();
    }
}
