package com.baizhi.entity;

import com.baizhi.util.Abook;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressBook {
    @Abook(name = "编号")
    private String id;
    @Abook(name = "名字")
    private String name;
    @Abook(name = "城市")
    private String city;
    @Abook(name = "手机")
    private String phone;
    @Abook(name = "创建时间")
    private Date createTime;
}
