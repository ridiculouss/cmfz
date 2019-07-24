package com.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Guru {
    private String id;
    private String name;
    private String profile;
    private String status;
    private String sex;
}
