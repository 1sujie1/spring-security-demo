package com.test.springsecuritydemo.authserver.entity.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MenuDto implements Serializable {

    private int id;

    private String menuName;

    private String path;

    private String perms;

    private Date createTime;

    private Date updateTime;

    private String remark;

    private int state;

}
