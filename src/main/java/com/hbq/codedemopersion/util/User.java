package com.hbq.codedemopersion.util;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String name;
    private String gender;
    private School school;
    @Data
    public static class School {
        private String scName;
        private String adress;
    }
}