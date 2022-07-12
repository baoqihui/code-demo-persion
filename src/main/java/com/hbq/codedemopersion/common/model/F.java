package com.hbq.codedemopersion.common.model;

import lombok.Builder;
import lombok.Data;

import java.util.HashSet;

@Data
@Builder
public class F {
    private String name;
    private String password;
    private Integer age;
    private String age2;
    private HashSet<S> s;
    public String fetchGroupKey(){
        return name+"#"+password;
    }
}
