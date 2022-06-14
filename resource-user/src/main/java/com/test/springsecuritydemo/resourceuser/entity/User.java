package com.test.springsecuritydemo.resourceuser.entity;

import com.baomidou.mybatisplus.annotations.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("ds_sys_user")
public class User implements Serializable {

    private int id;

    private String userName;

    private String password;

}
