package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Excel(name = "编号")
    private String id;
    @Excel(name = "手机")
    private String phone;
    @Excel(name = "密码")
    private String password;
    @Excel(name = "盐")
    private String salt;
    @Excel(name = "名字")
    private String dharmaName;
    @Excel(name = "省")
    private String province;
    @Excel(name = "市")
    private String city;
    @Excel(name = "性别")
    private String gender;
    @Excel(name = "个人签名")
    private String personalSign;
    @Excel(name = "头像", type = 2, width = 5)
    private String profile;
    @Excel(name = "状态")
    private String status;
    @Excel(name = "注册时间", format = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date registTime;
}
